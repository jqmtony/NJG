package com.kingdee.eas.fdc.basedata.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;

public class AcctAccreditUserControllerBean extends AbstractAcctAccreditUserControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.AcctAccreditUserControllerBean");
    
    public Result save(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException {
    	if(colls==null||colls.size()==0){
    		return null;
    	}
    	AcctAccreditUserInfo user=(AcctAccreditUserInfo)colls.get(0);
    	String schemeId=user.getScheme().getId().toString();
    	//delete
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("delete from T_FDC_AcctAccreditUser where fschemeid=?");
    	builder.addParam(schemeId);
    	builder.execute();
    	
    	if(colls.size()==1&&user.getRole()==null){
    		//½ö×öÉ¾³ý
    		return null;
    	}
    	//save
    	builder.clear();

		List params = new ArrayList();
		for (Iterator iter = colls.iterator(); iter.hasNext();) {
			AcctAccreditUserInfo info = (AcctAccreditUserInfo) iter.next();
			params.add(Arrays.asList(new Object[] { info.getId().toString(), schemeId, info.getRole().getId().toString() }));

		}
		String insertSql = "insert into T_FDC_AcctAccreditUser (fid,fschemeid,froleid) values(?,?,?) ";
		builder.executeBatch(insertSql, params);
    	return null;
    }
}