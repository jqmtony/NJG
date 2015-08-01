/*
 * @(#)FDCProDepConPayPlanPrintData.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import javax.imageio.ImageIO;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

/**
 * 描述:
 * @author jian_cao  date:2013-1-7
 * @version EAS6.1
 */
public class FDCProDepConPayPlanPrintDataProvider extends FDCNoteDataProvider {

	public KDTable kdtEntry = null;
	public static final String[] col = new String[] { "id", "number", "name", "fullOrgUnit.name", "payPlanCycle.cycle", "editMonth",
			"version", "description", "creator.name", "lastUpdateUser.name", "bookedDate", "entryImage" };


	/**
	 * 描述：构造函数
	 * @param billId
	 * @param mainQuery
	 */
	public FDCProDepConPayPlanPrintDataProvider(String billId, IMetaDataPK mainQuery) {

		super(billId, mainQuery);
	}

	public FDCProDepConPayPlanPrintDataProvider(String billId, IMetaDataPK mainQuery, KDTable kdtEntry) {
		super(billId, mainQuery);
		this.kdtEntry = kdtEntry;
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		IRowSet mainRowSet = null;
		DynamicRowSet dynamicRow = null;
		try {
			mainRowSet = super.getMainBillRowSet(ds);
			dynamicRow = new DynamicRowSet(col.length);
			for (int i = 0; i < col.length; i++) {
				ColInfo ci = new ColInfo();
				if (i == col.length - 1) {
					ci.colType = Types.BINARY;
				} else {
					ci.colType = Types.VARCHAR;
				}

				ci.columnName = col[i];
				ci.nullable = 1;
				dynamicRow.setColInfo(i + 1, ci);
			}

			BufferedImage bi = getImage(kdtEntry);
			byte[] imgbyte = null;
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", bao);
			imgbyte = bao.toByteArray();

			while (mainRowSet.next()) {
				/**
				 * 单据编码：	
				 * 单据名称：
					项目公司：
					计划周期：		
					编制月份：
					单位：	
					版本号：	
					编制说明：	
				 */
				dynamicRow.moveToInsertRow();
				dynamicRow.updateString("id", mainRowSet.getString("id"));
				dynamicRow.updateString("number", mainRowSet.getString("number"));
				dynamicRow.updateString("name", mainRowSet.getString("name"));
				dynamicRow.updateString("fullOrgUnit.name", mainRowSet.getString("fullOrgUnit.name"));
				dynamicRow.updateString("payPlanCycle.cycle", mainRowSet.getString("payPlanCycle.cycle"));
				dynamicRow.updateString("editMonth", mainRowSet.getString("month"));
				dynamicRow.updateString("version", mainRowSet.getString("version"));
				dynamicRow.updateString("description", mainRowSet.getString("description"));
				dynamicRow.updateString("creator.name", mainRowSet.getString("creator.name"));
				dynamicRow.updateString("lastUpdateUser.name", mainRowSet.getString("lastUpdateUser.name"));
				dynamicRow.updateString("bookedDate", mainRowSet.getString("bookedDate"));
				dynamicRow.updateObject("entryImage", imgbyte);
				dynamicRow.insertRow();
				dynamicRow.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		return dynamicRow;
	}

	/** 将组件转换为缓存图片 */
	private BufferedImage getImage(Component component) {
		KDTable tdt = (KDTable) component;
		int size = 0;

		for (int i = 0; i < tdt.getColumnCount(); i++) {
			size += tdt.getColumn(i).getWidth() + 5;
		}

		tdt.setSize(size, tdt.getHeight());
		
		int height = tdt.getRowCount() * 30 + 25;
		if (height == 0) {
			height = 1; // 因为height＝0时，下面一句会报异常，所以赋为1避免异常
		}
		BufferedImage bi = new BufferedImage(size, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();
		component.paint(g2);
		g2.dispose();

		return bi;
	}
}
