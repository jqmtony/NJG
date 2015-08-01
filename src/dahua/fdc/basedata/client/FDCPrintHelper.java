package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;

/**
 * 房地产辅助类:打印与打印预览相关操作 更多方法@see PrintManager
 * 
 * @author jianxing_zhou 2010-2-29
 */
public final class FDCPrintHelper {
	// 更多方法@see PrintManager

	/**
	 * 设置打印标题
	 * 
	 * @param table
	 * @param title
	 * @param titleFontsize
	 *            标题字体大小
	 */
	public static void setPrintTitle(KDTable table, String title, int titleFontsize) {
		if (titleFontsize < 1)
			titleFontsize = 14;
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(titleFontsize);
		sa.setBold(true);
		header.addRow(title, sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
	}

	public static void setPrintTitle(KDTable table) {
		setPrintTitle(table, table.getName(), 0);
	}

	// 更多方法@see PrintManager
}
