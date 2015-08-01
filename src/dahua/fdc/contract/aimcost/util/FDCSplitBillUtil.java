//test_0912
package com.kingdee.eas.fdc.aimcost.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlItemCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlItemInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlTypeEnum;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillCollection;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsCollection;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.client.AimCostHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCSplitBillUtil {
	
	/**
	 * 合同是否结算
	 * @param id
	 * @throws BOSException 
	 */
	public static boolean isConSettle(String id) throws BOSException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FHasSettled from t_con_contractbill where fid=? ");
		builder.addParam(id);
		IRowSet rs = builder.executeQuery();
		boolean isConSettle = false;
		try {
			if(rs!=null&&rs.next())
				isConSettle= rs.getBoolean("FHasSettled");
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return isConSettle;
	}

	//added by ken_liu..
	/**
	 * 把带@的id中的@去掉，得到合法id集合
	 * @param list
	 * @return
	 */
	private static Set transFromCostAccountList(ArrayList list) {
		HashSet result = new HashSet();
		for (int i = 0; i < list.size(); i++) {
			String id = (String) list.get(i);
			// modified by zhaoqin for R130917-0299 on 2013/11/19 start
			// result.add(id.substring(0, id.length() - 1));
			result.add(id.substring(0, id.indexOf("@")));
			// modified by zhaoqin for R130917-0299 on 2013/11/19 end
		}
		if (result.size() == 0) {
			result.add("null");
		}
		return result;
	}
	
	/**
	 * @author jie_chen
	 * @param fdcSplitBillInfo
	 * @param fdcSplitBillEntrys
	 * * @param isText 是否合同
	 * @throws Exception
	 * 成本控制单验证金额
	 * 
	 * @modify 处理累计及跨项目分期，以本拆分所在的科目为维度，结算前：科目合同+科目变更，结算后：科目结算
	 * @author pengwei_hou 2011.11.05
	 */
	public static void checkFDCSplitBillData(FDCSplitBillInfo fdcSplitBillInfo, FDCSplitBillEntryCollection fdcSplitBillEntrys,
			boolean isText) throws Exception {
		try {
			String id = null;
			if(fdcSplitBillInfo.getId()!=null){
				id=fdcSplitBillInfo.getId().toString();
			}
			//获得该项目成本控制单最终版本
			String curProjectId =  fdcSplitBillInfo.getCurProject().getId().toString();
			String contractID = isText ? fdcSplitBillInfo.getContractBill().getId().toString() : null;
			//结算没
			boolean isSettle = isText ? isConSettle(contractID) : false;
			
			//跨项目：明细科目、及明细的上级ID
			Set acctIdSet = new HashSet();
			ICostAccount iCostAccount  = CostAccountFactory.getRemoteInstance();//成本科目IcostAccount
			for(int j = 0 ,jSize =fdcSplitBillEntrys.size();j<jSize;j++){
				  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  fdcSplitBillEntrys.get(j);
				  if(!fDCSplitBillEntryInfo.isIsLeaf()){
					  continue;
				  }
				  CostAccountInfo acctInfo = fDCSplitBillEntryInfo.getCostAccount();
				  if(!acctInfo.isIsLeaf()){  //当前成本科目不是叶子节点
					  CostAccountCollection costAccountCol  = getCostAccounts(acctInfo,iCostAccount);//
					  for(int k  = 0 , costSize  =  costAccountCol.size();k<costSize;k++){
						  acctIdSet.add(costAccountCol.get(k).getId().toString());
					  }
				  }else{
					  acctIdSet.add(acctInfo.getId().toString());
					  
				  }
				  
			}
			
//			DynCostCtrlBillInfo dynCostCtrlInfo   =FDCSplitBillUtil.getLatestVerDynCost(curProjectId);
			
			//包含跨项目的明细科目及非明细科目的下级 
			DynCostCtrlBillEntryCollection dynCostCtrlEntrys  =FDCSplitBillUtil.getLatestVerDynCost(acctIdSet);
			
			if(dynCostCtrlEntrys!=null){
				//目标成本Map包含跨项目
				//				Map aimCostMap = AimCostHelper.getAimCostByAcct(null, acctIdSet);
				//modified by ken_liu...//修改bug：成本控制单的功能，给某个一级成本科目设置了控制比例，提示信息为二级科目控制
				Map ctrlMap = new HashMap(); //用以保存某控制规则下所有 成本科目---金额, 以便得到该规则下控制的 目标成本总值
				
				boolean strictFlag = false;   // 严格控制比例是否超过
				boolean alermFlag = false;    //提示控制比例是否超过
				StringBuffer strictMsg =new StringBuffer();  //严格控制比例提示信息
				StringBuffer alermMsg =new StringBuffer();   //提示控制比例提示信息
				for(int i = 0,size = dynCostCtrlEntrys.size();i<size;i++){
					  DynCostCtrlBillEntryInfo dynEntrys =   dynCostCtrlEntrys.get(i);
					  DynCostCtrlEntryItemsCollection  dynCostCtrlItemsCol =  dynEntrys.getItems();
					  ProductTypeInfo productType= dynEntrys.getProduct();
					  String productId  ="@";
					  if(productType!=null){
						  productId = productId+productType.getId().toString();
					  }
					  ctrlMap.clear();
					  ArrayList costAccountList = new ArrayList(); //成本科目ID 和 产品ID 确定 集合 KEY
					  StringBuffer costMsg  = new StringBuffer();
					  Set idSet = new HashSet();
					  for(int j =  0,itemsSize = dynCostCtrlItemsCol.size();j<itemsSize;j++){//获得分录的成本科目集合
						  DynCostCtrlEntryItemsInfo itemsInfos =  dynCostCtrlItemsCol.get(j);
						  CostAccountInfo costAccountInfo = itemsInfos.getCostAccount();
						  costMsg.append(costAccountInfo.getName()).append("，");
						  if(!costAccountInfo.isIsLeaf()){  //当前成本科目不是叶子节点
							  CostAccountCollection costAccountCol  = getCostAccounts(costAccountInfo,iCostAccount);//
							  for(int k  = 0 , costSize  =  costAccountCol.size();k<costSize;k++){
								  costAccountList.add(costAccountCol.get(k).getId().toString()+productId);
							  }
						  }else{
							  costAccountList.add(costAccountInfo.getId().toString()+productId);
						  }
						  Map aimCostMap2 = AimCostHelper.getAimCostByAcct(null, transFromCostAccountList(costAccountList));
						  ctrlMap.putAll(aimCostMap2);
					  }
					  
					  boolean isContains  = false; //是否在受控范围之内
					  
					  BigDecimal amountSum = FDCHelper.ZERO;//受控成本科目拆分金额之和
					  //取科目已拆分数据
					  //只过滤本拆分相关的上下级科目
					  for(int j = 0 ,jSize =fdcSplitBillEntrys.size();j<jSize;j++){
						  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  fdcSplitBillEntrys.get(j);
						  if(!fDCSplitBillEntryInfo.isIsLeaf()){
							  continue;
						  }
						  String containsStr  ="@";
						  ProductTypeInfo entryproductType= 
							  fDCSplitBillEntryInfo.getProduct();
						  if(productType!=null && entryproductType!=null){
							  containsStr = containsStr+entryproductType.getId().toString();
						  }
						  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
						  if(costAccountList.contains(containsStr)){
							  idSet.add(fDCSplitBillEntryInfo.getCostAccount().getId().toString());
						  }
					  }
					  
					  FDCSplitBillEntryCollection conSplitEntrys = getSplitEntrys(CostSplitBillTypeEnum.CONTRACTSPLIT,id, idSet);
					  FDCSplitBillEntryCollection conChangeSplitEntrys = getSplitEntrys(CostSplitBillTypeEnum.CNTRCHANGESPLIT,id, idSet);
					  FDCSplitBillEntryCollection conSettleSplitEntrys = getSplitEntrys(CostSplitBillTypeEnum.SETTLEMENTSPLIT,id, idSet);
					  FDCSplitBillEntryCollection noPaymentSplitEntrys = getSplitEntrys(CostSplitBillTypeEnum.NOTEXTCONSPLIT, id, idSet);
					  ContractBillInfo conInfo = null;
					  //科目拆分到的合同是否结算
					  boolean isOtherSettle = false;
					  String conId = null;
					  if(!isSettle){
						  //1.0结算前
						  //1.1已拆分合同
						  for(int j = 0 ,jSize =conSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSplitEntrys.get(j);
							  conInfo = ((ContractCostSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //如果结算了，累加结算
								  continue;
							  }
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.2已拆分变更
						  for(int j = 0 ,jSize =conChangeSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conChangeSplitEntrys.get(j);
							  conInfo = ((ConChangeSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //如果结算了，累加结算
								  continue;
							  }
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.3已拆分结算,科目跨项目及其它合同的结算
						  for(int j = 0 ,jSize =conSettleSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSettleSplitEntrys.get(j);
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.4 无文本合同拆分
						for (int j = 0, jSize = noPaymentSplitEntrys.size(); j < jSize; j++) {
							FDCSplitBillEntryInfo fDCSplitBillEntryInfo = noPaymentSplitEntrys.get(j);
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.5本次拆分
						for (int j = 0, jSize = fdcSplitBillEntrys.size(); j < jSize; j++) {
							FDCSplitBillEntryInfo fDCSplitBillEntryInfo = fdcSplitBillEntrys.get(j);
							if (!fDCSplitBillEntryInfo.isIsLeaf()) {
								continue;
							}
							String containsStr = "@";
							ProductTypeInfo entryproductType = fDCSplitBillEntryInfo.getProduct();
							if (productType != null && entryproductType != null) {
								containsStr = containsStr + entryproductType.getId().toString();
							}
							containsStr = fDCSplitBillEntryInfo.getCostAccount().getId().toString() + containsStr;
							if (costAccountList.contains(containsStr)) {//成本拆分金额累加
								amountSum = amountSum.add(fDCSplitBillEntryInfo.getAmount());
								isContains = true;
							}
						}
						 
					  }else{
						  //1.0结算后
						//1.1已拆分合同
						  for(int j = 0 ,jSize =conSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSplitEntrys.get(j);
							  conInfo = ((ContractCostSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //如果结算了，累加结算
								  continue;
							  }
							  conId = conInfo.getId().toString();
							  //本合同已结算，排除掉
							  if(contractID.equals(conId)){
								  continue;
							  }
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.2已拆分变更
						  for(int j = 0 ,jSize =conChangeSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conChangeSplitEntrys.get(j);
							  conInfo= ((ConChangeSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  conId = conInfo.getId().toString();
							  if(isOtherSettle){
								  //如果结算了，累加结算
								  continue;
							  }
							  if(contractID.equals(conId)){
								  continue;
							  }
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.1已拆分结算
						  for(int j = 0 ,jSize =conSettleSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSettleSplitEntrys.get(j);
							  conInfo= ((SettlementCostSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  if(contractID.equals(conId)){
								  continue;
							  }
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.3本次结算拆分
						  for(int j = 0 ,jSize =fdcSplitBillEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  fdcSplitBillEntrys.get(j);
							  if(!fDCSplitBillEntryInfo.isIsLeaf()){
								  continue;
							  }
							  String containsStr  ="@";
							  ProductTypeInfo entryproductType= 
								  fDCSplitBillEntryInfo.getProduct();
							  if(productType!=null && entryproductType!=null){
								  containsStr = containsStr+entryproductType.getId().toString();
							  }
							  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
							  if(costAccountList.contains(containsStr)){//成本拆分金额累加
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  
						  //1.4 无文本合同拆分
							for (int j = 0, jSize = noPaymentSplitEntrys.size(); j < jSize; j++) {
								FDCSplitBillEntryInfo fDCSplitBillEntryInfo = noPaymentSplitEntrys.get(j);
								  if(!fDCSplitBillEntryInfo.isIsLeaf()){
									  continue;
								  }
								  String containsStr  ="@";
								  ProductTypeInfo entryproductType= 
									  fDCSplitBillEntryInfo.getProduct();
								  if(productType!=null && entryproductType!=null){
									  containsStr = containsStr+entryproductType.getId().toString();
								  }
								  containsStr=fDCSplitBillEntryInfo.getCostAccount().getId().toString()+containsStr;
								  if(costAccountList.contains(containsStr)){//成本拆分金额累加
									  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
									  isContains = true;
								  }
							  }
					  }
					  
					  
					  
					  
					  if(isContains){//如果在控制范围之内求范围内成本科目的目标成本之和
						  BigDecimal aimCostSum = FDCHelper.ZERO;
						  Iterator iter = ctrlMap.entrySet().iterator();
						  while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							aimCostSum = FDCHelper.add(aimCostSum, entry.getValue());
						}

						/*  for(int j  = 0 ,jSize =costAccountList.size();j<jSize;j++ ){
							  String costContains   = costAccountList.get(j).toString();
							  Object obj= aimCostMap.get(costContains.substring(0,costContains.indexOf('@')));
							  if(obj!=null){
								  aimCostSum = aimCostSum.add((BigDecimal)obj);
							  }
						  }*/
						  
						 BigDecimal strictValue =  dynEntrys.getStrictValue();//严格控制比例
						 if(strictValue!=null){
							 BigDecimal strictSum =  FDCHelper.multiply(aimCostSum,FDCHelper.divide(strictValue,FDCHelper.ONE_HUNDRED));
							 if(strictSum.doubleValue() < amountSum.doubleValue()){
								 strictFlag = true;
							     // ××科目累计已发生成本（金额）大于该科目目标成本（金额）*提醒控制比例=金额，不能执行此操作！
								 strictMsg.append(costMsg).append("科目累计已发生成本： ").append(amountSum.setScale(2,BigDecimal.ROUND_HALF_UP));
								 strictMsg.append(" 大于该科目目标成本：").append(aimCostSum.setScale(2));
								 strictMsg.append("*").append(strictValue).append("%").append("=").append(strictSum);
								 strictMsg.append("\n");
							 }
						 }
						 BigDecimal alermValue = dynEntrys.getAlermValue();//提示控制比例
						 if(alermValue!=null  &&!strictFlag){
							 BigDecimal alermSum = FDCHelper.multiply(aimCostSum,FDCHelper.divide(alermValue,FDCHelper.ONE_HUNDRED));
							 if(alermSum.doubleValue() < amountSum.doubleValue()){
								 alermFlag = true;
								 alermMsg.append(costMsg).append("科目累计已发生成本: ").append(amountSum.setScale(2,BigDecimal.ROUND_HALF_UP));
								 alermMsg.append("大于该科目目标成本: ").append(aimCostSum.setScale(2));
								 alermMsg.append("*").append(alermValue).append("%").append("=").append(alermSum);
								 alermMsg.append("\n");
							 }
						 }
					  }
				}
				if(strictFlag){
					FDCMsgBox.showDetailAndOK(null, "超出成本控制单严格控制比例", strictMsg.toString(), 0);
					SysUtil.abort();
				}
				if(alermFlag){
					int i  = FDCMsgBox.showConfirm3a("超出成本控制单提示控制比例\n是否继续", alermMsg.toString());
					if(i!=0){
						SysUtil.abort();
					}
					
				}
			}
		} catch (BOSException e) {
			throw new BOSException(e);
		}
	}

	/**
	 * 检查目标成本测算中的成本数据，若超过了目标成本控制单，给出相应的提示
	 * @param currentMeasureCostInfo 待检查的目标成本测算
	 * @throws BOSException 
	 */
	public static void checkMeasureCostData(MeasureCostInfo currentMeasureCostInfo) throws BOSException {
	
		//   取得目标成本测算的保存阶段 
		MeasureStageInfo currentMeasureStageInfo = currentMeasureCostInfo.getMeasureStage();
		
		boolean hasFoundCurrentMeasureStage = false; // 在所有测算阶段集中是否找到当前测算阶段
		MeasureStageInfo previousMeasureStateInfo = null; //前一个测算阶段
		MeasureCostInfo previousMeasureCostInfo = null; //获得该项目下前一个阶段，最终版本的目标成本测算单;
		MeasureStageCollection measureStageCol = getEnabledMeasureStageCollection();
		for (int i = 0, size = measureStageCol.size(); i < size; i++) {
			MeasureStageInfo stageInfo = measureStageCol.get(i);
			if (previousMeasureCostInfo != null) {
				break;
			}
			if (hasFoundCurrentMeasureStage) {
				MeasureCostCollection measureCostCollection = getAllMeasureCostInfo(currentMeasureCostInfo);
				for (int j = 0, jSize = measureCostCollection.size(); j < jSize; j++) {
					if (stageInfo.getId().toString().equals(measureCostCollection.get(j).getMeasureStage().getId().toString())) {
						previousMeasureStateInfo = stageInfo;
						previousMeasureCostInfo = measureCostCollection.get(j);
						break;
					}
				}
			}
			if (stageInfo.getId().toString().equals(currentMeasureStageInfo.getId().toString())) {
				hasFoundCurrentMeasureStage = true;
			}
		}
		
		AimCostCtrlBillInfo aimCostCtrlInfo = getLaststVerAimCost(currentMeasureCostInfo.getProject().getId().toString());
		if (previousMeasureCostInfo != null && aimCostCtrlInfo != null) {  
			ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();// 成本科目IcostAccount
			AimCostCtrlBillEntryCollection aimCostEntryCol = aimCostCtrlInfo.getEntrys();
			if (aimCostEntryCol != null) {
				StringBuffer detailMessage = new StringBuffer(); //详细提示信息
				boolean higtFlag  =  false;//是否严格控制
				boolean lowFlag  =  false; //是否提示控制
				
				for (int i = 0, size = aimCostEntryCol.size(); i < size; i++) {// 获得目标成本测算明细
					AimCostCtrlBillEntryInfo aimEntryInfo = aimCostEntryCol.get(i);
					//查看目标成本控制单是否对之前一个最终版本控制aimCostCtrlInfo
					AimCostCtrlItemCollection stageItems = aimEntryInfo.getStageItems();
					AimCostCtrlItemInfo itemInfo =null;
					for(int j  = 0,jSize = stageItems.size();j<jSize;j++){
						AimCostCtrlItemInfo aimCostCtrlItemInfo = stageItems.get(j);
						if (aimCostCtrlItemInfo.getMeasuStage().getId().toString().equals(previousMeasureStateInfo.getId().toString())) {
							itemInfo  = aimCostCtrlItemInfo;
						}
					}
					float ctrlValue = itemInfo.getValue();//控制比例
					if(itemInfo==null || ctrlValue  == 0 )break;//之前版本不受控制
					

					//分录的成本科目分录
					AimCostCtrlCostActItemsCollection aimActItems = aimEntryInfo.getCostAccount();
					ProductTypeInfo productType = aimEntryInfo.getProduct(); // 产品
					String productId = "@";
					if (productType != null) {
						productId = productId + productType.getId().toString();
					}
					ArrayList costAccountList = new ArrayList(); // 成本科目ID 和 产品ID 确定 集合 KEY
					StringBuffer showMsgTemp =  new StringBuffer();
					String commaString = "，";
					for (int j = 0, jSize = aimActItems.size(); j < jSize; j++) { // 分录的成本科目集合
						AimCostCtrlCostActItemsInfo itemsInfos = aimActItems.get(j);
						CostAccountInfo costAccountInfo = itemsInfos.getCostAccount();
						showMsgTemp.append(costAccountInfo.getName()).append(commaString);
						if (!costAccountInfo.isIsLeaf()) { // 当前成本科目不是叶子节点
							CostAccountCollection costAccountCol = getAllCostAccounts(costAccountInfo, iCostAccount);//
							for (int k = 0, costSize = costAccountCol.size(); k < costSize; k++) {
								costAccountList.add(costAccountCol.get(k).getId().toString() + productId);
							}
						} else {
							costAccountList.add(costAccountInfo.getId().toString() + productId);
						}
					}
					// 去掉最后一个逗号
					String costAccountNames = "";
					if (showMsgTemp.length() > 0) {
						costAccountNames = showMsgTemp.subSequence(0, showMsgTemp.lastIndexOf(commaString)).toString();
					}
					//目标成本测算前阶段 
					 BigDecimal beforeSum = FDCHelper.ZERO; //前一个版本控制范围之内 目标成本之和
					 boolean isContains  = false; 
					
					 MeasureEntryCollection costEntry = previousMeasureCostInfo.getCostEntry();
					 for(int j  = 0 , jSize = costEntry.size();j<jSize;j++){
						 MeasureEntryInfo measureEntryInfo = costEntry.get(j);
						 ProductTypeInfo product = measureEntryInfo.getProduct();
						 String beforeProductId = "@";
						 if(productType!=null && product!=null){
							 beforeProductId  = beforeProductId + product.getId().toString();
						 }
						 beforeProductId=measureEntryInfo.getCostAccount().getId().toString()+beforeProductId;
						 if(costAccountList.contains(beforeProductId)){  //受控成本科目综合
							 beforeSum =  FDCHelper.add(beforeSum, measureEntryInfo.getAmount());
							 isContains = true;
						 }
					 }
					 
					// 如果在受控范围之内
					if (isContains) {
						BigDecimal nowSum = FDCHelper.ZERO;  //当前阶段目标受控范围之内成本科目之和
						MeasureEntryCollection costEntry2 = currentMeasureCostInfo.getCostEntry();
						for (int j = 0, jSize = costEntry2.size(); j < jSize; j++) {
							MeasureEntryInfo measureEntryInfo = costEntry2.get(j);
							ProductTypeInfo product = measureEntryInfo.getProduct();
							String nowProductId = "@";
							if (productType !=null && product != null) {
								nowProductId = nowProductId + product.getId().toString();
							}
							nowProductId=measureEntryInfo.getCostAccount().getId().toString()+nowProductId;
							if (costAccountList.contains(nowProductId)) { 
								nowSum = FDCHelper.add(nowSum, measureEntryInfo.getAmount());
							}
						}
						//控制
						AimCostCtrlTypeEnum ctrlType = aimEntryInfo.getCtrlType();
						if (ctrlType != null) {
							BigDecimal sum = FDCHelper.multiply(beforeSum, FDCHelper.divide(new BigDecimal(ctrlValue), FDCHelper.ONE_HUNDRED));
							if (sum.doubleValue() < nowSum.doubleValue()) {
								//目标成本控制金额 （1100*100%=1100）
								if(ctrlType.equals(AimCostCtrlTypeEnum.CONTROL_HIGH)){//严格控制
									higtFlag  = true;
								}else if(ctrlType.equals(AimCostCtrlTypeEnum.CONTROL_LOW )  && !higtFlag){//提示控制
									lowFlag  = true;
								}
								detailMessage.append(currentMeasureStageInfo.getName()).append(" 科目【").append(costAccountNames).append("】");
								detailMessage.append("目标成本").append(nowSum.setScale(2));
								detailMessage.append("大于").append(previousMeasureStateInfo.getName()).append("目标成本");
								detailMessage.append(beforeSum.setScale(2)).append("*").append(ctrlValue).append("%").append("=").append(
										sum).append("");
								detailMessage.append("\n");
							}
						}
					}
				}
				
				if(higtFlag){
					FDCMsgBox.showDetailAndOK(null, "超出目标成本严控比例，不能执行此操作！", detailMessage.toString(), 0);
					SysUtil.abort();
				}
				if(lowFlag){
					if (FDCMsgBox.YES != FDCMsgBox.showConfirm3a("超出目标成本控制提示控制比例，是否继续？", detailMessage.toString())) {
						SysUtil.abort();
					}					
				}
			}
		}
	}
	
	/**
	 * 
	 * 描述：取出当前目标成本对应的工程项目下的所有目标成本，按工程项目ID和是否最新版本过滤
	 * @param currentMeasureCostInfo 当前目标成本
	 */
	private static MeasureCostCollection getAllMeasureCostInfo(MeasureCostInfo currentMeasureCostInfo) throws BOSException {
		FilterInfo measureCostFilter = new FilterInfo();
		measureCostFilter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE,CompareType.EQUALS));
		measureCostFilter.getFilterItems().add(
				new FilterItemInfo("project.id", currentMeasureCostInfo.getProject().getId().toString(), CompareType.EQUALS));
		SelectorItemCollection measureCostSel = new SelectorItemCollection();
		measureCostSel.add(new SelectorItemInfo("id"));
		measureCostSel.add(new SelectorItemInfo("measureStage.id"));
		measureCostSel.add(new SelectorItemInfo("measureStage.name"));
		measureCostSel.add(new SelectorItemInfo("costEntry.amount"));
		measureCostSel.add(new SelectorItemInfo("costEntry.product.id"));
		measureCostSel.add(new SelectorItemInfo("costEntry.costAccount.id"));
		measureCostSel.add(new SelectorItemInfo("costEntry.costAccount.name"));
		measureCostSel.add(new SelectorItemInfo("costEntry.costAccount.isLeaf"));
		measureCostSel.add(new SelectorItemInfo("costEntry.costAccount.longNumber"));
		EntityViewInfo measureView =  new EntityViewInfo();
		measureView.setSelector(measureCostSel);
		measureView.setFilter(measureCostFilter);
		MeasureCostCollection measureCostCollection = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(measureView);
		return measureCostCollection;
	}

	/**
	 *  获取已启用的测算阶段，按“测算阶段”的number降序排序
	 */
	private static MeasureStageCollection getEnabledMeasureStageCollection() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
		SorterItemCollection coll = new SorterItemCollection();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("id"));
		sel.add(new SelectorItemInfo("name"));
		SorterItemInfo sortInfo = new SorterItemInfo("number");
		sortInfo.setSortType(SortType.DESCEND);
		coll.add(sortInfo);
		view.setSelector(sel);
		view.setSorter(coll);
		view.setFilter(filter);
		return MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);//所有测算阶段
	}

	/**
	 * 获得改项目的最新版本的 目标成本成本控制单，按工程项目ID和最新版本过滤
	 * @param curProjectId 工程项目ID
	 */
	public static AimCostCtrlBillInfo getLaststVerAimCost(String curProjectId) throws BOSException{
		FilterInfo  filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE,CompareType.EQUALS));
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("entrys.ctrlType"));
		sic.add(new SelectorItemInfo("entrys.product.id"));
		sic.add(new SelectorItemInfo("entrys.product.isEnabled"));
		sic.add(new SelectorItemInfo("entrys.product.name"));
		sic.add(new SelectorItemInfo("entrys.product.number"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.id"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.name"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.isLeaf"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.longNumber"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.curProject.id"));
		sic.add(new SelectorItemInfo("entrys.stageItems.value"));
		sic.add(new SelectorItemInfo("entrys.stageItems.measuStage.id"));
		sic.add(new SelectorItemInfo("entrys.stageItems.measuStage.isEnabled"));
		sic.add(new SelectorItemInfo("entrys.stageItems.measuStage.name"));
		sic.add(new SelectorItemInfo("entrys.stageItems.measuStage.number"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sic);
		view.setFilter(filter);
		AimCostCtrlBillCollection aimCostCtrlCol = AimCostCtrlBillFactory.getRemoteInstance().getAimCostCtrlBillCollection(view);
		if(aimCostCtrlCol!=null && aimCostCtrlCol.size()>0){
			return aimCostCtrlCol.get(0);
		}
		return null;
	}
	
	/**
	 * @author jie_chen
	 * 该项目有最终版本的成本控制单
	 * @param curProjectId
	 * @return
	 * @throws BOSException
	 */
	public static  DynCostCtrlBillInfo getLatestVerDynCost(String curProjectId) throws BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer",Boolean.TRUE,CompareType.EQUALS));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("id"));
		sel.add(new SelectorItemInfo("entrys.id"));
		sel.add(new SelectorItemInfo("entrys.strictValue"));
		sel.add(new SelectorItemInfo("entrys.alermValue"));
		sel.add(new SelectorItemInfo("entrys.product.id"));
		sel.add(new SelectorItemInfo("entrys.items.id"));
		sel.add(new SelectorItemInfo("entrys.items.costAccount.id"));
		sel.add(new SelectorItemInfo("entrys.items.costAccount.name"));
		sel.add(new SelectorItemInfo("entrys.items.costAccount.isLeaf"));
		sel.add(new SelectorItemInfo("entrys.items.costAccount.longNumber"));
		sel.add(new SelectorItemInfo("entrys.items.costAccount.curProject.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		SorterItemInfo sort = new SorterItemInfo("entrys.items.costAccount.longNumber");
		sort.setSortType(SortType.DESCEND);
		view.getSorter().add(sort);
		DynCostCtrlBillCollection   dynCostCtrlCol =  DynCostCtrlBillFactory.getRemoteInstance().getDynCostCtrlBillCollection(view);
		if(dynCostCtrlCol!=null  && dynCostCtrlCol.size()>0){
			return dynCostCtrlCol.get(0);
		}
		return null;
	}
	
	
	/**
	 * @author pengwei_hou
	 * 该项目有最终版本的成本控制单
	 * @param idSet 科目ID
	 * @return
	 * @throws BOSException
	 * @throws SQLException 
	 */
	public static  DynCostCtrlBillEntryCollection getLatestVerDynCost(Set idSet) throws BOSException, SQLException{
		if(idSet.size()==0){
			return new DynCostCtrlBillEntryCollection();
		}
		//科目的项目ID
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select distinct(fcurproject) from t_fdc_costaccount where ");
		builder.appendParam("fid", idSet.toArray());
		IRowSet rs = builder.executeQuery();
		Set prjIds = new HashSet();
		while(rs.next()){
			prjIds.add(rs.getString("fcurproject"));
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer",Boolean.TRUE,CompareType.EQUALS));
		//这里以items.costAccount.id是三层向下条件不支持,改为向上以科目工程项目查询
		filter.getFilterItems().add(new FilterItemInfo("parent.curProject.id",prjIds,CompareType.INCLUDE));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("parent.id"));
		sel.add(new SelectorItemInfo("id"));
		sel.add(new SelectorItemInfo("strictValue"));
		sel.add(new SelectorItemInfo("alermValue"));
		sel.add(new SelectorItemInfo("product.id"));
		sel.add(new SelectorItemInfo("items.id"));
		sel.add(new SelectorItemInfo("items.costAccount.id"));
		sel.add(new SelectorItemInfo("items.costAccount.name"));
		sel.add(new SelectorItemInfo("items.costAccount.isLeaf"));
		sel.add(new SelectorItemInfo("items.costAccount.longNumber"));
		sel.add(new SelectorItemInfo("items.costAccount.curProject.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		SorterItemInfo sort = new SorterItemInfo("items.costAccount.longNumber");
		sort.setSortType(SortType.DESCEND);
		view.getSorter().add(sort);
		DynCostCtrlBillEntryCollection   dynCostCtrlColEntrys =  DynCostCtrlBillEntryFactory.getRemoteInstance().getDynCostCtrlBillEntryCollection(view);
		if(dynCostCtrlColEntrys!=null  && dynCostCtrlColEntrys.size()>0){
			return dynCostCtrlColEntrys;
		}
		return new DynCostCtrlBillEntryCollection();
	}
	
	
	
	/**
	 * @author jie_chen
	 * 获得当前成本科目节点下所有明细节点
	 */
	public static CostAccountCollection getCostAccounts(CostAccountInfo costAccountInfo,ICostAccount iCostAccount ) throws BOSException{
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("id"));
		FilterInfo costAccountfilter = new FilterInfo();
		costAccountfilter.getFilterItems().add(new FilterItemInfo("curProject.id",costAccountInfo.getCurProject().getId().toString(),CompareType.EQUALS));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE,CompareType.EQUALS));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("longNumber",costAccountInfo.getLongNumber()+"%",CompareType.LIKE));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE,CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(costAccountfilter);
		return iCostAccount.getCostAccountCollection(view);
	}
	
	
	public static CostAccountCollection getAllCostAccounts(CostAccountInfo costAccountInfo,ICostAccount iCostAccount ) throws BOSException{
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("id"));
		sel.add(new SelectorItemInfo("longNumber"));
		FilterInfo costAccountfilter = new FilterInfo();
		costAccountfilter.getFilterItems().add(new FilterItemInfo("curProject.id",costAccountInfo.getCurProject().getId().toString(),CompareType.EQUALS));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("longNumber",costAccountInfo.getLongNumber()+"%",CompareType.LIKE));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE,CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(costAccountfilter);
		return iCostAccount.getCostAccountCollection(view);
	}
	
	//取当前有效拆分
	public static FDCSplitBillEntryCollection getSplitEntrys(CostSplitBillTypeEnum type,String id,Set idSet) throws BOSException{
		if(idSet.size()==0){
			return new FDCSplitBillEntryCollection();
		}
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("id"));
		sel.add(new SelectorItemInfo("amount"));
		sel.add(new SelectorItemInfo("isLeaf"));
		sel.add(new SelectorItemInfo("productType.id"));
		sel.add(new SelectorItemInfo("costAccount.id"));
		sel.add(new SelectorItemInfo("costAccount.name"));
		sel.add(new SelectorItemInfo("costAccount.longNumber"));
		sel.add(new SelectorItemInfo("costAccount.curProject.id"));
		sel.add(new SelectorItemInfo("parent.contractBill.id"));
		//分两种情况处理：每笔合同的结算前结算后
		sel.add(new SelectorItemInfo("parent.contractBill.hasSettled"));
		FilterInfo costAccountfilter = new FilterInfo();
		costAccountfilter.getFilterItems().add(new FilterItemInfo("costAccount.id",idSet,CompareType.INCLUDE));
		costAccountfilter.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.FALSE));
		if(id!=null){
			costAccountfilter.getFilterItems().add(new FilterItemInfo("parent.id",id,CompareType.NOTEQUALS));	
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(costAccountfilter);
		
		if(CostSplitBillTypeEnum.CONTRACTSPLIT.equals(type)){
			return ContractCostSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
		}else if(CostSplitBillTypeEnum.CNTRCHANGESPLIT.equals(type)){
			return ConChangeSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
		}else if(CostSplitBillTypeEnum.SETTLEMENTSPLIT.equals(type)){
			return SettlementCostSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
		} else if (CostSplitBillTypeEnum.NOTEXTCONSPLIT.equals(type)) {
			view.getFilter().getFilterItems().add(new FilterItemInfo("parent.isConWithoutText", Boolean.TRUE));
			return PaymentSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
		}
		
		return new FDCSplitBillEntryCollection();
	}
}
