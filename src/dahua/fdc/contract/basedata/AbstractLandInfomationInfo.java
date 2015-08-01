package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLandInfomationInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractLandInfomationInfo()
    {
        this("id");
    }
    protected AbstractLandInfomationInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:土地信息's 土地名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: 土地信息 's 城市 property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getCity()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("city");
    }
    public void setCity(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("city", item);
    }
    /**
     * Object: 土地信息 's 所属公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:土地信息's 获取日期property 
     */
    public java.util.Date getObtainDate()
    {
        return getDate("obtainDate");
    }
    public void setObtainDate(java.util.Date item)
    {
        setDate("obtainDate", item);
    }
    /**
     * Object:土地信息's 获取方式property 
     */
    public com.kingdee.eas.fdc.basedata.ObtainTypeEnum getObtainType()
    {
        return com.kingdee.eas.fdc.basedata.ObtainTypeEnum.getEnum(getInt("obtainType"));
    }
    public void setObtainType(com.kingdee.eas.fdc.basedata.ObtainTypeEnum item)
    {
		if (item != null) {
        setInt("obtainType", item.getValue());
		}
    }
    /**
     * Object:土地信息's 总地价property 
     */
    public java.math.BigDecimal getTotalLandPrice()
    {
        return getBigDecimal("totalLandPrice");
    }
    public void setTotalLandPrice(java.math.BigDecimal item)
    {
        setBigDecimal("totalLandPrice", item);
    }
    /**
     * Object:土地信息's 容积率property 
     */
    public double getPlotRatio()
    {
        return getDouble("plotRatio");
    }
    public void setPlotRatio(double item)
    {
        setDouble("plotRatio", item);
    }
    /**
     * Object:土地信息's 可售楼面单方地价property 
     */
    public java.math.BigDecimal getUnitPrice()
    {
        return getBigDecimal("unitPrice");
    }
    public void setUnitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("unitPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F47B6C93");
    }
}