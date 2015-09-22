package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRealDateRelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRealDateRelInfo()
    {
        this("id");
    }
    protected AbstractRealDateRelInfo(String pkField)
    {
        super(pkField);
        put("Entrys", new com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection());
    }
    /**
     * Object: 时间完成时间关系表 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.basedata.RealDateRelEntryCollection)get("Entrys");
    }
    /**
     * Object:时间完成时间关系表's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:时间完成时间关系表's 是否最新property 
     */
    public boolean isLatest()
    {
        return getBoolean("latest");
    }
    public void setLatest(boolean item)
    {
        setBoolean("latest", item);
    }
    /**
     * Object: 时间完成时间关系表 's 工程项目 property 
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
        return new BOSObjectType("FF80291B");
    }
}