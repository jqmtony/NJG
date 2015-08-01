package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.rpc.RPCException;
import com.kingdee.eas.util.client.ExceptionHandler;

public class CurProjectInfo extends AbstractCurProjectInfo implements Serializable 
{
    public CurProjectInfo()
    {
        super();
    }
    protected CurProjectInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * 保留原来的接口，从指标管理取数
     * @return
     */
    public CurProjCostEntriesCollection getCurProjCostEntries() {
    	String id = getId()==null?null:getId().toString();
		return ProjectHelper.getCurProjCostEntries(id);
    }
}