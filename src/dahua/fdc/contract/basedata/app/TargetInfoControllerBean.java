package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BITargetCollection;
import com.kingdee.eas.fdc.basedata.BITargetFactory;
import com.kingdee.eas.fdc.basedata.BITargetInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection;
import com.kingdee.eas.fdc.basedata.TargetInfoEntryInfo;
import com.kingdee.eas.fdc.basedata.TargetInfoInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class TargetInfoControllerBean extends AbstractTargetInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.TargetInfoControllerBean");
    
    public IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	TargetInfoInfo info = (TargetInfoInfo) model;
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("where year= '").append(info.getYear()).append("' and month = '")
    	  .append(info.getMonth()).append("' and curProject.id = '")
    	  .append(info.getCurProject().getId().toString()).append("'");
    	if(info.getId() != null) {
    		sb.append(" and id != '").append(info.getId().toString()).append("'");
    	}
    	
    	if(this._exists(ctx, sb.toString())) {
    		throw new EASBizException(new NumericExceptionSubItem("duplicate error", "工程项目已存在当前编制年月的单据。"));
    	}
    	
    	return super._save(ctx, model);
    }
    
    public TargetInfoEntryCollection _getInitedCollection(Context ctx, String prjId) throws BOSException {
    	String sql = " Select id,TargetName,TargetId,dataType,fromWhichGroup,statisticsType where TargetGroup = '9'  ";
    	BITargetCollection coll = BITargetFactory.getLocalInstance(ctx).getBITargetCollection(sql);
    	Map valueMap = getLatestValueMap(ctx, prjId);
    	
    	TargetInfoEntryCollection entrys = new TargetInfoEntryCollection();
    	for(int i=0;i<coll.size();i++){
    		TargetInfoEntryInfo entry = new TargetInfoEntryInfo();
    		BITargetInfo bITarget = coll.get(i);
    		entry.setTargetName(bITarget.getTargetName());
    		entry.setTargetId(bITarget.getTargetId());
    		entry.setDataType(bITarget.getDataType());
    		entry.setTargetGroup(bITarget.getFromWhichGroup());
    		entry.setStatisticsType(bITarget.getStatisticsType());
    		entry.setValue((String) valueMap.get(bITarget.getTargetId()));
    		entrys.add(entry);
    	}
    	return entrys;
    }
    
    public Map getLatestValueMap(Context ctx, String prjId) throws BOSException {
    	HashMap resultMap = new HashMap();
    	StringBuilder sb = new StringBuilder();
    	sb.append("select entry.ftargetId targetId, entry.fvalue value from")
    	  .append(" T_FDC_TargetInfoEntry entry")
    	  .append(" inner join T_FDC_TargetInfo head on entry.fHeadId=head.Fid")
    	  .append(" where head.fCurProjectId = '").append(prjId).append("'")
    	  .append(" and entry.ftargetId in ('openTime', 'openTimeCycle', 'operatingTime', 'operatingTimeCycle')")
    	  .append(" and head.fid = ")
    	  .append("select top 1 fid from T_FDC_TargetInfo where fCurProjectId = '").append(prjId).append("'")
    	  .append(" order by fyear*100+fmonth desc");
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sb.toString());
    	try {
			for(IRowSet rowSet = builder.executeQuery(); rowSet.next();) {
				resultMap.put(rowSet.getString("targetId"), rowSet.getString("value"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
//			e.printStackTrace();
		}
    	
    	return resultMap;
    }
	
}