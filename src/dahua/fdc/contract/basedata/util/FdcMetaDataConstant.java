/**
 * @(#)FdcMetaDataConstant.java 1.0 2014-3-19
 * @author ����
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
 * ���������ز�Ԫ���ݳ���
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcMetaDataConstant {

	/**
	 * Ԫ����_��չ��_entity
	 */
	public static final String META_DATA_EXTNAME_ENTITY = "entity";

	/**
	 * Ԫ����_��չ��_query
	 */
	public static final String META_DATA_EXTNAME_QUERY = "query";

	/**
	 * Ԫ����_��չ��_function
	 */
	public static final String META_DATA_EXTNAME_FUNCTION = "function";

	/**
	 * Ԫ����_��չ��_ui
	 */
	public static final String META_DATA_EXTNAME_UI = "ui";

	/**
	 * Ԫ����_��չ��_bizunit
	 */
	public static final String META_DATA_EXTNAME_BIZUNIT = "bizunit";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ��ǩ_key
	 */
	public static final String TAG_RESOURCE = "resource";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ������_key
	 */
	public static final String ATTRIBUTE_NAME_KEY = "key";

	/**
	 * ������_locale
	 */
	public static final String ATTRIBUTE_NAME_LOCALE = "locale";

	/**
	 * ������_value
	 */
	public static final String ATTRIBUTE_NAME_VALUE = "value";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * Ԫ����_�ļ���_��׺_query
	 */
	public static final String META_DATA_FILENAME_SUFFIX_QUERY = "Query";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ��Դ_����_��չ����
	 */
	public static final String RS_TYPE_EXTENDED_PROPERTY = "extendedProperty";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * defaultF7Query��
	 */
	public static final String DEFAULT_F7_QUERY_KEY = "defaultF7Query";

	/**
	 * defaultF7Query�������ĺ�׺��
	 */
	public static final String DEFAULT_F7_QUERY_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "."
			+ DEFAULT_F7_QUERY_KEY;

	/**
	 * defaultF7Query������ģʽ
	 */
	public static final String DEFAULT_F7_QUERY_FULL_KEY_PATTERN = "entityObject[{0}]."
			+ DEFAULT_F7_QUERY_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * orgType��
	 */
	public static final String ORG_TYPE_KEY = "OrgType";

	/**
	 * orgType�������ĺ�׺��
	 */
	public static final String ORG_TYPE_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "." + ORG_TYPE_KEY;

	/**
	 * orgType������ģʽ
	 */
	public static final String ORG_TYPE_FULL_KEY_PATTERN = "entityObject[{0}]." + ORG_TYPE_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ��֯����Set
	 */
	public static final Set ORG_TYPE_SET = new LinkedHashSet(Arrays.asList(new String[] { "NONE", "Admin", "Company",
			"Sale", "Purchase", "Storage", "CostCenter", "ProfitCenter", "ControlUnit", "UnionDebt", "Transport" }));

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * crNumberEdit��
	 */
	public static final String CR_NUMBER_EDIT_KEY = "CRNumberEdit";

	/**
	 * crNumberEdit�������ĺ�׺��
	 */
	public static final String CR_NUMBER_EDIT_FULL_KEY_SUFFIX = RS_TYPE_EXTENDED_PROPERTY + "." + CR_NUMBER_EDIT_KEY;

	/**
	 * crNumberEdit������ģʽ
	 */
	public static final String CR_NUMBER_EDIT_FULL_KEY_PATTERN = "entityObject[{0}]." + CR_NUMBER_EDIT_FULL_KEY_SUFFIX;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ����������ɱ�����޸�Set
	 */
	public static final Set CR_NUMBER_EDIT_SET = new LinkedHashSet(Arrays.asList(new String[] { "false", "true" }));

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * BOS��������_�ַ���_������Ŀ
	 */
	public static final String BOS_TYPE_STR_PROJECT = "C82BC724";
	/**
	 * BOS��������_�ַ���_��֯��Ԫ
	 */
	public static final String BOS_TYPE_STR_ORG_UNIT = "9127AC92";
	/**
	 * BOS��������_�ַ���_�ڼ�
	 */
	public static final String BOS_TYPE_STR_PERIOD = "82388C4C";
	/**
	 * BOS��������_�ַ���_�û�
	 */
	public static final String BOS_TYPE_STR_USER = "13B7DE7F";

	// ////////////////////////////////////

	/**
	 * BOS��������_�ַ���_����ʵ�����
	 */
	public static final String BOS_TYPE_STR_BILL_ENTRY_BASE = "0DDD7E9A";
	/**
	 * BOS��������_�ַ���_������������
	 */
	public static final String BOS_TYPE_STR_FDC_BASE_DATA = "E88F90EE";

	/**
	 * BOS��������_�ַ���_�������νṹ����
	 */
	public static final String BOS_TYPE_STR_FDC_TREE_BASE = "A1C9E662";
	/**
	 * BOS��������_�ַ���_���νṹ����
	 */
	public static final String BOS_TYPE_STR_TREE_BASE = "0A9C2851";
	/**
	 * BOS��������_�ַ���_���ݻ���
	 */
	public static final String BOS_TYPE_STR_DATA_BASE = "229FBE5D";

	// ////////////////////////////////////

	/**
	 * BOS��������_�ַ���_��������
	 */
	public static final String BOS_TYPE_STR_FDC_BILL = "C13B7C5A";
	/**
	 * BOS��������_�ַ���_��������
	 */
	public static final String BOS_TYPE_STR_BASE_BILL = "122FD3CD";
	/**
	 * BOS��������_�ַ���_���ĵ��ݻ���
	 */
	public static final String BOS_TYPE_STR_CORE_BILL_BASE = "56C04FF9";
	/**
	 * BOS��������_�ַ���_�������
	 */
	public static final String BOS_TYPE_STR_OBJECT_BASE = "0B2876F2";
	/**
	 * BOS��������_�ַ���_���Ļ���
	 */
	public static final String BOS_TYPE_STR_CORE_BASE = "3B2DDC00";

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * BOS��������_������Ŀ
	 */
	public static final BOSObjectType BOS_TYPE_PROJECT = BOSObjectType.create(BOS_TYPE_STR_PROJECT);
	/**
	 * BOS��������_��֯��Ԫ
	 */
	public static final BOSObjectType BOS_TYPE_ORG_UNIT = BOSObjectType.create(BOS_TYPE_STR_ORG_UNIT);
	/**
	 * BOS��������_�ڼ�
	 */
	public static final BOSObjectType BOS_TYPE_PERIOD = BOSObjectType.create(BOS_TYPE_STR_PERIOD);
	/**
	 * BOS��������_�û�
	 */
	public static final BOSObjectType BOS_TYPE_USER = BOSObjectType.create(BOS_TYPE_STR_USER);

	// ////////////////////////////////////

	/**
	 * BOS��������_����ʵ�����
	 */
	public static final BOSObjectType BOS_TYPE_BILL_ENTRY_BASE = BOSObjectType.create(BOS_TYPE_STR_BILL_ENTRY_BASE);
	/**
	 * BOS��������_������������
	 */
	public static final BOSObjectType BOS_TYPE_FDC_BASE_DATA = BOSObjectType.create(BOS_TYPE_STR_FDC_BASE_DATA);

	/**
	 * BOS��������_�������νṹ����
	 */
	public static final BOSObjectType BOS_TYPE_FDC_TREE_BASE = BOSObjectType.create(BOS_TYPE_STR_FDC_TREE_BASE);
	/**
	 * BOS��������_���νṹ����
	 */
	public static final BOSObjectType BOS_TYPE_TREE_BASE = BOSObjectType.create(BOS_TYPE_STR_TREE_BASE);
	/**
	 * BOS��������_���ݻ���
	 */
	public static final BOSObjectType BOS_TYPE_DATA_BASE = BOSObjectType.create(BOS_TYPE_STR_DATA_BASE);

	// ////////////////////////////////////

	/**
	 * BOS��������_��������
	 */
	public static final BOSObjectType BOS_TYPE_FDC_BILL = BOSObjectType.create(BOS_TYPE_STR_FDC_BILL);
	/**
	 * BOS��������_��������
	 */
	public static final BOSObjectType BOS_TYPE_BASE_BILL = BOSObjectType.create(BOS_TYPE_STR_BASE_BILL);
	/**
	 * BOS��������_���ĵ��ݻ���
	 */
	public static final BOSObjectType BOS_TYPE_CORE_BILL_BASE = BOSObjectType.create(BOS_TYPE_STR_CORE_BILL_BASE);
	/**
	 * BOS��������_�������
	 */
	public static final BOSObjectType BOS_TYPE_OBJECT_BASE = BOSObjectType.create(BOS_TYPE_STR_OBJECT_BASE);
	/**
	 * BOS��������_���Ļ���
	 */
	public static final BOSObjectType BOS_TYPE_CORE_BASE = BOSObjectType.create(BOS_TYPE_STR_CORE_BASE);

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * Entity����_BaseBill
	 */
	public static final EntityObjectInfo ENTITY_OBJECT_INFO_BASE_BILL;

	/**
	 * Entity����_EcBill
	 */
	public static final EntityObjectInfo ENTITY_OBJECT_INFO_FDC_BILL;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ���Լ���_BaseBill
	 */
	public static final PropertyCollection PROPERTY_COLLECTION_BASE_BILL;

	/**
	 * ���Լ���_EcBill
	 */
	public static final PropertyCollection PROPERTY_COLLECTION_FDC_BILL;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * �������б�_BaseBill
	 */
	public static final List PROPERTY_NAME_LIST_BASE_BILL;

	/**
	 * �������б�_EcBill
	 */
	public static final List PROPERTY_NAME_LIST_FDC_BILL;

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ����_������_�б�_������Ŀ
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_PROJECT = new ArrayList();
	/**
	 * ����_������_�б�_��֯��Ԫ
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_ORG_UNIT = new ArrayList();
	/**
	 * ����_������_�б�_�ڼ�
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_PERIOD = new ArrayList();
	/**
	 * ����_������_�б�_�û�
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_USER = new ArrayList();

	// ////////////////////////////////////

	/**
	 * ����_������_�б�_����ʵ�����
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE = new ArrayList();
	/**
	 * ����_������_�б�_������������
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA = new ArrayList();

	/**
	 * ����_������_�б�_�������νṹ����
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE = new ArrayList();
	/**
	 * ����_������_�б�_���νṹ����
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_TREE_BASE = new ArrayList();
	/**
	 * ����_������_�б�_���ݻ���
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_DATA_BASE = new ArrayList();

	// ////////////////////////////////////

	/**
	 * ����_������_�б�_��������
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_FDC_BILL = new ArrayList();
	/**
	 * ����_������_�б�_��������
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_BASE_BILL = new ArrayList();
	/**
	 * ����_������_�б�_���ĵ��ݻ���
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_CORE_BILL_BASE = new ArrayList();
	/**
	 * ����_������_�б�_�������
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_OBJECT_BASE = new ArrayList();
	/**
	 * ����_������_�б�_���Ļ���
	 */
	public static final List COMMON_PROPERTY_NAME_LIST_CORE_BASE = new ArrayList();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������б�_ӳ�� <br>
	 * ����key���BosType�ַ�����value���������_�б�
	 */
	public static final Map COMMON_PROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	static {
		// ����bosTypeȡ�ö�Ӧ��ʵ��
		ENTITY_OBJECT_INFO_BASE_BILL = MetaDataLoader.getEntity(null, BOS_TYPE_BASE_BILL);
		ENTITY_OBJECT_INFO_FDC_BILL = MetaDataLoader.getEntity(null, BOS_TYPE_FDC_BILL);

		// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)��ʹ���˻�����ԣ���Studio�в�Ӧʹ��
		PROPERTY_COLLECTION_BASE_BILL = ENTITY_OBJECT_INFO_BASE_BILL.getInheritedNoDuplicatedPropertiesRuntime();
		PROPERTY_COLLECTION_FDC_BILL = ENTITY_OBJECT_INFO_FDC_BILL.getInheritedNoDuplicatedPropertiesRuntime();

		// ȡ��������List
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

		// BillEntryBase������
		// billEntryBase
		COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_BILL_ENTRY_BASE.add("seq");

		// EcBaseData������
		// ecBaseData
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("id");
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("number");
		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("name");

		COMMON_PROPERTY_NAME_LIST_FDC_BASE_DATA.add("isEnabled");

		// DataBase������
		// ecTreeBase
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("id");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("number");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("name");

		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("longNumber");
		// COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("displayName");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("level");
		COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("isLeaf");

		// COMMON_PROPERTY_NAME_LIST_FDC_TREE_BASE.add("isEnabled");
		// EC���νṹ�淶������ǿ������Ϊparent
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

		// BaseBill������
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
