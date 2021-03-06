/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.core.client.f7.F7AdaptorBuilderParam;
import com.kingdee.eas.base.core.client.f7.F7ContextInitializationHelper;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisCollection;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo;
import com.kingdee.eas.fdc.aimcost.IForecastChangeVis;
import com.kingdee.eas.fdc.aimcost.client.AbstractForecastChangeVisEditUI;
import com.kingdee.eas.fdc.aimcost.client.ForecastChangeVisEditUI;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrySixMonthCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrySixMonthInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.fm.ecore.app.bean.commercialdraft.ContractInformation;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.context.f7.RegisterF7Param;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;

/**
 * output class name
 */
public class ProjectDynamicCostEditUI extends AbstractProjectDynamicCostEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDynamicCostEditUI.class);
    private int maxLevel = 1;
    Map parentAccountMap = new HashMap();
    Map programUnSignContractMap = new HashMap();
    BigDecimal ONE_HUNDRUD = new BigDecimal(100);
    BigDecimal ONE_MILLION = new BigDecimal(1000000);
    BigDecimal HALF_ONE_MILLION = new BigDecimal(500000);
    BigDecimal THREE = new BigDecimal(300000);
    String[][] columns = new String[][]{
    		{"firstAimCost", "firstDynaCost", "firstDiffAmount", "firstDiffRate"},
    		{"secondAimCost", "secondDynaCost", "secondDiffAmount", "secondDiffRate"},
    		{"thirdAimCost", "thirdDynaCost", "thirdDiffAmount", "thirdDiffRate"},
    		{"fourthAimCost", "fourthDynaCost", "fourthDiffAmount", "fourthDiffRate"},
    		{"fifthAimCost", "fifthDynaCost", "fifthDiffAmount", "fifthDiffRate"},
    		{"sixthAimCost", "sixthDynaCost", "sixthDiffAmount", "sixthDiffRate"}
    		};
    /**
     * output class constructor
     */
    public ProjectDynamicCostEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	this.contDescription.setVisible(false);
    	this.contstate.setVisible(false);
    	this.contauditTime.setEnabled(false);
    	this.contversion.setEnabled(false);
    	this.contBizDate.setEnabled(false);
    	this.prmtcurProject.setEnabled(false);
    	this.chkisLatest.setVisible(false);
    	this.kdtEntryPosition.setVisible(false);
    	this.kdtEntryPosition_detailPanel.setVisible(false);
    	initTableControl();
    	this.kDTable1.checkParsed();
    	this.kDTable2.checkParsed();
    	this.kDTable3.checkParsed();
    	this.kDTable4.checkParsed();
    	this.kDTable5.checkParsed();
    	this.kDTable6.checkParsed();
    	
    	if(getOprtState().equals(OprtState.VIEW))
    		this.btnLoadData.setEnabled(false);
//    	this.spYear.setEnabled(false);
//    	this.spMonth.setEnabled(false);
    	this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
    	this.btnUnAduit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
    	this.btnRevise.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
    	super.onLoad();
    	if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
    	this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		chkMenuItemSubmitAndAddNew.setSelected(false);
    	menuSubmitOption.setEnabled(false);
    	
//    	this.kDTabbedPane2.remove(6);//隐藏存储用的分录
    	this.kDTabbedPane2.remove(5);
    	this.kDTabbedPane2.remove(4);
    	this.kDTabbedPane2.remove(3);
    	this.kDTabbedPane2.remove(2);
    	this.kDTabbedPane2.remove(1);
    	this.kDTabbedPane2.remove(0);
    	kdtEntrysAccount.getColumn("diffRate").getStyleAttributes().setNumberFormat("0.00%");
    	kdtEntrysAccount.getColumn("alertIndex").getStyleAttributes().setNumberFormat("0.00%");
    	
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	int count = 0;
    	while(count < 6) {
    		month = month-1;
    		if(month <= 0) {
    			month = 12;
    			year = year-1;
    		}
    		String key = year+"年"+month+"月";
    		for(int i = 0; i < columns[count].length; i++) {
    			kdtEntrySixMonth.getHeadRow(0).getCell(columns[count][i]).setValue(key);
    		}
    		count++;
    	}
    	
//    	final EntityViewInfo evi = new EntityViewInfo();
//        final FilterInfo filter = new FilterInfo();
//        filter.getFilterItems().add(new FilterItemInfo("CU.id", (Object)SysContext.getSysContext().getCurrentCtrlUnit().getId()));
//        evi.setFilter(filter);
//        initF7Position(prmtfirstLevelPos, evi);
//        initF7Position(prmtsecondLevelPos, evi);
//        initF7Position(prmtthirdLevelPos, evi);
    	AdminOrgUnitInfo adminUnit = SysContext.getSysContext().getCurrentAdminUnit();
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	SorterItemCollection sic = new SorterItemCollection();
    	sic.add(new SorterItemInfo("number"));
    	evi.setSorter(sic);
    	filter.getFilterItems().add(new FilterItemInfo("adminOrgUnit.longnumber", adminUnit.getLongNumber()+"%", CompareType.LIKE));
    	prmtfirstLevelPos.setEntityViewInfo(evi);
    	prmtsecondLevelPos.setEntityViewInfo(evi);
    	prmtthirdLevelPos.setEntityViewInfo(evi);
    	
    	this.btnCopy.setVisible(false);//隐藏复制按钮
    	
    	spYear.setEnabled(false);
    	spMonth.setEnabled(false);
    }
    /**
     * 职位树状显示(只能单选)
     * @param box
     * @param evi
     * @throws BOSException
     */
    private void initF7Position(KDBizPromptBox box, EntityViewInfo evi) throws BOSException {
    	final RegisterF7Param f7Param = new RegisterF7Param(box, "com.kingdee.eas.basedata.org.app.PositionQuery", OrgType.Admin);
        final F7AdaptorBuilderParam f7AdaptorBuilderParam = new F7AdaptorBuilderParam("com.kingdee.eas.hr.base.client.f7.CustomizePositionF7AdaptorBuilder", new Class[] { IUIObject.class }, new Object[] { this });
        F7ContextInitializationHelper.initF7Context(f7Param, f7AdaptorBuilderParam);
        box.setHasCUDefaultFilter(false);
        box.setEntityViewInfo(evi);
        box.setEditable(false);
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        showTableTreeStyle();
        setContractTableMerge();
        restorePosition();
    }
    /**
     * 校验是否存在日期为当前年月的单据
     */
    private void checkRepeatBill() throws Exception{
    	int year = spYear.getIntegerVlaue().intValue();
    	int month = spMonth.getIntegerVlaue().intValue();
    	String projectId = editData.getCurProject().getId().toString();
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("year", year));
    	filter.getFilterItems().add(new FilterItemInfo("month", month));
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectId));
    	
    	if(ProjectDynamicCostFactory.getRemoteInstance().exists(filter)) {
    		MsgBox.showWarning("已存在日期为"+year+"年"+month+"月的单据!请对已存在单据修订!");
    		SysUtil.abort();
    	}
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 设置合同维度根据合约规划来融合
     */
    private void setContractTableMerge() {
    	String[] mergeColumn = new String[] {"programmingName", "Amount", "sumB", "unSignContract", "sumC", "dynamicCost", "notextContract","diffAmount","diffRate"}; 
    	for(int i = 0; i < kdtEntrysContract.getRowCount();) {
    		IRow row = kdtEntrysContract.getRow(i);
    		int count = 0;
    		int top = i;
    		String programmingId = row.getCell("programmingId").getValue().toString();
    		for(int j = top+1; j < kdtEntrysContract.getRowCount(); j++) {
    			IRow rowNext = kdtEntrysContract.getRow(j);
    			String nextProgarmId = rowNext.getCell("programmingId").getValue().toString();
    			if(nextProgarmId.equals(programmingId)) {
    				count++;
    			} else {
    				break;
    			}
    		}
    		int bottom = top+count;
    		for(int x = 0; x < mergeColumn.length; x++) {
    			int col = kdtEntrysContract.getColumnIndex(mergeColumn[x]);
    			kdtEntrysContract.getMergeManager().mergeBlock(top, col, bottom, col);
    		}
    		i = bottom+1;
    	}
    }
    /**
     * 锁定表格
     */
    private void initTableControl() {
    	this.kdtEntrysContract.getStyleAttributes().setLocked(true);
    	this.kdtEntrysAccount.getStyleAttributes().setLocked(true);
    	this.kdtEntrysAccount.getColumn("seq").getStyleAttributes().setHided(true);
    	this.kdtEntrysAccount.getColumn("level").getStyleAttributes().setHided(true);
    	this.kdtEentryTotal.getColumn("seq").getStyleAttributes().setHided(true);
    	this.kdtEntrySixMonth.getColumn("seq").getStyleAttributes().setHided(true);
    	
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	this.kDTable2.getStyleAttributes().setLocked(true);
    	this.kDTable3.getStyleAttributes().setLocked(true);
    	this.kDTable4.getStyleAttributes().setLocked(true);
    	this.kDTable5.getStyleAttributes().setLocked(true);
    	this.kDTable6.getStyleAttributes().setLocked(true);
    	this.kdtEentryTotal.getStyleAttributes().setLocked(true);
    	this.kdtEntrySixMonth.getStyleAttributes().setLocked(true);
    	
    	this.kDContainer2.getContentPane().add(kdtEntrysContract);
    	this.kDContainer1.getContentPane().add(kdtEntrysAccount);
    	this.kDContainer3.getContentPane().add(kdtEentryTotal);
    	this.kDContainer4.getContentPane().add(kdtEntrySixMonth);
    	
    	
    	this.kdtEntrysContract.getColumn("curMonthOtherId").getStyleAttributes().setHided(true);
    	this.kdtEntrysContract.getColumn("ForecastChangeVisID").getStyleAttributes().setHided(true);
    	this.kdtEntrysContract.getColumn("programmingId").getStyleAttributes().setHided(true);
    	
    	this.kdtEntrysAccount.getColumn("Comment").getStyleAttributes().setLocked(false);
    	this.kdtEntrysAccount.getColumn("accountIndex").getStyleAttributes().setHided(true);
//    	this.kdtEntrysAccount.getColumn("level").getStyleAttributes().setHided(true);
    }
    /**
     * 将对应编码的数据提取到对应的tab
     * @param table
     * @param codingNumber
     */
    private void loadDataToTab(KDTable table, String codingNumber) {
    	
    }
    /**
     * 获取第一版目标成本数据
     * @param info
     * @param codingNumber
     * @return
     * @throws Exception
     */
    private BigDecimal getFirstVersionAimCost(CurProjectInfo info,String codingNumber) throws Exception{
    	BigDecimal aimCost = BigDecimal.ZERO;
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select sum(目标成本) 目标成本");
    	sb.append(" from (");
    	sb.append(" select account.fcodingnumber 科目编码, sum(entry.FCostAmount) 目标成本 ");
    	sb.append(" from T_AIM_CostEntry entry");
    	sb.append(" left join T_AIM_AimCost aimCost on aimCost.fid=entry.fheadid");
    	sb.append(" left join T_FDC_CostAccount account on account.fid=entry.fcostaccountid");
    	sb.append(" where 1=1");
    	sb.append(" and aimCost.FOrgOrProId='"+info.getId().toString()+"'");
    	sb.append(" and TO_NUMBER(aimCost.fversionnumber)=1");
    	sb.append(" and account.fcodingnumber like '"+codingNumber+"%'");
    	sb.append(" group by account.fcodingnumber");
    	sb.append(" order by account.fcodingnumber");
    	sb.append(" )");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(sb.toString());
    	IRowSet rowSet = builder.executeQuery();
    	
    	while(rowSet.next()) {
    		BigDecimal aim = rowSet.getBigDecimal("目标成本");
    		aimCost = aim == null ? aimCost : aim;
    		break;
    	}
    	return aimCost;
    }
    /**
     * 加载汇总表
     * @throws Exception
     */
    private void loadTotalTableData() throws Exception{
    	kdtEentryTotal.removeRows();
    	Map totalMap = new TreeMap();
    	for(int i = 0; i < kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = kdtEntrysAccount.getRow(i);
    		Map detailMap = new HashMap();
    		String accNumber = (String) row.getCell("costAccountNumber").getValue();
    		int level = (Integer)row.getCell("level").getValue();
    		detailMap.put("costAccountNumber", accNumber);
    		detailMap.put("costAccount", row.getCell("costAccount").getValue());
    		detailMap.put("aimCost", row.getCell("aimCost").getValue());
    		detailMap.put("Contract", (BigDecimal)row.getCell("Contract").getValue());
    		detailMap.put("designChangeAmount", (BigDecimal)row.getCell("designChangeAmount").getValue());
    		detailMap.put("siteCertificatAmount", (BigDecimal)row.getCell("siteCertificatAmount").getValue());
    		detailMap.put("settlementAmount", (BigDecimal)row.getCell("settlementAmount").getValue());
    		detailMap.put("withouttxt", (BigDecimal)row.getCell("withouttxt").getValue());
    		detailMap.put("sumB", (BigDecimal)row.getCell("sumB").getValue());
    		detailMap.put("onWayCost", (BigDecimal)row.getCell("onWayCost").getValue());
    		detailMap.put("EstimateChangeBalance", (BigDecimal)row.getCell("EstimateChangeBalance").getValue());
    		detailMap.put("otherAmount", (BigDecimal)row.getCell("otherAmount").getValue());
    		detailMap.put("unSignContract", (BigDecimal)row.getCell("unSignContract").getValue());
    		detailMap.put("sumC", (BigDecimal)row.getCell("sumC").getValue());
    		detailMap.put("sumBC", (BigDecimal)row.getCell("sumBC").getValue());
    		detailMap.put("diffAmount", (BigDecimal)row.getCell("diffAmount").getValue());
    		detailMap.put("diffRate", (BigDecimal)row.getCell("diffRate").getValue());
    		detailMap.put("Comment", (BigDecimal)row.getCell("Comment").getValue());
    		
    		if(level == 2)
    			totalMap.put(accNumber, detailMap);
    	}
    	Set keySet = totalMap.keySet();
    	for(Iterator it = keySet.iterator(); it.hasNext();) {
    		String number = it.next().toString();
    		Map detail = (Map) totalMap.get(number);
    		IRow addRow = kdtEentryTotal.addRow();
    		addRow.getCell("costAccount").setValue(detail.get("costAccount"));
    		addRow.getCell("costAccountNumber").setValue(detail.get("costAccountNumber"));
    		BigDecimal aimCost = detail.get("aimCost") == null ? BigDecimal.ZERO : (BigDecimal)detail.get("aimCost");
    		addRow.getCell("aimCost").setValue(detail.get("aimCost"));
    		BigDecimal firstVersionAimCost = getFirstVersionAimCost(editData.getCurProject(), number);
    		addRow.getCell("firstVersionAimcost").setValue(firstVersionAimCost);
    		addRow.getCell("deletaAimcost").setValue(aimCost.subtract(firstVersionAimCost));
    		
    		addRow.getCell("Contract").setValue(detail.get("Contract"));
    		addRow.getCell("designChangeAmount").setValue(detail.get("designChangeAmount"));
    		addRow.getCell("siteCertificatAmount").setValue(detail.get("siteCertificatAmount"));
    		addRow.getCell("settlementAmount").setValue(detail.get("settlementAmount"));
    		addRow.getCell("withouttxt").setValue(detail.get("withouttxt"));
    		addRow.getCell("sumB").setValue(detail.get("sumB"));
    		addRow.getCell("onWayCost").setValue(detail.get("onWayCost"));
    		addRow.getCell("EstimateChangeBalance").setValue(detail.get("EstimateChangeBalance"));
    		addRow.getCell("otherAmount").setValue(detail.get("otherAmount"));
    		addRow.getCell("unSignContract").setValue(detail.get("unSignContract"));
    		addRow.getCell("sumC").setValue(detail.get("sumC"));
    		addRow.getCell("sumBC").setValue(detail.get("sumBC"));
    		addRow.getCell("diffAmount").setValue(detail.get("diffAmount"));
    		addRow.getCell("diffRate").setValue(detail.get("diffRate"));
    		addRow.getCell("Comment").setValue(detail.get("Comment"));
    	}
    }
    /**
     * 近六个月汇总表
     * @param info
     * @throws Exception 
     */
    private void loadSixMonthData(CurProjectInfo info) throws Exception {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	int count = 0;
    	kdtEntrySixMonth.removeRows();
//    	Map columnMap = new TreeMap();
    	while(count < 6) {
    		month = month-1;
    		if(month <= 0) {
    			month = 12;
    			year = year-1;
    		}
    		String key = year+"年"+month+"月";
//    		columnMap.put(key, columns[count]);
    		Map preTotalData = getPreTotalData(info, year, month);
    		for(int i = 0; i < columns[count].length; i++) {
    			kdtEntrySixMonth.getHeadRow(0).getCell(columns[count][i]).setValue(key);
    		}
    		if(preTotalData.size() > 0) {
    			if(kdtEntrySixMonth.getRowCount() <= 0) {
    				Set keySet = preTotalData.keySet();
    				for(Iterator it = keySet.iterator(); it.hasNext();) {
    					String accountNumber = (String) it.next();
    					Map detail = (Map) preTotalData.get(accountNumber);
    					IRow addRow = kdtEntrySixMonth.addRow();
    					addRow.getCell("costAccount").setValue((String)detail.get("CFCOSTACCOUNT"));
    					addRow.getCell("costAccountNumber").setValue((String)detail.get("CFCOSTACCOUNTNUMBER"));
    					addRow.getCell(columns[count][0]).setValue((BigDecimal)detail.get("CFAIMCOST"));
    					addRow.getCell(columns[count][1]).setValue((BigDecimal)detail.get("CFSUMBC"));
    					addRow.getCell(columns[count][2]).setValue((BigDecimal)detail.get("CFDIFFAMOUNT"));
    					addRow.getCell(columns[count][3]).setValue((BigDecimal)detail.get("CFDIFFRATE"));
    				}
    			} else {
    				for(int i=0; i < kdtEntrySixMonth.getRowCount(); i++) {
    					IRow row = kdtEntrySixMonth.getRow(i);
    					String accountNumber = (String) row.getCell("costAccountNumber").getValue();
    					Map detail = (Map) preTotalData.get(accountNumber);
    					row.getCell("costAccount").setValue((String)detail.get("CFCOSTACCOUNT"));
    					row.getCell("costAccountNumber").setValue((String)detail.get("CFCOSTACCOUNTNUMBER"));
    					row.getCell(columns[count][0]).setValue((BigDecimal)detail.get("CFAIMCOST"));
    					row.getCell(columns[count][1]).setValue((BigDecimal)detail.get("CFSUMBC"));
    					row.getCell(columns[count][2]).setValue((BigDecimal)detail.get("CFDIFFAMOUNT"));
    					row.getCell(columns[count][3]).setValue((BigDecimal)detail.get("CFDIFFRATE"));
    				}
    			}
    		}
    		count++;
    	}
    }
    /**
     * 获取指定年月的最新版汇总页签数据
     * @param info
     * @param year
     * @param month
     * @return
     * @throws Exception 
     */
    private Map getPreTotalData(CurProjectInfo info, int year, int month) throws Exception {
    	Map map = new TreeMap();
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select total.CFCOSTACCOUNT,total.CFCOSTACCOUNTNUMBER,total.CFAIMCOST,total.CFSUMBC, total.CFDIFFAMOUNT, total.CFDIFFRATE  ");
    	sb.append(" from CT_AIM_ProjectDCET total");
    	sb.append(" left join CT_AIM_ProjectDynamicCost head on total.fparentid=head.fid");
    	sb.append(" where 1=1");
    	sb.append(" and head.cfcurprojectid='"+info.getId().toString()+"'");
    	sb.append(" and head.cfislatest='1'");
    	sb.append(" and head.fyear='"+year+"' and head.fmonth='"+month+"'");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(sb.toString());
    	IRowSet rowSet = builder.executeQuery();
    	
    	while(rowSet.next()) {
    		Map detailMap = new HashMap();
    		String number = rowSet.getString("CFCOSTACCOUNTNUMBER");
    		detailMap.put("CFCOSTACCOUNT", rowSet.getString("CFCOSTACCOUNT"));
    		detailMap.put("CFCOSTACCOUNTNUMBER", rowSet.getString("CFCOSTACCOUNTNUMBER"));
    		detailMap.put("CFAIMCOST", rowSet.getBigDecimal("CFAIMCOST")); //目标成本
    		detailMap.put("CFSUMBC", rowSet.getBigDecimal("CFSUMBC"));//动态成本
    		detailMap.put("CFDIFFAMOUNT", rowSet.getBigDecimal("CFDIFFAMOUNT"));//成本差额
    		detailMap.put("CFDIFFRATE", rowSet.getBigDecimal("CFDIFFRATE"));//差异率
    		
    		map.put(number, detailMap);
    	}
    	return map;
    }
    /**
     * 提取最新数据按钮
     */
    protected void btnLoadData_actionPerformed(ActionEvent e) throws Exception {
    	//提取最新数据
    	if(kdtEntrysContract.getRowCount() > 0) {
    		int confirm = MsgBox.showConfirm2("是否更新最新数据?");
    		if(confirm != MsgBox.YES) {
    			SysUtil.abort();
    		}
    	}
    	loadContractTableData();
    	loadCostAccountTableData();
    	loadTotalTableData();
    	loadSixMonthData(editData.getCurProject());
    }
    
    /**
     * 加载合同维度数据
     * @throws Exception
     */
    private void loadContractTableData() throws Exception{
    	kdtEntrysContract.removeRows();

    	String contractSql = getContractSql(editData.getCurProject());
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(contractSql);
    	IRowSet rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		IRow addRow = kdtEntrysContract.addRow();
    		addRow.getCell("programmingName").setValue(rowSet.getString("programNAme"));
    		addRow.getCell("programmingId").setValue(rowSet.getString("programId"));
    		addRow.getCell("ContractBillId").setValue(rowSet.getString("contractId"));
    		addRow.getCell("Contract").setValue(rowSet.getString("contractName"));
    		addRow.getCell("Amount").setValue(rowSet.getBigDecimal("programAmount"));
    		addRow.getCell("ContractAmount").setValue(rowSet.getBigDecimal("contractAmount") == null ? BigDecimal.ZERO : rowSet.getBigDecimal("contractAmount"));
    		addRow.getCell("onWayCost").setValue(rowSet.getBigDecimal("onWayAmount") == null ? BigDecimal.ZERO:rowSet.getBigDecimal("onWayAmount"));
    	}
    	String noTextContractSql = getNoTextContractSql(editData.getCurProject());
    	Map noContractMap = new HashMap();
    	builder.clear();
    	builder.appendSql(noTextContractSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		noContractMap.put(rowSet.getString("programmingId"), rowSet.getBigDecimal("amount"));
    	}
    	//设计变更
    	String conChangeSql = getConChangeSql(editData.getCurProject(), "'工程指令', '技术核定', '设计变更'");
    	Map changeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(conChangeSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
//    		Map detail = new HashMap();
    		BigDecimal changeAmount = rowSet.getBigDecimal("changeAmount");
    		changeMap.put(rowSet.getString("contractId"), changeAmount);
    	}
    	//现场签证
    	String siteChangeSql = getConChangeSql(editData.getCurProject(), "'技术经济签证'");
    	Map siteChangeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(siteChangeSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		BigDecimal siteChangeAmount = rowSet.getBigDecimal("changeAmount");
    		siteChangeMap.put(rowSet.getString("contractId"), siteChangeAmount);
    	}
    	//已发生结算金额
    	String settmentSql = getSettmentSql(editData.getCurProject(), 1);
    	Map settleMap = new HashMap();
    	builder.clear();
    	builder.appendSql(settmentSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		BigDecimal settleAmount = rowSet.getBigDecimal("settleAmount");
    		settleMap.put(rowSet.getString("contractId"), settleAmount);
    	}
//    	//预估变更
//    	String forecasetChangeSql = getForecasetChangeSql(editData.getCurProject());
//    	Map forecasetChangeMap = new HashMap();
//    	builder.clear();
//    	builder.appendSql(forecasetChangeSql);
//    	rowSet = builder.executeQuery();
//    	while(rowSet.next()) {
//    		Map detailMap = new HashMap();
//    		detailMap.put("forecastChangeAmount", rowSet.getBigDecimal("forecastChangeAmount"));
//    		detailMap.put("forecastChangeId", rowSet.getString("forecastChangeId"));
//    		forecasetChangeMap.put(rowSet.getString("contractId"), detailMap);
//    	}
//    	//预估变更实际发生
//    	String actForecastSql = getgetForecasetChangeBalanceSql(editData.getCurProject());
//    	Map actForeCastMap = new HashMap();
//    	builder.clear();
//    	builder.appendSql(actForecastSql);
//    	rowSet = builder.executeQuery();
//    	while(rowSet.next()) {
//    		actForeCastMap.put(rowSet.getString("contractId"), rowSet.getBigDecimal("costAmount"));
//    	}
    	//本月其他调整
    	String curMonthOtherSql = getCurMonthOtherSql(editData.getCurProject());
    	Map otherChangeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(curMonthOtherSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		Map detailMap = new HashMap();
    		detailMap.put("adjustAmount", rowSet.getBigDecimal("adjustAmount"));
    		detailMap.put("billId", rowSet.getString("billId"));
    		detailMap.put("comment", rowSet.getString("fcomment"));
    		otherChangeMap.put(rowSet.getString("contractId"), detailMap);
    	}
    	//截至本月初所有其他调整
    	String allOtherSql = getAllOtherSql(editData.getCurProject());
    	Map allOtherMap = new HashMap();
    	builder.clear();
    	builder.appendSql(allOtherSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		BigDecimal adjustAmount = rowSet.getBigDecimal("adjustAmount");
    		allOtherMap.put(rowSet.getString("contractId"), adjustAmount);
    	}

    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
    		BigDecimal changeAmount = BigDecimal.ZERO;
    		BigDecimal siteChangeAmount = BigDecimal.ZERO;
    		BigDecimal settleAmount = BigDecimal.ZERO;
    		BigDecimal forecasetChangeamount = BigDecimal.ZERO;
    		BigDecimal otherAmount = BigDecimal.ZERO;
    		IRow row = kdtEntrysContract.getRow(i);
    		String contractId = row.getCell("ContractBillId").getValue().toString();
    		changeAmount = (BigDecimal) changeMap.get(contractId);
    		row.getCell("designChangeAmount").setValue(changeAmount==null ? BigDecimal.ZERO : changeAmount);
    		siteChangeAmount = (BigDecimal) siteChangeMap.get(contractId);
    		row.getCell("siteCertificatAmount").setValue(siteChangeAmount==null ? BigDecimal.ZERO : siteChangeAmount);
    		settleAmount = (BigDecimal) settleMap.get(contractId);
    		row.getCell("settlementAmount").setValue(settleAmount==null ? BigDecimal.ZERO : settleAmount);
    		//如果有结算金额 则合同、设计变更、现场签证金额为0
    		if(settleAmount != null) {
    			row.getCell("ContractAmount").setValue(BigDecimal.ZERO);
    			row.getCell("designChangeAmount").setValue(BigDecimal.ZERO);
    			row.getCell("siteCertificatAmount").setValue(BigDecimal.ZERO);
    		}
    		
    		BigDecimal noTextContract = (BigDecimal) noContractMap.get(row.getCell("programmingId").getValue().toString());
    		row.getCell("notextContract").setValue(noTextContract == null ? BigDecimal.ZERO : noTextContract);
    		ForecastChangeVisInfo forecastInfo = getForecastChangeVisInfoByContract(null, contractId);
    		ForecastChangeVisInfo preForecastInfo = getPreForecastChangeVisInfoByContract(null, contractId);
    		//预估变更
    		BigDecimal actForecastAmount = getgetForecasetChangeBalanceSql(editData.getCurProject(), contractId);//变更实际发生额
    		if(forecastInfo != null) {
    			forecasetChangeamount = forecastInfo.getAmount();
    			if(preForecastInfo != null) {
    				row.getCell("curMonthEstimateChange").setValue(forecasetChangeamount.subtract(preForecastInfo.getAmount()));
    			} else {
    				row.getCell("curMonthEstimateChange").setValue(forecasetChangeamount);
    			}
    			row.getCell("ForecastChangeVisID").setValue(forecastInfo.getId().toString());
    			if(actForecastAmount != null) {
    				BigDecimal balance = forecasetChangeamount.subtract(actForecastAmount);
    				if(!forecastInfo.isBanZreo()) //是否余额清零
    					row.getCell("EstimateChangeBalance").setValue(balance);
    				else
    					row.getCell("EstimateChangeBalance").setValue(BigDecimal.ZERO);
    			}
    		} else {
    			row.getCell("curMonthEstimateChange").setValue(BigDecimal.ZERO);
    			actForecastAmount = actForecastAmount == null ? BigDecimal.ZERO : actForecastAmount;
    			if(preForecastInfo != null && !preForecastInfo.isBanZreo()) //余额归零则余额为0
    				row.getCell("EstimateChangeBalance").setValue(preForecastInfo.getAmount().subtract(actForecastAmount));
    			else
    				row.getCell("EstimateChangeBalance").setValue(BigDecimal.ZERO);
    		}
    		//其他调整
    		Map otherDetailMap = (Map) otherChangeMap.get(row.getCell("ContractBillId").getValue().toString());
    		if(otherDetailMap != null) {
    			otherAmount = (BigDecimal) otherDetailMap.get("adjustAmount");
    			row.getCell("curMonthOtherAmount").setValue(otherAmount==null ? BigDecimal.ZERO : otherAmount);
    			row.getCell("curMonthOtherId").setValue(otherDetailMap.get("billId"));
    			row.getCell("Comment").setValue(otherDetailMap.get("comment"));
    		} else {
    			row.getCell("curMonthOtherAmount").setValue(BigDecimal.ZERO);
    		}
    		BigDecimal allOtherAmount = (BigDecimal) allOtherMap.get(row.getCell("ContractBillId").getValue().toString());
    		row.getCell("otherAmount").setValue(allOtherAmount==null ? BigDecimal.ZERO : allOtherAmount);
    	}
    	calculateSum();
    }
    /**
     * 科目维度table 树状显示
     */
    private void showTableTreeStyle() {
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	String parentCostAccountSql = getParentCostAccountSql(editData.getCurProject(), "4101");
    	builder.appendSql(parentCostAccountSql);
    	IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			while(rowSet.next()) {
				parentAccountMap.put(rowSet.getString("科目编码"), rowSet.getInt("级次"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	for(int i = 0; i < kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = kdtEntrysAccount.getRow(i);
    		Integer level = (Integer) row.getCell("level").getValue();
    		row.setTreeLevel(level.intValue()-1);
    		if(maxLevel < level.intValue())
    			maxLevel = level.intValue();
    		Integer parentLevel = (Integer) parentAccountMap.get(row.getCell("costAccountNumber").getValue());
    		if(parentLevel != null) {
    			if(parentLevel.intValue() == 1)
    				row.getStyleAttributes().setBackground(Color.CYAN);
    			else if(parentLevel.intValue() == 2)
    				row.getStyleAttributes().setBackground(Color.ORANGE);
    			else if(parentLevel.intValue() == 3)
    				row.getStyleAttributes().setBackground(Color.YELLOW);
    			else if(parentLevel.intValue() == 4)
    				row.getStyleAttributes().setBackground(Color.PINK);
    		}
    	}
    	kdtEntrysAccount.getTreeColumn().setDepth(maxLevel);
    }
    
    /**
     * 获取合约规划
     * @param id
     * @return
     * @throws Exception
     */
    private ProgrammingContractInfo getProgrammingContract(String id) throws Exception {
    	IProgrammingContract iPContract = ProgrammingContractFactory.getRemoteInstance();
    	SelectorItemCollection sic= new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("id"));
    	sic.add(new SelectorItemInfo("iscse"));
    	sic.add(new SelectorItemInfo("costEntries.*"));
    	sic.add(new SelectorItemInfo("costEntries.costAccount.*"));
    	if(iPContract.exists("where id='"+id+"'"))
    		return iPContract.getProgrammingContractInfo(new ObjectUuidPK(id),sic);
    	return null;
    }
    /**
     * 加载科目维度数据
     * @throws BOSException 
     */
    private void loadCostAccountTableData() throws Exception {
    	kdtEntrysAccount.removeRows();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	CurProjectInfo curProject = editData.getCurProject();
    	//加载科目树 
    	String costAccountSql = getCostAccountSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(costAccountSql);
    	IRowSet rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		IRow addRow = kdtEntrysAccount.addRow();
    		addRow.getCell("costAccount").setValue(rowSet.getString("科目名称"));
    		addRow.getCell("costAccountNumber").setValue(rowSet.getString("科目编码"));
    		int level = rowSet.getInt("级次");
    		addRow.getCell("level").setValue(level);
    		addRow.setTreeLevel(rowSet.getInt("级次")-1);
    		if(maxLevel < level)
    			maxLevel = level;
    		Integer parentLevel = (Integer) parentAccountMap.get(rowSet.getString("科目编码"));
    		if(parentLevel != null) {
    			if(parentLevel.intValue() == 1)
    				addRow.getStyleAttributes().setBackground(Color.CYAN);
    			else if(parentLevel.intValue() == 2)
    				addRow.getStyleAttributes().setBackground(Color.ORANGE);
    			else if(parentLevel.intValue() == 3)
    				addRow.getStyleAttributes().setBackground(Color.YELLOW);
    			else if(parentLevel.intValue() == 4)
    				addRow.getStyleAttributes().setBackground(Color.PINK);
    		}
    	}
    	kdtEntrysAccount.getTreeColumn().setDepth(maxLevel);
    	
    	//---------------------------------------目标成本
    	String aimCostSql = getAimCostSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(aimCostSql);
    	rowSet = builder.executeQuery();
    	Map aimCostMap = new HashMap();
    	while(rowSet.next()) {
    		aimCostMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("目标成本"));
    	}
    	//---------------------------------------合同金额
    	String actContractSql = getActContractSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actContractSql);
    	rowSet = builder.executeQuery();
    	Map contractMap = new HashMap();
    	while(rowSet.next()) {
    		contractMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("签约金额"));
    	}
    	//---------------------------------------设计变更
    	String actConChangeSql = getActConChangeSql(curProject, "4101", "'工程指令','技术核定','设计变更'");
    	builder.clear();
    	builder.appendSql(actConChangeSql);
    	rowSet = builder.executeQuery();
    	Map desChangeMap = new HashMap();
    	while(rowSet.next()) {
    		desChangeMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("变更金额"));
    	}
    	//---------------------------------------现场签证
    	String actTechChangeSql = getActConChangeSql(curProject, "4101", "'技术经济签证'");
    	builder.clear();
    	builder.appendSql(actTechChangeSql);
    	rowSet = builder.executeQuery();
    	Map techChangeMap = new HashMap();
    	while(rowSet.next()) {
    		techChangeMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("变更金额"));
    	}
    	//---------------------------------------结算
    	String actSettleSql = getActSettleSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actSettleSql);
    	rowSet = builder.executeQuery();
    	Map settleMap = new HashMap();
    	while(rowSet.next()) {
    		settleMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("结算金额"));
    	}
    	//---------------------------------------无文本合同
    	String noTextContractSql = getNoTextContractSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(noTextContractSql);
    	rowSet = builder.executeQuery();
    	Map noTextContractMap = new HashMap();
    	while(rowSet.next()) {
    		noTextContractMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("无文本金额"));
    	}
    	//---------------------------------------在途成本
    	String onWayCostSql = getOnWayCostSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(onWayCostSql);
    	rowSet = builder.executeQuery();
    	Map onWayCostMap = new HashMap();
    	while(rowSet.next()) {
    		onWayCostMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("在途成本"));
    	}
    	//---------------------------------------其他调整金额
    	String otherAdjustSql = getActOtherAdjustSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(otherAdjustSql);
    	rowSet = builder.executeQuery();
    	Map otherAdjustMap = new HashMap();
    	while(rowSet.next()) {
    		otherAdjustMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("其他调整金额"));
    	}
    	//---------------------------------------预估变更拆分
    	String actForecastSql = getActForecastSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actForecastSql);
    	rowSet = builder.executeQuery();
    	Map actForecastMap = new HashMap();
    	while(rowSet.next()) {
    		actForecastMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("预估金额"));
    	}
    	//---------------------------------------预估变更实际发生
    	String actChangeSql = getActChangeSql(curProject, "4101", "'工程指令','技术核定','设计变更','技术经济签证'");
    	builder.clear();
    	builder.appendSql(actChangeSql);
    	rowSet = builder.executeQuery();
    	Map changeMap = new HashMap();
    	while(rowSet.next()) {
    		changeMap.put(rowSet.getString("科目编码"), rowSet.getBigDecimal("变更金额"));
    	}
    	//---------------------------------------未签合同
    	Map unSignContract = getUnSignContract();
    	
    	//---------------------------------------填充数据
    	for(int i = 0; i < kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = kdtEntrysAccount.getRow(i);
    		String accountNumber = row.getCell("costAccountNumber").getValue().toString();
    		
    		row.getCell("aimCost").setValue(aimCostMap.get(accountNumber));
    		row.getCell("Contract").setValue(contractMap.get(accountNumber));
    		row.getCell("designChangeAmount").setValue(desChangeMap.get(accountNumber));
    		row.getCell("siteCertificatAmount").setValue(techChangeMap.get(accountNumber));
    		row.getCell("settlementAmount").setValue(settleMap.get(accountNumber));
    		row.getCell("withouttxt").setValue(noTextContractMap.get(accountNumber));
    		row.getCell("onWayCost").setValue(onWayCostMap.get(accountNumber));
    		
    		row.getCell("otherAmount").setValue(otherAdjustMap.get(accountNumber));
    		//预估变更余额
    		BigDecimal forcastChangeAmount = (BigDecimal) actForecastMap.get(accountNumber);
    		BigDecimal actChangeamount = (BigDecimal) changeMap.get(accountNumber);
    		BigDecimal banlance = forcastChangeAmount;
    		if(forcastChangeAmount != null && actChangeamount != null) {
    			banlance = forcastChangeAmount.subtract(actChangeamount);
    		}
    		row.getCell("EstimateChangeBalance").setValue(banlance);
    		//未签合同
    		row.getCell("unSignContract").setValue(unSignContract.get(accountNumber));
    	}
    	
    	
    	//逐级汇总
    	String[] sumCol = new String[] {"aimCost", "Contract", "designChangeAmount", "siteCertificatAmount", "settlementAmount", "withouttxt", "sumB", 
    									"onWayCost","EstimateChangeBalance","otherAmount","unSignContract","sumC","sumBC","diffAmount"};
    	Map sumMap=new HashMap();
    	for (int i = 0; i < this.kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = this.kdtEntrysAccount.getRow(i);
    		String number = UIRuleUtil.getString(row.getCell("costAccountNumber").getValue()).trim();
    		sumMap.put(number, row);
    		int level = row.getTreeLevel();
    		if(number.indexOf(".")>0){ 
    			String pnumber=number.substring(0, number.lastIndexOf("."));
    			for(int k=0;k<level;k++){
    				if(sumMap.get(pnumber)!=null){
    					IRow prow=(IRow) sumMap.get(pnumber);
//    					if(prow.getCell("Contract").getValue()!=null)
//    						prow.getCell("Contract").setValue(FDCHelper.add(prow.getCell("Contract").getValue(), row.getCell("Contract").getValue()));
//    					else
//    						prow.getCell("Contract").setValue(row.getCell("Contract").getValue());
    					for(int j = 0; j < sumCol.length; j++) {
    						String colName = sumCol[j];
    						if(prow.getCell(colName).getValue()!=null)
        						prow.getCell(colName).setValue(FDCHelper.add(prow.getCell(colName).getValue(), row.getCell(colName).getValue()));
        					else
        						prow.getCell(colName).setValue(row.getCell(colName).getValue());
    					}
    				}
    				if(pnumber.indexOf(".")>0)
    					pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
    			}
    		}
    	}
    	calculateAccountSum();
    }
    /**
     * 科目维度汇总
     */
    private void calculateAccountSum() {
    	for(int i = 0; i < kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = kdtEntrysAccount.getRow(i);
    		BigDecimal aimCost = (BigDecimal) row.getCell("aimCost").getValue();
    		int level = ((Integer) row.getCell("level").getValue()).intValue();
    		//预警指标
    		if(aimCost != null && aimCost.compareTo(BigDecimal.ZERO) != 0) {
    			BigDecimal rate = BigDecimal.ZERO;
    			if(level == 1) {
    				rate = ONE_MILLION.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			} else if(level == 2) {
    				rate = HALF_ONE_MILLION.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			} else if(level == 3) {
    				rate = THREE.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			}
    			
//    			row.getCell("alertIndex").setValue(ONE_HUNDRUD.add(rate));
    			row.getCell("alertIndex").setValue(rate.multiply(new BigDecimal("-1")));
    		}
    		//小计B
    		BigDecimal contractAmount = row.getCell("Contract").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("Contract").getValue();
    		BigDecimal desChangeAmount = row.getCell("designChangeAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("designChangeAmount").getValue();
    		BigDecimal siteCertAmount = row.getCell("siteCertificatAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("siteCertificatAmount").getValue();
    		BigDecimal settleAmount = row.getCell("settlementAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("settlementAmount").getValue();
    		BigDecimal withouttxt = row.getCell("withouttxt").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("withouttxt").getValue();
    		BigDecimal sumB = contractAmount.add(desChangeAmount).add(siteCertAmount).add(settleAmount).add(withouttxt);
    		row.getCell("sumB").setValue(sumB.compareTo(BigDecimal.ZERO) == 0 ? null : sumB);
    		
    		//小计C
    		BigDecimal onWayCost = row.getCell("onWayCost").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("onWayCost").getValue();
    		BigDecimal EstimateChangeBalance = row.getCell("EstimateChangeBalance").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("EstimateChangeBalance").getValue();
    		BigDecimal otherAmount = row.getCell("otherAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("otherAmount").getValue();
    		BigDecimal unSignContract = row.getCell("unSignContract").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("unSignContract").getValue();
    		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(otherAmount).add(unSignContract);
    		row.getCell("sumC").setValue(sumC.compareTo(BigDecimal.ZERO) == 0 ? null : sumC);
    		
    		//动态成本合计
    		BigDecimal sumBC = sumB.add(sumC);
    		row.getCell("sumBC").setValue(sumBC.compareTo(BigDecimal.ZERO) == 0 ? null : sumBC);
    		//差异金额
    		BigDecimal diffAmount = BigDecimal.ZERO;
    		if(aimCost != null)
    			diffAmount = aimCost.subtract(sumBC);
    		row.getCell("diffAmount").setValue(diffAmount.compareTo(BigDecimal.ZERO) == 0 ? null : diffAmount);
    		//差异率
    		BigDecimal diffRate = BigDecimal.ZERO;
    		if(aimCost != null)
    			diffRate = diffAmount.divide(aimCost, 4, BigDecimal.ROUND_HALF_UP);
    		row.getCell("diffRate").setValue(diffRate.compareTo(BigDecimal.ZERO) == 0 ? null : diffRate);
    	}
    }
    /**
     * 科目维度未签合同(根据合约规划的未签合同来拆分)
     * @throws Exception 
     */
    private Map getUnSignContract() throws Exception {
    	Map accountUnsignMap = new HashMap();
    	Set keySet = programUnSignContractMap.keySet();
    	Iterator iterator = keySet.iterator();
    	while(iterator.hasNext()) {
    		String programId = iterator.next().toString();
    		ProgrammingContractInfo programmingContract = getProgrammingContract(programId);
    		BigDecimal unSignContractAmount = (BigDecimal) programUnSignContractMap.get(programId);
    		if(!programmingContract.isIscse()) {
    			ProgrammingContracCostCollection costEntries = programmingContract.getCostEntries();
    			for(int i = 0; i < costEntries.size(); i++) {
    				ProgrammingContracCostInfo costInfo = costEntries.get(i);
    				String accNumber = costInfo.getCostAccount().getCodingNumber();
    				BigDecimal scale = costInfo.getContractScale().divide(ONE_HUNDRUD);
    				if(unSignContractAmount != null) {
    					if(accountUnsignMap.get(accNumber) == null) {
    						accountUnsignMap.put(accNumber, unSignContractAmount.multiply(scale));
    					} else {
    						BigDecimal unSignAmount = (BigDecimal) accountUnsignMap.get(accNumber);
    						BigDecimal newUnSign = FDCHelper.add(unSignAmount, unSignContractAmount.multiply(scale));
    						accountUnsignMap.put(accNumber, newUnSign);
    					}
    				}
    			}
    		}
    	}
    	return accountUnsignMap;
    }
    /**
     * 计算合计相关
     * @throws Exception 
     */
    private void calculateSum() throws Exception {
//    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
//    		IRow row = kdtEntrysContract.getRow(i);
//    		//计算小计B
//    		BigDecimal planAmount = (BigDecimal) row.getCell("Amount").getValue();
//    		BigDecimal contractAmount = (BigDecimal) row.getCell("ContractAmount").getValue();
//    		contractAmount = contractAmount== null ? BigDecimal.ZERO : contractAmount;
//    		BigDecimal desChangeAmount = (BigDecimal) row.getCell("designChangeAmount").getValue();
//    		desChangeAmount = desChangeAmount== null ? BigDecimal.ZERO : desChangeAmount;
//    		BigDecimal siteCerChangeAmount = (BigDecimal) row.getCell("siteCertificatAmount").getValue();
//    		siteCerChangeAmount = siteCerChangeAmount== null ? BigDecimal.ZERO : siteCerChangeAmount;
//    		BigDecimal settleAmount = (BigDecimal) row.getCell("settlementAmount").getValue();
//    		settleAmount = settleAmount== null ? BigDecimal.ZERO : settleAmount;
//    		BigDecimal sumB = settleAmount;
//    		if(settleAmount != null && settleAmount.compareTo(BigDecimal.ZERO) > 0) {
//    			row.getCell("sumB").setValue(sumB);
//    		} else {
//    			sumB = contractAmount.add(desChangeAmount).add(siteCerChangeAmount);
//    			row.getCell("sumB").setValue(sumB);
//    		}
//    		
//    		//计算未签合同 规划金额-已发生
//    		row.getCell("unSignContract").setValue(planAmount.subtract(sumB));
//    		
//    		//计算小计C
//    		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
////    		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
//    		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
//    		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
//    		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
//    		BigDecimal unSignContract = (BigDecimal) row.getCell("unSignContract").getValue();
//    		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(curMonthOtherAmount).add(otherAmount).add(unSignContract);
//    		row.getCell("sumC").setValue(sumC);
//    		
//    		//计算动态成本 sumB+sumC
//    		row.getCell("dynamicCost").setValue(sumC.add(sumB));
//    		//规划差额
//    		BigDecimal diffAmount = planAmount.subtract(sumB).subtract(sumC);
//    		row.getCell("diffAmount").setValue(diffAmount);
//    		//规划差异率
//    		BigDecimal diffRate = diffAmount.divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
//    		row.getCell("diffRate").setValue(diffRate);
//    	}
    	
    	//根据合约规划汇总
    	Map programMap = new HashMap();
    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
    		IRow row = kdtEntrysContract.getRow(i);
    		String programmingId = row.getCell("programmingId").getValue().toString();
    		if(programMap.get(programmingId) == null) {
    			Map detailMap = new HashMap();
    			BigDecimal planAmount = (BigDecimal) row.getCell("Amount").getValue();
    			detailMap.put("planAmount", planAmount);
    			//计算小计B
    			BigDecimal contractAmount = (BigDecimal) row.getCell("ContractAmount").getValue();
        		contractAmount = contractAmount== null ? BigDecimal.ZERO : contractAmount;
        		BigDecimal desChangeAmount = (BigDecimal) row.getCell("designChangeAmount").getValue();
        		desChangeAmount = desChangeAmount== null ? BigDecimal.ZERO : desChangeAmount;
        		BigDecimal siteCerChangeAmount = (BigDecimal) row.getCell("siteCertificatAmount").getValue();
        		siteCerChangeAmount = siteCerChangeAmount== null ? BigDecimal.ZERO : siteCerChangeAmount;
        		BigDecimal settleAmount = (BigDecimal) row.getCell("settlementAmount").getValue();
        		settleAmount = settleAmount== null ? BigDecimal.ZERO : settleAmount;
        		BigDecimal notextContract = (BigDecimal) row.getCell("notextContract").getValue();
        		notextContract = notextContract== null ? BigDecimal.ZERO : notextContract;
        		BigDecimal sumB = settleAmount;
        		if(settleAmount != null && settleAmount.compareTo(BigDecimal.ZERO) > 0) {
        			sumB = settleAmount;
        		} else {
        			sumB = contractAmount.add(desChangeAmount).add(siteCerChangeAmount);
        		}
        		detailMap.put("sumB", sumB.add(notextContract));//加上无文本
        		
        		//计算小计C
        		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
        		
        		//计算未签合同 规划金额-已发生-在途 
        		ProgrammingContractInfo pInfo = getProgrammingContract(programmingId);
        		BigDecimal unSignContract = BigDecimal.ZERO;
        		if(!pInfo.isIscse())//判断合约是否签完, 签完了则unSignContract 为0
        			unSignContract = planAmount.subtract(sumB).subtract(onWayCost);
        		//未签合同不能为负数,最小为0
        		unSignContract = unSignContract.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO:unSignContract;
        		detailMap.put("unSignContract",unSignContract);
        		
//        		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
        		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
        		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
        		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
        		//记录当前行 在途成本+预估变更签证余额+本月其他调整+截至本月其他调整 之和
        		BigDecimal rowSum = EstimateChangeBalance.add(curMonthOtherAmount).add(otherAmount).add(onWayCost);
        		detailMap.put("rowSum", rowSum);
        		BigDecimal sumC = rowSum.add(unSignContract);
        		detailMap.put("sumC",sumC);
        		
        		
        		
        		//计算动态成本 sumB+sumC
        		detailMap.put("dynamicCost",sumC.add(sumB));
        		//规划差额
        		BigDecimal diffAmount = planAmount.subtract(sumB).subtract(sumC);
        		detailMap.put("diffAmount",diffAmount);
        		//规划差异率
        		BigDecimal diffRate = diffAmount.divide(planAmount, 5, BigDecimal.ROUND_HALF_UP);
        		detailMap.put("diffRate",diffRate);
        		
        		programMap.put(programmingId, detailMap);
    		} else {
    			Map detailMap = (Map) programMap.get(programmingId);
    			//计算小计B
    			BigDecimal planAmount = (BigDecimal) row.getCell("Amount").getValue();
    			BigDecimal contractAmount = (BigDecimal) row.getCell("ContractAmount").getValue();
        		contractAmount = contractAmount== null ? BigDecimal.ZERO : contractAmount;
        		BigDecimal desChangeAmount = (BigDecimal) row.getCell("designChangeAmount").getValue();
        		desChangeAmount = desChangeAmount== null ? BigDecimal.ZERO : desChangeAmount;
        		BigDecimal siteCerChangeAmount = (BigDecimal) row.getCell("siteCertificatAmount").getValue();
        		siteCerChangeAmount = siteCerChangeAmount== null ? BigDecimal.ZERO : siteCerChangeAmount;
        		BigDecimal settleAmount = (BigDecimal) row.getCell("settlementAmount").getValue();
        		settleAmount = settleAmount== null ? BigDecimal.ZERO : settleAmount;
        		BigDecimal sumB = settleAmount;
        		if(settleAmount != null && settleAmount.compareTo(BigDecimal.ZERO) > 0) {
        			sumB = settleAmount;
        		} else {
        			sumB = contractAmount.add(desChangeAmount).add(siteCerChangeAmount);
        		}
        		BigDecimal preSumB = (BigDecimal) detailMap.get("sumB");
        		BigDecimal newSumB = preSumB.add(sumB);
        		detailMap.put("sumB",newSumB);
        		
        		//小计C
        		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
        		
        		//计算未签合同 规划金额-已发生-在途
        		ProgrammingContractInfo pInfo = getProgrammingContract(programmingId);
        		BigDecimal unSignContract = BigDecimal.ZERO;
        		if(!pInfo.isIscse())//判断合约是否签完, 签完了则unSignContract 为0
        			unSignContract = planAmount.subtract(newSumB).subtract(onWayCost);
//        		BigDecimal unSignContract = planAmount.subtract(sumB).subtract(onWayCost);
//        		BigDecimal preunSignContract = (BigDecimal) detailMap.get("unSignContract");
//        		detailMap.put("unSignContract", preunSignContract.add(unSignContract));
        		//未签合同不能为负数,最小为0
        		unSignContract = unSignContract.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO:unSignContract;
        		detailMap.put("unSignContract", unSignContract);
        		
//        		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
        		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
        		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
        		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
//        		BigDecimal unSignContract = (BigDecimal) row.getCell("unSignContract").getValue();
        		
        		BigDecimal preRowSum = (BigDecimal) detailMap.get("rowSum");
        		BigDecimal rowSum = EstimateChangeBalance.add(curMonthOtherAmount).add(otherAmount).add(onWayCost);
        		preRowSum = preRowSum.add(rowSum);
        		detailMap.put("rowSum", preRowSum);
        		
//        		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(curMonthOtherAmount).add(otherAmount).add(unSignContract);
//        		BigDecimal preSumC = (BigDecimal) detailMap.get("sumC");
//        		BigDecimal newSumC = preSumC.add(sumC);
        		BigDecimal newSumC = preRowSum.add(unSignContract);
        		detailMap.put("sumC", newSumC);
        		
        		
        		//计算动态成本 前一次
//        		BigDecimal preDynamicCost = (BigDecimal) detailMap.get("dynamicCost");
//        		detailMap.put("dynamicCost",preDynamicCost.add(sumB).add(newSumC));
        		detailMap.put("dynamicCost",newSumB.add(newSumC));
        		//规划差额
        		BigDecimal diffAmount = planAmount.subtract(newSumB).subtract(newSumC);
//        		BigDecimal preDiffAmount = (BigDecimal) detailMap.get("diffAmount");
//        		BigDecimal newDiffAmount = preDiffAmount.add(diffAmount);
        		detailMap.put("diffAmount",diffAmount);
        		//规划差异率
        		BigDecimal diffRate = diffAmount.divide(planAmount, 5, BigDecimal.ROUND_HALF_UP);
        		detailMap.put("diffRate",diffRate);
    		}
    	}
    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
    		IRow row = kdtEntrysContract.getRow(i);
    		String programmingId = row.getCell("programmingId").getValue().toString();
    		if(programMap.get(programmingId) != null) {
    			Map detailMap = (Map) programMap.get(programmingId);
    			row.getCell("sumB").setValue(detailMap.get("sumB"));
    			row.getCell("sumC").setValue(detailMap.get("sumC"));
    			row.getCell("unSignContract").setValue(detailMap.get("unSignContract"));
    			row.getCell("dynamicCost").setValue(detailMap.get("dynamicCost"));
    			row.getCell("diffAmount").setValue(detailMap.get("diffAmount"));
    			row.getCell("diffRate").setValue(detailMap.get("diffRate"));
    		}
    		programUnSignContractMap.put(programmingId, row.getCell("unSignContract").getValue());
    	}
    	setContractTableMerge();
    }
    /**
     * table单元格双击事件
     */
    protected void kdtEntrysContract_tableClicked(KDTMouseEvent e)
    		throws Exception {
    	if(e.getClickCount() == 2) {
    		String key = kdtEntrysContract.getColumnKey(e.getColIndex());
    		//本月其他调整
    		if("curMonthOtherAmount".equals(key)) {
    			Map context = getUIContext();
    			String contractId = kdtEntrysContract.getRow(e.getRowIndex()).getCell("ContractBillId").getValue().toString();
    			String otherSplitId = (String) kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherId").getValue();
    			ContractBillInfo contractBillInfo = getContractBillInfo(contractId);
    			context.put("project", editData.getCurProject());
    			context.put("Contract", contractBillInfo);
    			if(otherSplitId == null) {
    				IUIWindow otherWindow = UIFactory.createUIFactory().create(OtherSplitBillEditUI.class.getName(), context, null, OprtState.ADDNEW);
    				otherWindow.show();
    				BOSUuid otherId = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getId();
    				BigDecimal adjustAmount = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getAdjustAmount();
    				String comment = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getComment();
    				if(otherId != null)
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherId").setValue(otherId.toString());
    				if(adjustAmount != null) {
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").setValue(adjustAmount);
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("Comment").setValue(comment);
    				}
    			} else {
    				context.put(UIContext.ID, otherSplitId);
    				IUIWindow otherWindow = UIFactory.createUIFactory().create(OtherSplitBillEditUI.class.getName(), context, null, OprtState.VIEW);
    				otherWindow.show();
    				BigDecimal adjustAmount = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getAdjustAmount();
    				String comment = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getComment();
    				BigDecimal originAmount = (BigDecimal) kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").getValue();
    				if(originAmount != null && originAmount.compareTo(adjustAmount) != 0) {
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").setValue(adjustAmount);
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("Comment").setValue(comment);
    				}
    			}
    		}
    		//本月预估变更
    		if("curMonthEstimateChange".equals(key)) {
    			Map context = getUIContext();
    			String contractId = kdtEntrysContract.getRow(e.getRowIndex()).getCell("ContractBillId").getValue().toString();
    			String ForecastChangeVisID = (String) kdtEntrysContract.getRow(e.getRowIndex()).getCell("ForecastChangeVisID").getValue();
    			ContractBillInfo contractBillInfo = getContractBillInfo(contractId);
    			context.put("contractInfo", contractBillInfo);
    			if(ForecastChangeVisID == null) {
    				IUIWindow ForecastChange = UIFactory.createUIFactory().create(ForecastChangeVisEditUI.class.getName(), context, null, OprtState.ADDNEW);
    				ForecastChange.show();
    				BOSUuid id = ((ForecastChangeVisEditUI)ForecastChange.getUIObject()).editData2.getId();
    				BigDecimal amount = ((ForecastChangeVisEditUI)ForecastChange.getUIObject()).editData2.getAmount();
    				if(id != null)
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("ForecastChangeVisID").setValue(id.toString());
    				if(amount != null)
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthEstimateChange").setValue(amount);
    			} else {
    				context.put(UIContext.ID, ForecastChangeVisID);
    				IUIWindow ForecastChange = UIFactory.createUIFactory().create(ForecastChangeVisEditUI.class.getName(), context, null, OprtState.VIEW);
    				ForecastChange.show();
    				
    				BigDecimal adjustAmount = ((ForecastChangeVisEditUI)ForecastChange.getUIObject()).editData2.getAmount();
    				BigDecimal originAmount = (BigDecimal) kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthEstimateChange").getValue();
    				if(originAmount != null && originAmount.compareTo(adjustAmount) != 0) {
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthEstimateChange").setValue(adjustAmount);
    				}
    			}
    		}
    	}
    }
    /**
     * 还原合同信息
     * @param billId
     * @return
     * @throws Exception
     */
    private ContractBillInfo getContractBillInfo(String billId) throws Exception{
    	ContractBillInfo contractBill = null;
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	sic.add(new SelectorItemInfo("id"));
    	sic.add(new SelectorItemInfo("name"));
    	contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(billId), sic);
    	
    	return contractBill;
    }
    /**
     * 获取某项目无文本合同
     * @param info
     * @return
     */
    private String getNoTextContractSql(CurProjectInfo info) {
    	StringBuilder sql = new StringBuilder("");
    	
    	sql.append(" select programming.fid programmingId, programming.fname_l2 programName, sum(contract.FAmount) amount");
    	sql.append(" from T_CON_ContractWithoutText contract ");
    	sql.append(" left join T_FDC_CurProject project on project.fid=contract.FCurProjectID");
    	sql.append(" left join T_CON_ProgrammingContract programming on contract.FProgrammingContract=programming.fid");
    	sql.append(" where 1=1");
    	sql.append(" and project.fid='"+info.getId().toString()+"'");
    	sql.append(" and contract.FProgrammingContract is not null");
    	sql.append(" group by programming.fid, programming.fname_l2");
    	
    	return sql.toString();
    }
    /**
     * 获取某项目的合同信息(审批中以及已审核)
     * @return
     */
    private String getContractSql(CurProjectInfo info) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select  contract.fname contractName /*合同名称*/, contract.fid contractId/*合同ID*/,");
    	sb.append(" programming.famount programAmount/*规划金额*/,programming.fname_l2 programNAme/*合约规划名称*/,programming.fid programId,");
    	sb.append(" case when contract.fstate='4AUDITTED' then contract.famount else null end contractAmount/*合同金额*/,");
    	sb.append(" case when contract.fstate='3AUDITTING' then contract.famount else null end onWayAmount/*在途成本*/");
    	sb.append(" from t_con_contractbill contract /*合同*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*工程项目*/");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*合约规划*/");
    	sb.append(" where 1=1 ");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
    	sb.append(" and contract.fstate in('4AUDITTED', '3AUDITTING')");
    	sb.append(" and contract.FProgrammingContract is not null"); //过滤掉没有关联合约规划的历史数据
    	sb.append(" order by programming.fid");
    	return sb.toString();
    }
    /**
     * 获取某项目合同 变更相关(设计变更或者现场签证)(未生成变更结算+已生成变更结算但是未拆分的)
     * changeType 工程指令, 技术核定, 设计变更这三种名称 对应设计变更, 技术经济签证 对应现场签证
     */
    private String getConChangeSql(CurProjectInfo info, String changeType) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select sum(conChange.Famount) changeAmount/*变更金额*/, contract.fid contractId /*合同ID*/");
    	sb.append(" from T_CON_ContractChangeBill conChange /*合同变更单*/");
    	sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=conChange.Fchangetypeid /*变更类型*/");
    	sb.append(" left join t_con_contractbill contract on contract.fid=conChange.Fcontractbillid /*合同*/");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*合约规划*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*工程项目*/");
    	sb.append(" where 1=1");
    	sb.append(" and changeType.Fname_L2 in ("+changeType+")");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
//    	sb.append(" and conChange.Fisconsetted='0'");
    	sb.append(" and contract.fid is not null");
    	sb.append(" and contract.FProgrammingContract is not null"); //过滤掉没有关联合约规划的历史数据
    	sb.append(" group by changeType.Fname_L2,contract.fname, contract.fid");
//    	sb.append(" union all");
//    	sb.append(" select sum(conChange.Famount) 变更金额, changeType.Fname_L2 变更类型,contract.fname 合同名称, contract.fid 合同ID");
//    	sb.append(" from T_CON_ContractChangeBill conChange /*合同变更单*/");
//    	sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=conChange.FChangeTypeID /*变更类型*/");
//    	sb.append(" left join T_CON_ContractBill contract on contract.fid=conChange.FContractBillID /*合同*/");
//    	sb.append(" left join T_CON_ContractSettlementBill consettle on consettle.FContractBillID=contract.fid /*合同结算*/");
//    	sb.append(" left join T_CON_SettlementCostSplit settleSplit on consettle.fid=settleSplit.FSettlementBillID /*结算拆分*/");
//    	sb.append(" left join T_CON_SettlementCostSplitEntry settleSplitEntry on settleSplit.fid=settleSplitEntry.FParentID");
//    	sb.append(" where  1=1");
//    	sb.append(" and changeType.Fname_L2 in ("+changeType+")");
//    	sb.append(" and settleSplit.fid is null");
//    	sb.append(" and consettle.FIsSettled='1'");
//    	sb.append(" group by changeType.Fname_L2,contract.fname, contract.fid");
    	return sb.toString();
    }
    /**
     * 获取某项目合同对应的结算金额
     * @param info
     * @param flag (0代表未结算,1代表已结算)
     * @return
     */
    private String getSettmentSql(CurProjectInfo info, int flag) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select conSettle.FSettlePrice settleAmount/*结算金额*/,contract.fid contractId/*合同ID*/");
    	sb.append(" from T_CON_ContractSettlementBill conSettle /*合同结算*/ ");
    	sb.append(" left join T_CON_ContractBill contract on contract.fid=conSettle.Fcontractbillid /*合同*/ ");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*合约规划*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*项目*/");
    	sb.append(" where conSettle.Fissettled='"+flag+"'");
    	sb.append(" and contract.FProgrammingContract is not null"); //过滤掉没有关联合约规划的历史数据
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
    	return sb.toString();
    }
    
    /**
     * 获取某项目本月预估变更签证
     */
    private String getForecasetChangeSql(CurProjectInfo info) {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select forecastChange.CFAmount forecastChangeAmount/*预估变更金额*/, contract.fid contractId/*合同ID*/,forecastChange.Fid forecastChangeId");
    	sb.append(" from CT_AIM_ForecastChangeVis forecastChange");
    	sb.append(" left join T_CON_ContractBill contract on contract.fid=forecastChange.CFContractNumberID");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*项目*/");
    	sb.append(" where 1=1");
    	sb.append(" and forecastChange.CFIsLast='1'");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
    	sb.append(" and forecastChange.FBizDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getFirstDayOfMonth(date))+"'}");
    	sb.append(" and forecastChange.FBizDate<={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getLastDayOfMonth(date))+"'}");
    	return sb.toString();
    }
    /**
     * 获取预估变更已发生金额(ForecastChangeVisEditUI getSql 方法)
     */
    private BigDecimal getgetForecasetChangeBalanceSql(CurProjectInfo info, String contractId) throws Exception{
    	StringBuilder sb = new StringBuilder();
    	sb.append(" SELECT CONTRACTBILL.Fid contractId, sum(SUPPENTRY.FCostAmount) costAmount");
    	sb.append(" FROM T_CON_ChangeAuditBill  CHANGEAUDITBILL");
    	sb.append(" LEFT JOIN T_FDC_ChangeType AUDITTYPE ON CHANGEAUDITBILL.FAuditTypeID = AUDITTYPE.FID");
    	sb.append(" LEFT JOIN T_FDC_CurProject CURPROJECT ON CHANGEAUDITBILL.FCurProjectID = CURPROJECT.FID");
    	sb.append(" LEFT JOIN T_CON_ChangeSupplierEntry SUPPENTRY ON CHANGEAUDITBILL.FID = SUPPENTRY.FParentID");
    	sb.append(" LEFT JOIN T_CON_ContractBill CONTRACTBILL ON SUPPENTRY.FContractBillID = CONTRACTBILL.FID");
    	sb.append(" where 1 = 1 ");
    	sb.append(" and SUPPENTRY.CFforecastChangeVi in (select fid from CT_AIM_ForecastChangeVis where CFContractNumberID='"+contractId+"')");
    	sb.append(" and CURPROJECT.FID='"+info.getId().toString()+"'");
//    	sb.append(" and (CHANGEAUDITBILL.FChangeState='7Visa' or CHANGEAUDITBILL.FChangeState='5Audit')");
    	sb.append(" and SUPPENTRY.FContractBillID is not null");
    	sb.append(" group by CONTRACTBILL.Fid");
    	
    	BigDecimal actAmount = BigDecimal.ZERO;
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(sb.toString());
    	IRowSet rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		actAmount = rowSet.getBigDecimal("costAmount");
    	}
    	return actAmount;
    }
    /**
     * 预估变更（根据项目以及合同来获取  本月）
     * @param info
     * @param ContractId
     * @return
     * @throws Exception 
     */
    private ForecastChangeVisInfo getForecastChangeVisInfoByContract(CurProjectInfo info, String ContractId) throws Exception {
    	ForecastChangeVisInfo forcastinfo = null;
    	IForecastChangeVis iForecastChangeVis = ForecastChangeVisFactory.getRemoteInstance();
    	
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("contractNumber.id", ContractId, CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate", FDCDateHelper.getFirstDayOfMonth(date), CompareType.GREATER_EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate", FDCDateHelper.getLastDayOfMonth(date), CompareType.LESS_EQUALS));
    	
    	SorterItemInfo sorterItemInfo = new SorterItemInfo("version");
    	sorterItemInfo.setSortType(SortType.DESCEND);
    	evi.getSorter().add(sorterItemInfo);
    	
    	ForecastChangeVisCollection forecastChangeVisCollection = iForecastChangeVis.getForecastChangeVisCollection(evi);
    	if(forecastChangeVisCollection.size() > 0)
    		forcastinfo = forecastChangeVisCollection.get(0);
    	return forcastinfo;
    }
    /**
     * 预估变更（根据项目以及合同来获取  之前月）
     * @param info
     * @param ContractId
     * @return
     * @throws Exception 
     */
    private ForecastChangeVisInfo getPreForecastChangeVisInfoByContract(CurProjectInfo info, String ContractId) throws Exception {
    	ForecastChangeVisInfo forcastinfo = null;
    	IForecastChangeVis iForecastChangeVis = ForecastChangeVisFactory.getRemoteInstance();
    	
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
//    	if(month-1 == 0) {
//    		year = year -1;
//    		month = 12;
//    	}
    	Date date = getCurrentMonthDate(year, month);
    	
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("contractNumber.id", ContractId, CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate", FDCDateHelper.getFirstDayOfMonth(date), CompareType.LESS));
    	
    	SorterItemInfo sorterItemInfo = new SorterItemInfo("version");
    	sorterItemInfo.setSortType(SortType.DESCEND);
    	evi.getSorter().add(sorterItemInfo);
    	
    	ForecastChangeVisCollection forecastChangeVisCollection = iForecastChangeVis.getForecastChangeVisCollection(evi);
    	if(forecastChangeVisCollection.size() > 0)
    		forcastinfo = forecastChangeVisCollection.get(0);
    	return forcastinfo;
    }
    /**
     * 获取本月的其他调整
     * @param info
     * @return
     */
    private String getCurMonthOtherSql(CurProjectInfo info) {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select bill.CFAdjustAmount adjustAmount,bill.CFComment fcomment,contract.fid contractId,bill.fid billId from CT_AIM_OtherSplitBill bill");
    	sb.append(" left join t_con_contractbill contract on contract.fid=bill.CFContractID");
    	sb.append(" left join t_fdc_curproject project on project.fid=bill.cfcurprojectid");
    	sb.append(" where 1=1");
    	sb.append(" and project.fid='"+info.getId().toString()+"'");
    	sb.append(" and bill.fbizdate >= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getFirstDayOfMonth(date))+"'}");
    	sb.append(" and bill.fbizdate <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getLastDayOfMonth(date))+"'}");
    	return sb.toString();
    }
    /**
     * 获取截至本月初的所有其他调整
     */
    private String getAllOtherSql(CurProjectInfo info) {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select sum(bill.CFAdjustAmount) adjustAmount,contract.fid contractId from CT_AIM_OtherSplitBill bill");
    	sb.append(" left join t_con_contractbill contract on contract.fid=bill.CFContractID");
    	sb.append(" left join t_fdc_curproject project on project.fid=bill.cfcurprojectid");
    	sb.append(" where 1=1");
    	sb.append(" and project.fid='"+info.getId().toString()+"'");
    	sb.append(" and bill.fbizdate < {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(date))+"'}");
    	sb.append(" group by contract.fid");
    	return sb.toString();
    }
    /**
     * 根据编制月份构造月初日期
     * @return
     */
    private Date getCurrentMonthDate(int year, int month) {
    	Calendar cal = Calendar.getInstance();
    	cal.clear();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month-1);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	return cal.getTime();
    }
    //-----------------------------------------------------科目维度---------------------------------------------------------------//
    /**
     * 合同信息
     */
    private String getActContractSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 条目名称,科目编码,sum(应取金额) 签约金额 from");
    	sb.append(" (");
    	sb.append(" select 科目.fname_l2 条目名称,科目.fcodingnumber 科目编码,拆分分录.FAmount 应取金额 /*签约金额*/");
    	sb.append(" from T_FDC_CostAccount 科目 ");
    	sb.append(" left join T_CON_ContractCostSplitEntry 拆分分录 on 拆分分录.FCostAccountID=科目.fid");
    	sb.append("	left join T_CON_ContractCostSplit 合同拆分 on 合同拆分.fid=拆分分录.FParentID");
    	sb.append("	left join T_CON_ContractBill 合同 on 合同.fid=合同拆分.FContractBillID");
    	sb.append("	left join T_AIM_AimCost acost on acost.FOrgOrProId=科目.FCurProject");
    	sb.append("	left join T_CON_ContractSettlementBill 结算单 on 结算单.FContractBillID=合同.fid ");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and acost.FIsLastVersion='1'");
    	sb.append(" and 合同.fstate in ('4AUDITTED', '3AUDITTING')");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and (结算单.FIsSettled is null or 结算单.FIsSettled='0')");
    	sb.append(" and 拆分分录.flevel=0");
//    	sb.append(" union all");
//    	sb.append(" select 科目.fname_l2  条目名称, 科目.fcodingnumber 科目编码, 拆分分录.FAmount 应取金额  /*变更金额*/");
//    	sb.append(" from  T_CON_ConChangeSplitEntry 拆分分录");
//    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
//    	sb.append(" left join T_CON_ConChangeSplit 变更拆分 on  变更拆分.fid=拆分分录.FParentID");
//    	sb.append(" left join T_CON_ContractChangeBill 变更 on 变更.fid=变更拆分.FContractChangeID");
//    	sb.append(" left join T_CON_ChangeAuditBill 变更审批单  on 变更审批单.fid=变更.FChangeAuditID");
//    	sb.append(" left join T_FDC_ChangeType 变更类型 on 变更类型.fid=变更.FChangeTypeID ");
//    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
//    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
//    	sb.append(" and 变更.FIsConSetted=0 and  拆分分录.flevel='0' and 变更审批单.CFAddToContractCost=1");
    	sb.append(" union all");
    	sb.append(" select 科目.fname_l2 条目名称, 科目.fcodingnumber 科目编码,拆分分录.FAmount 应取金额 /*结算未拆分*/");
    	sb.append(" from T_FDC_CostAccount 科目");
    	sb.append(" left join T_CON_ContractCostSplitEntry 拆分分录 on 拆分分录.FCostAccountID=科目.fid");
    	sb.append(" left join T_CON_ContractCostSplit 合同拆分 on 合同拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractBill 合同 on 合同.fid=合同拆分.FContractBillID");
    	sb.append(" left join T_AIM_AimCost acost on acost.FOrgOrProId=科目.FCurProject");
    	sb.append(" left join T_CON_ContractSettlementBill 结算单 on 结算单.FContractBillID=合同.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit 结算拆分 on 结算单.fid=结算拆分.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry 结算拆分分录 on 结算拆分.fid=结算拆分分录.FParentID  and 拆分分录.FCostAccountID=结算拆分分录.FCostAccountID");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 合同.fstate in ('4AUDITTED', '3AUDITTING')");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and acost.FIsLastVersion='1' and 拆分分录.flevel='0' and 结算拆分.fid is null");
    	sb.append(" and 结算单.FIsSettled='1'");
    	sb.append(" )");
    	sb.append(" group by 科目编码,条目名称");
    	return sb.toString();
    }
    /**
     * 在途合同成本
     */
    private String getOnWayCostSql(CurProjectInfo info, String codingNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fcodingnumber 科目编码, 科目.fname_l2 科目名称,sum(拆分分录.famount) 在途成本");
    	sb.append(" from T_CON_ContractBillSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
//    	sb.append(" left join T_CON_ContractCostSplit 合同拆分 on 合同拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractBill 合同 on 合同.fid=拆分分录.FParentID");
    	sb.append(" where 1=1 ");
    	sb.append(" and 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+codingNumber+"%'");
    	sb.append(" and 合同.fstate='3AUDITTING'");
    	sb.append(" and 拆分分录.flevel='0'");
    	sb.append(" group by 科目.fcodingnumber,科目.fname_l2 ");
    	return sb.toString();
    }
    /**
     * 变更(工程指令, 技术核定, 设计变更对应设计变更,技术经济签证对应现场签证)
     */
    private String getActConChangeSql(CurProjectInfo info, String accountNumber, String changeName) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fname_l2 科目名称,科目.fcodingnumber 科目编码,sum(拆分分录.FAmount) 变更金额 ");
    	sb.append(" from T_CON_ConChangeSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit 变更拆分 on  变更拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill 变更 on 变更.fid=变更拆分.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill 变更审批单  on 变更审批单.fid=变更.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType 变更类型 on 变更类型.fid=变更.FChangeTypeID ");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and 变更类型.fname_l2 in ("+changeName+")");
    	sb.append(" and 变更.FIsConSetted=0 and  拆分分录.flevel='0' /*and 变更审批单.CFAddToContractCost=0*/ and 变更审批单.CFBillType !='10'");
    	sb.append(" group by 科目.fname_l2,科目.fcodingnumber");
    	sb.append(" union all");
    	sb.append(" select 科目.fname_l2 条目名称, 科目.fcodingnumber 科目编码, sum(拆分分录.FAmount) 变更金额");
    	sb.append(" from T_CON_ConChangeSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit 变更拆分 on  变更拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill 变更 on 变更.fid=变更拆分.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill 变更审批单  on 变更审批单.fid=变更.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType 变更类型 on 变更类型.fid=变更.FChangeTypeID ");
    	sb.append(" left join T_CON_ContractBill 合同 on 合同.fid=变更.FContractBillID");
    	sb.append(" left join T_CON_ContractSettlementBill 结算单 on 结算单.FContractBillID=合同.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit 结算拆分 on 结算单.fid=结算拆分.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry 结算拆分分录 on 结算拆分.fid=结算拆分分录.FParentID  and 拆分分录.FCostAccountID=结算拆分分录.FCostAccountID");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and 变更审批单.CFBillType !='10'");
    	sb.append(" and 变更类型.fname_l2 in ("+changeName+")");
    	sb.append(" and 拆分分录.flevel='0' and 结算拆分.fid is null and 结算单.FIsSettled='1'");
    	sb.append(" group by 科目.fname_l2,科目.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 结算
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActSettleSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fname_l2 条目名称,科目.fcodingnumber 科目编码,sum(拆分分录.FAmount) 结算金额");
    	sb.append(" from T_CON_SettlementCostSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and 拆分分录.Flevel='0'");
    	sb.append(" group by 科目.fname_l2,科目.fcodingnumber");
    	return sb.toString();
    }
    
    /**
     * 获取其他调整拆分金额
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActOtherAdjustSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fcodingnumber 科目编码,sum(拆分分录.FAmount) 其他调整金额");
    	sb.append(" from CT_AIM_OtherSplitNewEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" where 1=1");
    	sb.append(" and 拆分分录.flevel='0'");
    	sb.append(" and 科目.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" group by 科目.fcodingnumber");
    	sb.append(" order by 科目.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 获取预估变更拆分
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActForecastSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fcodingnumber 科目编码, sum(拆分分录.famount) 预估金额");
    	sb.append(" from T_AIM_ForecastVisSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.fcostaccountid");
    	sb.append(" left join CT_AIM_ForecastChangeVis 预估变更 on 预估变更.fid=拆分分录.fparentid");
    	sb.append(" where 1=1");
    	sb.append(" and 科目.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and 预估变更.cfislast='1'");
    	sb.append(" group by 科目.fcodingnumber");
    	sb.append(" order by 科目.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 获取变更实际发生(工程指令,技术核定,设计变更,技术经济签证对应预估变更实际会发生的)
     */
    private String getActChangeSql(CurProjectInfo info, String accountNumber, String changeName) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select 科目.fname_l2 科目名称,科目.fcodingnumber 科目编码,sum(拆分分录.FAmount) 变更金额 ");
    	sb.append(" from T_CON_ConChangeSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit 变更拆分 on  变更拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill 变更 on 变更.fid=变更拆分.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill 变更审批单  on 变更审批单.fid=变更.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType 变更类型 on 变更类型.fid=变更.FChangeTypeID ");
    	sb.append(" left join T_CON_ChangeSupplierEntry 下发单位 on 下发单位.fparentid=变更审批单.fid");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and 变更类型.fname_l2 in ("+changeName+")");
    	sb.append(" and 变更.FIsConSetted=0 and  拆分分录.flevel='0' and 变更审批单.CFBillType !='10'");
    	sb.append(" and 下发单位.cfforecastchangevi is not null");
    	sb.append(" group by 科目.fname_l2,科目.fcodingnumber");
    	sb.append(" union all");
    	sb.append(" select 科目.fname_l2 条目名称, 科目.fcodingnumber 科目编码, sum(拆分分录.FAmount) 变更金额");
    	sb.append(" from T_CON_ConChangeSplitEntry 拆分分录");
    	sb.append(" left join T_FDC_CostAccount 科目 on 科目.fid=拆分分录.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit 变更拆分 on  变更拆分.fid=拆分分录.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill 变更 on 变更.fid=变更拆分.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill 变更审批单  on 变更审批单.fid=变更.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType 变更类型 on 变更类型.fid=变更.FChangeTypeID ");
    	sb.append(" left join T_CON_ContractBill 合同 on 合同.fid=变更.FContractBillID");
    	sb.append(" left join T_CON_ContractSettlementBill 结算单 on 结算单.FContractBillID=合同.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit 结算拆分 on 结算单.fid=结算拆分.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry 结算拆分分录 on 结算拆分.fid=结算拆分分录.FParentID  and 拆分分录.FCostAccountID=结算拆分分录.FCostAccountID");
    	sb.append(" left join T_CON_ChangeSupplierEntry 下发单位 on 下发单位.fparentid=变更审批单.fid");
    	sb.append(" where 科目.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and 科目.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and 变更审批单.CFBillType !='10'");
    	sb.append(" and 变更类型.fname_l2 in ("+changeName+")");
    	sb.append(" and 下发单位.cfforecastchangevi is not null");
    	sb.append(" and 拆分分录.flevel='0' and 结算拆分.fid is null and 结算单.FIsSettled='1'");
    	sb.append(" group by 科目.fname_l2,科目.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 获取科目树
     * @param info
     * @param codingnumber
     * @return
     */
    private String getCostAccountSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fname_l2 科目名称, account.fcodingnumber 科目编码, account.FLevel 级次");
    	sb.append(" from T_FDC_CostAccount account");
    	sb.append(" where account.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and account.fcodingnumber like '"+codingnumber+"%'");
    	sb.append(" and account.fisenable='1'");
    	sb.append(" order by account.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 获取有子节点的科目
     * @param info
     * @param codingnumber
     * @return
     */
    private String getParentCostAccountSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select fcodingnumber 科目编码, flevel 级次  from T_FDC_CostAccount");
    	sb.append(" where fid in");
    	sb.append(" (");
    	sb.append(" select distinct fparentid from T_FDC_CostAccount");
    	sb.append(" where fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and fcodingnumber like '"+codingnumber+"%'");
    	sb.append(" )");
    	return sb.toString();
    }
    /**
     * 获取无文本合同
     * @param info
     * @param codingnumber
     * @return
     */
    private String getNoTextContractSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fname_l2 科目名称,account.fcodingnumber 科目编码,sum(sentry.FAmount) 无文本金额");
    	sb.append(" from T_CON_ContractWithoutText text");
    	sb.append(" left join T_FNC_PaymentSplit split on text.fid = split.FConWithoutTextID");
    	sb.append(" left join T_FNC_PaymentSplitEntry sentry on sentry.FParentID = split.fid");
    	sb.append(" left join T_FDC_CostAccount account on account.fid = sentry.FCostAccountID");
    	sb.append(" left join T_AIM_AimCost acost on acost.FOrgOrProId = account.fcurproject");
    	sb.append(" where account.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and sentry.flevel='0'");
    	sb.append(" and acost.FVersionNumber=(select MAX(to_number(FVERSIONNUMBER))from T_AIM_AimCost WHERE FOrgOrProId = account.fcurproject)");
    	sb.append(" group by account.fname_l2, account.fcodingnumber");
    	sb.append(" order by account.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 获取目标成本
     * @param info
     * @param codingnumber
     * @return
     */
    private String getAimCostSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fcodingnumber 科目编码, sum(entry.FCostAmount) 目标成本 ");
    	sb.append(" from T_AIM_CostEntry entry");
    	sb.append(" left join T_AIM_AimCost aimCost on aimCost.fid=entry.fheadid");
    	sb.append(" left join T_FDC_CostAccount account on account.fid=entry.fcostaccountid");
    	sb.append(" where 1=1");
    	sb.append(" and aimCost.FOrgOrProId='"+info.getId().toString()+"'");
    	sb.append(" and TO_NUMBER(aimCost.fversionnumber)=");
    	sb.append(" (");
    	sb.append(" select max(TO_NUMBER(fversionnumber)) from t_aim_aimcost where forgOrProId='"+info.getId().toString()+"'");
    	sb.append(" )");
    	sb.append(" group by account.fcodingnumber");
    	sb.append(" order by account.fcodingnumber");
    	return sb.toString();
    }
    /**
     * 一级预警
     */
    protected void prmtfirstLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * 二级预警
     */
    protected void prmtsecondLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * 三级预警
     */
    protected void prmtthirdLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * 恢复职位
     */
    private void restorePosition() {
    	Set firstSet = new HashSet();
    	Set secondSet = new HashSet();
    	Set thirdSet = new HashSet();
    	for(int i = 0; i < kdtEntryPosition.getRowCount(); i++) {
    		IRow row = kdtEntryPosition.getRow(i);
    		Integer level = (Integer) row.getCell("level").getValue();
    		if(level.intValue() == 1)
    			firstSet.add(row.getCell("position").getValue());
    		else if(level.intValue() == 2)
    			secondSet.add(row.getCell("position").getValue());
    		else if(level.intValue() == 3)
    			thirdSet.add(row.getCell("position").getValue());
    	}
    	prmtfirstLevelPos.setValue(firstSet.toArray());
    	prmtsecondLevelPos.setValue(secondSet.toArray());
    	prmtthirdLevelPos.setValue(thirdSet.toArray());
    }
    /**
     * 保存职位
     */
    private void savePosition() {
    	this.kdtEntryPosition.removeRows();
    	Object[] firstPosition = (Object[]) this.prmtfirstLevelPos.getValue();
    	Object[] secondPosition = (Object[]) this.prmtsecondLevelPos.getValue();
    	Object[] thirdPosition = (Object[]) this.prmtthirdLevelPos.getValue();
    	
    	for(int i = 0; i < firstPosition.length; i++) {
    		if(firstPosition[i] != null) {
    			IRow addRow = kdtEntryPosition.addRow();
    			addRow.getCell("level").setValue(Integer.valueOf(1));
    			addRow.getCell("position").setValue(firstPosition[i]);
    		}
    	}
    	for(int i = 0; i < secondPosition.length; i++) {
    		if(secondPosition[i] != null) {
    			IRow addRow = kdtEntryPosition.addRow();
    			addRow.getCell("level").setValue(Integer.valueOf(2));
    			addRow.getCell("position").setValue(secondPosition[i]);
    		}
    	}
    	for(int i = 0; i < thirdPosition.length; i++) {
    		if(thirdPosition[i] != null) {
    			IRow addRow = kdtEntryPosition.addRow();
    			addRow.getCell("level").setValue(Integer.valueOf(3));
    			addRow.getCell("position").setValue(thirdPosition[i]);
    		}
    	}
    }
    /**
     * 修订
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		MsgBox.showWarning("请先保存!");
    		SysUtil.abort();
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
    		MsgBox.showWarning("非已审核单据无法修订!");
    		SysUtil.abort();
    	}
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("info", editData);
    	UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ProjectDynamicCostEditUI.class.getName(), uiContext, null,OprtState.ADDNEW).show();
    	syncDataFromDB();
    }
    /**
     * 审核
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		MsgBox.showWarning("请先保存!");
    		SysUtil.abort();
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
    		MsgBox.showWarning("非提交状态单据无法审核!");
    		SysUtil.abort();
    	}
    	FDCClientUtils.checkBillInWorkflow(this,getSelectBOID());
    	super.actionAudit_actionPerformed(e);
    	syncDataFromDB();
    	MsgBox.showInfo("审核成功!");
    }
    /**
     * 反审核
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		MsgBox.showWarning("请先保存!");
    		SysUtil.abort();
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
    		MsgBox.showWarning("非审核状态单据无法反审核!");
    		SysUtil.abort();
    	}
    	if(!editData.isIsLatest()) {
    		MsgBox.showWarning("非最新版无法反审核!");
    		SysUtil.abort();
    	}
    	FDCClientUtils.checkBillInWorkflow(this,getSelectBOID());
    	super.actionUnAudit_actionPerformed(e);
    	syncDataFromDB();
    	MsgBox.showInfo("反审核成功!");
    }
    protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
    }
    protected void spYear_stateChanged(ChangeEvent e) throws Exception {
    }
    /**
	 * 同步数据库数据到界面,用于审批/反审批后显示审批人,审批日期
	 * @throws Exception
	 */
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	savePosition();
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	savePosition();
        super.actionSubmit_actionPerformed(e);
        syncDataFromDB();
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	checkRepeatBill();
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkBillInWorkflow(this,getSelectBOID());
    	if(!(editData.getState().equals(FDCBillStateEnum.SAVED) || 
    		 editData.getState().equals(FDCBillStateEnum.SUBMITTED))) {
    		MsgBox.showWarning("非保存或者提交单据无法修改!");
    		SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
        this.btnLoadData.setEnabled(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkBillInWorkflow(this,getSelectBOID());
    	if(!(editData.getState().equals(FDCBillStateEnum.SAVED) || 
       		 editData.getState().equals(FDCBillStateEnum.SUBMITTED))) {
    		MsgBox.showWarning("非保存状态单据无法删除!");
    		SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	CurProjectInfo curProject = (CurProjectInfo) getUIContext().get("project");
    	ProjectDynamicCostInfo info = (ProjectDynamicCostInfo) getUIContext().get("info");
    	if(info == null) {
    		info = new ProjectDynamicCostInfo();
    		info.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    		
    		info.setCurProject(curProject);
    		info.setVersion(1);
    		info.setBizDate(Calendar.getInstance().getTime());
    	} else {
    		info.setId(null);
    		info.setVersion(info.getVersion()+1);
    		ProjectDynamicCostEntryCollection entrys = info.getEntrys();
    		ProjectDynamicCostEntrysAccountCollection entrysAccount = info.getEntrysAccount();
    		ProjectDynamicCostEntryPositionCollection entryPosition = info.getEntryPosition();
    		ProjectDynamicCostEentryTotalCollection entryTotal = info.getEentryTotal();
    		ProjectDynamicCostEntrySixMonthCollection entrySixMonth = info.getEntrySixMonth();
    		
    		for(int i = 0; i < entrys.size(); i++) {
    			ProjectDynamicCostEntryInfo entryInfo = entrys.get(i);
    			entryInfo.setId(null);
    		}
    		for(int i = 0; i < entrysAccount.size(); i++) {
    			ProjectDynamicCostEntrysAccountInfo entryInfo = entrysAccount.get(i);
    			entryInfo.setId(null);
    		}
    		for(int i = 0; i < entryPosition.size(); i++) {
    			ProjectDynamicCostEntryPositionInfo entryInfo = entryPosition.get(i);
    			entryInfo.setId(null);
    		}
    		for(int i = 0; i < entryTotal.size(); i++) {
    			ProjectDynamicCostEentryTotalInfo entryInfo = entryTotal.get(i);
    			entryInfo.setId(null);
    		}
    		for(int i = 0; i < entrySixMonth.size(); i++) {
    			ProjectDynamicCostEntrySixMonthInfo entryInfo = entrySixMonth.get(i);
    			entryInfo.setId(null);
    		}
    	}
    	info.setState(FDCBillStateEnum.SAVED);
    	info.setIsLatest(false);
    	info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        return info;
    }

}