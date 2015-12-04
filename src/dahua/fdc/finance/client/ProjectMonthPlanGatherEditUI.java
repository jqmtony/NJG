/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBill;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.DepartmentEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.UseTypeEnum;
import com.kingdee.eas.fdc.contract.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.IProjectMonthPlanGather;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryInfo;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.fdc.finance.utils.TableHelper;
import com.kingdee.eas.fm.ecore.app.bean.commercialdraft.ContractInformation;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * output class name
 */
public class ProjectMonthPlanGatherEditUI extends AbstractProjectMonthPlanGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectMonthPlanGatherEditUI.class);
//    private KDTable bgTable=null;
    private KDTable contractTable=null;
    private Boolean isLoad=false;
	private KDComboBox cbRespPerson=null;
	private KDComboBox cbDepartment=null;
	private KDCheckBox cbHouseAmount=null;
	private List reportColoum=null;
	private List executeColoum=null;
	private List houseColoum=null;
	private Boolean isHasFbDetail = false;
    public ProjectMonthPlanGatherEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
    	addDataChangeListener(this.cbVersionType);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.cbVersionType);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthPlanGatherFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() {
		this.txtNumber.setMaxLength(255);

		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.txtVersion.setPrecision(1);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.contExecuteAmount.getBoundLabel().setForeground(Color.RED);
		
		this.contVersionType.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.btnGet.setEnabled(false);
			if(this.contractTable!=null){
				this.contractTable.setEnabled(false);
			}
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
//			if(this.txtVersion.getIntegerValue()!=null&&
//					this.txtVersion.getIntegerValue()==1){
//				this.btnGet.setEnabled(true);
//			}else{
//				this.btnGet.setEnabled(false);
//			}
			if(this.contractTable!=null){
				this.contractTable.setEnabled(true);
			}
			if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
				this.actionALine.setEnabled(true);
				this.actionILine.setEnabled(true);
				this.actionRLine.setEnabled(true);
			}else{
				this.actionALine.setEnabled(false);
				this.actionILine.setEnabled(false);
				this.actionRLine.setEnabled(false);
			}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		this.editData.setBizDate(cal.getTime());
		this.txtName.setText(year+"年"+month+"月"+"项目月度资金计划（"+this.editData.getCurProject().getName()+"）");
		
		super.storeFields();
	}
	public void loadFields() {
		isLoad=true;
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
		loadEntry();
		
		if(this.txtVersion.getIntegerValue()>1){
			this.cbVersionType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.cbVersionType.setEnabled(false);
			this.spYear.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spYear.setEnabled(false);
			this.spMonth.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spMonth.setEnabled(false);
		}
		
		attachListeners();
		setOprtState(this.getOprtState());
		isLoad=false;
	}
	protected void initMonthColoum(KDTable table,int year,int month,int cycle){
//		KDBizPromptBox f7Box = new KDBizPromptBox();
//		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//		
//		f7Box.setDisplayFormat("$name$");
//		f7Box.setEditFormat("$name$");
//		f7Box.setCommitFormat("$name$");
//		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
//		bgItemDialog.setMulSelect(false);
//		bgItemDialog.setSelectCombinItem(false);
//		f7Box.setSelector(bgItemDialog);
//		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		KDTextField remark = new KDTextField();
		remark.setMaxLength(255);
		KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(remark);
		
		reportColoum=new ArrayList();
		executeColoum=new ArrayList();
		houseColoum=new ArrayList();
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String monthStr= year + "年" + month + "月";
			String key=year+"year"+month+"m";
			
			IColumn amountColumn=table.addColumn();
			amountColumn.setKey(key+"amount");
			table.getColumn(key+"amount").setEditor(amountEditor);
			table.getColumn(key+"amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getColumn(key+"amount").getStyleAttributes().setFontColor(Color.BLUE);
			
			amountColumn=table.addColumn();
			if(i!=0)
				reportColoum.add(amountColumn);
			amountColumn.setKey(key+"reportAmount");
			table.getColumn(key+"reportAmount").setEditor(amountEditor);
			table.getColumn(key+"reportAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"reportAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			
//			amountColumn=table.addColumn();
//			houseColoum.add(amountColumn);
//			amountColumn.setKey(key+"houseAmount");
//			amountColumn.getStyleAttributes().setLocked(false);
//			table.getColumn(key+"houseAmount").setEditor(amountEditor);
//			table.getColumn(key+"houseAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			table.getColumn(key+"houseAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			//add by shilei 
			if(i==0)
			{
				amountColumn=table.addColumn();
				houseColoum.add(amountColumn);
				amountColumn.setKey(key+"houseAmount");
				amountColumn.getStyleAttributes().setLocked(false);
				table.getColumn(key+"houseAmount").setEditor(amountEditor);
				table.getColumn(key+"houseAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				table.getColumn(key+"houseAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				
				StyleAttributes col = table.getColumn(key+"reportAmount").getStyleAttributes();
				col.setLocked(true);
				col.setBackground(new Color(231, 237, 242));
				
				if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
					col.setHided(true);
				}
				
				IColumn businessTicketColumn=table.addColumn();//商票
				businessTicketColumn.setKey(key+"businessTicketAmount");
				businessTicketColumn.setEditor(amountEditor);
				businessTicketColumn.getStyleAttributes().setLocked(false);
				businessTicketColumn.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				businessTicketColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				
				
				IColumn promissoryNoteColumn=table.addColumn();//期票
				promissoryNoteColumn.setKey(key+"promissoryNoteAmount");
				promissoryNoteColumn.setEditor(amountEditor);
				promissoryNoteColumn.getStyleAttributes().setLocked(false);
				promissoryNoteColumn.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				promissoryNoteColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				
				IColumn cashPaymentColumn=table.addColumn();//现金支付（含支票）
				cashPaymentColumn.setKey(key+"cashPaymentAmount");
				cashPaymentColumn.setEditor(amountEditor);
				cashPaymentColumn.getStyleAttributes().setLocked(false);
				cashPaymentColumn.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				cashPaymentColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				
				IColumn otherTxColumn=table.addColumn();//其他
				otherTxColumn.setKey(key+"otherTxAmount");
				otherTxColumn.setEditor(amountEditor);
				otherTxColumn.getStyleAttributes().setLocked(false);
				otherTxColumn.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				otherTxColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				
				table.getHeadRow(0).getCell(key+"houseAmount").setValue(monthStr);
				table.getHeadRow(0).getCell(key+"businessTicketAmount").setValue(monthStr);
				table.getHeadRow(0).getCell(key+"promissoryNoteAmount").setValue(monthStr);
				table.getHeadRow(0).getCell(key+"cashPaymentAmount").setValue(monthStr);
				table.getHeadRow(0).getCell(key+"otherTxAmount").setValue(monthStr);
				
				table.getHeadRow(1).getCell(key+"houseAmount").setValue("抵房金额");
				table.getHeadRow(1).getCell(key+"businessTicketAmount").setValue("商票");
				table.getHeadRow(1).getCell(key+"promissoryNoteAmount").setValue("期票");
				table.getHeadRow(1).getCell(key+"cashPaymentAmount").setValue("现金支付（含支票）");
				table.getHeadRow(1).getCell(key+"otherTxAmount").setValue("其他");
			}
			
			amountColumn=table.addColumn();
			executeColoum.add(amountColumn);
			amountColumn.setKey(key+"executeAmount");
			table.getColumn(key+"executeAmount").setEditor(amountEditor);
			table.getColumn(key+"executeAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"executeAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
//			IColumn useTypeColumn=table.addColumn();
//			useTypeColumn.setKey(key+"useType");
//			
//			KDComboBox combo = new KDComboBox();
//	        for(int k = 0; k < UseTypeEnum.getEnumList().size(); k++){
//	        	combo.addItem(UseTypeEnum.getEnumList().get(k));
//	        }
//	        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
//			table.getColumn(key+"useType").setEditor(comboEditor);
			IColumn payTypeColumn=table.addColumn();
			payTypeColumn.setKey(key+"payType");
			payTypeColumn.getStyleAttributes().setLocked(false);
			payTypeColumn.setRequired(true);
			KDBizPromptBox f7Box = new KDBizPromptBox();
			f7Box.setDisplayFormat("$name$");
			f7Box.setEditFormat("$name$");
			f7Box.setCommitFormat("$name$");
			f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
			payTypeColumn.setEditor(f7Editor);
			ObjectValueRender ovrNum = new ObjectValueRender();
			ovrNum.setFormat(new BizDataFormat("$name$"));
			payTypeColumn.setRenderer(ovrNum);
			
			IColumn remarkColumn=table.addColumn();
			remarkColumn.setEditor(remarkEditor);
			remarkColumn.setKey(key+"remark");
			
			
			table.getHeadRow(0).getCell(key+"amount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"reportAmount").setValue(monthStr);
//			table.getHeadRow(0).getCell(key+"houseAmount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"executeAmount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"remark").setValue(monthStr);
//			table.getHeadRow(0).getCell(key+"useType").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"payType").setValue(monthStr);
			
			table.getHeadRow(1).getCell(key+"amount").setValue("计划支付");
			table.getHeadRow(1).getCell(key+"reportAmount").setValue("上报金额");
//			table.getHeadRow(1).getCell(key+"houseAmount").setValue("抵房金额");
			table.getHeadRow(1).getCell(key+"executeAmount").setValue("核定金额");
//			table.getHeadRow(1).getCell(key+"remark").setValue("下月形象进度和付款节点");
			table.getHeadRow(1).getCell(key+"remark").setValue("支付节点");
//			table.getHeadRow(1).getCell(key+"useType").setValue("用款类型");
			table.getHeadRow(1).getCell(key+"payType").setValue("付款类型");
			
			int merge=table.getHeadRow(0).getCell(key+"amount").getColumnIndex();
			table.getHeadMergeManager().mergeBlock(0, merge, 0, merge+4);
			
			//add by shilei
			if(i==0)
			{
				table.getHeadMergeManager().mergeBlock(0, merge, 0, merge+9);
			}
			
			month++;
		}
	}
	protected void initContractTable(int year,int month,int cycle) {
		this.contractTable=new KDTable();
		this.contractTable.checkParsed();
		IRow headRow=this.contractTable.addHeadRow();
		IRow headRowName=this.contractTable.addHeadRow();
		
		this.contractTable.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=this.contractTable.addColumn();
		column.setKey("conName");
		column.setWidth(200);
		headRow.getCell("conName").setValue("合约规划名称");
		headRowName.getCell("conName").setValue("合约规划名称");
		
		column=this.contractTable.addColumn();
		column.setKey("conAmount");
		column.setWidth(200);
		headRow.getCell("conAmount").setValue("规划金额");
		headRowName.getCell("conAmount").setValue("规划金额");
		
		column=this.contractTable.addColumn();
		column.setKey("number");
		column.setWidth(200);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		headRow.getCell("number").setValue("编码");
		headRowName.getCell("number").setValue("编码");
		
		column=this.contractTable.addColumn();
		column.setKey("name");
		column.setWidth(200);
		headRow.getCell("name").setValue("名称");
		headRowName.getCell("name").setValue("名称");
		
		column=this.contractTable.addColumn();
		column.setKey("supplier");
		column.setWidth(200);
		headRow.getCell("supplier").setValue("供应商");
		headRowName.getCell("supplier").setValue("供应商");
		column.getStyleAttributes().setHided(true);

		column=this.contractTable.addColumn();
		column.setKey("respPerson");
		headRow.getCell("respPerson").setValue("责任人");
		headRowName.getCell("respPerson").setValue("责任人");
		
		column=this.contractTable.addColumn();
		column.setKey("department");
		headRow.getCell("department").setValue("用款部门");
		headRowName.getCell("department").setValue("用款部门");
		
		column=this.contractTable.addColumn();
		column.setKey("lastPrice");
		headRow.getCell("lastPrice").setValue("合同最新造价/规划余额");
		headRowName.getCell("lastPrice").setValue("合同最新造价/规划余额");
		
		column=this.contractTable.addColumn();
		column.setKey("applyAmount");
		column.setWidth(150);
		headRow.getCell("applyAmount").setValue("本月申请金额");
		headRowName.getCell("applyAmount").setValue("本月申请金额");
		
		column=this.contractTable.addColumn();
		column.setKey("monthActPayAmount");
		column.setWidth(150);
		headRow.getCell("monthActPayAmount").setValue("本月实付金额");
		headRowName.getCell("monthActPayAmount").setValue("本月实付金额");
		
		column=this.contractTable.addColumn();
		column.setKey("actPayAmount");
		column.setWidth(150);
		headRow.getCell("actPayAmount").setValue("截止本月累计实付金额");
		headRowName.getCell("actPayAmount").setValue("截止本月累计实付金额");
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str+ "%";
				}
				return str;
			}
		});
		
		column=this.contractTable.addColumn();
		column.setKey("rate");
		column.setWidth(150);
		column.setRenderer(render_scale);
		headRow.getCell("rate").setValue("截止本月累计实付比例");
		headRowName.getCell("rate").setValue("截止本月累计实付比例");
		
		column=this.contractTable.addColumn();
		column.setKey("nextRate");
		column.setWidth(150);
		column.setRenderer(render_scale);
		headRow.getCell("nextRate").setValue("截止下月计划累计付款比例");
		headRowName.getCell("nextRate").setValue("截止下月计划累计付款比例");
		
		column=this.contractTable.addColumn();
		column.setKey("noPayAmount");
		column.setWidth(150);
		headRow.getCell("noPayAmount").setValue("本月已批未付金额");
		headRowName.getCell("noPayAmount").setValue("本月已批未付金额");
		
		
		this.contractTable.getColumn("conAmount").setEditor(amountEditor);
		this.contractTable.getColumn("conAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("conAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("lastPrice").setEditor(amountEditor);
		this.contractTable.getColumn("lastPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("lastPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("applyAmount").setEditor(amountEditor);
		this.contractTable.getColumn("applyAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("applyAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("monthActPayAmount").setEditor(amountEditor);
		this.contractTable.getColumn("monthActPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("monthActPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("actPayAmount").setEditor(amountEditor);
		this.contractTable.getColumn("actPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("actPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("nextRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("nextRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("noPayAmount").setEditor(amountEditor);
		this.contractTable.getColumn("noPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("noPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.setName("contractTable");
//		this.contractTable.getViewManager().setFreezeView(0, 7);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 5, 1, 5);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 6, 1, 6);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 7, 1, 7);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 8, 1, 8);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 9, 1, 9);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 10, 1, 10);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 11, 1, 11);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 12, 1, 12);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 13, 1, 13);
		
		initMonthColoum(this.contractTable,year,month,cycle);
		
		ActionMap actionMap = this.contractTable.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		this.contractTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				table_Clicked(e);
			}
		});
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(this.contractTable.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(this.contractTable, BorderLayout.CENTER);

		cbRespPerson=new KDComboBox();
		cbDepartment=new KDComboBox();
		cbHouseAmount=new KDCheckBox();
		
		KDLabelContainer contRespPerson = new KDLabelContainer();
		contRespPerson.setBoundLabelText("责任人");		
		contRespPerson.setBoundLabelLength(100);		
		contRespPerson.setBoundLabelUnderline(true);		
		contRespPerson.setBoundEditor(cbRespPerson);
		contRespPerson.setBounds(new Rectangle(150, 1, 270, 19));
        contEntry.add(contRespPerson);
        
		KDLabelContainer contRespDep = new KDLabelContainer();
		contRespDep.setBoundLabelText("用款部门");		
		contRespDep.setBoundLabelLength(100);		
		contRespDep.setBoundLabelUnderline(true);	
		contRespDep.setBoundEditor(cbDepartment);
		contRespDep.setBounds(new Rectangle(450, 1, 270, 19));
        contEntry.add(contRespDep);
        
//        KDLabelContainer contHouseAmount = new KDLabelContainer();
//        contHouseAmount.setBoundLabelText("显示抵房金额");		
//        contHouseAmount.setBoundLabelLength(100);		
//        contHouseAmount.setBoundLabelUnderline(true);	
//        contHouseAmount.setBoundEditor(cbHouseAmount);
//        contHouseAmount.setBounds(new Rectangle(750, 1, 270, 19));
//        contEntry.add(contHouseAmount);
		
//        KDWorkButton btnAddRowinfo = new KDWorkButton();
//		KDWorkButton btnInsertRowinfo = new KDWorkButton();
//		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
//
//		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
//		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
//		btnAddRowinfo.setText("新增行");
//		btnAddRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
//		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
//		btnInsertRowinfo.setText("插入行");
//		btnInsertRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
//		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
//		btnDeleteRowinfo.setText("删除行");
//		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.pnlBig.add(contEntry, "付款计划明细");
		
		this.contractTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()!=null
				&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject() instanceof ProjectMonthPlanGatherDateEntryInfo){
			if(table.getColumnKey(e.getColIndex()).indexOf("reportAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setReportAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			
				int year=((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).getYear();
				int month=((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).getMonth();
				
				int spYear=this.spYear.getIntegerVlaue().intValue();
				int spMonth=this.spMonth.getIntegerVlaue().intValue()+1;
				
				if(((ProjectMonthPlanGatherEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).getContractBill()!=null&&((year==spYear&&month==spMonth)||(year==spYear+1&&month==1))){
					table.getRow(e.getRowIndex()).getCell("nextRate").setValue(FDCHelper.multiply(FDCHelper.divide(FDCHelper.add(table.getRow(e.getRowIndex()).getCell("actPayAmount").getValue(),table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()), table.getRow(e.getRowIndex()).getCell("lastPrice").getValue(),4,BigDecimal.ROUND_HALF_UP), new BigDecimal(100)));
				}
			}else if(table.getColumnKey(e.getColIndex()).indexOf("executeAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setExecuteAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("houseAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setHouseAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}
			//by shilei
			else if(table.getColumnKey(e.getColIndex()).indexOf("businessTicketAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBusinessTicket((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("promissoryNoteAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setPromissoryNote((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("cashPaymentAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setCashPayment((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("otherTxAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setOtherTx((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}
			
			int sp = -1;
			String key = table.getColumnKey(e.getColIndex());
			if(key.indexOf("houseAmount")>=0||key.indexOf("businessTicketAmount")>=0||key.indexOf("promissoryNoteAmount")>=0||key.indexOf("cashPaymentAmount")>=0||key.indexOf("otherTxAmount")>=0)
			{
				IRow row = table.getRow(e.getRowIndex());
				BigDecimal amount = BigDecimal.ZERO;
				for(int j=0;j<this.contractTable.getColumnCount();j++)
				{
					key = this.contractTable.getColumnKey(j);
					if(key.indexOf("businessTicketAmount")>=0||key.indexOf("promissoryNoteAmount")>=0||key.indexOf("cashPaymentAmount")>=0||key.indexOf("otherTxAmount")>=0)
					{
						sp = j;
						amount = amount.add(UIRuleUtil.getBigDecimal(row.getCell(key).getValue()));
					}
				}
				if(sp!=-1)
				{
					BigDecimal dfAmount = UIRuleUtil.getBigDecimal(row.getCell(sp-4).getValue());
					row.getCell(sp-5).setValue(dfAmount.add(amount));
//					row.getCell(sp-1).setValue(dfAmount.add(amount));
					((ProjectMonthPlanGatherDateEntryInfo)row.getCell(sp-5).getUserObject()).setReportAmount(dfAmount.add(amount));
//					((ProjectMonthPlanGatherDateEntryInfo)row.getCell(sp-1).getUserObject()).setCashPayment(dfAmount.add(amount));
				}
			}
			
			
			TableHelper.getFootRow(table, new String[]{table.getColumnKey(e.getColIndex())});
		}
	}
	private void setRowHide(){
		PersonInfo person=null;
		DepartmentEnum department=null;
		if(cbRespPerson.getSelectedItem()!=null&&cbRespPerson.getSelectedItem() instanceof KDComboBoxMultiColumnItem){
			KDComboBoxMultiColumnItem personItem=(KDComboBoxMultiColumnItem) cbRespPerson.getSelectedItem();
			person=(PersonInfo) personItem.getUserObject();
		}
		if(cbDepartment.getSelectedItem()!=null&&cbDepartment.getSelectedItem() instanceof KDComboBoxMultiColumnItem){
			KDComboBoxMultiColumnItem departmentItem=(KDComboBoxMultiColumnItem) cbDepartment.getSelectedItem();
			department=(DepartmentEnum) departmentItem.getUserObject();
		}
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow row=this.contractTable.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			PersonInfo rowPeson=(PersonInfo) row.getCell("respPerson").getUserObject();
			DepartmentEnum rowDepartment=(DepartmentEnum) row.getCell("department").getValue();
			if(person!=null&&rowPeson!=null&&department!=null&&rowDepartment!=null){
				if(person.getId().equals(rowPeson.getId())&&department.equals(rowDepartment)){
					row.getStyleAttributes().setHided(false);
				}else{
					row.getStyleAttributes().setHided(true);
				}
			}else if(person!=null&&rowPeson!=null&&!person.getId().equals(rowPeson.getId())&&department==null&&rowDepartment!=null){
				row.getStyleAttributes().setHided(true);
			}else if(department!=null&&rowDepartment!=null&&!department.equals(rowDepartment)&&person==null&&rowPeson!=null){
				row.getStyleAttributes().setHided(true);
			}else{
				row.getStyleAttributes().setHided(false);
			}
		}
	}
	protected void cbRespPerson_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		setRowHide();
	}
	protected void cbRespDep_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		setRowHide();
	}
	protected void cbHouseAmount_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow row=this.contractTable.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			for(int j=0;j<this.houseColoum.size();j++){
				IColumn coloum=(IColumn) this.houseColoum.get(i);
				if(row.getCell(coloum.getKey()).getValue()==null){
					row.getStyleAttributes().setHided(cbHouseAmount.isSelected());
				}
			}
		}
	}
	protected void table_Clicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e){
		KDTable table = (KDTable) e.getSource();
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(table.getColumnKey(e.getColIndex()).indexOf("amount")>0
					&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()!=null){
				ProjectMonthPlanGatherEntryInfo entry=(ProjectMonthPlanGatherEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
				if(entry!=null&&entry.getSrcId()!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", entry.getSrcId());
					String uiClass=null;
					if(entry.getSrcId().getType().equals(new ContractPayPlanInfo().getBOSType())){
						uiClass=ContractPayPlanEditUI.class.getName();
					}else if(entry.getSrcId().getType().equals(new ProjectMonthPlanProInfo().getBOSType())){
						uiClass=ProjectMonthPlanProEditUI.class.getName();
					}else if(entry.getSrcId().getType().equals(new ProjectMonthPlanGatherInfo().getBOSType())){
						uiClass=ProjectMonthPlanGatherEditUI.class.getName();
					}
					try {
						if(uiClass!=null){
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClass, uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					} catch (UIException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
//	protected void initBgTable(int year,int month,int cycle) {
//		this.bgTable=new KDTable();
//		this.bgTable.checkParsed();
//		IRow headRow=this.bgTable.addHeadRow();
//		IRow headRowName=this.bgTable.addHeadRow();
//		
//		this.bgTable.getStyleAttributes().setLocked(true);
//		KDFormattedTextField amount = new KDFormattedTextField();
//		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
//		amount.setPrecision(2);
//		amount.setNegatived(false);
//		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
//		
//		IColumn column=this.bgTable.addColumn();
//		column.setKey("bgItem");
//		headRow.getCell("bgItem").setValue("预算项目");
//		headRowName.getCell("bgItem").setValue("预算项目");
//		
//		for(int i=0;i<cycle;i++){
//			if (month > 12) {
//				year += 1;
//				month = 1;
//			}
//			String monthStr= year + "年" + month + "月";
//			String key=year+"year"+month+"m";
//			
//			IColumn amountColumn=this.bgTable.addColumn();
//			amountColumn.setKey(key+"amount");
//			this.bgTable.getColumn(key+"amount").setEditor(amountEditor);
//			this.bgTable.getColumn(key+"amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			this.bgTable.getColumn(key+"amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//			
//			
//			this.bgTable.getHeadRow(0).getCell(key+"amount").setValue(monthStr);
//			
//			this.bgTable.getHeadRow(1).getCell(key+"amount").setValue("计划支付");
//			
//			int merge=this.bgTable.getHeadRow(0).getCell(key+"amount").getColumnIndex();
//			this.bgTable.getHeadMergeManager().mergeBlock(0, merge, 0, merge);
//			
//			month++;
//		}
//        
//		this.bgTable.setName("contractTable");
//		this.bgTable.getViewManager().setFreezeView(0, 1);
//		this.bgTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
//		
//		KDContainer contEntry = new KDContainer();
//		contEntry.setName(this.bgTable.getName());
//		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
//		contEntry.getContentPane().add(this.bgTable, BorderLayout.CENTER);
//
//		this.pnlBig.add(contEntry, "预算明细");
//	}
	protected void loadEntry(){
		ProjectMonthPlanGatherEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.pnlBig.removeAll();
		
		int spYear=this.spYear.getIntegerVlaue().intValue();
		int spMonth=this.spMonth.getIntegerVlaue().intValue()+1;
		int cycle=this.editData.getCycle().getCycle().getValue();
	
		this.initContractTable(spYear,spMonth,cycle);
//		this.initBgTable(spYear,spMonth,cycle);
		
		Map rowMap=new HashMap();
		ProjectMonthPlanGatherDateEntryCollection sortCol=new ProjectMonthPlanGatherDateEntryCollection();
		IRow row=null;
		
		this.cbRespPerson.removeAllItems();
		this.cbDepartment.removeAllItems();
		
		KDComboBoxMultiColumnItem defaultValue=new KDComboBoxMultiColumnItem(new String[] { "","" });
		this.cbRespPerson.addItem(defaultValue);
		this.cbDepartment.addItem(defaultValue);
		
		Set respPersonSetId=new HashSet();
		Set respDepSetId=new HashSet();
		Map reportAmountMap = getReportAmount();
		for(int i=0;i<col.size();i++){
			ProjectMonthPlanGatherEntryInfo entry=col.get(i);
			String id=null;
			String number=null;
			PersonInfo respPerson=null;
			DepartmentEnum department=null;
			BigDecimal lastPrice=null;
			BigDecimal monthActPayAmount=null;
			BigDecimal actPayAmount=null;
			String conName=null;
			String supplier = null; //供应商
			BigDecimal conAmount=null;
			BigDecimal noPayAmount=null;
			BigDecimal rate=null;
			Map applyAmountMap = null;
			if(entry.getRespPerson()!=null){
				respPerson=entry.getRespPerson();
				if(!respPersonSetId.contains(respPerson.getId().toString())){
					KDComboBoxMultiColumnItem person=new KDComboBoxMultiColumnItem(new String[] { respPerson.getNumber(),respPerson.getName() });
					person.setUserObject(respPerson);
					this.cbRespPerson.addItem(person);
					respPersonSetId.add(respPerson.getId().toString());
				}
			}
			if(entry.getSrcId() != null) {
				applyAmountMap = (Map) reportAmountMap.get(entry.getSrcId().toString());
			}
			if(entry.getDepartment()!=null){
				department=entry.getDepartment();
				if(!respDepSetId.contains(department)){
					KDComboBoxMultiColumnItem departmentItem=new KDComboBoxMultiColumnItem(new String[] { department.getAlias(),"" });
					departmentItem.setUserObject(department);
					this.cbDepartment.addItem(departmentItem);
					respDepSetId.add(department);
				}
			}
			if(entry.getContractBill()!=null){
				id=entry.getContractBill().getId().toString();
				number=entry.getContractBill().getNumber();
				if(entry.getContractBill().getProgrammingContract()!=null){
					conName=entry.getContractBill().getProgrammingContract().getName();
					conAmount=entry.getContractBill().getProgrammingContract().getAmount();
					try {
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("*"));
						sic.add(new SelectorItemInfo("partB.id"));
						ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(entry.getContractBill().getId()), sic);
						supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(contractBillInfo.getPartB().getId())).getName();
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
				}
				try{
					lastPrice=FDCUtils.getContractLastAmt (null,id);
					
					int year=this.spYear.getIntegerVlaue().intValue();
					int month=this.spMonth.getIntegerVlaue().intValue();
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, month-1);
					cal.set(Calendar.DATE, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+id+"' ");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					
					IRowSet rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
					_builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+id+"' ");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					_builder.appendSql("and fpayDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'} ");
					
					rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						monthActPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
					
					_builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(bill.FAmount-isnull(pay.fpayAmount,0)) sumCount from t_con_payRequestBill bill ");
					_builder.appendSql("left join (select sum(FAmount) fpayAmount,ffdcPayReqID from t_cas_paymentbill where fbillstatus=15 group by ffdcPayReqID) pay on pay.ffdcPayReqID=bill.fid where bill.fstate='4AUDITTED' and bill.fid='"+id+"'");
					_builder.appendSql("and bill.fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					_builder.appendSql("and bill.fbookedDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'} ");
					
					rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						noPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (UuidException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else if(entry.getProgrammingContract()!=null){
				id=entry.getId().toString();
				number=entry.getProgrammingContract().getNumber();
				
				conName=entry.getProgrammingContract().getName();
				conAmount=entry.getProgrammingContract().getAmount();
				lastPrice=entry.getProgrammingContract().getBalance();
				try{
					int year=this.spYear.getIntegerVlaue().intValue();
					int month=this.spMonth.getIntegerVlaue().intValue();
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, month-1);
					cal.set(Calendar.DATE, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(pay.FAmount) payAmount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+id+"'");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					
					IRowSet rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("payAmount"));
					}
					
					_builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(pay.FAmount) payAmount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+id+"'");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					_builder.appendSql("and fpayDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'} ");
					
					rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("payAmount"));
					}
					
					_builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(bill.FAmount-isnull(pay.fpayAmount,0)) sumCount from t_con_contractWithoutText bill ");
					_builder.appendSql("left join (select sum(FAmount) fpayAmount,ffdcPayReqID from t_cas_paymentbill where fbillstatus=15 group by ffdcPayReqID) pay on pay.ffdcPayReqID=bill.fid where bill.fstate='4AUDITTED' and bill.FProgrammingContract='"+id+"'");
					_builder.appendSql("and bill.fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					_builder.appendSql("and bill.fbookedDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'} ");
					
					rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						noPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (UuidException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else{
				id=entry.getId().toString();
			}
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectMonthPlanGatherDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				
				sortCol.add(dateEntry);
				
				int month=dateEntry.getMonth();
				int year=dateEntry.getYear();
				
				if(rowMap.containsKey(id)){
					row=(IRow) rowMap.get(id);
				}else{
					row=this.contractTable.addRow();
					row.setUserObject(entry);
					row.getCell("conName").setValue(conName);
					row.getCell("conAmount").setValue(conAmount);
					row.getCell("number").setValue(number);
					row.getCell("name").setValue(entry.getName());
					if(respPerson!=null){
						row.getCell("respPerson").setValue(respPerson.getName());
						row.getCell("respPerson").setUserObject(respPerson);
					}
					if(department!=null){
						row.getCell("department").setValue(department);
						row.getCell("department").setUserObject(department);
					}

					//申请金额
					String key = spYear+"year"+(spMonth-1)+"m";
					BigDecimal reportAmount = BigDecimal.ZERO;
					if(applyAmountMap != null) {
						reportAmount = applyAmountMap.get(key) == null ? BigDecimal.ZERO : (BigDecimal) applyAmountMap.get(key);
						row.getCell("applyAmount").setValue(reportAmount);
					}
					row.getCell("lastPrice").setValue(lastPrice);
					row.getCell("monthActPayAmount").setValue(monthActPayAmount);
					row.getCell("actPayAmount").setValue(actPayAmount);
//					row.getCell("noPayAmount").setValue(noPayAmount);
					//本期申请未付金额 改为 本期申请金额-本月实际付款
					row.getCell("noPayAmount").setValue(reportAmount.subtract(monthActPayAmount == null ? BigDecimal.ZERO : monthActPayAmount));
					
					if(entry.getContractBill()!=null){
						rate=FDCHelper.multiply(FDCHelper.divide(actPayAmount, lastPrice,4,BigDecimal.ROUND_HALF_UP),new BigDecimal(100));
						row.getCell("rate").setValue(rate);
						
						row.getCell("supplier").setValue(supplier);
						
						if((year==spYear&&month==spMonth)||(year==spYear+1&&month==1)){
							row.getCell("nextRate").setValue(FDCHelper.multiply(FDCHelper.divide(FDCHelper.add(actPayAmount, dateEntry.getReportAmount()),lastPrice,4,BigDecimal.ROUND_HALF_UP), new BigDecimal(100)));
						}
					}
					rowMap.put(id, row);
				}
				
				String key=year+"year"+month+"m";
				
				if(row.getCell(key+"amount")!=null){
					row.getCell(key+"amount").setUserObject(dateEntry);
					row.getCell(key+"amount").setValue(dateEntry.getAmount());
				}
				if(row.getCell(key+"reportAmount")!=null){
					row.getCell(key+"reportAmount").setUserObject(dateEntry);
					row.getCell(key+"reportAmount").setValue(dateEntry.getReportAmount());
					BigDecimal reportAmount = dateEntry.getReportAmount();
					BigDecimal amount = dateEntry.getAmount();
					//上报金额超过计划金额则显示红色
					if(reportAmount != null && amount != null && reportAmount.compareTo(amount) > 0) {
						row.getCell(key+"reportAmount").getStyleAttributes().setBackground(Color.RED);
					}
//					row.getCell(key+"reportAmount").getStyleAttributes().setBackground(Color.RED);
				}
				if(row.getCell(key+"houseAmount")!=null){
					row.getCell(key+"houseAmount").setUserObject(dateEntry);
					row.getCell(key+"houseAmount").setValue(dateEntry.getHouseAmount());
				}
				if(row.getCell(key+"executeAmount")!=null){
					row.getCell(key+"executeAmount").setUserObject(dateEntry);
					row.getCell(key+"executeAmount").setValue(dateEntry.getExecuteAmount());
				}
				if(row.getCell(key+"remark")!=null){
					row.getCell(key+"remark").setUserObject(dateEntry);
					row.getCell(key+"remark").setValue(dateEntry.getRemark());
				}
//				if(row.getCell(key+"useType")!=null){
//					row.getCell(key+"useType").setUserObject(dateEntry);
//					row.getCell(key+"useType").setValue(dateEntry.getUseType());
//				}
				if(row.getCell(key+"payType")!=null){
					row.getCell(key+"payType").setUserObject(dateEntry);
					row.getCell(key+"payType").setValue(dateEntry.getPayType());
				}
				//add by shilei
				if(row.getCell(key+"businessTicketAmount")!=null){
					row.getCell(key+"businessTicketAmount").setUserObject(dateEntry);
					row.getCell(key+"businessTicketAmount").setValue(dateEntry.getBusinessTicket());
				}
				if(row.getCell(key+"promissoryNoteAmount")!=null){
					row.getCell(key+"promissoryNoteAmount").setUserObject(dateEntry);
					row.getCell(key+"promissoryNoteAmount").setValue(dateEntry.getPromissoryNote());
				}
				if(row.getCell(key+"cashPaymentAmount")!=null){
					row.getCell(key+"cashPaymentAmount").setUserObject(dateEntry);
					row.getCell(key+"cashPaymentAmount").setValue(dateEntry.getCashPayment());
				}
				if(row.getCell(key+"otherTxAmount")!=null){
					row.getCell(key+"otherTxAmount").setUserObject(dateEntry);
					row.getCell(key+"otherTxAmount").setValue(dateEntry.getOtherTx());
				}
			}
		}
		cbRespPerson.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                	cbRespPerson_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		cbDepartment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                	cbRespDep_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		cbHouseAmount.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                	cbHouseAmount_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		try {
			cbHouseAmount_itemStateChanged(null);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if(this.contractTable.getRowCount()>0){
			IRow contractRow=this.contractTable.addRow(0);
			contractRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			contractRow.getStyleAttributes().setLocked(true);
			contractRow.getCell("name").setValue("合同付款计划明细");
			this.contractTable.getMergeManager().mergeBlock(0, 0, 0, this.contractTable.getColumnCount()-1);
			
			int proIndex=0;
			int zbContractIndex=0;
			for(int i=0;i<this.contractTable.getRowCount();i++){
				if((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()!=null&&((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()).getContractBill()!=null){
					proIndex=i+1;
				}
				if((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()!=null&&((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()).getSrcId() !=null)
					zbContractIndex = i;
			}
			
			IRow proRow=null;
			if(proIndex==0){
				proRow=this.contractTable.addRow();
				proIndex=proRow.getRowIndex();
			}else{
				proRow=this.contractTable.addRow(proIndex);
			}
			proRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			proRow.getStyleAttributes().setLocked(true);
			proRow.getCell("name").setValue("待签合同&&无合同费用付款计划明细");
			this.contractTable.getMergeManager().mergeBlock(proIndex, 0, proIndex, this.contractTable.getColumnCount()-1);
			
			//大总包合同
			if(isHasFbDetail) {
				proRow=this.contractTable.addRow(contractTable.getRowCount()-1);
				proIndex=proRow.getRowIndex();
				proRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				proRow.getStyleAttributes().setLocked(true);
				proRow.getCell("name").setValue("大总包合同付款计划");
				this.contractTable.getMergeManager().mergeBlock(proIndex, 0, proIndex, this.contractTable.getColumnCount()-1);
			}
		}
		
		try {
			getExecuteAmount();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		setColoumHide();
		
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow zeroRow=this.contractTable.getRow(i);
			ProjectMonthPlanGatherEntryInfo entry=(ProjectMonthPlanGatherEntryInfo) zeroRow.getUserObject();
			if(zeroRow.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			for(int j=0;j<this.contractTable.getColumnCount();j++){
				if(this.contractTable.getColumn(j).isRequired()&&this.contractTable.getColumnKey(j).indexOf("Amount")>0&&zeroRow.getCell(j).getUserObject()==null){
					ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
					String key=this.contractTable.getColumnKey(j);
					int year=Integer.valueOf(this.contractTable.getColumnKey(j).substring(0, key.indexOf("year")));
					int month=Integer.valueOf(this.contractTable.getColumnKey(j).substring(key.indexOf("year")+4, key.indexOf("m")));
					
					dateEntry.setYear(year);
					dateEntry.setMonth(month);
					dateEntry.setAmount(FDCHelper.ZERO);
					VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
					if(versionType.equals(VersionTypeEnum.REPORT)){
						dateEntry.setReportAmount(FDCHelper.ZERO);
					}else{
						dateEntry.setExecuteAmount(FDCHelper.ZERO);
					}
					
					key=year+"year"+month+"m"; 
					if(zeroRow.getCell(key+"amount")!=null){
						zeroRow.getCell(key+"amount").setUserObject(dateEntry);
						zeroRow.getCell(key+"amount").setValue(dateEntry.getAmount());
					}
					if(row.getCell(key+"reportAmount")!=null){
						zeroRow.getCell(key+"reportAmount").setUserObject(dateEntry);
						zeroRow.getCell(key+"reportAmount").setValue(dateEntry.getReportAmount());
					}
					if(row.getCell(key+"houseAmount")!=null){
						zeroRow.getCell(key+"houseAmount").setUserObject(dateEntry);
						zeroRow.getCell(key+"houseAmount").setValue(dateEntry.getHouseAmount());
					}
					if(row.getCell(key+"executeAmount")!=null){
						zeroRow.getCell(key+"executeAmount").setUserObject(dateEntry);
						zeroRow.getCell(key+"executeAmount").setValue(dateEntry.getExecuteAmount());
					}
					entry.getDateEntry().add(dateEntry);
				}
			}
		}
		
//		Map bgRowMap=new HashMap();
//		CRMHelper.sortCollection(sortCol, "bgItem.number", true);
//		for(int i=0;i<sortCol.size();i++){
//			ProjectMonthPlanGatherDateEntryInfo dateEntry=sortCol.get(i);
//			if(dateEntry.getBgItem()==null) continue;
//			String bgItemId=dateEntry.getBgItem().getId().toString();
//			int month=dateEntry.getMonth();
//			int year=dateEntry.getYear();
//			if(bgRowMap.containsKey(bgItemId)){
//				row=(IRow) bgRowMap.get(bgItemId);
//			}else{
//				row=this.bgTable.addRow();
//				row.getCell("bgItem").setValue(dateEntry.getBgItem().getName());
//				bgRowMap.put(bgItemId, row);
//			}
//			String key=year+"year"+month+"m";
//			
//			if(row.getCell(key+"amount")!=null){
//				BigDecimal amount=FDCHelper.ZERO;
//				if(row.getCell(key+"amount").getValue()!=null){
//					amount=(BigDecimal) row.getCell(key+"amount").getValue();
//				}
//				row.getCell(key+"amount").setValue(dateEntry.getAmount().add(amount));
//			}
//		}
		String amountColoun[]=new String[cycle*4+4];
		
		String fistKey = "";
		for(int i=0;i<cycle*4+4;i++){
			if (spMonth > 12) {
				spYear += 1;
				spMonth = 1;
			}
			String key=spYear+"year"+spMonth+"m";
			amountColoun[i]=key+"amount";
			amountColoun[i+1]=key+"reportAmount";
			amountColoun[i+2]=key+"executeAmount";
			amountColoun[i+3]=key+"houseAmount";
			
			//add by shilei
			if(i==0)
				fistKey = key;
			
			i=i+3;
			spMonth++;
		}
		amountColoun[amountColoun.length-1]=fistKey+"businessTicketAmount";
		amountColoun[amountColoun.length-2]=fistKey+"promissoryNoteAmount";
		amountColoun[amountColoun.length-3]=fistKey+"cashPaymentAmount";
		amountColoun[amountColoun.length-4]=fistKey+"otherTxAmount";
		
		TableHelper.getFootRow(this.contractTable, amountColoun);
		TableHelper.getFootRow(this.contractTable, new String[]{"monthActPayAmount","actPayAmount","lastPrice","actPayAmount","noPayAmount"});
//		CRMClientHelper.getFootRow(this.bgTable, amountColoun);
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.contractBill.programmingContract.*");
    	sel.add("entry.respPerson.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.payType.*");
    	sel.add("bizDate");
    	sel.add("amount");
    	sel.add("curProject.fullOrgUnit.id");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectMonthPlanGatherInfo info=(ProjectMonthPlanGatherInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectMonthPlanGatherInfo();
			info.setVersion(1);
			Date now=new Date();
			try {
				now=FDCCommonServerHelper.getServerTimeStamp();
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
			info.setBizDate(now);
			if((CurProjectInfo)this.getUIContext().get("curProject")!=null){
				info.setCurProject((CurProjectInfo)this.getUIContext().get("curProject"));
				try {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(info.getCurProject().getFullOrgUnit().getId()));
					info.setOrgUnit(orgUnitInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}else{
				FDCMsgBox.showWarning(this,"项目为空！");
	    		SysUtil.abort();
			}
		}else{
			info.setVersion(info.getVersion()+1);
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(BOSUuid.create(info.getEntry().get(i).getBOSType()));
				for(int j=0;j<info.getEntry().get(i).getDateEntry().size();j++){
					info.getEntry().get(i).getDateEntry().get(j).setId(BOSUuid.create(info.getEntry().get(i).getDateEntry().get(j).getBOSType()));
				}
			}
		}
		info.setVersionType(VersionTypeEnum.REPORT);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("cycle");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		try {
			FDCDataBaseCollection col = PayPlanCycleFactory.getRemoteInstance()
					.getFDCDataBaseCollection(view);
			if (col != null && col.size() > 0) {
				PayPlanCycleInfo cycle = (PayPlanCycleInfo) col.get(0);
				info.setCycle(cycle);
			} else {
				FDCMsgBox.showWarning(this, "必须启用一套付款计划周期!");
				SysUtil.abort();
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		info.setName(null);
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		info.setVersionType(null);
		return info;
	}
	
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCurProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbVersionType);
		
		//add by shilei保存校验：抵房，商票，期票，现金支付，其他 加起来之和等于上报金额
		
//		for(int i=0;i<this.contractTable.getRowCount();i++)
//		{
//			IRow row=this.contractTable.getRow(i);
//			BigDecimal amount = BigDecimal.ZERO;
//			
//			int sp = 0;
//			for(int j=0;j<this.contractTable.getColumnCount();j++)
//			{
//				String key = this.contractTable.getColumnKey(j);
//				if(key.indexOf("businessTicketAmount")>=0||key.indexOf("promissoryNoteAmount")>=0||key.indexOf("cashPaymentAmount")>=0||key.indexOf("otherTxAmount")>=0)
//				{
//					sp = j;
//					amount = amount.add(UIRuleUtil.getBigDecimal(row.getCell(key).getValue()));
//				}
//			}
//			
//			BigDecimal dfAmount = UIRuleUtil.getBigDecimal(row.getCell(sp-4).getValue());
//			BigDecimal subAmount = UIRuleUtil.getBigDecimal(row.getCell(sp-5).getValue());
//			
//			if(subAmount.compareTo(dfAmount.add(amount))!=0)
//			{
//				MsgBox.showWarning("第["+(i+1)+"]行 抵房，商票，期票，现金支付，其他 之和不等于上报金额，不允许执行此操作！\n (抵房+商票+期票+现金支付+其他 )= "+dfAmount.add(amount));
//				SysUtil.abort();
//			}
//		}
		
	}
	protected void verifyInputForSubmint() throws Exception {
		if(checkBizDate()){
			SysUtil.abort();
		}
		verifyInputForSave();
		
		int sYear = this.spYear.getIntegerVlaue().intValue();
		int sMonth = this.spMonth.getIntegerVlaue().intValue()+1;
		int circle = editData.getCycle().getCycle().getValue();
		IRow headRow=this.contractTable.getHeadRow(0);
		IRow headRowTwo=this.contractTable.getHeadRow(1);
		
		for(int i = 0; i < circle; i++) {
			if(sMonth > 12) {
				sYear += 1;
				sMonth = 1;
			}
			String Key = sYear+"year"+sMonth+"m";
			System.out.println(Key);
			sMonth++;
			for(int j = 0; j < contractTable.getRowCount(); j++) {
				IRow row = contractTable.getRow(j);
				if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
					continue;
				}
				BigDecimal amount = (BigDecimal) row.getCell(Key+"amount").getValue();
				PaymentTypeInfo payType = (PaymentTypeInfo) row.getCell(Key+"payType").getValue();
				if(amount != null) {
					if(amount.compareTo(BigDecimal.ZERO) != 0 && payType == null) {
						int index = row.getCell(Key+"payType").getColumnIndex();
						MsgBox.showWarning(headRow.getCell(index).getValue()+headRowTwo.getCell(index).getValue().toString()+"不能为空！");
						this.contractTable.getEditManager().editCellAt(j, index);
						SysUtil.abort();
					}
				}
			}
		}
		
//		IRow headRow=this.contractTable.getHeadRow(0);
//		IRow headRowTwo=this.contractTable.getHeadRow(1);
//		for(int i=0;i<this.contractTable.getRowCount();i++){
//			IRow row=this.contractTable.getRow(i);
//			if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
//				continue;
//			}
//			for(int j=0;j<this.contractTable.getColumnCount();j++){
//				if(this.contractTable.getColumn(j).isRequired()){
//					if(row.getCell(j).getValue()!=null&&row.getCell(j).getValue() instanceof String){
//						if("".equals(row.getCell(j).getValue().toString().trim())){
//							FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
//							this.contractTable.getEditManager().editCellAt(i, j);
//							SysUtil.abort();
//						}
//					}else if(row.getCell(j).getValue()==null){
//						FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
//						this.contractTable.getEditManager().editCellAt(i, j);
//						SysUtil.abort();
//					}
//				}
//			}
//		}
		checkExecuteAmount();
		checkYearAmount();
	}
	protected BigDecimal getMonthActPayAmount(String curProjectId,int year,int month) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(pb.famount) payAmount from t_cas_paymentbill pb left join T_CON_PayRequestBill prb on prb.fid=pb.fFdcPayReqID ");
		_builder.appendSql(" where pb.fbillstatus=15 and prb.fCurProjectid='"+curProjectId+"'");
		_builder.appendSql(" and pb.famount is not null and year(pb.fpayDate)="+year+" and month(pb.fpayDate)<"+month);
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected void checkExecuteAmount() throws BOSException, EASBizException, SQLException{
		this.editData.setAmount(null);
		String str=null;
		if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
			str="上报金额";
		}else{
			str="核定金额";
		}
		int nextyear=this.spYear.getIntegerVlaue();
		int nextmonth=this.spMonth.getIntegerVlaue()+1;
		if(nextmonth+1>12){
			nextyear=nextyear+1;
			nextmonth=1;
		}
		for(int i=0;i<this.contractTable.getRowCount();i++){
			ProjectMonthPlanGatherEntryInfo info=(ProjectMonthPlanGatherEntryInfo) this.contractTable.getRow(i).getUserObject();
			if(info!=null){
				this.editData.setAmount(FDCHelper.add(this.editData.getAmount(), this.contractTable.getRow(i).getCell(nextyear+"year"+nextmonth+"m"+"executeAmount").getValue()));
				BigDecimal actPayAmount=(BigDecimal) this.contractTable.getRow(i).getCell("actPayAmount").getValue();
				if(actPayAmount==null) actPayAmount=FDCHelper.ZERO;
				if(info.getContractBill()!=null&&info.getProgrammingContract()!=null){
					BigDecimal conAmount=(BigDecimal) this.contractTable.getRow(i).getCell("conAmount").getValue();
					BigDecimal checkAmount=FDCHelper.ZERO;
					int year=this.spYear.getIntegerVlaue();
					int month=this.spMonth.getIntegerVlaue();
					for(int j=0;j<1;j++){
						month=month+1;
						if(month>12){
							month=1;
							year=year+1;
						}
						String key=year+"year"+month+"m";
						if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
							checkAmount=FDCHelper.add(checkAmount,this.contractTable.getRow(i).getCell(key+"reportAmount").getValue());
						}else{
							checkAmount=FDCHelper.add(checkAmount,this.contractTable.getRow(i).getCell(key+"executeAmount").getValue());
						}
					}
					if(conAmount==null) conAmount=FDCHelper.ZERO;
					if(FDCHelper.add(actPayAmount, checkAmount).compareTo(conAmount)>0){
						FDCMsgBox.showWarning(this,"第"+(i+1)+"行,未来1个月的"+str+"+累计实付金额 不能大于合同关联合约规划金额！");
			    		SysUtil.abort();
					}
				}
				if(info.getProgrammingContract()!=null||(info.getContractBill()!=null&&info.getContractBill().getProgrammingContract()!=null)){
					BigDecimal conAmount=(BigDecimal) this.contractTable.getRow(i).getCell("conAmount").getValue();
					if(conAmount==null) conAmount=FDCHelper.ZERO;
					BigDecimal checkAmount=FDCHelper.ZERO;
					int year=this.spYear.getIntegerVlaue();
					int month=this.spMonth.getIntegerVlaue();
					for(int j=0;j<1;j++){
						month=month+1;
						if(month>12){
							month=1;
							year=year+1;
						}
						String key=year+"year"+month+"m";
						if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
							checkAmount=FDCHelper.add(checkAmount,this.contractTable.getRow(i).getCell(key+"reportAmount").getValue());
						}else{
							checkAmount=FDCHelper.add(checkAmount,this.contractTable.getRow(i).getCell(key+"executeAmount").getValue());
						}
					}
					if(FDCHelper.add(actPayAmount, checkAmount).compareTo(conAmount)>0){
						FDCMsgBox.showWarning(this,"第"+(i+1)+"行,"+str+"累计金额+累计实付金额不能大于合约规划金额！");
			    		SysUtil.abort();
					}
				}
			}
		}
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())
				&&this.txtExecuteAmount.getBigDecimalValue()!=null){
			if(this.editData.getAmount()==null||
					this.editData.getAmount().compareTo(this.txtExecuteAmount.getBigDecimalValue())>0){
				FDCMsgBox.showWarning(this,"项目月度资金计划超过事业部月度资金计划核定金额！");
	    		SysUtil.abort();
			}
		}
	}
	protected void checkYearAmount() throws BOSException, EASBizException, SQLException{
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_YEARPLAN", editData.getCurProject().getFullOrgUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		boolean isCheck=false;
		if(hmAllParam.get("CIFI_YEARPLAN")!=null){
			isCheck=Boolean.valueOf(hmAllParam.get("CIFI_YEARPLAN").toString()).booleanValue();
		}
		if(isCheck){
			int comYear=this.spYear.getIntegerVlaue().intValue();
			int comMonth=this.spMonth.getIntegerVlaue().intValue()+1;
			if(comMonth>12){
				comYear=comYear+1;
				comMonth=1;
			}
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
	    	filter.getFilterItems().add(new FilterItemInfo("year",comYear));
	    	filter.getFilterItems().add(new FilterItemInfo("amount",null,CompareType.NOTEQUALS));
	    	view.getSelector().add(new SelectorItemInfo("amount"));
	    	
	    	view.setFilter(filter);
	    	ProjectYearPlanTotalEntryCollection col=ProjectYearPlanTotalEntryFactory.getRemoteInstance().getProjectYearPlanTotalEntryCollection(view);
	    	if(col.size()>0&&this.editData.getAmount()!=null){
	    		BigDecimal actAmount=getMonthActPayAmount(this.editData.getCurProject().getId().toString(),comYear,comMonth);
	    		if(col.get(0).getAmount().compareTo(this.editData.getAmount().add(actAmount))>=0){
	    			return;
	    		}
	    	}
	    	FDCMsgBox.showWarning(this,"项目月度资金计划超过项目年度付款规划控制！");
    		SysUtil.abort();
		}
	}
	protected void checkInitVersionAmount() throws BOSException, EASBizException{
		if(this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0){
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_MONTHPLANGATHER", editData.getCurProject().getFullOrgUnit().getId().toString());
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			boolean isCheck=false;
			if(hmAllParam.get("CIFI_MONTHPLANGATHER")!=null){
				isCheck=Boolean.valueOf(hmAllParam.get("CIFI_MONTHPLANGATHER").toString()).booleanValue();
			}
			if(isCheck){
				int comYear=this.spYear.getIntegerVlaue().intValue();
				int comMonth=this.spMonth.getIntegerVlaue().intValue()+1;
				
				if(comMonth>12){
					comYear=comYear+1;
					comMonth=1;
				}
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, comYear);
				cal.set(Calendar.MONTH, comMonth-1);
				cal.set(Calendar.DATE, 1);
				Date begin=FDCDateHelper.getDayBegin(cal.getTime());
				
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
		    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
	    		filter.getFilterItems().add(new FilterItemInfo("version",1));
	    		filter.getFilterItems().add(new FilterItemInfo("amount",null,CompareType.NOTEQUALS));
	    		
		    	view.getSelector().add(new SelectorItemInfo("amount"));
		    	
		    	view.setFilter(filter);
		    	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
		    	if(col.size()>0&&this.editData.getAmount()!=null){
		    		if(col.get(0).getAmount().compareTo(this.editData.getAmount())>=0){
		    			return;
		    		}
		    	}
		    	FDCMsgBox.showWarning(this,"当月修订的月度总金额必须“小于等于”确认通过的初始版本的月度总金额！");
	    		SysUtil.abort();
			}
		}
	}
	protected boolean checkBizDate() throws EASBizException, BOSException{
		if(this.cbVersionType.getSelectedItem()==null){
			FDCMsgBox.showWarning(this,"版本类型不能为空！");
			return true;
		}
		int comYear=this.spYear.getIntegerVlaue().intValue();
		int comMonth=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, comYear);
		cal.set(Calendar.MONTH, comMonth-1);
		cal.set(Calendar.DATE, 1);
		Date begin=FDCDateHelper.getDayBegin(cal.getTime());

		VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
		if(versionType.equals(VersionTypeEnum.REPORT)){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.EXECUTE_VALUE));
			if(ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划已存在执行版！");
				return true;
			}
		}else{
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.REPORT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
	    	
			if(!ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"请先新增"+comYear+"年"+comMonth+"月项目月度资金计划上报版！");
				return true;
			}
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
    	filter.getFilterItems().add(new FilterItemInfo("versionType",versionType.getValue()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	
    	view.setFilter(filter);
    	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"还未审批！");
    		return true;
    	}else{
    		if(col.size()==0){
    			return false;
    		}else if(this.cbVersionType.isEnabled()){
    			FDCMsgBox.showWarning(this,"请对"+comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"进行修订操作！");
        		return true;
    		}else if(col.get(0).getVersion()==this.txtVersion.getIntegerValue()){
    			FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"已存在！");
        		return true;
    		}
    	}
		return false;
	}
	protected void reGetValue() throws Exception{
		if (!isLoad&&this.cbVersionType.getSelectedItem()!=null) {
			if(checkBizDate()){
//				this.cbVersionType.setSelectedItem(null);
				return;
			}
			int result = MsgBox.OK;
			if (this.contractTable.getRowCount() > 0 ) {
				result = FDCMsgBox.showConfirm2(this,"改变编制月份将根据编制周期重新调整编制表格！");
			}
			if (result == MsgBox.OK) {
//				this.bgTable.removeRows();
				this.contractTable.removeRows();
				this.btnGet_actionPerformed(null);
			} else {
//				this.cbVersionType.setSelectedItem(null);
				return;
			}
		}
	}
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
//	protected BgItemInfo getBgItem(ProgrammingContractInfo pc) throws EASBizException, BOSException{
//		if(pc==null) return null;
//		SelectorItemCollection sel=new SelectorItemCollection();
//		sel.add("costEntries.costAccount.longNumber");
//		pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()),sel);
//		Set costAccount=new HashSet();
//		for(int i=0;i<pc.getCostEntries().size();i++){
//			costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
//		}
//		return CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount);
//	}
	protected void btnGet_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getCurProject()==null){
			FDCMsgBox.showWarning(this,"工程项目为空！");
			return;
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.cbVersionType);
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.contractTable.getRowCount()>0){
       	 	isShowWarn=true;
        }
        if(isShowWarn){
       	 	if(FDCMsgBox.showConfirm2(this, "重新提取数据将会覆盖之前数据，是否继续？")== FDCMsgBox.YES){
       	 		isUpdate=true;
       	 	}
        }else{
       	 	isUpdate=true;
        }
        if(isUpdate){
        	this.editData.getEntry().clear();
        	this.storeFields();
        	
        	int comYear=this.spYear.getIntegerVlaue().intValue();
    		int comMonth=this.spMonth.getIntegerVlaue().intValue();
    		Calendar cal = Calendar.getInstance();
    		cal.set(Calendar.YEAR, comYear);
    		cal.set(Calendar.MONTH, comMonth-1);
    		cal.set(Calendar.DATE, 1);
    		
        	Date begin=FDCDateHelper.getDayBegin(cal.getTime());
        	VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
    		if(versionType.equals(VersionTypeEnum.REPORT)){
    			ProjectMonthPlanEntryCollection monthplancol=new ProjectMonthPlanEntryCollection();
            	Map contract=new HashMap();
            	Map entryMap=new HashMap();
            	
            	Set yearSet=new HashSet();
            	Set monthSet=new HashSet();
            	int spYear=this.spYear.getIntegerVlaue().intValue();
        		int spMonth=this.spMonth.getIntegerVlaue().intValue()+1;
        		int cycle=this.editData.getCycle().getCycle().getValue();
            	for(int i=0;i<cycle;i++){
        			if (spMonth > 12) {
        				spYear += 1;
        				spMonth = 1;
        			}
        			yearSet.add(spYear);
        			String key=spYear+"year"+spMonth+"m";
        			monthSet.add(key);
        			spMonth++;
            	}
            	
            	Set removeSet=new HashSet();
            	int respYear=this.spYear.getIntegerVlaue().intValue();
        		int respMonth=this.spMonth.getIntegerVlaue().intValue()+1;
            	for(int i=0;i<6;i++){
        			if (respMonth > 12) {
        				respYear += 1;
        				respMonth = 1;
        			}
        			String key=respYear+"year"+respMonth+"m";
        			removeSet.add(key);
        			respMonth++;
            	}
            	
            	EntityViewInfo view=new EntityViewInfo();
            	FilterInfo filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
//            	if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//        			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.respPerson.id", SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString()));
//        		}
            	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
            	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
            	
            	view.setFilter(filter);
            	view.getSelector().add("*");
            	view.getSelector().add("paymentType.*");
            	view.getSelector().add("head.contractBill.programmingContract.name");
            	view.getSelector().add("head.contractBill.programmingContract.amount");
            	view.getSelector().add("head.contractBill.id");
            	view.getSelector().add("head.contractBill.number");
            	view.getSelector().add("head.contractBill.name");
            	view.getSelector().add("head.contractBill.amount");
            	view.getSelector().add("head.contractBill.programmingContract.id");
            	view.getSelector().add("head.respPerson.*");
            	view.getSelector().add("head.department");
            	SorterItemCollection sort=new SorterItemCollection();
            	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
            	number.setSortType(SortType.ASCEND);
            	sort.add(number);
            	
            	view.setSorter(sort);
            	ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
            	
//            	Map bgItemMap=new HashMap();
        		for(int i=0;i<col.size();i++){
        			ContractPayPlanEntryInfo ppEntry=col.get(i);
//        			UseTypeEnum useType=ppEntry.getUseType();
        			PaymentTypeInfo paymentType = ppEntry.getPaymentType();
            		int year=ppEntry.getYear();
            		int month=ppEntry.getMonth();
            		
            		int sYear=this.spYear.getIntegerVlaue().intValue();
            		int sMonth=this.spMonth.getIntegerVlaue().intValue()+1;
            		if(sMonth > 12) {
            			sYear += 1;
            			sMonth = 1;
            		}
            		String nextMonthKey = sYear+"year"+sMonth+"m";
            		String key=year+"year"+month+"m";
            		if(!monthSet.contains(key)){
        				continue;
        			}
//            		String remark=ppEntry.getRemark();
            		String remark=ppEntry.getPayNode();
            		ContractBillInfo contractInfo=ppEntry.getHead().getContractBill();
            		String contractId=contractInfo.getId().toString();
            		
            		//计算上报金额
            		BigDecimal reportAmount = BigDecimal.ZERO;
            		if(nextMonthKey.equals(key)) {
            			BigDecimal preTotalAmount = getPreTotalAmount(contractInfo, sYear, sMonth);
                		BigDecimal actPayAmount = getActPayAmount(contractInfo, sYear, sMonth);
                		reportAmount = preTotalAmount.subtract(actPayAmount);
            		} else {
            			reportAmount = ppEntry.getPayAmount();
            		}
            		
            		BigDecimal amount=ppEntry.getPayAmount();
            		HashMap yearMap=null;
            		HashMap monthMap=null;
            		ProjectMonthPlanDateEntryInfo dateEntry=null;
            		ProjectMonthPlanEntryInfo entry=null;
            		
            		if(contract.containsKey(contractId)){
            			yearMap=(HashMap)contract.get(contractId);
            			entry=(ProjectMonthPlanEntryInfo) entryMap.get(contractId);
            			
            			if(yearMap.containsKey(year)){
            				monthMap=(HashMap)yearMap.get(year);
            				if(monthMap.containsKey(month)){
            					dateEntry=(ProjectMonthPlanDateEntryInfo) monthMap.get(month);
            					dateEntry.setAmount(dateEntry.getAmount().add(amount));
            				}else{
            					dateEntry=new ProjectMonthPlanDateEntryInfo();
            					dateEntry.setPayType(paymentType);
            					dateEntry.setAmount(amount);
            					dateEntry.setReportAmount(reportAmount);
            					dateEntry.setYear(year);
            					dateEntry.setMonth(month);
            					dateEntry.setRemark(remark);
            					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
            					
            					entry.getDateEntry().add(dateEntry);
            					monthMap.put(month, dateEntry);
            				}
            			}else{
            				monthMap=new HashMap();
            				
            				dateEntry=new ProjectMonthPlanDateEntryInfo();
//            				dateEntry.setUseType(useType);
            				dateEntry.setPayType(paymentType);
        					dateEntry.setAmount(amount);
        					dateEntry.setReportAmount(reportAmount);
        					dateEntry.setYear(year);
        					dateEntry.setMonth(month);
        					dateEntry.setRemark(remark);
        					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
        					
        					entry.getDateEntry().add(dateEntry);
        					monthMap.put(month, dateEntry);
        					
        					yearMap.put(year, monthMap);
            				
            			}
            		}else{
            			yearMap=new HashMap();
            			entry=new ProjectMonthPlanEntryInfo();
            			entry.setContractBill(contractInfo);
            			entry.setId(ppEntry.getHead().getId());
            			entry.put("department", ppEntry.getHead().getDepartment());
            			entry.put("respPerson", ppEntry.getHead().getRespPerson());
            			monthMap=new HashMap();
        				
        				dateEntry=new ProjectMonthPlanDateEntryInfo();
        				dateEntry.setPayType(paymentType);
    					dateEntry.setAmount(amount);
    					dateEntry.setReportAmount(reportAmount);
    					dateEntry.setYear(year);
    					dateEntry.setMonth(month);
    					dateEntry.setRemark(remark);
    					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    					
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(month, dateEntry);
    					
    					yearMap.put(year, monthMap);
    					
    					contract.put(contractId, yearMap);
    					
    					entryMap.put(contractId, entry);
    					
    					monthplancol.add(entry);
            		}
            	}
        		
            	entryMap=new HashMap();
            	IContractBill iContractBill = ContractBillFactory.getRemoteInstance();
        		for(int i=0;i<monthplancol.size();i++){
        			ProjectMonthPlanEntryInfo ppEntry=monthplancol.get(i);
        			String id=null;
        			if(ppEntry.getContractBill()!=null){
        				id=ppEntry.getContractBill().getId().toString();
        			}else{
        				continue;
        			}
        			ProjectMonthPlanGatherEntryInfo entry=null;
        			if(entryMap.containsKey(id)){
        				entry=(ProjectMonthPlanGatherEntryInfo) entryMap.get(id);
        				for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				boolean isAddNew=true;
            				for(int k=0;k<entry.getDateEntry().size();k++){
            					if(entry.getDateEntry().get(k).getYear()==dEntry.getYear()&&entry.getDateEntry().get(k).getMonth()==dEntry.getMonth()){
            						BigDecimal amount=FDCHelper.ZERO;
            						BigDecimal addAmount=FDCHelper.ZERO;
            						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
            						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
            						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
            						
            						isAddNew=false;
            						break;
            					}
            				}
            				if(isAddNew){
            					ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
                				gdEntry.setAmount(dEntry.getAmount());
//                				gdEntry.setReportAmount(dEntry.getAmount());
                				gdEntry.setReportAmount(dEntry.getReportAmount());
                				gdEntry.setYear(dEntry.getYear());
                				gdEntry.setMonth(dEntry.getMonth());
                				gdEntry.setRemark(dEntry.getRemark());
                				gdEntry.setPayType(dEntry.getPayType());
                				gdEntry.setCashPayment(dEntry.getReportAmount());
                				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
                				
                				entry.getDateEntry().add(gdEntry);
            				}
            			}
        			}else{
        				entry=new ProjectMonthPlanGatherEntryInfo();
        				entry.setContractBill(ppEntry.getContractBill());
        				entry.setName(ppEntry.getContractBill().getName());
        				
        				SelectorItemCollection sic = new SelectorItemCollection();
        				sic.add(new SelectorItemInfo("*"));
        				sic.add(new SelectorItemInfo("partB.id"));
        				
        				ContractBillInfo contractBillInfo = iContractBill.getContractBillInfo(new ObjectUuidPK(ppEntry.getContractBill().getId()), sic);
        				entry.setSupplier(contractBillInfo.getPartB());//供应商
        				entry.setId(BOSUuid.create(entry.getBOSType()));
        				entry.setSrcId(ppEntry.getId());
        				if(ppEntry.get("department")!=null)
        					entry.setDepartment((DepartmentEnum) ppEntry.get("department"));
        				if(ppEntry.get("respPerson")!=null)
        					entry.setRespPerson((PersonInfo) ppEntry.get("respPerson"));
        				
        				BigDecimal amount=FDCHelper.ZERO;
            			for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
//            				gdEntry.setReportAmount(dEntry.getAmount());
            				gdEntry.setReportAmount(dEntry.getReportAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setRemark(dEntry.getRemark());
//            				gdEntry.setUseType(dEntry.getUseType());
            				gdEntry.setPayType(dEntry.getPayType());
            				gdEntry.setCashPayment(dEntry.getReportAmount());
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				String key=dEntry.getYear()+"year"+dEntry.getMonth()+"m";
            				if(removeSet.contains(key)){
            					amount=FDCHelper.add(amount, dEntry.getAmount());
            				}
            				entry.getDateEntry().add(gdEntry);
            			}
            			if(amount.compareTo(FDCHelper.ZERO)==0){
            				continue;
            			}
            			this.editData.getEntry().add(entry);
            			
            			entryMap.put(ppEntry.getContractBill().getId().toString(), entry);
        			}
        		}
            	
        		view=new EntityViewInfo();
            	filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
            	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.bizDate",begin));
            	
            	view.setFilter(filter);
            	view.getSelector().add("dateEntry.*");
            	view.getSelector().add("dateEntry.payType.*");
            	view.getSelector().add("programmingContract.id");
            	view.getSelector().add("programmingContract.number");
            	view.getSelector().add("programmingContract.name");
            	view.getSelector().add("programmingContract.amount");
            	view.getSelector().add("programmingContract.balance");
            	view.getSelector().add("name");
            	view.getSelector().add("amount");
            	view.getSelector().add("head.respPerson.*");
            	view.getSelector().add("head.department");
            	sort=new SorterItemCollection();
            	SorterItemInfo seq=new SorterItemInfo("seq");
            	seq.setSortType(SortType.ASCEND);
            	SorterItemInfo dateSeq=new SorterItemInfo("dateEntry.seq");
            	dateSeq.setSortType(SortType.ASCEND);
            	sort.add(dateSeq);
            	
            	view.setSorter(sort);
            	ProjectMonthPlanProEntryCollection proCol=ProjectMonthPlanProEntryFactory.getRemoteInstance().getProjectMonthPlanProEntryCollection(view);
            	
            	entryMap=new HashMap();
        		for(int i=0;i<proCol.size();i++){
        			ProjectMonthPlanProEntryInfo ppEntry=proCol.get(i);
        			String id=ppEntry.getId().toString();
        			ProjectMonthPlanGatherEntryInfo entry=null;
        			if(entryMap.containsKey(id)){
        				entry=(ProjectMonthPlanGatherEntryInfo) entryMap.get(id);
        				for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				boolean isAddNew=true;
            				for(int k=0;k<entry.getDateEntry().size();k++){
            					if(entry.getDateEntry().get(k).getYear()==dEntry.getYear()&&entry.getDateEntry().get(k).getMonth()==dEntry.getMonth()){
            						BigDecimal amount=FDCHelper.ZERO;
            						BigDecimal addAmount=FDCHelper.ZERO;
            						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
            						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
            						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
            						
            						isAddNew=false;
            						break;
            					}
            				}
            				if(isAddNew){
            					ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
                				gdEntry.setAmount(dEntry.getAmount());
                				gdEntry.setReportAmount(dEntry.getAmount());
                				gdEntry.setYear(dEntry.getYear());
                				gdEntry.setMonth(dEntry.getMonth());
                				gdEntry.setRemark(dEntry.getRemark());
//                				gdEntry.setUseType(dEntry.getUseType());
                				gdEntry.setPayType(dEntry.getPayType());
                				gdEntry.setCashPayment(dEntry.getAmount());
                				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
                				
                				entry.getDateEntry().add(gdEntry);
            				}
            			}
        			}else{
        				entry=new ProjectMonthPlanGatherEntryInfo();
            			entry.setProgrammingContract(ppEntry.getProgrammingContract());
            			entry.setName(ppEntry.getName());
            			entry.setDepartment(ppEntry.getHead().getDepartment());
            			entry.setRespPerson(ppEntry.getHead().getRespPerson());
            			entry.setId(BOSUuid.create(entry.getBOSType()));
            			entry.setSrcId(ppEntry.getHead().getId());
            			BigDecimal amount=FDCHelper.ZERO;
            			for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setReportAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setRemark(dEntry.getRemark());
//            				gdEntry.setUseType(dEntry.getUseType());
            				gdEntry.setPayType(dEntry.getPayType());
            				gdEntry.setCashPayment(dEntry.getAmount());
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				String key=dEntry.getYear()+"year"+dEntry.getMonth()+"m";
            				if(removeSet.contains(key)){
            					amount=FDCHelper.add(amount, dEntry.getAmount());
            				}
            				entry.getDateEntry().add(gdEntry);
            			}
            			if(amount.compareTo(FDCHelper.ZERO)==0){
            				continue;
            			}
            			this.editData.getEntry().add(entry);
            			
            			entryMap.put(id, entry);
        			}
        		}
        		//获取大总包合同数据
        		ContractBillInfo currentDZBContract = getCurrentDZBContract();
//        		ContractBillCollection currentFBContract = getCurrentFBContract();
        		
        		ContractPayPlanEntryCollection contractPayPlanEntryColl = getFBContractPayPlanEntryColl(yearSet);
        		if(contractPayPlanEntryColl.size() > 0) {
        			isHasFbDetail = true;
        			Map planMap = new TreeMap();
        			for(int i = 0; i < contractPayPlanEntryColl.size(); i++) {
        				ContractPayPlanEntryInfo entryInfo = contractPayPlanEntryColl.get(i);
        				int year = entryInfo.getYear();
        				int month = entryInfo.getMonth();
        				String key=year+"year"+month+"m";
        				if(!planMap.containsKey(key)) {
        					Map map = new HashMap();
        					map.put("amount", entryInfo.getPayAmount());
        					map.put("paymentType", entryInfo.getPaymentType());
        					map.put("year", year);
        					map.put("month", month);
        					planMap.put(key, map);
        				} else {
        					Map map = (Map) planMap.get(key);
        					BigDecimal amount = (BigDecimal) map.get("amount");
        					map.put("amount", amount.add(entryInfo.getPayAmount()));
        				}
        			}
        			Set keySet = planMap.keySet();
        			Iterator it = keySet.iterator();
        			ProjectMonthPlanGatherEntryInfo entryInfo = new ProjectMonthPlanGatherEntryInfo();
        			if(currentDZBContract != null) {
        				entryInfo.setName(currentDZBContract.getName());
//        			entryInfo.setContractBill(currentDZBContract);
        				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
        				int sYear=this.spYear.getIntegerVlaue().intValue();
        				int sMonth=this.spMonth.getIntegerVlaue().intValue()+1;
        				if(sMonth > 12) {
        					sYear += 1;
        					sMonth = 1;
        				}
        				String nextMonthKey = sYear+"year"+sMonth+"m";
        				while(it.hasNext()) {
        					Map map = (Map) planMap.get(it.next().toString());
        					BigDecimal amount = (BigDecimal) map.get("amount");
        					Integer year = (Integer) map.get("year");
        					Integer month = (Integer) map.get("month");
        					BigDecimal reportAmount = amount;
        					String key = year.intValue()+"year"+month.intValue()+"m";
        					if(key.equals(nextMonthKey)) {
        						BigDecimal preFBTotalAmount = getPreFBTotalAmount(editData.getCurProject(), sYear, sMonth);
        						BigDecimal fbactPayAmount = getFBActPayAmount(editData.getCurProject(), sYear, sMonth);
        						reportAmount = preFBTotalAmount.subtract(fbactPayAmount);
        					}
        					PaymentTypeInfo payType = (PaymentTypeInfo) map.get("paymentType");
        					ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
        					gdEntry.setYear(year);
        					gdEntry.setMonth(month);
        					gdEntry.setAmount(amount.multiply(new BigDecimal("0.1")));
        					gdEntry.setPayType(payType);
        					gdEntry.setReportAmount(reportAmount.multiply(new BigDecimal("0.1")));
        					gdEntry.setCashPayment(reportAmount.multiply(new BigDecimal("0.1")));
        					gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
        					
        					entryInfo.getDateEntry().add(gdEntry);
        				}
        				editData.getEntry().add(entryInfo);
        			}
        		}
    		}else{
    			EntityViewInfo view=new EntityViewInfo();
    			FilterInfo filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
            	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
            	filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.REPORT_VALUE));
            	
            	view.setFilter(filter);
            	SelectorItemCollection sel=new SelectorItemCollection();
             	sel.add("entry.*");
             	sel.add("entry.contractBill.programmingContract.*");
            	sel.add("entry.contractBill.*");
            	sel.add("entry.respPerson.*");
            	sel.add("entry.programmingContract.*");
            	sel.add("entry.dateEntry.*");
            	sel.add("entry.dateEntry.payType.*");
            	view.setSelector(sel);
            	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
            	if(col.size()>1){
            		FDCMsgBox.showWarning(this,"存在多个上报版本！");
            	}else if(col.size()==0){
            		FDCMsgBox.showWarning(this,"不存在上报版本！");
            	}else{
            		for(int i=0;i<col.get(0).getEntry().size();i++){
            			ProjectMonthPlanGatherEntryInfo entry=col.get(0).getEntry().get(i);
            			entry.setId(BOSUuid.create(entry.getBOSType()));
            			entry.setSrcId(col.get(0).getId());
            			
            			for(int j=0;j<entry.getDateEntry().size();j++){
            				ProjectMonthPlanGatherDateEntryInfo dateEntry=entry.getDateEntry().get(j);
            				dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
            				dateEntry.setExecuteAmount(dateEntry.getReportAmount());
            				dateEntry.setHouseAmount(dateEntry.getHouseAmount());
            			}
            			this.editData.getEntry().add(entry);
            		}
            	}
    		}
        	this.loadEntry();
		}
	}
	
	/**
	 * 获取截止到当前年月后一个月（包含后一月）之前的所有计划金额
	 */
	private BigDecimal getPreTotalAmount(ContractBillInfo info, int year, int month) {
		BigDecimal amount = BigDecimal.ZERO;
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.id",info.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
    	
    	filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.LESS));
    	
    	filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("month", month, CompareType.LESS_EQUALS));
    	
    	filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or (#5 and #6))");
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	view.getSelector().add("paymentType.*");
    	view.getSelector().add("head.contractBill.programmingContract.name");
    	view.getSelector().add("head.contractBill.programmingContract.amount");
    	view.getSelector().add("head.contractBill.id");
    	view.getSelector().add("head.contractBill.number");
    	view.getSelector().add("head.contractBill.name");
    	view.getSelector().add("head.contractBill.amount");
    	view.getSelector().add("head.contractBill.programmingContract.id");
    	view.getSelector().add("head.respPerson.*");
    	view.getSelector().add("head.department");
    	SorterItemCollection sort=new SorterItemCollection();
    	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
    	number.setSortType(SortType.ASCEND);
    	sort.add(number);
    	
    	view.setSorter(sort);
    	try {
			ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
			for(int i = 0; i< col.size(); i++) {
				BigDecimal payAmount = col.get(i).getPayAmount();
				amount = amount.add(payAmount);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	
    	
    	return amount;
	}
	/**
	 * 获取某项目分包合同截止到当前年月后一个月（包含后一月）之前的所有计划金额
	 */
	private BigDecimal getPreFBTotalAmount(CurProjectInfo info, int year, int month) {
		BigDecimal amount = BigDecimal.ZERO;
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("head.contractBill.curProject.id",info.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
    	
    	filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.LESS));
    	
    	filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("month", month, CompareType.LESS_EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("head.contractBill.contractType.longnumber", "fb%", CompareType.LIKE));
    	
    	filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or (#5 and #6)) and #7");
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	view.getSelector().add("paymentType.*");
    	view.getSelector().add("head.contractBill.programmingContract.name");
    	view.getSelector().add("head.contractBill.programmingContract.amount");
    	view.getSelector().add("head.contractBill.id");
    	view.getSelector().add("head.contractBill.contractType.id");
    	view.getSelector().add("head.contractBill.contractType.number");
    	view.getSelector().add("head.contractBill.contractType.longnumber");
    	view.getSelector().add("head.contractBill.curProject.id");
    	view.getSelector().add("head.contractBill.number");
    	view.getSelector().add("head.contractBill.name");
    	view.getSelector().add("head.contractBill.amount");
    	view.getSelector().add("head.contractBill.programmingContract.id");
    	view.getSelector().add("head.respPerson.*");
    	view.getSelector().add("head.department");
    	SorterItemCollection sort=new SorterItemCollection();
    	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
    	number.setSortType(SortType.ASCEND);
    	sort.add(number);
    	
    	view.setSorter(sort);
    	try {
			ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
			for(int i = 0; i< col.size(); i++) {
				BigDecimal payAmount = col.get(i).getPayAmount();
				amount = amount.add(payAmount);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	
    	
    	return amount;
	}
	/**
	 * 获取合同累计付款（截止到当前月底）
	 * @param info
	 * @return
	 */
	private BigDecimal getActPayAmount(ContractBillInfo info, int year, int month) {
		BigDecimal actAmount = BigDecimal.ZERO;
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+info.getId().toString()+"'");
		_builder.appendSql(" and fpayDate is not null and famount is not null");
		_builder.appendSql(" and fpayDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'}");
		try {
			IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				actAmount=rowSet.getBigDecimal("payAmount") == null ? BigDecimal.ZERO : rowSet.getBigDecimal("payAmount");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actAmount;
	}
	/**
	 * 获取分包合同累计付款（截止到当前月底）
	 * @param info
	 * @return
	 */
	private BigDecimal getFBActPayAmount(CurProjectInfo info, int year, int month) {
		BigDecimal actAmount = BigDecimal.ZERO;
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(bill.famount) payAmount from t_cas_paymentbill bill");
		_builder.appendSql(" left join t_con_contractbill contract on contract.fid=bill.fcontractbillid");
		_builder.appendSql(" left join T_CON_ContractPayPlan conpayplan on conpayplan.FContractBillId=contract.fid");
		_builder.appendSql(" left join t_fdc_curproject project on project.fid=contract.fcurprojectid");
		_builder.appendSql(" left join t_Fdc_Contracttype type on type.fid=contract.fcontracttypeid");
		_builder.appendSql(" where bill.fbillstatus=15 and type.flongnumber like 'fb%'");
		_builder.appendSql(" and project.fid='"+info.getId().toString()+"'");
		_builder.appendSql(" and conpayplan.fid is not null and conpayplan.fislatest='1'");
		_builder.appendSql(" and bill.fpayDate is not null and bill.famount is not null");
		_builder.appendSql(" and bill.fpayDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'}");
		
//		_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+info.getId().toString()+"'");
//		_builder.appendSql(" and fpayDate is not null and famount is not null");
//		_builder.appendSql(" and fpayDate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getFirstDayOfMonth(cal.getTime())))+ "'}");
		try {
			IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				actAmount=rowSet.getBigDecimal("payAmount") == null ? BigDecimal.ZERO : rowSet.getBigDecimal("payAmount");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actAmount;
	}
	/**
	 * 获取最新版项目月度计划每月上报金额,按合同付款计划
	 */
	private Map getReportAmount() {
		Map reportMap = new HashMap();
    	try {
    		int years=this.spYear.getIntegerVlaue().intValue();
    		int months=this.spMonth.getIntegerVlaue().intValue();
    		Calendar cal = Calendar.getInstance();
    		cal.set(Calendar.YEAR, years);
    		cal.set(Calendar.MONTH, months-2);
    		cal.set(Calendar.DATE, 1);
    		cal.set(Calendar.HOUR_OF_DAY, 0);
    		cal.set(Calendar.MINUTE, 0);
    		cal.set(Calendar.SECOND, 0);
    		cal.set(Calendar.MILLISECOND, 0);
    		
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		view.setFilter(filter);
//    		view.getSelector().add(new SelectorItemInfo("*.*"));
//    		view.getSelector().add(new SelectorItemInfo("dateEntry.*"));
    		filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
    		filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    		filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    		filter.getFilterItems().add(new FilterItemInfo("head.bizDate", cal.getTime()));
			ProjectMonthPlanGatherEntryCollection col = ProjectMonthPlanGatherEntryFactory.getRemoteInstance().getProjectMonthPlanGatherEntryCollection(view);
			for(int i = 0; i < col.size(); i++) {
				ProjectMonthPlanGatherEntryInfo entryInfo = col.get(i);
				ProjectMonthPlanGatherDateEntryCollection dateEntry = entryInfo.getDateEntry();
				Map amountMap = new HashMap();
				for(int j = 0; j < dateEntry.size(); j++) {
					ProjectMonthPlanGatherDateEntryInfo dateEntryInfo = dateEntry.get(j);
					int year = dateEntryInfo.getYear();
					int month = dateEntryInfo.getMonth();
					String key=year+"year"+month+"m";
					BigDecimal reportAmount = dateEntryInfo.getReportAmount();					
					amountMap.put(key, reportAmount);
				}
				if(entryInfo.getSrcId() != null) {
					reportMap.put(entryInfo.getSrcId().toString(), amountMap);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return reportMap;
	}
	/**
	 * 获取当前项目下的大总包合同(一个项目只有一个大总包合同)
	 * @throws BOSException
	 */
	private ContractBillInfo getCurrentDZBContract() {
		ContractBillInfo info = null;
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("contractType.longnumber","zb%", CompareType.LIKE));
    	try {
    		ContractBillCollection coll = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
    		info = coll.get(0);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * 获取某项目的分包合同
	 * @return
	 */
	private ContractBillCollection getCurrentFBContract() {
		ContractBillCollection coll = null;
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("contractType.longnumber","fb%", CompareType.LIKE));
    	
    	try {
    		coll = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll;
	}
	/**
	 * 根据分包合同获取合同付款计划信息
	 * @param info
	 * @return
	 */
	private ContractPayPlanEntryCollection getFBContractPayPlanEntryColl(Set yearSet) {
		ContractPayPlanEntryCollection coll = null;
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("head.contractBill.contractType.longNumber","fb%", CompareType.LIKE));
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	view.getSelector().add("paymentType.*");
    	view.getSelector().add("head.contractBill.programmingContract.name");
    	view.getSelector().add("head.contractBill.programmingContract.amount");
    	view.getSelector().add("head.contractBill.id");
    	view.getSelector().add("head.contractBill.number");
    	view.getSelector().add("head.contractBill.name");
    	view.getSelector().add("head.contractBill.amount");
    	view.getSelector().add("head.contractBill.contractType.number");
    	view.getSelector().add("head.contractBill.contractType.name");
    	view.getSelector().add("head.contractBill.contractType.longNumber");
    	view.getSelector().add("head.contractBill.programmingContract.id");
    	view.getSelector().add("head.respPerson.*");
    	view.getSelector().add("head.department");
    	SorterItemCollection sort=new SorterItemCollection();
    	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
    	number.setSortType(SortType.ASCEND);
    	sort.add(number);
    	
    	view.setSorter(sort);
    	try {
			coll=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return coll;
	}
	/**
	 * 根据合同获取合同付款计划信息
	 * @param info
	 * @return
	 */
	private ContractPayPlanEntryCollection getContractPayPlanEntryColl(Set yearSet) {
		ContractPayPlanEntryCollection coll = null;
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	view.getSelector().add("paymentType.*");
    	view.getSelector().add("head.contractBill.programmingContract.name");
    	view.getSelector().add("head.contractBill.programmingContract.amount");
    	view.getSelector().add("head.contractBill.id");
    	view.getSelector().add("head.contractBill.number");
    	view.getSelector().add("head.contractBill.name");
    	view.getSelector().add("head.contractBill.amount");
    	view.getSelector().add("head.contractBill.contractType.number");
    	view.getSelector().add("head.contractBill.contractType.name");
    	view.getSelector().add("head.contractBill.programmingContract.id");
    	view.getSelector().add("head.respPerson.*");
    	view.getSelector().add("head.department");
    	SorterItemCollection sort=new SorterItemCollection();
    	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
    	number.setSortType(SortType.ASCEND);
    	sort.add(number);
    	
    	view.setSorter(sort);
    	try {
			coll=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return coll;
	}
	protected void getExecuteAmount() throws BOSException{
//    	EntityViewInfo view=new EntityViewInfo();
//    	FilterInfo filter=new FilterInfo();
//    	
//    	int comYear=this.spYear.getIntegerVlaue().intValue();
//		int comMonth=this.spMonth.getIntegerVlaue().intValue()+1;
//		
//		if(comMonth>12){
//			comYear=comYear+1;
//			comMonth=1;
//		}
//    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.bizDate",this.editData.getBizDate()));
//    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.versionType",VersionTypeEnum.EXECUTE_VALUE));
//    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.state",FDCBillStateEnum.AUDITTED_VALUE));
//    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.isLatest",Boolean.TRUE));
//    	filter.getFilterItems().add(new FilterItemInfo("headEntry.curProject.id",this.editData.getCurProject().getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("year",comYear));
//    	filter.getFilterItems().add(new FilterItemInfo("month",comMonth));
//    	view.setFilter(filter);
//    	view.getSelector().add(new SelectorItemInfo("executeAmount"));
//    	
//    	OrgUnitMonthPlanGatherDateEntryCollection col=OrgUnitMonthPlanGatherDateEntryFactory.getRemoteInstance().getOrgUnitMonthPlanGatherDateEntryCollection(view);
//    	if(col.size()>0){
//    		this.txtExecuteAmount.setValue(col.get(0).getExecuteAmount());
//    	}else{
//    		this.txtExecuteAmount.setValue(FDCHelper.ZERO);
//    	}
	}
	private void setColoumHide(){
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
			this.contExecuteAmount.setVisible(true);
		}else{
			this.contExecuteAmount.setVisible(false);
		}
		if(this.reportColoum!=null){
			for(int i=0;i<this.reportColoum.size();i++){
				IColumn coloum=(IColumn) this.reportColoum.get(i);
				if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(false);
					coloum.getStyleAttributes().setLocked(false);
					coloum.setRequired(true);
				}else if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(false);
					coloum.getStyleAttributes().setLocked(true);
					coloum.setRequired(false);
				}else{
					coloum.getStyleAttributes().setHided(true);
				}
			}
		}
		if(this.executeColoum!=null){
			for(int i=0;i<this.executeColoum.size();i++){
				IColumn coloum=(IColumn) this.executeColoum.get(i);
				if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(true);
					coloum.getStyleAttributes().setLocked(true);
					coloum.setRequired(false);
				}else if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
					String key=this.spYear.getIntegerVlaue()+"year"+(this.spMonth.getIntegerVlaue()+1)+"m"+"executeAmount";
					if(coloum.getKey().equals(key)){
						coloum.getStyleAttributes().setHided(false);
						coloum.getStyleAttributes().setLocked(false);
						coloum.setRequired(true);
					}else{
						coloum.getStyleAttributes().setHided(true);
						coloum.getStyleAttributes().setLocked(true);
						coloum.setRequired(false);
					}
				}else{
					coloum.getStyleAttributes().setHided(true);
				}
			}
		}
	}
	protected void cbVersionType_itemStateChanged(ItemEvent e) throws Exception {
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}else{
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		}
		if(!isLoad&&this.cbVersionType.getSelectedItem()!=null){
			if(checkBizDate()){
				this.cbVersionType.setSelectedItem(null);
				return;
			}
			int result = MsgBox.OK;
			if (this.contractTable.getRowCount() > 0) {
				result = FDCMsgBox.showConfirm2(this,"改变版本类型将根据编制周期重新调整编制表格！");
			}
			if (result == MsgBox.OK) {
//				this.bgTable.removeRows();
				this.contractTable.removeRows();
				this.btnGet_actionPerformed(null);
			} else {
				this.cbVersionType.setSelectedItem(null);
				return;
			}
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.contractTable;
		IRow row = table.addRow();
		ProjectMonthPlanGatherEntryInfo entry = new ProjectMonthPlanGatherEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue()+1;
		int cycle=this.editData.getCycle().getCycle().getValue();
		
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String key=year+"year"+month+"m";
			
			ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setExecuteAmount(FDCHelper.ZERO);

			if(row.getCell(key+"executeAmount")!=null){
				row.getCell(key+"executeAmount").setUserObject(dateEntry);
				row.getCell(key+"executeAmount").setValue(FDCHelper.ZERO);
			}
			if(row.getCell(key+"remark")!=null){
				row.getCell(key+"remark").setUserObject(dateEntry);
			}
			if(row.getCell(key+"useType")!=null){
				row.getCell(key+"useType").setUserObject(dateEntry);
			}
			entry.getDateEntry().add(dateEntry);
			month++;
		}
		this.editData.getEntry().add(entry);
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.contractTable;
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)){
				row = table.addRow();
			}else{
				if(table.getRow(top).getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
					FDCMsgBox.showInfo(this,"没有选中分录，无法插入！");
					return;
				}
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}
		ProjectMonthPlanGatherEntryInfo entry = new ProjectMonthPlanGatherEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue()+1;
		int cycle=this.editData.getCycle().getCycle().getValue();
		
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String key=year+"year"+month+"m";
			
			ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setExecuteAmount(FDCHelper.ZERO);
			
			if(row.getCell(key+"executeAmount")!=null){
				row.getCell(key+"executeAmount").setUserObject(dateEntry);
				row.getCell(key+"executeAmount").setValue(FDCHelper.ZERO);
			}
			if(row.getCell(key+"remark")!=null){
				row.getCell(key+"remark").setUserObject(dateEntry);
			}
			if(row.getCell(key+"useType")!=null){
				row.getCell(key+"useType").setUserObject(dateEntry);
			}
			entry.getDateEntry().add(dateEntry);
			month++;
		}
		this.editData.getEntry().add(entry);
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.contractTable;
		if (table.getSelectManager().size() == 0 || isTableColumnSelected(table)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = table.getSelectManager().get().getBeginRow();
			int bottom = table.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (table.getRow(top) == null||table.getRow(top).getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				ProjectMonthPlanGatherEntryInfo topValue=(ProjectMonthPlanGatherEntryInfo) table.getRow(top).getUserObject();
				table.removeRow(top);
				
				if(this.editData.getEntry().contains(topValue)){
					this.editData.getEntry().removeObject(topValue);
				}
				
				int spYear=this.spYear.getIntegerVlaue().intValue();
				int spMonth=this.spMonth.getIntegerVlaue().intValue()+1;
				int cycle=this.editData.getCycle().getCycle().getValue();
				
				String amountColoun[]=new String[cycle*4+4];
				String fistKey = "";
				for(int k=0;k<cycle*4+4;k++){
					if (spMonth > 12) {
						spYear += 1;
						spMonth = 1;
					}
					String key=spYear+"year"+spMonth+"m";
					amountColoun[k]=key+"amount";
					amountColoun[k+1]=key+"reportAmount";
					amountColoun[k+2]=key+"executeAmount";
					amountColoun[k+3]=key+"houseAmount";
					if(k==0)
						fistKey= key;
					
					k=k+3;
					spMonth++;
				}
				amountColoun[amountColoun.length-1]=fistKey+"businessTicketAmount";
				amountColoun[amountColoun.length-2]=fistKey+"promissoryNoteAmount";
				amountColoun[amountColoun.length-3]=fistKey+"cashPaymentAmount";
				amountColoun[amountColoun.length-4]=fistKey+"otherTxAmount";
				
				TableHelper.getFootRow(this.contractTable, amountColoun);
				TableHelper.getFootRow(this.contractTable, new String[]{"monthActPayAmount","actPayAmount","lastPrice","actPayAmount","noPayAmount"});
			}
		}
	}
	
}