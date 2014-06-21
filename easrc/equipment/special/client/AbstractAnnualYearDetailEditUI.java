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
public abstract class AbstractAnnualYearDetailEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAnnualYearDetailEditUI.class);
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
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseDpatmen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisConfirmation;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDpatmen;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIssued;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnIssued;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirmation;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnConfirmation;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnConfirm;
    protected com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = null;
    protected ActionEntry actionEntry = null;
    protected ActionConfirmation actionConfirmation = null;
    protected ActionUnConfirmation actionUnConfirmation = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionUnConfirm actionUnConfirm = null;
    /**
     * output class constructor
     */
    public AbstractAnnualYearDetailEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAnnualYearDetailEditUI.class.getName());
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
        //actionEntry
        this.actionEntry = new ActionEntry(this);
        getActionManager().registerAction("actionEntry", actionEntry);
        this.actionEntry.setExtendProperty("canForewarn", "true");
        this.actionEntry.setExtendProperty("userDefined", "true");
        this.actionEntry.setExtendProperty("isObjectUpdateLock", "false");
         this.actionEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEntry.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionConfirmation
        this.actionConfirmation = new ActionConfirmation(this);
        getActionManager().registerAction("actionConfirmation", actionConfirmation);
        this.actionConfirmation.setExtendProperty("canForewarn", "true");
        this.actionConfirmation.setExtendProperty("userDefined", "true");
        this.actionConfirmation.setExtendProperty("isObjectUpdateLock", "false");
         this.actionConfirmation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionConfirmation.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnConfirmation
        this.actionUnConfirmation = new ActionUnConfirmation(this);
        getActionManager().registerAction("actionUnConfirmation", actionUnConfirmation);
        this.actionUnConfirmation.setExtendProperty("canForewarn", "true");
        this.actionUnConfirmation.setExtendProperty("userDefined", "true");
        this.actionUnConfirmation.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnConfirmation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnConfirmation.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
        this.actionConfirm.setExtendProperty("canForewarn", "true");
        this.actionConfirm.setExtendProperty("userDefined", "true");
        this.actionConfirm.setExtendProperty("isObjectUpdateLock", "false");
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnConfirm
        this.actionUnConfirm = new ActionUnConfirm(this);
        getActionManager().registerAction("actionUnConfirm", actionUnConfirm);
        this.actionUnConfirm.setExtendProperty("canForewarn", "true");
        this.actionUnConfirm.setExtendProperty("userDefined", "true");
        this.actionUnConfirm.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnConfirm.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contuseDpatmen = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisConfirmation = new com.kingdee.bos.ctrl.swing.KDCheckBox();
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
        this.prmtuseDpatmen = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnIssued = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnIssued = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnConfirmation = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnConfirmation = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
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
        this.kdtEntry.setName("kdtEntry");
        this.contuseDpatmen.setName("contuseDpatmen");
        this.chkisConfirmation.setName("chkisConfirmation");
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
        this.prmtuseDpatmen.setName("prmtuseDpatmen");
        this.btnIssued.setName("btnIssued");
        this.btnUnIssued.setName("btnUnIssued");
        this.btnConfirmation.setName("btnConfirmation");
        this.btnUnConfirmation.setName("btnUnConfirmation");
        this.btnConfirm.setName("btnConfirm");
        this.btnUnConfirm.setName("btnUnConfirm");
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
        this.contDescription.setEnabled(false);		
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
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"zdaNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equipmentName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"code\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"useUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"planDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"address\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"companyNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"NO\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"engineNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"carNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"useDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"createUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"checkType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"check\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"result\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{zdaNumber}</t:Cell><t:Cell>$Resource{equipmentName}</t:Cell><t:Cell>$Resource{code}</t:Cell><t:Cell>$Resource{useUnit}</t:Cell><t:Cell>$Resource{planDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{address}</t:Cell><t:Cell>$Resource{companyNumber}</t:Cell><t:Cell>$Resource{NO}</t:Cell><t:Cell>$Resource{engineNumber}</t:Cell><t:Cell>$Resource{carNumber}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{useDate}</t:Cell><t:Cell>$Resource{createUnit}</t:Cell><t:Cell>$Resource{checkType}</t:Cell><t:Cell>$Resource{check}</t:Cell><t:Cell>$Resource{result}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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


                this.kdtEntry.putBindContents("editData",new String[] {"seq","zdaNumber","equipmentName","code","useUnit","planDate","endDate","state","address","companyNumber","NO","engineNumber","carNumber","weight","useDate","createUnit","checkType","check","result","beizhu"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_seq_TextField = new KDFormattedTextField();
        kdtEntry_seq_TextField.setName("kdtEntry_seq_TextField");
        kdtEntry_seq_TextField.setVisible(true);
        kdtEntry_seq_TextField.setEditable(true);
        kdtEntry_seq_TextField.setHorizontalAlignment(2);
        kdtEntry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_seq_CellEditor = new KDTDefaultCellEditor(kdtEntry_seq_TextField);
        this.kdtEntry.getColumn("seq").setEditor(kdtEntry_seq_CellEditor);
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
        KDTextField kdtEntry_code_TextField = new KDTextField();
        kdtEntry_code_TextField.setName("kdtEntry_code_TextField");
        kdtEntry_code_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_code_CellEditor = new KDTDefaultCellEditor(kdtEntry_code_TextField);
        this.kdtEntry.getColumn("code").setEditor(kdtEntry_code_CellEditor);
        KDTextField kdtEntry_useUnit_TextField = new KDTextField();
        kdtEntry_useUnit_TextField.setName("kdtEntry_useUnit_TextField");
        kdtEntry_useUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_useUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_useUnit_TextField);
        this.kdtEntry.getColumn("useUnit").setEditor(kdtEntry_useUnit_CellEditor);
        KDDatePicker kdtEntry_planDate_DatePicker = new KDDatePicker();
        kdtEntry_planDate_DatePicker.setName("kdtEntry_planDate_DatePicker");
        kdtEntry_planDate_DatePicker.setVisible(true);
        kdtEntry_planDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_planDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_planDate_DatePicker);
        this.kdtEntry.getColumn("planDate").setEditor(kdtEntry_planDate_CellEditor);
        KDDatePicker kdtEntry_endDate_DatePicker = new KDDatePicker();
        kdtEntry_endDate_DatePicker.setName("kdtEntry_endDate_DatePicker");
        kdtEntry_endDate_DatePicker.setVisible(true);
        kdtEntry_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_endDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_endDate_DatePicker);
        this.kdtEntry.getColumn("endDate").setEditor(kdtEntry_endDate_CellEditor);
        KDComboBox kdtEntry_state_ComboBox = new KDComboBox();
        kdtEntry_state_ComboBox.setName("kdtEntry_state_ComboBox");
        kdtEntry_state_ComboBox.setVisible(true);
        kdtEntry_state_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.sbStatusType").toArray());
        KDTDefaultCellEditor kdtEntry_state_CellEditor = new KDTDefaultCellEditor(kdtEntry_state_ComboBox);
        this.kdtEntry.getColumn("state").setEditor(kdtEntry_state_CellEditor);
        KDTextField kdtEntry_address_TextField = new KDTextField();
        kdtEntry_address_TextField.setName("kdtEntry_address_TextField");
        kdtEntry_address_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_address_CellEditor = new KDTDefaultCellEditor(kdtEntry_address_TextField);
        this.kdtEntry.getColumn("address").setEditor(kdtEntry_address_CellEditor);
        KDTextField kdtEntry_companyNumber_TextField = new KDTextField();
        kdtEntry_companyNumber_TextField.setName("kdtEntry_companyNumber_TextField");
        kdtEntry_companyNumber_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_companyNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_companyNumber_TextField);
        this.kdtEntry.getColumn("companyNumber").setEditor(kdtEntry_companyNumber_CellEditor);
        KDTextField kdtEntry_NO_TextField = new KDTextField();
        kdtEntry_NO_TextField.setName("kdtEntry_NO_TextField");
        kdtEntry_NO_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_NO_CellEditor = new KDTDefaultCellEditor(kdtEntry_NO_TextField);
        this.kdtEntry.getColumn("NO").setEditor(kdtEntry_NO_CellEditor);
        KDTextField kdtEntry_engineNumber_TextField = new KDTextField();
        kdtEntry_engineNumber_TextField.setName("kdtEntry_engineNumber_TextField");
        kdtEntry_engineNumber_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_engineNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_engineNumber_TextField);
        this.kdtEntry.getColumn("engineNumber").setEditor(kdtEntry_engineNumber_CellEditor);
        KDTextField kdtEntry_carNumber_TextField = new KDTextField();
        kdtEntry_carNumber_TextField.setName("kdtEntry_carNumber_TextField");
        kdtEntry_carNumber_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_carNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_carNumber_TextField);
        this.kdtEntry.getColumn("carNumber").setEditor(kdtEntry_carNumber_CellEditor);
        KDFormattedTextField kdtEntry_weight_TextField = new KDFormattedTextField();
        kdtEntry_weight_TextField.setName("kdtEntry_weight_TextField");
        kdtEntry_weight_TextField.setVisible(true);
        kdtEntry_weight_TextField.setEditable(true);
        kdtEntry_weight_TextField.setHorizontalAlignment(2);
        kdtEntry_weight_TextField.setDataType(1);
        	kdtEntry_weight_TextField.setMinimumValue(new java.math.BigDecimal("-3.4028234663852886E38"));
        	kdtEntry_weight_TextField.setMaximumValue(new java.math.BigDecimal("3.4028234663852886E38"));
        kdtEntry_weight_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_weight_CellEditor = new KDTDefaultCellEditor(kdtEntry_weight_TextField);
        this.kdtEntry.getColumn("weight").setEditor(kdtEntry_weight_CellEditor);
        KDDatePicker kdtEntry_useDate_DatePicker = new KDDatePicker();
        kdtEntry_useDate_DatePicker.setName("kdtEntry_useDate_DatePicker");
        kdtEntry_useDate_DatePicker.setVisible(true);
        kdtEntry_useDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_useDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_useDate_DatePicker);
        this.kdtEntry.getColumn("useDate").setEditor(kdtEntry_useDate_CellEditor);
        KDTextField kdtEntry_createUnit_TextField = new KDTextField();
        kdtEntry_createUnit_TextField.setName("kdtEntry_createUnit_TextField");
        kdtEntry_createUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_createUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_createUnit_TextField);
        this.kdtEntry.getColumn("createUnit").setEditor(kdtEntry_createUnit_CellEditor);
        KDComboBox kdtEntry_checkType_ComboBox = new KDComboBox();
        kdtEntry_checkType_ComboBox.setName("kdtEntry_checkType_ComboBox");
        kdtEntry_checkType_ComboBox.setVisible(true);
        kdtEntry_checkType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.CheckType").toArray());
        KDTDefaultCellEditor kdtEntry_checkType_CellEditor = new KDTDefaultCellEditor(kdtEntry_checkType_ComboBox);
        this.kdtEntry.getColumn("checkType").setEditor(kdtEntry_checkType_CellEditor);
        KDCheckBox kdtEntry_check_CheckBox = new KDCheckBox();
        kdtEntry_check_CheckBox.setName("kdtEntry_check_CheckBox");
        KDTDefaultCellEditor kdtEntry_check_CellEditor = new KDTDefaultCellEditor(kdtEntry_check_CheckBox);
        this.kdtEntry.getColumn("check").setEditor(kdtEntry_check_CellEditor);
        KDComboBox kdtEntry_result_ComboBox = new KDComboBox();
        kdtEntry_result_ComboBox.setName("kdtEntry_result_ComboBox");
        kdtEntry_result_ComboBox.setVisible(true);
        kdtEntry_result_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.CheckResult").toArray());
        KDTDefaultCellEditor kdtEntry_result_CellEditor = new KDTDefaultCellEditor(kdtEntry_result_ComboBox);
        this.kdtEntry.getColumn("result").setEditor(kdtEntry_result_CellEditor);
        KDTextField kdtEntry_beizhu_TextField = new KDTextField();
        kdtEntry_beizhu_TextField.setName("kdtEntry_beizhu_TextField");
        kdtEntry_beizhu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntry_beizhu_TextField);
        this.kdtEntry.getColumn("beizhu").setEditor(kdtEntry_beizhu_CellEditor);
        // contuseDpatmen		
        this.contuseDpatmen.setBoundLabelText(resHelper.getString("contuseDpatmen.boundLabelText"));		
        this.contuseDpatmen.setBoundLabelLength(100);		
        this.contuseDpatmen.setBoundLabelUnderline(true);		
        this.contuseDpatmen.setVisible(true);
        // chkisConfirmation		
        this.chkisConfirmation.setText(resHelper.getString("chkisConfirmation.text"));		
        this.chkisConfirmation.setVisible(true);		
        this.chkisConfirmation.setHorizontalAlignment(2);		
        this.chkisConfirmation.setEnabled(false);
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
        // prmtuseDpatmen		
        this.prmtuseDpatmen.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtuseDpatmen.setVisible(true);		
        this.prmtuseDpatmen.setEditable(true);		
        this.prmtuseDpatmen.setDisplayFormat("$name$");		
        this.prmtuseDpatmen.setEditFormat("$number$");		
        this.prmtuseDpatmen.setCommitFormat("$number$");		
        this.prmtuseDpatmen.setRequired(false);
        // btnIssued		
        this.btnIssued.setText(resHelper.getString("btnIssued.text"));
        this.btnIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnIssued_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnUnIssued		
        this.btnUnIssued.setText(resHelper.getString("btnUnIssued.text"));
        this.btnUnIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUnIssued_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnConfirmation
        this.btnConfirmation.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirmation, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirmation.setText(resHelper.getString("btnConfirmation.text"));
        // btnUnConfirmation
        this.btnUnConfirmation.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnConfirmation, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnConfirmation.setText(resHelper.getString("btnUnConfirmation.text"));
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnUnConfirm
        this.btnUnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnConfirm.setText(resHelper.getString("btnUnConfirm.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,kdtEntry,prmtuseDpatmen,chkisConfirmation}));
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
        contCreator.setBounds(new Rectangle(35, 564, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(35, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 590, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        contDescription.setBounds(new Rectangle(517, 614, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(517, 614, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(369, 35, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(369, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntry.setBounds(new Rectangle(29, 65, 951, 493));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryInfo(),null,false);
        this.add(kdtEntry_detailPanel, new KDLayout.Constraints(29, 65, 951, 493, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		kdtEntry_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("state","1");
vo.put("checkType","10");
vo.put("result","10");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        contuseDpatmen.setBounds(new Rectangle(706, 35, 270, 19));
        this.add(contuseDpatmen, new KDLayout.Constraints(706, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkisConfirmation.setBounds(new Rectangle(33, 35, 270, 19));
        this.add(chkisConfirmation, new KDLayout.Constraints(33, 35, 270, 19, 0));
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
        //contuseDpatmen
        contuseDpatmen.setBoundEditor(prmtuseDpatmen);

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
        this.toolBar.add(btnIssued);
        this.toolBar.add(btnUnIssued);
        this.toolBar.add(btnConfirmation);
        this.toolBar.add(btnUnConfirmation);
        this.toolBar.add(btnConfirm);
        this.toolBar.add(btnUnConfirm);
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
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.zdaNumber", java.lang.Object.class, this.kdtEntry, "zdaNumber.text");
		dataBinder.registerBinding("Entry.equipmentName", String.class, this.kdtEntry, "equipmentName.text");
		dataBinder.registerBinding("Entry.code", String.class, this.kdtEntry, "code.text");
		dataBinder.registerBinding("Entry.useUnit", String.class, this.kdtEntry, "useUnit.text");
		dataBinder.registerBinding("Entry.planDate", java.util.Date.class, this.kdtEntry, "planDate.text");
		dataBinder.registerBinding("Entry.endDate", java.util.Date.class, this.kdtEntry, "endDate.text");
		dataBinder.registerBinding("Entry.state", com.kingdee.util.enums.Enum.class, this.kdtEntry, "state.text");
		dataBinder.registerBinding("Entry.address", String.class, this.kdtEntry, "address.text");
		dataBinder.registerBinding("Entry.companyNumber", String.class, this.kdtEntry, "companyNumber.text");
		dataBinder.registerBinding("Entry.NO", String.class, this.kdtEntry, "NO.text");
		dataBinder.registerBinding("Entry.engineNumber", String.class, this.kdtEntry, "engineNumber.text");
		dataBinder.registerBinding("Entry.carNumber", String.class, this.kdtEntry, "carNumber.text");
		dataBinder.registerBinding("Entry.weight", java.math.BigDecimal.class, this.kdtEntry, "weight.text");
		dataBinder.registerBinding("Entry.useDate", java.util.Date.class, this.kdtEntry, "useDate.text");
		dataBinder.registerBinding("Entry.createUnit", String.class, this.kdtEntry, "createUnit.text");
		dataBinder.registerBinding("Entry.checkType", com.kingdee.util.enums.Enum.class, this.kdtEntry, "checkType.text");
		dataBinder.registerBinding("Entry.beizhu", String.class, this.kdtEntry, "beizhu.text");
		dataBinder.registerBinding("Entry.check", boolean.class, this.kdtEntry, "check.text");
		dataBinder.registerBinding("Entry.result", com.kingdee.util.enums.Enum.class, this.kdtEntry, "result.text");
		dataBinder.registerBinding("isConfirmation", boolean.class, this.chkisConfirmation, "selected");
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
		dataBinder.registerBinding("useDpatmen", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDpatmen, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.special.app.AnnualYearDetailEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)ov;
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
		getValidateHelper().registerBindProperty("Entry.code", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.useUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.planDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.companyNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.NO", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.engineNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.carNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.useDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.createUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.checkType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.check", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.result", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isConfirmation", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("useDpatmen", ValidateHelper.ON_SAVE);    		
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
     * output btnIssued_actionPerformed method
     */
    protected void btnIssued_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnUnIssued_actionPerformed method
     */
    protected void btnUnIssued_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"equipmentName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"name")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"code").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"code")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"useUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"usingDept.name")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"state").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"tzsbStatus"));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"address").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"address.name")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"companyNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"innerNumber")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"NO").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"model")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"engineNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"engineNumber")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"carNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"carNumber")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"weight").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getBigDecimal(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"ratedWeight")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"useDate").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getDateValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"qyDate")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"createUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"mader")));

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
    	sic.add(new SelectorItemInfo("Entry.code"));
    	sic.add(new SelectorItemInfo("Entry.useUnit"));
    	sic.add(new SelectorItemInfo("Entry.planDate"));
    	sic.add(new SelectorItemInfo("Entry.endDate"));
    	sic.add(new SelectorItemInfo("Entry.state"));
    	sic.add(new SelectorItemInfo("Entry.address"));
    	sic.add(new SelectorItemInfo("Entry.companyNumber"));
    	sic.add(new SelectorItemInfo("Entry.NO"));
    	sic.add(new SelectorItemInfo("Entry.engineNumber"));
    	sic.add(new SelectorItemInfo("Entry.carNumber"));
    	sic.add(new SelectorItemInfo("Entry.weight"));
    	sic.add(new SelectorItemInfo("Entry.useDate"));
    	sic.add(new SelectorItemInfo("Entry.createUnit"));
    	sic.add(new SelectorItemInfo("Entry.checkType"));
    	sic.add(new SelectorItemInfo("Entry.beizhu"));
    	sic.add(new SelectorItemInfo("Entry.check"));
    	sic.add(new SelectorItemInfo("Entry.result"));
        sic.add(new SelectorItemInfo("isConfirmation"));
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
			sic.add(new SelectorItemInfo("useDpatmen.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useDpatmen.id"));
        	sic.add(new SelectorItemInfo("useDpatmen.number"));
        	sic.add(new SelectorItemInfo("useDpatmen.name"));
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
     * output actionEntry_actionPerformed method
     */
    public void actionEntry_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().entry(editData);
    }
    	

    /**
     * output actionConfirmation_actionPerformed method
     */
    public void actionConfirmation_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().confirmation(editData);
    }
    	

    /**
     * output actionUnConfirmation_actionPerformed method
     */
    public void actionUnConfirmation_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().unConfirmation(editData);
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().confirm(editData);
    }
    	

    /**
     * output actionUnConfirm_actionPerformed method
     */
    public void actionUnConfirm_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().unConfirm(editData);
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
	public RequestContext prepareActionEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEntry() {
    	return false;
    }
	public RequestContext prepareActionConfirmation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirmation() {
    	return false;
    }
	public RequestContext prepareActionUnConfirmation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnConfirmation() {
    	return false;
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
    	return false;
    }
	public RequestContext prepareActionUnConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnConfirm() {
    	return false;
    }

    /**
     * output ActionEntry class
     */     
    protected class ActionEntry extends ItemAction {     
    
        public ActionEntry()
        {
            this(null);
        }

        public ActionEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailEditUI.this, "ActionEntry", "actionEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirmation class
     */     
    protected class ActionConfirmation extends ItemAction {     
    
        public ActionConfirmation()
        {
            this(null);
        }

        public ActionConfirmation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirmation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailEditUI.this, "ActionConfirmation", "actionConfirmation_actionPerformed", e);
        }
    }

    /**
     * output ActionUnConfirmation class
     */     
    protected class ActionUnConfirmation extends ItemAction {     
    
        public ActionUnConfirmation()
        {
            this(null);
        }

        public ActionUnConfirmation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnConfirmation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirmation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirmation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailEditUI.this, "ActionUnConfirmation", "actionUnConfirmation_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailEditUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionUnConfirm class
     */     
    protected class ActionUnConfirm extends ItemAction {     
    
        public ActionUnConfirm()
        {
            this(null);
        }

        public ActionUnConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailEditUI.this, "ActionUnConfirm", "actionUnConfirm_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.special.client", "AnnualYearDetailEditUI");
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
        return com.kingdee.eas.port.equipment.special.client.AnnualYearDetailEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo objectValue = new com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/special/AnnualYearDetail";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.special.app.AnnualYearDetailQuery");
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