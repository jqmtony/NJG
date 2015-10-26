package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataEdateDataInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataEdateDataInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataEdateDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 日期数据 's null property 
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
     * Object:日期数据's 记录号property 
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
     * Object:日期数据's 日期property 
     */
    public java.util.Date getDateData()
    {
        return getDate("dateData");
    }
    public void setDateData(java.util.Date item)
    {
        setDate("dateData", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("072DBAD5");
    }
}