package com.kingdee.eas.fdc.basedata.util;

import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��ȡһЩ���õĹ�����֯��һЩ��������
 * 
 * @author pengwei_hou
 * 
 */
public class FullOrgUnitHelper {

	/**
	 * 
	 * ���������ݵ�ǰ������֯��ȡ�¼�������ʵ�������֯ ͨ����������ƥ��
	 */
	public static Set getChildrenCompanyOrgUnitInfos(BOSUuid companyID) throws Exception {
		Set ids = new HashSet();
		CompanyOrgUnitInfo orgUnitInfo = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(companyID));

		if (null != orgUnitInfo) {
			String orgUnitLongNumber = orgUnitInfo.getLongNumber();
			ids = getChildrenCompanyOrgUnitInfos(orgUnitLongNumber);
		}

		return ids;
	}

	/**
	 * 
	 * ���������ݵ�ǰ������֯��ȡ�¼�������ʵ�������֯ ͨ����������ƥ��
	 */
	public static Set getChildrenCompanyOrgUnitInfos(String orgUnitLongNumber) throws Exception {
		Set ids = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from t_org_company ");
		builder.appendSql("where flongnumber like ");
		builder.appendParam(orgUnitLongNumber + "%");
		builder.appendSql(" and ");
		builder.appendParam("fisbizunit", Boolean.TRUE);
		builder.appendSql(" and ");
		builder.appendParam("fid", FDCUtils.getAuthorizedOrgs(null).toArray(), "VARCHAR");
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			ids.add(rs.getString("fid"));
		}
		return ids;
	}
}
