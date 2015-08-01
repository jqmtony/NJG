/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * 描述：这个类主要实现对Excel导入,过滤类型<p>
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
