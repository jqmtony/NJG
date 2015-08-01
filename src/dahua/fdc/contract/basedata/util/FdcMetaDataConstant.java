/**
 * @(#)FdcMetaDataConstant.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.util.BOSObjectType;

/**
 * 描述：房地产元数据常量
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcMetaDataConstant {

	/**
	 * 元数据_拓展名_entity
	 */
	public static final String META_DATA_EXTNAME_ENTITY = "entity";

	/**
	 * 元数据_拓展名_query
	 */
	public static final String META_DATA_EXTNAME_QUERY = "query";

	/**
	 * 元数据_拓展名_function
	 */
	public static final String META_DATA_EXTNAME_FUNCTION = "function";

	/**
	 * 元数据_拓展名_ui
	 */
	public static final String META_DATA_EXTNAME_UI = "ui";

	/**
	 * 元数据_拓展名_bizunit
	 */
	public static final String META_DATA_EXTNAME_BIZUNIT = "bizunit";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 标签_key
	 */
	public static final String TAG_RESOURCE = "resource";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 属性名_key
	 */
	public static final String ATTRIBUTE_NAME_KEY = "key";

	/**
	 * 属性名_locale
	 */
	public static final String ATTRIBUTE_NAME_LOCALE = "locale";

	/**
	 * 属性名_value
	 */
	public static final String ATTRIBUTE_NAME_VALUE = "value";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 元数据_文件名_后缀_query
	 */
	public static final String META_DATA_FILENAME_SUFFIX_QUERY = "Query";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 资源_类型_拓展属性
	 */
	public static final String RS_TYPE_EXTENDED_PROPERTY = "extendedProperty";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * defaultF7Query键
	 */
	public static final String DEFAULT_F7_QUERY_KEY = "defaultF7Query";

	/**
	 * defaultF7Query完整键的后缀名
	 */
	public static final String DEFAULT_F7_QUERY_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "."
			+ DEFAULT_F7_QUERY_KEY;

	/**
	 * defaultF7Query完整键模式
	 */
	public static final String DEFAULT_F7_QUERY_FULL_KEY_PATTERN = "entityObject[{0}]."
			+ DEFAULT_F7_QUERY_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * orgType键
	 */
	public static final String ORG_TYPE_KEY = "OrgType";

	/**
	 * orgType完整键的后缀名
	 */
	public static final String ORG_TYPE_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "." + ORG_TYPE_KEY;

	/**
	 * orgType完整键模式
	 */
	public static final String ORG_TYPE_FULL_KEY_PATTERN = "entityObject[{0}]." + ORG_TYPE_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 组织类型Set
	 */
	public static final Set ORG_TYPE_SET = new LinkedHashSet(Arrays.asList(new String[] { "NONE", "Admin", "Company",
			"Sale", "Purchase", "Storage", "CostCenter", "ProfitCenter", "ControlUnit", "UnionDebt", "Transport" }));

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * crNumberEdit键
	 */
	public static final String CR_NUMBER_EDIT_KEY = "CRNumberEdit";

	/**
	 * crNumberEdit完整键的后缀名
	 */
	public static final String CR_NUMBER_EDIT_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "." + CR_NUMBER_EDIT_KEY;

	/**
	 * crNumberEdit完整键模式
	 */
	public static final String CR_NUMBER_EDIT_FULL_KEY_PATTERN = "entityObject[{0}]." + CR_NUMBER_EDIT_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 编码规则生成编码可修改Set
	 */
	public static final Set CR_NUMBER_EDIT_SET = new LinkedHashSet(Arrays.asList(new String[] { "false", "true" }));

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * BOS对象类型_字符串_工程项目
	 */
	public static final String BOS_TYPE_STR_PROJECT = "C82BC724";
	/**
	 * BOS对象类型_字符串_组织单元
	 */
	public static final String BOS_TYPE_STR_ORG_UNIT = "9127AC92";
	/**
	 * BOS对象类型_字符串_期间
	 */
	public static final String BOS_TYPE_STR_PERIOD = "82388C4C";
	/**
	 * BOS对象类型_字符串_用户
	 */
	public static final String BOS_TYPE_STR_USER = "13B7DE7F";

	// ////////////////////////////////////

	/**
	 * BOS对象类型_字符串_单据实体基类
	 */
	public static final String BOS_TYPE_STR_BILL_ENTRY_BASE = "0DDD7E9A";
	/**
	 * BOS对象类型_字符串_建筑基础数据
	 */
	public static final String BOS_TYPE_STR_FDC_BASE_DATA = "E88F90EE";

	/**
	 * BOS对象类型_字符串_建筑树形结构基类
	 */
	public static final String BOS_TYPE_STR_FDC_TREE_BASE = "A1C9E662";
	/**
	 * BOS对象类型_字符串_树形结构基类
	 */
	public static final String BOS_TYPE_STR_TREE_BASE = "0A9C2851";
	/**
	 * BOS对象类型_字符串_数据基类
	 */
	public static final String BOS_TYPE_STR_DATA_BASE = "229FBE5D";

	// ////////////////////////////////////

	/**
	 * BOS对象类型_字符串_建筑单据
	 */
	public static final String BOS_TYPE_STR_FDC_BILL = "C13B7C5A";
	/**
	 * BOS对象类型_字符串_基本单据
	 */
	public static final String BOS_TYPE_STR_BASE_BILL = "122FD3CD";
	/**
	 * BOS对象类型_字符串_核心单据基类
	 */
	public static final String BOS_TYPE_STR_CORE_BILL_BASE = "56C04FF9";
	/**
	 * BOS对象类型_字符串_对象基类
	 */
	public static final String BOS_TYPE_STR_OBJECT_BASE = "0B2876F2";
	/**
	 * BOS对象类型_字符串_核心基类
	 */
	public static final String BOS_TYPE_STR_CORE_BASE = "3B2DDC00";

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * BOS对象类型_工程项目
	 */
	public static final BOSObjectType BOS_TYPE_PROJECT = BOSObjectType.create(BOS_TYPE_STR_PROJECT);
	/**
	 * BOS对象类型_组织单元
	 */
	public static final BOSObjectType BOS_TYPE_ORG_UNIT = BOSObjectType.create(BOS_TYPE_STR_ORG_UNIT);
	/**
	 * BOS对象类型_期间
	 */
	public static final BOSObjectType BOS_TYPE_PERIOD = BOSObjectType.create(BOS_TYPE_STR_PERIOD);
	/**
	 * BOS对象类型_用户
	 */
	public static final BOSObjectType BOS_TYPE_USER = BOSObjectType.create(BOS_TYPE_STR_USER);

	// ////////////////////////////////////

	/**
	 * BOS对象类型_单据实体基类
	 */
	public static final BOSObjectType BOS_TYPE_BILL_ENTRY_BASE = BOSObjectType.create(BOS_TYPE_STR_BILL_ENTRY_BASE);
	/**
	 * BOS对象类型_建筑基础数据
	 */
	public static final BOSObjectType BOS_TYPE_FDC_BASE_DATA = BOSObjectType.create(BOS_TYPE_STR_FDC_BASE_DATA);

	/**
	 * BOS对象类型_建筑树形结构基类
	 */
	public static final BOSObjectType BOS_TYPE_FDC_TREE_BASE = BOSObjectType.create(BOS_TYPE_STR_FDC_TREE_BASE);
	/**
	 * BOS对象类型_树形结构基类
	 */
	public static final BOSObjectType BOS_TYPE_TREE_BASE = BOSObjectType.create(BOS_TYPE_STR_TREE_BASE);
	/**
	 * BOS对象类型_数据基类
	 */
	public static final BOSObjectType BOS_TYPE_DATA_BASE = BOSObjectType.create(BOS_TYPE_STR_DATA_BASE);

	// ////////////////////////////////////

	/**
	 * BOS对象类型_建筑单据
	 */
	public static final BOSObjectType BOS_TYPE_FDC_BILL = BOSObjectType.create(BOS_TYPE_STR_FDC_BILL);
	/**
	 * BOS对象类型_基本单据
	 */
	public static final BOSObjectType BOS_TYPE_BASE_BILL = BOSObjectType.create(BOS_TYPE_STR_BASE_BILL);
	/**
	 * BOS对象类型_核心单据基类
	 */
	public static final BOSObjectType BOS_TYPE_CORE_BILL_BASE = BOSObjectType.create(BOS_TYPE_STR_CORE_BILL_BASE);
	/**
	 * BOS对象类型_对象基类
	 */
	public static final BOSObjectType BOS_TYPE_OBJECT_BASE = BOSObjectType.create(BOS_TYPE_STR_OBJECT_BASE);
	/**
	 * BOS对象类型_核心基类
	 */
	public static final BOSObjectType BOS_TYPE_CORE_BASE = BOSObjectType.create(BOS_TYPE_STR_CORE_BASE);

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * Entity对象_BaseBill
	 */
	public static final EntityObjectInfo ENTITY_OBJECT_INFO_BASE_BILL;

	/**
	 * Entity对象_EcBill
	 */
	public static final EntityObjectInfo ENTITY_OBJECT_INFO_FDC_BILL;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 属性集合_BaseBill
	 */
	public static final PropertyCollection PROPERTY_COLLECTION_BASE_BILL;

	/**
	 * 属性集合_EcBill
	 */
	public static final PropertyCollection PROPERTY_COLLECTION_FDC_BILL;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 属性名列表_BaseBill
	 */
	public static final List PROPERTY_NAME_LIST_BASE_BILL;

	/**
	 * 属性名列表_EcBill
	 */
	public static final List PROPERTY_NAME_LIST_FDC_BILL;

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 常用_属性名_列表_工程项目
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_PROJECT = new ArrayList();
	/**
	 * 常用_属性名_列表_组织单元
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_ORG_UNIT = new ArrayList();
	/**
	 * 常用_属性名_列表_期间
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_PERIOD = new ArrayList();
	/**
	 * 常用_属性名_列表_用户
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_USER = new ArrayList();

	// ////////////////////////////////////

	/**
	 * 常用_属性名_列表_单据实体基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE = new ArrayList();
	/**
	 * 常用_属性名_列表_建筑基础数据
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA = new ArrayList();

	/**
	 * 常用_属性名_列表_建筑树形结构基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE = new ArrayList();
	/**
	 * 常用_属性名_列表_树形结构基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_TREE_BASE = new ArrayList();
	/**
	 * 常用_属性名_列表_数据基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_DATA_BASE = new ArrayList();

	// ////////////////////////////////////

	/**
	 * 常用_属性名_列表_建筑单据
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_BILL = new ArrayList();
	/**
	 * 常用_属性名_列表_基本单据
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_BASE_BILL = new ArrayList();
	/**
	 * 常用_属性名_列表_核心单据基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE = new ArrayList();
	/**
	 * 常用_属性名_列表_对象基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_OBJECT_BASE = new ArrayList();
	/**
	 * 常用_属性名_列表_核心基类
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_CORE_BASE = new ArrayList();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 属性名列表_映射 <br>
	 * 其中key存放BosType字符串，value存放属性名_列表
	 */
	public static final Map COMMON_PROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	static {
		// 根据bosType取得对应的实体
		ENTITY_OBJECT_INFO_BASE_BILL = MetaDataLoader.getEntity(null, BOS_TYPE_BASE_BILL);
		ENTITY_OBJECT_INFO_FDC_BILL = MetaDataLoader.getEntity(null, BOS_TYPE_FDC_BILL);

		// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)；使用了缓存策略，在Studio中不应使用
		PROPERTY_COLLECTION_BASE_BILL = ENTITY_OBJECT_INFO_BASE_BILL.getInheritedNoDuplicatedPropertiesRuntime();
		PROPERTY_COLLECTION_FDC_BILL = ENTITY_OBJECT_INFO_FDC_BILL.getInheritedNoDuplicatedPropertiesRuntime();

		// 取得属性名List
		PROPERTY_NAME_LIST_BASE_BILL = FdcMetaDataUtil.getPropertyNameList(PROPERTY_COLLECTION_BASE_BILL.iterator());
		PROPERTY_NAME_LIST_FDC_BILL = FdcMetaDataUtil.getPropertyNameList(PROPERTY_COLLECTION_FDC_BILL.iterator());

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		// project
		COMMON_PROPERTY_NAME_LIST_PROJECT.add("projectOrg.id");
		COMMON_PROPERTY_NAME_LIST_PROJECT.add("projectOrg.number");
		COMMON_PROPERTY_NAME_LIST_PROJECT.add("projectOrg.name");

		COMMON_PROPERTY_NAME_LIST_PROJECT.add("id");
		COMMON_PROPERTY_NAME_LIST_PROJECT.add("number");
		COMMON_PROPERTY_NAME_LIST_PROJECT.add("name");

		COMMON_PROPERTY_NAME_LIST_PROJECT.add("isEnabled");

		// orgUnit
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("id");
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("number");
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("name");

		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("longNumber");
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("displayName");
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("level");
		COMMON_PROPERTY_NAME_LIST_ORG_UNIT.add("isLeaf");

		// period
		COMMON_PROPERTY_NAME_LIST_PERIOD.add("id");
		COMMON_PROPERTY_NAME_LIST_PERIOD.add("number");
		COMMON_PROPERTY_NAME_LIST_PERIOD.add("name");

		COMMON_PROPERTY_NAME_LIST_PERIOD.add("periodType");
		COMMON_PROPERTY_NAME_LIST_PERIOD.add("periodNumber");

		// user
		COMMON_PROPERTY_NAME_LIST_USER.add("id");
		COMMON_PROPERTY_NAME_LIST_USER.add("number");
		COMMON_PROPERTY_NAME_LIST_USER.add("name");

		COMMON_PROPERTY_NAME_LIST_USER.add("type");

		// BillEntryBase派生类
		// billEntryBase
		COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE.add("seq");

		// EcBaseData派生类
		// ecBaseData
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("id");
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("number");
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("name");

		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("isEnabled");

		// DataBase派生类
		// ecTreeBase
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("number");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("name");

		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("longNumber");
		// COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("displayName");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("level");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("isLeaf");

		// COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("isEnabled");
		// EC树形结构规范：父类强制命名为parent
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("parent.id");

		// treeBase
		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("number");
		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("name");

		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("longNumber");
		// COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("displayName");
		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("level");
		COMMON_PROPERTY_NAME_LIST_TREE_BASE.add("isLeaf");

		// dataBase
		COMMON_PROPERTY_NAME_LIST_DATA_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_DATA_BASE.add("number");
		COMMON_PROPERTY_NAME_LIST_DATA_BASE.add("name");

		// BaseBill派生类
		// ecBill
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("creator.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("creator.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("creator.name");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("createTime");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("lastUpdateUser.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("lastUpdateUser.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("lastUpdateUser.name");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("lastUpdateTime");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("auditor.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("auditor.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("auditor.name");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("auditDate");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("projectOrg.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("projectOrg.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("projectOrg.name");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("project.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("project.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("project.name");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("period.id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("period.number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("period.name");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("period.periodYear");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("period.periodNumber");

		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("id");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("number");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("name");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("billSate");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("bizDate");
		COMMON_PROPERTY_NAME_LIST_FDC_BILL.add("description");

		// baseBill
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("creator.id");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("creator.number");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("creator.name");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("createTime");

		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("lastUpdateUser.id");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("lastUpdateUser.number");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("lastUpdateUser.name");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("lastUpdateTime");

		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("auditor.id");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("auditor.number");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("auditor.name");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("auditDate");

		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("projectOrg.id");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("projectOrg.number");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("projectOrg.name");

		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("id");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("number");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("name");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("billSate");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("bizDate");
		COMMON_PROPERTY_NAME_LIST_BASE_BILL.add("description");

		// coreBillBase
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("creator.id");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("creator.number");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("creator.name");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("createTime");

		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("lastUpdateUser.id");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("lastUpdateUser.number");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("lastUpdateUser.name");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("lastUpdateTime");

		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("auditor.id");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("auditor.number");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("auditor.name");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("auditDate");

		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("number");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("name");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("billSate");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("bizDate");
		COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE.add("description");

		// objectBase
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("creator.id");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("creator.number");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("creator.name");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("createTime");

		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("lastUpdateUser.id");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("lastUpdateUser.number");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("lastUpdateUser.name");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("lastUpdateTime");

		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("auditor.id");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("auditor.number");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("auditor.name");
		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("auditDate");

		COMMON_PROPERTY_NAME_LIST_OBJECT_BASE.add("id");

		// coreBase
		COMMON_PROPERTY_NAME_LIST_CORE_BASE.add("id");

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_PROJECT, COMMON_PROPERTY_NAME_LIST_PROJECT);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_ORG_UNIT, COMMON_PROPERTY_NAME_LIST_ORG_UNIT);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_PERIOD, COMMON_PROPERTY_NAME_LIST_PERIOD);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_USER, COMMON_PROPERTY_NAME_LIST_USER);

		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_BILL_ENTRY_BASE, COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_FDC_BASE_DATA, COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA);

		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_FDC_TREE_BASE, COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_TREE_BASE, COMMON_PROPERTY_NAME_LIST_TREE_BASE);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_DATA_BASE, COMMON_PROPERTY_NAME_LIST_DATA_BASE);

		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_FDC_BILL, COMMON_PROPERTY_NAME_LIST_FDC_BILL);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_BASE_BILL, COMMON_PROPERTY_NAME_LIST_BASE_BILL);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_CORE_BILL_BASE, COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_OBJECT_BASE, COMMON_PROPERTY_NAME_LIST_OBJECT_BASE);
		COMMON_PROPERTY_NAME_LIST_MAP.put(BOS_TYPE_STR_CORE_BASE, COMMON_PROPERTY_NAME_LIST_CORE_BASE);
	}

}
