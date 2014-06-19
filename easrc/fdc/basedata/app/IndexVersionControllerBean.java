package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ProjectBaseInvestListEntryFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class IndexVersionControllerBean extends AbstractIndexVersionControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.IndexVersionControllerBean");
    
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("level.id", arg1.toString()));
		if(ProjectBaseInvestListEntryFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经投资分析指标分录引用，不能进行删除操作！"));
		}
	}
}