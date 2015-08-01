/**
 * @(#)FdcMetaDataHelper.java 1.0 2014-9-16
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.uiframe.SysMenuItemCollection;
import com.kingdee.eas.base.uiframe.SysMenuItemFactory;
import com.kingdee.eas.base.uiframe.SysMenuItemInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FdcListUIComponent;
import com.kingdee.eas.fdc.basedata.client.FDCBillListUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;

/**
 * ���������ز� Ԫ��������(ֻ���ڿͻ���)
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-9-16
 * @version 1.0, 2014-9-16
 * @see
 * @since JDK1.4
 */
public class FdcMetaDataHelper {

	/**
	 * ת����ѯPKӳ��
	 * <p>
	 * ����key����ΪListUI��Mapת��Ϊkey����ΪClass��Map
	 * 
	 * @param listUIMap
	 * @return
	 */
	public static Map translateQueryPKMap(Map listUIMap) {
		Map listUIClassMap = new LinkedHashMap();
	
		if (FdcMapUtil.isNotEmpty(listUIMap)) {
			Set listUISet = listUIMap.keySet();
			ListUI listUI = null;
			IMetaDataPK queryPK = null;
			Class listUIClass = null;
	
			for (Iterator iterator = listUISet.iterator(); iterator.hasNext();) {
				listUI = (ListUI) iterator.next();
				queryPK = (IMetaDataPK) listUIMap.get(listUI);
				listUIClass = listUI.getClass();
	
				listUIClassMap.put(listUIClass, queryPK);
			}
		}
	
		return listUIClassMap;
	}

	/**
	 * ȡ��ListUI��Ӧ������ѯPKӳ��
	 * <p>
	 * ����key���ListUIʵ����value���ListUI��Ӧ��MainQueryPK
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map getMainQueryPKMap(String packagePerfix) {
		return FdcMetaDataHelper.getQueryPKMap(packagePerfix, false);
	}

	/**
	 * ȡ��ListUI��Ӧ���״��ѯPKӳ��
	 * <p>
	 * ����key���ListUIʵ����value���ListUI��Ӧ��PrintQueryPK
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map getPrintQueryPKMap(String packagePerfix) {
		return FdcMetaDataHelper.getQueryPKMap(packagePerfix, true);
	}

	/**
	 * ȡ��ListUI��Ӧ�Ĳ�ѯPKӳ��
	 * <p>
	 * ����key���ListUIʵ����value���ListUI��Ӧ��QueryPK
	 * 
	 * @param packagePerfix
	 * @param isPrintQuery
	 *            �Ƿ����״��ѯ
	 * @return
	 */
	private static Map getQueryPKMap(String packagePerfix, boolean isPrintQuery) {
		Map listUIMap = new LinkedHashMap();
	
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		FilterItemCollection filterCols = filter.getFilterItems();
		filterCols.add(new FilterItemInfo("uiClassName", packagePerfix + "%", CompareType.LIKE));
		SorterItemCollection sorter = view.getSorter();
		sorter.add(new SorterItemInfo("longNumber"));
	
		// ��ѯ�˵�
		SysMenuItemCollection cols = null;
		try {
			cols = SysMenuItemFactory.getRemoteInstance().getSysMenuItemCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			Map otherSysMenuItemMap = new LinkedHashMap();
			SysMenuItemInfo sysMenuItemInfo = null;
			String uiClassName = null;
			Class uiClass = null;
			Object ui = null;
			ListUI listUI = null;
			IMetaDataPK queryPK = null;
	
			Set listUIClassNameSet = new HashSet();
			for (Iterator iterator = cols.iterator(); iterator.hasNext();) {
				sysMenuItemInfo = (SysMenuItemInfo) iterator.next();
				uiClassName = sysMenuItemInfo.getUiClassName();
				// ������ListUI��β��UI
				if (!uiClassName.endsWith("ListUI")) {
					otherSysMenuItemMap.put(sysMenuItemInfo.getDisplayName(), uiClassName);
					continue;
				}
				try {
					if (listUIClassNameSet.contains(uiClassName)) {
						continue;
					}
	
					uiClass = Class.forName(uiClassName);
					ui = uiClass.newInstance();
					if (ui instanceof FDCBillListUI) {
						listUI = (FDCBillListUI) ui;
	
						if (!isPrintQuery) {
							// ȡ������ѯPK
							queryPK = listUI.getMainQueryPK();
						} else {
							// ȡ���״��ѯPK
							queryPK = FDCClientHelper.getPrintQueryPK(listUI);
						}
	
						listUIMap.put(listUI, queryPK);
						listUIClassNameSet.add(uiClassName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			if (!otherSysMenuItemMap.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("������ListUI��β��UI�б�");
				MapUtils.debugPrint(System.out, null, otherSysMenuItemMap);
				System.out.println("size = " + otherSysMenuItemMap.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
		}
	
		return listUIMap;
	}

	/**
	 * ȡ��ListUI��Ӧ������ѯPK�б�
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static List getMainQueryPKList(String packagePerfix) {
		List queryPKList = new ArrayList();
	
		// ȡ��ListUI��Ӧ������ѯPKӳ��
		Map listUIMap = getMainQueryPKMap(packagePerfix);
		queryPKList.addAll(listUIMap.values());
	
		return queryPKList;
	}

	/**
	 * ȡ��ListUI��Ӧ���״��ѯPK�б�
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static List getPrintQueryPKList(String packagePerfix) {
		List queryPKList = new ArrayList();
	
		// ȡ��ListUI��Ӧ���״��ѯPKӳ��
		Map listUIMap = getPrintQueryPKMap(packagePerfix);
		queryPKList.addAll(listUIMap.values());
	
		return queryPKList;
	}

	/**
	 * ȡ�ò�ƥ��Ĳ�ѯPKMap
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static Map getMainQueryPKMapOfNotMatch(String packagePerfix) {
		Map listUIMapOfNotMatch = new LinkedHashMap();
		Map listUIMapOfNoEntity = new LinkedHashMap();
	
		// ȡ��ListUI��Ӧ������ѯPKӳ��
		Map listUIMapAll_main = getMainQueryPKMap(packagePerfix);
		// ȡ��ListUI��Ӧ���״�PKӳ��
		Map listUIMapAll_print = getPrintQueryPKMap(packagePerfix);
		if (FdcMapUtil.isNotEmpty(listUIMapAll_main)) {
			Set listUISet = listUIMapAll_main.keySet();
			ListUI listUI = null;
			Class listUIClass = null;
			String listUIName = null;
			String listUIEntityName = null;
			IMetaDataPK mainQueryPK = null;
			String mainQueryFullName = null;
			IMetaDataPK printQueryPK = null;
			String printQueryName = null;
			String printQueryFullName = null;
			String printQueryEntityName = null;
	
			// ת����ѯPKӳ��
			Map listUIClassMapAll_main = translateQueryPKMap(listUIMapAll_main);
			Map listUIClassMapAll_print = translateQueryPKMap(listUIMapAll_print);
	
			String key = null;
			String value = null;
	
			for (Iterator iterator = listUISet.iterator(); iterator.hasNext();) {
				listUI = (ListUI) iterator.next();
				listUIClass = listUI.getClass();
				listUIName = listUIClass.getName();
	
				mainQueryPK = (IMetaDataPK) listUIClassMapAll_main.get(listUIClass);
				mainQueryFullName = mainQueryPK.getFullName();
				printQueryPK = (IMetaDataPK) listUIClassMapAll_print.get(listUIClass);
				if (null != printQueryPK) {
					printQueryFullName = printQueryPK.getFullName();
				} else {
					printQueryFullName = null;
				}
	
				key = listUIName;
				value = "[mainQueryFullName��" + mainQueryFullName + ", printQueryFullName��" + printQueryFullName + "]";
	
				if (null == printQueryPK) {
					listUIMapOfNotMatch.put(key, value);
					continue;
				}
	
				String modelPackageName_listUI = FDCClientHelper.getModelPackageName(listUI);
				String modelPackageName_printQuery = FdcMetaDataUtil.getModelPackageName(printQueryPK, true);
	
				if (!modelPackageName_listUI.equals(modelPackageName_printQuery)) {
					listUIMapOfNotMatch.put(key, value);
				} else {
					// ����CoreUIȡ�ö�Ӧ��EntityObjectInfo('ʵ�������Ϣ')����
					listUIEntityName = FDCClientHelper.getEntityName(listUI);
					if (null == listUIEntityName) {
						listUIMapOfNoEntity.put(key, value);
						continue;
					}
	
					printQueryName = printQueryPK.getName();
					int printQueryNameLength = printQueryName.length();
					int suffixLength = 0;
					if (printQueryName.endsWith("PrintQuery")) {
						suffixLength = "PrintQuery".length();
						printQueryEntityName = printQueryName.substring(0, printQueryNameLength - suffixLength);
					} else if (printQueryName.endsWith("TDQuery")) {
						suffixLength = "TDQuery".length();
						printQueryEntityName = printQueryName.substring(0, printQueryNameLength - suffixLength);
					} else if (printQueryName.startsWith("Print") && printQueryName.endsWith("Query")
							&& !printQueryName.endsWith("PrintQuery")) {
						suffixLength = "Query".length();
						printQueryEntityName = printQueryName.substring("Print".length(), printQueryNameLength
								- suffixLength);
					} else if (printQueryName.startsWith("TD") && printQueryName.endsWith("Query")
							&& !printQueryName.endsWith("TDQuery")) {
						suffixLength = "Query".length();
						printQueryEntityName = printQueryName.substring("TD".length(), printQueryNameLength
								- suffixLength);
					}
	
					// ListUI��Ӧ��ʵ�����ƺ�Query��Ӧ��ʵ�����Ʋ�ƥ��
					if (!listUIEntityName.equals(printQueryEntityName)) {
						listUIMapOfNotMatch.put(key, value);
					}
				}
			}
	
			if (!listUIMapOfNotMatch.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("��ƥ��Ĳ�ѯPK��");
				MapUtils.debugPrint(System.out, null, listUIMapOfNotMatch);
				System.out.println("size = " + listUIMapOfNotMatch.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
			if (!listUIMapOfNoEntity.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("û��ʵ��Ĳ�ѯPK��");
				MapUtils.debugPrint(System.out, null, listUIMapOfNoEntity);
				System.out.println("size = " + listUIMapOfNoEntity.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
		}
	
		return listUIMapOfNotMatch;
	}

	/**
	 * ȡ��UI�����Ϣ
	 * 
	 * @param packagePerfix
	 * @return
	 */
	public static String getUICheckMsg(String packagePerfix) {
		StringBuffer stringBuffer = new StringBuffer();
	
		Map listUIMapOfNoEntity = new LinkedHashMap();
	
		// ȡ��ListUI��Ӧ������ѯPKӳ��
		Map listUIMapAll_main = getMainQueryPKMap(packagePerfix);
		if (FdcMapUtil.isNotEmpty(listUIMapAll_main)) {
			// ת����ѯPKӳ��
			Map listUIClassMapAll_main = translateQueryPKMap(listUIMapAll_main);
	
			Set listUISet = listUIMapAll_main.keySet();
			ListUI listUI = null;
			Class listUIClass = null;
			String listUIFullName = null;
			IMetaDataPK mainQueryPK = null;
			String mainQueryFullName = null;
			IMetaDataPK printQueryPK = null;
			String printQueryFullName = null;
			String tdFileName = null;
	
			String modelPackageName = null;
			String entityName = null;
	
			String template_listUIFullName = "{0}.client.{1}ListUI";
			String template_mainQueryFullName = "{0}.app.{1}Query";
			String template_printQueryFullName = "{0}.app.{1}PrintQuery";
			String template_tdFileName = "{0}/{1}";
	
			String listUIFullName_stand = null;
			String mainQueryFullName_stand = null;
			String printQueryFullName_stand = null;
			String tdFileName_stand = null;
	
			String template_msg = "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}";
			String msg = null;
	
			String msg_head = null;
			msg_head = template_msg.replaceAll("\\{0\\}", "ListUI");
			msg_head = msg_head.replaceAll("\\{1\\}", "EntityName");
			msg_head = msg_head.replaceAll("\\{2\\}", "<--->");
			msg_head = msg_head.replaceAll("\\{3\\}", "ListUI");
			msg_head = msg_head.replaceAll("\\{4\\}", "��׼ListUI����");
			msg_head = msg_head.replaceAll("\\{5\\}", "MainQuery");
			msg_head = msg_head.replaceAll("\\{6\\}", "��׼MainQuery����");
			msg_head = msg_head.replaceAll("\\{7\\}", "PrintQuery");
			msg_head = msg_head.replaceAll("\\{8\\}", "��׼PrintQuery����");
			msg_head = msg_head.replaceAll("\\{9\\}", "TDFileName");
			msg_head = msg_head.replaceAll("\\{10\\}", "��׼TDFileName����");
			stringBuffer.append(msg_head).append("\n");
	
			boolean isStandListUIFullName = false;
			boolean isStandMainQueryFullName = false;
			boolean isStandPrintQueryFullName = false;
			boolean isStandTDFileName = false;
	
			String key = null;
			String value = null;
			for (Iterator iterator = listUISet.iterator(); iterator.hasNext();) {
				listUI = (ListUI) iterator.next();
				listUIClass = listUI.getClass();
				listUIFullName = listUIClass.getName();
	
				mainQueryPK = (IMetaDataPK) listUIClassMapAll_main.get(listUIClass);
				if (null != mainQueryPK) {
					mainQueryFullName = mainQueryPK.getFullName();
				} else {
					mainQueryFullName = "null";
				}
				// ȡ���״��ѯPK
				printQueryPK = FDCClientHelper.getPrintQueryPK(listUI);
				if (null != printQueryPK) {
					printQueryFullName = printQueryPK.getFullName();
				} else {
					printQueryFullName = "null";
				}
				// ȡ���״�ģ���ļ���
				tdFileName = FDCClientHelper.getTDFileName(listUI);
				if (null == tdFileName) {
					tdFileName = "null";
				}
	
				// ȡ��UI��Ӧ��model����
				modelPackageName = FDCClientHelper.getModelPackageName(listUI);
				// ȡ�á�ƥ��ʵ������
				entityName = FDCClientHelper.getMatchEntityName(listUI);
				if (null == entityName) {
					key = listUIFullName;
					value = "[" + mainQueryFullName + ", " + printQueryFullName + ", " + tdFileName + "]";
					listUIMapOfNoEntity.put(key, value);
					continue;
				}
	
				// ȡ�ñ�׼ListUI����MainQuery����PrintQuery����TDFileName��
				listUIFullName_stand = template_listUIFullName.replaceAll("\\{0\\}", modelPackageName).replaceAll(
						"\\{1\\}", entityName);
				mainQueryFullName_stand = template_mainQueryFullName.replaceAll("\\{0\\}", modelPackageName)
						.replaceAll("\\{1\\}", entityName);
				printQueryFullName_stand = template_printQueryFullName.replaceAll("\\{0\\}", modelPackageName)
						.replaceAll("\\{1\\}", entityName);
				// �״�ģ���ļ����Ƚ�����
				tdFileName_stand = template_tdFileName.replaceAll("\\{0\\}", modelPackageName).replaceAll("\\{1\\}",
						entityName);
				tdFileName_stand = tdFileName_stand.replaceAll(FDCConstants.EAS_PAKAGE_NAME, "");
				tdFileName_stand = tdFileName_stand.replaceAll("\\.", "/");
				tdFileName_stand = FDCConstants.TD_FILE_NAME_PREFIX + tdFileName_stand;
	
				// �Ƿ��Ǳ�׼ListUI����MainQuery����PrintQuery����TDFileName��
				isStandListUIFullName = listUIFullName_stand.equals(listUIFullName) ? true : false;
				isStandMainQueryFullName = mainQueryFullName_stand.equals(mainQueryFullName) ? true : false;
				isStandPrintQueryFullName = printQueryFullName_stand.equals(printQueryFullName) ? true : false;
				isStandTDFileName = tdFileName_stand.equals(tdFileName) ? true : false;
	
				msg = template_msg.replaceAll("\\{0\\}", listUIFullName);
				msg = msg.replaceAll("\\{1\\}", entityName);
				msg = msg.replaceAll("\\{2\\}", "");
				msg = msg.replaceAll("\\{3\\}", listUIFullName);
				msg = msg.replaceAll("\\{4\\}", isStandListUIFullName ? "" : listUIFullName_stand);
				msg = msg.replaceAll("\\{5\\}", mainQueryFullName);
				msg = msg.replaceAll("\\{6\\}", isStandMainQueryFullName ? "" : mainQueryFullName_stand);
				msg = msg.replaceAll("\\{7\\}", printQueryFullName);
				msg = msg.replaceAll("\\{8\\}", isStandPrintQueryFullName ? "" : printQueryFullName_stand);
				msg = msg.replaceAll("\\{9\\}", tdFileName);
				msg = msg.replaceAll("\\{10\\}", isStandTDFileName ? "" : tdFileName_stand);
	
				stringBuffer.append(msg).append("\n");
			}
	
			if (stringBuffer.length() > 0) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("UI�����Ϣ��");
				System.out.println(stringBuffer);
	
				System.out.println("===========================================");
				System.out.println();
			}
			if (!listUIMapOfNoEntity.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("û��ʵ���ListUI��");
				MapUtils.debugPrint(System.out, null, listUIMapOfNoEntity);
				System.out.println("size = " + listUIMapOfNoEntity.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
		}
	
		return stringBuffer.toString();
	}

	/**
	 * �����״�Query�ļ�
	 * 
	 * @param metadataRootPath
	 *            Ԫ���ݸ�·��
	 * @param packagePerfix
	 *            ��ǰ׺
	 * @param isRepairOld
	 *            �Ƿ��滻���еľ��ļ�
	 * @param isSave
	 *            �Ƿ񱣴�
	 * @param isCheckUIPrintQueryPK
	 *            �Ƿ���UI��Ӧ��PrintQuery
	 * @return
	 */
	public static Map generatePrintQueryFile(String metadataRootPath, String packagePerfix, boolean isRepairOld,
			boolean isSave, boolean isCheckUIPrintQueryPK) {
		if (isCheckUIPrintQueryPK) {
			return FdcMetaDataHelper.generatePrintQueryFile_checkPQ(metadataRootPath, packagePerfix, isRepairOld, isSave);
		} else {
			return FdcMetaDataHelper.generatePrintQueryFile_notCheckPQ(metadataRootPath, packagePerfix, isRepairOld, isSave);
		}
	}

	/**
	 * �����״�Query�ļ�_���UI��Ӧ��PrintQuery
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	private static Map generatePrintQueryFile_checkPQ(String metadataRootPath, String packagePerfix,
			boolean isRepairOld, boolean isSave) {
		Map map = new LinkedHashMap();
	
		// ȡ��ListUI��Ӧ������ѯӳ��
		Map mainQueryPKMap = getMainQueryPKMap(packagePerfix);
		if (FdcMapUtil.isEmpty(mainQueryPKMap)) {
			return map;
		}
	
		IMetaDataPK mainQueryPK = null;
		IMetaDataPK printQueryPK = null;
	
		String mainQueryName = null;
		String printQueryName = null;
		String mainQueryFullName = null;
		String mainQueryFileFullName = null;
		String printQueryFullName = null;
		String printQueryFileFullName = null;
	
		File mainQueryFile = null;
		File printQueryFile = null;
		List mainQueryFileLineList = new ArrayList();
		List printQueryFileLineList = new ArrayList();
		String mainQueryLine = null;
		String printQueryLine = null;
		StringBuffer mainQueryStringBuffer = null;
		StringBuffer printQueryStringBuffer = null;
	
		Set mainQueryPKSet = mainQueryPKMap.keySet();
		ListUI listUI = null;
		for (Iterator iterator = mainQueryPKSet.iterator(); iterator.hasNext();) {
			listUI = (ListUI) iterator.next();
			mainQueryPK = (IMetaDataPK) mainQueryPKMap.get(listUI);
			mainQueryName = mainQueryPK.getName();
			mainQueryFullName = mainQueryPK.getFullName();
	
			// �Զ�ƥ��
			printQueryPK = FDCClientHelper.getPrintQueryPK(listUI);
			printQueryName = printQueryPK.getFullName();
			// ȡ��Ԫ�����ļ���������
			printQueryFileFullName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath, printQueryName,
					FdcMetaDataConstant.META_DATA_EXTNAME_QUERY);
			printQueryFile = new File(printQueryFileFullName);
	
			// ������
			if (!isRepairOld) {
				// �Ѿ����ڣ�ֱ�Ӻ���
				if (printQueryFile.exists()) {
					continue;
				}
				// �����ڣ���������
				else {
					// ȡ��Ԫ�����ļ���������
					mainQueryFileFullName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath, mainQueryFullName,
							FdcMetaDataConstant.META_DATA_EXTNAME_QUERY);
					// MainQuery�������Ԫ���ݹ����ռ��У������޸���Դ
					mainQueryFile = new File(mainQueryFileFullName);
					if (!mainQueryFile.exists()) {
						continue;
					}
	
					// �������е��״�Ԫ����
					printQueryName = mainQueryName.replaceAll("Query", "TDQuery");
					printQueryFullName = mainQueryFullName.replaceAll("Query", "TDQuery");
					printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "TDQuery.query");
					printQueryFile = new File(printQueryFileFullName);
					if (printQueryFile.exists()) {
						continue;
					}
					printQueryName = mainQueryName.replaceAll("Query", "PrintQuery");
					printQueryFullName = mainQueryFullName.replaceAll("Query", "PrintQuery");
					printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "PrintQuery.query");
					printQueryFile = new File(printQueryFileFullName);
					if (printQueryFile.exists()) {
						continue;
					}
				}
			}
			// ����
			else {
				// ȡ��Ԫ�����ļ���������
				mainQueryFileFullName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath, mainQueryFullName,
						FdcMetaDataConstant.META_DATA_EXTNAME_QUERY);
				// MainQuery�������Ԫ���ݹ����ռ��У������޸���Դ
				mainQueryFile = new File(mainQueryFileFullName);
				if (!mainQueryFile.exists()) {
					continue;
				}
			}
	
			try {
				// ���ж��ļ���List��
				mainQueryFileLineList = FdcFileUtil.readLines(mainQueryFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	
			mainQueryStringBuffer = new StringBuffer();
			printQueryStringBuffer = new StringBuffer();
	
			for (Iterator iterator2 = mainQueryFileLineList.iterator(); iterator.hasNext();) {
				mainQueryLine = (String) iterator2.next();
	
				// �滻����
				printQueryLine = mainQueryLine.replaceAll(mainQueryName, printQueryName);
				printQueryFileLineList.add(printQueryLine);
	
				mainQueryStringBuffer.append(mainQueryLine).append("\n");
				printQueryStringBuffer.append(printQueryLine).append("\n");
			}
	
			// System.out.println("QueryԪ��������" + queryStringBuffer + "\n\n");
			// System.out.println("PrintQueryԪ��������" + printQueryStringBuffer + "\n\n");
	
			if (isSave) {
				try {
					if (!printQueryFile.exists()) {
						// �������ļ�
						printQueryFile.createNewFile();
					}
					// ����д�뵽�ļ���
					FdcFileUtil.writeLines(printQueryFile, printQueryFileLineList);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	
			map.put(mainQueryFullName, printQueryFullName);
		}
	
		if (!map.isEmpty()) {
			System.out.println("===========================================");
			System.out.println();
	
			System.out.println("ģ��ǰ׺��" + packagePerfix);
	
			System.out.println("�ļ����ƹ�ϵ��");
			MapUtils.debugPrint(System.out, null, map);
			System.out.println("size = " + map.size());
	
			System.out.println("���״��ļ��б�");
			System.out.println("{");
			String value = null;
			for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
				value = (String) iterator.next();
				System.out.println("    " + value);
			}
			System.out.println("}");
	
			System.out.println("===========================================");
			System.out.println();
		}
	
		return map;
	}

	/**
	 * �����״�Query�ļ�_�����UI��Ӧ��PrintQuery
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	private static Map generatePrintQueryFile_notCheckPQ(String metadataRootPath, String packagePerfix,
			boolean isRepairOld, boolean isSave) {
		Map map = new LinkedHashMap();
	
		// ȡ��ListUI��Ӧ������ѯPK
		List mainQueryPKList = getMainQueryPKList(packagePerfix);
		if (FdcCollectionUtil.isEmpty(mainQueryPKList)) {
			return map;
		}
	
		IMetaDataPK mainQueryPK = null;
		// IMetaDataPK printQueryPK = null;
	
		String mainQueryName = null;
		String mainQueryFullName = null;
		String mainQueryFileFullName = null;
		String printQueryName = null;
		String printQueryFullName = null;
		String printQueryFileFullName = null;
	
		File mainQueryFile = null;
		File printQueryFile = null;
		List mainQueryFileLineList = new ArrayList();
		List printQueryFileLineList = new ArrayList();
		String mainQueryLine = null;
		String printQueryLine = null;
		StringBuffer mainQueryStringBuffer = null;
		StringBuffer printQueryStringBuffer = null;
	
		// ListUI��Ӧ������ѯ����������
		Map queryMap = new LinkedHashMap();
		QueryInfo queryInfo = null;
		for (Iterator iterator = mainQueryPKList.iterator(); iterator.hasNext();) {
			mainQueryPK = (IMetaDataPK) iterator.next();
			mainQueryFullName = mainQueryPK.getFullName();
			// ������أ���Ҫ����ȡֵ
			queryInfo = MetaDataLoader.getQuery(null, mainQueryPK);
	
			if (null != queryInfo) {
				queryMap.put(mainQueryFullName, queryInfo);
			}
		}
	
		Set querySet = queryMap.keySet();
		for (Iterator iterator2 = querySet.iterator(); iterator2.hasNext();) {
			mainQueryFullName = (String) iterator2.next();
			queryInfo = (QueryInfo) queryMap.get(mainQueryFullName);
			mainQueryName = queryInfo.getName();
	
			// ֻƥ��ָ��ģ���µ�Ԫ����
			if (!queryInfo.getPackage().startsWith(packagePerfix)) {
				continue;
			}
			// �����Ѿ����úõ��״�Ԫ����
			if (mainQueryName.endsWith("TDQuery")) {
				continue;
			}
			if (mainQueryName.endsWith("PrintQuery")) {
				continue;
			}
			if (mainQueryName.startsWith("Print") && !mainQueryName.endsWith("PrintQuery")
					&& mainQueryName.endsWith("Query")) {
				continue;
			}
	
			// ȡ��Ԫ�����ļ���������
			mainQueryFileFullName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath, mainQueryFullName,
					FdcMetaDataConstant.META_DATA_EXTNAME_QUERY);
			// �������Ԫ���ݹ����ռ���
			mainQueryFile = new File(mainQueryFileFullName);
			if (!mainQueryFile.exists()) {
				continue;
			}
	
			// ������
			if (!isRepairOld) {
				// �������е��״�Ԫ����
				printQueryName = mainQueryName.replaceAll("Query", "TDQuery");
				printQueryFullName = mainQueryFullName.replaceAll("Query", "TDQuery");
				printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "TDQuery.query");
				printQueryFile = new File(printQueryFileFullName);
				if (printQueryFile.exists()) {
					continue;
				}
				printQueryName = mainQueryName.replaceAll("Query", "PrintQuery");
				printQueryFullName = mainQueryFullName.replaceAll("Query", "PrintQuery");
				printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "PrintQuery.query");
				printQueryFile = new File(printQueryFileFullName);
				if (printQueryFile.exists()) {
					continue;
				}
			} else {
				// �������е��״�Ԫ����
				printQueryName = mainQueryName.replaceAll("Query", "TDQuery");
				printQueryFullName = mainQueryFullName.replaceAll("Query", "TDQuery");
				printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "TDQuery.query");
				printQueryFile = new File(printQueryFileFullName);
				if (!printQueryFile.exists()) {
					printQueryName = mainQueryName.replaceAll("Query", "PrintQuery");
					printQueryFullName = mainQueryFullName.replaceAll("Query", "PrintQuery");
					printQueryFileFullName = mainQueryFileFullName.replaceAll("Query.query", "PrintQuery.query");
					printQueryFile = new File(printQueryFileFullName);
				}
			}
	
			try {
				// ���ж��ļ���List��
				mainQueryFileLineList = FdcFileUtil.readLines(mainQueryFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	
			mainQueryStringBuffer = new StringBuffer();
			printQueryStringBuffer = new StringBuffer();
	
			for (Iterator iterator = mainQueryFileLineList.iterator(); iterator.hasNext();) {
				mainQueryLine = (String) iterator.next();
	
				printQueryLine = mainQueryLine.replaceAll(mainQueryName, printQueryName);
				printQueryFileLineList.add(printQueryLine);
	
				mainQueryStringBuffer.append(mainQueryLine).append("\n");
				printQueryStringBuffer.append(printQueryLine).append("\n");
			}
	
			// System.out.println("QueryԪ��������" + queryStringBuffer + "\n\n");
			// System.out.println("PrintQueryԪ��������" + printQueryStringBuffer + "\n\n");
	
			if (isSave) {
				try {
					if (!printQueryFile.exists()) {
						// �������ļ�
						printQueryFile.createNewFile();
					}
					// ����д�뵽�ļ���
					FdcFileUtil.writeLines(printQueryFile, printQueryFileLineList);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	
			map.put(mainQueryFullName, printQueryFullName);
		}
	
		if (!map.isEmpty()) {
			System.out.println("===========================================");
			System.out.println();
	
			System.out.println("ģ��ǰ׺��" + packagePerfix);
	
			System.out.println("�ļ����ƹ�ϵ��");
			MapUtils.debugPrint(System.out, null, map);
			System.out.println("size = " + map.size());
	
			System.out.println("���״��ļ��б�");
			System.out.println("{");
			String value = null;
			for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
				value = (String) iterator.next();
				System.out.println("    " + value);
			}
			System.out.println("}");
	
			System.out.println("===========================================");
			System.out.println();
		}
	
		return map;
	}

	/**
	 * ȡ�÷��ز�UI���Map
	 * <p>
	 * ����key�����ʱ��UI�࣬value��ŷ��ز�UI���
	 * 
	 * @param metadataRootPath
	 *            Ԫ���ݸ�·��
	 * @param packagePerfix
	 *            ��ǰ׺
	 * @return
	 */
	public static Map getFdcUIComponentMap(String metadataRootPath, String packagePerfix) {
		Map listUIClassMap = new LinkedHashMap();
	
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		FilterItemCollection filterCols = filter.getFilterItems();
		filterCols.add(new FilterItemInfo("uiClassName", packagePerfix + "%", CompareType.LIKE));
		SorterItemCollection sorter = view.getSorter();
		sorter.add(new SorterItemInfo("longNumber"));
	
		// ��ѯ�˵�
		SysMenuItemCollection cols = null;
		try {
			cols = SysMenuItemFactory.getRemoteInstance().getSysMenuItemCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			Map otherSysMenuItemMap = new LinkedHashMap();
			Map shareUIMap = new LinkedHashMap();
			List shareUIList = null;
			Map shareBizInterfaceMap = new LinkedHashMap();
			List shareBizInterfaceList = null;
	
			SysMenuItemInfo sysMenuItemInfo = null;
			String uiClassName = null;
			String displayName = null;
			Class uiClass = null;
			Object ui = null;
			ListUI listUI = null;
			FdcListUIComponent fdcUIComponent = null;
			String entityBOSType = null;
	
			Set shareUISet = shareUIMap.keySet();
			Set shareBizInterfaceSet = shareBizInterfaceMap.keySet();
			for (Iterator iterator = cols.iterator(); iterator.hasNext();) {
				sysMenuItemInfo = (SysMenuItemInfo) iterator.next();
				uiClassName = sysMenuItemInfo.getUiClassName();
				displayName = sysMenuItemInfo.getDisplayName();
				// ������ListUI��β��UI
				if (!uiClassName.endsWith("ListUI")) {
					otherSysMenuItemMap.put(displayName, uiClassName);
					continue;
				}
				try {
					// ����UI�Ĳ˵�
					if (shareUISet.contains(uiClassName)) {
						shareUIList = (List) shareUIMap.get(uiClassName);
						if (null == shareUIList) {
							shareUIList = new ArrayList();
							shareUIMap.put(uiClassName, shareUIList);
						}
						shareUIList.add(displayName);
	
						continue;
					}
	
					uiClass = Class.forName(uiClassName);
					ui = uiClass.newInstance();
					if (ui instanceof ListUI) {
						listUI = (ListUI) ui;
	
						// ȡ�� ListUI���
						fdcUIComponent = FdcUIComponentUtil.getUIComponent(listUI);
						// ����ϵͳ�˵���
						fdcUIComponent.setSysMenuItemInfo(sysMenuItemInfo);
	
						listUIClassMap.put(uiClass, fdcUIComponent);
	
						shareUIList = new ArrayList();
						shareUIMap.put(uiClassName, shareUIList);
						shareUIList.add(displayName);
	
						// ����BizInterface��UI
						entityBOSType = fdcUIComponent.getEntityBOSType();
						shareBizInterfaceList = (List) shareBizInterfaceMap.get(entityBOSType);
						if (null == shareBizInterfaceList) {
							shareBizInterfaceList = new ArrayList();
							shareBizInterfaceMap.put(entityBOSType, shareBizInterfaceList);
						}
						shareBizInterfaceList.add(uiClassName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			if (!otherSysMenuItemMap.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("������ListUI��β��UI�б�");
				MapUtils.debugPrint(System.out, null, otherSysMenuItemMap);
				System.out.println("size = " + otherSysMenuItemMap.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
	
			// Map tempShareUIMap = new LinkedHashMap();
			// shareUISet = shareUIMap.keySet();
			// for (Iterator iterator = shareUISet.iterator(); iterator.hasNext();) {
			// entityBOSType = (String) iterator.next();
			// shareUIList = (List) shareUIMap.get(entityBOSType);
			// if (1 != shareUIList.size()) {
			// tempShareUIMap.put(entityBOSType, shareUIList);
			// }
			// }
			// if (!tempShareUIMap.isEmpty()) {
			// System.out.println("===========================================");
			// System.out.println();
			//
			// System.out.println("����UI�Ĳ˵��б�");
			// MapUtils.debugPrint(System.out, null, tempShareUIMap);
			// System.out.println("size = " + tempShareUIMap.size());
			//
			// System.out.println("===========================================");
			// System.out.println();
			// }
	
			Map tempShareBizInterfaceMap = new LinkedHashMap();
			shareBizInterfaceSet = shareBizInterfaceMap.keySet();
			for (Iterator iterator = shareBizInterfaceSet.iterator(); iterator.hasNext();) {
				entityBOSType = (String) iterator.next();
				shareBizInterfaceList = (List) shareBizInterfaceMap.get(entityBOSType);
				if (1 != shareBizInterfaceList.size()) {
					tempShareBizInterfaceMap.put(entityBOSType, shareBizInterfaceList);
				}
			}
			if (!tempShareBizInterfaceMap.isEmpty()) {
	
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("����BizInterface��UI�б�");
				MapUtils.debugPrint(System.out, null, tempShareBizInterfaceMap);
				System.out.println("size = " + tempShareBizInterfaceMap.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
		}
	
		return listUIClassMap;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ����ҵ��Ԫ�ļ�
	 * 
	 * @param metadataRootPath
	 *            Ԫ���ݸ�·��
	 * @param packagePerfix
	 *            ��ǰ׺
	 * @param isRepairOld
	 *            �Ƿ��滻���еľ��ļ�
	 * @param isSave
	 *            �Ƿ񱣴�
	 * @return
	 */
	public static Map generateBizUnitFile(String metadataRootPath, String packagePerfix, boolean isRepairOld,
			boolean isSave) {
		Map map = new LinkedHashMap();
	
		Map listUIClassMap = getFdcUIComponentMap(metadataRootPath, packagePerfix);
		if (FdcMapUtil.isNotEmpty(listUIClassMap)) {
			Map existsMap = new LinkedHashMap();
			Map noBizInterfaceMap = new LinkedHashMap();
			Map noEntityMap_deply = new LinkedHashMap();
	
			// String package_tpl = "{package}";
			// String entity_tpl = "{entity}";
			//
			// String entityPK_tpl = "com.kingdee.eas.{package}.app.{entity}";
			// String queryPK_tpl = "com.kingdee.eas.{package}.app.{entity}Query";
			// String listUIPK_tpl = "com.kingdee.eas.{package}.client.{entity}ListUI";
			// String editUIPK_tpl = "com.kingdee.eas.{package}.client.{entity}EditUI";
			String listUIFunctionPK_tpl = "com.kingdee.eas.{package}.{entity}Function";
			String editUIFunctionPK_tpl = "com.kingdee.eas.{package}.{entity}Function";
			// String alias_zh_CN_tpl = "{alias_zh_CN}";
			// String alias_zh_HK_tpl = "{alias_zh_HK}";
			// String alias_zh_TW_tpl = "{alias_zh_TW}";
			// String description_zh_CN_tpl = "{description_zh_CN}";
			// String description_zh_HK_tpl = "{description_zh_HK}";
			// String description_zh_TW_tpl = "{description_zh_TW}";
			// String mainQuery_zh_CN_tpl = "com.kingdee.eas.{package}.app.{entity}Query";
			// String mainQuery_zh_HK_tpl = "com.kingdee.eas.{package}.app.{entity}Query";
			// String mainQuery_zh_TW_tpl = "com.kingdee.eas.{package}.app.{entity}Query";
			// String printQuery_zh_CN_tpl = "com.kingdee.eas.{package}.app.{entity}PrintQuery";
			// String printQuery_zh_HK_tpl = "com.kingdee.eas.{package}.app.{entity}PrintQuery";
			// String printQuery_zh_TW_tpl = "com.kingdee.eas.{package}.app.{entity}PrintQuery";
	
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
	
			String package_rpl = "\\{package\\}";
			String entity_rpl = "\\{entity\\}";
	
			String entityPK_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}";
			// String queryPK_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}Query";
			String listUIPK_rpl = "com.kingdee.eas.\\{package\\}.client.\\{entity\\}ListUI";
			String editUIPK_rpl = "com.kingdee.eas.\\{package\\}.client.\\{entity\\}EditUI";
			String listUIFunctionPK_rpl = "com.kingdee.eas.\\{package\\}.\\{entity\\}Function";
			String editUIFunctionPK_rpl = "com.kingdee.eas.\\{package\\}.\\{entity\\}Function";
			String alias_zh_CN_rpl = "\\{alias_zh_CN\\}";
			String alias_zh_HK_rpl = "\\{alias_zh_HK\\}";
			String alias_zh_TW_rpl = "\\{alias_zh_TW\\}";
			String description_zh_CN_rpl = "\\{description_zh_CN\\}";
			String description_zh_HK_rpl = "\\{description_zh_HK\\}";
			String description_zh_TW_rpl = "\\{description_zh_TW\\}";
			String mainQuery_zh_CN_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}Query";
			String mainQuery_zh_HK_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}Query";
			String mainQuery_zh_TW_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}Query";
			String printQuery_zh_CN_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}PrintQuery";
			String printQuery_zh_HK_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}PrintQuery";
			String printQuery_zh_TW_rpl = "com.kingdee.eas.\\{package\\}.app.\\{entity\\}PrintQuery";
	
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
	
			Set listUIClassSet = listUIClassMap.keySet();
	
			Class listUIClass = null;
			String listUIClassName = null;
			FdcListUIComponent fdcUIComponent = null;
	
			// BizUnitģ���ļ�
			// String templateFileName = "W:/workspace/BizUnitTemplate.bizunit";
			String templateFileName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath,
					"com.kingdee.eas.fdc.account.tpl.BizUnitTemplate", FdcMetaDataConstant.META_DATA_EXTNAME_BIZUNIT);
			File templateFile = new File(templateFileName);
			if ((null == templateFile) || !templateFile.exists()) {
				throw new RuntimeException("������BizUnitģ���ļ���" + templateFileName);
			}
			String bizUnitTemplateStr = null;
			try {
				bizUnitTemplateStr = FdcFileUtil.readFileToString(templateFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (FdcStringUtil.isBlank(bizUnitTemplateStr)) {
				throw new RuntimeException("BizUnitģ���ļ�Ϊ�գ�" + templateFileName);
			}
	
			String bizUnitFullName = null;
			String bizUnitFileName = null;
			File bizUnitFile = null;
			String bizUnitStr = null;
	
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
	
			BOSObjectType bizType = null;
			EntityObjectInfo entityObjectInfo = null;
	
			String packageName = null;
			String modelSubPackageName = null;
			String entity = null;
	
			String entityPK = null;
			String queryPK = null;
			String listUIPK = null;
			String editUIPK = null;
			String listUIFunctionPK = null;
			String editUIFunctionPK = null;
			String alias_zh_CN = null;
			String alias_zh_HK = null;
			String alias_zh_TW = null;
			String description_zh_CN = null;
			String description_zh_HK = null;
			String description_zh_TW = null;
			String mainQuery_zh_CN = null;
			String mainQuery_zh_HK = null;
			String mainQuery_zh_TW = null;
			String printQuery_zh_CN = null;
			String printQuery_zh_HK = null;
			String printQuery_zh_TW = null;
	
			for (Iterator iterator = listUIClassSet.iterator(); iterator.hasNext();) {
				listUIClass = (Class) iterator.next();
				listUIClassName = listUIClass.getName();
	
				fdcUIComponent = (FdcListUIComponent) listUIClassMap.get(listUIClass);
				if (null != fdcUIComponent) {
					bizType = fdcUIComponent.getBizType();
					if (null == bizType) {
						noBizInterfaceMap.put(listUIClassName, bizType);
						continue;
					}
					try {
						entityObjectInfo = MetaDataLoader.getEntity(null, bizType);
					} catch (Exception e) {
						// e.printStackTrace();
	
						entityObjectInfo = null;
					}
					if (null == entityObjectInfo) {
						noEntityMap_deply.put(listUIClassName, bizType);
						continue;
					}
	
					// packageName = entityObjectInfo.getPackage();
					entity = entityObjectInfo.getName();
					entityPK = entityObjectInfo.getFullName();
	
					// ȡ�÷����MetaDataPK��Ӧ��model����
					modelSubPackageName = FdcMetaDataUtil.getModelPackageName(new MetaDataPK(entityPK));
					packageName = modelSubPackageName.substring(FDCConstants.EAS_PAKAGE_NAME.length() + 1);
					bizUnitFullName = modelSubPackageName + "." + entity;
					// ȡ��Ԫ�����ļ���������
					bizUnitFileName = FdcMetaDataUtil.getMetaDataFileFullName(metadataRootPath, bizUnitFullName,
							FdcMetaDataConstant.META_DATA_EXTNAME_BIZUNIT);
					bizUnitFile = new File(bizUnitFileName);
					// �Ѿ����ڣ�ֱ�Ӻ���
					if (bizUnitFile.exists()) {
						existsMap.put(listUIClassName, bizUnitFullName);
						if (!isRepairOld) {
							continue;
						}
					}
	
					// �����ڣ�����
					queryPK = (null != fdcUIComponent.getMainQueryPK()) ? fdcUIComponent.getMainQueryPK().getFullName()
							: null;
					listUIPK = listUIClassName;
					editUIPK = fdcUIComponent.getEditUIName();
					listUIFunctionPK = listUIFunctionPK_tpl.replaceFirst(package_rpl, packageName).replaceFirst(
							entity_rpl, entity);
					editUIFunctionPK = editUIFunctionPK_tpl.replaceFirst(package_rpl, packageName).replaceFirst(
							entity_rpl, entity);
					alias_zh_CN = entityObjectInfo.getAlias(Locale.SIMPLIFIED_CHINESE);
					alias_zh_HK = entityObjectInfo.getAlias(Locale.TRADITIONAL_CHINESE);
					alias_zh_TW = entityObjectInfo.getAlias(Locale.TRADITIONAL_CHINESE);
					description_zh_CN = entityObjectInfo.getDescription(Locale.SIMPLIFIED_CHINESE);
					description_zh_HK = entityObjectInfo.getDescription(Locale.TRADITIONAL_CHINESE);
					description_zh_TW = entityObjectInfo.getDescription(Locale.TRADITIONAL_CHINESE);
					mainQuery_zh_CN = queryPK;
					mainQuery_zh_HK = mainQuery_zh_CN;
					mainQuery_zh_TW = mainQuery_zh_CN;
					printQuery_zh_CN = (null != fdcUIComponent.getPrintQueryPK()) ? fdcUIComponent.getPrintQueryPK()
							.getFullName() : null;
					printQuery_zh_HK = printQuery_zh_CN;
					printQuery_zh_TW = printQuery_zh_CN;
	
					if ((null == queryPK)
							|| !FdcMetaDataUtil.exists(metadataRootPath, queryPK, FdcMetaDataConstant.META_DATA_EXTNAME_QUERY)) {
						queryPK = "";
						mainQuery_zh_CN = "";
						mainQuery_zh_HK = "";
						mainQuery_zh_TW = "";
					}
					if ((null == listUIPK)
							|| !FdcMetaDataUtil.exists(metadataRootPath, listUIPK, FdcMetaDataConstant.META_DATA_EXTNAME_UI)) {
						listUIPK = "";
					}
					if ((null == listUIFunctionPK)
							|| !FdcMetaDataUtil.exists(metadataRootPath, listUIFunctionPK,
									FdcMetaDataConstant.META_DATA_EXTNAME_FUNCTION)) {
						listUIFunctionPK = "";
					}
					if ((null == editUIFunctionPK)
							|| !FdcMetaDataUtil.exists(metadataRootPath, editUIFunctionPK,
									FdcMetaDataConstant.META_DATA_EXTNAME_FUNCTION)) {
						editUIFunctionPK = "";
					}
					if ((null == printQuery_zh_CN)
							|| !FdcMetaDataUtil.exists(metadataRootPath, printQuery_zh_CN, FdcMetaDataConstant.META_DATA_EXTNAME_QUERY)) {
						printQuery_zh_CN = "";
						printQuery_zh_HK = "";
						printQuery_zh_TW = "";
					}
	
					alias_zh_CN = FdcObjectUtil.defaultIfNull(alias_zh_CN, "");
					alias_zh_HK = FdcObjectUtil.defaultIfNull(alias_zh_HK, "");
					alias_zh_TW = FdcObjectUtil.defaultIfNull(alias_zh_TW, "");
					description_zh_CN = FdcObjectUtil.defaultIfNull(alias_zh_CN, "");
					description_zh_HK = FdcObjectUtil.defaultIfNull(alias_zh_HK, "");
					description_zh_TW = FdcObjectUtil.defaultIfNull(alias_zh_TW, "");
	
					bizUnitStr = bizUnitTemplateStr.replaceAll(entityPK_rpl, entityPK);
					bizUnitStr = bizUnitStr.replaceAll(listUIPK_rpl, listUIPK);
					bizUnitStr = bizUnitStr.replaceAll(editUIPK_rpl, editUIPK);
					bizUnitStr = bizUnitStr.replaceAll(listUIFunctionPK_rpl, listUIFunctionPK);
					bizUnitStr = bizUnitStr.replaceAll(editUIFunctionPK_rpl, editUIFunctionPK);
					bizUnitStr = bizUnitStr.replaceAll(alias_zh_CN_rpl, alias_zh_CN);
					bizUnitStr = bizUnitStr.replaceAll(alias_zh_HK_rpl, alias_zh_HK);
					bizUnitStr = bizUnitStr.replaceAll(alias_zh_TW_rpl, alias_zh_TW);
					bizUnitStr = bizUnitStr.replaceAll(description_zh_CN_rpl, description_zh_CN);
					bizUnitStr = bizUnitStr.replaceAll(description_zh_HK_rpl, description_zh_HK);
					bizUnitStr = bizUnitStr.replaceAll(description_zh_TW_rpl, description_zh_TW);
					bizUnitStr = bizUnitStr.replaceAll(mainQuery_zh_CN_rpl, mainQuery_zh_CN);
					bizUnitStr = bizUnitStr.replaceAll(mainQuery_zh_HK_rpl, mainQuery_zh_HK);
					bizUnitStr = bizUnitStr.replaceAll(mainQuery_zh_TW_rpl, mainQuery_zh_TW);
					bizUnitStr = bizUnitStr.replaceAll(printQuery_zh_CN_rpl, printQuery_zh_CN);
					bizUnitStr = bizUnitStr.replaceAll(printQuery_zh_HK_rpl, printQuery_zh_HK);
					bizUnitStr = bizUnitStr.replaceAll(printQuery_zh_TW_rpl, printQuery_zh_TW);
	
					bizUnitStr = bizUnitStr.replaceAll(package_rpl, packageName);
					bizUnitStr = bizUnitStr.replaceAll(entity_rpl, entity);
	
					if (isSave) {
						try {
							// д�ַ������ļ���(ע�⣺bizunit��xml�ļ���һ��Ҫ���ñ����ʽΪUTF-8)
							FdcFileUtil.writeStringToFile(bizUnitFile, bizUnitStr, "UTF-8");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
	
					map.put(listUIPK, bizUnitFullName);
				}
			}
	
			if (!noBizInterfaceMap.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("û��BizInterface��UI�б�");
				MapUtils.debugPrint(System.out, null, noBizInterfaceMap);
				System.out.println("size = " + noBizInterfaceMap.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
			if (!noEntityMap_deply.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("û��Entityӳ���UI�б�");
				MapUtils.debugPrint(System.out, null, noEntityMap_deply);
				System.out.println("size = " + noEntityMap_deply.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
			if (!existsMap.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("�Ѿ�����BizUnit��UI�б�");
				MapUtils.debugPrint(System.out, null, existsMap);
				System.out.println("size = " + existsMap.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
			if (!map.isEmpty()) {
				System.out.println("===========================================");
				System.out.println();
	
				System.out.println("����BizUnit��UI�б�");
				MapUtils.debugPrint(System.out, null, map);
				System.out.println("size = " + map.size());
	
				System.out.println("===========================================");
				System.out.println();
			}
		}
	
		return map;
	}

	/**
	 * ����������DEP������
	 * 
	 * @param metadataRootPath
	 *            Ԫ���ݸ�·��
	 * @param packagePerfix
	 *            ��ǰ׺
	 * @author RD_skyiter_wang
	 * @createDate 2014-3-20
	 */
	public static StringBuffer generateDepWhiteList(String metadataRootPath, String packagePerfix) {
		String tpl = "    <ui name=\"{0}\" alias=\"{1}-{2}\"/>";
		StringBuffer stringBuffer = new StringBuffer();
	
		Map listUIClassMap = getFdcUIComponentMap(metadataRootPath, packagePerfix);
		Set listUIClassKeySet = listUIClassMap.keySet();
		Set listUIClassNameSet = new LinkedHashSet();
		Set editUIClassNameSet = new LinkedHashSet();
	
		for (Iterator iterator = listUIClassKeySet.iterator(); iterator.hasNext();) {
			Class listUIClass = (Class) iterator.next();
	
			FdcListUIComponent fdcUIComponent = (FdcListUIComponent) listUIClassMap.get(listUIClass);
			if (null == fdcUIComponent) {
				continue;
			}
	
			SysMenuItemInfo sysMenuItemInfo = fdcUIComponent.getSysMenuItemInfo();
			String menuName = sysMenuItemInfo.getName();
			String listUIName = listUIClass.getName();
			if (!listUIClassNameSet.contains(listUIName)) {
				listUIClassNameSet.add(listUIName);
	
				String listLine = tpl.replace("{0}", listUIName);
				listLine = listLine.replace("{1}", menuName);
				listLine = listLine.replace("{2}", "�б�");
				stringBuffer.append(listLine).append("\n");
			}
	
			try {
				String editUIName = fdcUIComponent.getEditUIName();
				if (null == editUIName || !editUIName.startsWith(packagePerfix)) {
					continue;
				}
	
				if (!editUIClassNameSet.contains(editUIName)) {
					Class editUIClass = Class.forName(editUIName);
					Object editUI = editUIClass.newInstance();
					if (editUI instanceof EditUI) {
						editUIClassNameSet.add(editUIName);
	
						String editLine = tpl.replace("{0}", editUIName);
						editLine = editLine.replace("{1}", menuName);
						editLine = editLine.replace("{2}", "�༭");
						stringBuffer.append(editLine).append("\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		listUIClassMap.clear();
		listUIClassKeySet.clear();
		listUIClassNameSet.clear();
		editUIClassNameSet.clear();
	
		// ///////////////////////////////////////////////////////////////////
	
		System.out.println("===========================================");
		System.out.println();
	
		System.out.println(stringBuffer);
	
		System.out.println("===========================================");
		System.out.println();
	
		// ///////////////////////////////////////////////////////////////////
		stringBuffer.append("\n");
		// ///////////////////////////////////////////////////////////////////
	
		return stringBuffer;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

}
