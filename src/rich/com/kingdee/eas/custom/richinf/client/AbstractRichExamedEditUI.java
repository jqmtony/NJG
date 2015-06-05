/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

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
public abstract class AbstractRichExamedEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRichExamedEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contldNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contywNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqyUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfkUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfpNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsales;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttjType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbizState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjCompany;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDjrentry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtDjrentry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcardNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcardType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcardAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsaleAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkhc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzjjg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyhxAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtldNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdjDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtywNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtqyUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdjUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtkpUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtfkUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfpNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtamount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsales;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttjType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox bizState;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanebeizhu;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtbeizhu;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtkpCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdjCompany;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcardNo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cardType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcardAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtsaleAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtzjjg;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtyhxAmount;
    protected com.kingdee.eas.custom.richinf.RichExamedInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRichExamedEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRichExamedEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contldNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contywNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqyUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfkUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfpNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsales = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttjType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbizState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbeizhu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkdj = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtDjrentry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contcardNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcardType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcardAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsaleAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkhc = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contzjjg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyhxAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtldNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkdjDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtywNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtqyUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtdjUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtkpUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtfkUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtfpNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtsales = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmttjType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.scrollPanebeizhu = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtbeizhu = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtkpCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtdjCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtcardNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cardType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtcardAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtsaleAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtzjjg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtyhxAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contldNumber.setName("contldNumber");
        this.contdjDate.setName("contdjDate");
        this.contywNumber.setName("contywNumber");
        this.contqyUnit.setName("contqyUnit");
        this.contdjUnit.setName("contdjUnit");
        this.contkpUnit.setName("contkpUnit");
        this.contfkUnit.setName("contfkUnit");
        this.contfpNumber.setName("contfpNumber");
        this.contamount.setName("contamount");
        this.contsales.setName("contsales");
        this.conttjType.setName("conttjType");
        this.contbizState.setName("contbizState");
        this.contbeizhu.setName("contbeizhu");
        this.contkpCompany.setName("contkpCompany");
        this.contdjCompany.setName("contdjCompany");
        this.chkdj.setName("chkdj");
        this.contauditDate.setName("contauditDate");
        this.kdtDjrentry.setName("kdtDjrentry");
        this.kDSeparator8.setName("kDSeparator8");
        this.contcardNo.setName("contcardNo");
        this.contcardType.setName("contcardType");
        this.contcardAmount.setName("contcardAmount");
        this.contsaleAmount.setName("contsaleAmount");
        this.chkhc.setName("chkhc");
        this.contzjjg.setName("contzjjg");
        this.contyhxAmount.setName("contyhxAmount");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtldNumber.setName("txtldNumber");
        this.pkdjDate.setName("pkdjDate");
        this.txtywNumber.setName("txtywNumber");
        this.prmtqyUnit.setName("prmtqyUnit");
        this.prmtdjUnit.setName("prmtdjUnit");
        this.prmtkpUnit.setName("prmtkpUnit");
        this.prmtfkUnit.setName("prmtfkUnit");
        this.txtfpNumber.setName("txtfpNumber");
        this.txtamount.setName("txtamount");
        this.prmtsales.setName("prmtsales");
        this.prmttjType.setName("prmttjType");
        this.bizState.setName("bizState");
        this.scrollPanebeizhu.setName("scrollPanebeizhu");
        this.txtbeizhu.setName("txtbeizhu");
        this.prmtkpCompany.setName("prmtkpCompany");
        this.prmtdjCompany.setName("prmtdjCompany");
        this.pkauditDate.setName("pkauditDate");
        this.txtcardNo.setName("txtcardNo");
        this.cardType.setName("cardType");
        this.txtcardAmount.setName("txtcardAmount");
        this.txtsaleAmount.setName("txtsaleAmount");
        this.prmtzjjg.setName("prmtzjjg");
        this.txtyhxAmount.setName("txtyhxAmount");
        // CoreUI		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);		
        this.menuItemCopyLine.setVisible(false);
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
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"ywdjbh\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djtcNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"slType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"skType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jsAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{ywdjbh}</t:Cell><t:Cell>$Resource{djr}</t:Cell><t:Cell>$Resource{djtcNumber}</t:Cell><t:Cell>$Resource{djctName}</t:Cell><t:Cell>$Resource{slType}</t:Cell><t:Cell>$Resource{skType}</t:Cell><t:Cell>$Resource{jsAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","ywdjbh","djr","djtcNumber","djctName","slType","skType","jsAmount","beizhu"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_ywdjbh_TextField = new KDTextField();
        kdtEntrys_ywdjbh_TextField.setName("kdtEntrys_ywdjbh_TextField");
        kdtEntrys_ywdjbh_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_ywdjbh_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ywdjbh_TextField);
        this.kdtEntrys.getColumn("ywdjbh").setEditor(kdtEntrys_ywdjbh_CellEditor);
        KDTextField kdtEntrys_djr_TextField = new KDTextField();
        kdtEntrys_djr_TextField.setName("kdtEntrys_djr_TextField");
        kdtEntrys_djr_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_djr_CellEditor = new KDTDefaultCellEditor(kdtEntrys_djr_TextField);
        this.kdtEntrys.getColumn("djr").setEditor(kdtEntrys_djr_CellEditor);
        KDTextField kdtEntrys_djtcNumber_TextField = new KDTextField();
        kdtEntrys_djtcNumber_TextField.setName("kdtEntrys_djtcNumber_TextField");
        kdtEntrys_djtcNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_djtcNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_djtcNumber_TextField);
        this.kdtEntrys.getColumn("djtcNumber").setEditor(kdtEntrys_djtcNumber_CellEditor);
        KDTextField kdtEntrys_djctName_TextField = new KDTextField();
        kdtEntrys_djctName_TextField.setName("kdtEntrys_djctName_TextField");
        kdtEntrys_djctName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_djctName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_djctName_TextField);
        this.kdtEntrys.getColumn("djctName").setEditor(kdtEntrys_djctName_CellEditor);
        final KDBizPromptBox kdtEntrys_slType_PromptBox = new KDBizPromptBox();
        kdtEntrys_slType_PromptBox.setQueryInfo("com.kingdee.eas.custom.richbase.app.SaleTypeQuery");
        kdtEntrys_slType_PromptBox.setVisible(true);
        kdtEntrys_slType_PromptBox.setEditable(true);
        kdtEntrys_slType_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_slType_PromptBox.setEditFormat("$number$");
        kdtEntrys_slType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_slType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_slType_PromptBox);
        this.kdtEntrys.getColumn("slType").setEditor(kdtEntrys_slType_CellEditor);
        ObjectValueRender kdtEntrys_slType_OVR = new ObjectValueRender();
        kdtEntrys_slType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("slType").setRenderer(kdtEntrys_slType_OVR);
        final KDBizPromptBox kdtEntrys_skType_PromptBox = new KDBizPromptBox();
        kdtEntrys_skType_PromptBox.setQueryInfo("com.kingdee.eas.custom.richbase.app.ReceTypeQuery");
        kdtEntrys_skType_PromptBox.setVisible(true);
        kdtEntrys_skType_PromptBox.setEditable(true);
        kdtEntrys_skType_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_skType_PromptBox.setEditFormat("$number$");
        kdtEntrys_skType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_skType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_skType_PromptBox);
        this.kdtEntrys.getColumn("skType").setEditor(kdtEntrys_skType_CellEditor);
        ObjectValueRender kdtEntrys_skType_OVR = new ObjectValueRender();
        kdtEntrys_skType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("skType").setRenderer(kdtEntrys_skType_OVR);
        KDFormattedTextField kdtEntrys_jsAmount_TextField = new KDFormattedTextField();
        kdtEntrys_jsAmount_TextField.setName("kdtEntrys_jsAmount_TextField");
        kdtEntrys_jsAmount_TextField.setVisible(true);
        kdtEntrys_jsAmount_TextField.setEditable(true);
        kdtEntrys_jsAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_jsAmount_TextField.setDataType(1);
        	kdtEntrys_jsAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_jsAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_jsAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_jsAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_jsAmount_TextField);
        this.kdtEntrys.getColumn("jsAmount").setEditor(kdtEntrys_jsAmount_CellEditor);
        KDTextArea kdtEntrys_beizhu_TextArea = new KDTextArea();
        kdtEntrys_beizhu_TextArea.setName("kdtEntrys_beizhu_TextArea");
        kdtEntrys_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntrys_beizhu_TextArea);
        this.kdtEntrys.getColumn("beizhu").setEditor(kdtEntrys_beizhu_CellEditor);
        // contldNumber		
        this.contldNumber.setBoundLabelText(resHelper.getString("contldNumber.boundLabelText"));		
        this.contldNumber.setBoundLabelLength(100);		
        this.contldNumber.setBoundLabelUnderline(true);		
        this.contldNumber.setVisible(true);
        // contdjDate		
        this.contdjDate.setBoundLabelText(resHelper.getString("contdjDate.boundLabelText"));		
        this.contdjDate.setBoundLabelLength(100);		
        this.contdjDate.setBoundLabelUnderline(true);		
        this.contdjDate.setVisible(true);
        // contywNumber		
        this.contywNumber.setBoundLabelText(resHelper.getString("contywNumber.boundLabelText"));		
        this.contywNumber.setBoundLabelLength(100);		
        this.contywNumber.setBoundLabelUnderline(true);		
        this.contywNumber.setVisible(false);
        // contqyUnit		
        this.contqyUnit.setBoundLabelText(resHelper.getString("contqyUnit.boundLabelText"));		
        this.contqyUnit.setBoundLabelLength(100);		
        this.contqyUnit.setBoundLabelUnderline(true);		
        this.contqyUnit.setVisible(true);
        // contdjUnit		
        this.contdjUnit.setBoundLabelText(resHelper.getString("contdjUnit.boundLabelText"));		
        this.contdjUnit.setBoundLabelLength(100);		
        this.contdjUnit.setBoundLabelUnderline(true);		
        this.contdjUnit.setVisible(true);
        // contkpUnit		
        this.contkpUnit.setBoundLabelText(resHelper.getString("contkpUnit.boundLabelText"));		
        this.contkpUnit.setBoundLabelLength(100);		
        this.contkpUnit.setBoundLabelUnderline(true);		
        this.contkpUnit.setVisible(true);
        // contfkUnit		
        this.contfkUnit.setBoundLabelText(resHelper.getString("contfkUnit.boundLabelText"));		
        this.contfkUnit.setBoundLabelLength(100);		
        this.contfkUnit.setBoundLabelUnderline(true);		
        this.contfkUnit.setVisible(true);
        // contfpNumber		
        this.contfpNumber.setBoundLabelText(resHelper.getString("contfpNumber.boundLabelText"));		
        this.contfpNumber.setBoundLabelLength(100);		
        this.contfpNumber.setBoundLabelUnderline(true);		
        this.contfpNumber.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // contsales		
        this.contsales.setBoundLabelText(resHelper.getString("contsales.boundLabelText"));		
        this.contsales.setBoundLabelLength(100);		
        this.contsales.setBoundLabelUnderline(true);		
        this.contsales.setVisible(true);
        // conttjType		
        this.conttjType.setBoundLabelText(resHelper.getString("conttjType.boundLabelText"));		
        this.conttjType.setBoundLabelLength(100);		
        this.conttjType.setBoundLabelUnderline(true);		
        this.conttjType.setVisible(true);
        // contbizState		
        this.contbizState.setBoundLabelText(resHelper.getString("contbizState.boundLabelText"));		
        this.contbizState.setBoundLabelLength(100);		
        this.contbizState.setBoundLabelUnderline(true);		
        this.contbizState.setVisible(true);
        // contbeizhu		
        this.contbeizhu.setBoundLabelText(resHelper.getString("contbeizhu.boundLabelText"));		
        this.contbeizhu.setBoundLabelLength(100);		
        this.contbeizhu.setBoundLabelUnderline(true);		
        this.contbeizhu.setVisible(true);
        // contkpCompany		
        this.contkpCompany.setBoundLabelText(resHelper.getString("contkpCompany.boundLabelText"));		
        this.contkpCompany.setBoundLabelLength(100);		
        this.contkpCompany.setBoundLabelUnderline(true);		
        this.contkpCompany.setVisible(true);
        // contdjCompany		
        this.contdjCompany.setBoundLabelText(resHelper.getString("contdjCompany.boundLabelText"));		
        this.contdjCompany.setBoundLabelLength(100);		
        this.contdjCompany.setBoundLabelUnderline(true);		
        this.contdjCompany.setVisible(true);
        // chkdj		
        this.chkdj.setText(resHelper.getString("chkdj.text"));		
        this.chkdj.setHorizontalAlignment(2);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // kdtDjrentry
		String kdtDjrentryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"djxmbm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djxmmc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jxbs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"klj\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"zkl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"jsje\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"se\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"jshj\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ywdjbh\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{djxmbm}</t:Cell><t:Cell>$Resource{djxmmc}</t:Cell><t:Cell>$Resource{jxbs}</t:Cell><t:Cell>$Resource{klj}</t:Cell><t:Cell>$Resource{zkl}</t:Cell><t:Cell>$Resource{jsje}</t:Cell><t:Cell>$Resource{se}</t:Cell><t:Cell>$Resource{jshj}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell><t:Cell>$Resource{ywdjbh}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDjrentry.setFormatXml(resHelper.translateString("kdtDjrentry",kdtDjrentryStrXML));

                this.kdtDjrentry.putBindContents("editData",new String[] {"Djrentry.seq","Djrentry.djxmbm","Djrentry.djxmmc","Djrentry.jxbs","Djrentry.klj","Djrentry.zkl","Djrentry.jsje","Djrentry.se","Djrentry.jshj","Djrentry.beizhu","Djrentry.ywdjbh"});


        this.kdtDjrentry.checkParsed();
        KDFormattedTextField kdtDjrentry_seq_TextField = new KDFormattedTextField();
        kdtDjrentry_seq_TextField.setName("kdtDjrentry_seq_TextField");
        kdtDjrentry_seq_TextField.setVisible(true);
        kdtDjrentry_seq_TextField.setEditable(true);
        kdtDjrentry_seq_TextField.setHorizontalAlignment(2);
        kdtDjrentry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtDjrentry_seq_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_seq_TextField);
        this.kdtDjrentry.getColumn("seq").setEditor(kdtDjrentry_seq_CellEditor);
        KDTextField kdtDjrentry_djxmbm_TextField = new KDTextField();
        kdtDjrentry_djxmbm_TextField.setName("kdtDjrentry_djxmbm_TextField");
        kdtDjrentry_djxmbm_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDjrentry_djxmbm_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_djxmbm_TextField);
        this.kdtDjrentry.getColumn("djxmbm").setEditor(kdtDjrentry_djxmbm_CellEditor);
        KDTextField kdtDjrentry_djxmmc_TextField = new KDTextField();
        kdtDjrentry_djxmmc_TextField.setName("kdtDjrentry_djxmmc_TextField");
        kdtDjrentry_djxmmc_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDjrentry_djxmmc_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_djxmmc_TextField);
        this.kdtDjrentry.getColumn("djxmmc").setEditor(kdtDjrentry_djxmmc_CellEditor);
        KDCheckBox kdtDjrentry_jxbs_CheckBox = new KDCheckBox();
        kdtDjrentry_jxbs_CheckBox.setName("kdtDjrentry_jxbs_CheckBox");
        KDTDefaultCellEditor kdtDjrentry_jxbs_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_jxbs_CheckBox);
        this.kdtDjrentry.getColumn("jxbs").setEditor(kdtDjrentry_jxbs_CellEditor);
        KDFormattedTextField kdtDjrentry_klj_TextField = new KDFormattedTextField();
        kdtDjrentry_klj_TextField.setName("kdtDjrentry_klj_TextField");
        kdtDjrentry_klj_TextField.setVisible(true);
        kdtDjrentry_klj_TextField.setEditable(true);
        kdtDjrentry_klj_TextField.setHorizontalAlignment(2);
        kdtDjrentry_klj_TextField.setDataType(1);
        	kdtDjrentry_klj_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtDjrentry_klj_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtDjrentry_klj_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtDjrentry_klj_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_klj_TextField);
        this.kdtDjrentry.getColumn("klj").setEditor(kdtDjrentry_klj_CellEditor);
        KDFormattedTextField kdtDjrentry_zkl_TextField = new KDFormattedTextField();
        kdtDjrentry_zkl_TextField.setName("kdtDjrentry_zkl_TextField");
        kdtDjrentry_zkl_TextField.setVisible(true);
        kdtDjrentry_zkl_TextField.setEditable(true);
        kdtDjrentry_zkl_TextField.setHorizontalAlignment(2);
        kdtDjrentry_zkl_TextField.setDataType(1);
        	kdtDjrentry_zkl_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtDjrentry_zkl_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtDjrentry_zkl_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtDjrentry_zkl_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_zkl_TextField);
        this.kdtDjrentry.getColumn("zkl").setEditor(kdtDjrentry_zkl_CellEditor);
        KDFormattedTextField kdtDjrentry_jsje_TextField = new KDFormattedTextField();
        kdtDjrentry_jsje_TextField.setName("kdtDjrentry_jsje_TextField");
        kdtDjrentry_jsje_TextField.setVisible(true);
        kdtDjrentry_jsje_TextField.setEditable(true);
        kdtDjrentry_jsje_TextField.setHorizontalAlignment(2);
        kdtDjrentry_jsje_TextField.setDataType(1);
        	kdtDjrentry_jsje_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtDjrentry_jsje_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtDjrentry_jsje_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtDjrentry_jsje_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_jsje_TextField);
        this.kdtDjrentry.getColumn("jsje").setEditor(kdtDjrentry_jsje_CellEditor);
        KDFormattedTextField kdtDjrentry_se_TextField = new KDFormattedTextField();
        kdtDjrentry_se_TextField.setName("kdtDjrentry_se_TextField");
        kdtDjrentry_se_TextField.setVisible(true);
        kdtDjrentry_se_TextField.setEditable(true);
        kdtDjrentry_se_TextField.setHorizontalAlignment(2);
        kdtDjrentry_se_TextField.setDataType(1);
        	kdtDjrentry_se_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtDjrentry_se_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtDjrentry_se_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtDjrentry_se_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_se_TextField);
        this.kdtDjrentry.getColumn("se").setEditor(kdtDjrentry_se_CellEditor);
        KDFormattedTextField kdtDjrentry_jshj_TextField = new KDFormattedTextField();
        kdtDjrentry_jshj_TextField.setName("kdtDjrentry_jshj_TextField");
        kdtDjrentry_jshj_TextField.setVisible(true);
        kdtDjrentry_jshj_TextField.setEditable(true);
        kdtDjrentry_jshj_TextField.setHorizontalAlignment(2);
        kdtDjrentry_jshj_TextField.setDataType(1);
        	kdtDjrentry_jshj_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtDjrentry_jshj_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtDjrentry_jshj_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtDjrentry_jshj_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_jshj_TextField);
        this.kdtDjrentry.getColumn("jshj").setEditor(kdtDjrentry_jshj_CellEditor);
        KDTextField kdtDjrentry_beizhu_TextField = new KDTextField();
        kdtDjrentry_beizhu_TextField.setName("kdtDjrentry_beizhu_TextField");
        kdtDjrentry_beizhu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDjrentry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_beizhu_TextField);
        this.kdtDjrentry.getColumn("beizhu").setEditor(kdtDjrentry_beizhu_CellEditor);
        KDTextField kdtDjrentry_ywdjbh_TextField = new KDTextField();
        kdtDjrentry_ywdjbh_TextField.setName("kdtDjrentry_ywdjbh_TextField");
        kdtDjrentry_ywdjbh_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDjrentry_ywdjbh_CellEditor = new KDTDefaultCellEditor(kdtDjrentry_ywdjbh_TextField);
        this.kdtDjrentry.getColumn("ywdjbh").setEditor(kdtDjrentry_ywdjbh_CellEditor);
        // kDSeparator8
        // contcardNo		
        this.contcardNo.setBoundLabelText(resHelper.getString("contcardNo.boundLabelText"));		
        this.contcardNo.setBoundLabelLength(100);		
        this.contcardNo.setBoundLabelUnderline(true);		
        this.contcardNo.setVisible(true);
        // contcardType		
        this.contcardType.setBoundLabelText(resHelper.getString("contcardType.boundLabelText"));		
        this.contcardType.setBoundLabelLength(100);		
        this.contcardType.setBoundLabelUnderline(true);		
        this.contcardType.setVisible(true);
        // contcardAmount		
        this.contcardAmount.setBoundLabelText(resHelper.getString("contcardAmount.boundLabelText"));		
        this.contcardAmount.setBoundLabelLength(100);		
        this.contcardAmount.setBoundLabelUnderline(true);		
        this.contcardAmount.setVisible(true);
        // contsaleAmount		
        this.contsaleAmount.setBoundLabelText(resHelper.getString("contsaleAmount.boundLabelText"));		
        this.contsaleAmount.setBoundLabelLength(100);		
        this.contsaleAmount.setBoundLabelUnderline(true);		
        this.contsaleAmount.setVisible(true);
        // chkhc		
        this.chkhc.setText(resHelper.getString("chkhc.text"));		
        this.chkhc.setHorizontalAlignment(2);
        // contzjjg		
        this.contzjjg.setBoundLabelText(resHelper.getString("contzjjg.boundLabelText"));		
        this.contzjjg.setBoundLabelLength(100);		
        this.contzjjg.setBoundLabelUnderline(true);		
        this.contzjjg.setVisible(true);
        // contyhxAmount		
        this.contyhxAmount.setBoundLabelText(resHelper.getString("contyhxAmount.boundLabelText"));		
        this.contyhxAmount.setBoundLabelLength(100);		
        this.contyhxAmount.setBoundLabelUnderline(true);		
        this.contyhxAmount.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtldNumber		
        this.txtldNumber.setHorizontalAlignment(2);		
        this.txtldNumber.setMaxLength(100);		
        this.txtldNumber.setRequired(false);
        // pkdjDate		
        this.pkdjDate.setRequired(false);
        // txtywNumber		
        this.txtywNumber.setHorizontalAlignment(2);		
        this.txtywNumber.setMaxLength(100);		
        this.txtywNumber.setRequired(false);
        // prmtqyUnit		
        this.prmtqyUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtqyUnit.setEditable(true);		
        this.prmtqyUnit.setDisplayFormat("$name$");		
        this.prmtqyUnit.setEditFormat("$number$");		
        this.prmtqyUnit.setCommitFormat("$number$");		
        this.prmtqyUnit.setRequired(false);
        // prmtdjUnit		
        this.prmtdjUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtdjUnit.setEditable(true);		
        this.prmtdjUnit.setDisplayFormat("$name$");		
        this.prmtdjUnit.setEditFormat("$number$");		
        this.prmtdjUnit.setCommitFormat("$number$");		
        this.prmtdjUnit.setRequired(false);
        // prmtkpUnit		
        this.prmtkpUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtkpUnit.setEditable(true);		
        this.prmtkpUnit.setDisplayFormat("$name$");		
        this.prmtkpUnit.setEditFormat("$number$");		
        this.prmtkpUnit.setCommitFormat("$number$");		
        this.prmtkpUnit.setRequired(false);
        // prmtfkUnit		
        this.prmtfkUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtfkUnit.setEditable(true);		
        this.prmtfkUnit.setDisplayFormat("$name$");		
        this.prmtfkUnit.setEditFormat("$number$");		
        this.prmtfkUnit.setCommitFormat("$number$");		
        this.prmtfkUnit.setRequired(false);
        // txtfpNumber		
        this.txtfpNumber.setHorizontalAlignment(2);		
        this.txtfpNumber.setMaxLength(100);		
        this.txtfpNumber.setRequired(false);
        // txtamount		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setMaxLength(100);		
        this.txtamount.setRequired(false);
        // prmtsales		
        this.prmtsales.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtsales.setEditable(true);		
        this.prmtsales.setDisplayFormat("$name$");		
        this.prmtsales.setEditFormat("$number$");		
        this.prmtsales.setCommitFormat("$number$");		
        this.prmtsales.setRequired(false);
        // prmttjType		
        this.prmttjType.setQueryInfo("com.kingdee.eas.custom.richbase.app.ExamTypeQuery");		
        this.prmttjType.setEditable(true);		
        this.prmttjType.setDisplayFormat("$name$");		
        this.prmttjType.setEditFormat("$number$");		
        this.prmttjType.setCommitFormat("$number$");		
        this.prmttjType.setRequired(false);
        // bizState		
        this.bizState.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.richbase.BizStateEnum").toArray());		
        this.bizState.setRequired(false);
        // scrollPanebeizhu
        // txtbeizhu		
        this.txtbeizhu.setRequired(false);		
        this.txtbeizhu.setMaxLength(255);
        // prmtkpCompany		
        this.prmtkpCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtkpCompany.setEditable(true);		
        this.prmtkpCompany.setDisplayFormat("$name$");		
        this.prmtkpCompany.setEditFormat("$number$");		
        this.prmtkpCompany.setCommitFormat("$number$");		
        this.prmtkpCompany.setRequired(false);
        // prmtdjCompany		
        this.prmtdjCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtdjCompany.setEditable(true);		
        this.prmtdjCompany.setDisplayFormat("$name$");		
        this.prmtdjCompany.setEditFormat("$number$");		
        this.prmtdjCompany.setCommitFormat("$number$");		
        this.prmtdjCompany.setRequired(false);
        // pkauditDate		
        this.pkauditDate.setRequired(false);
        // txtcardNo		
        this.txtcardNo.setHorizontalAlignment(2);		
        this.txtcardNo.setMaxLength(100);		
        this.txtcardNo.setRequired(false);
        // cardType		
        this.cardType.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.richbase.CardType").toArray());		
        this.cardType.setRequired(false);
        // txtcardAmount		
        this.txtcardAmount.setHorizontalAlignment(2);		
        this.txtcardAmount.setDataType(1);		
        this.txtcardAmount.setSupportedEmpty(true);		
        this.txtcardAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtcardAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtcardAmount.setPrecision(2);		
        this.txtcardAmount.setRequired(false);
        // txtsaleAmount		
        this.txtsaleAmount.setHorizontalAlignment(2);		
        this.txtsaleAmount.setDataType(1);		
        this.txtsaleAmount.setSupportedEmpty(true);		
        this.txtsaleAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtsaleAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtsaleAmount.setPrecision(2);		
        this.txtsaleAmount.setRequired(false);
        // prmtzjjg		
        this.prmtzjjg.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtzjjg.setEditable(true);		
        this.prmtzjjg.setDisplayFormat("$name$");		
        this.prmtzjjg.setEditFormat("$number$");		
        this.prmtzjjg.setCommitFormat("$number$");		
        this.prmtzjjg.setRequired(false);
        // txtyhxAmount		
        this.txtyhxAmount.setVisible(true);		
        this.txtyhxAmount.setHorizontalAlignment(2);		
        this.txtyhxAmount.setDataType(1);		
        this.txtyhxAmount.setSupportedEmpty(true);		
        this.txtyhxAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtyhxAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtyhxAmount.setPrecision(2);		
        this.txtyhxAmount.setRequired(false);		
        this.txtyhxAmount.setEnabled(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtDjrentry,chkhc,prmtzjjg,txtcardNo,cardType,txtcardAmount,txtsaleAmount,txtldNumber,pkdjDate,txtywNumber,prmtqyUnit,prmtdjUnit,prmtkpUnit,prmtfkUnit,txtfpNumber,txtamount,prmtsales,prmttjType,bizState,txtbeizhu,prmtkpCompany,prmtdjCompany,chkdj,pkauditDate,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,kDDateCreateTime,prmtLastUpdateUser,kDDateLastUpdateTime,kdtEntrys,txtyhxAmount}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(438, 581, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(728, 581, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(438, 603, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(728, 603, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(728, 27, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(350, 27, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(290, 603, 270, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(13, 581, 270, 19));
        this.add(contAuditor, null);
        kdtEntrys.setBounds(new Rectangle(8, 188, 992, 171));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.custom.richinf.RichExamedEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, null);
        contldNumber.setBounds(new Rectangle(15, 27, 270, 19));
        this.add(contldNumber, null);
        contdjDate.setBounds(new Rectangle(350, 49, 270, 19));
        this.add(contdjDate, null);
        contywNumber.setBounds(new Rectangle(625, 12, 101, 19));
        this.add(contywNumber, null);
        contqyUnit.setBounds(new Rectangle(15, 49, 270, 19));
        this.add(contqyUnit, null);
        contdjUnit.setBounds(new Rectangle(15, 71, 270, 19));
        this.add(contdjUnit, null);
        contkpUnit.setBounds(new Rectangle(350, 71, 270, 19));
        this.add(contkpUnit, null);
        contfkUnit.setBounds(new Rectangle(728, 71, 270, 19));
        this.add(contfkUnit, null);
        contfpNumber.setBounds(new Rectangle(15, 92, 270, 19));
        this.add(contfpNumber, null);
        contamount.setBounds(new Rectangle(350, 92, 270, 19));
        this.add(contamount, null);
        contsales.setBounds(new Rectangle(728, 92, 270, 19));
        this.add(contsales, null);
        conttjType.setBounds(new Rectangle(15, 114, 270, 19));
        this.add(conttjType, null);
        contbizState.setBounds(new Rectangle(350, 114, 270, 19));
        this.add(contbizState, null);
        contbeizhu.setBounds(new Rectangle(622, 138, 378, 43));
        this.add(contbeizhu, null);
        contkpCompany.setBounds(new Rectangle(350, 5, 270, 19));
        this.add(contkpCompany, null);
        contdjCompany.setBounds(new Rectangle(15, 5, 270, 19));
        this.add(contdjCompany, null);
        chkdj.setBounds(new Rectangle(728, 7, 72, 19));
        this.add(chkdj, null);
        contauditDate.setBounds(new Rectangle(13, 603, 270, 19));
        this.add(contauditDate, null);
        kdtDjrentry.setBounds(new Rectangle(6, 362, 994, 216));
        kdtDjrentry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtDjrentry,new com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryInfo(),null,false);
        this.add(kdtDjrentry_detailPanel, null);
        kDSeparator8.setBounds(new Rectangle(2, 135, 1008, 8));
        this.add(kDSeparator8, null);
        contcardNo.setBounds(new Rectangle(15, 141, 270, 19));
        this.add(contcardNo, null);
        contcardType.setBounds(new Rectangle(350, 141, 270, 19));
        this.add(contcardType, null);
        contcardAmount.setBounds(new Rectangle(15, 164, 270, 19));
        this.add(contcardAmount, null);
        contsaleAmount.setBounds(new Rectangle(350, 164, 270, 19));
        this.add(contsaleAmount, null);
        chkhc.setBounds(new Rectangle(828, 7, 107, 19));
        this.add(chkhc, null);
        contzjjg.setBounds(new Rectangle(728, 114, 270, 19));
        this.add(contzjjg, null);
        contyhxAmount.setBounds(new Rectangle(728, 49, 270, 19));
        this.add(contyhxAmount, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contldNumber
        contldNumber.setBoundEditor(txtldNumber);
        //contdjDate
        contdjDate.setBoundEditor(pkdjDate);
        //contywNumber
        contywNumber.setBoundEditor(txtywNumber);
        //contqyUnit
        contqyUnit.setBoundEditor(prmtqyUnit);
        //contdjUnit
        contdjUnit.setBoundEditor(prmtdjUnit);
        //contkpUnit
        contkpUnit.setBoundEditor(prmtkpUnit);
        //contfkUnit
        contfkUnit.setBoundEditor(prmtfkUnit);
        //contfpNumber
        contfpNumber.setBoundEditor(txtfpNumber);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contsales
        contsales.setBoundEditor(prmtsales);
        //conttjType
        conttjType.setBoundEditor(prmttjType);
        //contbizState
        contbizState.setBoundEditor(bizState);
        //contbeizhu
        contbeizhu.setBoundEditor(scrollPanebeizhu);
        //scrollPanebeizhu
        scrollPanebeizhu.getViewport().add(txtbeizhu, null);
        //contkpCompany
        contkpCompany.setBoundEditor(prmtkpCompany);
        //contdjCompany
        contdjCompany.setBoundEditor(prmtdjCompany);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contcardNo
        contcardNo.setBoundEditor(txtcardNo);
        //contcardType
        contcardType.setBoundEditor(cardType);
        //contcardAmount
        contcardAmount.setBoundEditor(txtcardAmount);
        //contsaleAmount
        contsaleAmount.setBoundEditor(txtsaleAmount);
        //contzjjg
        contzjjg.setBoundEditor(prmtzjjg);
        //contyhxAmount
        contyhxAmount.setBoundEditor(txtyhxAmount);

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
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.custom.richinf.RichExamedEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.djtcNumber", String.class, this.kdtEntrys, "djtcNumber.text");
		dataBinder.registerBinding("entrys.djctName", String.class, this.kdtEntrys, "djctName.text");
		dataBinder.registerBinding("entrys.slType", java.lang.Object.class, this.kdtEntrys, "slType.text");
		dataBinder.registerBinding("entrys.skType", java.lang.Object.class, this.kdtEntrys, "skType.text");
		dataBinder.registerBinding("entrys.beizhu", String.class, this.kdtEntrys, "beizhu.text");
		dataBinder.registerBinding("entrys.djr", String.class, this.kdtEntrys, "djr.text");
		dataBinder.registerBinding("entrys.jsAmount", java.math.BigDecimal.class, this.kdtEntrys, "jsAmount.text");
		dataBinder.registerBinding("entrys.ywdjbh", String.class, this.kdtEntrys, "ywdjbh.text");
		dataBinder.registerBinding("dj", boolean.class, this.chkdj, "selected");
		dataBinder.registerBinding("entrys.Djrentry.seq", int.class, this.kdtDjrentry, "seq.text");
		dataBinder.registerBinding("entrys.Djrentry", com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryInfo.class, this.kdtDjrentry, "userObject");
		dataBinder.registerBinding("entrys.Djrentry.djxmbm", String.class, this.kdtDjrentry, "djxmbm.text");
		dataBinder.registerBinding("entrys.Djrentry.djxmmc", String.class, this.kdtDjrentry, "djxmmc.text");
		dataBinder.registerBinding("entrys.Djrentry.jxbs", boolean.class, this.kdtDjrentry, "jxbs.text");
		dataBinder.registerBinding("entrys.Djrentry.klj", java.math.BigDecimal.class, this.kdtDjrentry, "klj.text");
		dataBinder.registerBinding("entrys.Djrentry.zkl", java.math.BigDecimal.class, this.kdtDjrentry, "zkl.text");
		dataBinder.registerBinding("entrys.Djrentry.jsje", java.math.BigDecimal.class, this.kdtDjrentry, "jsje.text");
		dataBinder.registerBinding("entrys.Djrentry.se", java.math.BigDecimal.class, this.kdtDjrentry, "se.text");
		dataBinder.registerBinding("entrys.Djrentry.jshj", java.math.BigDecimal.class, this.kdtDjrentry, "jshj.text");
		dataBinder.registerBinding("entrys.Djrentry.beizhu", String.class, this.kdtDjrentry, "beizhu.text");
		dataBinder.registerBinding("entrys.Djrentry.ywdjbh", String.class, this.kdtDjrentry, "ywdjbh.text");
		dataBinder.registerBinding("hc", boolean.class, this.chkhc, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("ldNumber", String.class, this.txtldNumber, "text");
		dataBinder.registerBinding("djDate", java.util.Date.class, this.pkdjDate, "value");
		dataBinder.registerBinding("ywNumber", String.class, this.txtywNumber, "text");
		dataBinder.registerBinding("qyUnit", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtqyUnit, "data");
		dataBinder.registerBinding("djUnit", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtdjUnit, "data");
		dataBinder.registerBinding("kpUnit", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtkpUnit, "data");
		dataBinder.registerBinding("fkUnit", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtfkUnit, "data");
		dataBinder.registerBinding("fpNumber", String.class, this.txtfpNumber, "text");
		dataBinder.registerBinding("amount", String.class, this.txtamount, "text");
		dataBinder.registerBinding("sales", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtsales, "data");
		dataBinder.registerBinding("tjType", com.kingdee.eas.custom.richbase.ExamTypeInfo.class, this.prmttjType, "data");
		dataBinder.registerBinding("bizState", com.kingdee.eas.custom.richbase.BizStateEnum.class, this.bizState, "selectedItem");
		dataBinder.registerBinding("beizhu", String.class, this.txtbeizhu, "text");
		dataBinder.registerBinding("kpCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtkpCompany, "data");
		dataBinder.registerBinding("djCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtdjCompany, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("cardNo", String.class, this.txtcardNo, "text");
		dataBinder.registerBinding("cardType", com.kingdee.eas.custom.richbase.CardType.class, this.cardType, "selectedItem");
		dataBinder.registerBinding("cardAmount", java.math.BigDecimal.class, this.txtcardAmount, "value");
		dataBinder.registerBinding("saleAmount", java.math.BigDecimal.class, this.txtsaleAmount, "value");
		dataBinder.registerBinding("zjjg", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtzjjg, "data");
		dataBinder.registerBinding("yhxAmount", java.math.BigDecimal.class, this.txtyhxAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.richinf.app.RichExamedEditUIHandler";
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
        this.kdtDjrentry.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.richinf.RichExamedInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.djtcNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.djctName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.slType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.skType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.djr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.jsAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ywdjbh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.djxmbm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.djxmmc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.jxbs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.klj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.zkl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.jsje", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.se", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.jshj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Djrentry.ywdjbh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ldNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ywNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qyUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fkUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fpNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sales", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tjType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cardNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cardType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cardAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zjjg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yhxAmount", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.djtcNumber"));
    	sic.add(new SelectorItemInfo("entrys.djctName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.slType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.slType.id"));
			sic.add(new SelectorItemInfo("entrys.slType.name"));
        	sic.add(new SelectorItemInfo("entrys.slType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.skType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.skType.id"));
			sic.add(new SelectorItemInfo("entrys.skType.name"));
        	sic.add(new SelectorItemInfo("entrys.skType.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.beizhu"));
    	sic.add(new SelectorItemInfo("entrys.djr"));
    	sic.add(new SelectorItemInfo("entrys.jsAmount"));
    	sic.add(new SelectorItemInfo("entrys.ywdjbh"));
        sic.add(new SelectorItemInfo("dj"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.Djrentry.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.Djrentry.id"));
		}
    	sic.add(new SelectorItemInfo("entrys.Djrentry.djxmbm"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.djxmmc"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.jxbs"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.klj"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.zkl"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.jsje"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.se"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.jshj"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.beizhu"));
    	sic.add(new SelectorItemInfo("entrys.Djrentry.ywdjbh"));
        sic.add(new SelectorItemInfo("hc"));
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
        sic.add(new SelectorItemInfo("ldNumber"));
        sic.add(new SelectorItemInfo("djDate"));
        sic.add(new SelectorItemInfo("ywNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("qyUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("qyUnit.id"));
        	sic.add(new SelectorItemInfo("qyUnit.number"));
        	sic.add(new SelectorItemInfo("qyUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("djUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("djUnit.id"));
        	sic.add(new SelectorItemInfo("djUnit.number"));
        	sic.add(new SelectorItemInfo("djUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("kpUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("kpUnit.id"));
        	sic.add(new SelectorItemInfo("kpUnit.number"));
        	sic.add(new SelectorItemInfo("kpUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fkUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fkUnit.id"));
        	sic.add(new SelectorItemInfo("fkUnit.number"));
        	sic.add(new SelectorItemInfo("fkUnit.name"));
		}
        sic.add(new SelectorItemInfo("fpNumber"));
        sic.add(new SelectorItemInfo("amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sales.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sales.id"));
        	sic.add(new SelectorItemInfo("sales.number"));
        	sic.add(new SelectorItemInfo("sales.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tjType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tjType.id"));
        	sic.add(new SelectorItemInfo("tjType.number"));
        	sic.add(new SelectorItemInfo("tjType.name"));
		}
        sic.add(new SelectorItemInfo("bizState"));
        sic.add(new SelectorItemInfo("beizhu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("kpCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("kpCompany.id"));
        	sic.add(new SelectorItemInfo("kpCompany.number"));
        	sic.add(new SelectorItemInfo("kpCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("djCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("djCompany.id"));
        	sic.add(new SelectorItemInfo("djCompany.number"));
        	sic.add(new SelectorItemInfo("djCompany.name"));
		}
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("cardNo"));
        sic.add(new SelectorItemInfo("cardType"));
        sic.add(new SelectorItemInfo("cardAmount"));
        sic.add(new SelectorItemInfo("saleAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("zjjg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("zjjg.id"));
        	sic.add(new SelectorItemInfo("zjjg.number"));
        	sic.add(new SelectorItemInfo("zjjg.name"));
		}
        sic.add(new SelectorItemInfo("yhxAmount"));
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
        return new MetaDataPK("com.kingdee.eas.custom.richinf.client", "RichExamedEditUI");
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
        return com.kingdee.eas.custom.richinf.client.RichExamedEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamedFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamedInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamedInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/custom/richinf/RichExamed";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.custom.richinf.app.RichExamedQuery");
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
		vo.put("bizState","30");
vo.put("cardType","10");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}