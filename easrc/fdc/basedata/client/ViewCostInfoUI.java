/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;

/**
 * output class name
 */
public class ViewCostInfoUI extends AbstractViewCostInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(ViewCostInfoUI.class);
    public static final String SETTLE_ADJUST = "settleAdjust";

	public static final String BUILD_PART = "buildPart";

	public static final String SELL_PART = "sellPart";

	public static final String DIFF = "diff";

	public static final String DYNAMIC_COST = "dynamicCost";

	public static final String AIM_COST = "aimCost";

	public static final String INTENDING_HAPPEN = "intendingHappen";

	public static final String HAS_HAPPEN = "hasHappen";

	public static final String NO_TEXT = "noText";
	
	public static final String HAS_PAY = "hasPay";

	public static final String HAS_PAID = "hasPaid";
	
	public static final String SETTLE = "Settle";

	public static final String NO_SETTLE = "NoSettle";

	public static final String SUB_TOTAL_SETTLE = "subTotalSettle";

	public static final String SUB_TOTAL_NO_SETTLE = "subTotalNoSettle";

	public static final String ASSIGN_AMOUNT_SETTLE = "assignAmountSettle";

	public static final String ASSIGN_AMOUNT_NO_SETTLE = "assignAmountNoSettle";
	public static final String PARTSETTLE_AMOUNT = "partSettleAmount";

	public static final String HASALLSETTLED_AMOUNT = "hasAllSettled";

	private ChangeTypeCollection changeTypes;


    private RetValue retValue;
    //可售面积
    private BigDecimal sellArea = null;
    //建筑面积 
	private BigDecimal buildArea = null;
    
	private Map amountMap = new HashMap();
	
	private boolean isAddThis = false;
	
    /**
     * output class constructor
     */
    public ViewCostInfoUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void initTable(){
    	costInfoTable.checkParsed();
    	costInfoTable.getColumn("aimCost").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("alreayHappend").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("DyCost").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("addCost").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("beforecanSaleBuild").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("endCanSaleBuild").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("endCanSaleBuild").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("beforecanSaleConstract").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("endCanSaleConstract").getStyleAttributes().setNumberFormat("####.00");
    	costInfoTable.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("alreayHappend").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("DyCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("addCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("beforecanSaleBuild").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("endCanSaleBuild").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("beforecanSaleConstract").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	costInfoTable.getColumn("endCanSaleConstract").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    }
    
   
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		this.changeTypes = ChangeTypeFactory.getRemoteInstance().getChangeTypeCollection(view);
    	super.onLoad();
    	setUITitle("科目成本信息");
    	this.actionExitCurrent.setVisible(false);
    	this.actionExit.setVisible(false);
    	Set projectIds  = new HashSet();
    	Set accountIds  = new HashSet();
    	ContractCostSplitEntryInfo info = null;
    	ConChangeSplitEntryInfo cInfo = null;
    	SettlementCostSplitEntryInfo sInfo = null;
    	String src = "contractSplit";
    	if(getUIContext().get("src") != null){
    		src = getUIContext().get("src").toString();
    	}
        if(getUIContext().get("entryCollection")!=null){
        	if(src.equals("contractSplit")){
        		ContractCostSplitEntryCollection cols = (ContractCostSplitEntryCollection) getUIContext().get("entryCollection");
            	for(Iterator it = cols.iterator();it.hasNext();){
            		info = (ContractCostSplitEntryInfo) it.next();
            		if(info.getCostAccount() != null){
            			projectIds.add(info.getCostAccount().getCurProject().getId().toString());
            			amountMap.put(info.getCostAccount().getId().toString(),info.getAmount());
            			accountIds.add(info.getCostAccount().getId().toString());
            		}
            		
            	}
        	}else if(src.equals("changeSplit")){
        		ConChangeSplitEntryCollection cols  = (ConChangeSplitEntryCollection)getUIContext().get("entryCollection");
        		for(Iterator it = cols.iterator();it.hasNext();){
            		cInfo =  (ConChangeSplitEntryInfo) it.next();
            		if(cInfo.getCostAccount() != null){
            			projectIds.add(cInfo.getCostAccount().getCurProject().getId().toString());
            			amountMap.put(cInfo.getCostAccount().getId().toString(),cInfo.getAmount());
            			accountIds.add(cInfo.getCostAccount().getId().toString());
            		}
            		
            	}
        	}else if(src.equals("settSplit")){
        		SettlementCostSplitEntryCollection cols = (SettlementCostSplitEntryCollection) getUIContext().get("entryCollection");
            	for(Iterator it = cols.iterator();it.hasNext();){
            		sInfo = (SettlementCostSplitEntryInfo) it.next();
            		if(sInfo.getCostAccount() != null){
            			projectIds.add(sInfo.getCostAccount().getCurProject().getId().toString());
            			amountMap.put(sInfo.getCostAccount().getId().toString(),sInfo.getAmount());
            			accountIds.add(sInfo.getCostAccount().getId().toString());
            		}
            		
            	}
        	}
        	
        }
        
        if(getUIContext().get("isAddThis") != null){
        	isAddThis = Boolean.valueOf(getUIContext().get("isAddThis").toString().trim()).booleanValue();
        }
        if(projectIds.size()==0 || accountIds.size() == 0){
        	return;
        }
        initTable();
        fetchData(projectIds,accountIds);
        fillTable();
    }
    
    public void fillTable(){
    	costInfoTable.removeRows();
    	costInfoTable.checkParsed();
    	CostAccountCollection costAccounts  = null;
    	if(retValue.get("CostAccountCollection") != null)
    		costAccounts = (CostAccountCollection)retValue.get("CostAccountCollection");
		if(costAccounts==null)
			return;
		int maxLevel = retValue.getInt("maxLevel");//
		
		RetValue costValues = (RetValue)retValue.get("costValues");
		RetValue settEntryValues = (RetValue)retValue.get("settEntryValues");
		RetValue areaValue = (RetValue)retValue.get("areaValue");
		
		BigDecimal sellAreaValue = FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType));
		this.sellArea = sellAreaValue;
		logger.info("可售单方："+sellAreaValue);
		BigDecimal buildAreaValue= FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType));
		this.buildArea = buildAreaValue;
		logger.info("建筑单方:"+buildAreaValue);
		this.costInfoTable.getTreeColumn().setDepth(maxLevel);
		IRow row  = null;
    	for(Iterator it = costAccounts.iterator();it.hasNext();){//TODO 科目循环
    		CostAccountInfo costAccountInfo = (CostAccountInfo) it.next();//
    		row = costInfoTable.addRow();
    		row.getStyleAttributes().setLocked(true);
			row.setTreeLevel(costAccountInfo.getLevel() - 1);
			String longNumber = costAccountInfo.getLongNumber();
			row.getCell("costAcctNumber").setValue(longNumber.replace('!', '.'));
			row.getCell("costAcctName").setValue(costAccountInfo.getName());
			if(costAccountInfo.isIsLeaf()){
				row.setUserObject(costAccountInfo);
				RetValue costValue = (RetValue)costValues.get(longNumber);
				if(costValue==null)
					continue;
				RetValue settEntryValue = (RetValue)settEntryValues.get(longNumber);
				
				//未结算合同 - 签约合同金额
				BigDecimal unSettSignAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("unSettSignAmt"));
				//已结算合同 - 签约合同金额
				BigDecimal settSignAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("settSignAmt"));
				BigDecimal unSettConTotalAmt = unSettSignAmt;
				BigDecimal settConTotalAmt = settSignAmt;
				//各变更类型对应的金额
				for (int i = 0; i < changeTypes.size()&&settEntryValue!=null; i++) {
					ChangeTypeInfo change = changeTypes.get(i);
					String cellKey  = change.getId().toString()+NO_SETTLE;
					String valueKey = change.getId().toString()+"unSettleAmt";
					BigDecimal changeAmount = FDCHelper.toBigDecimal(settEntryValue.getBigDecimal(valueKey));
					if(changeAmount.compareTo(FDCHelper.ZERO)!=0)
//						row.getCell(cellKey).setValue(changeAmount);
					unSettConTotalAmt = unSettConTotalAmt.add(changeAmount);
					
					cellKey  = change.getId().toString()+SETTLE;
					valueKey = change.getId().toString()+"settleAmt";
					
					changeAmount = FDCHelper.toBigDecimal(settEntryValue.getBigDecimal(valueKey));
					if(changeAmount.compareTo(FDCHelper.ZERO)!=0)
//						row.getCell(cellKey).setValue(changeAmount);
					settConTotalAmt = settConTotalAmt.add(changeAmount);
				}
				//结算调整
				BigDecimal settAdjustAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("settAdjustAmt"));
				settConTotalAmt = settConTotalAmt.add(settAdjustAmt);
				
				//非合同性成本 --无文本合同
				BigDecimal  unContractCostAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("unContractCostAmt"));
				
				//目前已发生 = 未结算合同小计 + 已结算合同小计 + 非合同性成本
				BigDecimal happenAmt = unSettConTotalAmt.add(settConTotalAmt).add(unContractCostAmt);
				if(happenAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("alreayHappend").setValue(happenAmt);
				
				//目标成本
				BigDecimal aimCost = FDCHelper.toBigDecimal(costValue.getBigDecimal("aimCost"));
				if(aimCost.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("aimCost").setValue(aimCost);
				
				BigDecimal diffAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("diffAmt"));
				
				//动态成本=目标成本＋差异
				BigDecimal dynCost = aimCost.add(diffAmt);
				if(dynCost.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("DyCost").setValue(dynCost);
				//TODO
				//可售单方
				
				BigDecimal amount = FDCHelper.ZERO;
				if(amountMap.get(costAccountInfo.getId().toString()) != null){
						amount = (BigDecimal) amountMap.get(costAccountInfo.getId().toString());
				}
				row.getCell("addCost").setValue(amount);
				
				if(sellAreaValue.compareTo(FDCHelper.ZERO)!=0&&dynCost.compareTo(FDCHelper.ZERO)!=0){
					BigDecimal sellPart = happenAmt.divide(sellAreaValue,2,BigDecimal.ROUND_HALF_UP);
					amount = FDCHelper.ZERO;
					if(amountMap.get(costAccountInfo.getId().toString()) != null){
						amount = (BigDecimal) amountMap.get(costAccountInfo.getId().toString());
					}
					
					BigDecimal endSellPart = FDCHelper.ZERO;
					if(isAddThis){
						endSellPart = FDCHelper.add(happenAmt,amount).divide(sellAreaValue,2,BigDecimal.ROUND_HALF_UP);
					}else{
						endSellPart = sellPart;
					}
					
					row.getCell("beforecanSaleBuild").setValue(sellPart);
					row.getCell("endCanSaleBuild").setValue(endSellPart);
				}
				//建筑单方
				if(buildAreaValue.compareTo(FDCHelper.ZERO)!=0&&dynCost.compareTo(FDCHelper.ZERO)!=0){
					BigDecimal buildPart = happenAmt.divide(buildArea,2,BigDecimal.ROUND_HALF_UP);
					row.getCell("beforecanSaleConstract").setValue(buildPart);
					amount = FDCHelper.ZERO;
					if(amountMap.get(costAccountInfo.getId().toString()) != null){
						amount = (BigDecimal) amountMap.get(costAccountInfo.getId().toString());
					}
					BigDecimal endbuildPart = FDCHelper.ZERO;
					if(isAddThis){
						endbuildPart = FDCHelper.add(happenAmt,amount).divide(buildArea,2,BigDecimal.ROUND_HALF_UP);
					}else{
						endbuildPart = buildPart;
					}
					row.getCell("endCanSaleConstract").setValue(endbuildPart);
				}
			}
			else{
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
			
			
    		
    		
    	}
    }
    
    private void  fetchData(Set prjIds,Set accountIds)
    {
    	TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
//    		paramValue.put("selectObjID", null);
		paramValue.put("leafPrjIDs", prjIds);
		paramValue.put("accountIds", accountIds);
		//kemu
//    		paramValue.put("selectObjIsPrj", Boolean.valueOf(true));
//    		retValue = ProjectCostRptFacadeFactory.getRemoteInstance().getDynCostInfo(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
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
     * output actionExit_actionPerformed
     */
    public void actionExit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

}