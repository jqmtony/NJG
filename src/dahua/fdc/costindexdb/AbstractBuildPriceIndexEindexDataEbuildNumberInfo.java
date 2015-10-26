package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataEbuildNumberInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataEbuildNumberInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataEbuildNumberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 楼号数据 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo getParent1()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:楼号数据's 记录号property 
     */
    public String getRecordSeq()
    {
        return getString("recordSeq");
    }
    public void setRecordSeq(String item)
    {
        setString("recordSeq", item);
    }
    /**
     * Object: 楼号数据 's 楼号 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo getBuildNumber()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo)get("buildNumber");
    }
    public void setBuildNumber(com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo item)
    {
        put("buildNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("433C1BDA");
    }
}