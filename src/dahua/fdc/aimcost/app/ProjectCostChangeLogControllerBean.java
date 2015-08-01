package com.kingdee.eas.fdc.aimcost.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectCostChangeLogControllerBean extends AbstractProjectCostChangeLogControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.ProjectCostChangeLogControllerBean");
    
    /**
	 * ��Ŀ�ɱ��仯�����¼������������������ݣ�<p>
	 * 1. Ŀ��ɱ�AimCost�ύʱ�� <br>
	 * 2. ��̬�ɱ�DynamicCost�ύʱ��<br>
	 * 3. FDCCostRptFacade��_submitAIMAimProductCost(Context, Map)��_submitAimProductCost(Context, Map)ʱ<br>
	 * 4. �ɱ�����MeasureCost����ʱ<br>
	 * 5. FDCCostSplit�����仯ʱ<br>
	 */
	protected boolean _insertLog(Context ctx, Set prjSet) throws BOSException {
		if(prjSet==null||prjSet.size()==0){
			return false;
		}
		try {
			String sql = "insert into T_AIM_ProjectCostChangeLog (fid,fprojectid,fhaschanged) values(?,?,?)";
			List paramList = new ArrayList();
			ProjectCostChangeLogInfo logInfo = new ProjectCostChangeLogInfo();
			for (Iterator iter = prjSet.iterator(); iter.hasNext();) {
				String id = BOSUuid.create(logInfo.getBOSType()).toString();
				String prjId = (String) iter.next();
				Boolean hasChanged = Boolean.TRUE;
				paramList.add(Arrays.asList(new Object[] { id, prjId, hasChanged }));
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.executeBatch(sql, paramList);
		}catch(Exception e){
			logger.error("insert log err", e);
			return false;
		}
		
		return true;
	}

	protected boolean _updateLog(Context ctx, Set prjSet) throws BOSException {
		if(prjSet==null||prjSet.size()==0){
			return false;
		}
		try {
			String sql = "update T_AIM_ProjectCostChangeLog set fhaschanged=? where fprojectid=?";
			List paramList = new ArrayList();
			for (Iterator iter = prjSet.iterator(); iter.hasNext();) {
				String prjId = (String) iter.next();
				Boolean hasChanged = Boolean.FALSE;
				paramList.add(Arrays.asList(new Object[] { hasChanged,prjId}));
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.executeBatch(sql, paramList);
		}catch(Exception e){
			logger.error("insert log err", e);
			return false;
		}
		
		return true;
	}
	protected boolean _deleteLog(Context ctx) throws BOSException {
		try{
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("delete from T_AIM_ProjectCostChangeLog where fhaschanged=0");
			builder.execute();
		}catch(Exception e){
			logger.error("delete log err", e);
			return false;
		}
		return true;
	}
	protected Set _getChangePrjs(Context ctx, Set prjSet) throws BOSException {
		Set prjSets=new HashSet();
		if(prjSet==null||prjSet.size()==0){
			return prjSets;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct fprojectid from T_AIM_ProjectCostChangeLog where fhaschanged=1 and ");
		builder.appendParam("fprojectid", prjSet.toArray());
		IRowSet rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				prjSets.add(rowSet.getString("fprojectid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return prjSets;
	}

	protected Set _getAllChangePrjs(Context ctx) throws BOSException {
		Set prjSets=new HashSet();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct fprojectid from T_AIM_ProjectCostChangeLog where fhaschanged=1");
		IRowSet rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				prjSets.add(rowSet.getString("fprojectid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return prjSets;
	}

}