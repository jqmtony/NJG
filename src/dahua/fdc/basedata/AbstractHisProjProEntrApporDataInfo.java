package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHisProjProEntrApporDataInfo extends com.kingdee.eas.fdc.basedata.ProjProEntrApporDataInfo implements Serializable 
{
    public AbstractHisProjProEntrApporDataInfo()
    {
        this("id");
    }
    protected AbstractHisProjProEntrApporDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 历史工程项目设置分摊数据 's 历史工程项目产品设置分录 property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjProductEntriesInfo getHisProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjProductEntriesInfo)get("hisProjProductEntries");
    }
    public void setHisProjProductEntries(com.kingdee.eas.fdc.basedata.HisProjProductEntriesInfo item)
    {
        put("hisProjProductEntries", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("324293B7");
    }
}