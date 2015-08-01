package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;

public class HappenDataInfo implements Serializable{
	String acctId = "";

	CostSplitBillTypeEnum billType;

	BigDecimal amount = FDCHelper.ZERO;

	Map productAmountMap = new HashMap();

	boolean isSettled = false;

	String userObjectId = null;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CostSplitBillTypeEnum getBillType() {
		return billType;
	}

	public void setBillType(CostSplitBillTypeEnum billType) {
		this.billType = billType;
	}

	public boolean isSettled() {
		return isSettled;
	}

	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
	}

	public void addProductAmount(String productId, BigDecimal amount) {
		if (this.productAmountMap.containsKey(productId)) {
			BigDecimal productAmount = (BigDecimal) this.productAmountMap
					.get(productId);
			this.productAmountMap.put(productId, productAmount.add(amount));
		} else {
			this.productAmountMap.put(productId, amount);
		}
	}

	public void addProductAll(Map productMap) {
		Set set = productMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String acctId = (String) iter.next();
			BigDecimal amount = (BigDecimal) productMap.get(acctId);
			if (this.productAmountMap.containsKey(acctId)) {
				BigDecimal sum = (BigDecimal) this.productAmountMap.get(acctId);
				this.productAmountMap.put(acctId, sum.add(amount));
			} else {
				this.productAmountMap.put(acctId, amount);
			}
		}
	}

	public void add(HappenDataInfo addData) {
		if (addData == null) {
			return;
		}
		if (addData.getAmount() != null) {
			this.setAmount(amount.add(addData.getAmount()));
		}
		if (addData.getProductAmountMap() != null) {
			this.addProductAll(addData.getProductAmountMap());
		}
	}

	public void setProductAmountMap(Map productAmount) {
		this.productAmountMap = productAmount;
	}

	public Map getProductAmountMap() {
		return productAmountMap;
	}

	public String getUserObjectId() {
		return userObjectId;
	}

	public void setUserObjectId(String userObjectId) {
		this.userObjectId = userObjectId;
	}

	public void addAmount(BigDecimal add) {
		amount = amount.add(add);
	}

	public String toString() {
		return amount.toString() + productAmountMap.toString();
	}

	/**
	 * key组成说明：<br>
	 * 当userObjectId不空时，key = acctId + userObject + 1或0
	 * 当userObjectId为空时，key = acctId + 1或0
	 * 1 表示已结算的金额，0表示未结算金额，对合同拆分和变更拆分而言，一般取未结算金额相加
	 * 
	 * 结算拆分的	  		key = acctId <p>
	 * 
	 * --------------------<br>
	 * 所以调用的地方一般这么写：<br>
	 * 1. 全项目动态成本表 (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 0);<br>
	 * 2. 动态成本监控表   (HappenDataInfo) happenGetter.changeSplitMap.get(costAccounts.get(i).getId().toString() + 0);
	 */
	public String getKey() {
		String key = acctId;
		if (this.billType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {//变更拆分
			if (this.userObjectId != null) {
				key += userObjectId;
			}
			if (isSettled) {
				key += 1;
			} else {
				key += 0;
			}
		} else if (this.billType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			if (this.userObjectId != null) {
				if (isSettled) {
					key += 1;
				} else {
					key += 0;
				}
			}
		}
		//return key;
		return key;//结算拆分
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
}
