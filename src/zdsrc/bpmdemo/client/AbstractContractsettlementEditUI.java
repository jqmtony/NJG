/**
 * output package name
 */
package com.kingdee.eas.bpmdemo.client;

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
public abstract class AbstractContractsettlementEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractsettlementEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer bookedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorgunit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgetFeeCriteria;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continfoPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuaranceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQualityTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqualityGuaranteRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttchment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttenTwo;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconstructPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCompensation;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelDeduct;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelGuerdon;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable damages;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel damages_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable deduction;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel deduction_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtJlEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtJlEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSettlementEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtSettlementEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtorgunit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtgetFeeCriteria;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinfoPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbuildArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGuaranceAmt;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkQualityTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtqualityGuaranteRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtqualityGuarante;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttchment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalSettlePrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttenTwo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPeriod;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtconstructPrice;
    protected com.kingdee.eas.bpmdemo.ContractsettlementInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractsettlementEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractsettlementEditUI.class.getName());
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
        this.bookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.paneBIZLayerControl17 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contorgunit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOriginalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgetFeeCriteria = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contunitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continfoPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuildArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGuaranceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQualityTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqualityGuaranteRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqualityGuarante = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttchment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalOriginalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalSettlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttenTwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExchangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contconstructPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.panelCompensation = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelDeduct = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelGuerdon = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.damages = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.deduction = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtJlEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSettlementEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtorgunit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcontractNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcontractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtgetFeeCriteria = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSettlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtunitPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinfoPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGuaranceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkQualityTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtqualityGuaranteRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtqualityGuarante = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttchment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotalOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalSettlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAttenTwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtExchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtconstructPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.bookedDate.setName("bookedDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.paneBIZLayerControl17.setName("paneBIZLayerControl17");
        this.contorgunit.setName("contorgunit");
        this.contcurProject.setName("contcurProject");
        this.contcontractNo.setName("contcontractNo");
        this.contcontractName.setName("contcontractName");
        this.contname.setName("contname");
        this.contOriginalAmount.setName("contOriginalAmount");
        this.contgetFeeCriteria.setName("contgetFeeCriteria");
        this.contSettlePrice.setName("contSettlePrice");
        this.contunitPrice.setName("contunitPrice");
        this.continfoPrice.setName("continfoPrice");
        this.contbuildArea.setName("contbuildArea");
        this.contGuaranceAmt.setName("contGuaranceAmt");
        this.contQualityTime.setName("contQualityTime");
        this.contqualityGuaranteRate.setName("contqualityGuaranteRate");
        this.contqualityGuarante.setName("contqualityGuarante");
        this.contAttchment.setName("contAttchment");
        this.contTotalOriginalAmount.setName("contTotalOriginalAmount");
        this.contTotalSettlePrice.setName("contTotalSettlePrice");
        this.contAttenTwo.setName("contAttenTwo");
        this.kDSeparator8.setName("kDSeparator8");
        this.contCurrency.setName("contCurrency");
        this.contPeriod.setName("contPeriod");
        this.contExchangeRate.setName("contExchangeRate");
        this.contconstructPrice.setName("contconstructPrice");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.panelCompensation.setName("panelCompensation");
        this.panelDeduct.setName("panelDeduct");
        this.panelGuerdon.setName("panelGuerdon");
        this.kDPanel1.setName("kDPanel1");
        this.damages.setName("damages");
        this.deduction.setName("deduction");
        this.kdtJlEntrys.setName("kdtJlEntrys");
        this.kdtSettlementEntrys.setName("kdtSettlementEntrys");
        this.txtorgunit.setName("txtorgunit");
        this.txtcurProject.setName("txtcurProject");
        this.txtcontractNo.setName("txtcontractNo");
        this.txtcontractName.setName("txtcontractName");
        this.txtname.setName("txtname");
        this.txtOriginalAmount.setName("txtOriginalAmount");
        this.txtgetFeeCriteria.setName("txtgetFeeCriteria");
        this.txtSettlePrice.setName("txtSettlePrice");
        this.txtunitPrice.setName("txtunitPrice");
        this.txtinfoPrice.setName("txtinfoPrice");
        this.txtbuildArea.setName("txtbuildArea");
        this.txtGuaranceAmt.setName("txtGuaranceAmt");
        this.pkQualityTime.setName("pkQualityTime");
        this.txtqualityGuaranteRate.setName("txtqualityGuaranteRate");
        this.txtqualityGuarante.setName("txtqualityGuarante");
        this.txtAttchment.setName("txtAttchment");
        this.txtTotalOriginalAmount.setName("txtTotalOriginalAmount");
        this.txtTotalSettlePrice.setName("txtTotalSettlePrice");
        this.txtAttenTwo.setName("txtAttenTwo");
        this.prmtCurrency.setName("prmtCurrency");
        this.prmtPeriod.setName("prmtPeriod");
        this.txtExchangeRate.setName("txtExchangeRate");
        this.txtconstructPrice.setName("txtconstructPrice");
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
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);		
        this.contCreator.setForeground(new java.awt.Color(0,0,0));
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setBoundLabelAlignment(7);		
        this.contCreateTime.setVisible(true);		
        this.contCreateTime.setForeground(new java.awt.Color(0,0,0));
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);		
        this.contLastUpdateUser.setBoundLabelAlignment(7);		
        this.contLastUpdateUser.setForeground(new java.awt.Color(0,0,0));
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);		
        this.contLastUpdateTime.setBoundLabelAlignment(7);		
        this.contLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);		
        this.contNumber.setForeground(new java.awt.Color(0,0,0));
        // bookedDate		
        this.bookedDate.setBoundLabelText(resHelper.getString("bookedDate.boundLabelText"));		
        this.bookedDate.setBoundLabelLength(100);		
        this.bookedDate.setBoundLabelUnderline(true);		
        this.bookedDate.setBoundLabelAlignment(7);		
        this.bookedDate.setVisible(true);		
        this.bookedDate.setForeground(new java.awt.Color(0,0,0));
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(true);		
        this.contDescription.setForeground(new java.awt.Color(0,0,0));
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelAlignment(7);		
        this.contAuditor.setVisible(true);		
        this.contAuditor.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl17		
        this.paneBIZLayerControl17.setVisible(true);		
        this.paneBIZLayerControl17.setOpaque(true);		
        this.paneBIZLayerControl17.setBackground(new java.awt.Color(236,236,232));
        // contorgunit		
        this.contorgunit.setBoundLabelText(resHelper.getString("contorgunit.boundLabelText"));		
        this.contorgunit.setBoundLabelLength(100);		
        this.contorgunit.setBoundLabelUnderline(true);		
        this.contorgunit.setVisible(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);
        // contcontractNo		
        this.contcontractNo.setBoundLabelText(resHelper.getString("contcontractNo.boundLabelText"));		
        this.contcontractNo.setBoundLabelLength(100);		
        this.contcontractNo.setBoundLabelUnderline(true);		
        this.contcontractNo.setVisible(true);
        // contcontractName		
        this.contcontractName.setBoundLabelText(resHelper.getString("contcontractName.boundLabelText"));		
        this.contcontractName.setBoundLabelLength(100);		
        this.contcontractName.setBoundLabelUnderline(true);		
        this.contcontractName.setVisible(true);
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);
        // contOriginalAmount		
        this.contOriginalAmount.setBoundLabelText(resHelper.getString("contOriginalAmount.boundLabelText"));		
        this.contOriginalAmount.setBoundLabelLength(100);		
        this.contOriginalAmount.setBoundLabelUnderline(true);		
        this.contOriginalAmount.setVisible(true);
        // contgetFeeCriteria		
        this.contgetFeeCriteria.setBoundLabelText(resHelper.getString("contgetFeeCriteria.boundLabelText"));		
        this.contgetFeeCriteria.setBoundLabelLength(100);		
        this.contgetFeeCriteria.setBoundLabelUnderline(true);		
        this.contgetFeeCriteria.setVisible(true);
        // contSettlePrice		
        this.contSettlePrice.setBoundLabelText(resHelper.getString("contSettlePrice.boundLabelText"));		
        this.contSettlePrice.setBoundLabelLength(100);		
        this.contSettlePrice.setBoundLabelUnderline(true);		
        this.contSettlePrice.setVisible(true);
        // contunitPrice		
        this.contunitPrice.setBoundLabelText(resHelper.getString("contunitPrice.boundLabelText"));		
        this.contunitPrice.setBoundLabelLength(100);		
        this.contunitPrice.setBoundLabelUnderline(true);		
        this.contunitPrice.setVisible(true);
        // continfoPrice		
        this.continfoPrice.setBoundLabelText(resHelper.getString("continfoPrice.boundLabelText"));		
        this.continfoPrice.setBoundLabelLength(100);		
        this.continfoPrice.setBoundLabelUnderline(true);		
        this.continfoPrice.setVisible(true);
        // contbuildArea		
        this.contbuildArea.setBoundLabelText(resHelper.getString("contbuildArea.boundLabelText"));		
        this.contbuildArea.setBoundLabelLength(100);		
        this.contbuildArea.setBoundLabelUnderline(true);		
        this.contbuildArea.setVisible(true);
        // contGuaranceAmt		
        this.contGuaranceAmt.setBoundLabelText(resHelper.getString("contGuaranceAmt.boundLabelText"));		
        this.contGuaranceAmt.setBoundLabelLength(100);		
        this.contGuaranceAmt.setBoundLabelUnderline(true);		
        this.contGuaranceAmt.setVisible(true);
        // contQualityTime		
        this.contQualityTime.setBoundLabelText(resHelper.getString("contQualityTime.boundLabelText"));		
        this.contQualityTime.setBoundLabelLength(100);		
        this.contQualityTime.setBoundLabelUnderline(true);		
        this.contQualityTime.setVisible(true);
        // contqualityGuaranteRate		
        this.contqualityGuaranteRate.setBoundLabelText(resHelper.getString("contqualityGuaranteRate.boundLabelText"));		
        this.contqualityGuaranteRate.setBoundLabelLength(100);		
        this.contqualityGuaranteRate.setBoundLabelUnderline(true);		
        this.contqualityGuaranteRate.setVisible(true);
        // contqualityGuarante		
        this.contqualityGuarante.setBoundLabelText(resHelper.getString("contqualityGuarante.boundLabelText"));		
        this.contqualityGuarante.setBoundLabelLength(100);		
        this.contqualityGuarante.setBoundLabelUnderline(true);		
        this.contqualityGuarante.setVisible(true);
        // contAttchment		
        this.contAttchment.setBoundLabelText(resHelper.getString("contAttchment.boundLabelText"));		
        this.contAttchment.setBoundLabelLength(100);		
        this.contAttchment.setBoundLabelUnderline(true);		
        this.contAttchment.setVisible(true);
        // contTotalOriginalAmount		
        this.contTotalOriginalAmount.setBoundLabelText(resHelper.getString("contTotalOriginalAmount.boundLabelText"));		
        this.contTotalOriginalAmount.setBoundLabelLength(100);		
        this.contTotalOriginalAmount.setBoundLabelUnderline(true);		
        this.contTotalOriginalAmount.setVisible(true);
        // contTotalSettlePrice		
        this.contTotalSettlePrice.setBoundLabelText(resHelper.getString("contTotalSettlePrice.boundLabelText"));		
        this.contTotalSettlePrice.setBoundLabelLength(100);		
        this.contTotalSettlePrice.setBoundLabelUnderline(true);		
        this.contTotalSettlePrice.setVisible(true);
        // contAttenTwo		
        this.contAttenTwo.setBoundLabelText(resHelper.getString("contAttenTwo.boundLabelText"));		
        this.contAttenTwo.setBoundLabelLength(100);		
        this.contAttenTwo.setBoundLabelUnderline(true);		
        this.contAttenTwo.setVisible(true);
        // kDSeparator8
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);		
        this.contCurrency.setVisible(true);
        // contPeriod		
        this.contPeriod.setBoundLabelText(resHelper.getString("contPeriod.boundLabelText"));		
        this.contPeriod.setBoundLabelLength(100);		
        this.contPeriod.setBoundLabelUnderline(true);		
        this.contPeriod.setVisible(true);
        // contExchangeRate		
        this.contExchangeRate.setBoundLabelText(resHelper.getString("contExchangeRate.boundLabelText"));		
        this.contExchangeRate.setBoundLabelLength(100);		
        this.contExchangeRate.setBoundLabelUnderline(true);		
        this.contExchangeRate.setVisible(true);
        // contconstructPrice		
        this.contconstructPrice.setBoundLabelText(resHelper.getString("contconstructPrice.boundLabelText"));		
        this.contconstructPrice.setBoundLabelLength(100);		
        this.contconstructPrice.setBoundLabelUnderline(true);		
        this.contconstructPrice.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setVisible(true);		
        this.prmtCreator.setRequired(false);		
        this.prmtCreator.setForeground(new java.awt.Color(0,0,0));
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);		
        this.kDDateCreateTime.setVisible(true);		
        this.kDDateCreateTime.setRequired(false);		
        this.kDDateCreateTime.setForeground(new java.awt.Color(0,0,0));
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setVisible(false);		
        this.prmtLastUpdateUser.setRequired(false);		
        this.prmtLastUpdateUser.setForeground(new java.awt.Color(0,0,0));
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);		
        this.kDDateLastUpdateTime.setVisible(false);		
        this.kDDateLastUpdateTime.setRequired(false);		
        this.kDDateLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(false);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(false);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setRequired(false);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setVisible(true);		
        this.prmtAuditor.setRequired(false);		
        this.prmtAuditor.setForeground(new java.awt.Color(0,0,0));
        // panelCompensation		
        this.panelCompensation.setVisible(true);
        // panelDeduct		
        this.panelDeduct.setVisible(true);
        // panelGuerdon
        // kDPanel1
        // damages
		String damagesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deductType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{deductType}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.damages.setFormatXml(resHelper.translateString("damages",damagesStrXML));

                this.damages.putBindContents("editData",new String[] {"select","number","name","amount","type","deductType","creator","createDate"});


        this.damages.checkParsed();
        KDTextField damages_select_TextField = new KDTextField();
        damages_select_TextField.setName("damages_select_TextField");
        damages_select_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_select_CellEditor = new KDTDefaultCellEditor(damages_select_TextField);
        this.damages.getColumn("select").setEditor(damages_select_CellEditor);
        KDTextField damages_number_TextField = new KDTextField();
        damages_number_TextField.setName("damages_number_TextField");
        damages_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_number_CellEditor = new KDTDefaultCellEditor(damages_number_TextField);
        this.damages.getColumn("number").setEditor(damages_number_CellEditor);
        KDTextField damages_name_TextField = new KDTextField();
        damages_name_TextField.setName("damages_name_TextField");
        damages_name_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_name_CellEditor = new KDTDefaultCellEditor(damages_name_TextField);
        this.damages.getColumn("name").setEditor(damages_name_CellEditor);
        KDFormattedTextField damages_amount_TextField = new KDFormattedTextField();
        damages_amount_TextField.setName("damages_amount_TextField");
        damages_amount_TextField.setVisible(true);
        damages_amount_TextField.setEditable(true);
        damages_amount_TextField.setHorizontalAlignment(2);
        damages_amount_TextField.setDataType(1);
        	damages_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	damages_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        damages_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor damages_amount_CellEditor = new KDTDefaultCellEditor(damages_amount_TextField);
        this.damages.getColumn("amount").setEditor(damages_amount_CellEditor);
        KDTextField damages_type_TextField = new KDTextField();
        damages_type_TextField.setName("damages_type_TextField");
        damages_type_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_type_CellEditor = new KDTDefaultCellEditor(damages_type_TextField);
        this.damages.getColumn("type").setEditor(damages_type_CellEditor);
        KDTextField damages_deductType_TextField = new KDTextField();
        damages_deductType_TextField.setName("damages_deductType_TextField");
        damages_deductType_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_deductType_CellEditor = new KDTDefaultCellEditor(damages_deductType_TextField);
        this.damages.getColumn("deductType").setEditor(damages_deductType_CellEditor);
        KDTextField damages_creator_TextField = new KDTextField();
        damages_creator_TextField.setName("damages_creator_TextField");
        damages_creator_TextField.setMaxLength(100);
        KDTDefaultCellEditor damages_creator_CellEditor = new KDTDefaultCellEditor(damages_creator_TextField);
        this.damages.getColumn("creator").setEditor(damages_creator_CellEditor);
        KDDatePicker damages_createDate_DatePicker = new KDDatePicker();
        damages_createDate_DatePicker.setName("damages_createDate_DatePicker");
        damages_createDate_DatePicker.setVisible(true);
        damages_createDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor damages_createDate_CellEditor = new KDTDefaultCellEditor(damages_createDate_DatePicker);
        this.damages.getColumn("createDate").setEditor(damages_createDate_CellEditor);
        // deduction
		String deductionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"reason\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"deductTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{reason}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{deductTime}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.deduction.setFormatXml(resHelper.translateString("deduction",deductionStrXML));

                this.deduction.putBindContents("editData",new String[] {"select","number","type","reason","amount","deductTime","creator","createDate"});


        this.deduction.checkParsed();
        KDTextField deduction_select_TextField = new KDTextField();
        deduction_select_TextField.setName("deduction_select_TextField");
        deduction_select_TextField.setMaxLength(100);
        KDTDefaultCellEditor deduction_select_CellEditor = new KDTDefaultCellEditor(deduction_select_TextField);
        this.deduction.getColumn("select").setEditor(deduction_select_CellEditor);
        KDTextField deduction_number_TextField = new KDTextField();
        deduction_number_TextField.setName("deduction_number_TextField");
        deduction_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor deduction_number_CellEditor = new KDTDefaultCellEditor(deduction_number_TextField);
        this.deduction.getColumn("number").setEditor(deduction_number_CellEditor);
        KDTextField deduction_type_TextField = new KDTextField();
        deduction_type_TextField.setName("deduction_type_TextField");
        deduction_type_TextField.setMaxLength(100);
        KDTDefaultCellEditor deduction_type_CellEditor = new KDTDefaultCellEditor(deduction_type_TextField);
        this.deduction.getColumn("type").setEditor(deduction_type_CellEditor);
        KDTextField deduction_reason_TextField = new KDTextField();
        deduction_reason_TextField.setName("deduction_reason_TextField");
        deduction_reason_TextField.setMaxLength(100);
        KDTDefaultCellEditor deduction_reason_CellEditor = new KDTDefaultCellEditor(deduction_reason_TextField);
        this.deduction.getColumn("reason").setEditor(deduction_reason_CellEditor);
        KDFormattedTextField deduction_amount_TextField = new KDFormattedTextField();
        deduction_amount_TextField.setName("deduction_amount_TextField");
        deduction_amount_TextField.setVisible(true);
        deduction_amount_TextField.setEditable(true);
        deduction_amount_TextField.setHorizontalAlignment(2);
        deduction_amount_TextField.setDataType(1);
        	deduction_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	deduction_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        deduction_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor deduction_amount_CellEditor = new KDTDefaultCellEditor(deduction_amount_TextField);
        this.deduction.getColumn("amount").setEditor(deduction_amount_CellEditor);
        KDDatePicker deduction_deductTime_DatePicker = new KDDatePicker();
        deduction_deductTime_DatePicker.setName("deduction_deductTime_DatePicker");
        deduction_deductTime_DatePicker.setVisible(true);
        deduction_deductTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor deduction_deductTime_CellEditor = new KDTDefaultCellEditor(deduction_deductTime_DatePicker);
        this.deduction.getColumn("deductTime").setEditor(deduction_deductTime_CellEditor);
        KDTextField deduction_creator_TextField = new KDTextField();
        deduction_creator_TextField.setName("deduction_creator_TextField");
        deduction_creator_TextField.setMaxLength(100);
        KDTDefaultCellEditor deduction_creator_CellEditor = new KDTDefaultCellEditor(deduction_creator_TextField);
        this.deduction.getColumn("creator").setEditor(deduction_creator_CellEditor);
        KDDatePicker deduction_createDate_DatePicker = new KDDatePicker();
        deduction_createDate_DatePicker.setName("deduction_createDate_DatePicker");
        deduction_createDate_DatePicker.setVisible(true);
        deduction_createDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor deduction_createDate_CellEditor = new KDTDefaultCellEditor(deduction_createDate_DatePicker);
        this.deduction.getColumn("createDate").setEditor(deduction_createDate_CellEditor);
        // kdtJlEntrys
		String kdtJlEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"resaon\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{resaon}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtJlEntrys.setFormatXml(resHelper.translateString("kdtJlEntrys",kdtJlEntrysStrXML));

                this.kdtJlEntrys.putBindContents("editData",new String[] {"seq","select","number","name","amount","resaon","type","creator","createTime"});


        this.kdtJlEntrys.checkParsed();
        KDFormattedTextField kdtJlEntrys_seq_TextField = new KDFormattedTextField();
        kdtJlEntrys_seq_TextField.setName("kdtJlEntrys_seq_TextField");
        kdtJlEntrys_seq_TextField.setVisible(true);
        kdtJlEntrys_seq_TextField.setEditable(true);
        kdtJlEntrys_seq_TextField.setHorizontalAlignment(2);
        kdtJlEntrys_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtJlEntrys_seq_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_seq_TextField);
        this.kdtJlEntrys.getColumn("seq").setEditor(kdtJlEntrys_seq_CellEditor);
        KDTextField kdtJlEntrys_select_TextField = new KDTextField();
        kdtJlEntrys_select_TextField.setName("kdtJlEntrys_select_TextField");
        kdtJlEntrys_select_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_select_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_select_TextField);
        this.kdtJlEntrys.getColumn("select").setEditor(kdtJlEntrys_select_CellEditor);
        KDTextField kdtJlEntrys_number_TextField = new KDTextField();
        kdtJlEntrys_number_TextField.setName("kdtJlEntrys_number_TextField");
        kdtJlEntrys_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_number_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_number_TextField);
        this.kdtJlEntrys.getColumn("number").setEditor(kdtJlEntrys_number_CellEditor);
        KDTextField kdtJlEntrys_name_TextField = new KDTextField();
        kdtJlEntrys_name_TextField.setName("kdtJlEntrys_name_TextField");
        kdtJlEntrys_name_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_name_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_name_TextField);
        this.kdtJlEntrys.getColumn("name").setEditor(kdtJlEntrys_name_CellEditor);
        KDFormattedTextField kdtJlEntrys_amount_TextField = new KDFormattedTextField();
        kdtJlEntrys_amount_TextField.setName("kdtJlEntrys_amount_TextField");
        kdtJlEntrys_amount_TextField.setVisible(true);
        kdtJlEntrys_amount_TextField.setEditable(true);
        kdtJlEntrys_amount_TextField.setHorizontalAlignment(2);
        kdtJlEntrys_amount_TextField.setDataType(1);
        	kdtJlEntrys_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtJlEntrys_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtJlEntrys_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtJlEntrys_amount_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_amount_TextField);
        this.kdtJlEntrys.getColumn("amount").setEditor(kdtJlEntrys_amount_CellEditor);
        KDTextField kdtJlEntrys_resaon_TextField = new KDTextField();
        kdtJlEntrys_resaon_TextField.setName("kdtJlEntrys_resaon_TextField");
        kdtJlEntrys_resaon_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_resaon_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_resaon_TextField);
        this.kdtJlEntrys.getColumn("resaon").setEditor(kdtJlEntrys_resaon_CellEditor);
        KDTextField kdtJlEntrys_type_TextField = new KDTextField();
        kdtJlEntrys_type_TextField.setName("kdtJlEntrys_type_TextField");
        kdtJlEntrys_type_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_type_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_type_TextField);
        this.kdtJlEntrys.getColumn("type").setEditor(kdtJlEntrys_type_CellEditor);
        KDTextField kdtJlEntrys_creator_TextField = new KDTextField();
        kdtJlEntrys_creator_TextField.setName("kdtJlEntrys_creator_TextField");
        kdtJlEntrys_creator_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJlEntrys_creator_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_creator_TextField);
        this.kdtJlEntrys.getColumn("creator").setEditor(kdtJlEntrys_creator_CellEditor);
        KDDatePicker kdtJlEntrys_createTime_DatePicker = new KDDatePicker();
        kdtJlEntrys_createTime_DatePicker.setName("kdtJlEntrys_createTime_DatePicker");
        kdtJlEntrys_createTime_DatePicker.setVisible(true);
        kdtJlEntrys_createTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtJlEntrys_createTime_CellEditor = new KDTDefaultCellEditor(kdtJlEntrys_createTime_DatePicker);
        this.kdtJlEntrys.getColumn("createTime").setEditor(kdtJlEntrys_createTime_CellEditor);
        // kdtSettlementEntrys
		String kdtSettlementEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"settlePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"qualityGuarante\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"period\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"getFeeCriteria\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unitPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"infoPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditorTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"originalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{billName}</t:Cell><t:Cell>$Resource{settlePrice}</t:Cell><t:Cell>$Resource{qualityGuarante}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{getFeeCriteria}</t:Cell><t:Cell>$Resource{unitPrice}</t:Cell><t:Cell>$Resource{infoPrice}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditorTime}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSettlementEntrys.setFormatXml(resHelper.translateString("kdtSettlementEntrys",kdtSettlementEntrysStrXML));

                this.kdtSettlementEntrys.putBindContents("editData",new String[] {"seq","number","billName","settlePrice","qualityGuarante","description","bookedDate","period","state","buildArea","getFeeCriteria","unitPrice","infoPrice","creator","createTime","auditor","auditorTime","originalAmount"});


        this.kdtSettlementEntrys.checkParsed();
        KDFormattedTextField kdtSettlementEntrys_seq_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_seq_TextField.setName("kdtSettlementEntrys_seq_TextField");
        kdtSettlementEntrys_seq_TextField.setVisible(true);
        kdtSettlementEntrys_seq_TextField.setEditable(true);
        kdtSettlementEntrys_seq_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtSettlementEntrys_seq_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_seq_TextField);
        this.kdtSettlementEntrys.getColumn("seq").setEditor(kdtSettlementEntrys_seq_CellEditor);
        KDTextField kdtSettlementEntrys_number_TextField = new KDTextField();
        kdtSettlementEntrys_number_TextField.setName("kdtSettlementEntrys_number_TextField");
        kdtSettlementEntrys_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_number_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_number_TextField);
        this.kdtSettlementEntrys.getColumn("number").setEditor(kdtSettlementEntrys_number_CellEditor);
        KDTextField kdtSettlementEntrys_billName_TextField = new KDTextField();
        kdtSettlementEntrys_billName_TextField.setName("kdtSettlementEntrys_billName_TextField");
        kdtSettlementEntrys_billName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_billName_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_billName_TextField);
        this.kdtSettlementEntrys.getColumn("billName").setEditor(kdtSettlementEntrys_billName_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_settlePrice_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_settlePrice_TextField.setName("kdtSettlementEntrys_settlePrice_TextField");
        kdtSettlementEntrys_settlePrice_TextField.setVisible(true);
        kdtSettlementEntrys_settlePrice_TextField.setEditable(true);
        kdtSettlementEntrys_settlePrice_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_settlePrice_TextField.setDataType(1);
        	kdtSettlementEntrys_settlePrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_settlePrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_settlePrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_settlePrice_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_settlePrice_TextField);
        this.kdtSettlementEntrys.getColumn("settlePrice").setEditor(kdtSettlementEntrys_settlePrice_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_qualityGuarante_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_qualityGuarante_TextField.setName("kdtSettlementEntrys_qualityGuarante_TextField");
        kdtSettlementEntrys_qualityGuarante_TextField.setVisible(true);
        kdtSettlementEntrys_qualityGuarante_TextField.setEditable(true);
        kdtSettlementEntrys_qualityGuarante_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_qualityGuarante_TextField.setDataType(1);
        	kdtSettlementEntrys_qualityGuarante_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_qualityGuarante_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_qualityGuarante_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_qualityGuarante_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_qualityGuarante_TextField);
        this.kdtSettlementEntrys.getColumn("qualityGuarante").setEditor(kdtSettlementEntrys_qualityGuarante_CellEditor);
        KDTextField kdtSettlementEntrys_description_TextField = new KDTextField();
        kdtSettlementEntrys_description_TextField.setName("kdtSettlementEntrys_description_TextField");
        kdtSettlementEntrys_description_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_description_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_description_TextField);
        this.kdtSettlementEntrys.getColumn("description").setEditor(kdtSettlementEntrys_description_CellEditor);
        KDDatePicker kdtSettlementEntrys_bookedDate_DatePicker = new KDDatePicker();
        kdtSettlementEntrys_bookedDate_DatePicker.setName("kdtSettlementEntrys_bookedDate_DatePicker");
        kdtSettlementEntrys_bookedDate_DatePicker.setVisible(true);
        kdtSettlementEntrys_bookedDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtSettlementEntrys_bookedDate_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_bookedDate_DatePicker);
        this.kdtSettlementEntrys.getColumn("bookedDate").setEditor(kdtSettlementEntrys_bookedDate_CellEditor);
        KDTextField kdtSettlementEntrys_period_TextField = new KDTextField();
        kdtSettlementEntrys_period_TextField.setName("kdtSettlementEntrys_period_TextField");
        kdtSettlementEntrys_period_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_period_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_period_TextField);
        this.kdtSettlementEntrys.getColumn("period").setEditor(kdtSettlementEntrys_period_CellEditor);
        KDTextField kdtSettlementEntrys_state_TextField = new KDTextField();
        kdtSettlementEntrys_state_TextField.setName("kdtSettlementEntrys_state_TextField");
        kdtSettlementEntrys_state_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_state_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_state_TextField);
        this.kdtSettlementEntrys.getColumn("state").setEditor(kdtSettlementEntrys_state_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_buildArea_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_buildArea_TextField.setName("kdtSettlementEntrys_buildArea_TextField");
        kdtSettlementEntrys_buildArea_TextField.setVisible(true);
        kdtSettlementEntrys_buildArea_TextField.setEditable(true);
        kdtSettlementEntrys_buildArea_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_buildArea_TextField.setDataType(1);
        	kdtSettlementEntrys_buildArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_buildArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_buildArea_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_buildArea_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_buildArea_TextField);
        this.kdtSettlementEntrys.getColumn("buildArea").setEditor(kdtSettlementEntrys_buildArea_CellEditor);
        KDTextField kdtSettlementEntrys_getFeeCriteria_TextField = new KDTextField();
        kdtSettlementEntrys_getFeeCriteria_TextField.setName("kdtSettlementEntrys_getFeeCriteria_TextField");
        kdtSettlementEntrys_getFeeCriteria_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_getFeeCriteria_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_getFeeCriteria_TextField);
        this.kdtSettlementEntrys.getColumn("getFeeCriteria").setEditor(kdtSettlementEntrys_getFeeCriteria_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_unitPrice_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_unitPrice_TextField.setName("kdtSettlementEntrys_unitPrice_TextField");
        kdtSettlementEntrys_unitPrice_TextField.setVisible(true);
        kdtSettlementEntrys_unitPrice_TextField.setEditable(true);
        kdtSettlementEntrys_unitPrice_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_unitPrice_TextField.setDataType(1);
        	kdtSettlementEntrys_unitPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_unitPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_unitPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_unitPrice_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_unitPrice_TextField);
        this.kdtSettlementEntrys.getColumn("unitPrice").setEditor(kdtSettlementEntrys_unitPrice_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_infoPrice_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_infoPrice_TextField.setName("kdtSettlementEntrys_infoPrice_TextField");
        kdtSettlementEntrys_infoPrice_TextField.setVisible(true);
        kdtSettlementEntrys_infoPrice_TextField.setEditable(true);
        kdtSettlementEntrys_infoPrice_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_infoPrice_TextField.setDataType(1);
        	kdtSettlementEntrys_infoPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_infoPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_infoPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_infoPrice_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_infoPrice_TextField);
        this.kdtSettlementEntrys.getColumn("infoPrice").setEditor(kdtSettlementEntrys_infoPrice_CellEditor);
        KDTextField kdtSettlementEntrys_creator_TextField = new KDTextField();
        kdtSettlementEntrys_creator_TextField.setName("kdtSettlementEntrys_creator_TextField");
        kdtSettlementEntrys_creator_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_creator_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_creator_TextField);
        this.kdtSettlementEntrys.getColumn("creator").setEditor(kdtSettlementEntrys_creator_CellEditor);
        KDDatePicker kdtSettlementEntrys_createTime_DatePicker = new KDDatePicker();
        kdtSettlementEntrys_createTime_DatePicker.setName("kdtSettlementEntrys_createTime_DatePicker");
        kdtSettlementEntrys_createTime_DatePicker.setVisible(true);
        kdtSettlementEntrys_createTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtSettlementEntrys_createTime_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_createTime_DatePicker);
        this.kdtSettlementEntrys.getColumn("createTime").setEditor(kdtSettlementEntrys_createTime_CellEditor);
        KDTextField kdtSettlementEntrys_auditor_TextField = new KDTextField();
        kdtSettlementEntrys_auditor_TextField.setName("kdtSettlementEntrys_auditor_TextField");
        kdtSettlementEntrys_auditor_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSettlementEntrys_auditor_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_auditor_TextField);
        this.kdtSettlementEntrys.getColumn("auditor").setEditor(kdtSettlementEntrys_auditor_CellEditor);
        KDDatePicker kdtSettlementEntrys_auditorTime_DatePicker = new KDDatePicker();
        kdtSettlementEntrys_auditorTime_DatePicker.setName("kdtSettlementEntrys_auditorTime_DatePicker");
        kdtSettlementEntrys_auditorTime_DatePicker.setVisible(true);
        kdtSettlementEntrys_auditorTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtSettlementEntrys_auditorTime_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_auditorTime_DatePicker);
        this.kdtSettlementEntrys.getColumn("auditorTime").setEditor(kdtSettlementEntrys_auditorTime_CellEditor);
        KDFormattedTextField kdtSettlementEntrys_originalAmount_TextField = new KDFormattedTextField();
        kdtSettlementEntrys_originalAmount_TextField.setName("kdtSettlementEntrys_originalAmount_TextField");
        kdtSettlementEntrys_originalAmount_TextField.setVisible(true);
        kdtSettlementEntrys_originalAmount_TextField.setEditable(true);
        kdtSettlementEntrys_originalAmount_TextField.setHorizontalAlignment(2);
        kdtSettlementEntrys_originalAmount_TextField.setDataType(1);
        	kdtSettlementEntrys_originalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtSettlementEntrys_originalAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtSettlementEntrys_originalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtSettlementEntrys_originalAmount_CellEditor = new KDTDefaultCellEditor(kdtSettlementEntrys_originalAmount_TextField);
        this.kdtSettlementEntrys.getColumn("originalAmount").setEditor(kdtSettlementEntrys_originalAmount_CellEditor);
        // txtorgunit		
        this.txtorgunit.setVisible(true);		
        this.txtorgunit.setHorizontalAlignment(2);		
        this.txtorgunit.setMaxLength(100);		
        this.txtorgunit.setRequired(false);
        // txtcurProject		
        this.txtcurProject.setVisible(true);		
        this.txtcurProject.setHorizontalAlignment(2);		
        this.txtcurProject.setMaxLength(100);		
        this.txtcurProject.setRequired(false);
        // txtcontractNo		
        this.txtcontractNo.setVisible(true);		
        this.txtcontractNo.setHorizontalAlignment(2);		
        this.txtcontractNo.setMaxLength(100);		
        this.txtcontractNo.setRequired(false);
        // txtcontractName		
        this.txtcontractName.setVisible(true);		
        this.txtcontractName.setHorizontalAlignment(2);		
        this.txtcontractName.setMaxLength(100);		
        this.txtcontractName.setRequired(false);
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(false);
        // txtOriginalAmount		
        this.txtOriginalAmount.setVisible(true);		
        this.txtOriginalAmount.setHorizontalAlignment(2);		
        this.txtOriginalAmount.setDataType(1);		
        this.txtOriginalAmount.setSupportedEmpty(true);		
        this.txtOriginalAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtOriginalAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtOriginalAmount.setPrecision(2);		
        this.txtOriginalAmount.setRequired(false);
        // txtgetFeeCriteria		
        this.txtgetFeeCriteria.setVisible(true);		
        this.txtgetFeeCriteria.setHorizontalAlignment(2);		
        this.txtgetFeeCriteria.setDataType(1);		
        this.txtgetFeeCriteria.setSupportedEmpty(true);		
        this.txtgetFeeCriteria.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtgetFeeCriteria.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtgetFeeCriteria.setPrecision(10);		
        this.txtgetFeeCriteria.setRequired(false);
        // txtSettlePrice		
        this.txtSettlePrice.setVisible(true);		
        this.txtSettlePrice.setHorizontalAlignment(2);		
        this.txtSettlePrice.setDataType(1);		
        this.txtSettlePrice.setSupportedEmpty(true);		
        this.txtSettlePrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtSettlePrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtSettlePrice.setPrecision(2);		
        this.txtSettlePrice.setRequired(false);
        // txtunitPrice		
        this.txtunitPrice.setVisible(true);		
        this.txtunitPrice.setHorizontalAlignment(2);		
        this.txtunitPrice.setDataType(1);		
        this.txtunitPrice.setSupportedEmpty(true);		
        this.txtunitPrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtunitPrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtunitPrice.setPrecision(2);		
        this.txtunitPrice.setRequired(false);
        // txtinfoPrice		
        this.txtinfoPrice.setVisible(true);		
        this.txtinfoPrice.setHorizontalAlignment(2);		
        this.txtinfoPrice.setMaxLength(100);		
        this.txtinfoPrice.setRequired(false);
        // txtbuildArea		
        this.txtbuildArea.setVisible(true);		
        this.txtbuildArea.setHorizontalAlignment(2);		
        this.txtbuildArea.setDataType(1);		
        this.txtbuildArea.setSupportedEmpty(true);		
        this.txtbuildArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtbuildArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtbuildArea.setPrecision(10);		
        this.txtbuildArea.setRequired(false);
        // txtGuaranceAmt		
        this.txtGuaranceAmt.setVisible(true);		
        this.txtGuaranceAmt.setHorizontalAlignment(2);		
        this.txtGuaranceAmt.setDataType(1);		
        this.txtGuaranceAmt.setSupportedEmpty(true);		
        this.txtGuaranceAmt.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtGuaranceAmt.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtGuaranceAmt.setPrecision(2);		
        this.txtGuaranceAmt.setRequired(false);
        // pkQualityTime		
        this.pkQualityTime.setVisible(true);		
        this.pkQualityTime.setRequired(false);
        // txtqualityGuaranteRate		
        this.txtqualityGuaranteRate.setVisible(true);		
        this.txtqualityGuaranteRate.setHorizontalAlignment(2);		
        this.txtqualityGuaranteRate.setDataType(1);		
        this.txtqualityGuaranteRate.setSupportedEmpty(true);		
        this.txtqualityGuaranteRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtqualityGuaranteRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtqualityGuaranteRate.setPrecision(10);		
        this.txtqualityGuaranteRate.setRequired(false);
        // txtqualityGuarante		
        this.txtqualityGuarante.setVisible(true);		
        this.txtqualityGuarante.setHorizontalAlignment(2);		
        this.txtqualityGuarante.setDataType(1);		
        this.txtqualityGuarante.setSupportedEmpty(true);		
        this.txtqualityGuarante.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtqualityGuarante.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtqualityGuarante.setPrecision(2);		
        this.txtqualityGuarante.setRequired(false);
        // txtAttchment		
        this.txtAttchment.setVisible(true);		
        this.txtAttchment.setHorizontalAlignment(2);		
        this.txtAttchment.setMaxLength(100);		
        this.txtAttchment.setRequired(false);
        // txtTotalOriginalAmount		
        this.txtTotalOriginalAmount.setVisible(true);		
        this.txtTotalOriginalAmount.setHorizontalAlignment(2);		
        this.txtTotalOriginalAmount.setDataType(1);		
        this.txtTotalOriginalAmount.setSupportedEmpty(true);		
        this.txtTotalOriginalAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtTotalOriginalAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtTotalOriginalAmount.setPrecision(2);		
        this.txtTotalOriginalAmount.setRequired(false);
        // txtTotalSettlePrice		
        this.txtTotalSettlePrice.setVisible(true);		
        this.txtTotalSettlePrice.setHorizontalAlignment(2);		
        this.txtTotalSettlePrice.setDataType(1);		
        this.txtTotalSettlePrice.setSupportedEmpty(true);		
        this.txtTotalSettlePrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtTotalSettlePrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtTotalSettlePrice.setPrecision(2);		
        this.txtTotalSettlePrice.setRequired(false);
        // txtAttenTwo		
        this.txtAttenTwo.setVisible(true);		
        this.txtAttenTwo.setHorizontalAlignment(2);		
        this.txtAttenTwo.setMaxLength(100);		
        this.txtAttenTwo.setRequired(false);
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setVisible(true);		
        this.prmtCurrency.setEditable(true);		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setRequired(false);
        // prmtPeriod		
        this.prmtPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtPeriod.setVisible(true);		
        this.prmtPeriod.setEditable(true);		
        this.prmtPeriod.setDisplayFormat("$periodNumber$");		
        this.prmtPeriod.setEditFormat("$number$");		
        this.prmtPeriod.setCommitFormat("$number$");		
        this.prmtPeriod.setRequired(false);
        // txtExchangeRate		
        this.txtExchangeRate.setVisible(true);		
        this.txtExchangeRate.setHorizontalAlignment(2);		
        this.txtExchangeRate.setDataType(1);		
        this.txtExchangeRate.setSupportedEmpty(true);		
        this.txtExchangeRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtExchangeRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtExchangeRate.setPrecision(10);		
        this.txtExchangeRate.setRequired(false);
        // txtconstructPrice		
        this.txtconstructPrice.setVisible(true);		
        this.txtconstructPrice.setHorizontalAlignment(2);		
        this.txtconstructPrice.setDataType(1);		
        this.txtconstructPrice.setSupportedEmpty(true);		
        this.txtconstructPrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtconstructPrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtconstructPrice.setPrecision(2);		
        this.txtconstructPrice.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {deduction,damages,kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,txtorgunit,txtcurProject,txtcontractNo,txtcontractName,txtname,txtOriginalAmount,txtgetFeeCriteria,txtSettlePrice,txtunitPrice,txtinfoPrice,txtbuildArea,txtGuaranceAmt,pkQualityTime,txtqualityGuaranteRate,txtqualityGuarante,txtAttchment,txtTotalOriginalAmount,txtTotalSettlePrice,txtAttenTwo,prmtCurrency,prmtPeriod,txtExchangeRate,txtconstructPrice}));
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(13, 560, 448, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 560, 448, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(553, 558, 446, 19));
        this.add(contCreateTime, new KDLayout.Constraints(553, 558, 446, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(594, 593, 27, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(594, 593, 27, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(553, 593, 446, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(553, 593, 446, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(6, 72, 448, 19));
        this.add(contNumber, new KDLayout.Constraints(6, 72, 448, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        bookedDate.setBounds(new Rectangle(6, 102, 270, 19));
        this.add(bookedDate, new KDLayout.Constraints(6, 102, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(366, 252, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(366, 252, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(13, 593, 448, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 593, 448, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        paneBIZLayerControl17.setBounds(new Rectangle(6, 331, 993, 213));
        this.add(paneBIZLayerControl17, new KDLayout.Constraints(6, 331, 993, 213, 0));
        contorgunit.setBounds(new Rectangle(6, 6, 448, 19));
        this.add(contorgunit, new KDLayout.Constraints(6, 6, 448, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(553, 6, 446, 19));
        this.add(contcurProject, new KDLayout.Constraints(553, 6, 446, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcontractNo.setBounds(new Rectangle(6, 31, 448, 19));
        this.add(contcontractNo, new KDLayout.Constraints(6, 31, 448, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractName.setBounds(new Rectangle(552, 31, 449, 19));
        this.add(contcontractName, new KDLayout.Constraints(552, 31, 449, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contname.setBounds(new Rectangle(554, 72, 446, 19));
        this.add(contname, new KDLayout.Constraints(554, 72, 446, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOriginalAmount.setBounds(new Rectangle(366, 132, 270, 19));
        this.add(contOriginalAmount, new KDLayout.Constraints(366, 132, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contgetFeeCriteria.setBounds(new Rectangle(6, 162, 270, 19));
        this.add(contgetFeeCriteria, new KDLayout.Constraints(6, 162, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettlePrice.setBounds(new Rectangle(727, 288, 270, 19));
        this.add(contSettlePrice, new KDLayout.Constraints(727, 288, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contunitPrice.setBounds(new Rectangle(729, 162, 270, 19));
        this.add(contunitPrice, new KDLayout.Constraints(729, 162, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continfoPrice.setBounds(new Rectangle(6, 192, 270, 19));
        this.add(continfoPrice, new KDLayout.Constraints(6, 192, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbuildArea.setBounds(new Rectangle(366, 192, 270, 19));
        this.add(contbuildArea, new KDLayout.Constraints(366, 192, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGuaranceAmt.setBounds(new Rectangle(729, 192, 270, 19));
        this.add(contGuaranceAmt, new KDLayout.Constraints(729, 192, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQualityTime.setBounds(new Rectangle(6, 222, 270, 19));
        this.add(contQualityTime, new KDLayout.Constraints(6, 222, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contqualityGuaranteRate.setBounds(new Rectangle(366, 222, 270, 19));
        this.add(contqualityGuaranteRate, new KDLayout.Constraints(366, 222, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contqualityGuarante.setBounds(new Rectangle(729, 222, 270, 19));
        this.add(contqualityGuarante, new KDLayout.Constraints(729, 222, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAttchment.setBounds(new Rectangle(6, 252, 269, 19));
        this.add(contAttchment, new KDLayout.Constraints(6, 252, 269, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalOriginalAmount.setBounds(new Rectangle(729, 252, 270, 19));
        this.add(contTotalOriginalAmount, new KDLayout.Constraints(729, 252, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalSettlePrice.setBounds(new Rectangle(366, 162, 270, 19));
        this.add(contTotalSettlePrice, new KDLayout.Constraints(366, 162, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttenTwo.setBounds(new Rectangle(5, 288, 472, 19));
        this.add(contAttenTwo, new KDLayout.Constraints(5, 288, 472, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(6, 56, 993, 10));
        this.add(kDSeparator8, new KDLayout.Constraints(6, 56, 993, 10, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(366, 102, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(366, 102, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPeriod.setBounds(new Rectangle(4, 132, 270, 19));
        this.add(contPeriod, new KDLayout.Constraints(4, 132, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExchangeRate.setBounds(new Rectangle(729, 102, 270, 19));
        this.add(contExchangeRate, new KDLayout.Constraints(729, 102, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contconstructPrice.setBounds(new Rectangle(729, 132, 270, 19));
        this.add(contconstructPrice, new KDLayout.Constraints(729, 132, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //bookedDate
        bookedDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //paneBIZLayerControl17
        paneBIZLayerControl17.add(panelCompensation, resHelper.getString("panelCompensation.constraints"));
        paneBIZLayerControl17.add(panelDeduct, resHelper.getString("panelDeduct.constraints"));
        paneBIZLayerControl17.add(panelGuerdon, resHelper.getString("panelGuerdon.constraints"));
        paneBIZLayerControl17.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //panelCompensation
        panelCompensation.setLayout(null);        damages.setBounds(new Rectangle(5, 5, 979, 171));
        damages_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,damages,new com.kingdee.eas.bpmdemo.ContractsettlementAssEntryInfo(),null,false);
        panelCompensation.add(damages_detailPanel, null);
        //panelDeduct
        panelDeduct.setLayout(null);        deduction.setBounds(new Rectangle(6, 7, 977, 161));
        deduction_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,deduction,new com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryInfo(),null,false);
        panelDeduct.add(deduction_detailPanel, null);
        //panelGuerdon
        panelGuerdon.setLayout(null);        kdtJlEntrys.setBounds(new Rectangle(9, 3, 600, 170));
        kdtJlEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtJlEntrys,new com.kingdee.eas.bpmdemo.ContractsettlementJlEntryInfo(),null,false);
        panelGuerdon.add(kdtJlEntrys_detailPanel, null);
        //kDPanel1
        kDPanel1.setLayout(null);        kdtSettlementEntrys.setBounds(new Rectangle(3, 7, 600, 170));
        kdtSettlementEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtSettlementEntrys,new com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryInfo(),null,false);
        kDPanel1.add(kdtSettlementEntrys_detailPanel, null);
        //contorgunit
        contorgunit.setBoundEditor(txtorgunit);
        //contcurProject
        contcurProject.setBoundEditor(txtcurProject);
        //contcontractNo
        contcontractNo.setBoundEditor(txtcontractNo);
        //contcontractName
        contcontractName.setBoundEditor(txtcontractName);
        //contname
        contname.setBoundEditor(txtname);
        //contOriginalAmount
        contOriginalAmount.setBoundEditor(txtOriginalAmount);
        //contgetFeeCriteria
        contgetFeeCriteria.setBoundEditor(txtgetFeeCriteria);
        //contSettlePrice
        contSettlePrice.setBoundEditor(txtSettlePrice);
        //contunitPrice
        contunitPrice.setBoundEditor(txtunitPrice);
        //continfoPrice
        continfoPrice.setBoundEditor(txtinfoPrice);
        //contbuildArea
        contbuildArea.setBoundEditor(txtbuildArea);
        //contGuaranceAmt
        contGuaranceAmt.setBoundEditor(txtGuaranceAmt);
        //contQualityTime
        contQualityTime.setBoundEditor(pkQualityTime);
        //contqualityGuaranteRate
        contqualityGuaranteRate.setBoundEditor(txtqualityGuaranteRate);
        //contqualityGuarante
        contqualityGuarante.setBoundEditor(txtqualityGuarante);
        //contAttchment
        contAttchment.setBoundEditor(txtAttchment);
        //contTotalOriginalAmount
        contTotalOriginalAmount.setBoundEditor(txtTotalOriginalAmount);
        //contTotalSettlePrice
        contTotalSettlePrice.setBoundEditor(txtTotalSettlePrice);
        //contAttenTwo
        contAttenTwo.setBoundEditor(txtAttenTwo);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contPeriod
        contPeriod.setBoundEditor(prmtPeriod);
        //contExchangeRate
        contExchangeRate.setBoundEditor(txtExchangeRate);
        //contconstructPrice
        contconstructPrice.setBoundEditor(txtconstructPrice);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("AssEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.damages, "id.text");
		dataBinder.registerBinding("AssEntrys", com.kingdee.eas.bpmdemo.ContractsettlementAssEntryInfo.class, this.damages, "userObject");
		dataBinder.registerBinding("AssEntrys.select", String.class, this.damages, "select.text");
		dataBinder.registerBinding("AssEntrys.number", String.class, this.damages, "number.text");
		dataBinder.registerBinding("AssEntrys.name", String.class, this.damages, "name.text");
		dataBinder.registerBinding("AssEntrys.amount", java.math.BigDecimal.class, this.damages, "amount.text");
		dataBinder.registerBinding("AssEntrys.type", String.class, this.damages, "type.text");
		dataBinder.registerBinding("AssEntrys.deductType", String.class, this.damages, "deductType.text");
		dataBinder.registerBinding("AssEntrys.creator", String.class, this.damages, "creator.text");
		dataBinder.registerBinding("AssEntrys.createDate", java.util.Date.class, this.damages, "createDate.text");
		dataBinder.registerBinding("OtherEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.deduction, "id.text");
		dataBinder.registerBinding("OtherEntrys", com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryInfo.class, this.deduction, "userObject");
		dataBinder.registerBinding("OtherEntrys.select", String.class, this.deduction, "select.text");
		dataBinder.registerBinding("OtherEntrys.number", String.class, this.deduction, "number.text");
		dataBinder.registerBinding("OtherEntrys.type", String.class, this.deduction, "type.text");
		dataBinder.registerBinding("OtherEntrys.reason", String.class, this.deduction, "reason.text");
		dataBinder.registerBinding("OtherEntrys.amount", java.math.BigDecimal.class, this.deduction, "amount.text");
		dataBinder.registerBinding("OtherEntrys.deductTime", java.util.Date.class, this.deduction, "deductTime.text");
		dataBinder.registerBinding("OtherEntrys.creator", String.class, this.deduction, "creator.text");
		dataBinder.registerBinding("OtherEntrys.createDate", java.util.Date.class, this.deduction, "createDate.text");
		dataBinder.registerBinding("JlEntrys.seq", int.class, this.kdtJlEntrys, "seq.text");
		dataBinder.registerBinding("JlEntrys", com.kingdee.eas.bpmdemo.ContractsettlementJlEntryInfo.class, this.kdtJlEntrys, "userObject");
		dataBinder.registerBinding("JlEntrys.select", String.class, this.kdtJlEntrys, "select.text");
		dataBinder.registerBinding("JlEntrys.number", String.class, this.kdtJlEntrys, "number.text");
		dataBinder.registerBinding("JlEntrys.name", String.class, this.kdtJlEntrys, "name.text");
		dataBinder.registerBinding("JlEntrys.amount", java.math.BigDecimal.class, this.kdtJlEntrys, "amount.text");
		dataBinder.registerBinding("JlEntrys.resaon", String.class, this.kdtJlEntrys, "resaon.text");
		dataBinder.registerBinding("JlEntrys.type", String.class, this.kdtJlEntrys, "type.text");
		dataBinder.registerBinding("JlEntrys.creator", String.class, this.kdtJlEntrys, "creator.text");
		dataBinder.registerBinding("JlEntrys.createTime", java.util.Date.class, this.kdtJlEntrys, "createTime.text");
		dataBinder.registerBinding("SettlementEntrys.seq", int.class, this.kdtSettlementEntrys, "seq.text");
		dataBinder.registerBinding("SettlementEntrys", com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryInfo.class, this.kdtSettlementEntrys, "userObject");
		dataBinder.registerBinding("SettlementEntrys.number", String.class, this.kdtSettlementEntrys, "number.text");
		dataBinder.registerBinding("SettlementEntrys.billName", String.class, this.kdtSettlementEntrys, "billName.text");
		dataBinder.registerBinding("SettlementEntrys.settlePrice", java.math.BigDecimal.class, this.kdtSettlementEntrys, "settlePrice.text");
		dataBinder.registerBinding("SettlementEntrys.qualityGuarante", java.math.BigDecimal.class, this.kdtSettlementEntrys, "qualityGuarante.text");
		dataBinder.registerBinding("SettlementEntrys.description", String.class, this.kdtSettlementEntrys, "description.text");
		dataBinder.registerBinding("SettlementEntrys.bookedDate", java.util.Date.class, this.kdtSettlementEntrys, "bookedDate.text");
		dataBinder.registerBinding("SettlementEntrys.period", String.class, this.kdtSettlementEntrys, "period.text");
		dataBinder.registerBinding("SettlementEntrys.state", String.class, this.kdtSettlementEntrys, "state.text");
		dataBinder.registerBinding("SettlementEntrys.buildArea", java.math.BigDecimal.class, this.kdtSettlementEntrys, "buildArea.text");
		dataBinder.registerBinding("SettlementEntrys.getFeeCriteria", String.class, this.kdtSettlementEntrys, "getFeeCriteria.text");
		dataBinder.registerBinding("SettlementEntrys.unitPrice", java.math.BigDecimal.class, this.kdtSettlementEntrys, "unitPrice.text");
		dataBinder.registerBinding("SettlementEntrys.infoPrice", java.math.BigDecimal.class, this.kdtSettlementEntrys, "infoPrice.text");
		dataBinder.registerBinding("SettlementEntrys.creator", String.class, this.kdtSettlementEntrys, "creator.text");
		dataBinder.registerBinding("SettlementEntrys.createTime", java.util.Date.class, this.kdtSettlementEntrys, "createTime.text");
		dataBinder.registerBinding("SettlementEntrys.auditor", String.class, this.kdtSettlementEntrys, "auditor.text");
		dataBinder.registerBinding("SettlementEntrys.auditorTime", java.util.Date.class, this.kdtSettlementEntrys, "auditorTime.text");
		dataBinder.registerBinding("SettlementEntrys.originalAmount", java.math.BigDecimal.class, this.kdtSettlementEntrys, "originalAmount.text");
		dataBinder.registerBinding("orgunit", String.class, this.txtorgunit, "text");
		dataBinder.registerBinding("curProject", String.class, this.txtcurProject, "text");
		dataBinder.registerBinding("contractNo", String.class, this.txtcontractNo, "text");
		dataBinder.registerBinding("contractName", String.class, this.txtcontractName, "text");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("OriginalAmount", java.math.BigDecimal.class, this.txtOriginalAmount, "value");
		dataBinder.registerBinding("getFeeCriteria", java.math.BigDecimal.class, this.txtgetFeeCriteria, "value");
		dataBinder.registerBinding("SettlePrice", java.math.BigDecimal.class, this.txtSettlePrice, "value");
		dataBinder.registerBinding("unitPrice", java.math.BigDecimal.class, this.txtunitPrice, "value");
		dataBinder.registerBinding("infoPrice", String.class, this.txtinfoPrice, "text");
		dataBinder.registerBinding("buildArea", java.math.BigDecimal.class, this.txtbuildArea, "value");
		dataBinder.registerBinding("GuaranceAmt", java.math.BigDecimal.class, this.txtGuaranceAmt, "value");
		dataBinder.registerBinding("QualityTime", java.util.Date.class, this.pkQualityTime, "value");
		dataBinder.registerBinding("qualityGuaranteRate", java.math.BigDecimal.class, this.txtqualityGuaranteRate, "value");
		dataBinder.registerBinding("qualityGuarante", java.math.BigDecimal.class, this.txtqualityGuarante, "value");
		dataBinder.registerBinding("Attchment", String.class, this.txtAttchment, "text");
		dataBinder.registerBinding("TotalOriginalAmount", java.math.BigDecimal.class, this.txtTotalOriginalAmount, "value");
		dataBinder.registerBinding("TotalSettlePrice", java.math.BigDecimal.class, this.txtTotalSettlePrice, "value");
		dataBinder.registerBinding("AttenTwo", String.class, this.txtAttenTwo, "text");
		dataBinder.registerBinding("Currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("Period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtPeriod, "data");
		dataBinder.registerBinding("ExchangeRate", java.math.BigDecimal.class, this.txtExchangeRate, "value");
		dataBinder.registerBinding("constructPrice", java.math.BigDecimal.class, this.txtconstructPrice, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.bpmdemo.app.ContractsettlementEditUIHandler";
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
        this.deduction.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.bpmdemo.ContractsettlementInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.select", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.deductType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.createDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.select", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.reason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.deductTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OtherEntrys.createDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.select", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.resaon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("JlEntrys.createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.billName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.settlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.qualityGuarante", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.buildArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.getFeeCriteria", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.unitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.infoPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.auditorTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlementEntrys.originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgunit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("OriginalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("getFeeCriteria", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SettlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("infoPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("GuaranceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("QualityTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qualityGuaranteRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qualityGuarante", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Attchment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TotalOriginalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TotalSettlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AttenTwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ExchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constructPrice", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("AssEntrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("AssEntrys.*"));
		}
		else{
			sic.add(new SelectorItemInfo("AssEntrys.name"));
        	sic.add(new SelectorItemInfo("AssEntrys.number"));
		}
    	sic.add(new SelectorItemInfo("AssEntrys.select"));
    	sic.add(new SelectorItemInfo("AssEntrys.amount"));
    	sic.add(new SelectorItemInfo("AssEntrys.type"));
    	sic.add(new SelectorItemInfo("AssEntrys.deductType"));
    	sic.add(new SelectorItemInfo("AssEntrys.creator"));
    	sic.add(new SelectorItemInfo("AssEntrys.createDate"));
    	sic.add(new SelectorItemInfo("OtherEntrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("OtherEntrys.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("OtherEntrys.number"));
		}
    	sic.add(new SelectorItemInfo("OtherEntrys.select"));
    	sic.add(new SelectorItemInfo("OtherEntrys.type"));
    	sic.add(new SelectorItemInfo("OtherEntrys.reason"));
    	sic.add(new SelectorItemInfo("OtherEntrys.amount"));
    	sic.add(new SelectorItemInfo("OtherEntrys.deductTime"));
    	sic.add(new SelectorItemInfo("OtherEntrys.creator"));
    	sic.add(new SelectorItemInfo("OtherEntrys.createDate"));
    	sic.add(new SelectorItemInfo("JlEntrys.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("JlEntrys.*"));
		}
		else{
			sic.add(new SelectorItemInfo("JlEntrys.name"));
        	sic.add(new SelectorItemInfo("JlEntrys.number"));
		}
    	sic.add(new SelectorItemInfo("JlEntrys.select"));
    	sic.add(new SelectorItemInfo("JlEntrys.amount"));
    	sic.add(new SelectorItemInfo("JlEntrys.resaon"));
    	sic.add(new SelectorItemInfo("JlEntrys.type"));
    	sic.add(new SelectorItemInfo("JlEntrys.creator"));
    	sic.add(new SelectorItemInfo("JlEntrys.createTime"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SettlementEntrys.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("SettlementEntrys.number"));
		}
    	sic.add(new SelectorItemInfo("SettlementEntrys.billName"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.settlePrice"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.qualityGuarante"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.description"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.bookedDate"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.period"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.state"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.buildArea"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.getFeeCriteria"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.unitPrice"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.infoPrice"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.creator"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.createTime"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.auditor"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.auditorTime"));
    	sic.add(new SelectorItemInfo("SettlementEntrys.originalAmount"));
        sic.add(new SelectorItemInfo("orgunit"));
        sic.add(new SelectorItemInfo("curProject"));
        sic.add(new SelectorItemInfo("contractNo"));
        sic.add(new SelectorItemInfo("contractName"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("OriginalAmount"));
        sic.add(new SelectorItemInfo("getFeeCriteria"));
        sic.add(new SelectorItemInfo("SettlePrice"));
        sic.add(new SelectorItemInfo("unitPrice"));
        sic.add(new SelectorItemInfo("infoPrice"));
        sic.add(new SelectorItemInfo("buildArea"));
        sic.add(new SelectorItemInfo("GuaranceAmt"));
        sic.add(new SelectorItemInfo("QualityTime"));
        sic.add(new SelectorItemInfo("qualityGuaranteRate"));
        sic.add(new SelectorItemInfo("qualityGuarante"));
        sic.add(new SelectorItemInfo("Attchment"));
        sic.add(new SelectorItemInfo("TotalOriginalAmount"));
        sic.add(new SelectorItemInfo("TotalSettlePrice"));
        sic.add(new SelectorItemInfo("AttenTwo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Currency.id"));
        	sic.add(new SelectorItemInfo("Currency.number"));
        	sic.add(new SelectorItemInfo("Currency.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Period.id"));
        	sic.add(new SelectorItemInfo("Period.number"));
        	sic.add(new SelectorItemInfo("Period.periodNumber"));
		}
        sic.add(new SelectorItemInfo("ExchangeRate"));
        sic.add(new SelectorItemInfo("constructPrice"));
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
        return new MetaDataPK("com.kingdee.eas.bpmdemo.client", "ContractsettlementEditUI");
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
        return com.kingdee.eas.bpmdemo.client.ContractsettlementEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.bpmdemo.ContractsettlementFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.bpmdemo.ContractsettlementInfo objectValue = new com.kingdee.eas.bpmdemo.ContractsettlementInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/bpmdemo/Contractsettlement";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.bpmdemo.app.ContractsettlementQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return damages;
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