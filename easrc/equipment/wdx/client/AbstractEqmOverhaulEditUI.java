/**
 * output package name
 */
package com.kingdee.eas.port.equipment.wdx.client;

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
public abstract class AbstractEqmOverhaulEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEqmOverhaulEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contimplementUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexpenseAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contestimateCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompleteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectLeader;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtimplementUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexpenseAccount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtplanCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtestimateCost;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcompleteDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectLeader;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRepairProgram;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneRepairProgram;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRepairProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFinish;
    protected com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo editData = null;
    protected ActionFinish actionFinish = null;
    /**
     * output class constructor
     */
    public AbstractEqmOverhaulEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEqmOverhaulEditUI.class.getName());
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
        //actionFinish
        this.actionFinish = new ActionFinish(this);
        getActionManager().registerAction("actionFinish", actionFinish);
        this.actionFinish.setExtendProperty("canForewarn", "true");
        this.actionFinish.setExtendProperty("userDefined", "true");
        this.actionFinish.setExtendProperty("isObjectUpdateLock", "false");
         this.actionFinish.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFinish.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contimplementUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexpenseAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contestimateCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcompleteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectLeader = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtimplementUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtexpenseAccount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtplanCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtestimateCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkcompleteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkstartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtprojectLeader = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRepairProgram = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneRepairProgram = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtRepairProgram = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnFinish = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.contCU.setName("contCU");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contStatus.setName("contStatus");
        this.contimplementUnit.setName("contimplementUnit");
        this.contprojectNumber.setName("contprojectNumber");
        this.contprojectName.setName("contprojectName");
        this.contexpenseAccount.setName("contexpenseAccount");
        this.contplanCost.setName("contplanCost");
        this.contestimateCost.setName("contestimateCost");
        this.contcompleteDate.setName("contcompleteDate");
        this.contstartDate.setName("contstartDate");
        this.contprojectLeader.setName("contprojectLeader");
        this.kdtE1.setName("kdtE1");
        this.prmtCU.setName("prmtCU");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.comboStatus.setName("comboStatus");
        this.prmtimplementUnit.setName("prmtimplementUnit");
        this.prmtprojectNumber.setName("prmtprojectNumber");
        this.prmtprojectName.setName("prmtprojectName");
        this.txtexpenseAccount.setName("txtexpenseAccount");
        this.txtplanCost.setName("txtplanCost");
        this.txtestimateCost.setName("txtestimateCost");
        this.pkcompleteDate.setName("pkcompleteDate");
        this.pkstartDate.setName("pkstartDate");
        this.prmtprojectLeader.setName("prmtprojectLeader");
        this.contRepairProgram.setName("contRepairProgram");
        this.scrollPaneRepairProgram.setName("scrollPaneRepairProgram");
        this.txtRepairProgram.setName("txtRepairProgram");
        this.btnFinish.setName("btnFinish");
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
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setEnabled(false);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
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
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // kDPanel1
        // kDPanel2
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
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contimplementUnit		
        this.contimplementUnit.setBoundLabelText(resHelper.getString("contimplementUnit.boundLabelText"));		
        this.contimplementUnit.setBoundLabelLength(100);		
        this.contimplementUnit.setBoundLabelUnderline(true);		
        this.contimplementUnit.setVisible(true);
        // contprojectNumber		
        this.contprojectNumber.setBoundLabelText(resHelper.getString("contprojectNumber.boundLabelText"));		
        this.contprojectNumber.setBoundLabelLength(100);		
        this.contprojectNumber.setBoundLabelUnderline(true);		
        this.contprojectNumber.setVisible(true);
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contexpenseAccount		
        this.contexpenseAccount.setBoundLabelText(resHelper.getString("contexpenseAccount.boundLabelText"));		
        this.contexpenseAccount.setBoundLabelLength(100);		
        this.contexpenseAccount.setBoundLabelUnderline(true);		
        this.contexpenseAccount.setVisible(true);
        // contplanCost		
        this.contplanCost.setBoundLabelText(resHelper.getString("contplanCost.boundLabelText"));		
        this.contplanCost.setBoundLabelLength(100);		
        this.contplanCost.setBoundLabelUnderline(true);		
        this.contplanCost.setVisible(true);
        // contestimateCost		
        this.contestimateCost.setBoundLabelText(resHelper.getString("contestimateCost.boundLabelText"));		
        this.contestimateCost.setBoundLabelLength(100);		
        this.contestimateCost.setBoundLabelUnderline(true);		
        this.contestimateCost.setVisible(true);
        // contcompleteDate		
        this.contcompleteDate.setBoundLabelText(resHelper.getString("contcompleteDate.boundLabelText"));		
        this.contcompleteDate.setBoundLabelLength(100);		
        this.contcompleteDate.setBoundLabelUnderline(true);		
        this.contcompleteDate.setVisible(true);
        // contstartDate		
        this.contstartDate.setBoundLabelText(resHelper.getString("contstartDate.boundLabelText"));		
        this.contstartDate.setBoundLabelLength(100);		
        this.contstartDate.setBoundLabelUnderline(true);		
        this.contstartDate.setVisible(true);
        // contprojectLeader		
        this.contprojectLeader.setBoundLabelText(resHelper.getString("contprojectLeader.boundLabelText"));		
        this.contprojectLeader.setBoundLabelLength(100);		
        this.contprojectLeader.setBoundLabelUnderline(true);		
        this.contprojectLeader.setVisible(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"equNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"equType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"useDepart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equTecParametetr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{equNumber}</t:Cell><t:Cell>$Resource{equName}</t:Cell><t:Cell>$Resource{equType}</t:Cell><t:Cell>$Resource{useDepart}</t:Cell><t:Cell>$Resource{equTecParametetr}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));
        kdtE1.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtE1_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtE1.putBindContents("editData",new String[] {"seq","equNumber","equName","equType","useDepart","equTecParametetr","remark"});


        this.kdtE1.checkParsed();
        final KDBizPromptBox kdtE1_equNumber_PromptBox = new KDBizPromptBox();
        kdtE1_equNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
        kdtE1_equNumber_PromptBox.setVisible(true);
        kdtE1_equNumber_PromptBox.setEditable(true);
        kdtE1_equNumber_PromptBox.setDisplayFormat("$number$");
        kdtE1_equNumber_PromptBox.setEditFormat("$number$");
        kdtE1_equNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_equNumber_CellEditor = new KDTDefaultCellEditor(kdtE1_equNumber_PromptBox);
        this.kdtE1.getColumn("equNumber").setEditor(kdtE1_equNumber_CellEditor);
        ObjectValueRender kdtE1_equNumber_OVR = new ObjectValueRender();
        kdtE1_equNumber_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtE1.getColumn("equNumber").setRenderer(kdtE1_equNumber_OVR);
        KDTextField kdtE1_equName_TextField = new KDTextField();
        kdtE1_equName_TextField.setName("kdtE1_equName_TextField");
        kdtE1_equName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_equName_CellEditor = new KDTDefaultCellEditor(kdtE1_equName_TextField);
        this.kdtE1.getColumn("equName").setEditor(kdtE1_equName_CellEditor);
        KDTextField kdtE1_equType_TextField = new KDTextField();
        kdtE1_equType_TextField.setName("kdtE1_equType_TextField");
        kdtE1_equType_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_equType_CellEditor = new KDTDefaultCellEditor(kdtE1_equType_TextField);
        this.kdtE1.getColumn("equType").setEditor(kdtE1_equType_CellEditor);
        final KDBizPromptBox kdtE1_useDepart_PromptBox = new KDBizPromptBox();
        kdtE1_useDepart_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtE1_useDepart_PromptBox.setVisible(true);
        kdtE1_useDepart_PromptBox.setEditable(true);
        kdtE1_useDepart_PromptBox.setDisplayFormat("$number$");
        kdtE1_useDepart_PromptBox.setEditFormat("$number$");
        kdtE1_useDepart_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_useDepart_CellEditor = new KDTDefaultCellEditor(kdtE1_useDepart_PromptBox);
        this.kdtE1.getColumn("useDepart").setEditor(kdtE1_useDepart_CellEditor);
        ObjectValueRender kdtE1_useDepart_OVR = new ObjectValueRender();
        kdtE1_useDepart_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("useDepart").setRenderer(kdtE1_useDepart_OVR);
        KDTextField kdtE1_equTecParametetr_TextField = new KDTextField();
        kdtE1_equTecParametetr_TextField.setName("kdtE1_equTecParametetr_TextField");
        kdtE1_equTecParametetr_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_equTecParametetr_CellEditor = new KDTDefaultCellEditor(kdtE1_equTecParametetr_TextField);
        this.kdtE1.getColumn("equTecParametetr").setEditor(kdtE1_equTecParametetr_CellEditor);
        KDTextField kdtE1_remark_TextField = new KDTextField();
        kdtE1_remark_TextField.setName("kdtE1_remark_TextField");
        kdtE1_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE1_remark_CellEditor = new KDTDefaultCellEditor(kdtE1_remark_TextField);
        this.kdtE1.getColumn("remark").setEditor(kdtE1_remark_CellEditor);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkBizDate
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);		
        this.comboStatus.setRequired(true);
        // prmtimplementUnit		
        this.prmtimplementUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtimplementUnit.setEditable(true);		
        this.prmtimplementUnit.setDisplayFormat("$name$");		
        this.prmtimplementUnit.setEditFormat("$number$");		
        this.prmtimplementUnit.setCommitFormat("$number$");		
        this.prmtimplementUnit.setRequired(false);
        // prmtprojectNumber		
        this.prmtprojectNumber.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtprojectNumber.setEditable(true);		
        this.prmtprojectNumber.setDisplayFormat("$number$");		
        this.prmtprojectNumber.setEditFormat("$number$");		
        this.prmtprojectNumber.setCommitFormat("$number$");		
        this.prmtprojectNumber.setRequired(false);
        // prmtprojectName		
        this.prmtprojectName.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtprojectName.setEditable(true);		
        this.prmtprojectName.setDisplayFormat("$name$");		
        this.prmtprojectName.setEditFormat("$number$");		
        this.prmtprojectName.setCommitFormat("$number$");		
        this.prmtprojectName.setRequired(false);
        // txtexpenseAccount		
        this.txtexpenseAccount.setHorizontalAlignment(2);		
        this.txtexpenseAccount.setDataType(1);		
        this.txtexpenseAccount.setSupportedEmpty(true);		
        this.txtexpenseAccount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtexpenseAccount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtexpenseAccount.setPrecision(2);		
        this.txtexpenseAccount.setRequired(false);
        // txtplanCost		
        this.txtplanCost.setHorizontalAlignment(2);		
        this.txtplanCost.setDataType(1);		
        this.txtplanCost.setSupportedEmpty(true);		
        this.txtplanCost.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtplanCost.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtplanCost.setPrecision(2);		
        this.txtplanCost.setRequired(false);
        // txtestimateCost		
        this.txtestimateCost.setHorizontalAlignment(2);		
        this.txtestimateCost.setDataType(1);		
        this.txtestimateCost.setSupportedEmpty(true);		
        this.txtestimateCost.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtestimateCost.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtestimateCost.setPrecision(2);		
        this.txtestimateCost.setRequired(false);
        // pkcompleteDate		
        this.pkcompleteDate.setRequired(false);
        // pkstartDate		
        this.pkstartDate.setRequired(false);
        // prmtprojectLeader		
        this.prmtprojectLeader.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtprojectLeader.setEditable(true);		
        this.prmtprojectLeader.setDisplayFormat("$name$");		
        this.prmtprojectLeader.setEditFormat("$number$");		
        this.prmtprojectLeader.setCommitFormat("$number$");		
        this.prmtprojectLeader.setRequired(false);
        // contRepairProgram		
        this.contRepairProgram.setBoundLabelText(resHelper.getString("contRepairProgram.boundLabelText"));		
        this.contRepairProgram.setBoundLabelLength(100);		
        this.contRepairProgram.setBoundLabelUnderline(true);		
        this.contRepairProgram.setVisible(true);
        // scrollPaneRepairProgram
        // txtRepairProgram		
        this.txtRepairProgram.setRequired(false);		
        this.txtRepairProgram.setMaxLength(500);
        // btnFinish
        this.btnFinish.setAction((IItemAction)ActionProxyFactory.getProxy(actionFinish, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFinish.setText(resHelper.getString("btnFinish.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtimplementUnit,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,prmtprojectNumber,prmtprojectName,txtexpenseAccount,txtplanCost,txtestimateCost,prmtprojectLeader,pkstartDate,pkcompleteDate,txtRepairProgram,kdtE1}));
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
        this.setBounds(new Rectangle(10, 10, 1083, 662));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1083, 662));
        contCreator.setBounds(new Rectangle(11, 604, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(11, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(11, 628, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(11, 628, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(365, 604, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(365, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(365, 628, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(365, 628, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(1011, 643, 66, 19));
        this.add(contDescription, new KDLayout.Constraints(1011, 643, 66, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(719, 604, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(719, 604, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(1014, 627, 45, 19));
        this.add(contBizStatus, new KDLayout.Constraints(1014, 627, 45, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(719, 628, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(719, 628, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 985, 584));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 10, 985, 584, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 984, 551));        contCU.setBounds(new Rectangle(14, 39, 270, 19));
        kDPanel1.add(contCU, new KDLayout.Constraints(14, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(14, 10, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(14, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(681, 10, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(681, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(347, 10, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(347, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contimplementUnit.setBounds(new Rectangle(347, 39, 270, 19));
        kDPanel1.add(contimplementUnit, new KDLayout.Constraints(347, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectNumber.setBounds(new Rectangle(14, 68, 270, 19));
        kDPanel1.add(contprojectNumber, new KDLayout.Constraints(14, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectName.setBounds(new Rectangle(347, 68, 270, 19));
        kDPanel1.add(contprojectName, new KDLayout.Constraints(347, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contexpenseAccount.setBounds(new Rectangle(681, 39, 270, 19));
        kDPanel1.add(contexpenseAccount, new KDLayout.Constraints(681, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contplanCost.setBounds(new Rectangle(681, 68, 270, 19));
        kDPanel1.add(contplanCost, new KDLayout.Constraints(681, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contestimateCost.setBounds(new Rectangle(347, 97, 270, 19));
        kDPanel1.add(contestimateCost, new KDLayout.Constraints(347, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcompleteDate.setBounds(new Rectangle(14, 128, 270, 19));
        kDPanel1.add(contcompleteDate, new KDLayout.Constraints(14, 128, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contstartDate.setBounds(new Rectangle(681, 97, 270, 19));
        kDPanel1.add(contstartDate, new KDLayout.Constraints(681, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contprojectLeader.setBounds(new Rectangle(14, 97, 270, 19));
        kDPanel1.add(contprojectLeader, new KDLayout.Constraints(14, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtE1.setBounds(new Rectangle(14, 154, 937, 376));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.wdx.EqmOverhaulE1Info(),null,false);
        kDPanel1.add(kdtE1_detailPanel, new KDLayout.Constraints(14, 154, 937, 376, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contimplementUnit
        contimplementUnit.setBoundEditor(prmtimplementUnit);
        //contprojectNumber
        contprojectNumber.setBoundEditor(prmtprojectNumber);
        //contprojectName
        contprojectName.setBoundEditor(prmtprojectName);
        //contexpenseAccount
        contexpenseAccount.setBoundEditor(txtexpenseAccount);
        //contplanCost
        contplanCost.setBoundEditor(txtplanCost);
        //contestimateCost
        contestimateCost.setBoundEditor(txtestimateCost);
        //contcompleteDate
        contcompleteDate.setBoundEditor(pkcompleteDate);
        //contstartDate
        contstartDate.setBoundEditor(pkstartDate);
        //contprojectLeader
        contprojectLeader.setBoundEditor(prmtprojectLeader);
        //kDPanel2
        kDPanel2.setLayout(null);        contRepairProgram.setBounds(new Rectangle(11, 20, 946, 139));
        kDPanel2.add(contRepairProgram, null);
        //contRepairProgram
        contRepairProgram.setBoundEditor(scrollPaneRepairProgram);
        //scrollPaneRepairProgram
        scrollPaneRepairProgram.getViewport().add(txtRepairProgram, null);

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
        this.toolBar.add(btnFinish);
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
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.wdx.EqmOverhaulE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.equNumber", java.lang.Object.class, this.kdtE1, "equNumber.text");
		dataBinder.registerBinding("E1.useDepart", java.lang.Object.class, this.kdtE1, "useDepart.text");
		dataBinder.registerBinding("E1.equTecParametetr", String.class, this.kdtE1, "equTecParametetr.text");
		dataBinder.registerBinding("E1.equName", String.class, this.kdtE1, "equName.text");
		dataBinder.registerBinding("E1.equType", String.class, this.kdtE1, "equType.text");
		dataBinder.registerBinding("E1.remark", String.class, this.kdtE1, "remark.text");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("implementUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtimplementUnit, "data");
		dataBinder.registerBinding("projectNumber", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtprojectNumber, "data");
		dataBinder.registerBinding("projectName", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtprojectName, "data");
		dataBinder.registerBinding("expenseAccount", java.math.BigDecimal.class, this.txtexpenseAccount, "value");
		dataBinder.registerBinding("planCost", java.math.BigDecimal.class, this.txtplanCost, "value");
		dataBinder.registerBinding("estimateCost", java.math.BigDecimal.class, this.txtestimateCost, "value");
		dataBinder.registerBinding("completeDate", java.util.Date.class, this.pkcompleteDate, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkstartDate, "value");
		dataBinder.registerBinding("projectLeader", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtprojectLeader, "data");
		dataBinder.registerBinding("RepairProgram", String.class, this.txtRepairProgram, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.wdx.app.EqmOverhaulEditUIHandler";
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
        this.prmtimplementUnit.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.useDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equTecParametetr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("implementUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("expenseAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("estimateCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("completeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectLeader", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("RepairProgram", ValidateHelper.ON_SAVE);    		
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
     * output kdtE1_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE1_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equTecParametetr").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"size")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"name")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equType").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"number")));

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
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.equNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.equNumber.id"));
			sic.add(new SelectorItemInfo("E1.equNumber.number"));
			sic.add(new SelectorItemInfo("E1.equNumber.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.useDepart.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.useDepart.id"));
			sic.add(new SelectorItemInfo("E1.useDepart.name"));
        	sic.add(new SelectorItemInfo("E1.useDepart.number"));
		}
    	sic.add(new SelectorItemInfo("E1.equTecParametetr"));
    	sic.add(new SelectorItemInfo("E1.equName"));
    	sic.add(new SelectorItemInfo("E1.equType"));
    	sic.add(new SelectorItemInfo("E1.remark"));
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
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("implementUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("implementUnit.id"));
        	sic.add(new SelectorItemInfo("implementUnit.number"));
        	sic.add(new SelectorItemInfo("implementUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectNumber.id"));
        	sic.add(new SelectorItemInfo("projectNumber.number"));
        	sic.add(new SelectorItemInfo("projectNumber.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectName.id"));
        	sic.add(new SelectorItemInfo("projectName.number"));
        	sic.add(new SelectorItemInfo("projectName.name"));
		}
        sic.add(new SelectorItemInfo("expenseAccount"));
        sic.add(new SelectorItemInfo("planCost"));
        sic.add(new SelectorItemInfo("estimateCost"));
        sic.add(new SelectorItemInfo("completeDate"));
        sic.add(new SelectorItemInfo("startDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectLeader.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectLeader.id"));
        	sic.add(new SelectorItemInfo("projectLeader.number"));
        	sic.add(new SelectorItemInfo("projectLeader.name"));
		}
        sic.add(new SelectorItemInfo("RepairProgram"));
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
     * output actionFinish_actionPerformed method
     */
    public void actionFinish_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.wdx.EqmOverhaulFactory.getRemoteInstance().actionFinish(editData);
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
	public RequestContext prepareActionFinish(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFinish() {
    	return false;
    }

    /**
     * output ActionFinish class
     */     
    protected class ActionFinish extends ItemAction {     
    
        public ActionFinish()
        {
            this(null);
        }

        public ActionFinish(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFinish.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinish.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFinish.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEqmOverhaulEditUI.this, "ActionFinish", "actionFinish_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.wdx.client", "EqmOverhaulEditUI");
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
        return com.kingdee.eas.port.equipment.wdx.client.EqmOverhaulEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.wdx.EqmOverhaulFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo objectValue = new com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/wdx/EqmOverhaul";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.wdx.app.EqmOverhaulQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(comboStatus.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据状态"});
		}
			super.beforeStoreFields(arg0);
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