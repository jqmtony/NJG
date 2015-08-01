package com.kingdee.eas.fdc.costdb.client;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class DBAimCostVersionHandler {
	private int lastVerisonNumber = 1;

	public DBAimCostVersionHandler(String objectId) throws BOSException,
			SQLException {
		String sql = "select top 1 FVersionNumber from T_Aim_AimCost "
				+ "where FOrgOrProId='" + objectId + "' and "
				+ "FIsLastVersion =1";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		while (rs.next()) {
			String versionNumber = rs.getString("FVersionNumber");
			int k = versionNumber.indexOf(".");
			String number = versionNumber.substring(0, k);
			this.lastVerisonNumber = Integer.parseInt(number);

		}
	}

	public String getFirstVersion() {
		return "1.0";
	}

	public static String getPreVersion(String versionNumber) {
		int k = versionNumber.indexOf(".");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		if (num <= 1) {
			return null;
		}
		return (num - 1) + ".0";
	}

	public static String getNextVersion(String versionNumber) {
		int k = versionNumber.indexOf(".");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		return (num + 1) + ".0";
	}

	public String getLastVersion() {
		return this.lastVerisonNumber + ".0";
	}

	public void verifyVersion(String versionNumber) {
		if (versionNumber == null) {
			return;
		}
		int k = versionNumber.indexOf(".");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		if (num > this.lastVerisonNumber) {
			SysUtil.abort();
		}
	}
}
