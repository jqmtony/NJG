package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import java.util.Date;

import com.kingdee.util.DateTimeUtils;

public class PayRequestFullFilterParam implements Serializable {

	private String[] compayIds;

	private String[] projectIds;

	private String[] contractIds;

	private String payeeId;

	private Date dateFrom;

	private Date dateTo;

	private int payState;
	
	private boolean isIncludeClose;


	public boolean isIncludeClose() {
		return isIncludeClose;
	}

	public void setIncludeClose(boolean isIncludeClose) {
		this.isIncludeClose = isIncludeClose;
	}

	public String[] getCompayIds() {
		return compayIds;
	}

	public void setCompayIds(String[] compayIds) {
		this.compayIds = compayIds;
	}

	public int getPayState() {
		return payState;
	}

	public void setPayState(int contractState) {
		this.payState = contractState;
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

	public String[] getContractIds() {
		return contractIds;
	}

	public void setContractIds(String[] changeTypeIds) {
		this.contractIds = changeTypeIds;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String partBId) {
		this.payeeId = partBId;
	}

}
