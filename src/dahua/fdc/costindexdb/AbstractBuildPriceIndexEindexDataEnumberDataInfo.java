package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataEnumberDataInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataEnumberDataInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataEnumberDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 数字数据 's null property 
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
     * Object:数字数据's 记录号property 
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
     * Object:数字数据's 数字property 
     */
    public java.math.BigDecimal getNumberData()
    {
        return getBigDecimal("numberData");
    }
    public void setNumberData(java.math.BigDecimal item)
    {
        setBigDecimal("numberData", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D6303DB0");
    }
}