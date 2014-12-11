package com.kingdee.eas.port.pm.invite.app;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.fi.arap.app.util.TempTableUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class EvaluationControllerBean extends AbstractEvaluationControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invite.app.EvaluationControllerBean");

	protected String _TDTempTable(Context ctx, String pk, String param)throws BOSException {
		String tempTablefee = "";
		String gys = "";
		String gys2 = "";
		String sql = "select cfenterprise from CT_INV_EvaluationEntryUnit  where fparentid='brj1LK9iTFy9ZgOBprhRHU2+aUU='  order by fseq asc";
		List list = new ArrayList();
		Map mapSum = new HashMap();
		Map map = new HashMap();
		map.put("cfjudges", "评标专家");
		map.put("cfindicators", "项目内容");
		map.put("cffullscore", "满分");
		int gysCount = 0;
		try {
			IRowSet rs = DbUtil.executeQuery(ctx, sql);
			int i=1;
			while(rs.next()){//供应商
				if(i==1){
					gys = "gys"+i+" varchar(100)";
					gys2 = "gys"+i+" ";
				}else{
					gys = gys+",gys"+i+" varchar(100)";
					gys2 = gys2+",gys"+i+" ";
				}
				map.put("gys"+i,rs.getString("cfenterprise"));
				gysCount= rs.size();
				i++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		list.add(map);
		mapSum.put("0", map);
		String compTblDefinefee = " (cfjudges varchar(100)," +
		"cfindicators varchar(100),"+
		"cffullscore varchar(100), " +
		gys +
		") ";
		
		String insertField = " (cfjudges ," +
		"cfindicators ,"+
		"cffullscore , " +
		gys2 +
		") ";
		TempTablePool tfee = null;
		try {
			tempTablefee = TempTableUtil.createTempTable(ctx, compTblDefinefee);
			tfee = TempTablePool.getInstance(ctx);
			tfee.getName();
			tfee.createIndex(tempTablefee, "cfjudges", false, false); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for (int i = 1; i <= gysCount; i++) {
			sql = getTaoDaSql(String.valueOf(((Map)list.get(0)).get("gys"+i)));
			try {
				IRowSet rs = DbUtil.executeQuery(ctx, sql);
				while(rs.next()){
					String key = rs.getString("cfjudges")+rs.getString("cfindicators")+rs.getString("cffullscore");
					if(mapSum.get(key)!=null){
						map = (Map)mapSum.get(key);
						map.put("gys"+i,rs.getString("cfscore"));
					}else{
						map = new HashMap();
						map.put("cfjudges", rs.getString("cfjudges"));
						map.put("cfindicators",rs.getString("cfindicators"));
						map.put("cffullscore", rs.getString("cffullscore"));
						map.put("gys"+i,rs.getString("cfscore"));
					}
					mapSum.put(key, map);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		Iterator it = mapSum.entrySet().iterator();    
		while (it.hasNext())     
		{    
		        Map.Entry pairs = (Map.Entry)it.next();    
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());   
		        
		        Iterator it2 = ((Map)pairs.getValue()).entrySet().iterator();  
		        int k = 1;
		        String value = "";
				while (it2.hasNext())     
				{    
					 pairs = (Map.Entry)it2.next();    
					if(k==1){
						value = "'"+String.valueOf(pairs.getValue())+"'";
					}else{
						
						value = value +","+"'"+String.valueOf(pairs.getValue())+"'";
					}
					k++;
				}
		        StringBuffer insertsql = new StringBuffer();
				insertsql.append("insert into ").append(tempTablefee);
				insertsql.append(insertField);
				insertsql.append(" values (");
				insertsql.append(value);
				insertsql.append(" ) ");
				
		        try
		        {
		        	Connection con = null;
					Statement stmt = null;
					con = EJBFactory.getConnection(ctx);
					stmt = con.createStatement();
					logger.debug("insert comp table \r\n " + insertsql);
		        	stmt.execute(insertsql.toString());
		        	tfee.analyzeTable(con, tempTablefee); 
		        }catch (Exception e) { 
		      	  e.printStackTrace(System.out);
		        }
		 }  
		return tempTablefee;
	}
	
	String getTaoDaSql(String gys){
		StringBuffer sb = new StringBuffer();
		sb.append(" select  distinct u.cfenterprise,s.cfseq ,s.fseq,s.cfjudges,s.cfindicators,cffullscore,cfscore from CT_INV_Evaluation e");
		sb.append(" left join CT_INV_EvaluationEntryScore s on s.fparentid=e.fid  ");
		sb.append(" left join CT_INV_EvaluationEntryUnit u on u.fparentid=e.fid and u.fseq=s.cfseq");
		sb.append(" where e.fid='brj1LK9iTFy9ZgOBprhRHU2+aUU=' and s.cfjudges='杨坚东' and u.cfenterprise='"+gys+"' "); 
		sb.append(" order by s.cfseq asc,s.fseq asc");
		return sb.toString();
	}
    
}