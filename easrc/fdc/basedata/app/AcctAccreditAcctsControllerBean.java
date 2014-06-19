package com.kingdee.eas.fdc.basedata.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AcctAccreditAcctsInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;

public class AcctAccreditAcctsControllerBean extends AbstractAcctAccreditAcctsControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.AcctAccreditAcctsControllerBean");
    public Result submit(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException {
    	CoreBaseCollection c=colls;
    	if(c==null||c.isEmpty()){
    		return null;
    	}
    	AcctAccreditAcctsInfo acctAccreditAcctsInfo = (AcctAccreditAcctsInfo)c.get(0);
    	if(acctAccreditAcctsInfo.getScheme()==null||acctAccreditAcctsInfo.getScheme().getId()==null){
    		return null;
    	}
		String schemeId=acctAccreditAcctsInfo.getScheme().getId().toString();
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("delete from T_FDC_AcctAccreditAccts where fschemeid=?");
    	builder.addParam(schemeId);
    	builder.execute();
    	if(acctAccreditAcctsInfo.getCostAccount()==null){
    		return null;
    	}
    	builder.clear();
    	String sql="insert into T_FDC_AcctAccreditAccts (fid,fcostAccountId,fschemeid) values(?,?,?)";
    	List list=new ArrayList();
    	for(Iterator iter=c.iterator();iter.hasNext();)	{
    		AcctAccreditAcctsInfo info=(AcctAccreditAcctsInfo)iter.next();
    		String fid=info.getId()!=null?info.getId().toString():BOSUuid.create(info.getBOSType()).toString();
    		String fcostAccountId=info.getCostAccount().getId().toString();
    		String fschemeId=info.getScheme().getId().toString();
    		list.add(Arrays.asList(new String[]{
    			fid,fcostAccountId,fschemeId
    		}));
    	}
    	builder.executeBatch(sql, list);
    	return null;
    }
}