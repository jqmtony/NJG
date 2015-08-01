package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjCostEntriesInfo extends com.kingdee.eas.fdc.basedata.ProjCostEntriesInfo implements Serializable 
{
    public AbstractCurProjCostEntriesInfo()
    {
        this("id");
    }
    protected AbstractCurProjCostEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 当前工程项目成本分录 's 当前工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A49C2078");
    }
}