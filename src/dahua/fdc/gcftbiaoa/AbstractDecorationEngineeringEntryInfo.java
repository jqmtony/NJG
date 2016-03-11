package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDecorationEngineeringEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDecorationEngineeringEntryInfo()
    {
        this("id");
    }
    protected AbstractDecorationEngineeringEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo getParent()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 装修面积property 
     */
    public java.math.BigDecimal getDecorationArea()
    {
        return getBigDecimal("decorationArea");
    }
    public void setDecorationArea(java.math.BigDecimal item)
    {
        setBigDecimal("decorationArea", item);
    }
    /**
     * Object:分录's 造价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("Price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("Price", item);
    }
    /**
     * Object:分录's 装修面积指标（元/㎡）property 
     */
    public java.math.BigDecimal getRenovationAreaIndex()
    {
        return getBigDecimal("renovationAreaIndex");
    }
    public void setRenovationAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("renovationAreaIndex", item);
    }
    /**
     * Object:分录's 占相应总造价比例（%）property 
     */
    public java.math.BigDecimal getProportion()
    {
        return getBigDecimal("proportion");
    }
    public void setProportion(java.math.BigDecimal item)
    {
        setBigDecimal("proportion", item);
    }
    /**
     * Object:分录's 装修套数property 
     */
    public java.math.BigDecimal getRenovationNumber()
    {
        return getBigDecimal("renovationNumber");
    }
    public void setRenovationNumber(java.math.BigDecimal item)
    {
        setBigDecimal("renovationNumber", item);
    }
    /**
     * Object:分录's 总造价property 
     */
    public java.math.BigDecimal getSumPrice()
    {
        return getBigDecimal("sumPrice");
    }
    public void setSumPrice(java.math.BigDecimal item)
    {
        setBigDecimal("sumPrice", item);
    }
    /**
     * Object:分录's 总装修面积property 
     */
    public java.math.BigDecimal getSumDecorationArea()
    {
        return getBigDecimal("sumDecorationArea");
    }
    public void setSumDecorationArea(java.math.BigDecimal item)
    {
        setBigDecimal("sumDecorationArea", item);
    }
    /**
     * Object:分录's 总装修面积指标（元/㎡）property 
     */
    public java.math.BigDecimal getSumRenovationAreaIndex()
    {
        return getBigDecimal("sumRenovationAreaIndex");
    }
    public void setSumRenovationAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("sumRenovationAreaIndex", item);
    }
    /**
     * Object:分录's 平均装修面积指标（元/㎡）property 
     */
    public java.math.BigDecimal getAvgRenovationAreaIndex()
    {
        return getBigDecimal("avgRenovationAreaIndex");
    }
    public void setAvgRenovationAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("avgRenovationAreaIndex", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getRemak()
    {
        return getString("remak");
    }
    public void setRemak(String item)
    {
        setString("remak", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("614539B6");
    }
}