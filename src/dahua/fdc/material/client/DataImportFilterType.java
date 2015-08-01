/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.material.client;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * �������������Ҫʵ�ֶ�Excel����,��������<p>
 * @author luoxiaolong
 * @version EAS 6.0
 * @see MaterialInfoUI
 */
public class DataImportFilterType extends FileFilter{

	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		return f.getName().endsWith(".xls");
	}

	public String getDescription() {
		return ".xls";
	}

}
