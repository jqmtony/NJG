package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIndoorengEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractIndoorengEntryInfo()
    {
        this("id");
    }
    protected AbstractIndoorengEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo getParent()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 键property 
     */
    public String getKey()
    {
        return getString("key");
    }
    public void setKey(String item)
    {
        setString("key", item);
    }
    /**
     * Object:分录's 表头property 
     */
    public String getTitle()
    {
        return getString("title");
    }
    public void setTitle(String item)
    {
        setString("title", item);
    }
    /**
     * Object:分录's 装修面积/工程量（平米）property 
     */
    public java.math.BigDecimal getDecorateArea()
    {
        return getBigDecimal("DecorateArea");
    }
    public void setDecorateArea(java.math.BigDecimal item)
    {
        setBigDecimal("DecorateArea", item);
    }
    /**
     * Object:分录's 装修面积指标（元/㎡）property 
     */
    public java.math.BigDecimal getDecorateAreaIndex()
    {
        return getBigDecimal("DecorateAreaIndex");
    }
    public void setDecorateAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("DecorateAreaIndex", item);
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
     * Object:分录's 占总造价比例（%）property 
     */
    public java.math.BigDecimal getSumproportion()
    {
        return getBigDecimal("Sumproportion");
    }
    public void setSumproportion(java.math.BigDecimal item)
    {
        setBigDecimal("Sumproportion", item);
    }
    /**
     * Object:分录's 综合单价（元/平米）property 
     */
    public java.math.BigDecimal getComePrice()
    {
        return getBigDecimal("ComePrice");
    }
    public void setComePrice(java.math.BigDecimal item)
    {
        setBigDecimal("ComePrice", item);
    }
    /**
     * Object:分录's 主材价property 
     */
    public java.math.BigDecimal getAreaproportion()
    {
        return getBigDecimal("Areaproportion");
    }
    public void setAreaproportion(java.math.BigDecimal item)
    {
        setBigDecimal("Areaproportion", item);
    }
    /**
     * Object:分录's 计量单位property 
     */
    public String getDanwei()
    {
        return getString("danwei");
    }
    public void setDanwei(String item)
    {
        setString("danwei", item);
    }
    /**
     * Object:分录's 主材(设备)名称、品牌、型号及其他property 
     */
    public String getOther()
    {
        return getString("other");
    }
    public void setOther(String item)
    {
        setString("other", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C07D8FCE");
    }
}