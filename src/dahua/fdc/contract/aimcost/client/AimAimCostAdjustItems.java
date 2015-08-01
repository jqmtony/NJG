package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;

public class AimAimCostAdjustItems {
	private String id;
	private String parentID;
	private String costAccountID;
	private BigDecimal occuredAmount;
	private String contract;
	private String contractNumber;
	private String contractID;
	private String changeContract;
	private String changeContractNumber;
	private String changeContractID;
	private String settleContract;
	private String settleContractNumber;
	private String settleContractID;
	private String overDescription;

	private String flag;

	private BigDecimal aimCost;
	private BigDecimal overAmount;

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getChangeContractNumber() {
		return changeContractNumber;
	}

	public void setChangeContractNumber(String changeContractNumber) {
		this.changeContractNumber = changeContractNumber;
	}

	public String getSettleContractNumber() {
		return settleContractNumber;
	}

	public void setSettleContractNumber(String settleContractNumber) {
		this.settleContractNumber = settleContractNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public BigDecimal getOverAmount() {
		return overAmount;
	}

	public void setOverAmount(BigDecimal overAmount) {
		this.overAmount = overAmount;
	}

	public BigDecimal getAimCost() {
		return aimCost;
	}

	public void setAimCost(BigDecimal aimCost) {
		this.aimCost = aimCost;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getCostAccountID() {
		return costAccountID;
	}

	public void setCostAccountID(String costAccountID) {
		this.costAccountID = costAccountID;
	}

	public BigDecimal getOccuredAmount() {
		return occuredAmount;
	}

	public void setOccuredAmount(BigDecimal occuredAmount) {
		this.occuredAmount = occuredAmount;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public String getChangeContract() {
		return changeContract;
	}

	public void setChangeContract(String changeContract) {
		this.changeContract = changeContract;
	}

	public String getChangeContractID() {
		return changeContractID;
	}

	public void setChangeContractID(String changeContractID) {
		this.changeContractID = changeContractID;
	}

	public String getSettleContract() {
		return settleContract;
	}

	public void setSettleContract(String settleContract) {
		this.settleContract = settleContract;
	}

	public String getSettleContractID() {
		return settleContractID;
	}

	public void setSettleContractID(String settleContractID) {
		this.settleContractID = settleContractID;
	}

	public String getOverDescription() {
		return overDescription;
	}

	public void setOverDescription(String overDescription) {
		this.overDescription = overDescription;
	}


}
