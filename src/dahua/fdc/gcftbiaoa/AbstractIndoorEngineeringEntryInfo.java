package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIndoorEngineeringEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractIndoorEngineeringEntryInfo()
    {
        this("id");
    }
    protected AbstractIndoorEngineeringEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringInfo getParent()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��ͷproperty 
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
     * Object:��¼'s ���property 
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
     * Object:��¼'s Ӳ��/�̻������ƽ�ף�property 
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
     * Object:��¼'s Ӳ��/�̻����ָ�꣨Ԫ/�O��property 
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
     * Object:��¼'s ռ����۱�����%��property 
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
     * Object:��¼'s ռ��Ӧ���������%��property 
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
     * Object:��¼'s �ۺϵ���property 
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
     * Object:��¼'s ��λproperty 
     */
    public int getDanwei()
    {
        return getInt("danwei");
    }
    public void setDanwei(int item)
    {
        setInt("danwei", item);
    }
    /**
     * Object:��¼'s ��property 
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
     * Object:��¼'s ����(�豸)���ơ�Ʒ�ơ��ͺż�����property 
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
        return new BOSObjectType("AFCCECD9");
    }
}