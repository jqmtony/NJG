/**
 * @(#)FdcManagementUtil.java 1.0 2014-5-22
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

/**
 * ���������ز���������
 * <p>
 * 1���ṩ����ӿڣ����ڼ��Ӻ͹��� Java ������Լ� Java ��������������еĲ���ϵͳ <br>
 * 2���μ�java.lang.management��, java.lang.Runtime�� <br>
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-5-22
 * @version 1.0, 2014-5-22
 * @see
 * @since JDK1.4
 */
public class FdcManagementUtil {

	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.util.FdcManagementUtil");

	/**
	 * ��������¼ִ��ʱ��ǰ
	 * 
	 * @param classz
	 * @param methodName
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 */
	public static Map recodeExeTimeBefore(Class classz, String methodName) {
		Map map = new HashedMap();

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		map.put("className", classz.getName());
		map.put("methodName", methodName);

		startTime = System.currentTimeMillis();

		map.put("startTime", new Long(startTime));
		map.put("endTime", new Long(endTime));
		map.put("exeTime", new Long(exeTime));

		return map;
	}

	/**
	 * ��������¼ִ��ʱ���
	 * 
	 * @param classz
	 * @param methodName
	 * @param map
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 */
	public static void recodeExeTimeAfter(Class classz, String methodName, Map map) {
		// ���ִ��Map��Ϣ
		checExekMapInfo(classz, methodName, map);

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		long startTime = 0;
		long endTime = 0;
		long exeTime = 0;

		startTime = FdcMapUtil.getLongValue(map, "startTime");

		endTime = System.currentTimeMillis();
		exeTime = endTime - startTime;
		// �������
		double exeTimeDouble = (exeTime / 1000.0);

		String classSimpleName = FdcClassUtil.getSimpleName(classz);
		String str = endTime + ":" + classSimpleName + "." + methodName;

		// 10�����ڣ�����"��"��ʾִ�н��
		if (exeTimeDouble < 60 * 10) {
			logger.info("======================" + str + ", exeTime: " + exeTimeDouble + " S");
		}
		// 10Сʱ�ڣ�����"����"��ʾִ�н��
		else if (exeTimeDouble < (60 * 60 * 10)) {
			logger.info("======================" + str + ", exeTime: " + (exeTimeDouble / 60) + " MIN");
		}
		// ���򣬲���"Сʱ"��ʾִ�н��
		else {
			logger.info("======================" + str + ", exeTime: " + (exeTimeDouble / (60 * 60)) + " H");
		}

		map.put("startTime", new Long(startTime));
		map.put("endTime", new Long(endTime));
		map.put("exeTime", new Long(exeTime));
	}

	/**
	 * ��������¼ִ���ڴ�ǰ
	 * 
	 * @param classz
	 * @param methodName
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 */
	public static Map recodeExeMemoryBefore(Class classz, String methodName) {
		Map map = new HashedMap();

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		// JDK1.5֮ǰ��֧�ָ�API(OperatingSystemMXBean)
		// OperatingSystemMXBean osmb = (OperatingSystemMXBean)
		// ManagementFactory.getOperatingSystemMXBean();
		// double totalPhysicalMemorySize_start = 0;
		// double freePhysicalMemorySize_start = 0;
		// double totalPhysicalMemorySize_end = 0;
		// double freePhysicalMemorySize_end = 0;

		Runtime runTime = Runtime.getRuntime();
		double totalRuntimeMemorySize_start = 0;
		double freeRuntimeMemorySize_start = 0;
		double maxRuntimeMemorySize_start = 0;
		double totalRuntimeMemorySize_end = 0;
		double freeRuntimeMemorySize_end = 0;
		double maxRuntimeMemorySize_end = 0;

		double exeMemorySize = 0;
		int mb = 1024 * 1024;

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		// totalPhysicalMemorySize_start = osmb.getTotalPhysicalMemorySize() * 1.0 / mb;
		// freePhysicalMemorySize_start = osmb.getFreePhysicalMemorySize() * 1.0 / mb;
		totalRuntimeMemorySize_start = runTime.totalMemory() * 1.0 / mb;
		freeRuntimeMemorySize_start = runTime.freeMemory() * 1.0 / mb;
		maxRuntimeMemorySize_start = runTime.maxMemory() * 1.0 / mb;

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		map.put("className", classz.getName());
		map.put("methodName", methodName);

		map.put("totalRuntimeMemorySize_start", new Double(totalRuntimeMemorySize_start));
		map.put("freeRuntimeMemorySize_start", new Double(freeRuntimeMemorySize_start));
		map.put("maxRuntimeMemorySize_start", new Double(maxRuntimeMemorySize_start));
		map.put("totalRuntimeMemorySize_end", new Double(totalRuntimeMemorySize_end));
		map.put("freeRuntimeMemorySize_end", new Double(freeRuntimeMemorySize_end));
		map.put("maxRuntimeMemorySize_end", new Double(maxRuntimeMemorySize_end));

		map.put("exeMemorySize", new Double(exeMemorySize));

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		return map;
	}

	/**
	 * ��������¼ִ���ڴ��
	 * 
	 * @param classz
	 * @param methodName
	 * @param map
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 */
	public static void recodeExeMemoryAfter(Class classz, String methodName, Map map) {
		// ���ִ��Map��Ϣ
		checExekMapInfo(classz, methodName, map);

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		// JDK1.5֮ǰ��֧�ָ�API(OperatingSystemMXBean)
		// OperatingSystemMXBean osmb = (OperatingSystemMXBean)
		// ManagementFactory.getOperatingSystemMXBean();
		// double totalPhysicalMemorySize_start = 0;
		// double freePhysicalMemorySize_start = 0;
		// double totalPhysicalMemorySize_end = 0;
		// double freePhysicalMemorySize_end = 0;

		Runtime runTime = Runtime.getRuntime();
		double totalRuntimeMemorySize_start = 0;
		double freeRuntimeMemorySize_start = 0;
		double maxRuntimeMemorySize_start = 0;
		double totalRuntimeMemorySize_end = 0;
		double freeRuntimeMemorySize_end = 0;
		double maxRuntimeMemorySize_end = 0;

		double exeMemorySize = 0;
		int mb = 1024 * 1024;

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		// totalPhysicalMemorySize_start = FdcMapUtil.getDoubleValue(map,
		// "totalPhysicalMemorySize_start");
		// freePhysicalMemorySize_start = FdcMapUtil.getDoubleValue(map,
		// "freePhysicalMemorySize_start");
		totalRuntimeMemorySize_start = FdcMapUtil.getDoubleValue(map, "totalRuntimeMemorySize_start");
		freeRuntimeMemorySize_start = FdcMapUtil.getDoubleValue(map, "freeRuntimeMemorySize_start");
		maxRuntimeMemorySize_start = FdcMapUtil.getDoubleValue(map, "maxRuntimeMemorySize_start");

		// totalPhysicalMemorySize_end = osmb.getTotalPhysicalMemorySize() * 1.0 / mb;
		// freePhysicalMemorySize_end = osmb.getFreePhysicalMemorySize() * 1.0 / mb;
		// exeMemorySize = freePhysicalMemorySize_end - freePhysicalMemorySize_start;
		totalRuntimeMemorySize_end = runTime.totalMemory() * 1.0 / mb;
		freeRuntimeMemorySize_end = runTime.freeMemory() * 1.0 / mb;
		maxRuntimeMemorySize_end = runTime.maxMemory() * 1.0 / mb;
		exeMemorySize = freeRuntimeMemorySize_start - freeRuntimeMemorySize_end;

		map.put("totalRuntimeMemorySize_start", new Double(totalRuntimeMemorySize_start));
		map.put("freeRuntimeMemorySize_start", new Double(freeRuntimeMemorySize_start));
		map.put("maxRuntimeMemorySize_start", new Double(maxRuntimeMemorySize_start));
		map.put("totalRuntimeMemorySize_end", new Double(totalRuntimeMemorySize_end));
		map.put("freeRuntimeMemorySize_end", new Double(freeRuntimeMemorySize_end));
		map.put("maxRuntimeMemorySize_end", new Double(maxRuntimeMemorySize_end));

		map.put("exeMemorySize", new Double(exeMemorySize));

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////

		String classSimpleName = FdcClassUtil.getSimpleName(classz);
		long currentTime = System.currentTimeMillis();
		String str = currentTime + ":" + classSimpleName + "." + methodName;

		// logger.info("======================" + str + "��ϵͳ�����ڴ��ܼƣ���ʼ��" +
		// totalPhysicalMemorySize_start + "MB");
		// logger.info("======================" + str + "��ϵͳ��������ڴ��ܼƣ���ʼ��" +
		// freePhysicalMemorySize_start + "MB");
		// logger.info("======================" + str + "��exeMemory��" + exeMemorySize + "MB");

		// logger.info("======================" + str + "��ϵͳ�����ڴ��ܼƣ�������" +
		// totalPhysicalMemorySize_end + "MB");
		// logger.info("======================" + str + "��ϵͳ��������ڴ��ܼƣ�������" +
		// freePhysicalMemorySize_end + "MB");
		// logger.info("======================" + str + "��exeMemory��" + exeMemorySize + "MB");

		logger.info("======================" + str + "������ʱ�ڴ��ܼƣ���ʼ��" + totalRuntimeMemorySize_start + " MB");
		logger.info("======================" + str + "������ʱʣ���ڴ��ܼƣ���ʼ��" + freeRuntimeMemorySize_start + " MB");
		logger.info("======================" + str + "������ʱ�������ڴ��ܼƣ���ʼ��" + maxRuntimeMemorySize_start + " MB");

		logger.info("======================" + str + "������ʱ�ڴ��ܼƣ�������" + totalRuntimeMemorySize_end + " MB");
		logger.info("======================" + str + "������ʱʣ���ڴ��ܼƣ�������" + freeRuntimeMemorySize_end + " MB");
		logger.info("======================" + str + "������ʱ�������ڴ��ܼƣ�������" + maxRuntimeMemorySize_end + " MB");

		// ///////////////////////////////////////////////////////////////////////

		logger.info("======================" + str + ", exeMemory: " + exeMemorySize + " MB");

		// ///////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////
	}

	/**
	 * ���������ִ��Map��Ϣ
	 * 
	 * @param classz
	 * @param methodName
	 * @param map
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-11
	 */
	private static void checExekMapInfo(Class classz, String methodName, Map map) {
		String beforeClassName = FdcMapUtil.getString(map, "className");
		String afterClassName = classz.getName();

		String msg = "ǰ��һ�¡�ǰ������" + beforeClassName + ", ��������" + afterClassName;
		Assert.assertEquals(msg, beforeClassName, afterClassName);

		String beforeMethodName = FdcMapUtil.getString(map, "methodName");
		String afterMethodName = methodName;

		msg = "ǰ��һ�¡�ǰ��������" + beforeMethodName + ", �󷽷�����" + afterMethodName;
		Assert.assertEquals(msg, beforeMethodName, afterMethodName);
	}
}
