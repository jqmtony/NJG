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
	 * ��ͬ�Ƿ����
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
	 * �Ѵ�@��id�е�@ȥ�����õ��Ϸ�id����
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
	 * * @param isText �Ƿ��ͬ
	 * @throws Exception
	 * �ɱ����Ƶ���֤���
	 * 
	 * @modify �����ۼƼ�����Ŀ���ڣ��Ա�������ڵĿ�ĿΪά�ȣ�����ǰ����Ŀ��ͬ+��Ŀ���������󣺿�Ŀ����
	 * @author pengwei_hou 2011.11.05
	 */
	public static void checkFDCSplitBillData(FDCSplitBillInfo fdcSplitBillInfo, FDCSplitBillEntryCollection fdcSplitBillEntrys,
			boolean isText) throws Exception {
		try {
			String id = null;
			if(fdcSplitBillInfo.getId()!=null){
				id=fdcSplitBillInfo.getId().toString();
			}
			//��ø���Ŀ�ɱ����Ƶ����հ汾
			String curProjectId =  fdcSplitBillInfo.getCurProject().getId().toString();
			String contractID = isText ? fdcSplitBillInfo.getContractBill().getId().toString() : null;
			//����û
			boolean isSettle = isText ? isConSettle(contractID) : false;
			
			//����Ŀ����ϸ��Ŀ������ϸ���ϼ�ID
			Set acctIdSet = new HashSet();
			ICostAccount iCostAccount  = CostAccountFactory.getRemoteInstance();//�ɱ���ĿIcostAccount
			for(int j = 0 ,jSize =fdcSplitBillEntrys.size();j<jSize;j++){
				  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  fdcSplitBillEntrys.get(j);
				  if(!fDCSplitBillEntryInfo.isIsLeaf()){
					  continue;
				  }
				  CostAccountInfo acctInfo = fDCSplitBillEntryInfo.getCostAccount();
				  if(!acctInfo.isIsLeaf()){  //��ǰ�ɱ���Ŀ����Ҷ�ӽڵ�
					  CostAccountCollection costAccountCol  = getCostAccounts(acctInfo,iCostAccount);//
					  for(int k  = 0 , costSize  =  costAccountCol.size();k<costSize;k++){
						  acctIdSet.add(costAccountCol.get(k).getId().toString());
					  }
				  }else{
					  acctIdSet.add(acctInfo.getId().toString());
					  
				  }
				  
			}
			
//			DynCostCtrlBillInfo dynCostCtrlInfo   =FDCSplitBillUtil.getLatestVerDynCost(curProjectId);
			
			//��������Ŀ����ϸ��Ŀ������ϸ��Ŀ���¼� 
			DynCostCtrlBillEntryCollection dynCostCtrlEntrys  =FDCSplitBillUtil.getLatestVerDynCost(acctIdSet);
			
			if(dynCostCtrlEntrys!=null){
				//Ŀ��ɱ�Map��������Ŀ
				//				Map aimCostMap = AimCostHelper.getAimCostByAcct(null, acctIdSet);
				//modified by ken_liu...//�޸�bug���ɱ����Ƶ��Ĺ��ܣ���ĳ��һ���ɱ���Ŀ�����˿��Ʊ�������ʾ��ϢΪ������Ŀ����
				Map ctrlMap = new HashMap(); //���Ա���ĳ���ƹ��������� �ɱ���Ŀ---���, �Ա�õ��ù����¿��Ƶ� Ŀ��ɱ���ֵ
				
				boolean strictFlag = false;   // �ϸ���Ʊ����Ƿ񳬹�
				boolean alermFlag = false;    //��ʾ���Ʊ����Ƿ񳬹�
				StringBuffer strictMsg =new StringBuffer();  //�ϸ���Ʊ�����ʾ��Ϣ
				StringBuffer alermMsg =new StringBuffer();   //��ʾ���Ʊ�����ʾ��Ϣ
				for(int i = 0,size = dynCostCtrlEntrys.size();i<size;i++){
					  DynCostCtrlBillEntryInfo dynEntrys =   dynCostCtrlEntrys.get(i);
					  DynCostCtrlEntryItemsCollection  dynCostCtrlItemsCol =  dynEntrys.getItems();
					  ProductTypeInfo productType= dynEntrys.getProduct();
					  String productId  ="@";
					  if(productType!=null){
						  productId = productId+productType.getId().toString();
					  }
					  ctrlMap.clear();
					  ArrayList costAccountList = new ArrayList(); //�ɱ���ĿID �� ��ƷID ȷ�� ���� KEY
					  StringBuffer costMsg  = new StringBuffer();
					  Set idSet = new HashSet();
					  for(int j =  0,itemsSize = dynCostCtrlItemsCol.size();j<itemsSize;j++){//��÷�¼�ĳɱ���Ŀ����
						  DynCostCtrlEntryItemsInfo itemsInfos =  dynCostCtrlItemsCol.get(j);
						  CostAccountInfo costAccountInfo = itemsInfos.getCostAccount();
						  costMsg.append(costAccountInfo.getName()).append("��");
						  if(!costAccountInfo.isIsLeaf()){  //��ǰ�ɱ���Ŀ����Ҷ�ӽڵ�
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
					  
					  boolean isContains  = false; //�Ƿ����ܿط�Χ֮��
					  
					  BigDecimal amountSum = FDCHelper.ZERO;//�ܿسɱ���Ŀ��ֽ��֮��
					  //ȡ��Ŀ�Ѳ������
					  //ֻ���˱������ص����¼���Ŀ
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
					  //��Ŀ��ֵ��ĺ�ͬ�Ƿ����
					  boolean isOtherSettle = false;
					  String conId = null;
					  if(!isSettle){
						  //1.0����ǰ
						  //1.1�Ѳ�ֺ�ͬ
						  for(int j = 0 ,jSize =conSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSplitEntrys.get(j);
							  conInfo = ((ContractCostSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //��������ˣ��ۼӽ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.2�Ѳ�ֱ��
						  for(int j = 0 ,jSize =conChangeSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conChangeSplitEntrys.get(j);
							  conInfo = ((ConChangeSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //��������ˣ��ۼӽ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.3�Ѳ�ֽ���,��Ŀ����Ŀ��������ͬ�Ľ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.4 ���ı���ͬ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.5���β��
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
							if (costAccountList.contains(containsStr)) {//�ɱ���ֽ���ۼ�
								amountSum = amountSum.add(fDCSplitBillEntryInfo.getAmount());
								isContains = true;
							}
						}
						 
					  }else{
						  //1.0�����
						//1.1�Ѳ�ֺ�ͬ
						  for(int j = 0 ,jSize =conSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conSplitEntrys.get(j);
							  conInfo = ((ContractCostSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  if(isOtherSettle){
								  //��������ˣ��ۼӽ���
								  continue;
							  }
							  conId = conInfo.getId().toString();
							  //����ͬ�ѽ��㣬�ų���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.2�Ѳ�ֱ��
						  for(int j = 0 ,jSize =conChangeSplitEntrys.size();j<jSize;j++){
							  FDCSplitBillEntryInfo fDCSplitBillEntryInfo =  conChangeSplitEntrys.get(j);
							  conInfo= ((ConChangeSplitInfo)fDCSplitBillEntryInfo.get("parent")).getContractBill();
							  isOtherSettle = conInfo.isHasSettled();
							  conId = conInfo.getId().toString();
							  if(isOtherSettle){
								  //��������ˣ��ۼӽ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.1�Ѳ�ֽ���
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  //1.3���ν�����
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
							  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
								  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
								  isContains = true;
							  }
						  }
						  
						  //1.4 ���ı���ͬ���
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
								  if(costAccountList.contains(containsStr)){//�ɱ���ֽ���ۼ�
									  amountSum  =   amountSum.add(fDCSplitBillEntryInfo.getAmount());
									  isContains = true;
								  }
							  }
					  }
					  
					  
					  
					  
					  if(isContains){//����ڿ��Ʒ�Χ֮����Χ�ڳɱ���Ŀ��Ŀ��ɱ�֮��
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
						  
						 BigDecimal strictValue =  dynEntrys.getStrictValue();//�ϸ���Ʊ���
						 if(strictValue!=null){
							 BigDecimal strictSum =  FDCHelper.multiply(aimCostSum,FDCHelper.divide(strictValue,FDCHelper.ONE_HUNDRED));
							 if(strictSum.doubleValue() < amountSum.doubleValue()){
								 strictFlag = true;
							     // ������Ŀ�ۼ��ѷ����ɱ��������ڸÿ�ĿĿ��ɱ�����*���ѿ��Ʊ���=������ִ�д˲�����
								 strictMsg.append(costMsg).append("��Ŀ�ۼ��ѷ����ɱ��� ").append(amountSum.setScale(2,BigDecimal.ROUND_HALF_UP));
								 strictMsg.append(" ���ڸÿ�ĿĿ��ɱ���").append(aimCostSum.setScale(2));
								 strictMsg.append("*").append(strictValue).append("%").append("=").append(strictSum);
								 strictMsg.append("\n");
							 }
						 }
						 BigDecimal alermValue = dynEntrys.getAlermValue();//��ʾ���Ʊ���
						 if(alermValue!=null  &&!strictFlag){
							 BigDecimal alermSum = FDCHelper.multiply(aimCostSum,FDCHelper.divide(alermValue,FDCHelper.ONE_HUNDRED));
							 if(alermSum.doubleValue() < amountSum.doubleValue()){
								 alermFlag = true;
								 alermMsg.append(costMsg).append("��Ŀ�ۼ��ѷ����ɱ�: ").append(amountSum.setScale(2,BigDecimal.ROUND_HALF_UP));
								 alermMsg.append("���ڸÿ�ĿĿ��ɱ�: ").append(aimCostSum.setScale(2));
								 alermMsg.append("*").append(alermValue).append("%").append("=").append(alermSum);
								 alermMsg.append("\n");
							 }
						 }
					  }
				}
				if(strictFlag){
					FDCMsgBox.showDetailAndOK(null, "�����ɱ����Ƶ��ϸ���Ʊ���", strictMsg.toString(), 0);
					SysUtil.abort();
				}
				if(alermFlag){
					int i  = FDCMsgBox.showConfirm3a("�����ɱ����Ƶ���ʾ���Ʊ���\n�Ƿ����", alermMsg.toString());
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
	 * ���Ŀ��ɱ������еĳɱ����ݣ���������Ŀ��ɱ����Ƶ���������Ӧ����ʾ
	 * @param currentMeasureCostInfo ������Ŀ��ɱ�����
	 * @throws BOSException 
	 */
	public static void checkMeasureCostData(MeasureCostInfo currentMeasureCostInfo) throws BOSException {
	
		//   ȡ��Ŀ��ɱ�����ı���׶� 
		MeasureStageInfo currentMeasureStageInfo = currentMeasureCostInfo.getMeasureStage();
		
		boolean hasFoundCurrentMeasureStage = false; // �����в���׶μ����Ƿ��ҵ���ǰ����׶�
		MeasureStageInfo previousMeasureStateInfo = null; //ǰһ������׶�
		MeasureCostInfo previousMeasureCostInfo = null; //��ø���Ŀ��ǰһ���׶Σ����հ汾��Ŀ��ɱ����㵥;
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
			ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();// �ɱ���ĿIcostAccount
			AimCostCtrlBillEntryCollection aimCostEntryCol = aimCostCtrlInfo.getEntrys();
			if (aimCostEntryCol != null) {
				StringBuffer detailMessage = new StringBuffer(); //��ϸ��ʾ��Ϣ
				boolean higtFlag  =  false;//�Ƿ��ϸ����
				boolean lowFlag  =  false; //�Ƿ���ʾ����
				
				for (int i = 0, size = aimCostEntryCol.size(); i < size; i++) {// ���Ŀ��ɱ�������ϸ
					AimCostCtrlBillEntryInfo aimEntryInfo = aimCostEntryCol.get(i);
					//�鿴Ŀ��ɱ����Ƶ��Ƿ��֮ǰһ�����հ汾����aimCostCtrlInfo
					AimCostCtrlItemCollection stageItems = aimEntryInfo.getStageItems();
					AimCostCtrlItemInfo itemInfo =null;
					for(int j  = 0,jSize = stageItems.size();j<jSize;j++){
						AimCostCtrlItemInfo aimCostCtrlItemInfo = stageItems.get(j);
						if (aimCostCtrlItemInfo.getMeasuStage().getId().toString().equals(previousMeasureStateInfo.getId().toString())) {
							itemInfo  = aimCostCtrlItemInfo;
						}
					}
					float ctrlValue = itemInfo.getValue();//���Ʊ���
					if(itemInfo==null || ctrlValue  == 0 )break;//֮ǰ�汾���ܿ���
					

					//��¼�ĳɱ���Ŀ��¼
					AimCostCtrlCostActItemsCollection aimActItems = aimEntryInfo.getCostAccount();
					ProductTypeInfo productType = aimEntryInfo.getProduct(); // ��Ʒ
					String productId = "@";
					if (productType != null) {
						productId = productId + productType.getId().toString();
					}
					ArrayList costAccountList = new ArrayList(); // �ɱ���ĿID �� ��ƷID ȷ�� ���� KEY
					StringBuffer showMsgTemp =  new StringBuffer();
					String commaString = "��";
					for (int j = 0, jSize = aimActItems.size(); j < jSize; j++) { // ��¼�ĳɱ���Ŀ����
						AimCostCtrlCostActItemsInfo itemsInfos = aimActItems.get(j);
						CostAccountInfo costAccountInfo = itemsInfos.getCostAccount();
						showMsgTemp.append(costAccountInfo.getName()).append(commaString);
						if (!costAccountInfo.isIsLeaf()) { // ��ǰ�ɱ���Ŀ����Ҷ�ӽڵ�
							CostAccountCollection costAccountCol = getAllCostAccounts(costAccountInfo, iCostAccount);//
							for (int k = 0, costSize = costAccountCol.size(); k < costSize; k++) {
								costAccountList.add(costAccountCol.get(k).getId().toString() + productId);
							}
						} else {
							costAccountList.add(costAccountInfo.getId().toString() + productId);
						}
					}
					// ȥ�����һ������
					String costAccountNames = "";
					if (showMsgTemp.length() > 0) {
						costAccountNames = showMsgTemp.subSequence(0, showMsgTemp.lastIndexOf(commaString)).toString();
					}
					//Ŀ��ɱ�����ǰ�׶� 
					 BigDecimal beforeSum = FDCHelper.ZERO; //ǰһ���汾���Ʒ�Χ֮�� Ŀ��ɱ�֮��
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
						 if(costAccountList.contains(beforeProductId)){  //�ܿسɱ���Ŀ�ۺ�
							 beforeSum =  FDCHelper.add(beforeSum, measureEntryInfo.getAmount());
							 isContains = true;
						 }
					 }
					 
					// ������ܿط�Χ֮��
					if (isContains) {
						BigDecimal nowSum = FDCHelper.ZERO;  //��ǰ�׶�Ŀ���ܿط�Χ֮�ڳɱ���Ŀ֮��
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
						//����
						AimCostCtrlTypeEnum ctrlType = aimEntryInfo.getCtrlType();
						if (ctrlType != null) {
							BigDecimal sum = FDCHelper.multiply(beforeSum, FDCHelper.divide(new BigDecimal(ctrlValue), FDCHelper.ONE_HUNDRED));
							if (sum.doubleValue() < nowSum.doubleValue()) {
								//Ŀ��ɱ����ƽ�� ��1100*100%=1100��
								if(ctrlType.equals(AimCostCtrlTypeEnum.CONTROL_HIGH)){//�ϸ����
									higtFlag  = true;
								}else if(ctrlType.equals(AimCostCtrlTypeEnum.CONTROL_LOW )  && !higtFlag){//��ʾ����
									lowFlag  = true;
								}
								detailMessage.append(currentMeasureStageInfo.getName()).append(" ��Ŀ��").append(costAccountNames).append("��");
								detailMessage.append("Ŀ��ɱ�").append(nowSum.setScale(2));
								detailMessage.append("����").append(previousMeasureStateInfo.getName()).append("Ŀ��ɱ�");
								detailMessage.append(beforeSum.setScale(2)).append("*").append(ctrlValue).append("%").append("=").append(
										sum).append("");
								detailMessage.append("\n");
							}
						}
					}
				}
				
				if(higtFlag){
					FDCMsgBox.showDetailAndOK(null, "����Ŀ��ɱ��Ͽر���������ִ�д˲�����", detailMessage.toString(), 0);
					SysUtil.abort();
				}
				if(lowFlag){
					if (FDCMsgBox.YES != FDCMsgBox.showConfirm3a("����Ŀ��ɱ�������ʾ���Ʊ������Ƿ������", detailMessage.toString())) {
						SysUtil.abort();
					}					
				}
			}
		}
	}
	
	/**
	 * 
	 * ������ȡ����ǰĿ��ɱ���Ӧ�Ĺ�����Ŀ�µ�����Ŀ��ɱ�����������ĿID���Ƿ����°汾����
	 * @param currentMeasureCostInfo ��ǰĿ��ɱ�
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
	 *  ��ȡ�����õĲ���׶Σ���������׶Ρ���number��������
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
		return MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);//���в���׶�
	}

	/**
	 * ��ø���Ŀ�����°汾�� Ŀ��ɱ��ɱ����Ƶ�����������ĿID�����°汾����
	 * @param curProjectId ������ĿID
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
	 * ����Ŀ�����հ汾�ĳɱ����Ƶ�
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
	 * ����Ŀ�����հ汾�ĳɱ����Ƶ�
	 * @param idSet ��ĿID
	 * @return
	 * @throws BOSException
	 * @throws SQLException 
	 */
	public static  DynCostCtrlBillEntryCollection getLatestVerDynCost(Set idSet) throws BOSException, SQLException{
		if(idSet.size()==0){
			return new DynCostCtrlBillEntryCollection();
		}
		//��Ŀ����ĿID
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
		//������items.costAccount.id����������������֧��,��Ϊ�����Կ�Ŀ������Ŀ��ѯ
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
	 * ��õ�ǰ�ɱ���Ŀ�ڵ���������ϸ�ڵ�
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
	
	//ȡ��ǰ��Ч���
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
		//�������������ÿ�ʺ�ͬ�Ľ���ǰ�����
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
