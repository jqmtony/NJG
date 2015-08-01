package com.kingdee.eas.fdc.basedata;

public class FDCBillWFAuditUtil {
	
	/****
	 * 序号
	 * id
	 */
	static public final String ID = "id";
	/****
	 * 单据号
	 * billId
	 */
	static public final String BillID = "billId";
	/****
	 * 审批人
	 * peronName
	 */
	static public final String PersonName = "peronName";
	
	/***
	 * 审批部门
	 * adminUnitName
	 */
	static public final String AdminUnitName = "adminUnitName";
	
	/***
	 * 审批人主要职位
	 * auditPosition
	 */
	
	static public final String AuditPosition = "auditPosition";
	
	/***
	 * 审批意见
	 * opinion
	 */
	
	static public final String Opinion = "opinion";
	
	/***
	 * 审批时间
	 * createTime
	 */
	
	static public final String CreateTime = "createTime";
	
	/***
	 * 组织名称
	 * ctrlUnitName
	 */
	
	static public final String CtrlUnitName = "ctrlUnitName";
	
	/***
	 * 审批节点名称
	 * ctrlUnitName
	 */
	
	static public final String AuditNodeName = "auditNodeName";
	
	/***
	 * 审批节点名称
	 * ctrlUnitName
	 */
	
	/**
	 * 审批结果
	 * isPass
	 */
	static public final String IsPass = "isPass";
	
	/**
	 * 决策选项值
	 * handlerOpinion
	 */
	static public final String HandlerOpinion = "handlerOpinion";
	
	/**
	 * 具体决策项内容
	 * handlerContent
	 */
	static public final String HandlerContent = "handlerContent";
	
	
	static public final String[] AuditInfoCols = new String[] {ID,BillID,IsPass,Opinion,HandlerOpinion,HandlerContent,PersonName,AdminUnitName,AuditPosition,CreateTime,CtrlUnitName,AuditNodeName};

}
