package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinglePointTempEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSinglePointTempEntryInfo()
    {
        this("id");
    }
    protected AbstractSinglePointTempEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.SinglePointTempInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.SinglePointTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.database.SinglePointTempInfo item)
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
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getBaseUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("baseUnit");
    }
    public void setBaseUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("baseUnit", item);
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
    /**
     * Object: 分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAcount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAcount");
    }
    public void setCostAcount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAcount", item);
    }
    /**
     * Object:分录's 成本科目简码property 
     */
    public String getAccouNumber()
    {
        return getString("accouNumber");
    }
    public void setAccouNumber(String item)
    {
        setString("accouNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E05DC85D");
    }
}