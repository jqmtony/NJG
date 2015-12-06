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

import javax.swing.SpinnerNumberModel;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisCollection;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo;
import com.kingdee.eas.fdc.aimcost.IForecastChangeVis;
import com.kingdee.eas.fdc.aimcost.client.AbstractForecastChangeVisEditUI;
import com.kingdee.eas.fdc.aimcost.client.ForecastChangeVisEditUI;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fm.ecore.app.bean.commercialdraft.ContractInformation;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
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
    BigDecimal HALF_ONE_MILLION = new BigDecimal(5000000);
    BigDecimal THREE = new BigDecimal(300000);
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
    	
//    	this.kDTabbedPane2.remove(6);//���ش洢�õķ�¼
    	this.kDTabbedPane2.remove(5);
    	this.kDTabbedPane2.remove(4);
    	this.kDTabbedPane2.remove(3);
    	this.kDTabbedPane2.remove(2);
    	this.kDTabbedPane2.remove(1);
    	this.kDTabbedPane2.remove(0);
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
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * ���ú�ͬά�ȸ��ݺ�Լ�滮���ں�
     */
    private void setContractTableMerge() {
    	String[] mergeColumn = new String[] {"programmingName", "Amount", "sumB", "unSignContract", "sumC", "dynamicCost", "diffAmount","diffRate"}; 
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
     * �������
     */
    private void initTableControl() {
    	this.kdtEntrysContract.getStyleAttributes().setLocked(true);
    	this.kdtEntrysAccount.getStyleAttributes().setLocked(true);
    	this.kdtEntrysAccount.getColumn("seq").getStyleAttributes().setHided(true);
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	this.kDTable2.getStyleAttributes().setLocked(true);
    	this.kDTable3.getStyleAttributes().setLocked(true);
    	this.kDTable4.getStyleAttributes().setLocked(true);
    	this.kDTable5.getStyleAttributes().setLocked(true);
    	this.kDTable6.getStyleAttributes().setLocked(true);
    	
    	this.kDContainer2.getContentPane().add(kdtEntrysContract);
    	this.kDContainer1.getContentPane().add(kdtEntrysAccount);
    	
    	this.kdtEntrysAccount.getColumn("Comment").getStyleAttributes().setLocked(false);
    	this.kdtEntrysAccount.getColumn("accountIndex").getStyleAttributes().setHided(true);
    	this.kdtEntrysAccount.getColumn("level").getStyleAttributes().setHided(true);
    }
    /**
     * ��ȡ�������ݰ�ť
     */
    protected void btnLoadData_actionPerformed(ActionEvent e) throws Exception {
    	//��ȡ��������
    	if(kdtEntrysContract.getRowCount() > 0) {
    		int confirm = MsgBox.showConfirm2("�Ƿ������������?");
    		if(confirm != MsgBox.YES) {
    			SysUtil.abort();
    		}
    	}
    	loadContractTableData();
    	loadCostAccountTableData();
    }
    
    /**
     * ���غ�ͬά������
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
    		addRow.getCell("ContractAmount").setValue(rowSet.getBigDecimal("contractAmount"));
    		addRow.getCell("onWayCost").setValue(rowSet.getBigDecimal("onWayAmount") == null ? BigDecimal.ZERO:rowSet.getBigDecimal("onWayAmount"));
    	}
    	//��Ʊ��
    	String conChangeSql = getConChangeSql(editData.getCurProject(), "'����ָ��', '�����˶�', '��Ʊ��'");
    	Map changeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(conChangeSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
//    		Map detail = new HashMap();
    		BigDecimal changeAmount = rowSet.getBigDecimal("changeAmount");
    		changeMap.put(rowSet.getString("contractId"), changeAmount);
    	}
    	//�ֳ�ǩ֤
    	String siteChangeSql = getConChangeSql(editData.getCurProject(), "'��������ǩ֤'");
    	Map siteChangeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(siteChangeSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		BigDecimal siteChangeAmount = rowSet.getBigDecimal("changeAmount");
    		siteChangeMap.put(rowSet.getString("contractId"), siteChangeAmount);
    	}
    	//�ѷ���������
    	String settmentSql = getSettmentSql(editData.getCurProject(), 1);
    	Map settleMap = new HashMap();
    	builder.clear();
    	builder.appendSql(settmentSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		BigDecimal settleAmount = rowSet.getBigDecimal("settleAmount");
    		settleMap.put(rowSet.getString("contractId"), settleAmount);
    	}
//    	//Ԥ�����
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
//    	//Ԥ�����ʵ�ʷ���
//    	String actForecastSql = getgetForecasetChangeBalanceSql(editData.getCurProject());
//    	Map actForeCastMap = new HashMap();
//    	builder.clear();
//    	builder.appendSql(actForecastSql);
//    	rowSet = builder.executeQuery();
//    	while(rowSet.next()) {
//    		actForeCastMap.put(rowSet.getString("contractId"), rowSet.getBigDecimal("costAmount"));
//    	}
    	//������������
    	String curMonthOtherSql = getCurMonthOtherSql(editData.getCurProject());
    	Map otherChangeMap = new HashMap();
    	builder.clear();
    	builder.appendSql(curMonthOtherSql);
    	rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		Map detailMap = new HashMap();
    		detailMap.put("adjustAmount", rowSet.getBigDecimal("adjustAmount"));
    		detailMap.put("billId", rowSet.getString("billId"));
    		otherChangeMap.put(rowSet.getString("contractId"), detailMap);
    	}
    	//�������³�������������
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

    		ForecastChangeVisInfo forecastInfo = getForecastChangeVisInfoByContract(null, contractId);
    		ForecastChangeVisInfo preForecastInfo = getPreForecastChangeVisInfoByContract(null, contractId);
    		//Ԥ�����
//    		Map forecasetDetailMap = (Map) forecasetChangeMap.get(contractId);
    		BigDecimal actForecastAmount = getgetForecasetChangeBalanceSql(editData.getCurProject(), contractId);//���ʵ�ʷ�����
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
    				if(!forecastInfo.isBanZreo())
    					row.getCell("EstimateChangeBalance").setValue(balance);
    				else
    					row.getCell("EstimateChangeBalance").setValue(BigDecimal.ZERO);
    			}
    		} else {
    			row.getCell("curMonthEstimateChange").setValue(BigDecimal.ZERO);
    			if(preForecastInfo != null)
    				row.getCell("EstimateChangeBalance").setValue(preForecastInfo.getAmount());
    			else
    				row.getCell("EstimateChangeBalance").setValue(BigDecimal.ZERO);
    		}
    		//��������
    		Map otherDetailMap = (Map) otherChangeMap.get(row.getCell("ContractBillId").getValue().toString());
    		if(otherDetailMap != null) {
    			otherAmount = (BigDecimal) otherDetailMap.get("adjustAmount");
    			row.getCell("curMonthOtherAmount").setValue(otherAmount==null ? BigDecimal.ZERO : otherAmount);
    			row.getCell("curMonthOtherId").setValue(otherDetailMap.get("billId"));
    		} else {
    			row.getCell("curMonthOtherAmount").setValue(BigDecimal.ZERO);
    		}
    		BigDecimal allOtherAmount = (BigDecimal) allOtherMap.get(row.getCell("ContractBillId").getValue().toString());
    		row.getCell("otherAmount").setValue(allOtherAmount==null ? BigDecimal.ZERO : allOtherAmount);
    	}
    	calculateSum();
    }
    /**
     * ��Ŀά��table ��״��ʾ
     */
    private void showTableTreeStyle() {
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	String parentCostAccountSql = getParentCostAccountSql(editData.getCurProject(), "4101");
    	builder.appendSql(parentCostAccountSql);
    	IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			while(rowSet.next()) {
				parentAccountMap.put(rowSet.getString("��Ŀ����"), rowSet.getInt("����"));
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
     * ��ȡ��Լ�滮
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
     * ���ؿ�Ŀά������
     * @throws BOSException 
     */
    private void loadCostAccountTableData() throws Exception {
    	kdtEntrysAccount.removeRows();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	CurProjectInfo curProject = editData.getCurProject();
    	//���ؿ�Ŀ�� 
    	String costAccountSql = getCostAccountSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(costAccountSql);
    	IRowSet rowSet = builder.executeQuery();
    	while(rowSet.next()) {
    		IRow addRow = kdtEntrysAccount.addRow();
    		addRow.getCell("costAccount").setValue(rowSet.getString("��Ŀ����"));
    		addRow.getCell("costAccountNumber").setValue(rowSet.getString("��Ŀ����"));
    		int level = rowSet.getInt("����");
    		addRow.getCell("level").setValue(level);
    		addRow.setTreeLevel(rowSet.getInt("����")-1);
    		if(maxLevel < level)
    			maxLevel = level;
    		Integer parentLevel = (Integer) parentAccountMap.get(rowSet.getString("��Ŀ����"));
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
    	
    	//---------------------------------------Ŀ��ɱ�
    	String aimCostSql = getAimCostSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(aimCostSql);
    	rowSet = builder.executeQuery();
    	Map aimCostMap = new HashMap();
    	while(rowSet.next()) {
    		aimCostMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("Ŀ��ɱ�"));
    	}
    	//---------------------------------------��ͬ���
    	String actContractSql = getActContractSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actContractSql);
    	rowSet = builder.executeQuery();
    	Map contractMap = new HashMap();
    	while(rowSet.next()) {
    		contractMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("ǩԼ���"));
    	}
    	//---------------------------------------��Ʊ��
    	String actConChangeSql = getActConChangeSql(curProject, "4101", "'����ָ��','�����˶�','��Ʊ��'");
    	builder.clear();
    	builder.appendSql(actConChangeSql);
    	rowSet = builder.executeQuery();
    	Map desChangeMap = new HashMap();
    	while(rowSet.next()) {
    		desChangeMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("������"));
    	}
    	//---------------------------------------�ֳ�ǩ֤
    	String actTechChangeSql = getActConChangeSql(curProject, "4101", "'��������ǩ֤'");
    	builder.clear();
    	builder.appendSql(actTechChangeSql);
    	rowSet = builder.executeQuery();
    	Map techChangeMap = new HashMap();
    	while(rowSet.next()) {
    		techChangeMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("������"));
    	}
    	//---------------------------------------����
    	String actSettleSql = getActSettleSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actSettleSql);
    	rowSet = builder.executeQuery();
    	Map settleMap = new HashMap();
    	while(rowSet.next()) {
    		settleMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("������"));
    	}
    	//---------------------------------------���ı���ͬ
    	String noTextContractSql = getNoTextContractSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(noTextContractSql);
    	rowSet = builder.executeQuery();
    	Map noTextContractMap = new HashMap();
    	while(rowSet.next()) {
    		noTextContractMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("���ı����"));
    	}
    	//---------------------------------------��;�ɱ�
    	String onWayCostSql = getOnWayCostSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(onWayCostSql);
    	rowSet = builder.executeQuery();
    	Map onWayCostMap = new HashMap();
    	while(rowSet.next()) {
    		onWayCostMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("��;�ɱ�"));
    	}
    	//---------------------------------------�����������
    	String otherAdjustSql = getActOtherAdjustSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(otherAdjustSql);
    	rowSet = builder.executeQuery();
    	Map otherAdjustMap = new HashMap();
    	while(rowSet.next()) {
    		otherAdjustMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("�����������"));
    	}
    	//---------------------------------------Ԥ��������
    	String actForecastSql = getActForecastSql(curProject, "4101");
    	builder.clear();
    	builder.appendSql(actForecastSql);
    	rowSet = builder.executeQuery();
    	Map actForecastMap = new HashMap();
    	while(rowSet.next()) {
    		actForecastMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("Ԥ�����"));
    	}
    	//---------------------------------------Ԥ�����ʵ�ʷ���
    	String actChangeSql = getActChangeSql(curProject, "4101", "'����ָ��','�����˶�','��Ʊ��','��������ǩ֤'");
    	builder.clear();
    	builder.appendSql(actChangeSql);
    	rowSet = builder.executeQuery();
    	Map changeMap = new HashMap();
    	while(rowSet.next()) {
    		changeMap.put(rowSet.getString("��Ŀ����"), rowSet.getBigDecimal("������"));
    	}
    	//---------------------------------------δǩ��ͬ
    	Map unSignContract = getUnSignContract();
    	
    	//---------------------------------------�������
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
    		//Ԥ��������
    		BigDecimal forcastChangeAmount = (BigDecimal) actForecastMap.get(accountNumber);
    		BigDecimal actChangeamount = (BigDecimal) changeMap.get(accountNumber);
    		BigDecimal banlance = forcastChangeAmount;
    		if(forcastChangeAmount != null && actChangeamount != null) {
    			banlance = forcastChangeAmount.subtract(actChangeamount);
    		}
    		row.getCell("EstimateChangeBalance").setValue(banlance);
    		//δǩ��ͬ
    		row.getCell("unSignContract").setValue(unSignContract.get(accountNumber));
    	}
    	
    	
    	//�𼶻���
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
     * ��Ŀά�Ȼ���
     */
    private void calculateAccountSum() {
    	for(int i = 0; i < kdtEntrysAccount.getRowCount(); i++) {
    		IRow row = kdtEntrysAccount.getRow(i);
    		BigDecimal aimCost = (BigDecimal) row.getCell("aimCost").getValue();
    		int level = ((Integer) row.getCell("level").getValue()).intValue();
    		//Ԥ��ָ��
    		if(aimCost != null && aimCost.compareTo(BigDecimal.ZERO) != 0) {
    			BigDecimal rate = BigDecimal.ZERO;
    			if(level == 1) {
    				rate = ONE_MILLION.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			} else if(level == 2) {
    				rate = HALF_ONE_MILLION.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			} else if(level == 3) {
    				rate = THREE.divide(aimCost,2,BigDecimal.ROUND_HALF_UP);
    			}
    			
    			row.getCell("alertIndex").setValue(ONE_HUNDRUD.add(rate));
    		}
    		//С��B
    		BigDecimal contractAmount = row.getCell("Contract").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("Contract").getValue();
    		BigDecimal desChangeAmount = row.getCell("designChangeAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("designChangeAmount").getValue();
    		BigDecimal siteCertAmount = row.getCell("siteCertificatAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("siteCertificatAmount").getValue();
    		BigDecimal settleAmount = row.getCell("settlementAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("settlementAmount").getValue();
    		BigDecimal withouttxt = row.getCell("withouttxt").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("withouttxt").getValue();
    		BigDecimal sumB = contractAmount.add(desChangeAmount).add(siteCertAmount).add(settleAmount).add(withouttxt);
    		row.getCell("sumB").setValue(sumB.compareTo(BigDecimal.ZERO) == 0 ? null : sumB);
    		
    		//С��C
    		BigDecimal onWayCost = row.getCell("onWayCost").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("onWayCost").getValue();
    		BigDecimal EstimateChangeBalance = row.getCell("EstimateChangeBalance").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("EstimateChangeBalance").getValue();
    		BigDecimal otherAmount = row.getCell("otherAmount").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("otherAmount").getValue();
    		BigDecimal unSignContract = row.getCell("unSignContract").getValue() == null ? BigDecimal.ZERO : (BigDecimal)row.getCell("unSignContract").getValue();
    		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(otherAmount).add(unSignContract);
    		row.getCell("sumC").setValue(sumC.compareTo(BigDecimal.ZERO) == 0 ? null : sumC);
    		
    		//��̬�ɱ��ϼ�
    		BigDecimal sumBC = sumB.add(sumC);
    		row.getCell("sumBC").setValue(sumBC.compareTo(BigDecimal.ZERO) == 0 ? null : sumBC);
    		//������
    		BigDecimal diffAmount = BigDecimal.ZERO;
    		if(aimCost != null)
    			diffAmount = aimCost.subtract(sumBC);
    		row.getCell("diffAmount").setValue(diffAmount.compareTo(BigDecimal.ZERO) == 0 ? null : diffAmount);
    		//������
    		BigDecimal diffRate = BigDecimal.ZERO;
    		if(aimCost != null)
    			diffRate = diffAmount.divide(aimCost, 4, BigDecimal.ROUND_HALF_UP);
    		row.getCell("diffRate").setValue(diffRate.compareTo(BigDecimal.ZERO) == 0 ? null : diffRate);
    	}
    }
    /**
     * ��Ŀά��δǩ��ͬ(���ݺ�Լ�滮��δǩ��ͬ�����)
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
     * ����ϼ����
     * @throws Exception 
     */
    private void calculateSum() throws Exception {
//    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
//    		IRow row = kdtEntrysContract.getRow(i);
//    		//����С��B
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
//    		//����δǩ��ͬ �滮���-�ѷ���
//    		row.getCell("unSignContract").setValue(planAmount.subtract(sumB));
//    		
//    		//����С��C
//    		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
////    		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
//    		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
//    		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
//    		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
//    		BigDecimal unSignContract = (BigDecimal) row.getCell("unSignContract").getValue();
//    		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(curMonthOtherAmount).add(otherAmount).add(unSignContract);
//    		row.getCell("sumC").setValue(sumC);
//    		
//    		//���㶯̬�ɱ� sumB+sumC
//    		row.getCell("dynamicCost").setValue(sumC.add(sumB));
//    		//�滮���
//    		BigDecimal diffAmount = planAmount.subtract(sumB).subtract(sumC);
//    		row.getCell("diffAmount").setValue(diffAmount);
//    		//�滮������
//    		BigDecimal diffRate = diffAmount.divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
//    		row.getCell("diffRate").setValue(diffRate);
//    	}
    	
    	//���ݺ�Լ�滮����
    	Map programMap = new HashMap();
    	for(int i = 0; i < kdtEntrysContract.getRowCount(); i++) {
    		IRow row = kdtEntrysContract.getRow(i);
    		String programmingId = row.getCell("programmingId").getValue().toString();
    		if(programMap.get(programmingId) == null) {
    			Map detailMap = new HashMap();
    			BigDecimal planAmount = (BigDecimal) row.getCell("Amount").getValue();
    			detailMap.put("planAmount", planAmount);
    			//����С��B
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
        		detailMap.put("sumB", sumB);
        		
        		//����С��C
        		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
        		
        		//����δǩ��ͬ �滮���-�ѷ���-��; 
        		ProgrammingContractInfo pInfo = getProgrammingContract(programmingId);
        		BigDecimal unSignContract = BigDecimal.ZERO;
        		if(!pInfo.isIscse())//�жϺ�Լ�Ƿ�ǩ��, ǩ������unSignContract Ϊ0
        			unSignContract = planAmount.subtract(sumB).subtract(onWayCost);
        		detailMap.put("unSignContract",unSignContract);
        		
//        		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
        		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
        		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
        		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
//        		BigDecimal unSignContract = (BigDecimal) row.getCell("unSignContract").getValue();
        		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(curMonthOtherAmount).add(otherAmount).add(unSignContract);
        		detailMap.put("sumC",sumC);
        		
        		
        		
        		//���㶯̬�ɱ� sumB+sumC
        		detailMap.put("dynamicCost",sumC.add(sumB));
        		//�滮���
        		BigDecimal diffAmount = planAmount.subtract(sumB).subtract(sumC);
        		detailMap.put("diffAmount",diffAmount);
        		//�滮������
        		BigDecimal diffRate = diffAmount.divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
        		detailMap.put("diffRate",diffRate);
        		
        		programMap.put(programmingId, detailMap);
    		} else {
    			Map detailMap = (Map) programMap.get(programmingId);
    			//����С��B
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
        		detailMap.put("sumB", preSumB.add(sumB));
        		
        		//С��C
        		BigDecimal onWayCost = (BigDecimal) row.getCell("onWayCost").getValue();
        		
        		//����δǩ��ͬ �滮���-�ѷ���-��;
        		ProgrammingContractInfo pInfo = getProgrammingContract(programmingId);
        		BigDecimal unSignContract = BigDecimal.ZERO;
        		if(!pInfo.isIscse())//�жϺ�Լ�Ƿ�ǩ��, ǩ������unSignContract Ϊ0
        			unSignContract = planAmount.subtract(sumB).subtract(onWayCost);
//        		BigDecimal unSignContract = planAmount.subtract(sumB).subtract(onWayCost);
        		BigDecimal preunSignContract = (BigDecimal) detailMap.get("unSignContract");
        		detailMap.put("unSignContract", preunSignContract.add(unSignContract));
        		
//        		BigDecimal curMonthEstimateChange = (BigDecimal) row.getCell("curMonthEstimateChange").getValue();
        		BigDecimal EstimateChangeBalance = (BigDecimal) row.getCell("EstimateChangeBalance").getValue();
        		BigDecimal curMonthOtherAmount = (BigDecimal) row.getCell("curMonthOtherAmount").getValue();
        		BigDecimal otherAmount = (BigDecimal) row.getCell("otherAmount").getValue();
//        		BigDecimal unSignContract = (BigDecimal) row.getCell("unSignContract").getValue();
        		
        		BigDecimal sumC = onWayCost.add(EstimateChangeBalance).add(curMonthOtherAmount).add(otherAmount).add(unSignContract);
        		BigDecimal preSumC = (BigDecimal) detailMap.get("sumC");
        		BigDecimal newSumC = preSumC.add(sumC);
        		detailMap.put("sumC", newSumC);
        		
        		
        		//���㶯̬�ɱ� ǰһ��
        		BigDecimal preDynamicCost = (BigDecimal) detailMap.get("dynamicCost");
        		detailMap.put("dynamicCost",preDynamicCost.add(sumB).add(sumC));
        		//�滮���
        		BigDecimal diffAmount = planAmount.subtract(newSumB).subtract(newSumC);
//        		BigDecimal preDiffAmount = (BigDecimal) detailMap.get("diffAmount");
//        		BigDecimal newDiffAmount = preDiffAmount.add(diffAmount);
        		detailMap.put("diffAmount",diffAmount);
        		//�滮������
        		BigDecimal diffRate = diffAmount.divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
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
     * table��Ԫ��˫���¼�
     */
    protected void kdtEntrysContract_tableClicked(KDTMouseEvent e)
    		throws Exception {
    	if(e.getClickCount() == 2) {
    		String key = kdtEntrysContract.getColumnKey(e.getColIndex());
    		//������������
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
    				if(otherId != null)
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherId").setValue(otherId.toString());
    				if(adjustAmount != null)
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").setValue(adjustAmount);
    			} else {
    				context.put(UIContext.ID, otherSplitId);
    				IUIWindow otherWindow = UIFactory.createUIFactory().create(OtherSplitBillEditUI.class.getName(), context, null, OprtState.VIEW);
    				otherWindow.show();
    				BigDecimal adjustAmount = ((OtherSplitBillEditUI)otherWindow.getUIObject()).editData.getAdjustAmount();
    				BigDecimal originAmount = (BigDecimal) kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").getValue();
    				if(originAmount != null && originAmount.compareTo(adjustAmount) != 0) {
    					kdtEntrysContract.getRow(e.getRowIndex()).getCell("curMonthOtherAmount").setValue(adjustAmount);
    				}
    			}
    		}
    		//����Ԥ�����
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
     * ��ԭ��ͬ��Ϣ
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
     * ��ȡĳ��Ŀ�ĺ�ͬ��Ϣ(�������Լ������)
     * @return
     */
    private String getContractSql(CurProjectInfo info) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select  contract.fname contractName /*��ͬ����*/, contract.fid contractId/*��ͬID*/,");
    	sb.append(" programming.famount programAmount/*�滮���*/,programming.fname_l2 programNAme/*��Լ�滮����*/,programming.fid programId,");
    	sb.append(" case when contract.fstate='4AUDITTED' then contract.famount else null end contractAmount/*��ͬ���*/,");
    	sb.append(" case when contract.fstate='3AUDITTING' then contract.famount else null end onWayAmount/*��;�ɱ�*/");
    	sb.append(" from t_con_contractbill contract /*��ͬ*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*������Ŀ*/");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*��Լ�滮*/");
    	sb.append(" where 1=1 ");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
//    	sb.append(" and contract.fstate in('4AUDITTED', '3AUDITTING')");
    	sb.append(" and contract.FProgrammingContract is not null"); //���˵�û�й�����Լ�滮����ʷ����
    	sb.append(" order by programming.fid");
    	return sb.toString();
    }
    /**
     * ��ȡĳ��Ŀ��ͬ ������(��Ʊ�������ֳ�ǩ֤)(δ���ɱ������+�����ɱ�����㵫��δ��ֵ�)
     * changeType ����ָ��, �����˶�, ��Ʊ������������ ��Ӧ��Ʊ��, ��������ǩ֤ ��Ӧ�ֳ�ǩ֤
     */
    private String getConChangeSql(CurProjectInfo info, String changeType) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select sum(conChange.Famount) changeAmount/*������*/, contract.fid contractId /*��ͬID*/");
    	sb.append(" from T_CON_ContractChangeBill conChange /*��ͬ�����*/");
    	sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=conChange.Fchangetypeid /*�������*/");
    	sb.append(" left join t_con_contractbill contract on contract.fid=conChange.Fcontractbillid /*��ͬ*/");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*��Լ�滮*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*������Ŀ*/");
    	sb.append(" where 1=1");
    	sb.append(" and changeType.Fname_L2 in ("+changeType+")");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
//    	sb.append(" and conChange.Fisconsetted='0'");
    	sb.append(" and contract.fid is not null");
    	sb.append(" and contract.FProgrammingContract is not null"); //���˵�û�й�����Լ�滮����ʷ����
    	sb.append(" group by changeType.Fname_L2,contract.fname, contract.fid");
//    	sb.append(" union all");
//    	sb.append(" select sum(conChange.Famount) ������, changeType.Fname_L2 �������,contract.fname ��ͬ����, contract.fid ��ͬID");
//    	sb.append(" from T_CON_ContractChangeBill conChange /*��ͬ�����*/");
//    	sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=conChange.FChangeTypeID /*�������*/");
//    	sb.append(" left join T_CON_ContractBill contract on contract.fid=conChange.FContractBillID /*��ͬ*/");
//    	sb.append(" left join T_CON_ContractSettlementBill consettle on consettle.FContractBillID=contract.fid /*��ͬ����*/");
//    	sb.append(" left join T_CON_SettlementCostSplit settleSplit on consettle.fid=settleSplit.FSettlementBillID /*������*/");
//    	sb.append(" left join T_CON_SettlementCostSplitEntry settleSplitEntry on settleSplit.fid=settleSplitEntry.FParentID");
//    	sb.append(" where  1=1");
//    	sb.append(" and changeType.Fname_L2 in ("+changeType+")");
//    	sb.append(" and settleSplit.fid is null");
//    	sb.append(" and consettle.FIsSettled='1'");
//    	sb.append(" group by changeType.Fname_L2,contract.fname, contract.fid");
    	return sb.toString();
    }
    /**
     * ��ȡĳ��Ŀ��ͬ��Ӧ�Ľ�����
     * @param info
     * @param flag (0����δ����,1�����ѽ���)
     * @return
     */
    private String getSettmentSql(CurProjectInfo info, int flag) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select conSettle.FSettlePrice settleAmount/*������*/,contract.fid contractId/*��ͬID*/");
    	sb.append(" from T_CON_ContractSettlementBill conSettle /*��ͬ����*/ ");
    	sb.append(" left join T_CON_ContractBill contract on contract.fid=conSettle.Fcontractbillid /*��ͬ*/ ");
    	sb.append(" left join T_CON_ProgrammingContract programming on programming.fid=contract.FProgrammingContract /*��Լ�滮*/");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*��Ŀ*/");
    	sb.append(" where conSettle.Fissettled='"+flag+"'");
    	sb.append(" and contract.FProgrammingContract is not null"); //���˵�û�й�����Լ�滮����ʷ����
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
    	return sb.toString();
    }
    
    /**
     * ��ȡĳ��Ŀ����Ԥ�����ǩ֤
     */
    private String getForecasetChangeSql(CurProjectInfo info) {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select forecastChange.CFAmount forecastChangeAmount/*Ԥ��������*/, contract.fid contractId/*��ͬID*/,forecastChange.Fid forecastChangeId");
    	sb.append(" from CT_AIM_ForecastChangeVis forecastChange");
    	sb.append(" left join T_CON_ContractBill contract on contract.fid=forecastChange.CFContractNumberID");
    	sb.append(" left join t_fdc_curproject curProject on curProject.fid=contract.fcurprojectid /*��Ŀ*/");
    	sb.append(" where 1=1");
    	sb.append(" and forecastChange.CFIsLast='1'");
    	sb.append(" and curProject.fid='"+info.getId().toString()+"'");
    	sb.append(" and forecastChange.FBizDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(date))+"'}");
    	sb.append(" and forecastChange.FBizDate<={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getLastDayOfMonth(date))+"'}");
    	return sb.toString();
    }
    /**
     * ��ȡԤ������ѷ������
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
    	sb.append(" and CHANGEAUDITBILL.FChangeState='7Visa'");
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
     * Ԥ�������������Ŀ�Լ���ͬ����ȡ  ���£�
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
     * Ԥ�������������Ŀ�Լ���ͬ����ȡ  ֮ǰ�£�
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
//    	Date date = getCurrentMonthDate(year, month);
    	
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("contractNumber.id", ContractId, CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("bizDate", FDCDateHelper.getFirstDayOfMonth(date), CompareType.LESS));
    	
    	SorterItemInfo sorterItemInfo = new SorterItemInfo("version");
    	sorterItemInfo.setSortType(SortType.DESCEND);
    	evi.getSorter().add(sorterItemInfo);
    	
    	ForecastChangeVisCollection forecastChangeVisCollection = iForecastChangeVis.getForecastChangeVisCollection(evi);
    	if(forecastChangeVisCollection.size() > 1)
    		forcastinfo = forecastChangeVisCollection.get(1);
    	return forcastinfo;
    }
    /**
     * ��ȡ���µ���������
     * @param info
     * @return
     */
    private String getCurMonthOtherSql(CurProjectInfo info) {
    	int year = this.spYear.getIntegerVlaue().intValue();
    	int month = this.spMonth.getIntegerVlaue().intValue();
    	Date date = getCurrentMonthDate(year, month);
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select bill.CFAdjustAmount adjustAmount,contract.fid contractId,bill.fid billId from CT_AIM_OtherSplitBill bill");
    	sb.append(" left join t_con_contractbill contract on contract.fid=bill.CFContractID");
    	sb.append(" left join t_fdc_curproject project on project.fid=bill.cfcurprojectid");
    	sb.append(" where 1=1");
    	sb.append(" and project.fid='"+info.getId().toString()+"'");
    	sb.append(" and bill.fbizdate >= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getFirstDayOfMonth(date))+"'}");
    	sb.append(" and bill.fbizdate <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getLastDayOfMonth(date))+"'}");
    	return sb.toString();
    }
    /**
     * ��ȡ�������³���������������
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
     * ���ݱ����·ݹ����³�����
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
    //-----------------------------------------------------��Ŀά��---------------------------------------------------------------//
    /**
     * ��ͬ��Ϣ
     */
    private String getActContractSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ����,��Ŀ����,sum(Ӧȡ���) ǩԼ��� from");
    	sb.append(" (");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����,��Ŀ.fcodingnumber ��Ŀ����,��ַ�¼.FAmount Ӧȡ��� /*ǩԼ���*/");
    	sb.append(" from T_FDC_CostAccount ��Ŀ ");
    	sb.append(" left join T_CON_ContractCostSplitEntry ��ַ�¼ on ��ַ�¼.FCostAccountID=��Ŀ.fid");
    	sb.append("	left join T_CON_ContractCostSplit ��ͬ��� on ��ͬ���.fid=��ַ�¼.FParentID");
    	sb.append("	left join T_CON_ContractBill ��ͬ on ��ͬ.fid=��ͬ���.FContractBillID");
    	sb.append("	left join T_AIM_AimCost acost on acost.FOrgOrProId=��Ŀ.FCurProject");
    	sb.append("	left join T_CON_ContractSettlementBill ���㵥 on ���㵥.FContractBillID=��ͬ.fid ");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and acost.FIsLastVersion='1'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and (���㵥.FIsSettled is null or ���㵥.FIsSettled='0')");
    	sb.append(" and ��ַ�¼.flevel=0");
    	sb.append(" union all");
    	sb.append(" select ��Ŀ.fname_l2  ��Ŀ����, ��Ŀ.fcodingnumber ��Ŀ����, ��ַ�¼.FAmount Ӧȡ���  /*������*/");
    	sb.append(" from  T_CON_ConChangeSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit ������ on  ������.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill ��� on ���.fid=������.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill ���������  on ���������.fid=���.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType ������� on �������.fid=���.FChangeTypeID ");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and ���.FIsConSetted=0 and  ��ַ�¼.flevel='0' and ���������.CFAddToContractCost=1");
    	sb.append(" union all");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����, ��Ŀ.fcodingnumber ��Ŀ����,��ַ�¼.FAmount Ӧȡ��� /*����δ���*/");
    	sb.append(" from T_FDC_CostAccount ��Ŀ");
    	sb.append(" left join T_CON_ContractCostSplitEntry ��ַ�¼ on ��ַ�¼.FCostAccountID=��Ŀ.fid");
    	sb.append(" left join T_CON_ContractCostSplit ��ͬ��� on ��ͬ���.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractBill ��ͬ on ��ͬ.fid=��ͬ���.FContractBillID");
    	sb.append(" left join T_AIM_AimCost acost on acost.FOrgOrProId=��Ŀ.FCurProject");
    	sb.append(" left join T_CON_ContractSettlementBill ���㵥 on ���㵥.FContractBillID=��ͬ.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit ������ on ���㵥.fid=������.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry �����ַ�¼ on ������.fid=�����ַ�¼.FParentID  and ��ַ�¼.FCostAccountID=�����ַ�¼.FCostAccountID");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and acost.FIsLastVersion='1' and ��ַ�¼.flevel='0' and ������.fid is null");
    	sb.append(" and ���㵥.FIsSettled='1'");
    	sb.append(" )");
    	sb.append(" group by ��Ŀ����,��Ŀ����");
    	return sb.toString();
    }
    /**
     * ��;��ͬ�ɱ�
     */
    private String getOnWayCostSql(CurProjectInfo info, String codingNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fcodingnumber ��Ŀ����, ��Ŀ.fname_l2 ��Ŀ����,sum(��ַ�¼.famount) ��;�ɱ�");
    	sb.append(" from T_CON_ContractCostSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.fcostaccountid");
    	sb.append(" left join T_CON_ContractCostSplit ��ͬ��� on ��ͬ���.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractBill ��ͬ on ��ͬ.fid=��ͬ���.FContractBillID");
    	sb.append(" where 1=1 ");
    	sb.append(" and ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+codingNumber+"%'");
    	sb.append(" and ��ͬ.fstate='3AUDITTING'");
    	sb.append(" and ��ַ�¼.flevel='0'");
    	sb.append(" group by ��Ŀ.fcodingnumber,��Ŀ.fname_l2 ");
    	return sb.toString();
    }
    /**
     * ���(����ָ��, �����˶�, ��Ʊ����Ӧ��Ʊ��,��������ǩ֤��Ӧ�ֳ�ǩ֤)
     */
    private String getActConChangeSql(CurProjectInfo info, String accountNumber, String changeName) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����,��Ŀ.fcodingnumber ��Ŀ����,sum(��ַ�¼.FAmount) ������ ");
    	sb.append(" from T_CON_ConChangeSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit ������ on  ������.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill ��� on ���.fid=������.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill ���������  on ���������.fid=���.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType ������� on �������.fid=���.FChangeTypeID ");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and �������.fname_l2 in ("+changeName+")");
    	sb.append(" and ���.FIsConSetted=0 and  ��ַ�¼.flevel='0' and ���������.CFAddToContractCost=0 and ���������.CFBillType !='10'");
    	sb.append(" group by ��Ŀ.fname_l2,��Ŀ.fcodingnumber");
    	sb.append(" union all");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����, ��Ŀ.fcodingnumber ��Ŀ����, sum(��ַ�¼.FAmount) ������");
    	sb.append(" from T_CON_ConChangeSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit ������ on  ������.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill ��� on ���.fid=������.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill ���������  on ���������.fid=���.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType ������� on �������.fid=���.FChangeTypeID ");
    	sb.append(" left join T_CON_ContractBill ��ͬ on ��ͬ.fid=���.FContractBillID");
    	sb.append(" left join T_CON_ContractSettlementBill ���㵥 on ���㵥.FContractBillID=��ͬ.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit ������ on ���㵥.fid=������.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry �����ַ�¼ on ������.fid=�����ַ�¼.FParentID  and ��ַ�¼.FCostAccountID=�����ַ�¼.FCostAccountID");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and ���������.CFBillType !='10'");
    	sb.append(" and �������.fname_l2 in ("+changeName+")");
    	sb.append(" and ��ַ�¼.flevel='0' and ������.fid is null and ���㵥.FIsSettled='1'");
    	sb.append(" group by ��Ŀ.fname_l2,��Ŀ.fcodingnumber");
    	return sb.toString();
    }
    /**
     * ����
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActSettleSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����,��Ŀ.fcodingnumber ��Ŀ����,sum(��ַ�¼.FAmount) ������");
    	sb.append(" from T_CON_SettlementCostSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and ��ַ�¼.Flevel='0'");
    	sb.append(" group by ��Ŀ.fname_l2,��Ŀ.fcodingnumber");
    	return sb.toString();
    }
    
    /**
     * ��ȡ����������ֽ��
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActOtherAdjustSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fcodingnumber ��Ŀ����,sum(��ַ�¼.cfamount) �����������");
    	sb.append(" from CT_AIM_OtherSplitBillEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.CFCostAccountID");
    	sb.append(" where 1=1");
    	sb.append(" and ��Ŀ.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" group by ��Ŀ.fcodingnumber");
    	sb.append(" order by ��Ŀ.fcodingnumber");
    	return sb.toString();
    }
    /**
     * ��ȡԤ��������
     * @param info
     * @param accountNumber
     * @return
     */
    private String getActForecastSql(CurProjectInfo info, String accountNumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fcodingnumber ��Ŀ����, sum(��ַ�¼.famount) Ԥ�����");
    	sb.append(" from T_AIM_ForecastVisSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.fcostaccountid");
    	sb.append(" left join CT_AIM_ForecastChangeVis Ԥ����� on Ԥ�����.fid=��ַ�¼.fparentid");
    	sb.append(" where 1=1");
    	sb.append(" and ��Ŀ.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and Ԥ�����.cfislast='1'");
    	sb.append(" group by ��Ŀ.fcodingnumber");
    	sb.append(" order by ��Ŀ.fcodingnumber");
    	return sb.toString();
    }
    /**
     * ��ȡ���ʵ�ʷ���(����ָ��,�����˶�,��Ʊ��,��������ǩ֤��ӦԤ�����ʵ�ʻᷢ����)
     */
    private String getActChangeSql(CurProjectInfo info, String accountNumber, String changeName) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����,��Ŀ.fcodingnumber ��Ŀ����,sum(��ַ�¼.FAmount) ������ ");
    	sb.append(" from T_CON_ConChangeSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit ������ on  ������.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill ��� on ���.fid=������.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill ���������  on ���������.fid=���.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType ������� on �������.fid=���.FChangeTypeID ");
    	sb.append(" left join T_CON_ChangeSupplierEntry �·���λ on �·���λ.fparentid=���������.fid");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and �������.fname_l2 in ("+changeName+")");
    	sb.append(" and ���.FIsConSetted=0 and  ��ַ�¼.flevel='0' and ���������.CFAddToContractCost=0 and ���������.CFBillType !='10'");
    	sb.append(" and �·���λ.cfforecastchangevi is not null");
    	sb.append(" group by ��Ŀ.fname_l2,��Ŀ.fcodingnumber");
    	sb.append(" union all");
    	sb.append(" select ��Ŀ.fname_l2 ��Ŀ����, ��Ŀ.fcodingnumber ��Ŀ����, sum(��ַ�¼.FAmount) ������");
    	sb.append(" from T_CON_ConChangeSplitEntry ��ַ�¼");
    	sb.append(" left join T_FDC_CostAccount ��Ŀ on ��Ŀ.fid=��ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ConChangeSplit ������ on  ������.fid=��ַ�¼.FParentID");
    	sb.append(" left join T_CON_ContractChangeBill ��� on ���.fid=������.FContractChangeID");
    	sb.append(" left join T_CON_ChangeAuditBill ���������  on ���������.fid=���.FChangeAuditID");
    	sb.append(" left join T_FDC_ChangeType ������� on �������.fid=���.FChangeTypeID ");
    	sb.append(" left join T_CON_ContractBill ��ͬ on ��ͬ.fid=���.FContractBillID");
    	sb.append(" left join T_CON_ContractSettlementBill ���㵥 on ���㵥.FContractBillID=��ͬ.fid ");
    	sb.append(" left join T_CON_SettlementCostSplit ������ on ���㵥.fid=������.FSettlementBillID ");
    	sb.append(" left join T_CON_SettlementCostSplitEntry �����ַ�¼ on ������.fid=�����ַ�¼.FParentID  and ��ַ�¼.FCostAccountID=�����ַ�¼.FCostAccountID");
    	sb.append(" left join T_CON_ChangeSupplierEntry �·���λ on �·���λ.fparentid=���������.fid");
    	sb.append(" where ��Ŀ.FCurProject='"+info.getId().toString()+"'");
    	sb.append(" and ��Ŀ.fcodingnumber like '"+accountNumber+"%'");
    	sb.append(" and ���������.CFBillType !='10'");
    	sb.append(" and �������.fname_l2 in ("+changeName+")");
    	sb.append(" and �·���λ.cfforecastchangevi is not null");
    	sb.append(" and ��ַ�¼.flevel='0' and ������.fid is null and ���㵥.FIsSettled='1'");
    	sb.append(" group by ��Ŀ.fname_l2,��Ŀ.fcodingnumber");
    	return sb.toString();
    }
    /**
     * ��ȡ��Ŀ��
     * @param info
     * @param codingnumber
     * @return
     */
    private String getCostAccountSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fname_l2 ��Ŀ����, account.fcodingnumber ��Ŀ����, account.FLevel ����");
    	sb.append(" from T_FDC_CostAccount account");
    	sb.append(" where account.fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and account.fcodingnumber like '"+codingnumber+"%'");
    	sb.append(" and account.fisenable='1'");
    	sb.append(" order by account.fcodingnumber");
    	return sb.toString();
    }
    /**
     * ��ȡ���ӽڵ�Ŀ�Ŀ
     * @param info
     * @param codingnumber
     * @return
     */
    private String getParentCostAccountSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select fcodingnumber ��Ŀ����, flevel ����  from T_FDC_CostAccount");
    	sb.append(" where fid in");
    	sb.append(" (");
    	sb.append(" select distinct fparentid from T_FDC_CostAccount");
    	sb.append(" where fcurproject='"+info.getId().toString()+"'");
    	sb.append(" and fcodingnumber like '"+codingnumber+"%'");
    	sb.append(" )");
    	return sb.toString();
    }
    /**
     * ��ȡ���ı���ͬ
     * @param info
     * @param codingnumber
     * @return
     */
    private String getNoTextContractSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fname_l2 ��Ŀ����,account.fcodingnumber ��Ŀ����,sum(sentry.FAmount) ���ı����");
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
     * ��ȡĿ��ɱ�
     * @param info
     * @param codingnumber
     * @return
     */
    private String getAimCostSql(CurProjectInfo info, String codingnumber) {
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select account.fcodingnumber ��Ŀ����, entry.FCostAmount Ŀ��ɱ� ");
    	sb.append(" from T_AIM_CostEntry entry");
    	sb.append(" left join T_AIM_AimCost aimCost on aimCost.fid=entry.fheadid");
    	sb.append(" left join T_FDC_CostAccount account on account.fid=entry.fcostaccountid");
    	sb.append(" where 1=1");
    	sb.append(" and aimCost.FOrgOrProId='"+info.getId().toString()+"'");
    	sb.append(" and TO_NUMBER(aimCost.fversionnumber)=");
    	sb.append(" (");
    	sb.append(" select max(TO_NUMBER(fversionnumber)) from t_aim_aimcost where forgOrProId='"+info.getId().toString()+"'");
    	sb.append(" )");
    	sb.append(" order by account.fcodingnumber");
    	return sb.toString();
    }
    /**
     * һ��Ԥ��
     */
    protected void prmtfirstLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * ����Ԥ��
     */
    protected void prmtsecondLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * ����Ԥ��
     */
    protected void prmtthirdLevelPos_dataChanged(DataChangeEvent e)
    		throws Exception {
    }
    /**
     * �ָ�ְλ
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
     * ����ְλ
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
     * �޶�
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
    }
    /**
     * ���
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    }
    /**
     * �����
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnAudit_actionPerformed(e);
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
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        this.btnLoadData.setEnabled(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
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
    		info.setId(BOSUuid.create(info.getBOSType()));
    		info.setVersion(info.getVersion()+1);
    		ProjectDynamicCostEntryCollection entrys = info.getEntrys();
    		ProjectDynamicCostEntrysAccountCollection entrysAccount = info.getEntrysAccount();
    		ProjectDynamicCostEntryPositionCollection entryPosition = info.getEntryPosition();
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
    	}
    	info.setState(FDCBillStateEnum.SAVED);
    	info.setIsLatest(false);
        return info;
    }

}