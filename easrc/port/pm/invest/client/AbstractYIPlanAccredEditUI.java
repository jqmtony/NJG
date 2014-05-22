/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

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
public abstract class AbstractYIPlanAccredEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractYIPlanAccredEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccredDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccredPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccredType;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremark;
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
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkaccredDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaccredPerson;
    protected com.kingdee.bos.ctrl.swing.KDComboBox accredType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea accredInformation;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtremark;
    protected com.kingdee.eas.port.pm.invest.YIPlanAccredInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractYIPlanAccredEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractYIPlanAccredEditUI.class.getName());
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
        this.contaccredDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccredPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccredType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contremark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.pkaccredDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtaccredPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.accredType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.accredInformation = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtremark = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.contaccredDate.setName("contaccredDate");
        this.contaccredPerson.setName("contaccredPerson");
        this.contaccredType.setName("contaccredType");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.contremark.setName("contremark");
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
        this.pkaccredDate.setName("pkaccredDate");
        this.prmtaccredPerson.setName("prmtaccredPerson");
        this.accredType.setName("accredType");
        this.kdtE1.setName("kdtE1");
        this.kdtE2.setName("kdtE2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.accredInformation.setName("accredInformation");
        this.txtremark.setName("txtremark");
        // CoreUI		
        this.setPreferredSize(new Dimension(1000,670));
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
        // contaccredDate		
        this.contaccredDate.setBoundLabelText(resHelper.getString("contaccredDate.boundLabelText"));		
        this.contaccredDate.setBoundLabelLength(100);		
        this.contaccredDate.setBoundLabelUnderline(true);		
        this.contaccredDate.setVisible(true);
        // contaccredPerson		
        this.contaccredPerson.setBoundLabelText(resHelper.getString("contaccredPerson.boundLabelText"));		
        this.contaccredPerson.setBoundLabelLength(100);		
        this.contaccredPerson.setBoundLabelUnderline(true);		
        this.contaccredPerson.setVisible(true);
        // contaccredType		
        this.contaccredType.setBoundLabelText(resHelper.getString("contaccredType.boundLabelText"));		
        this.contaccredType.setBoundLabelLength(100);		
        this.contaccredType.setBoundLabelUnderline(true);		
        this.contaccredType.setVisible(true);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);
        // contremark		
        this.contremark.setBoundLabelText(resHelper.getString("contremark.boundLabelText"));		
        this.contremark.setBoundLabelLength(100);		
        this.contremark.setBoundLabelUnderline(true);		
        this.contremark.setVisible(true);
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
        this.txtNumber.setEnabled(false);
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
        // pkaccredDate		
        this.pkaccredDate.setRequired(false);
        // prmtaccredPerson		
        this.prmtaccredPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtaccredPerson.setEditable(true);		
        this.prmtaccredPerson.setDisplayFormat("$name$");		
        this.prmtaccredPerson.setEditFormat("$number$");		
        this.prmtaccredPerson.setCommitFormat("$number$");		
        this.prmtaccredPerson.setRequired(false);		
        this.prmtaccredPerson.setEnabledMultiSelection(true);
        this.prmtaccredPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtaccredPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // accredType		
        this.accredType.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invest.AccredTypeEnum").toArray());		
        this.accredType.setRequired(false);		
        this.accredType.setEnabled(false);
        this.accredType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    accredType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"company\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectType\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"contentSReq\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accredResu\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"projectConclude\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{company}</t:Cell><t:Cell>$Resource{projectType}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{contentSReq}</t:Cell><t:Cell>$Resource{accredResu}</t:Cell><t:Cell>$Resource{projectConclude}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));
        this.kdtE1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtE1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.kdtE1.putBindContents("editData",new String[] {"seq","company","projectType","projectName","amount","contentSReq","accredResu","projectConclude"});


        this.kdtE1.checkParsed();
        final KDBizPromptBox kdtE1_company_PromptBox = new KDBizPromptBox();
        kdtE1_company_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.F7CUQuery");
        kdtE1_company_PromptBox.setVisible(true);
        kdtE1_company_PromptBox.setEditable(true);
        kdtE1_company_PromptBox.setDisplayFormat("$number$");
        kdtE1_company_PromptBox.setEditFormat("$number$");
        kdtE1_company_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_company_CellEditor = new KDTDefaultCellEditor(kdtE1_company_PromptBox);
        this.kdtE1.getColumn("company").setEditor(kdtE1_company_CellEditor);
        ObjectValueRender kdtE1_company_OVR = new ObjectValueRender();
        kdtE1_company_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("company").setRenderer(kdtE1_company_OVR);
        final KDBizPromptBox kdtE1_projectType_PromptBox = new KDBizPromptBox();
        kdtE1_projectType_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectTypeQuery");
        kdtE1_projectType_PromptBox.setVisible(true);
        kdtE1_projectType_PromptBox.setEditable(true);
        kdtE1_projectType_PromptBox.setDisplayFormat("$number$");
        kdtE1_projectType_PromptBox.setEditFormat("$number$");
        kdtE1_projectType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_projectType_CellEditor = new KDTDefaultCellEditor(kdtE1_projectType_PromptBox);
        this.kdtE1.getColumn("projectType").setEditor(kdtE1_projectType_CellEditor);
        ObjectValueRender kdtE1_projectType_OVR = new ObjectValueRender();
        kdtE1_projectType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("projectType").setRenderer(kdtE1_projectType_OVR);
        final KDBizPromptBox kdtE1_projectName_PromptBox = new KDBizPromptBox();
        kdtE1_projectName_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.invest.app.YearInvestPlanQuery");
        kdtE1_projectName_PromptBox.setVisible(true);
        kdtE1_projectName_PromptBox.setEditable(true);
        kdtE1_projectName_PromptBox.setDisplayFormat("$number$");
        kdtE1_projectName_PromptBox.setEditFormat("$number$");
        kdtE1_projectName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_projectName_CellEditor = new KDTDefaultCellEditor(kdtE1_projectName_PromptBox);
        this.kdtE1.getColumn("projectName").setEditor(kdtE1_projectName_CellEditor);
        ObjectValueRender kdtE1_projectName_OVR = new ObjectValueRender();
        kdtE1_projectName_OVR.setFormat(new BizDataFormat("$projectName$"));
        this.kdtE1.getColumn("projectName").setRenderer(kdtE1_projectName_OVR);
        KDFormattedTextField kdtE1_amount_TextField = new KDFormattedTextField();
        kdtE1_amount_TextField.setName("kdtE1_amount_TextField");
        kdtE1_amount_TextField.setVisible(true);
        kdtE1_amount_TextField.setEditable(true);
        kdtE1_amount_TextField.setHorizontalAlignment(2);
        kdtE1_amount_TextField.setDataType(1);
        	kdtE1_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_amount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_amount_CellEditor = new KDTDefaultCellEditor(kdtE1_amount_TextField);
        this.kdtE1.getColumn("amount").setEditor(kdtE1_amount_CellEditor);
        KDComboBox kdtE1_accredResu_ComboBox = new KDComboBox();
        kdtE1_accredResu_ComboBox.setName("kdtE1_accredResu_ComboBox");
        kdtE1_accredResu_ComboBox.setVisible(true);
        kdtE1_accredResu_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invest.ObjectStateEnum").toArray());
        KDTDefaultCellEditor kdtE1_accredResu_CellEditor = new KDTDefaultCellEditor(kdtE1_accredResu_ComboBox);
        this.kdtE1.getColumn("accredResu").setEditor(kdtE1_accredResu_CellEditor);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"accredDpart\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accredPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"opinion\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accreConclu\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{accredDpart}</t:Cell><t:Cell>$Resource{accredPerson}</t:Cell><t:Cell>$Resource{opinion}</t:Cell><t:Cell>$Resource{accreConclu}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));

                this.kdtE2.putBindContents("editData",new String[] {"E2.seq","E2.accredDpart","E2.accredPerson","E2.opinion","E2.accreConclu","E2.remark"});


        this.kdtE2.checkParsed();
        final KDBizPromptBox kdtE2_accredDpart_PromptBox = new KDBizPromptBox();
        kdtE2_accredDpart_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtE2_accredDpart_PromptBox.setVisible(true);
        kdtE2_accredDpart_PromptBox.setEditable(true);
        kdtE2_accredDpart_PromptBox.setDisplayFormat("$number$");
        kdtE2_accredDpart_PromptBox.setEditFormat("$number$");
        kdtE2_accredDpart_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE2_accredDpart_CellEditor = new KDTDefaultCellEditor(kdtE2_accredDpart_PromptBox);
        this.kdtE2.getColumn("accredDpart").setEditor(kdtE2_accredDpart_CellEditor);
        ObjectValueRender kdtE2_accredDpart_OVR = new ObjectValueRender();
        kdtE2_accredDpart_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE2.getColumn("accredDpart").setRenderer(kdtE2_accredDpart_OVR);
        final KDBizPromptBox kdtE2_accredPerson_PromptBox = new KDBizPromptBox();
        kdtE2_accredPerson_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtE2_accredPerson_PromptBox.setVisible(true);
        kdtE2_accredPerson_PromptBox.setEditable(true);
        kdtE2_accredPerson_PromptBox.setDisplayFormat("$number$");
        kdtE2_accredPerson_PromptBox.setEditFormat("$number$");
        kdtE2_accredPerson_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE2_accredPerson_CellEditor = new KDTDefaultCellEditor(kdtE2_accredPerson_PromptBox);
        this.kdtE2.getColumn("accredPerson").setEditor(kdtE2_accredPerson_CellEditor);
        ObjectValueRender kdtE2_accredPerson_OVR = new ObjectValueRender();
        kdtE2_accredPerson_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE2.getColumn("accredPerson").setRenderer(kdtE2_accredPerson_OVR);
        KDTextField kdtE2_opinion_TextField = new KDTextField();
        kdtE2_opinion_TextField.setName("kdtE2_opinion_TextField");
        kdtE2_opinion_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE2_opinion_CellEditor = new KDTDefaultCellEditor(kdtE2_opinion_TextField);
        this.kdtE2.getColumn("opinion").setEditor(kdtE2_opinion_CellEditor);
        KDTextField kdtE2_accreConclu_TextField = new KDTextField();
        kdtE2_accreConclu_TextField.setName("kdtE2_accreConclu_TextField");
        kdtE2_accreConclu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_accreConclu_CellEditor = new KDTDefaultCellEditor(kdtE2_accreConclu_TextField);
        this.kdtE2.getColumn("accreConclu").setEditor(kdtE2_accreConclu_CellEditor);
        KDTextField kdtE2_remark_TextField = new KDTextField();
        kdtE2_remark_TextField.setName("kdtE2_remark_TextField");
        kdtE2_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE2_remark_CellEditor = new KDTDefaultCellEditor(kdtE2_remark_TextField);
        this.kdtE2.getColumn("remark").setEditor(kdtE2_remark_CellEditor);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(1);		
        this.kDLabelContainer1.setBoundLabelAlignment(8);
        // kDScrollPane1
        // accredInformation
        // txtremark		
        this.txtremark.setHorizontalAlignment(2);		
        this.txtremark.setMaxLength(100);		
        this.txtremark.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {pkaccredDate,kdtE2,prmtaccredPerson,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,accredType,txtremark,kdtE1}));
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
        this.setBounds(new Rectangle(11, 8, 949, 725));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(11, 8, 949, 725));
        contCreator.setBounds(new Rectangle(12, 663, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(12, 663, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(12, 689, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(12, 689, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(322, 664, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(322, 664, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(322, 690, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(322, 690, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(341, 12, 270, 19));
        this.add(contCU, new KDLayout.Constraints(341, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(14, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(14, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(286, 697, 32, 19));
        this.add(contBizDate, new KDLayout.Constraints(286, 697, 32, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(613, 702, 16, 19));
        this.add(contDescription, new KDLayout.Constraints(613, 702, 16, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(633, 663, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(633, 663, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(668, 12, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(668, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(903, 695, 34, 19));
        this.add(contBizStatus, new KDLayout.Constraints(903, 695, 34, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(633, 689, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(633, 689, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaccredDate.setBounds(new Rectangle(341, 38, 270, 19));
        this.add(contaccredDate, new KDLayout.Constraints(341, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaccredPerson.setBounds(new Rectangle(668, 38, 270, 19));
        this.add(contaccredPerson, new KDLayout.Constraints(668, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaccredType.setBounds(new Rectangle(14, 38, 270, 19));
        this.add(contaccredType, new KDLayout.Constraints(14, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(14, 65, 923, 383));
        this.add(kDContainer1, new KDLayout.Constraints(14, 65, 923, 383, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(14, 452, 923, 206));
        this.add(kDContainer2, new KDLayout.Constraints(14, 452, 923, 206, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contremark.setBounds(new Rectangle(319, 708, 270, 19));
        this.add(contremark, new KDLayout.Constraints(319, 708, 270, 19, 0));
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
        //contaccredDate
        contaccredDate.setBoundEditor(pkaccredDate);
        //contaccredPerson
        contaccredPerson.setBoundEditor(prmtaccredPerson);
        //contaccredType
        contaccredType.setBoundEditor(accredType);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info(),null,false);
        kDContainer1.getContentPane().add(kdtE1_detailPanel, BorderLayout.CENTER);
		kdtE1_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("accredResu","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info(),null,false);
        kDContainer2.getContentPane().add(kdtE2_detailPanel, BorderLayout.NORTH);
        kDContainer2.getContentPane().add(kDLabelContainer1, BorderLayout.SOUTH);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(accredInformation, null);
        //contremark
        contremark.setBoundEditor(txtremark);

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
		dataBinder.registerBinding("accredDate", java.util.Date.class, this.pkaccredDate, "value");
		dataBinder.registerBinding("accredPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtaccredPerson, "data");
		dataBinder.registerBinding("accredType", com.kingdee.eas.port.pm.invest.AccredTypeEnum.class, this.accredType, "selectedItem");
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.projectType", java.lang.Object.class, this.kdtE1, "projectType.text");
		dataBinder.registerBinding("E1.amount", java.math.BigDecimal.class, this.kdtE1, "amount.text");
		dataBinder.registerBinding("E1.projectConclude", String.class, this.kdtE1, "projectConclude.text");
		dataBinder.registerBinding("E1.contentSReq", String.class, this.kdtE1, "contentSReq.text");
		dataBinder.registerBinding("E1.company", java.lang.Object.class, this.kdtE1, "company.text");
		dataBinder.registerBinding("E1.accredResu", com.kingdee.util.enums.Enum.class, this.kdtE1, "accredResu.text");
		dataBinder.registerBinding("E1.projectName", java.lang.Object.class, this.kdtE1, "projectName.text");
		dataBinder.registerBinding("E1.E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E1.E2", com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E1.E2.accredDpart", java.lang.Object.class, this.kdtE2, "accredDpart.text");
		dataBinder.registerBinding("E1.E2.accredPerson", java.lang.Object.class, this.kdtE2, "accredPerson.text");
		dataBinder.registerBinding("E1.E2.opinion", String.class, this.kdtE2, "opinion.text");
		dataBinder.registerBinding("E1.E2.accreConclu", String.class, this.kdtE2, "accreConclu.text");
		dataBinder.registerBinding("E1.E2.remark", String.class, this.kdtE2, "remark.text");
		dataBinder.registerBinding("accredInformation", String.class, this.accredInformation, "text");
		dataBinder.registerBinding("remark", String.class, this.txtremark, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.app.YIPlanAccredEditUIHandler";
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
        this.pkaccredDate.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invest.YIPlanAccredInfo)ov;
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
		getValidateHelper().registerBindProperty("accredDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accredPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accredType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.projectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.projectConclude", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.contentSReq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.accredResu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.accredDpart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.accredPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.opinion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.accreConclu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.E2.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accredInformation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    		
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
     * output prmtaccredPerson_dataChanged method
     */
    protected void prmtaccredPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output accredType_actionPerformed method
     */
    protected void accredType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtE1_tableClicked method
     */
    protected void kdtE1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("accredDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accredPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accredPerson.id"));
        	sic.add(new SelectorItemInfo("accredPerson.number"));
        	sic.add(new SelectorItemInfo("accredPerson.name"));
		}
        sic.add(new SelectorItemInfo("accredType"));
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.projectType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.projectType.id"));
			sic.add(new SelectorItemInfo("E1.projectType.name"));
        	sic.add(new SelectorItemInfo("E1.projectType.number"));
		}
    	sic.add(new SelectorItemInfo("E1.amount"));
    	sic.add(new SelectorItemInfo("E1.projectConclude"));
    	sic.add(new SelectorItemInfo("E1.contentSReq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.company.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.company.id"));
			sic.add(new SelectorItemInfo("E1.company.name"));
        	sic.add(new SelectorItemInfo("E1.company.number"));
		}
    	sic.add(new SelectorItemInfo("E1.accredResu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.projectName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.projectName.id"));
			sic.add(new SelectorItemInfo("E1.projectName.projectName"));
        	sic.add(new SelectorItemInfo("E1.projectName.number"));
		}
    	sic.add(new SelectorItemInfo("E1.E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.E2.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.E2.id"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.E2.accredDpart.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.E2.accredDpart.id"));
			sic.add(new SelectorItemInfo("E1.E2.accredDpart.name"));
        	sic.add(new SelectorItemInfo("E1.E2.accredDpart.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.E2.accredPerson.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.E2.accredPerson.id"));
			sic.add(new SelectorItemInfo("E1.E2.accredPerson.name"));
        	sic.add(new SelectorItemInfo("E1.E2.accredPerson.number"));
		}
    	sic.add(new SelectorItemInfo("E1.E2.opinion"));
    	sic.add(new SelectorItemInfo("E1.E2.accreConclu"));
    	sic.add(new SelectorItemInfo("E1.E2.remark"));
        sic.add(new SelectorItemInfo("accredInformation"));
        sic.add(new SelectorItemInfo("remark"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.client", "YIPlanAccredEditUI");
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
        return com.kingdee.eas.port.pm.invest.client.YIPlanAccredEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.YIPlanAccredFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.YIPlanAccredInfo objectValue = new com.kingdee.eas.port.pm.invest.YIPlanAccredInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invest/YIPlanAccred";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invest.app.YIPlanAccredQuery");
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
		vo.put("accredType","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}