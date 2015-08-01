/**
 * @(#)FdcRptConstant.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata;

/**
 * 房地产 报表常量
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcRptConstant {

	/**
	 * KDTable
	 */
	public static final String KDTABLE = "KDTable";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

	// 报表.查询结果
	public static final String RPT_QUERYRESULT = "rpt.QueryResult";
	// 报表.临时表
	public static final String RPT_TEMPTABLE = "rpt.TempTable";
	// 报表.参数
	public static final String RPT_PARAMETERS = "rpt.Parameters";
	// 报表.插入临时表SQL
	public static final String RPT_INSERDATASQL = "rpt.InserDataSql";
	// 报表.插入临时表SQL_Test
	public static final String RPT_INSERDATASQL_TEST = "rpt.InserDataSql_Test";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

	// ID
	public static final String COL_ID = "id";

	// 序号、编码、名称
	public static final String COL_SEQ = "seq";
	public static final String COL_NUMBER = "number";
	public static final String COL_NAME = "name";

	// 单价、数量、金额
	public static final String COL_PRICE = "price";
	public static final String COL_QTY = "qty";
	public static final String COL_AMOUNT = "amount";

	// 备注
	public static final String COL_DESCRIPTION = "description";
	// 规格
	public static final String COL_MODEL = "model";

	// 业务期间、业务日期
	public static final String COL_BIZPERIOD = "bizPeriod";
	public static final String COL_BIZDATE = "bizDate";

	// 计量单位
	public static final String COL_MEASUREUNITNUMBER = "measureUnitNumber";
	public static final String COL_MEASUREUNITNAME = "measureUnitName";

	// 工程量清单
	public static final String COL_ITEMNUMBER = "itemNumber";
	public static final String COL_ITEMNAME = "itemName";
	public static final String COL_ITEMDISPLAYNAME = "itemDisplayName";

	// 资源
	// 注意：resourceType对应的是ResourceTypeEnum.enum，而不是ResourceType.entity
	public static final String COL_RESOURCETYPE = "resourceType";
	public static final String COL_RESOURCENUMBER = "resourceNumber";
	public static final String COL_RESOURCENAME = "resourceName";
	public static final String COL_RESOURCEITEMNUMBER = "resourceItemNumber";
	public static final String COL_RESOURCEITEMNAME = "resourceItemName";

	// CBS 0#台账
	// 注意：
	// 1、Ec标准产品中，CBS即0#台账，它是WBS+PLS的综合体，没有编码和名称
	// 2、以方远为代表的成本管理(CBS模式)模块中，CBS不同于Ec标准产品中的0#台账，其编码手动输入，名称对应资源项名称
	public static final String COL_CBSNUMBER = "cbsNumber";
	public static final String COL_CBSNAME = "cbsName";

	// OBS
	public static final String COL_OBSNUMBER = "obsNumber";
	public static final String COL_OBSNAME = "obsName";

	// WBS
	public static final String COL_WBSNUMBER = "wbsNumber";
	public static final String COL_WBSNAME = "wbsName";
	public static final String COL_WBSDISPLAYNAME = "wbsDisplayName";

	// 成本科目
	public static final String COL_COSTACCOUNTNUMBER = "costAccountNumber";
	public static final String COL_COSTACCOUNTNAME = "costAccountName";

	// 其他工程费
	public static final String COL_OTHERACCOUNTNUMBER = "otherAccountNumber";
	public static final String COL_OTHERACCOUNTNAME = "otherAccountName";

	// 定额
	public static final String COL_INDEXITEMNUMBER = "indexItemNumber";
	public static final String COL_INDEXITEMNAME = "indexItemName";

	// 单位数量
	public static final String COL_UNITQTY = "unitQty";
	// 单位金额
	public static final String COL_UNITAMOUNT = "unitAmount";

	// 设计数量
	public static final String COL_DESIGNQTY = "designQty";
	// 变更数量
	public static final String COL_CHANGEQTY = "changeQty";
	// 完成数量
	public static final String COL_COMPLETEQTY = "completeQty";
	// 日志记录数量
	public static final String COL_DAILYQTY = "dailyQty";
	// 填报数量
	public static final String COL_FILLQTY = "fillQty";
	// 核定数量
	public static final String COL_CHECKQTY = "checkQty";
	// 结算数量
	public static final String COL_SETTLEQTY = "settleQty";

	// 已分摊金额、未分摊
	public static final String COL_SPLITAMOUNT = "splitAmount";
	public static final String COL_UNSPLITAMOUNT = "unSplitAmount";
	public static final String COL_TOTALAMOUNT = "totalAmount";
	public static final String COL_LStSPLITAMOUNT = "lstSplitAmount";
	public static final String COL_TOTALSPLITAMOUNT = "totalSplitAmount";
	public static final String COL_CURAMOUNT = "curAmount";
	public static final String COL_SPLITSCALE = "splitScale";

	// ////////////////////////////////////////////////////////////////////////////
	// 预留字段
	// ////////////////////////////////////////////////////////////////////////////

	public static final String COL_XXXNAME1_1 = "xxxName1_1";
	public static final String COL_XXXNAME1_2 = "xxxName1_2";
	public static final String COL_XXXNAME1_3 = "xxxName1_3";
	public static final String COL_XXXNAME1_4 = "xxxName1_4";
	public static final String COL_XXXNAME1_5 = "xxxName1_5";

	public static final String COL_XXXNAME2_1 = "xxxName2_1";
	public static final String COL_XXXNAME2_2 = "xxxName2_2";
	public static final String COL_XXXNAME2_3 = "xxxName2_3";
	public static final String COL_XXXNAME2_4 = "xxxName2_4";
	public static final String COL_XXXNAME2_5 = "xxxName2_5";

	public static final String COL_XXXNAME3_1 = "xxxName3_1";
	public static final String COL_XXXNAME3_2 = "xxxName3_2";
	public static final String COL_XXXNAME3_3 = "xxxName3_3";
	public static final String COL_XXXNAME3_4 = "xxxName3_4";
	public static final String COL_XXXNAME3_5 = "xxxName3_5";

	public static final String COL_XXXNAME4_1 = "xxxName4_1";
	public static final String COL_XXXNAME4_2 = "xxxName4_2";
	public static final String COL_XXXNAME4_3 = "xxxName4_3";
	public static final String COL_XXXNAME4_4 = "xxxName4_4";
	public static final String COL_XXXNAME4_5 = "xxxName4_5";

	public static final String COL_XXXNAME5_1 = "xxxName5_1";
	public static final String COL_XXXNAME5_2 = "xxxName5_2";
	public static final String COL_XXXNAME5_3 = "xxxName5_3";
	public static final String COL_XXXNAME5_4 = "xxxName5_4";
	public static final String COL_XXXNAME5_5 = "xxxName5_5";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

	// 树形结构
	// 长编码、显示名称、级次、是否是叶子节点、父节点、顶级节点
	public static final String COL_LONGNUMBER = "longNumber";
	public static final String COL_DISPLAYNAME = "displayName";
	public static final String COL_SIMPLENAME = "simpleName";
	public static final String COL_LEVEL = "level";
	public static final String COL_ISLEAF = "isLeaf";
	public static final String COL_PARENTID = "parentId";
	public static final String COL_TOPID = "topId";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

	// 项目组织
	public static final String COL_PROJECTORGNUMBER = "projectOrgNumber";
	public static final String COL_PROJECTORGNAME = "projectOrgName";
	public static final String COL_PROJECTORGDISPLAYNAME = "projectOrgDisplayName";

	// 工程项目
	public static final String COL_PROJECTNUMBER = "projectNumber";
	public static final String COL_PROJECTNAME = "projectName";
	public static final String COL_PROJECTDISPLAYNAME = "projectDisplayName";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

}
