package com.kingdee.eas.fdc.basedata;

public class FDCBillWFAuditUtil {
	
	/****
	 * ���
	 * id
	 */
	static public final String ID = "id";
	/****
	 * ���ݺ�
	 * billId
	 */
	static public final String BillID = "billId";
	/****
	 * ������
	 * peronName
	 */
	static public final String PersonName = "peronName";
	
	/***
	 * ��������
	 * adminUnitName
	 */
	static public final String AdminUnitName = "adminUnitName";
	
	/***
	 * ��������Ҫְλ
	 * auditPosition
	 */
	
	static public final String AuditPosition = "auditPosition";
	
	/***
	 * �������
	 * opinion
	 */
	
	static public final String Opinion = "opinion";
	
	/***
	 * ����ʱ��
	 * createTime
	 */
	
	static public final String CreateTime = "createTime";
	
	/***
	 * ��֯����
	 * ctrlUnitName
	 */
	
	static public final String CtrlUnitName = "ctrlUnitName";
	
	/***
	 * �����ڵ�����
	 * ctrlUnitName
	 */
	
	static public final String AuditNodeName = "auditNodeName";
	
	/***
	 * �����ڵ�����
	 * ctrlUnitName
	 */
	
	/**
	 * �������
	 * isPass
	 */
	static public final String IsPass = "isPass";
	
	/**
	 * ����ѡ��ֵ
	 * handlerOpinion
	 */
	static public final String HandlerOpinion = "handlerOpinion";
	
	/**
	 * �������������
	 * handlerContent
	 */
	static public final String HandlerContent = "handlerContent";
	
	
	static public final String[] AuditInfoCols = new String[] {ID,BillID,IsPass,Opinion,HandlerOpinion,HandlerContent,PersonName,AdminUnitName,AuditPosition,CreateTime,CtrlUnitName,AuditNodeName};

}
