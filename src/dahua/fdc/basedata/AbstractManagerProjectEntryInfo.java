package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractManagerProjectEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractManagerProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractManagerProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:管理项目分录's 主键property 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 管理项目分录 's 内部管理 property 
     */
    public com.kingdee.eas.fdc.basedata.InnerManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.InnerManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.InnerManagerInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 管理项目分录 's 工程项目 property 
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
        return new BOSObjectType("53DDECF4");
    }
}