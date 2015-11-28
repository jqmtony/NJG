package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildSplitBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildSplitBillEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildSplitBillEntryInfo(String pkField)
    {
        super(pkField);
        put("Details", new com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailCollection());
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 要素property 
     */
    public String getPointName()
    {
        return getString("pointName");
    }
    public void setPointName(String item)
    {
        setString("pointName", item);
    }
    /**
     * Object:分录's 数值property 
     */
    public java.math.BigDecimal getDataValue()
    {
        return getBigDecimal("dataValue");
    }
    public void setDataValue(java.math.BigDecimal item)
    {
        setBigDecimal("dataValue", item);
    }
    /**
     * Object:分录's 是否拆到楼号property 
     */
    public boolean isSplitBuild()
    {
        return getBoolean("splitBuild");
    }
    public void setSplitBuild(boolean item)
    {
        setBoolean("splitBuild", item);
    }
    /**
     * Object: 分录 's 明细数据 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailCollection getDetails()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailCollection)get("Details");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("969D5B38");
    }
}