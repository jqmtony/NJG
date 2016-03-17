package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSwbEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSwbEntryInfo()
    {
        this("id");
    }
    protected AbstractSwbEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.SwbInfo getParent()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.SwbInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.gcftbiaoa.SwbInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 剑property 
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
     * Object:分录's 硬景/绿化面积（平米）property 
     */
    public java.math.BigDecimal getGreenArea()
    {
        return getBigDecimal("GreenArea");
    }
    public void setGreenArea(java.math.BigDecimal item)
    {
        setBigDecimal("GreenArea", item);
    }
    /**
     * Object:分录's 硬景/绿化面积指标（元/㎡）property 
     */
    public java.math.BigDecimal getGreenAreaIndex()
    {
        return getBigDecimal("GreenAreaIndex");
    }
    public void setGreenAreaIndex(java.math.BigDecimal item)
    {
        setBigDecimal("GreenAreaIndex", item);
    }
    /**
     * Object:分录's 占对应面积比例（%）property 
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
     * Object:分录's 综合单价property 
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
     * Object:分录's 单位property 
     */
    public int getDanwei()
    {
        return getInt("danwei");
    }
    public void setDanwei(int item)
    {
        setInt("danwei", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E1AACC1B");
    }
}