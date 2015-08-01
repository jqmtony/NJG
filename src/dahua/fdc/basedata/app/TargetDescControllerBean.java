package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.TargetDescInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class TargetDescControllerBean extends AbstractTargetDescControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.TargetDescControllerBean");
    
    public IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	TargetDescInfo info = (TargetDescInfo) model;
    	
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
} 