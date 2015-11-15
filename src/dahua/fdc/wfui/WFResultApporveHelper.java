package com.kingdee.eas.fdc.wfui;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class WFResultApporveHelper {

	
	public static Map<String, String> getApporveResultForMap(String billId){
		Map<String,String> resultMap = new HashMap<String,String>();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.FOpinion_l2,b.FACTDEFNAME_l2,b.FPERSONEMPNAME_l2,a.FCreateTime from T_BAS_MultiApprove a  ");
		sb.append(" left join T_WFR_AssignDetail b on b.FASSIGNID=a.FASSIGNMENTID ");
		sb.append(" where a.FBillID='"+billId+"' order by b.FACTDEFNAME_l2,a.FCreateTime desc ");
		
		FDCSQLBuilder build = new FDCSQLBuilder();
		build.appendSql(sb.toString());
		
		try {
			//1、审批意见   2、审批部门   3、审批人   4、审批时间
			IRowSet executeQuery = build.executeQuery();
			while(executeQuery.next()){
				String result = executeQuery.getString(1)!=null?executeQuery.getString(1):"";
				String actName = executeQuery.getString(2)!=null?executeQuery.getString(2):"";
				String person = executeQuery.getString(3)!=null?executeQuery.getString(3):"";
				String date = executeQuery.getString(4)!=null?executeQuery.getString(4):"";
				if(resultMap.get(actName)==null)
					resultMap.put(actName, person+"!"+result+"@"+date);
				
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	public static Map<String, String> getApporveResultForPerson(String billId){
		Map<String,String> resultMap = new HashMap<String,String>();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.FOpinion_l2,b.FACTDEFNAME_l2,b.FPERSONEMPNAME_l2,a.FCreateTime from T_BAS_MultiApprove a  ");
		sb.append(" left join T_WFR_AssignDetail b on b.FASSIGNID=a.FASSIGNMENTID ");
		sb.append(" where a.FBillID='"+billId+"' order by b.FACTDEFNAME_l2,a.FCreateTime desc ");
		
		FDCSQLBuilder build = new FDCSQLBuilder();
		build.appendSql(sb.toString());
		
		try {
			//1、审批意见   2、审批部门   3、审批人   4、审批时间
			IRowSet executeQuery = build.executeQuery();
			while(executeQuery.next()){
				String result = executeQuery.getString(1)!=null?executeQuery.getString(1):"";
				String actName = executeQuery.getString(2)!=null?executeQuery.getString(2):"";
				String person = executeQuery.getString(3)!=null?executeQuery.getString(3):"";
				if(resultMap.get(actName)==null)
					resultMap.put(actName, person+":"+result);
				
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
