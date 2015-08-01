package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.util.DateTimeUtils;

public class ContractFullFilterParam implements Serializable {
	private String[] compayIds;

	private String[] projectIds;

	private String[] contractTypeIds;

	private String providerId;

	private Date dateFrom;

	private Date dateTo;

	private int yearFrom;

	private int yearTo;

	private int monthFrom;

	private int monthTo;

	private int dateType;

	private int contractState;

	private int settleState;

	private boolean isShowAll;

	public boolean isShowAll() {
		return isShowAll;
	}

	public void setShowAll(boolean isShowAll) {
		this.isShowAll = isShowAll;
	}

	public String[] getCompayIds() {
		return compayIds;
	}

	public void setCompayIds(String[] compayIds) {
		this.compayIds = compayIds;
	}

	public int getContractState() {
		return contractState;
	}

	public void setContractState(int contractState) {
		this.contractState = contractState;
	}

	public String[] getContractTypeIds() {
		return contractTypeIds;
	}

	public void setContractTypeIds(String[] contractTypeIds) {
		this.contractTypeIds = contractTypeIds;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public String[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String[] projectIds) {
		this.projectIds = projectIds;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public int getSettleState() {
		return settleState;
	}

	public void setSettleState(int settleState) {
		this.settleState = settleState;
	}

	public Date getBeginQueryDate() {
		Date date = null;
		if (this.getDateType() == 0) {
			date = this.getDateFrom();
		} else if (this.getDateType() == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearFrom());
			cal.set(Calendar.MONTH, this.getMonthFrom() - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (this.getDateType() == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearFrom());
			cal.set(Calendar.MONTH, (this.getMonthFrom()-1) * 3 );
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (this.getDateType() == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearFrom());
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	
	public Date getEndQueryDate() {
		Date date = null;
		if (this.getDateType() == 0) {
			date = this.getDateTo();
		} else if (this.getDateType() == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearTo());
			cal.set(Calendar.MONTH, this.getMonthTo());
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (this.getDateType() == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearTo());
			cal.set(Calendar.MONTH, this.getMonthTo()* 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (this.getDateType() == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, this.getYearTo()+1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public int getMonthFrom() {
		return monthFrom;
	}

	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}

	public int getMonthTo() {
		return monthTo;
	}

	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}

	public int getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}

	public int getYearTo() {
		return yearTo;
	}

	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
}
