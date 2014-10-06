/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

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
public abstract class AbstractEquLeaseEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEquLeaseEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttheLessor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttheLessee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsigningDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleaseSales;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleaseStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleaseEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrentPay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthaveRented;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsurplus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleaseMent;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
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
    protected com.kingdee.bos.ctrl.swing.KDTextField txtconNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttheLessor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttheLessee;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksigningDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtleaseSales;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkleaseStart;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkleaseEnd;
    protected com.kingdee.bos.ctrl.swing.KDComboBox rentPay;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthaveRented;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtsurplus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox leaseMent;
    protected com.kingdee.eas.port.equipment.operate.EquLeaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEquLeaseEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEquLeaseEditUI.class.getName());
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
        this.contconNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttheLessor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttheLessee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsigningDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleaseSales = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleaseStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleaseEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrentPay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthaveRented = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsurplus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleaseMent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
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
        this.txtconNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttheLessor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttheLessee = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pksigningDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtleaseSales = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkleaseStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkleaseEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.rentPay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txthaveRented = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtsurplus = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.leaseMent = new com.kingdee.bos.ctrl.swing.KDComboBox();
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
        this.contconNo.setName("contconNo");
        this.conttheLessor.setName("conttheLessor");
        this.conttheLessee.setName("conttheLessee");
        this.contsigningDate.setName("contsigningDate");
        this.contleaseSales.setName("contleaseSales");
        this.contleaseStart.setName("contleaseStart");
        this.contleaseEnd.setName("contleaseEnd");
        this.contrentPay.setName("contrentPay");
        this.conthaveRented.setName("conthaveRented");
        this.contsurplus.setName("contsurplus");
        this.contleaseMent.setName("contleaseMent");
        this.kdtE1.setName("kdtE1");
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
        this.txtconNo.setName("txtconNo");
        this.txttheLessor.setName("txttheLessor");
        this.txttheLessee.setName("txttheLessee");
        this.pksigningDate.setName("pksigningDate");
        this.txtleaseSales.setName("txtleaseSales");
        this.pkleaseStart.setName("pkleaseStart");
        this.pkleaseEnd.setName("pkleaseEnd");
        this.rentPay.setName("rentPay");
        this.txthaveRented.setName("txthaveRented");
        this.txtsurplus.setName("txtsurplus");
        this.leaseMent.setName("leaseMent");
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
        this.contBizDate.setEnabled(false);		
        this.contBizDate.setVisible(false);
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
        // contconNo		
        this.contconNo.setBoundLabelText(resHelper.getString("contconNo.boundLabelText"));		
        this.contconNo.setBoundLabelLength(100);		
        this.contconNo.setBoundLabelUnderline(true);		
        this.contconNo.setVisible(true);
        // conttheLessor		
        this.conttheLessor.setBoundLabelText(resHelper.getString("conttheLessor.boundLabelText"));		
        this.conttheLessor.setBoundLabelLength(100);		
        this.conttheLessor.setBoundLabelUnderline(true);		
        this.conttheLessor.setVisible(true);
        // conttheLessee		
        this.conttheLessee.setBoundLabelText(resHelper.getString("conttheLessee.boundLabelText"));		
        this.conttheLessee.setBoundLabelLength(100);		
        this.conttheLessee.setBoundLabelUnderline(true);		
        this.conttheLessee.setVisible(true);
        // contsigningDate		
        this.contsigningDate.setBoundLabelText(resHelper.getString("contsigningDate.boundLabelText"));		
        this.contsigningDate.setBoundLabelLength(100);		
        this.contsigningDate.setBoundLabelUnderline(true);		
        this.contsigningDate.setVisible(true);
        // contleaseSales		
        this.contleaseSales.setBoundLabelText(resHelper.getString("contleaseSales.boundLabelText"));		
        this.contleaseSales.setBoundLabelLength(100);		
        this.contleaseSales.setBoundLabelUnderline(true);		
        this.contleaseSales.setVisible(true);
        // contleaseStart		
        this.contleaseStart.setBoundLabelText(resHelper.getString("contleaseStart.boundLabelText"));		
        this.contleaseStart.setBoundLabelLength(100);		
        this.contleaseStart.setBoundLabelUnderline(true);		
        this.contleaseStart.setVisible(true);
        // contleaseEnd		
        this.contleaseEnd.setBoundLabelText(resHelper.getString("contleaseEnd.boundLabelText"));		
        this.contleaseEnd.setBoundLabelLength(100);		
        this.contleaseEnd.setBoundLabelUnderline(true);		
        this.contleaseEnd.setVisible(true);
        // contrentPay		
        this.contrentPay.setBoundLabelText(resHelper.getString("contrentPay.boundLabelText"));		
        this.contrentPay.setBoundLabelLength(100);		
        this.contrentPay.setBoundLabelUnderline(true);		
        this.contrentPay.setVisible(true);
        // conthaveRented		
        this.conthaveRented.setBoundLabelText(resHelper.getString("conthaveRented.boundLabelText"));		
        this.conthaveRented.setBoundLabelLength(100);		
        this.conthaveRented.setBoundLabelUnderline(true);		
        this.conthaveRented.setVisible(true);
        // contsurplus		
        this.contsurplus.setBoundLabelText(resHelper.getString("contsurplus.boundLabelText"));		
        this.contsurplus.setBoundLabelLength(100);		
        this.contsurplus.setBoundLabelUnderline(true);		
        this.contsurplus.setVisible(true);
        // contleaseMent		
        this.contleaseMent.setBoundLabelText(resHelper.getString("contleaseMent.boundLabelText"));		
        this.contleaseMent.setBoundLabelLength(100);		
        this.contleaseMent.setBoundLabelUnderline(true);		
        this.contleaseMent.setVisible(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"equNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"shuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"use\" t:width=\"240\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{equNumber}</t:Cell><t:Cell>$Resource{equName}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{shuliang}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{use}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtE1.putBindContents("editData",new String[] {"seq","equNumber","equName","model","unit","shuliang","amount","use"});


        this.kdtE1.checkParsed();
        KDFormattedTextField kdtE1_seq_TextField = new KDFormattedTextField();
        kdtE1_seq_TextField.setName("kdtE1_seq_TextField");
        kdtE1_seq_TextField.setVisible(true);
        kdtE1_seq_TextField.setEditable(true);
        kdtE1_seq_TextField.setHorizontalAlignment(2);
        kdtE1_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_seq_CellEditor = new KDTDefaultCellEditor(kdtE1_seq_TextField);
        this.kdtE1.getColumn("seq").setEditor(kdtE1_seq_CellEditor);
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
        KDTextField kdtE1_model_TextField = new KDTextField();
        kdtE1_model_TextField.setName("kdtE1_model_TextField");
        kdtE1_model_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_model_CellEditor = new KDTDefaultCellEditor(kdtE1_model_TextField);
        this.kdtE1.getColumn("model").setEditor(kdtE1_model_CellEditor);
        KDTextField kdtE1_unit_TextField = new KDTextField();
        kdtE1_unit_TextField.setName("kdtE1_unit_TextField");
        kdtE1_unit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_unit_CellEditor = new KDTDefaultCellEditor(kdtE1_unit_TextField);
        this.kdtE1.getColumn("unit").setEditor(kdtE1_unit_CellEditor);
        KDFormattedTextField kdtE1_shuliang_TextField = new KDFormattedTextField();
        kdtE1_shuliang_TextField.setName("kdtE1_shuliang_TextField");
        kdtE1_shuliang_TextField.setVisible(true);
        kdtE1_shuliang_TextField.setEditable(true);
        kdtE1_shuliang_TextField.setHorizontalAlignment(2);
        kdtE1_shuliang_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_shuliang_CellEditor = new KDTDefaultCellEditor(kdtE1_shuliang_TextField);
        this.kdtE1.getColumn("shuliang").setEditor(kdtE1_shuliang_CellEditor);
        KDFormattedTextField kdtE1_amount_TextField = new KDFormattedTextField();
        kdtE1_amount_TextField.setName("kdtE1_amount_TextField");
        kdtE1_amount_TextField.setVisible(true);
        kdtE1_amount_TextField.setEditable(true);
        kdtE1_amount_TextField.setHorizontalAlignment(2);
        kdtE1_amount_TextField.setDataType(1);
        	kdtE1_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_amount_CellEditor = new KDTDefaultCellEditor(kdtE1_amount_TextField);
        this.kdtE1.getColumn("amount").setEditor(kdtE1_amount_CellEditor);
        KDTextArea kdtE1_use_TextArea = new KDTextArea();
        kdtE1_use_TextArea.setName("kdtE1_use_TextArea");
        kdtE1_use_TextArea.setMaxLength(1000);
        KDTDefaultCellEditor kdtE1_use_CellEditor = new KDTDefaultCellEditor(kdtE1_use_TextArea);
        this.kdtE1.getColumn("use").setEditor(kdtE1_use_CellEditor);
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
        // txtconNo		
        this.txtconNo.setVisible(true);		
        this.txtconNo.setHorizontalAlignment(2);		
        this.txtconNo.setMaxLength(100);		
        this.txtconNo.setRequired(false);
        // txttheLessor		
        this.txttheLessor.setVisible(true);		
        this.txttheLessor.setHorizontalAlignment(2);		
        this.txttheLessor.setMaxLength(100);		
        this.txttheLessor.setRequired(false);
        // txttheLessee		
        this.txttheLessee.setVisible(true);		
        this.txttheLessee.setHorizontalAlignment(2);		
        this.txttheLessee.setMaxLength(100);		
        this.txttheLessee.setRequired(false);
        // pksigningDate		
        this.pksigningDate.setVisible(true);		
        this.pksigningDate.setRequired(false);
        // txtleaseSales		
        this.txtleaseSales.setVisible(true);		
        this.txtleaseSales.setHorizontalAlignment(2);		
        this.txtleaseSales.setDataType(1);		
        this.txtleaseSales.setSupportedEmpty(true);		
        this.txtleaseSales.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtleaseSales.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtleaseSales.setPrecision(2);		
        this.txtleaseSales.setRequired(false);
        // pkleaseStart		
        this.pkleaseStart.setVisible(true);		
        this.pkleaseStart.setRequired(false);
        // pkleaseEnd		
        this.pkleaseEnd.setVisible(true);		
        this.pkleaseEnd.setRequired(false);
        // rentPay		
        this.rentPay.setVisible(true);		
        this.rentPay.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.RentPay").toArray());		
        this.rentPay.setRequired(false);
        // txthaveRented		
        this.txthaveRented.setVisible(true);		
        this.txthaveRented.setHorizontalAlignment(2);		
        this.txthaveRented.setDataType(0);		
        this.txthaveRented.setSupportedEmpty(true);		
        this.txthaveRented.setRequired(false);
        // txtsurplus		
        this.txtsurplus.setVisible(true);		
        this.txtsurplus.setHorizontalAlignment(2);		
        this.txtsurplus.setDataType(0);		
        this.txtsurplus.setSupportedEmpty(true);		
        this.txtsurplus.setRequired(false);
        // leaseMent		
        this.leaseMent.setVisible(true);		
        this.leaseMent.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.LeaseMent").toArray());		
        this.leaseMent.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtconNo,txttheLessor,txttheLessee,pksigningDate,txtleaseSales,pkleaseStart,pkleaseEnd,rentPay,txthaveRented,txtsurplus,leaseMent}));
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
        this.add(contCU, new KDLayout.Constraints(706, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(973, 272, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(973, 272, 270, 19, 0));
        contDescription.setBounds(new Rectangle(983, 298, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(983, 298, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(369, 127, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(369, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contconNo.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(contconNo, new KDLayout.Constraints(369, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttheLessor.setBounds(new Rectangle(33, 40, 270, 19));
        this.add(conttheLessor, new KDLayout.Constraints(33, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttheLessee.setBounds(new Rectangle(369, 40, 270, 19));
        this.add(conttheLessee, new KDLayout.Constraints(369, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsigningDate.setBounds(new Rectangle(706, 40, 270, 19));
        this.add(contsigningDate, new KDLayout.Constraints(706, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contleaseSales.setBounds(new Rectangle(33, 69, 270, 19));
        this.add(contleaseSales, new KDLayout.Constraints(33, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contleaseStart.setBounds(new Rectangle(369, 69, 270, 19));
        this.add(contleaseStart, new KDLayout.Constraints(369, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contleaseEnd.setBounds(new Rectangle(706, 69, 270, 19));
        this.add(contleaseEnd, new KDLayout.Constraints(706, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrentPay.setBounds(new Rectangle(33, 98, 270, 19));
        this.add(contrentPay, new KDLayout.Constraints(33, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conthaveRented.setBounds(new Rectangle(369, 98, 270, 19));
        this.add(conthaveRented, new KDLayout.Constraints(369, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsurplus.setBounds(new Rectangle(706, 98, 270, 19));
        this.add(contsurplus, new KDLayout.Constraints(706, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contleaseMent.setBounds(new Rectangle(33, 127, 270, 19));
        this.add(contleaseMent, new KDLayout.Constraints(33, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtE1.setBounds(new Rectangle(33, 172, 943, 373));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.operate.EquLeaseE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(33, 172, 943, 373, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contconNo
        contconNo.setBoundEditor(txtconNo);
        //conttheLessor
        conttheLessor.setBoundEditor(txttheLessor);
        //conttheLessee
        conttheLessee.setBoundEditor(txttheLessee);
        //contsigningDate
        contsigningDate.setBoundEditor(pksigningDate);
        //contleaseSales
        contleaseSales.setBoundEditor(txtleaseSales);
        //contleaseStart
        contleaseStart.setBoundEditor(pkleaseStart);
        //contleaseEnd
        contleaseEnd.setBoundEditor(pkleaseEnd);
        //contrentPay
        contrentPay.setBoundEditor(rentPay);
        //conthaveRented
        conthaveRented.setBoundEditor(txthaveRented);
        //contsurplus
        contsurplus.setBoundEditor(txtsurplus);
        //contleaseMent
        contleaseMent.setBoundEditor(leaseMent);

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
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.operate.EquLeaseE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.equNumber", java.lang.Object.class, this.kdtE1, "equNumber.text");
		dataBinder.registerBinding("E1.equName", String.class, this.kdtE1, "equName.text");
		dataBinder.registerBinding("E1.model", String.class, this.kdtE1, "model.text");
		dataBinder.registerBinding("E1.unit", String.class, this.kdtE1, "unit.text");
		dataBinder.registerBinding("E1.shuliang", int.class, this.kdtE1, "shuliang.text");
		dataBinder.registerBinding("E1.amount", java.math.BigDecimal.class, this.kdtE1, "amount.text");
		dataBinder.registerBinding("E1.use", String.class, this.kdtE1, "use.text");
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
		dataBinder.registerBinding("conNo", String.class, this.txtconNo, "text");
		dataBinder.registerBinding("theLessor", String.class, this.txttheLessor, "text");
		dataBinder.registerBinding("theLessee", String.class, this.txttheLessee, "text");
		dataBinder.registerBinding("signingDate", java.util.Date.class, this.pksigningDate, "value");
		dataBinder.registerBinding("leaseSales", java.math.BigDecimal.class, this.txtleaseSales, "value");
		dataBinder.registerBinding("leaseStart", java.util.Date.class, this.pkleaseStart, "value");
		dataBinder.registerBinding("leaseEnd", java.util.Date.class, this.pkleaseEnd, "value");
		dataBinder.registerBinding("rentPay", com.kingdee.eas.port.equipment.base.enumbase.RentPay.class, this.rentPay, "selectedItem");
		dataBinder.registerBinding("haveRented", int.class, this.txthaveRented, "value");
		dataBinder.registerBinding("surplus", int.class, this.txtsurplus, "value");
		dataBinder.registerBinding("leaseMent", com.kingdee.eas.port.equipment.base.enumbase.LeaseMent.class, this.leaseMent, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.operate.app.EquLeaseEditUIHandler";
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
        this.txtconNo.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.operate.EquLeaseInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.equNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.shuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.use", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("conNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("theLessor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("theLessee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signingDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseSales", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseStart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseEnd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rentPay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("haveRented", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("surplus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseMent", ValidateHelper.ON_SAVE);    		
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
     * output kdtE1_tableClicked method
     */
    protected void kdtE1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }


    /**
     * output kdtE1_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE1_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"name")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"model").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"model")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"unit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"unit.name")));

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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.equNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.equNumber.id"));
			sic.add(new SelectorItemInfo("E1.equNumber.number"));
			sic.add(new SelectorItemInfo("E1.equNumber.name"));
		}
    	sic.add(new SelectorItemInfo("E1.equName"));
    	sic.add(new SelectorItemInfo("E1.model"));
    	sic.add(new SelectorItemInfo("E1.unit"));
    	sic.add(new SelectorItemInfo("E1.shuliang"));
    	sic.add(new SelectorItemInfo("E1.amount"));
    	sic.add(new SelectorItemInfo("E1.use"));
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
        sic.add(new SelectorItemInfo("conNo"));
        sic.add(new SelectorItemInfo("theLessor"));
        sic.add(new SelectorItemInfo("theLessee"));
        sic.add(new SelectorItemInfo("signingDate"));
        sic.add(new SelectorItemInfo("leaseSales"));
        sic.add(new SelectorItemInfo("leaseStart"));
        sic.add(new SelectorItemInfo("leaseEnd"));
        sic.add(new SelectorItemInfo("rentPay"));
        sic.add(new SelectorItemInfo("haveRented"));
        sic.add(new SelectorItemInfo("surplus"));
        sic.add(new SelectorItemInfo("leaseMent"));
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.operate.client", "EquLeaseEditUI");
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
        return com.kingdee.eas.port.equipment.operate.client.EquLeaseEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.operate.EquLeaseFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.operate.EquLeaseInfo objectValue = new com.kingdee.eas.port.equipment.operate.EquLeaseInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/operate/EquLease";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.operate.app.EquLeaseQuery");
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
		vo.put("rentPay","0");
vo.put("leaseMent","0");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}