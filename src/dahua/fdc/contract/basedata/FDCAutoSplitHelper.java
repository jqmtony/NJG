package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.finance.AutoSplitSortor;

public class FDCAutoSplitHelper implements Comparator {
	private static final Logger logger = CoreUIObject.getLogger(FDCAutoSplitHelper.class);
    private Context ctx=null;
    
    public FDCAutoSplitHelper(Context ctx){
    	this.ctx=ctx;
    }
    
    private Collator comparator = Collator.getInstance(Locale.getDefault());
	private static AutoSplitSortor sortComparator=null;
	private static Comparator getSortComparator(){
		if(sortComparator==null){
			sortComparator=new AutoSplitSortor();
		}
		return sortComparator;
	}
	public int compare(Object o1, Object o2) {
		if(o1!=null&&o2!=null&&o1 instanceof Integer&&o2 instanceof Integer){
			return compareAgain((Integer)o1, (Integer)o2);
		}
		return 0;
	}
	private int compareAgain(Integer o1,Integer o2) {
		if(o1.intValue()>o2.intValue())   
			return   1;   
		else   
			return   0;   
	}
	
	//����Ʒ����Ŀ��ɱ�
	private static Map getAIMAimCostData(Map param) throws BOSException, SQLException {
		Set acctIds = (Set)param.get("acctIds");
		Map keys = (Map)param.get("keys");
		Map retMap = new HashMap();
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		aimView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", acctIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("head.isLastVersion", new Integer(1)));
		aimView.getSelector().add("costAmount");
		aimView.getSelector().add("costAccount.id");
		aimView.getSelector().add("costAccount.type");
		aimView.getSelector().add("product.id");

		CostEntryCollection costEntrys = null;
		costEntrys = CostEntryFactory.getRemoteInstance().getCostEntryCollection(aimView);

		if (costEntrys.size() > 0) {
			Map entryMap = new HashMap();
			//Ŀ��ɱ����ݣ���̯��Ŀ����Ʒ��֣�
			for (int i = 0; i < costEntrys.size(); i++) {
				CostEntryInfo entry = costEntrys.get(i);
				CostAccountInfo costAccount = entry.getCostAccount();
				if (costAccount == null || costAccount.getId() == null) {
					continue;
				}
				BigDecimal value = entry.getCostAmount();
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				String key = costAccount.getId().toString();
				//��Ŀ���
				if(acctIds.contains(key)){
					retMap.put(key, FDCHelper.add(retMap.get(key), value));
				}
				if(CostAccountTypeEnum.SIX.equals(entry.getCostAccount().getType())){
					entryMap.put(entry.getId().toString(), costAccount.getId().toString());
				}else{
					if(entry.getProduct()!=null){
						key = key + entry.getProduct().getId().toString();
					}
					if(keys.containsKey(key)){
						//��Ʒ���
						retMap.put(key, FDCHelper.add(retMap.get(key), value));
					}
				}				
			}
			// ��ƷĿ��ɱ�
			if (entryMap.size() > 0) {
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().clear();
				view.getSelector().add("costEntryId");
				view.getSelector().add("product.id");
				view.getSelector().add("splitAmount");
				filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("costEntryId", new HashSet(entryMap.keySet()), CompareType.INCLUDE));
//				filter.getFilterItems().add(new FilterItemInfo("apportionType.id", ApportionTypeInfo.aimCostType));
				AimCostProductSplitEntryCollection splits = null;
				splits = AimCostProductSplitEntryFactory.getRemoteInstance().getAimCostProductSplitEntryCollection(view);

				for (int i = 0; i < splits.size(); i++) {
					AimCostProductSplitEntryInfo info = splits.get(i);
					String acctId = (String)entryMap.get(info.getCostEntryId());
					String key = acctId;
					if(info.getProduct()!=null){
						key = key + info.getProduct().getId().toString();
					}
					if (keys.containsKey(key)) {
						retMap.put(key, FDCHelper.add(retMap.get(key),info.getSplitAmount()));
					} 
				}
			}
		}
		return retMap;
	}
	
    /**
     * ������δ¼������ɱ�ʱ����Ŀ��ɱ��Զ����
     * @throws BOSException 
     * @throws EASBizException 
     * 
     */
    public  static FDCSplitBillEntryCollection splitByAimCostSplitScale(FDCSplitBillEntryCollection entrys,Map dataMap) throws Exception{
    	
    	Set acctIds = new HashSet();
    	Map keys = new HashMap();
    	//��Ҫ��ֵ��ܽ��
    	
		for(int i=0; i<entrys.size(); i++){
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) entrys.get(i);
			if(entry.isIsLeaf()){
				if(entry!=null&&entry.getCostAccount()!=null&&entry.getCostAccount().getId()!=null){
					String key = entry.getCostAccount().getId().toString();
					acctIds.add(key);
					if(entry.getProduct()!=null){
						key = key+entry.getProduct().getId().toString();
						keys.put(key, Boolean.TRUE);
					}else{
						keys.put(key, Boolean.FALSE);
					}
				}
			}
		}
		if(acctIds.size()==0){
			return new FDCSplitBillEntryCollection();
		}
		Map param = new HashMap();
		param.put("acctIds", acctIds);
		param.put("keys", keys);
		Map aimCostMap = getAIMAimCostData(param);
		/**
		 * �磺���β��ѡ�гɱ���ĿA�Ķ����Ͳ㡢�ɱ���ĿB�� �ɱ���ĿA��Ŀ��ɱ������������и߲�80�򡢶��70�򡢵Ͳ�50�򣩡�
		 * �ɱ���ĿB��Ŀ��ɱ��������򡣳ɱ���ĿA�Ĳ�ֱ���%Ϊ��70+50��/��70+50+300��*100= 28.57 ��
		 * �ɱ���ĿB�Ĳ�ֱ���%Ϊ300/��50+70+300�� *100= 71.42 
		 */
		setUnionAmount(entrys,dataMap,aimCostMap);
    	
    	return entrys;
	}
	private static void setUnionAmount(FDCSplitBillEntryCollection entrys, Map dataMap,Map aimCostMap) {
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal totalAimCost = FDCHelper.ZERO;
		BigDecimal totalAmount = FDCHelper.toBigDecimal(dataMap.get("totalAmount"));
		for(int i=0;i<entrys.size();i++){
    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)entrys.get(i);
    			BigDecimal amt = FDCHelper.ZERO;
    				FDCSplitBillEntryInfo subEntry = entry;
    				BigDecimal splitAmt = FDCHelper.ZERO;
    				String costAccountID = subEntry.getCostAccount().getId().toString();
    				if(subEntry.isIsLeaf()){
    					
    					if(subEntry.getProduct()!=null){
    						String prodId = subEntry.getProduct().getId().toString();
    						//��Ʒ��Ŀ��ɱ�ָ��
    						if(aimCostMap.containsKey(costAccountID+prodId)){
    							splitAmt = (BigDecimal)aimCostMap.get(costAccountID+prodId);
    						}
    					}else{
    						if(aimCostMap.containsKey(costAccountID)){
    							splitAmt = (BigDecimal)aimCostMap.get(costAccountID);
    						}
    					}
    				}
    				amt = FDCHelper.add(amt, splitAmt);
    				
    			totalAimCost = FDCHelper.add(totalAimCost, amt);
		}
		for(int i=0;i<entrys.size();i++){
    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)entrys.get(i);
    		if(entry!=null&&entry.getLevel()==0){
    			BigDecimal topAmount = FDCHelper.ZERO;
    			if(!entry.isIsLeaf()){
	    			for(int j=i+1;j<entrys.size();j++){
	    				FDCSplitBillEntryInfo subEntry = (FDCSplitBillEntryInfo)entrys.get(j);
	    				if(subEntry.getLevel()<=entry.getLevel()){
	    					break;
	    				}
	    				String costAccountID = subEntry.getCostAccount().getId().toString();
	    				BigDecimal splitAmt = FDCHelper.ZERO;
	    				if(subEntry.isIsLeaf()){
		    				if(subEntry.getProduct()!=null){
		    					String prodId = subEntry.getProduct().getId().toString();
			            		//��Ʒ��Ŀ��ɱ�ָ��
			            		if(aimCostMap.containsKey(costAccountID+prodId)){
			            			splitAmt = (BigDecimal)aimCostMap.get(costAccountID+prodId);
			            		}
			            		topAmount =FDCHelper.add(topAmount, splitAmt) ;
		    				}else{
		    					if(aimCostMap.containsKey(costAccountID)){
		    						splitAmt = (BigDecimal)aimCostMap.get(costAccountID);
			            		}
		    					topAmount =FDCHelper.add(topAmount, splitAmt) ;
		    				}
	    				}
	    				
	    			}
    			}else{
    				//��ϸ��ֱ��ȡ��ĿĿ��ɱ�
    				String costAccountID = entry.getCostAccount().getId().toString();
    				BigDecimal splitAmt = FDCHelper.ZERO;
    				if(aimCostMap.containsKey(costAccountID)){
						splitAmt = (BigDecimal)aimCostMap.get(costAccountID);
            		}
					topAmount =FDCHelper.add(topAmount, splitAmt) ;
    			}
    			//δ��̯����Ʒȡ��Ŀ
    			//������ȡ��Ϊ0��������ȡ����!
    			amount = FDCHelper.divide(FDCHelper.multiply(totalAmount,topAmount),totalAimCost,10,BigDecimal.ROUND_HALF_UP);
    			if(amount==null){
    				amount= FDCHelper.ZERO;
    			}
    			entry.setAmount(amount);
    		}    		
    	}
	}
}
