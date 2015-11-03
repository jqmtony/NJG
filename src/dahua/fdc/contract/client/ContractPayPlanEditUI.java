/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;
import org.jgroups.jmx.protocols.FD;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.UseTypeEnum;
//import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryCollection;
//import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanFactory;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.fdc.finance.utils.TableHelper;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fi.gl.common.KDSpinnerCellEditor;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ContractPayPlanEditUI extends AbstractContractPayPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanEditUI.class);
    private boolean canSelectOtherOrgPerson = false;
    public ContractPayPlanEditUI() throws Exception
    {
        super();
    }

    protected void attachListeners() {
//    	addDataChangeListener(this.prmtRespDept);
    	addDataChangeListener(this.prmtRespPerson);
    	addDataChangeListener(this.pkBizDate);
	}
	protected void detachListeners() {
//		removeDataChangeListener(this.prmtRespDept);
		removeDataChangeListener(this.prmtRespPerson);
		removeDataChangeListener(this.pkBizDate);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractPayPlanFactory.getRemoteInstance();
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
		
		actionAudit_actionPerformed(e);
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
	protected void initControl() throws EASBizException, BOSException {
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
		
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.btnSubmit.setText("提交&审批");
		this.btnSubmit.setToolTipText("提交&审批");
		this.txtVersion.setPrecision(1);
		
		String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		}
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
//		FDCClientUtils.setRespDeptF7(prmtRespDept, this,canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId);
		
		this.actionModify.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_duizsetting"));
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}
	}
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getContractBill()!=null){
			this.txtContractInfo.setText(this.editData.getContractBill().getNumber() + " " + editData.getContractBill().getName());
			this.txtProj.setText(this.editData.getContractBill().getCurProject().getDisplayName());
			this.txtOrg.setText(this.editData.getContractBill().getOrgUnit().getDisplayName());
			this.txtAmount.setValue(this.editData.getContractBill().getAmount());
			try {
				this.txtLastAmount.setValue(FDCUtils.getContractLastAmt (null,this.editData.getContractBill().getId().toString()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			} 
		}
		loadEntry();
		initContract();
		
		if(this.txtVersion.getIntegerValue().intValue()>1){
			this.prmtRespPerson.setEnabled(false);
//			this.prmtRespDept.setEnabled(false);
			this.cbDepartment.setEnabled(false);
		}
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	private void initContract(){
		if(editData.getContractBill()==null) return;
		UIContext uiContext = new UIContext(this);
		try {
			uiContext.put("ID", editData.getContractBill().getId().toString());
			ContractBillEditUI	ui = (ContractBillEditUI) UIFactoryHelper.initUIObject(ContractBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
			this.contract.setViewportView(ui);
			this.contract.setKeyBoardControl(true);
			this.contract.setEnabled(false);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}
	protected void fetchInitData() throws Exception {
		
	}
	protected void initTable() {
		this.kdtEntry.checkParsed();
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < UseTypeEnum.getEnumList().size(); i++){
        	combo.addItem(UseTypeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("useType").setEditor(comboEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("payRate").setEditor(amountEditor);
		this.kdtEntry.getColumn("payRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("payRate").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtEntry.getColumn("payAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("payAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("payAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		KDBizPromptBox bizPayTypeBox = new KDBizPromptBox();
		bizPayTypeBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		bizPayTypeBox.setDisplayFormat("$name$");
		bizPayTypeBox.setCommitFormat("$number$");
		KDTDefaultCellEditor payTypeEditor=new KDTDefaultCellEditor(bizPayTypeBox);
		this.kdtEntry.getColumn("payType").setEditor(payTypeEditor);
		
		this.kdtEntry.getColumn("useType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtEntry.getColumn("year").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtEntry.getColumn("month").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtEntry.getColumn("actPayRate").setEditor(amountEditor);
		this.kdtEntry.getColumn("actPayRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("actPayRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("actPayRate").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("actPayRate").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		this.kdtEntry.getColumn("actPayAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("actPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("actPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("actPayAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("actPayAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		this.kdtEntry.getColumn("checkPayAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("checkPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("checkPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("checkPayAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("checkPayAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		this.kdtEntry.getColumn("checkPayAmount").getStyleAttributes().setHided(true);
		
		KDTextField remark = new KDTextField();
		remark.setMaxLength(255);
		KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(remark);
		this.kdtEntry.getColumn("remark").setEditor(remarkEditor);
		this.kdtEntry.getHeadRow(0).getCell("remark").setValue("用款说明");
		
		this.kdtEntry.getColumn("useType").getStyleAttributes().setHided(true);
		
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
//		this.kdtEntry.getColumn("bgItem").setEditor(f7Editor);
		
		this.kdtAct.checkParsed();
		this.kdtAct.getColumn("date").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.kdtAct.getColumn("date").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		this.kdtAct.getColumn("payAmount").setEditor(amountEditor);
		this.kdtAct.getColumn("payAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtAct.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtAct.getColumn("payAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		this.kdtAct.getColumn("payRate").setEditor(amountEditor);
		this.kdtAct.getColumn("payRate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtAct.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtAct.getColumn("payRate").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		this.kdtAct.getStyleAttributes().setLocked(true);
		
		ActionMap actionMap = this.kdtEntry.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
	}
	protected void pkBizDate_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal actAmount=FDCHelper.ZERO;
		BigDecimal nextActAmount=FDCHelper.ZERO;
		if(this.pkBizDate.getValue()!=null){
			Calendar bizcal = Calendar.getInstance();
			bizcal.setTime((Date) this.pkBizDate.getValue());
			int bizyear=bizcal.get(Calendar.YEAR);
			int bizmonth=bizcal.get(Calendar.MONTH)+1;
			
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+this.editData.getContractBill().getId().toString()+"'");
			_builder.appendSql(" and fpayDate is not null and famount is not null");
			_builder.appendSql(" and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth((Date) this.pkBizDate.getValue())))+ "'}");
    		
			final IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				actAmount=rowSet.getBigDecimal("payAmount");
			}
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				int comYear=Integer.parseInt(this.kdtEntry.getRow(i).getCell("year").getValue().toString());
				int comMonth=Integer.parseInt(this.kdtEntry.getRow(i).getCell("month").getValue().toString());
				
				if(comYear==bizyear&&comMonth==bizmonth+1){
					nextActAmount=FDCHelper.add(actAmount, this.kdtEntry.getRow(i).getCell("payAmount").getValue());
				}
			}
		}
		this.txtActAmount.setValue(actAmount);
		this.txtRate.setValue(FDCHelper.divide(actAmount, this.txtLastAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
		
		this.txtNextActAmount.setValue(nextActAmount);
		this.txtNextRate.setValue(FDCHelper.divide(nextActAmount, this.txtLastAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
	}

	protected void loadEntry(){
		this.kdtAct.removeRows();
		BigDecimal amount=FDCHelper.toBigDecimal(this.txtLastAmount.getNumberValue(),2);
		
		if(this.editData.getContractBill()==null||amount==null||amount.compareTo(FDCHelper.ZERO)==0) return;

		try {
			Calendar bizcal = Calendar.getInstance();
			bizcal.setTime(this.editData.getBizDate());
			int bizyear=bizcal.get(Calendar.YEAR);
			int bizmonth=bizcal.get(Calendar.MONTH)+1;
			
			BigDecimal actAmount=FDCHelper.ZERO;
			
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select famount payAmount,fpayDate paydate from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+this.editData.getContractBill().getId().toString()+"'");
			_builder.appendSql(" and fpayDate is not null and famount is not null order by fpayDate");
			final IRowSet rowSet = _builder.executeQuery();
			
			Map value=new HashMap();
			
			while (rowSet.next()) {
				IRow row=this.kdtAct.addRow();
				
				Date payDate=rowSet.getDate("paydate");
				row.getCell("date").setValue(payDate);
				
				BigDecimal cellPayAmountValue=rowSet.getBigDecimal("payAmount");
				row.getCell("payAmount").setValue(cellPayAmountValue);
				
				BigDecimal cellRateValue=cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("payRate").setValue(cellRateValue);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(payDate);
				int year=cal.get(Calendar.YEAR);
				int month=cal.get(Calendar.MONTH)+1;
				
				if((year==bizyear&&month<=bizmonth)||(year<bizyear)){
					actAmount=FDCHelper.add(actAmount, cellPayAmountValue);
				}
				String date=String.valueOf(year)+String.valueOf(month);
				if(value.get(date)!=null){
					value.put(date,((BigDecimal)value.get(date)).add(cellPayAmountValue));
				}else{
					value.put(date,cellPayAmountValue);
				}
			}
			this.txtActAmount.setValue(actAmount);
			this.txtRate.setValue(FDCHelper.divide(actAmount, this.txtLastAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
			
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				int comYear=Integer.parseInt(this.kdtEntry.getRow(i).getCell("year").getValue().toString());
				int comMonth=Integer.parseInt(this.kdtEntry.getRow(i).getCell("month").getValue().toString());
				
				if(comYear==bizyear&&comMonth==bizmonth+1){
					actAmount=FDCHelper.add(actAmount, this.kdtEntry.getRow(i).getCell("payAmount").getValue());
					
					EntityViewInfo view=new EntityViewInfo();
			    	FilterInfo filter=new FilterInfo();
			    	
			    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.versionType",VersionTypeEnum.EXECUTE_VALUE));
			    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.state",FDCBillStateEnum.AUDITTED_VALUE));
			    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.isLatest",Boolean.TRUE));
			    	filter.getFilterItems().add(new FilterItemInfo("headEntry.contractBill.id",this.editData.getContractBill().getId().toString()));
			    	filter.getFilterItems().add(new FilterItemInfo("year",comYear));
			    	filter.getFilterItems().add(new FilterItemInfo("month",comMonth));
			    	view.setFilter(filter);
			    	view.getSelector().add(new SelectorItemInfo("executeAmount"));
			    	
			    	ProjectMonthPlanGatherDateEntryCollection col=ProjectMonthPlanGatherDateEntryFactory.getRemoteInstance().getProjectMonthPlanGatherDateEntryCollection(view);
			    	if(col.size()>0){
			    		this.kdtEntry.getRow(i).getCell("checkPayAmount").setValue(col.get(0).getExecuteAmount());
			    	}
				}
				KDSpinnerCellEditor year = new KDSpinnerCellEditor(new SpinnerNumberModel(comYear,1,10000,1));
				this.kdtEntry.getRow(i).getCell("year").setEditor(year);
				
				KDSpinnerCellEditor month = new KDSpinnerCellEditor(new SpinnerNumberModel(comMonth,1,12,1));
				this.kdtEntry.getRow(i).getCell("month").setEditor(month);
				
				String date=String.valueOf(comYear)+String.valueOf(comMonth);
				BigDecimal sum=(BigDecimal) value.get(date);
				
				if(sum!=null&&sum.compareTo(FDCHelper.ZERO)>0){
					this.kdtEntry.getRow(i).getCell("actPayAmount").setValue(sum);
					
					BigDecimal rate=sum.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 2, BigDecimal.ROUND_HALF_UP);
					this.kdtEntry.getRow(i).getCell("actPayRate").setValue(rate);
					
					this.kdtEntry.getRow(i).getCell("year").getStyleAttributes().setLocked(true);
					this.kdtEntry.getRow(i).getCell("year").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					this.kdtEntry.getRow(i).getCell("month").getStyleAttributes().setLocked(true);
					this.kdtEntry.getRow(i).getCell("month").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				}
			}
			this.txtNextActAmount.setValue(actAmount);
			this.txtNextRate.setValue(FDCHelper.divide(actAmount, this.txtLastAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		TableHelper.getFootRow(this.kdtEntry, new String[]{"checkPayAmount","payAmount","payRate","actPayAmount","actPayRate"});
		TableHelper.getFootRow(this.kdtAct, new String[]{"payRate","payAmount"});
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("contractBill.id");
    	sel.add("contractBill.number");
    	sel.add("contractBill.name");
    	sel.add("contractBill.curProject.displayName");
    	sel.add("contractBill.orgUnit.displayName");
    	sel.add("contractBill.amount");
    	sel.add("contractBill.programmingContract.amount");
		return sel;
	}
	protected IObjectValue createNewData() {
		ContractPayPlanInfo info=(ContractPayPlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ContractPayPlanInfo();
			info.setVersion(1);
			String contractBillId = (String)getUIContext().get("contractBillId");
		
			ContractBillInfo contractBillInfo = null;
			try {
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("number");
				sic.add("name");
				sic.add("amount");
				sic.add("curProject.displayName");
				sic.add("orgUnit.displayName");
				sic.add("respPerson.*");
				sic.add("respDept.*");
				sic.add("programmingContract.amount");
				sic.add("partB.name");
				sic.add("payItems.*");
				sic.add("payItems.paymentType.*");
				contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), sic);			
			} catch (Exception e1) {
				handUIException(e1);
			}
			if(contractBillInfo!=null){
				info.setOrgUnit(contractBillInfo.getOrgUnit());
				info.setContractBill(contractBillInfo);
//				info.setRespDept(contractBillInfo.getRespDept());
//				info.setRespPerson(contractBillInfo.getRespPerson());
				
				ContractPayItemCollection ecoItemsColl = contractBillInfo.getPayItems();
				for(int i = 0; i < ecoItemsColl.size(); i++) {
					ContractPayItemInfo ecoItemInfo = ecoItemsColl.get(i);
					Calendar cal = Calendar.getInstance();
					cal.clear();
					cal.setTime(ecoItemInfo.getPayItemDate());
					ContractPayPlanEntryInfo entryInfo = new ContractPayPlanEntryInfo();
					entryInfo.setYear(cal.get(Calendar.YEAR));
					entryInfo.setMonth(cal.get(Calendar.MONTH)+1);
					entryInfo.setPaymentType(ecoItemInfo.getPaymentType());
					entryInfo.setPayAmount(ecoItemInfo.getAmount());
					entryInfo.setPayRate(ecoItemInfo.getProp());
					entryInfo.setPayNode(ecoItemInfo.getPayCondition());
					
					info.getEntry().add(entryInfo);
				}
			}else{
				FDCMsgBox.showWarning(this,"合同为空！");
	    		SysUtil.abort();
			}
		}else{
			info.setVersion(info.getVersion()+1);
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(BOSUuid.create(info.getEntry().get(i).getBOSType()));
			}
		}
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	protected boolean checkBizDate() throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",this.editData.getContractBill().getId().toString()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ContractPayPlanCollection col=ContractPayPlanFactory.getRemoteInstance().getContractPayPlanCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,"最新合同付款计划还未审批！");
    		return true;
    	}else{
    		if(col.size()==0){
    			this.txtVersion.setValue(1);
    		}else{
    			this.txtVersion.setValue(col.get(0).getVersion()+1);
    		}
    		return false;
    	}
	}
	protected void verifyInputForSubmint() throws Exception {
		if(checkBizDate()){
			SysUtil.abort();
		}
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRespPerson);
//		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRespDept);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbDepartment);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		BigDecimal sum=FDCHelper.ZERO;
		
		Calendar bizcal = Calendar.getInstance();
		bizcal.setTime((Date) this.pkBizDate.getValue());
		int bizyear=bizcal.get(Calendar.YEAR);
		int bizmonth=bizcal.get(Calendar.MONTH)+1;
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("year").getValue()==null){
				FDCMsgBox.showWarning(this,"计划年份不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("year"));
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("month").getValue()==null){
				FDCMsgBox.showWarning(this,"计划月份不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("month"));
				SysUtil.abort();
			}
//			if(this.kdtEntry.getRow(i).getCell("payType")==null){
//				FDCMsgBox.showWarning(this,"付款类型不能为空！");
//				SysUtil.abort();
//			}
			if(this.kdtEntry.getRow(i).getCell("payType").getValue()==null){
				FDCMsgBox.showWarning(this,"付款类型不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("payType"));
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("payRate").getValue()==null){
				FDCMsgBox.showWarning(this,"计划付款比例不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("payRate"));
				SysUtil.abort();
			}
			if(this.kdtEntry.getRow(i).getCell("payAmount").getValue()==null){
				FDCMsgBox.showWarning(this,"计划付款金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("payAmount"));
				SysUtil.abort();
			}
			Integer year=(Integer) this.kdtEntry.getRow(i).getCell("year").getValue();
			Integer month=(Integer) this.kdtEntry.getRow(i).getCell("month").getValue();
//			if(year==bizyear&&month==bizmonth+1){
//				if(this.kdtEntry.getRow(i).getCell("remark").getValue()==null||"".equals(this.kdtEntry.getRow(i).getCell("remark").getValue().toString().trim())){
//					FDCMsgBox.showWarning(this,"用款说明不能为空！");
//					this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("remark"));
//					SysUtil.abort();
//				}
//			}
			if((year==bizyear&&month==bizmonth+1)||year>bizyear){
				sum=FDCHelper.add(sum, this.kdtEntry.getRow(i).getCell("payAmount").getValue());
			}
		}
//		if(this.kdtEntry.getRowCount()<6){
//			FDCMsgBox.showWarning(this,"合同付款计划不能小于六个月的计划！");
//			SysUtil.abort();
//		}
		BigDecimal totalRate = (BigDecimal) this.kdtEntry.getFootRow(0).getCell("payRate").getValue();
		if(totalRate != null && totalRate.compareTo(new BigDecimal("100")) != 0) {
			FDCMsgBox.showWarning(this,"计划付款比例之和不为100%!!请修改!");
			SysUtil.abort();
		}
		if(this.editData.getContractBill().getProgrammingContract()!=null&&this.editData.getContractBill().getProgrammingContract().getAmount()!=null){
			if(FDCHelper.add(this.txtActAmount.getBigDecimalValue(), sum).compareTo(this.editData.getContractBill().getProgrammingContract().getAmount())>0){
				FDCMsgBox.showWarning(this,"截止本月累计实付金额+合同付款计划总额之和大于合同关联合约规划金额！");
				SysUtil.abort();
			}
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		int year=0;
		int month=0;
		if(this.kdtEntry.getRowCount()==0){
			Date now=new Date();
			try {
				now=FDCCommonServerHelper.getServerTimeStamp();
			} catch (BOSException e1) {
				logger.error(e1.getMessage());
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			year=cal.get(Calendar.YEAR);
			month=cal.get(Calendar.MONTH)+1+1;
		}else{
			year=Integer.parseInt(this.kdtEntry.getRow(this.kdtEntry.getRowCount()-1).getCell("year").getValue().toString());
			month=Integer.parseInt(this.kdtEntry.getRow(this.kdtEntry.getRowCount()-1).getCell("month").getValue().toString())+1;
		}
		IRow row = this.kdtEntry.addRow();
		ContractPayPlanEntryInfo info = new ContractPayPlanEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		if(month>12){
			month=1;
			year=year+1;
		}
		row.getCell("year").setValue(year);
		row.getCell("month").setValue(month);
		
		KDSpinnerCellEditor yearEditor = new KDSpinnerCellEditor(new SpinnerNumberModel(year,1,10000,1));
		row.getCell("year").setEditor(yearEditor);
		
		KDSpinnerCellEditor monthEditor = new KDSpinnerCellEditor(new SpinnerNumberModel(month,1,12,1));
		row.getCell("month").setEditor(monthEditor);
		
		row.getCell("payAmount").setValue(FDCHelper.ZERO);
		row.getCell("payRate").setValue(FDCHelper.ZERO);
		row.setUserObject(info);
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = this.kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtEntry)){
				actionALine_actionPerformed(e);
			}else{
				int year=0;
				int month=0;
				if(this.kdtEntry.getRowCount()==0){
					Date now=new Date();
					try {
						now=FDCCommonServerHelper.getServerTimeStamp();
					} catch (BOSException e1) {
						logger.error(e1.getMessage());
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					year=cal.get(Calendar.YEAR);
					month=cal.get(Calendar.MONTH)+1+1;
				}else{
					year=Integer.parseInt(this.kdtEntry.getRow(top).getCell("year").getValue().toString());
					month=Integer.parseInt(this.kdtEntry.getRow(top).getCell("month").getValue().toString())-1;
					if(month<1){
						month=12;
						year=year-1;
					}
				}
				for(int i=0;i<this.kdtEntry.getRowCount();i++){
					int comYear=Integer.parseInt(this.kdtEntry.getRow(i).getCell("year").getValue().toString());
					int comMonth=Integer.parseInt(this.kdtEntry.getRow(i).getCell("month").getValue().toString());
					if(comYear==year&&comMonth==month){
						FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月 合同付款计划已存在，禁止插入行！");
						return;
					}
				}
				ContractPayPlanEntryInfo info = new ContractPayPlanEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				
				if(month>12){
					month=1;
					year=year+1;
				}
				row = this.kdtEntry.addRow(top);
				row.getCell("year").setValue(year);
				row.getCell("month").setValue(month);
				
				KDSpinnerCellEditor yearEditor = new KDSpinnerCellEditor(new SpinnerNumberModel(year,1,10000,1));
				row.getCell("year").setEditor(yearEditor);
				
				KDSpinnerCellEditor monthEditor = new KDSpinnerCellEditor(new SpinnerNumberModel(month,1,12,1));
				row.getCell("month").setEditor(monthEditor);
				
				row.setUserObject(info);
			}
		} else {
			actionALine_actionPerformed(e);
		}
		loadEntry();
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				int comYear=Integer.parseInt(this.kdtEntry.getRow(top).getCell("year").getValue().toString());
				int comMonth=Integer.parseInt(this.kdtEntry.getRow(top).getCell("month").getValue().toString());
				if(this.kdtEntry.getRow(top).getCell("actPayAmount").getValue()!=null
						&&((BigDecimal)this.kdtEntry.getRow(top).getCell("actPayAmount").getValue()).compareTo(FDCHelper.ZERO)>0){
					FDCMsgBox.showInfo(this, comYear+"年"+comMonth+"月 合同实际付款已存在，禁止删除行！");
					return;
				}
				this.kdtEntry.removeRow(top);
			}
			
			loadEntry();
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("payAmount")||
				this.kdtEntry.getColumnKey(e.getColIndex()).equals("payRate")){
			BigDecimal amount=FDCHelper.toBigDecimal(this.txtLastAmount.getNumberValue(),2);
			if(amount==null||amount.compareTo(FDCHelper.ZERO)==0) return;
			
			BigDecimal actPayAmount=(BigDecimal) this.kdtEntry.getRow(e.getRowIndex()).getCell("actPayAmount").getValue();
			if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("payAmount")){
				BigDecimal cellPayRateValue=FDCHelper.ZERO;
				BigDecimal cellPayAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
				cellPayRateValue=cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					FDCMsgBox.showWarning(this,"计划付款比例大于100%！");
					this.kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
					SysUtil.abort();
				}
				if(actPayAmount!=null&&cellPayAmountValue.compareTo(actPayAmount)<0){
					FDCMsgBox.showWarning(this,"计划付款金额小于实际付款金额！");
					this.kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
					SysUtil.abort();
				}
				this.kdtEntry.getCell(e.getRowIndex(), "payRate").setValue(cellPayRateValue);
			}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("payRate")){
				BigDecimal cellPayAmountValue=FDCHelper.ZERO;
				BigDecimal cellPayRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
				
				cellPayAmountValue=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(amount,cellPayRateValue),FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP),2);
				if(cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					FDCMsgBox.showWarning(this,"计划付款比例大于100%！");
					this.kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
					SysUtil.abort();
				}
				if(actPayAmount!=null&&cellPayAmountValue.compareTo(actPayAmount)<0){
					FDCMsgBox.showWarning(this,"计划付款金额小于实际付款金额！");
					this.kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
					SysUtil.abort();
				}
				this.kdtEntry.getCell(e.getRowIndex(), "payAmount").setValue(cellPayAmountValue);
			}
			pkBizDate_dataChanged(null);
			
			TableHelper.getFootRow(this.kdtEntry, new String[]{"payAmount","payRate"});
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("year")||
				this.kdtEntry.getColumnKey(e.getColIndex()).equals("month")){
			
			int comYear=Integer.parseInt(this.kdtEntry.getRow(e.getRowIndex()).getCell("year").getValue().toString());
			int comMonth=Integer.parseInt(this.kdtEntry.getRow(e.getRowIndex()).getCell("month").getValue().toString());
			
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				if(i==e.getRowIndex()) continue;
				int year=Integer.parseInt(this.kdtEntry.getRow(i).getCell("year").getValue().toString());
				int month=Integer.parseInt(this.kdtEntry.getRow(i).getCell("month").getValue().toString());
				if(comYear==year&&comMonth==month){
					FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月 合同付款计划已存在，禁止修改！");
					this.kdtEntry.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getOldValue());
					return;
				}
			}
			pkBizDate_dataChanged(null);
			
			KDSpinnerCellEditor year = new KDSpinnerCellEditor(new SpinnerNumberModel(comYear,1,10000,1));
			this.kdtEntry.getRow(e.getRowIndex()).getCell("year").setEditor(year);
			
			KDSpinnerCellEditor month = new KDSpinnerCellEditor(new SpinnerNumberModel(comMonth,1,12,1));
			this.kdtEntry.getRow(e.getRowIndex()).getCell("month").setEditor(month);
			
			loadEntry();
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("useType")&&e.getRowIndex()==0){
			for(int i=1;i<this.kdtEntry.getRowCount();i++){
				this.kdtEntry.getRow(i).getCell("useType").setValue(this.kdtEntry.getRow(0).getCell("useType").getValue());
			}
		}
	}
	protected AdminOrgUnitInfo getDepByPerson(PersonInfo person) throws BOSException{
		SelectorItemCollection personsel=new SelectorItemCollection();
		personsel.add("position.adminOrgUnit.*");
		if(person!=null){
			EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
    		view.setFilter(filter);
    		view.setSelector(personsel);
			
			PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
			if(pmcol.size()>0){
				return pmcol.get(0).getPosition().getAdminOrgUnit();
			}
		}
		return null;
	}
	protected void prmtRespPerson_dataChanged(DataChangeEvent e) throws Exception {
//		PersonInfo person=(PersonInfo) prmtRespPerson.getValue();
//		if(person!=null){
//			this.prmtRespDept.setValue(getDepByPerson(person));
//		}else{
//			this.prmtRespDept.setValue(null);
//		}
	}
	
	/**
	 * 修订
	 */
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() == null) {
			MsgBox.showWarning(this, "请先保存单据!");
	        SysUtil.abort();
		}
		ContractPayPlanInfo info=getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		IUIWindow ui = UIFactory.createUIFactory().create(ContractPayPlanEditUI.class.getName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
	}
	
	private void checkAudited(ContractPayPlanInfo info) throws BOSException, EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "非审批单据不能修订！");
	        SysUtil.abort();
		}
	}
	private void checkLastVersion(ContractPayPlanInfo info) throws BOSException, EASBizException {
		if(!info.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修订！");
	        SysUtil.abort();
		}
	}
	private ContractPayPlanInfo getSelectedInfo() throws BOSException, EASBizException {
//		checkSelected();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("entry.*");
    	sel.add("entry.paymentType.*");
    	sel.add("contractBill.number");
    	sel.add("contractBill.name");
		sel.add("contractBill.amount");
		sel.add("contractBill.curProject.displayName");
		sel.add("contractBill.orgUnit.displayName");
		sel.add("respPerson.*");
		sel.add("respDept.*");
		return ContractPayPlanFactory.getRemoteInstance().getContractPayPlanInfo(new ObjectUuidPK(editData.getId()),sel);
	}
}