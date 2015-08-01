/**
 * @(#)FdcColumnName.java 1.0 2014-11-4
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

/**
 * 描述：房地产列名
 * <p>
 * 列名定义，这样可以做到统一
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-11-4
 * @version 1.0, 2014-11-4
 * @see
 * @since JDK1.4
 */
public class FdcColumnName {

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
	public static final String COL_DESC = "description";
	// 规格
	public static final String COL_MODEL = "model";

	// 业务期间、业务日期
	public static final String COL_BIZPERIOD = "bizPeriod";
	public static final String COL_BIZDATE = "bizDate";

	// 计量单位
	public static final String COL_MEASUREUNIT = "measureUnit";
	public static final String COL_MEASUREUNITNAME = "measureUnit.name";

	// 工程量清单
	public static final String COL_ITEM = "item";
	public static final String COL_ITEMNAME = "item.name";
	public static final String COL_ITEMDISPLAYNAME = "item.displayName";

	// 资源
	// 注意：resourceType对应的是ResourceTypeEnum.enum，而不是ResourceType.entity
	public static final String COL_RESOURCETYPE = "resourceType";
	public static final String COL_RESOURCE = "resource";
	public static final String COL_RESOURCENAME = "resource.name";
	public static final String COL_RESOURCEITEM = "resourceItem";
	public static final String COL_RESOURCEITEMNAME = "resourceItem.name";

	// CBS 0#台账
	public static final String COL_CBS = "cbs";
	public static final String COL_CBSNAME = "cbs.name";
	public static final String COL_CBSDISPLAYNAME = "cbs.displayName";

	// OBS
	public static final String COL_OBS = "obs";
	public static final String COL_OBSNAME = "obs.name";
	public static final String COL_OBSDISPLAYNAME = "obs.displayName";

	// WBS
	public static final String COL_WBS = "wbs";
	public static final String COL_WBSNAME = "wbs.name";
	public static final String COL_WBSDISPLAYNAME = "wbs.displayName";

	// 成本科目
	public static final String COL_COSTACCOUNT = "costAccount";
	public static final String COL_COSTACCOUNTNAME = "costAccount.name";

	// 其他工程费
	public static final String COL_OTHERACCOUNT = "otherAccount";
	public static final String COL_OTHERACCOUNTNAME = "otherAccount.name";

	// 定额
	public static final String COL_INDEXITEM = "indexItem";
	public static final String COL_INDEXITEMNAME = "indexItem.name";

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
	public static final String COL_PARENTID = "parent.id";
	public static final String COL_TOPID = "top.id";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////

	// 项目组织
	public static final String COL_PROJECTORG = "projectOrg";
	public static final String COL_PROJECTORGNAME = "projectOrg.name";
	public static final String COL_PROJECTORGDISPLAYNAME = "projectOrg.displayName";

	// 工程项目
	public static final String COL_PROJECT = "project";
	public static final String COL_PROJECTNAME = "project.name";
	public static final String COL_PROJECTDISPLAYNAME = "project.displayName";

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////
	
}
