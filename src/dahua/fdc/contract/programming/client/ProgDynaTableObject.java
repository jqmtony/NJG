package com.kingdee.eas.fdc.contract.programming.client;

import java.math.BigDecimal;

public class ProgDynaTableObject {
	private String id;// 框架合约ID
	private int level;// 树型层次
	private String longNumber;// 框架合约长编码
	private String name;// 框架合约名称
	private BigDecimal amount;// 框架合约规划金额
	private BigDecimal controlAmount;// 框架合约控制金额
	private BigDecimal balance;// 框架合约规划余额
	private BigDecimal controlBalance;// 框架合约控制余额
	private String invite;// 招标立项
	private String contractname;// 合同名称
	private String signSec;// 合同乙方
	private BigDecimal signUpAmount;// 签约金额
	private BigDecimal changeAmount;// 变更金额
	private BigDecimal newCost;// 最新造价
	private BigDecimal settleAmount;// 结算金额
	private String remark;// 备注

	private String ccbFid;

	public String getCcbFid() {
		return ccbFid;
	}

	public void setCcbFid(String ccbFid) {
		this.ccbFid = ccbFid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String getLongNumber() {
		return longNumber;
	}
	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getControlAmount() {
		return controlAmount;
	}

	public void setControlAmount(BigDecimal controlAmount) {
		this.controlAmount = controlAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getControlBalance() {
		return controlBalance;
	}

	public void setControlBalance(BigDecimal controlBalance) {
		this.controlBalance = controlBalance;
	}
	public String getInvite() {
		return invite;
	}
	public void setInvite(String invite) {
		this.invite = invite;
	}
	public String getContractname() {
		return contractname;
	}
	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	public String getSignSec() {
		return signSec;
	}
	public void setSignSec(String signSec) {
		this.signSec = signSec;
	}

	public BigDecimal getSignUpAmount() {
		return signUpAmount;
	}

	public void setSignUpAmount(BigDecimal signUpAmount) {
		this.signUpAmount = signUpAmount;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public BigDecimal getNewCost() {
		return newCost;
	}

	public void setNewCost(BigDecimal newCost) {
		this.newCost = newCost;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
