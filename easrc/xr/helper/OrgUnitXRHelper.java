package com.kingdee.eas.xr.helper;

import java.sql.SQLException;
import java.util.HashSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class OrgUnitXRHelper {
	/**
	 * 根据部门获得所有子部门（所有深度下的子节点）
	 * @param parent
	 * @return
	 * @throws BOSException
	 */
	public static AdminOrgUnitCollection getChildByParent(AdminOrgUnitInfo parent) throws BOSException {
//		LinkedHashMap queryMap = new LinkedHashMap();
//		queryMap.put("parent", parent.getId().toString());
		AdminOrgUnitCollection resutl = new AdminOrgUnitCollection();
//		resutl.add(parent);
//		AdminOrgUnitCollection collection = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(
//				EDxbangUtil.getEntityViewInfo(queryMap, new CompareType[] { CompareType.EQUALS }));
//		for (int i = collection.size(); --i >= 0;) {
//			AdminOrgUnitInfo adminOrgUnitInfo = collection.get(i);
//			if (!adminOrgUnitInfo.isIsLeaf()) {
//				resutl.addCollection(getChildByParent(adminOrgUnitInfo));
//			}
//		}
//		resutl.addCollection(collection);
		return resutl;
	}
	/**
	 * 判断当前公司是否有科目表
	 * */
    public static void checkCompanyAccountTable(CompanyOrgUnitInfo curCompany)
    {
        if(curCompany == null)
            throw new RuntimeException("Company can not be null.");
        if(curCompany.getAccountTable() == null){
            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.basedata.master.account.client.AccountResource", "CurrentCompanyHasNoStartAccountTable"));
            SysUtil.abort();
        }
    }
    /**
	 * 由部门得到财务组织
	 */
	public static CompanyOrgUnitInfo getComOrgByAdminOrg(Context ctx,
			AdminOrgUnitInfo adminOrgUnitInfo) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo costCenterOrgUnitInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
		do {
			if (parentCost == null)
				break;
			if (parentCost.isIsCompanyOrgUnit()) {
				String id = parentCost.getId().toString();
				if (id == null)
					continue;
				ICompanyOrgUnit iCompanyOrgUnit = null;
				if(ctx!=null)
					iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
				else
					iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
				costCenterOrgUnitInfo = (CompanyOrgUnitInfo) iCompanyOrgUnit.getValue(new ObjectUuidPK(id));
				break;
			}
			parentCost = parentCost.getParent();
			if (parentCost != null) {
				String id = parentCost.getId().toString();
				if (id != null) {
					IAdminOrgUnit iAdmin = null;
					if (ctx!=null) 
						iAdmin = AdminOrgUnitFactory.getLocalInstance(ctx);
					else
						iAdmin = AdminOrgUnitFactory.getRemoteInstance();
					parentCost = (AdminOrgUnitInfo) iAdmin.getValue(new ObjectUuidPK(id));
				}
			}
		} while (true);
		return costCenterOrgUnitInfo;
	}
	
	/**
	 * 根据选择部门，带出部门负责人
	 * @return
	 */
	public static PersonInfo getyuskAmount(String depID)throws Exception{
		PersonInfo pInfo =null;
		StringBuffer perid = new StringBuffer() ;
		String sql = "select per.fname_l2 as na,per.fid as perid from T_ORG_PositionMember post left join T_ORG_Position ps on ps.fid=post.FPositionID left join T_ORG_Admin admin on admin.fid=ps.fadminorgunitid left join T_BD_Person per on per.fid=post.fpersonid" +
				" where admin.fid='"+depID+"' and ps.FIsRespPosition='1'" ;
			XRSQLBuilder sqlbuild = new XRSQLBuilder();
			sqlbuild.appendSql(sql);
			IRowSet rowset = sqlbuild.executeQuery();
			while(rowset.next()){
		if(rowset.getString("perid")!=null)
			perid.append(rowset.getString("perid"));
		}
			if(perid!=null&&perid.length()>0){
				String PID = perid.toString();
				PersonInfo pInfo1 = PersonFactory.getRemoteInstance().getPersonInfo(new ObjectUuidPK(PID));
				 pInfo=pInfo1;
			}
			return pInfo;
	}
	
	/** 
	 * 
	 *版本：EAS7.0
	 *作者（修改人）：钟小花
	 *修改时间：2012-11-29
	 *描述：由行政组织取得采购组织
	 * @throws SQLException 
	 *
	 */
	public static PurchaseOrgUnitInfo getPurchaseOrgUnit(AdminOrgUnitInfo adminOrgUnitInfo,KDBizPromptBox box) throws EASBizException,
			BOSException, SQLException {
		PurchaseOrgUnitInfo purOrgUnitInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
			if (parentCost != null){
				String id = parentCost.getId().toString();
				if(id!=null){
					IPurchaseOrgUnit iPurOrgUnit = null;
					iPurOrgUnit = PurchaseOrgUnitFactory.getRemoteInstance();
					XRSQLBuilder build=new XRSQLBuilder();
					String sql="SELECT c.fid as 采购组织ID FROM T_ORG_UnitRelation AS a LEFT OUTER JOIN T_ORG_BaseUnit AS c ON a.FFromUnitID = c.FID LEFT OUTER JOIN T_ORG_BaseUnit AS b ON a. FToUnitID = b.FID INNER JOIN T_ORG_TypeRelation AS d ON a.FTypeRelationID = d.FID where d.fid='00000000-0000-0000-0000-0000000000120FE9F8B5' and b.fid='"+id+"'";
					build.appendSql(sql);
					IRowSet set=build.executeQuery();
					int i=-1;
					HashSet<String> hs=new HashSet<String>();
					if(set.size()>0){
						String []arr=new String[set.size()];
							while(set.next()){
								if(set.getString("采购组织ID")!=null && !"".equals(set.getString("采购组织ID").trim())){
									arr[i+1]=set.getString("采购组织ID").trim();
									hs.add(arr[i+1]);
								}
							}
						if(hs.size()==1){
							purOrgUnitInfo =(PurchaseOrgUnitInfo)iPurOrgUnit.getValue(new ObjectUuidPK(arr[0]));
							EntityViewInfo ev=new EntityViewInfo();
							FilterInfo f=new FilterInfo();
							f.getFilterItems().add(new FilterItemInfo("id",hs,CompareType.INCLUDE));
							ev.setFilter(f);
							box.setEntityViewInfo(ev);
						}
						if(hs.size()>1){
							EntityViewInfo ev=new EntityViewInfo();
							FilterInfo f=new FilterInfo();
							f.getFilterItems().add(new FilterItemInfo("id",hs,CompareType.INCLUDE));
							ev.setFilter(f);
							box.setEntityViewInfo(ev);
						}
					}
				}
		}
        	 return purOrgUnitInfo;
	}
	
	/** 
	 * 
	 *版本：EAS7.0
	 *作者（修改人）：钟小花
	 *修改时间：2012-11-29
	 *描述：由行政组织取得库存组织
	 * @throws SQLException 
	 *
	 */
	public static StorageOrgUnitInfo getStorageOrgUnit(AdminOrgUnitInfo adminOrgUnitInfo,KDBizPromptBox box) throws EASBizException,
			BOSException, SQLException {
		StorageOrgUnitInfo sInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
			if (parentCost!= null){
				String id = parentCost.getId().toString();
				if(id!=null){
					IStorageOrgUnit iPurOrgUnit = null;
					iPurOrgUnit = StorageOrgUnitFactory.getRemoteInstance();
					XRSQLBuilder build=new XRSQLBuilder();
					String sql="SELECT c.fid as 库存组织ID FROM T_ORG_UnitRelation AS a LEFT OUTER JOIN T_ORG_BaseUnit AS c ON a.FFromUnitID = c.FID LEFT OUTER JOIN T_ORG_BaseUnit AS b ON a. FToUnitID = b.FID INNER JOIN T_ORG_TypeRelation AS d ON a.FTypeRelationID = d.FID where d.fid='00000000-0000-0000-0000-0000000000140FE9F8B5' and b.fid='"+id+"'";
					build.appendSql(sql);
					IRowSet set=build.executeQuery();
					int i=-1;
					HashSet<String> hs=new HashSet<String>();
					if(set.size()>0){
						String []arr=new String[set.size()];
							while(set.next()){
								if(set.getString("库存组织ID")!=null && !"".equals(set.getString("库存组织ID").trim())){
									arr[i+1]=set.getString("库存组织ID").trim();
									hs.add(arr[i+1]);
								}
							}
							if(hs.size()==1){
								sInfo =(StorageOrgUnitInfo)iPurOrgUnit.getValue(new ObjectUuidPK(arr[0]));
								EntityViewInfo ev=new EntityViewInfo();
								FilterInfo f=new FilterInfo();
								f.getFilterItems().add(new FilterItemInfo("id",hs,CompareType.INCLUDE));
								ev.setFilter(f);
								box.setEntityViewInfo(ev);
							}
							if(hs.size()>1){
								EntityViewInfo ev=new EntityViewInfo();
								FilterInfo f=new FilterInfo();
								f.getFilterItems().add(new FilterItemInfo("id",hs,CompareType.INCLUDE));
								ev.setFilter(f);
								box.setEntityViewInfo(ev);
							}
					}
				}
			}
				
        	 return sInfo;
	}
}
