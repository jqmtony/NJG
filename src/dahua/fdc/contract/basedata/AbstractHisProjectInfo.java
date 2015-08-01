package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHisProjectInfo extends com.kingdee.eas.fdc.basedata.ProjectInfo implements Serializable 
{
    public AbstractHisProjectInfo()
    {
        this("id");
    }
    protected AbstractHisProjectInfo(String pkField)
    {
        super(pkField);
        put("hisProjCostEntries", new com.kingdee.eas.fdc.basedata.HisProjCostEntriesCollection());
        put("hisProjProductEntries", new com.kingdee.eas.fdc.basedata.HisProjProductEntriesCollection());
    }
    /**
     * Object: 历史工程项目 's 当前工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 历史工程项目 's 历史工程项目产品设置分录 property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjProductEntriesCollection getHisProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjProductEntriesCollection)get("hisProjProductEntries");
    }
    /**
     * Object: 历史工程项目 's 历史工程项目成本分录 property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjCostEntriesCollection getHisProjCostEntries()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjCostEntriesCollection)get("hisProjCostEntries");
    }
    /**
     * Object:历史工程项目's 仅用于目标成本property 
     */
    public boolean isOnlyApplyObjCost()
    {
        return getBoolean("onlyApplyObjCost");
    }
    public void setOnlyApplyObjCost(boolean item)
    {
        setBoolean("onlyApplyObjCost", item);
    }
    /**
     * Object:历史工程项目's 版本名称property 
     */
    public String getVersionName()
    {
        return getVersionName((Locale)null);
    }
    public void setVersionName(String item)
    {
		setVersionName(item,(Locale)null);
    }
    public String getVersionName(Locale local)
    {
        return TypeConversionUtils.objToString(get("versionName", local));
    }
    public void setVersionName(String item, Locale local)
    {
        put("versionName", item, local);
    }
    /**
     * Object:历史工程项目's 版本号property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object: 历史工程项目 's 父结点 property 
     */
    public com.kingdee.eas.fdc.basedata.HisProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.HisProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.HisProjectInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("34011799");
    }
}