/*
 * @(#)ContractChangeBillContants.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.contract.client;

/**
 * 
 * 描述:变更签证单常量类
 * @author liupd  date:2006-8-3 <p>
 * @version EAS5.1.3
 */
public final class ContractChangeBillContants {

	/*
	 * 变更签证单表格的列名 
	 */
	public static final String COL_ID = "id";
	public static final String COL_STATE = "state";
	public static final String COL_AFTERSIGN_STATE="afterSignState";//变更签证确认在结算走工作流的过程中的状态  by Cassiel_peng 2009-8-20
	public static final String COL_CHANGEAUDIT = "changeAudit.number";
	public static final String COL_CHANGETYPE = "changeType.name";
    public static final String COL_NUMBER = "number";
    public static final String COL_BILLNAME = "billName";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_BUDGETPERSON = "budgetPerson.name";
    public static final String COL_CONDUCTTIME = "conductTime";
    public static final String COL_CONDUCTDEPT = "conductDept.name";
    public static final String COL_SETTLEAMOUNT = "settleAmount";
    public static final String COL_MAINSUPP = "mainSupp";
    public static final String COL_AUDITOR = "auditor.name";
    public static final String COL_HASSETTLED = "hasSettled";
    public static final String COL_SETTLEDTIME = "settleTimed";
    public static final String COL_CHANGEAUDIT_ID="changeAudit.id";
}
