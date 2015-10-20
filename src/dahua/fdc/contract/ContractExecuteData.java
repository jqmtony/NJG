package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContractExecuteData implements Serializable, Comparable {
	


	private List children = new ArrayList();
	//
	private ContractBillInfo contract;
	//
	private ContractWithoutTextInfo noTextContract;
	// 对方单位（无文本合同）
	private String partB;
	
	// 未付款金额
	private BigDecimal notPay;
	// 完工未付款金额
	private BigDecimal completeNotPay;
	// 累计完工工程量
	private BigDecimal completeProjectAmount;
	// 变更金额
	private BigDecimal changeAmount;
	// 合同最新造价（本位币）
	private BigDecimal contractLastAmount;
	// 合同最新造价（原币）
	private BigDecimal contractLastSrcAmount;
	// 已实现产值(总结算金额)
	private BigDecimal totalSettPrice;	
	// 计划付款信息：计划付款id
	private Object planPayId;	
	// 计划付款信息：计划付款日期
	private Object planPayDate;
	// 计划付款信息：计划付款金额
	private BigDecimal planPayAmount;
	// 计划付款信息：计划付款原币金额
	private BigDecimal planPaySrcAmount;	
	// 实际付款信息：实际付款id
	private Object realPayId;
	// 实际付款信息：实际付款日期
	private Object realPayDate;
	// 实际付款信息：实际付款原币金额
	private BigDecimal realPaySrcAmount;
	// 实际付款信息：实际付款本币金额
	private BigDecimal realPayAmount;
	// 实际付款信息：合同内工程款
	private BigDecimal projectPriceInContract;
	
	/* modified by zhaoqin for R140227-0281 on 2014/03/20 */
	// 实际付款信息：合同内工程款(原币)
	private BigDecimal addProjectAmt;
	
	//付款单信息：业务日期
	private Object payBizDate;
	//累计法票金额本币
	private BigDecimal allFinvoiceAmt; 
	//累计法票金额原币
	private BigDecimal allFinvoiceOriAmt; 
	//调整款项
	private Map adjustSumMap;
	//付款单发票
	private Map payAmountMap;
	private Map payAmountOriMap;
	

	public Map getPayAmountMap() {
		return payAmountMap;
	}

	public void setPayAmountMap(Map payAmountMap) {
		this.payAmountMap = payAmountMap;
	}

	public Map getPayAmountOriMap() {
		return payAmountOriMap;
	}

	public void setPayAmountOriMap(Map payAmountOriMap) {
		this.payAmountOriMap = payAmountOriMap;
	}

	public Map getAdjustSumMap() {
		return adjustSumMap;
	}

	public void setAdjustSumMap(Map adjustSumMap) {
		this.adjustSumMap = adjustSumMap;
	}

	public BigDecimal getAllFinvoiceOriAmt() {
		return allFinvoiceOriAmt;
	}

	public void setAllFinvoiceOriAmt(BigDecimal allFinvoiceOriAmt) {
		this.allFinvoiceOriAmt = allFinvoiceOriAmt;
	}

	public BigDecimal getAllFinvoiceAmt() {
		return allFinvoiceAmt;
	}

	public void setAllFinvoiceAmt(BigDecimal allFinvoiceAmt) {
		this.allFinvoiceAmt = allFinvoiceAmt;
	}

	public String getPartB() {
		return partB;
	}

	public void setPartB(String partB) {
		this.partB = partB;
	}

	public BigDecimal getNotPay() {
		return notPay;
	}

	public void setNotPay(BigDecimal notPay) {
		this.notPay = notPay;
	}

	public BigDecimal getCompleteNotPay() {
		return completeNotPay;
	}

	public void setCompleteNotPay(BigDecimal completeNotPay) {
		this.completeNotPay = completeNotPay;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public ContractBillInfo getContract() {
		return contract;
	}

	public void setContract(ContractBillInfo contract) {
		this.contract = contract;
	}

	public BigDecimal getContractLastAmount() {
		return contractLastAmount;
	}

	public void setContractLastAmount(BigDecimal contractLastAmount) {
		this.contractLastAmount = contractLastAmount;
	}

	public BigDecimal getTotalSettPrice() {
		return totalSettPrice;
	}

	public void setTotalSettPrice(BigDecimal totalSettPrice) {
		this.totalSettPrice = totalSettPrice;
	}

	public Object getPlanPayId() {
		return planPayId;
	}

	public void setPlanPayId(Object planPayId) {
		this.planPayId = planPayId;
	}

	public Object getPlanPayDate() {
		return planPayDate;
	}

	public void setPlanPayDate(Object planPayDate) {
		this.planPayDate = planPayDate;
	}

	public BigDecimal getPlanPayAmount() {
		return planPayAmount;
	}

	public void setPlanPayAmount(BigDecimal planPayAmount) {
		this.planPayAmount = planPayAmount;
	}

	public Object getRealPayId() {
		return realPayId;
	}

	public void setRealPayId(Object realPayId) {
		this.realPayId = realPayId;
	}

	public Object getRealPayDate() {
		return realPayDate;
	}

	public void setRealPayDate(Object realPayDate) {
		this.realPayDate = realPayDate;
	}

	public BigDecimal getRealPaySrcAmount() {
		return realPaySrcAmount;
	}

	public void setRealPaySrcAmount(BigDecimal realPaySrcAmount) {
		this.realPaySrcAmount = realPaySrcAmount;
	}

	public BigDecimal getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(BigDecimal realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public BigDecimal getProjectPriceInContract() {
		return projectPriceInContract;
	}

	public void setProjectPriceInContract(BigDecimal projectPriceInContract) {
		this.projectPriceInContract = projectPriceInContract;
	}
	

	public BigDecimal getCompleteProjectAmount() {
		return completeProjectAmount;
	}

	public void setCompleteProjectAmount(BigDecimal completeProjectAmount) {
		this.completeProjectAmount = completeProjectAmount;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public ContractWithoutTextInfo getNoTextContract() {
		return noTextContract;
	}

	public void setNoTextContract(ContractWithoutTextInfo noTextContract) {
		this.noTextContract = noTextContract;
	}

	public BigDecimal getContractLastSrcAmount() {
		return contractLastSrcAmount;
	}

	public void setContractLastSrcAmount(BigDecimal contractLastSrcAmount) {
		this.contractLastSrcAmount = contractLastSrcAmount;
	}

	public BigDecimal getPlanPaySrcAmount() {
		return planPaySrcAmount;
	}

	public void setPlanPaySrcAmount(BigDecimal planPaySrcAmount) {
		this.planPaySrcAmount = planPaySrcAmount;
	}
	
	public Object getPayBizDate() {
		return payBizDate;
	}

	public void setPayBizDate(Object payBizDate) {
		this.payBizDate = payBizDate;
	}
	
	/* modified by zhaoqin for R140227-0281 on 2014/03/20 start */
	public BigDecimal getAddProjectAmt() {
		return addProjectAmt;
	}
	
	public void setAddProjectAmt(BigDecimal addProjectAmt) {
		this.addProjectAmt = addProjectAmt;
	}
	/* modified by zhaoqin for R140227-0281 on 2014/03/20 end */

	public int compareTo(Object arg0) {
		ContractExecuteData other = (ContractExecuteData) arg0;
		String otherProjectName = "";
		if (other.getContract() != null) {
			otherProjectName = other.getContract().getCurProject().getName();
		} else {
			otherProjectName = other.getNoTextContract().getCurProject().getName();
		}
		String projectName = "";
		if (this.getContract() != null) {
			projectName = this.getContract().getCurProject().getName();
		} else {
			projectName = this.getNoTextContract().getCurProject().getName();
		}
		return projectName.compareTo(otherProjectName);
	}
}
