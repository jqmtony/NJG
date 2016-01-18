/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectPlanDetialTypeEnum;
import com.kingdee.eas.fdc.finance.ProjectYearPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanInfo;
import com.kingdee.eas.fdc.finance.utils.TableHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectYearPlanNewEditUI extends AbstractProjectYearPlanNewEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectYearPlanNewEditUI.class);
    public ProjectYearPlanNewEditUI() throws Exception
    {
        super();
    }
    private Boolean isPro=null;
    private Boolean isLoad=false;
    private KDTable table=null;
    private int year_old = 0;
	private int month_old =0;
	private Color rowBg = new Color(246, 246, 191);
	protected void attachListeners() {
		
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectYearPlanFactory.getRemoteInstance();
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
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			if(table!=null)
				table.setEnabled(false);
		} else {
			this.unLockUI();
			if(table!=null)
				table.setEnabled(true);
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
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
		
		this.prmtCurProject.setEnabled(false);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1990,3099,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
	}
	protected void setPlanDetialEntryInfo(IRow row,ProjectYearPlanEntryInfo entry,ProjectYearPlanDateEntryInfo detailEntry,int year,int month,int quarter,ProjectPlanDetialTypeEnum type){
		detailEntry.setYear(year);
		detailEntry.setMonth(month);
		detailEntry.setQuarter(quarter);
		detailEntry.setType(type);
		if(!entry.getDateEntry().contains(detailEntry)){
			entry.getDateEntry().add(detailEntry);
		}
		String key="";
		if(type.equals(ProjectPlanDetialTypeEnum.YEAR)){
			key=year+"Y";
		}else if(type.equals(ProjectPlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M";
		}else{
			key=year+"Y"+quarter+"Q";
		}
		row.getCell(key+"amount").setUserObject(detailEntry);
		row.getCell(key+"amount").setValue(detailEntry.getAmount());
	}
	protected void loadEntry(ProjectYearPlanInfo info,int beginYear,int beginMonth,int planMonth){
		Map contractTypeMap=new HashMap();
		List noContractType=new ArrayList();
		for(int i=0;i<info.getEntry().size();i++){
			ProjectYearPlanEntryInfo entry=info.getEntry().get(i);
			if(entry.getContractType()!=null){
				if(contractTypeMap.containsKey(entry.getContractType().getNumber())){
					List value=(List) contractTypeMap.get(entry.getContractType().getNumber());
					value.add(entry);
				}else{
					List value=new ArrayList();
					value.add(entry);
					contractTypeMap.put(entry.getContractType().getNumber(), value);
				}
			}else{
				noContractType.add(entry);
			}
		}
		table.getTreeColumn().setDepth(2);
		Object[] key = contractTypeMap.keySet().toArray();
		Arrays.sort(key);
		for (int kk = 0; kk < key.length; kk++) {
			IRow contractRow=table.addRow();
			contractRow.getStyleAttributes().setBackground(rowBg);
			contractRow.setTreeLevel(0);
			List value = (List) contractTypeMap.get(key[kk]);
			for(int i=0;i<value.size();i++){
				ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo) value.get(i);
				contractRow.getCell("name").setValue(entry.getContractType().getName());
				IRow row=table.addRow();
				row.setTreeLevel(1);
				row.setUserObject(entry);
				if(entry.getProgrammingContract()!=null){
					row.getCell("number").setValue(entry.getProgrammingContract().getLongNumber());
					row.getCell("name").setValue(entry.getProgrammingContract().getName());
					row.getCell("amount").setValue(entry.getProgrammingContract().getAmount());
				}else{
					row.getCell("name").setValue(entry.getName());
					row.getCell("amount").setValue(entry.getAmount());
				}
				row.getCell("currentProgress").setValue(entry.getCurrentProgress());
				if(entry.getContractBill()!=null){
					row.getCell("conName").setValue(entry.getContractBill().getName());
					row.getCell("conAmount").setValue(entry.getContractBill().getAmount());
					if(entry.getContractBill().getPartB()!=null){
						row.getCell("partB").setValue(entry.getContractBill().getPartB().getName());
					}
					int year=this.spYear.getIntegerVlaue().intValue();
					int month=this.spMonth.getIntegerVlaue().intValue()-1;
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, month-1);
					cal.set(Calendar.DATE, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					
					BigDecimal actAmount=null;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+entry.getContractBill().getId().toString()+"' ");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					
					try {
						IRowSet rowSet = _builder.executeQuery();
						if (rowSet.size() == 1) {
							rowSet.next();
							actAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
						}
						row.getCell("actAmount").setValue(actAmount);
					} catch (BOSException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else{
					int year=this.spYear.getIntegerVlaue().intValue();
					int month=this.spMonth.getIntegerVlaue().intValue()-1;
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, month-1);
					cal.set(Calendar.DATE, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					
					if(entry.getProgrammingContract()!=null){
						BigDecimal actAmount=null;
						FDCSQLBuilder _builder = new FDCSQLBuilder();
						_builder.appendSql("select sum(pay.FAmount) sumCount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+entry.getProgrammingContract().getId().toString()+"'");
						_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
						try {
							IRowSet rowSet = _builder.executeQuery();
							if (rowSet.size() == 1) {
								rowSet.next();
								actAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
							}
							row.getCell("actAmount").setValue(actAmount);
						} catch (BOSException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				Map yearEntryMap=new HashMap();
				Map monthEntryMap=new HashMap();
				Map quarterEntryMap=new HashMap();
				for(int k=0;k<entry.getDateEntry().size();k++){
					ProjectYearPlanDateEntryInfo detailEntry=entry.getDateEntry().get(k);
					if(detailEntry.getType().equals(ProjectPlanDetialTypeEnum.YEAR)){
						yearEntryMap.put(detailEntry.getYear()+"Y", detailEntry);
					}else if(detailEntry.getType().equals(ProjectPlanDetialTypeEnum.MONTH)){
						monthEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getMonth()+"M", detailEntry);
					}else{
						quarterEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getQuarter()+"Q", detailEntry);
					}
				}
				int year=beginYear;
				int bYear=beginYear;
				int bMonth=beginMonth;
				for(int k=0;k<planMonth;k++){
					int quarter=0;
					bMonth=bMonth+1;
					if(bMonth>12){
						bMonth=1;
						bYear=bYear+1;
					}
					if(bMonth<4){
						quarter=1;
					}else if(bMonth<7){
						quarter=2;
					}else if(bMonth<10){
						quarter=3;
					}else if(bMonth<13){
						quarter=4;
					}
					if(bYear-year<2){
						if(monthEntryMap.containsKey(bYear+"Y"+bMonth+"M")){
							ProjectYearPlanDateEntryInfo detailEntry=(ProjectYearPlanDateEntryInfo) monthEntryMap.get(bYear+"Y"+bMonth+"M");
							setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
						}else{
							ProjectYearPlanDateEntryInfo detailEntry=new ProjectYearPlanDateEntryInfo();
							setPlanDetialEntryInfo(row,entry,detailEntry,bYear,bMonth,0,ProjectPlanDetialTypeEnum.MONTH);
						}
					}else{
						if(quarterEntryMap.containsKey(bYear+"Y"+quarter+"Q")){
							ProjectYearPlanDateEntryInfo detailEntry=(ProjectYearPlanDateEntryInfo) quarterEntryMap.get(bYear+"Y"+quarter+"Q");
							setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
						}else{
							ProjectYearPlanDateEntryInfo detailEntry=new ProjectYearPlanDateEntryInfo();
							setPlanDetialEntryInfo(row,entry,detailEntry,bYear,0,quarter,ProjectPlanDetialTypeEnum.QUARTER);
						}
					}
				}
			}
		}
		if(noContractType.size()>0){
			IRow contractRow=table.addRow();
			contractRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
			contractRow.setTreeLevel(0);
			contractRow.getCell("name").setValue("无合同类型");
		}
		for (int kk = 0; kk < noContractType.size(); kk++) {
			ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo) noContractType.get(kk);
			IRow row=table.addRow();
			row.setUserObject(entry);
			if(entry.getProgrammingContract()!=null){
				row.getCell("number").setValue(entry.getProgrammingContract().getLongNumber());
				row.getCell("name").setValue(entry.getProgrammingContract().getName());
				row.getCell("amount").setValue(entry.getProgrammingContract().getAmount());
			}
			if(entry.getContractBill()!=null){
				row.getCell("conName").setValue(entry.getContractBill().getName());
				row.getCell("conAmount").setValue(entry.getContractBill().getAmount());
				if(entry.getContractBill().getPartB()!=null){
					row.getCell("partB").setValue(entry.getContractBill().getPartB().getName());
				}
			}
			if(entry.getContractBill()!=null){
				row.getCell("conName").setValue(entry.getContractBill().getName());
				row.getCell("conAmount").setValue(entry.getContractBill().getAmount());
				if(entry.getContractBill().getPartB()!=null){
					row.getCell("partB").setValue(entry.getContractBill().getPartB().getName());
				}
				int year=this.spYear.getIntegerVlaue().intValue();
				int month=this.spMonth.getIntegerVlaue().intValue()-1;
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				BigDecimal actAmount=null;
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+entry.getContractBill().getId().toString()+"' ");
				_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
				
				try {
					IRowSet rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
					row.getCell("actAmount").setValue(actAmount);
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				int year=this.spYear.getIntegerVlaue().intValue();
				int month=this.spMonth.getIntegerVlaue().intValue()-1;
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				if(entry.getProgrammingContract()!=null){
					BigDecimal actAmount=null;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(pay.FAmount) sumCount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+entry.getProgrammingContract().getId().toString()+"'");
					_builder.appendSql("and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(cal.getTime())))+ "'} ");
					try {
						IRowSet rowSet = _builder.executeQuery();
						if (rowSet.size() == 1) {
							rowSet.next();
							actAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
						}
						row.getCell("actAmount").setValue(actAmount);
					} catch (BOSException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			Map yearEntryMap=new HashMap();
			Map monthEntryMap=new HashMap();
			Map quarterEntryMap=new HashMap();
			for(int k=0;k<entry.getDateEntry().size();k++){
				ProjectYearPlanDateEntryInfo detailEntry=entry.getDateEntry().get(k);
				if(detailEntry.getType().equals(ProjectPlanDetialTypeEnum.YEAR)){
					yearEntryMap.put(detailEntry.getYear()+"Y", detailEntry);
				}else if(detailEntry.getType().equals(ProjectPlanDetialTypeEnum.MONTH)){
					monthEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getMonth()+"M", detailEntry);
				}else{
					quarterEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getQuarter()+"Q", detailEntry);
				}
			}
			int year=beginYear;
			int bYear=beginYear;
			int bMonth=beginMonth;
			for(int k=0;k<planMonth;k++){
				int quarter=0;
				bMonth=bMonth+1;
				if(bMonth>12){
					bMonth=1;
					bYear=bYear+1;
				}
				if(bMonth<4){
					quarter=1;
				}else if(bMonth<7){
					quarter=2;
				}else if(bMonth<10){
					quarter=3;
				}else if(bMonth<13){
					quarter=4;
				}
				if(bYear-year<2){
					if(monthEntryMap.containsKey(bYear+"Y"+bMonth+"M")){
						ProjectYearPlanDateEntryInfo detailEntry=(ProjectYearPlanDateEntryInfo) monthEntryMap.get(bYear+"Y"+bMonth+"M");
						setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
					}else{
						ProjectYearPlanDateEntryInfo detailEntry=new ProjectYearPlanDateEntryInfo();
						setPlanDetialEntryInfo(row,entry,detailEntry,bYear,bMonth,0,ProjectPlanDetialTypeEnum.MONTH);
					}
				}else{
					if(quarterEntryMap.containsKey(bYear+"Y"+quarter+"Q")){
						ProjectYearPlanDateEntryInfo detailEntry=(ProjectYearPlanDateEntryInfo) quarterEntryMap.get(bYear+"Y"+quarter+"Q");
						setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
					}else{
						ProjectYearPlanDateEntryInfo detailEntry=new ProjectYearPlanDateEntryInfo();
						setPlanDetialEntryInfo(row,entry,detailEntry,bYear,0,quarter,ProjectPlanDetialTypeEnum.QUARTER);
					}
				}
			}
		
		}
		for(int i=0;i<this.table.getColumnCount();i++){
			String k=this.table.getColumnKey(i);
			if(k.indexOf("amount")>=0||k.indexOf("Amount")>=0){
				TableHelper.getFootRow(this.table, new String[]{k});
			}
		}
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
		this.txtName.setText("项目"+year+"年"+month+"月"+"年度资金计划（"+this.editData.getCurProject().getName()+"）");
		
		super.storeFields();
	}
	public void loadFields() {
		if(isPro==null){
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_PROJECTPLANPRO", this.editData.getOrgUnit().getId().toString());
			try {
				HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
				if(hmAllParam.get("CIFI_PROJECTPLANPRO")!=null){
					isPro=Boolean.parseBoolean(hmAllParam.get("CIFI_PROJECTPLANPRO").toString());
				}else{
					isPro=false;
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		isLoad=true;
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			year_old = year;
			month_old = month;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
			
			if(!this.getOprtState().equals(OprtState.ADDNEW)){
				createTable(this.editData,year,month-1,39);
			}
		}
		if(this.getOprtState().equals(OprtState.ADDNEW)){
			Boolean isCheck=false;
			try {
				isCheck=checkBizDate();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(isCheck){
				SysUtil.abort();
			}
		}
		if(this.txtVersion.getIntegerValue()>1){
			this.spYear.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spYear.setEnabled(false);
			this.spMonth.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spMonth.setEnabled(false);
		}
		
		attachListeners();
		setOprtState(this.getOprtState());
		setAuditButtonStatus(this.getOprtState());
		isLoad=false;
	}
	protected void createTable(ProjectYearPlanInfo info,int beginYear,int beginMonth,int planMonth){
		this.pnlBig.removeAll();
		int year=beginYear;
		int month=beginMonth;
		table=new KDTable();
		enableExportExcel(table);
		table.checkParsed();
		FDCTableHelper.disableDelete(table);
		IRow headRow=table.addHeadRow();
		
		table.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=table.addColumn();
		column.setKey("number");
		headRow.getCell("number").setValue("合约规划编码");
		
		KDBizPromptBox prmtcontract = new KDBizPromptBox();
		prmtcontract.setDisplayFormat("$name$");
		prmtcontract.setEditFormat("$number$");
		prmtcontract.setCommitFormat("$number$");
		prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");

		prmtcontract.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				filter.getFilterItems().add(new FilterItemInfo("programming.state",FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id",editData.getCurProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("programming.isLatest",Boolean.TRUE));
//				filter.getFilterItems().add(new FilterItemInfo("programming.isCiting",Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("id","select fparentid from T_CON_ProgrammingContract where fparentid is not null",CompareType.NOTINNER));
				
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		if(this.isPro){
			column.getStyleAttributes().setHided(true);
		}
		KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(prmtcontract);
		column.setEditor(contractEditor);
		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		column.setRenderer(ovrNum);
		
		column=table.addColumn();
		column.setKey("name");
		if(this.isPro){
			headRow.getCell("name").setValue("事项");
		}else{
			headRow.getCell("name").setValue("合约规划名称");
		}
		
		column=table.addColumn();
		column.setKey("amount");
		if(this.isPro){
			headRow.getCell("amount").setValue("金额");
		}else{
			headRow.getCell("amount").setValue("合约规划金额(元)");
		}
		
		column=table.addColumn();
		column.setKey("conName");
		headRow.getCell("conName").setValue("合同名称");
		
		column=table.addColumn();
		column.setKey("conAmount");
		headRow.getCell("conAmount").setValue("合同金额(元)");
		
		column=table.addColumn();
		column.setKey("partB");
		headRow.getCell("partB").setValue("合同施工单位名称");
		
		//modify by yxl 20160108 项目月度、年度资金计划里增加“当前进度说明”列，为文本格式、必录项
		column=table.addColumn();
		column.setKey("currentProgress");
		column.setWidth(200);
//		column.getStyleAttributes().setLocked(false);
		column.getStyleAttributes().setWrapText(true);
		headRow.getCell("currentProgress").setValue("当前进度说明");
		
		column=table.addColumn();
		column.setKey("actAmount");
		headRow.getCell("actAmount").setValue("截止"+beginYear+"年"+beginMonth+"月累计实际付款");
		
		table.getColumn("amount").setEditor(amountEditor);
		table.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		table.getColumn("conAmount").setEditor(amountEditor);
		table.getColumn("conAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("conAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		table.getColumn("actAmount").setEditor(amountEditor);
		table.getColumn("actAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        
		
		table.setName("");
		table.getViewManager().setFreezeView(0, 7);
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		this.pnlBig.add(contEntry, "资金计划明细");
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
		table.addKDTMouseListener(new KDTMouseListener() {
            public void tableClicked(KDTMouseEvent e) {
            	table_tableClicked(e);
            }
        });
		
		KDWorkButton btnExportExcel = new KDWorkButton();
//
		this.actionExportExcel.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
		btnExportExcel = (KDWorkButton)contEntry.add(this.actionExportExcel);
		btnExportExcel.setText("导出");
		btnExportExcel.setSize(new Dimension(140, 19));
		for(int i=0;i<planMonth;i++){
			int quarter=0;
			beginMonth=beginMonth+1;
			if(beginMonth>12){
				beginMonth=1;
				beginYear=beginYear+1;
			}
			if(beginMonth<4){
				quarter=1;
			}else if(beginMonth<7){
				quarter=2;
			}else if(beginMonth<10){
				quarter=3;
			}else if(beginMonth<13){
				quarter=4;
			}
			if(beginYear-year<2){
				addColumn(table,ProjectPlanDetialTypeEnum.MONTH,beginYear,beginMonth,0);
			}else{
				addColumn(table,ProjectPlanDetialTypeEnum.QUARTER,beginYear,0,quarter);
			}
		}
		loadEntry(info,year,month,planMonth);
	}
	
	protected void table_tableClicked(KDTMouseEvent e){
		if(e.getType() == 0) // 如果点击表头不要处理
			return;
		if(e.getColIndex() == table.getColumnIndex("currentProgress")) {
			if(STATUS_ADDNEW.equals(oprtState) || STATUS_EDIT.equals(oprtState)) {
//				KDDetailedAreaUtil.showDialog(this, table, 250, 200, 500);
				showAreaDialog(table, 250, 200, 255, true);
			}else if(STATUS_VIEW.equals(oprtState))
				showAreaDialog(table, 250, 200, 255, false);
		}
    }
	
	private void showAreaDialog(KDTable table, int X, int Y, int len, boolean isEdit){
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		final IRow row = table.getRow(rowIndex);
		if(row.getStyleAttributes().getBackground().equals(rowBg)) {
			return;
		}
		final ICell cell = table.getCell(rowIndex, colIndex);
		BasicView view = table.getViewManager().getView(5);
		Point p = view.getLocationOnScreen();
		Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
		KDDetailedAreaDialog dialog;
		Window parent = SwingUtilities.getWindowAncestor(this);
		if (parent instanceof Dialog) {
			dialog = new KDDetailedAreaDialog((Dialog) parent, X, Y, true){
				protected void btnOK_actionPerformed(ActionEvent e) {
					super.btnOK_actionPerformed(e);
					cell.setValue(getData());
					ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo)row.getUserObject();
					if(entry != null)
						entry.setCurrentProgress((String)getData());
				}
			};
		} else if (parent instanceof Frame) {
			dialog = new KDDetailedAreaDialog((Frame) parent, X, Y, true){
				protected void btnOK_actionPerformed(ActionEvent e) {
					super.btnOK_actionPerformed(e);
					cell.setValue(getData());
					ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo)row.getUserObject();
					if(entry != null)
						entry.setCurrentProgress((String)getData());
				}
			};
		} else {
			dialog = new KDDetailedAreaDialog(true);
		}
		dialog.setData((String)cell.getValue());
		dialog.setPRENTE_X(p.x + rect.x + rect.width);
		dialog.setPRENTE_Y(p.y + rect.y);
		dialog.setMaxLength(len);
		dialog.setEditable(isEdit);
		dialog.show();
	}
	
	protected void addColumn(KDTable table,ProjectPlanDetialTypeEnum type,int year,int month,int quarter){
		IRow headRow1=table.getHeadRow(0);
		
		KDFormattedTextField txtamount = new KDFormattedTextField();
		txtamount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtamount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtamount.setNegatived(false);
		txtamount.setMinimumValue(FDCHelper.ZERO);
		txtamount.setPrecision(2);
		KDTDefaultCellEditor amount = new KDTDefaultCellEditor(txtamount);
		
		String key=null;
		if(type.equals(ProjectPlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"amount";
		}else if(type.equals(ProjectPlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"amount";
		}
		if(table.getColumn(key)!=null)return;
		IColumn coloum=table.addColumn();
		coloum.setEditor(amount);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.setKey(key);
		if(type.equals(ProjectPlanDetialTypeEnum.MONTH)){
			headRow1.getCell(key).setValue(year+"年"+month+"月"+"金额");
		}else if(type.equals(ProjectPlanDetialTypeEnum.QUARTER)){
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度"+"金额");
		}
	}
	protected BigDecimal getMonthActPayAmount(String id,int year,int month,boolean isAll) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+id+"'");
		if(isAll){
			_builder.appendSql(" and famount is not null");
		}else{
			_builder.appendSql(" and famount is not null and year(fpayDate)="+year+" and month(fpayDate)="+month);
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getMonthActPayAmount(String id,String bgItemId,int year,int month) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(prbEntry.frequestAmount) payAmount from t_cas_paymentbill pb left join T_CON_PayRequestBill prb on prb.fid=pb.fFdcPayReqID ");
		_builder.appendSql(" left join T_CON_PayRequestBillBgEntry prbEntry on prbEntry.FHeadId = prb.fid ");
		_builder.appendSql(" where pb.fbillstatus=15 and pb.fcontractbillid='"+id+"' and prbEntry.fbgItemId='"+bgItemId+"'");
		_builder.appendSql(" and prbEntry.frequestAmount is not null and year(pb.fpayDate)="+year+" and month(pb.fpayDate)="+month);
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.contractType.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
//    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("bizDate");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectYearPlanInfo info=(ProjectYearPlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectYearPlanInfo();
			info.setVersion(1);
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
			for(int i=0;i<info.getTotalEntry().size();i++){
				info.getTotalEntry().get(i).setId(BOSUuid.create(info.getTotalEntry().get(i).getBOSType()));
			}
			for(int i=0;i<info.getTotalBgEntry().size();i++){
				info.getTotalBgEntry().get(i).setId(BOSUuid.create(info.getTotalBgEntry().get(i).getBOSType()));
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
		
		info.setName(null);
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
	}
	protected void btnGet_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getCurProject()==null){
			FDCMsgBox.showWarning(this,"工程项目为空！");
			return;
		}
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.table!=null&&this.table.getRowCount()>0){
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

			ProjectMonthPlanEntryCollection monthplancol=new ProjectMonthPlanEntryCollection();
        	Map entryMap=new HashMap();
        	HashMap monthMap=new HashMap();
        	
        	Set yearSet=new HashSet();
        	Set monthSet=new HashSet();
        	int beginYear=this.spYear.getIntegerVlaue().intValue();
    		int beginMonth=this.spMonth.getIntegerVlaue().intValue();
    		
    		int year=beginYear;
    		for(int i=0;i<39;i++){
    			int quarter=0;
    			beginMonth=beginMonth+1;
    			if(beginMonth>12){
    				beginMonth=1;
    				beginYear=beginYear+1;
    			}
    			yearSet.add(beginYear);
    			if(beginMonth<4){
    				quarter=1;
    			}else if(beginMonth<7){
    				quarter=2;
    			}else if(beginMonth<10){
    				quarter=3;
    			}else if(beginMonth<13){
    				quarter=4;
    			}
    			if(beginYear-year<2){
    				String key=beginYear+"Y"+beginMonth+"M";
        			monthSet.add(key);
    			}else{
    				String key=beginYear+"Y"+quarter+"Q";
        			monthSet.add(key);
    			}
    		}
        	EntityViewInfo view=new EntityViewInfo();
        	FilterInfo filter=new FilterInfo();
        	
        	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
//        	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.id","nN3mgj6sSUiXB4839povCw1t0fQ="));
//        	if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//    			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.respPerson.id", SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString()));
//    		}
        	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
        	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
        	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
        	
        	view.setFilter(filter);
        	view.getSelector().add("*");
        	view.getSelector().add("head.contractBill.programmingContract.*");
        	view.getSelector().add("head.contractBill.contractType.*");
        	view.getSelector().add("head.contractBill.partB.*");
        	view.getSelector().add("head.contractBill.*");
        	SorterItemCollection sort=new SorterItemCollection();
        	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
        	number.setSortType(SortType.ASCEND);
        	sort.add(number);
        	
        	view.setSorter(sort);
        	ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
        	
    		for(int i=0;i<col.size();i++){
    			ContractPayPlanEntryInfo ppEntry=col.get(i);
        		int comyear=ppEntry.getYear();
        		int commonth=ppEntry.getMonth();
        		int quarter=0;
        		if(commonth<4){
    				quarter=1;
    			}else if(commonth<7){
    				quarter=2;
    			}else if(commonth<10){
    				quarter=3;
    			}else if(commonth<13){
    				quarter=4;
    			}
        		String key=comyear+"Y"+quarter+"Q";
        		if(comyear-year<2){
    				key=comyear+"Y"+commonth+"M";
    			}
        		if(!monthSet.contains(key)){
    				continue;
    			}
        		ContractBillInfo contractInfo=ppEntry.getHead().getContractBill();
        		String contractId=contractInfo.getId().toString();
        		BigDecimal amount=ppEntry.getPayAmount();
        		ProjectMonthPlanDateEntryInfo dateEntry=null;
        		ProjectMonthPlanEntryInfo entry=null;
        		
        		if(entryMap.containsKey(contractId)){
        			entry=(ProjectMonthPlanEntryInfo) entryMap.get(contractId);
        			if(monthMap.containsKey(comyear+"Y"+commonth+"M"+contractId)){
        				dateEntry=(ProjectMonthPlanDateEntryInfo) monthMap.get(comyear+"Y"+commonth+"M"+contractId);
    					dateEntry.setAmount(dateEntry.getAmount().add(amount));
        			}else{
        				dateEntry=new ProjectMonthPlanDateEntryInfo();
    					dateEntry.setAmount(amount);
    					dateEntry.setYear(comyear);
    					dateEntry.setMonth(commonth);
    					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    					
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(comyear+"Y"+commonth+"M"+contractId, dateEntry);
        			}
        		}else{
        			entry=new ProjectMonthPlanEntryInfo();
        			entry.setContractBill(contractInfo);
        			entry.setId(ppEntry.getHead().getId());
        			
    				dateEntry=new ProjectMonthPlanDateEntryInfo();
					dateEntry.setAmount(amount);
					dateEntry.setYear(comyear);
					dateEntry.setMonth(commonth);
					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
					
					entry.getDateEntry().add(dateEntry);
					
					monthMap.put(comyear+"Y"+commonth+"M"+contractId, dateEntry);
					entryMap.put(contractId, entry);
					
					monthplancol.add(entry);
        		}
        	}
        	entryMap=new HashMap();
        	
    		for(int i=0;i<monthplancol.size();i++){
    			ProjectMonthPlanEntryInfo ppEntry=monthplancol.get(i);
    			String id=null;
    			if(ppEntry.getContractBill()!=null){
    				id=ppEntry.getContractBill().getId().toString();
    			}else{
    				continue;
    			}
    			ProjectYearPlanEntryInfo entry=null;
    			if(entryMap.containsKey(id)){
    				entry=(ProjectYearPlanEntryInfo) entryMap.get(id);
    				for(int j=0;j<ppEntry.getDateEntry().size();j++){
        				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
        				int comyear=dEntry.getYear();
        				int commonth=dEntry.getMonth();
        				int quarter=0;
                		if(commonth<4){
            				quarter=1;
            			}else if(commonth<7){
            				quarter=2;
            			}else if(commonth<10){
            				quarter=3;
            			}else if(commonth<13){
            				quarter=4;
            			}
                		boolean isquarter=true;
                		if(comyear-year<2){
                			isquarter=false;
                		}
        				boolean isAddNew=true;
        				for(int k=0;k<entry.getDateEntry().size();k++){
        					if((entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getMonth()==commonth)
        							||(isquarter&&entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getQuarter()==quarter)){
        						BigDecimal amount=FDCHelper.ZERO;
        						BigDecimal addAmount=FDCHelper.ZERO;
        						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
        						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
        						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
        						
        						isAddNew=false;
        					}
        				}
        				if(isAddNew){
        					ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setQuarter(quarter);
            				if(comyear-year<2){
                				gdEntry.setType(ProjectPlanDetialTypeEnum.MONTH);
                			}else{
                				gdEntry.setType(ProjectPlanDetialTypeEnum.QUARTER);
                			}
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				entry.getDateEntry().add(gdEntry);
        				}
        			}
    			}else{
    				entry=new ProjectYearPlanEntryInfo();
    				entry.setContractBill(ppEntry.getContractBill());
    				entry.setContractType(ppEntry.getContractBill().getContractType());
    				entry.setProgrammingContract(ppEntry.getContractBill().getProgrammingContract());
    				entry.setId(BOSUuid.create(entry.getBOSType()));
    				
        			for(int j=0;j<ppEntry.getDateEntry().size();j++){
        				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
        				int comyear=dEntry.getYear();
    	        		int commonth=dEntry.getMonth();
    	        		int quarter=0;
    	        		if(commonth<4){
    	    				quarter=1;
    	    			}else if(commonth<7){
    	    				quarter=2;
    	    			}else if(commonth<10){
    	    				quarter=3;
    	    			}else if(commonth<13){
    	    				quarter=4;
    	    			}
    	        		boolean isquarter=true;
                		if(comyear-year<2){
                			isquarter=false;
                		}
    	        		boolean isAddNew=true;
        				for(int k=0;k<entry.getDateEntry().size();k++){
        					if((entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getMonth()==commonth)
        							||(isquarter&&entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getQuarter()==quarter)){
        						BigDecimal amount=FDCHelper.ZERO;
        						BigDecimal addAmount=FDCHelper.ZERO;
        						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
        						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
        						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
        						
        						isAddNew=false;
        					}
        				}
        				if(isAddNew){
        					ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setQuarter(quarter);
            				if(comyear-year<2){
                				gdEntry.setType(ProjectPlanDetialTypeEnum.MONTH);
                			}else{
                				gdEntry.setType(ProjectPlanDetialTypeEnum.QUARTER);
                			}
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				entry.getDateEntry().add(gdEntry);
        				}
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
        	view.getSelector().add("programmingContract.*");
        	view.getSelector().add("contractType.*");
        	view.getSelector().add("name");
        	view.getSelector().add("amount");
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
    			String id=null;
    			if(ppEntry.getProgrammingContract()!=null){
    				id=ppEntry.getProgrammingContract().getId().toString();
    			}else{
    				id=ppEntry.getId().toString();
    			}
    			ProjectYearPlanEntryInfo entry=null;
    			if(entryMap.containsKey(id)){
    				entry=(ProjectYearPlanEntryInfo) entryMap.get(id);
    				for(int j=0;j<ppEntry.getDateEntry().size();j++){
        				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
        				int comyear=dEntry.getYear();
        				int commonth=dEntry.getMonth();
        				int quarter=0;
                		if(commonth<4){
            				quarter=1;
            			}else if(commonth<7){
            				quarter=2;
            			}else if(commonth<10){
            				quarter=3;
            			}else if(commonth<13){
            				quarter=4;
            			}
                		boolean isquarter=true;
                		if(comyear-year<2){
                			isquarter=false;
                		}
        				boolean isAddNew=true;
        				for(int k=0;k<entry.getDateEntry().size();k++){
        					if((entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getMonth()==commonth)
        							||(isquarter&&entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getQuarter()==quarter)){
        						BigDecimal amount=FDCHelper.ZERO;
        						BigDecimal addAmount=FDCHelper.ZERO;
        						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
        						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
        						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
        						
        						isAddNew=false;
        					}
        				}
        				if(isAddNew){
        					ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setQuarter(quarter);
            				if(comyear-year<2){
                				gdEntry.setType(ProjectPlanDetialTypeEnum.MONTH);
                			}else{
                				gdEntry.setType(ProjectPlanDetialTypeEnum.QUARTER);
                			}
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				entry.getDateEntry().add(gdEntry);
        				}
        			}
    			}else{
    				entry=new ProjectYearPlanEntryInfo();
        			entry.setProgrammingContract(ppEntry.getProgrammingContract());
    				entry.setContractType(ppEntry.getContractType());
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			entry.setName(ppEntry.getName());
        			entry.setAmount(ppEntry.getAmount());
        			for(int j=0;j<ppEntry.getDateEntry().size();j++){
        				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
        				int comyear=dEntry.getYear();
    	        		int commonth=dEntry.getMonth();
    	        		int quarter=0;
    	        		if(commonth<4){
    	    				quarter=1;
    	    			}else if(commonth<7){
    	    				quarter=2;
    	    			}else if(commonth<10){
    	    				quarter=3;
    	    			}else if(commonth<13){
    	    				quarter=4;
    	    			}
    	        		boolean isquarter=true;
                		if(comyear-year<2){
                			isquarter=false;
                		}
    	        		boolean isAddNew=true;
        				for(int k=0;k<entry.getDateEntry().size();k++){
        					if((entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getMonth()==commonth)
        							||(isquarter&&entry.getDateEntry().get(k).getYear()==comyear&&entry.getDateEntry().get(k).getQuarter()==quarter)){
        						BigDecimal amount=FDCHelper.ZERO;
        						BigDecimal addAmount=FDCHelper.ZERO;
        						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
        						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
        						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
        						
        						isAddNew=false;
        					}
        				}
        				if(isAddNew){
        					ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setQuarter(quarter);
            				if(comyear-year<2){
                				gdEntry.setType(ProjectPlanDetialTypeEnum.MONTH);
                			}else{
                				gdEntry.setType(ProjectPlanDetialTypeEnum.QUARTER);
                			}
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				entry.getDateEntry().add(gdEntry);
        				}
        			}
        			this.editData.getEntry().add(entry);
        			
        			entryMap.put(id, entry);
    			}
    		}
    		beginYear=this.spYear.getIntegerVlaue().intValue();
    		beginMonth=this.spMonth.getIntegerVlaue().intValue();
    		
    		createTable(this.editData,beginYear,beginMonth-1,39);
        }
	}
	
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCurProject);
	}
	protected boolean checkBizDate() throws BOSException{
		int comYear=this.spYear.getIntegerVlaue().intValue();
		int comMonth=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, comYear);
		cal.set(Calendar.MONTH, comMonth-1);
		cal.set(Calendar.DATE, 1);
		Date begin=FDCDateHelper.getDayBegin(cal.getTime());
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ProjectYearPlanCollection col=ProjectYearPlanFactory.getRemoteInstance().getProjectYearPlanCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目年度资金计划-版本号"+col.get(0).getVersion()+"还未审批！");
    		return true;
    	}else{
    		if(col.size()==0){
    			return false;
    		}else if(col.get(0).getVersion()==this.txtVersion.getIntegerValue()){
    			FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目年度资金计划-版本号"+col.get(0).getVersion()+"已存在！");
        		return true;
    		}
    	}
    	return false;
	}
	protected void verifyInputForSubmint() throws Exception {
		if(checkBizDate()){
			SysUtil.abort();
		}
		verifyInputForSave();
		//modify by yxl 20160118 提交时校验“当前进度说明”列必录
		for(int i=0; i<table.getRowCount(); i++){
			IRow row = table.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(rowBg)){
				continue;
			}
			if(FDCHelper.isEmpty(row.getCell("currentProgress").getValue())){
				MsgBox.showWarning("第["+(i+1)+"]行的当前进度说明不能为空！");
				SysUtil.abort();
			}
		}
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void reGetValue() throws BOSException, SQLException{
		if(isLoad){
			return;
		}
		if(checkBizDate()){
			this.spYear.setValue(year_old, false);
			this.spMonth.setValue(month_old,false);
			return;
		}
		int result = MsgBox.OK;
		if (table!=null&&table.getRowCount() > 0) {
			result = FDCMsgBox.showConfirm2(this,"改变计划编制月份将重新获取分录数据！");
		}
		if (result == MsgBox.OK) {
			year_old = this.spYear.getIntegerVlaue().intValue();
			month_old=this.spMonth.getIntegerVlaue().intValue();
			this.editData.getEntry().clear();
			try {
				this.btnGet_actionPerformed(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.spYear.setValue(year_old, false);
			this.spMonth.setValue(month_old,false);
			return;
		}
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
//		if(table.getColumnKey(e.getColIndex()).equals("currentProgress")){
//			KDDetailedAreaUtil.setWrapFalse(table.getCell(e.getRowIndex(), e.getColIndex()));
//			ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo)table.getRow(e.getRowIndex()).getUserObject();
//			entry.setCurrentProgress((String)table.getCell(e.getRowIndex(), e.getColIndex()).getValue());
////			if(entry!=null)
////				entry.setAmount(1)
//		}
		if(table.getColumnKey(e.getColIndex()).equals("number")){
			ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
			ProgrammingContractInfo pro=(ProgrammingContractInfo) table.getRow(e.getRowIndex()).getCell("number").getValue();
			String name=null;
			BigDecimal amount=null;
			ContractTypeInfo ct=null;
			if(pro!=null){
				name=pro.getName();
				amount=pro.getAmount();
//				if(pro.getContractType()!=null){
//					try {
//						ct=ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(pro.getContractType().getId()));
//					} catch (EASBizException e1) {
//						e1.printStackTrace();
//					} catch (BOSException e1) {
//						e1.printStackTrace();
//					}	
//				}
				table.getRow(e.getRowIndex()).getCell("name").setValue(name);
				table.getRow(e.getRowIndex()).getCell("amount").setValue(amount);
//				try {
//					if(pro!=null){
//						table.getRow(e.getRowIndex()).getCell("actPayAmount").setValue(getActPayAmount(pro.getId().toString()));
//					}else{
//						table.getRow(e.getRowIndex()).getCell("actPayAmount").setValue(null);
//					}
//				} catch (BOSException e1) {
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
				entry.setProgrammingContract(pro);
//				entry.setContractType(ct);
			}
		}
	}
	public void actionExportExcel_actionPerformed(ActionEvent e)throws Exception {
		List tableList=new ArrayList();
		Object objs[]=new Object[2];
		objs[0]="资金计划明细";
		objs[1]=table;
		tableList.add(objs);
		FDCTableHelper.exportExcel(this,tableList);
	}
	
}