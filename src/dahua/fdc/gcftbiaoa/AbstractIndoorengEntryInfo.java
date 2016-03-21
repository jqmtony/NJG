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
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s װ�����/��������ƽ�ף�property 
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
     * Object:��¼'s װ�����ָ�꣨Ԫ/�O��property 
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
     * Object:��¼'s �ۺϵ��ۣ�Ԫ/ƽ�ף�property 
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
     * Object:��¼'s ���ļ�property 
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
     * Object:��¼'s ������λproperty 
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
        return new BOSObjectType("C07D8FCE");
    }
}