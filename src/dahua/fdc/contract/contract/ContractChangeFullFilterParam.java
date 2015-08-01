package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.util.DateTimeUtils;

public class ContractChangeFullFilterParam implements Serializable {

	private String[] compayIds;

	private String[] projectIds;

	private String[] changeTypeIds;

	private String partBId;

	private Date dateFrom;

	private Date dateTo;

	private int contractState;


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


	public Date getDateFrom() {
		return DateTimeUtils.truncateDate(dateFrom);
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return DateTimeUtils.truncateDate(dateTo);
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String[] projectIds) {
		this.projectIds = projectIds;
	}

	public String[] getChangeTypeIds() {
		return changeTypeIds;
	}

	public void setChangeTypeIds(String[] changeTypeIds) {
		this.changeTypeIds = changeTypeIds;
	}

	public String getPartBId() {
		return partBId;
	}

	public void setPartBId(String partBId) {
		this.partBId = partBId;
	}

}
