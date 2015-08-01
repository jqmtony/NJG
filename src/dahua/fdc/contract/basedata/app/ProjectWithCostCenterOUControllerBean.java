package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * ����:������Ŀ�ͳɱ�������֯��Ӧ��ϵ
 * @author jackwang  date:2006-8-4 <p>
 * @version EAS5.1
 */
public class ProjectWithCostCenterOUControllerBean extends AbstractProjectWithCostCenterOUControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectWithCostCenterOUControllerBean");
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
		super._update(ctx,pk,model);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
	EASBizException {
		super._delete(ctx,pk);
	}
	protected void _submitAll(Context ctx, IObjectCollection objCol) throws BOSException, EASBizException {
		if(objCol==null)
			return ;
		//sort 
		FDCHelper.sortObjectCollection((AbstractObjectCollection)objCol,new Comparator(){
			private Collator comparator = Collator.getInstance(Locale.getDefault());
			public int compare(Object o1, Object o2) {
				if(o1==null&&o2==null){
					return 0;
				}
				if(o1!=null&&o2==null){
					return 1;
				}
				if(o1==null&&o2!=null){
					return -1;
				}

				ProjectWithCostCenterOUInfo info1=(ProjectWithCostCenterOUInfo)o1;
				ProjectWithCostCenterOUInfo info2=(ProjectWithCostCenterOUInfo)o2;

				CurProjectInfo prj1 =info1.getCurProject(); 
				CurProjectInfo prj2 =info2.getCurProject();
				return comparePrj(prj1,prj2);
			
			}
			private int comparePrj(CurProjectInfo info1,CurProjectInfo info2){
				String curNumber1 = info1.getLongNumber();
				String curNumber2 = info2.getLongNumber();
				if(FDCHelper.isEmpty(curNumber1)){
					return -1;
				}
				if(FDCHelper.isEmpty(curNumber2)){
					return 1;
				}
				int value=compareNumber(curNumber1, curNumber2);
				return value;
				
			}
			
			private int compareNumber(String number1, String number2) {
/*				String nums1[] = number1.split("!");
				String nums2[] = number2.split("!");
				int value = 0;
				for (int i = 0; i < nums1.length && i < nums2.length; i++) {
					// value=nums1[i].compareTo(nums2[i]);
					value = comparator.compare(nums1[i], nums2[i]);
					if (value != 0) {
						return value;
					}
				}
				// number1,number2���̲�һ����ǰ�벿����һ����
				value = nums2.length - nums1.length;*/
				int value = comparator.compare(number1, number2);
				return value;
			}
		});
		int count = objCol.size();
		IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getLocalInstance(ctx);
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		Set set=new HashSet();
		set.add(OrgConstants.SYS_CU_ID);
		set.add(ContextUtil.getCurrentCtrlUnit(ctx).getId().toString());
		view.getFilter().getFilterItems().add(new FilterItemInfo("costCenterOU.CU.id",set,CompareType.INCLUDE));
		iProjectWithCostCenterOU.delete(view.getFilter());
//		ProjectWithCostCenterOUCollection oldCollection  = iProjectWithCostCenterOU.getProjectWithCostCenterOUCollection();
//		ProjectWithCostCenterOUCollection newCollection  = (ProjectWithCostCenterOUCollection)objCol;
		//���ھɼ�������Ķ���,���¼�������û�е�,Ҫɾ��
/*		for(int i = 0;i<oldCollection.size();i++){			
				if(!newCollection.containsKey((oldCollection.get(i).getId()))){
					//todo ��Ҫ�ж�����
					_delete(ctx,new ObjectUuidPK(oldCollection.get(i).getId().toString()));
				}			
		}*/
		
		//������߸����¼��϶���
		for (int i = 0; i <count; i++) {
			ProjectWithCostCenterOUInfo info = (ProjectWithCostCenterOUInfo)objCol.getObject(i);
			
			//�ж�����
			
			//����
			_submit(ctx,info);
			
			//���¹�����Ŀ	
			updateProject(ctx,info.getCurProject(),info.getCostCenterOU());
		}
	}
	protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return null;
	}

	//���¹�����Ŀ ��Ӧ�ĳɱ�����
	protected void updateProject(Context ctx,CurProjectInfo project,CostCenterOrgUnitInfo orgUnit) throws BOSException{
		
		String sql = "  update t_fdc_curproject  set fcostcenterId =? where fid in  ( "+
           			 "  select ch.fid from t_fdc_curproject pa   "+         
         			 "  inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1 " +
         			 "  where pa.fid=? " +
         			 "  ) " ;
		
		
		DbUtil.execute(ctx,sql,new Object[]{orgUnit.getId().toString(),project.getId().toString()});
		
		if(!project.isIsLeaf()){
			return;
		}
		//������Ӧ�ĵ��ݳɱ�����
		try{
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select ch.fid from t_fdc_curproject pa ");
			builder.appendSql("inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1 ");
			builder.appendSql("where pa.fid=?");
			builder.addParam(project.getId().toString());
			IRowSet rowSet=builder.executeQuery();
			String [] leafIds=new String[rowSet.size()];
			int i=0;
			while(rowSet.next()){
				leafIds[i]=rowSet.getString("fid");
				i++;
			}
			String orgUnitId=orgUnit.getId().toString();
			//��ͬ
			List sqlList=new ArrayList();
			String contractSql="update T_Con_ContractBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String changetSql="update T_Con_ContractChangeBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String settleSql="update T_Con_ContractSettlementBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String payReqSql="update T_Con_PayRequestBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String changetAuditSql="update T_CON_ChangeAuditBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String compensateSql="update T_Con_CompensationBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String guerdonSql="update T_Con_GuerdonBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String deductSql="update T_FNC_deductBill set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String conSplitSql="update T_Con_ContractCostSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String settleSplitSql="update T_Con_SettlementCostSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String changeSplitSql="update T_Con_ConChangeSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String conNoSplitSql="update T_Con_ConNoCostSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String changeNoSplitSql="update T_Con_ConChangeNoCostSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			String settNoSplitSql="update T_Con_SettNoCostSplit set forgUnitId='"+orgUnitId+"' where "+getInSql("fcurProjectId", leafIds);
			
			//��д������Ŀ
			String updatePrjSql="update T_FDC_CurProject set fcostCenterid=null where fcostcenterid not in (select fcostcenterouid from t_Fdc_Projectwithcostcenterou)";
			sqlList.add(contractSql);
			sqlList.add(changetSql);
			sqlList.add(settleSql);
			sqlList.add(payReqSql);
			sqlList.add(payReqSql);
			sqlList.add(changetAuditSql);
			sqlList.add(compensateSql);
			sqlList.add(guerdonSql);
			sqlList.add(deductSql);
			sqlList.add(settleSplitSql);
			sqlList.add(changeSplitSql);
			sqlList.add(conNoSplitSql);
			sqlList.add(changeNoSplitSql);
			sqlList.add(settNoSplitSql);
			sqlList.add(conSplitSql);
			sqlList.add(updatePrjSql);
			builder.executeBatch(sqlList);
			//
		}catch(SQLException e){
			throw new BOSException(e);
		}
	}
	private String getInSql(String key,String []params){
		StringBuffer sql=new StringBuffer(key+" in (");
		for(int i=0;i<params.length;i++){
			if(i>0){
				sql.append(",'"+params[i]+"'");
			}else{
				sql.append("'"+params[i]+"'");
			}
		}
		sql.append(')');
		return sql.toString();
	}
	
}