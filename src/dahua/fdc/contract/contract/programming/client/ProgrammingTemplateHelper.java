/*
 * @(#)ProgrammingTemplateHelper.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * 合约框架模板 助手
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-11
 * @see
 * @since 1.4
 */
public class ProgrammingTemplateHelper {

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	/**
	 * 描述：级联查找所有父类TreeBase
	 * 
	 * @param uiObject
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-20
	 */
	protected static IObjectCollection findTreeBaseInfoWithSelf(CoreUI uiObject) {
		IObjectCollection parentCols = new CostAccountCollection();

		String sqlStr = generateFindParentWithSelfCascadeSql();
		String idStr = uiObject.getUIContext().get("ID").toString();
		Object[] params = new Object[] { OrgConstants.DEF_CU_ID, idStr, OrgConstants.DEF_CU_ID };

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sqlStr);
		builder.addParams(params);

		Logger logger = uiObject.getLogger(uiObject.getClass());

		logger.info("ProgrammingTemplateHelper.findTreeBaseInfoWithSelf(),sql:" + sqlStr);
		logger.info("ProgrammingTemplateHelper.findTreeBaseInfoWithSelf(),params:" + builder.getParamaters());

		RowSet rs;
		try {
			rs = builder.executeQuery();

			CostAccountInfo bill = null;
			CostAccountInfo parent = null;

			String parentId = null;
			String id = null;
			String number = null;
			String longNumber = null;
			String name = null;
			BigDecimal level = null;
			boolean isLeaf = false;

			while (rs.next()) {
				parentId = rs.getString("FParentID");
				id = rs.getString("FID");
				number = rs.getString("FNumber");
				longNumber = rs.getString("FLongNumber");
				name = rs.getString("FName_L2");
				level = rs.getBigDecimal("FLevel");
				isLeaf = rs.getBoolean("FIsLeaf");

				bill = new CostAccountInfo();
				bill.setId(BOSUuid.read(id));
				bill.setNumber(number);
				bill.setLongNumber(longNumber);
				bill.setName(name);
				bill.setLevel(level.intValue());
				bill.setIsLeaf(isLeaf);

				if (null != parentId) {
					parent = new CostAccountInfo();
					parent.setId(BOSUuid.read(parentId));

					bill.setParent(parent);
				}

				parentCols.addObject(bill);
			}
		} catch (BOSException e1) {
			uiObject.handUIExceptionAndAbort(e1);
		} catch (SQLException e) {
			uiObject.handUIExceptionAndAbort(e);
		}

		return parentCols;
	}

	/**
	 * 描述：生成 级联查找所有父类id（包括自己）的 SQL
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-20
	 */
	private static String generateFindParentWithSelfCascadeSql() {
		StringBuffer sb = new StringBuffer();

		sb.append("	SELECT DISTINCT bill.FParentID, bill.FID, bill.FNumber, bill.FLongNumber, 		\r\n");
		sb.append("	 		bill.FName_L2, bill.FLevel, bill.FIsLeaf		\r\n");
		sb.append("	  FROM T_FDC_CostAccount bill		\r\n");
		sb.append("	 INNER JOIN (SELECT bill.FParentID, bill.FID, bill.FNumber, bill.FLongNumber, 		\r\n");
		sb.append("	 				bill.FName_L2, bill.FLevel, bill.FIsLeaf		\r\n");
		sb.append("	               FROM T_FDC_CostAccount bill		\r\n");
		sb.append("	              INNER JOIN T_CON_PTECost tfdcPTEC		\r\n");
		sb.append("	                 ON tfdcPTEC.FCostAccountID = bill.FID		\r\n");
		sb.append("	              INNER JOIN T_CON_ProgrammingTE tfdcPTE		\r\n");
		sb.append("	                 ON tfdcPTE.FID = tfdcPTEC.FParentID		\r\n");
		sb.append("	              WHERE bill.FFullOrgUnit = ? AND tfdcPTE.FParentID = ?) sub		\r\n");
		sb.append("	    ON CHARINDEX(bill.FLongNumber || '!', sub.FLongNumber || '!') = 1		\r\n");
		sb.append("	  WHERE bill.FFullOrgUnit = ?		\r\n");
		sb.append("	  ORDER BY bill.FLongNumber		\r\n");

		String sqlStr = sb.toString();

		return sqlStr;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
}
