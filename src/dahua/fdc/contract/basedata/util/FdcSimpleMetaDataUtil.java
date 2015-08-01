/**
 * @(#)FdcSimpleMetaDataUtil.java 1.0 2014-3-19
 * @author ����
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
 * ���������ز���Ԫ���ݹ�����
 * <p>
 * �ù������Ƕ�FdcMetaDataUtil�ļ򵥷�װ
 * 
 * @author ����
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
	 * ģ��_entity_��������
	 */
	public static final String TEMPLATE_ENTITY_FULL_NAME = "com.kingdee.eas.fdc.account.aim.app.AimCollectBill";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ȡ��Ԫ���ݸ�·��
	 * 
	 * @return
	 */
	public static String getMetaDataRootPath() {
		return "W:/workspace/fdc600/eas_metas_60/metadata";
	}

	/**
	 * ȡ����ʱԪ���ݸ�·��
	 * 
	 * @return
	 */
	public static String getTempMetaDataRootPath() {
		return "D:/Temp/XML";
	}

	/**
	 * ȡ��ʵ���ļ���������
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static String getEntityFileFullName(String entityFullName) {
		return FdcMetaDataUtil.getEntityFileFullName(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * ȡ��ʵ���ļ�XML��Ԫ��
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static Element getRootElement(String entityFullName) {
		return FdcMetaDataUtil.getRootElement(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * ȡ����չ����XMLԪ��
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
	 * ȡ��defaultF7Query��չ����XMLԪ��
	 * 
	 * @param entityFullName
	 * @return
	 */
	public static Element getDefaultF7QueryElement(String entityFullName) {
		return FdcMetaDataUtil.getDefaultF7QueryElement(FdcSimpleMetaDataUtil.getMetaDataRootPath(), entityFullName);
	}

	/**
	 * ȡ��defaultF7Query��չ����XMLԪ��_ģ��
	 * 
	 * @return
	 */
	public static Element getTemplate_defaultF7QueryElement() {
		return getDefaultF7QueryElement(FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME);
	}

	/**
	 * ����defaultF7Query��չ����
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
	 * ����defaultF7Query��չ����
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendDefaultF7Query(String matadataRootPath, String packagePerfix) {
		return appendDefaultF7Query(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * ����defaultF7Query��չ����
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendDefaultF7Query(String packagePerfix, boolean isSave) {
		return appendDefaultF7Query(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * ����defaultF7Query��չ����
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendDefaultF7Query(String packagePerfix) {
		return appendDefaultF7Query(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true);
	}

	/**
	 * �����״�Query�ļ�
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
	 * �����״�Query�ļ�
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map generatePrintQueryFile(String matadataRootPath, String packagePerfix) {
		return generatePrintQueryFile(matadataRootPath, packagePerfix, false, true, false);
	}

	/**
	 * �����״�Query�ļ�
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map generatePrintQueryFile(String packagePerfix, boolean isSave) {
		return generatePrintQueryFile(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave, false);
	}

	/**
	 * �����״�Query�ļ�
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map generatePrintQueryFile(String packagePerfix) {
		return generatePrintQueryFile(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, true, false);
	}

	/**
	 * ����ҵ��Ԫ�ļ�
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map generateBizUnitFile(String matadataRootPath, String packagePerfix) {
		return FdcMetaDataHelper.generateBizUnitFile(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * ����ҵ��Ԫ�ļ�
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
	 * ����ҵ��Ԫ�ļ�
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
	 * ����orgType��չ����
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
	 * ����orgType��չ����
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendOrgType(String matadataRootPath, String packagePerfix) {
		return appendOrgType(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * ����orgType��չ����
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendOrgType(String packagePerfix, boolean isSave) {
		return appendOrgType(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * ����orgType��չ����
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
	 * ����crNumberEdit��չ����
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
	 * ����crNumberEdit��չ����
	 * 
	 * @param matadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendCRNumberEdit(String matadataRootPath, String packagePerfix) {
		return appendCRNumberEdit(matadataRootPath, packagePerfix, false, true);
	}

	/**
	 * ����crNumberEdit��չ����
	 * 
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendCRNumberEdit(String packagePerfix, boolean isSave) {
		return appendCRNumberEdit(FdcSimpleMetaDataUtil.getMetaDataRootPath(), packagePerfix, false, isSave);
	}

	/**
	 * ����crNumberEdit��չ����
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
