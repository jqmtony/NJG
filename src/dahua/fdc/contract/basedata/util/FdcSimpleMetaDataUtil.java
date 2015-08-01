/**
 * @(#)FdcSimpleMetaDataUtil.java 1.0 2014-3-19
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom.Element;

import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.util.StringUtils;

/**
 * 描述：房地产简单元数据工具类
 * <p>
 * 该工具类是对FdcMetaDataUtil的简单封装
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcSimpleMetaDataUtil {

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 模板_entity_完整名称
	 */
	public static final String TEMPLATE_ENTITY_FULL_NAME = "com.kingdee.eas.fdc.account.aim.app.AimCollectBill";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 取得元数据根路径
	 * 
	 * @return
	 */
	public static String getMetaDataRootPath() {
		return "W:/workspace/fdc600/eas_metas_60/metadata";
	}

	/**
	 * 取得临时元数据根路径
	 * 
	 * @return
	 */
	public static String getTempMetaDataRootPath() {
		return "D:/Temp/XML";
	}

	/**
	 * 取得实体文件完整名称
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static String getEntityFileFullName(String entityFullName) {
		return FdcMetaDataUtil.getEntityFileFullName(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * 取得实体文件XML根元素
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static Element getRootElement(String entityFullName) {
		return FdcMetaDataUtil.getRootElement(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * 取得拓展属性XML元素
	 * 
	 * @param entityFullName
	 * @param extendedPropertyKey
	 * @return
	 */
	public static Element getExtendedPropertyElement(String entityFullName, String extendedPropertyKey) {
		return FdcMetaDataUtil.getExtendedPropertyElement(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName,
				extendedPropertyKey);
	}

	/**
	 * 取得defaultF7Query拓展属性XML元素
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static Element getDefaultF7QueryElement(String entityFullName) {
		return FdcMetaDataUtil.getDefaultF7QueryElement(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * 取得defaultF7Query拓展属性XML元素_模板
	 * 
	 * @return
	 */
	public static Element getTemplate_defaultF7QueryElement() {
		return getDefaultF7QueryElement(FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME);
	}

	/**
	 * 附加defaultF7Query拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendDefaultF7Query(String matadataRootPath, String packagePerfix, boolean isRepairOld,
			boolean isSave) {
		Map map = new LinkedHashMap();

		if (!StringUtils.isEmpty(packagePerfix)) {
			Class baseClassz = FDCBill.class;
			String querySuffix = FdcMetaDataConstant.META_DATA_FILENAME_SUFFIX_QUERY;
			String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;

			map = FdcMetaDataUtil.appendDefaultF7Query(matadataRootPath, packagePerfix, baseClassz, querySuffix,
					templateEntityFullName, isRepairOld, isSave);
		}

		return map;
	}

	/**
	 * 附加defaultF7Query拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendDefaultF7Query(String matadataRootPath, String packagePerfix) {
		return appendDefaultF7Query(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * 附加defaultF7Query拓展属性
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendDefaultF7Query(String packagePerfix, boolean isSave) {
		return appendDefaultF7Query(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * 附加defaultF7Query拓展属性
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendDefaultF7Query(String packagePerfix) {
		return appendDefaultF7Query(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true);
	}

	/**
	 * 生成套打Query文件
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @param isCheckUIPrintQueryPK
	 * @return
	 */
	public static Map generatePrintQueryFile(String matadataRootPath, String packagePerfix, boolean isRepairOld,
			boolean isSave, boolean isCheckUIPrintQueryPK) {
		Map map = new LinkedHashMap();

		if (!StringUtils.isEmpty(packagePerfix)) {
			map = FdcMetaDataHelper.generatePrintQueryFile(matadataRootPath, packagePerfix, isRepairOld, isSave,
					isCheckUIPrintQueryPK);
		}

		return map;
	}

	/**
	 * 生成套打Query文件
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map generatePrintQueryFile(String matadataRootPath, String packagePerfix) {
		return generatePrintQueryFile(matadataRootPath, packagePerfix, false, true, false);
	}

	/**
	 * 生成套打Query文件
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map generatePrintQueryFile(String packagePerfix, boolean isSave) {
		return generatePrintQueryFile(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave, false);
	}

	/**
	 * 生成套打Query文件
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map generatePrintQueryFile(String packagePerfix) {
		return generatePrintQueryFile(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true, false);
	}

	/**
	 * 生成业务单元文件
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map generateBizUnitFile(String matadataRootPath, String packagePerfix) {
		return FdcMetaDataHelper.generateBizUnitFile(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * 生成业务单元文件
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map generateBizUnitFile(String packagePerfix, boolean isSave) {
		return FdcMetaDataHelper.generateBizUnitFile(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false,
				isSave);
	}

	/**
	 * 生成业务单元文件
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map generateBizUnitFile(String packagePerfix) {
		return generateBizUnitFile(packagePerfix, true);
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * 附加orgType拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendOrgType(String matadataRootPath, String packagePerfix, boolean isRepairOld, boolean isSave) {
		Map map = new LinkedHashMap();

		if (!StringUtils.isEmpty(packagePerfix)) {
			Class baseClassz = FDCBill.class;
			String orgTypeValue = "Transport";
			String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;

			map = FdcMetaDataUtil.appendOrgType(matadataRootPath, packagePerfix, baseClassz, orgTypeValue,
					templateEntityFullName, isRepairOld, isSave);
		}

		return map;
	}

	/**
	 * 附加orgType拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendOrgType(String matadataRootPath, String packagePerfix) {
		return appendOrgType(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * 附加orgType拓展属性
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendOrgType(String packagePerfix, boolean isSave) {
		return appendOrgType(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * 附加orgType拓展属性
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendOrgType(String packagePerfix) {
		return appendOrgType(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true);
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * 附加crNumberEdit拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendCRNumberEdit(String matadataRootPath, String packagePerfix, boolean isRepairOld,
			boolean isSave) {
		Map map = new LinkedHashMap();

		if (!StringUtils.isEmpty(packagePerfix)) {
			Class baseClassz = FDCBill.class;
			String crNumberEditValue = "true";
			String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;

			map = FdcMetaDataUtil.appendCRNumberEdit(matadataRootPath, packagePerfix, baseClassz, crNumberEditValue,
					templateEntityFullName, isRepairOld, isSave);
		}

		return map;
	}

	/**
	 * 附加crNumberEdit拓展属性
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendCRNumberEdit(String matadataRootPath, String packagePerfix) {
		return appendCRNumberEdit(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * 附加crNumberEdit拓展属性
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendCRNumberEdit(String packagePerfix, boolean isSave) {
		return appendCRNumberEdit(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * 附加crNumberEdit拓展属性
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendCRNumberEdit(String packagePerfix) {
		return appendCRNumberEdit(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true);
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

}
