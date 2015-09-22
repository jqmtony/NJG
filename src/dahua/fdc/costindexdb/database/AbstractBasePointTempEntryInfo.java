package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePointTempEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBasePointTempEntryInfo()
    {
        this("id");
    }
    protected AbstractBasePointTempEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BasePointTempInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BasePointTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.database.BasePointTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 要素名称property 
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
     * Object: 分录 's 计量单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnitBase()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unitBase");
    }
    public void setUnitBase(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unitBase", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FE15A4E6");
    }
}