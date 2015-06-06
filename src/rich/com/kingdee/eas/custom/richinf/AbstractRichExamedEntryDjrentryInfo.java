package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamedEntryDjrentryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichExamedEntryDjrentryInfo()
    {
        this("id");
    }
    protected AbstractRichExamedEntryDjrentryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������ϸ 's null property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedEntryInfo getParent1()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.custom.richinf.RichExamedEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:��������ϸ's ������Ŀ����property 
     */
    public String getDjxmbm()
    {
        return getString("djxmbm");
    }
    public void setDjxmbm(String item)
    {
        setString("djxmbm", item);
    }
    /**
     * Object:��������ϸ's ������Ŀ����property 
     */
    public String getDjxmmc()
    {
        return getString("djxmmc");
    }
    public void setDjxmmc(String item)
    {
        setString("djxmmc", item);
    }
    /**
     * Object:��������ϸ's �����ʾproperty 
     */
    public boolean isJxbs()
    {
        return getBoolean("jxbs");
    }
    public void setJxbs(boolean item)
    {
        setBoolean("jxbs", item);
    }
    /**
     * Object:��������ϸ's ������property 
     */
    public java.math.BigDecimal getKlj()
    {
        return getBigDecimal("klj");
    }
    public void setKlj(java.math.BigDecimal item)
    {
        setBigDecimal("klj", item);
    }
    /**
     * Object:��������ϸ's �ۿ���property 
     */
    public java.math.BigDecimal getZkl()
    {
        return getBigDecimal("zkl");
    }
    public void setZkl(java.math.BigDecimal item)
    {
        setBigDecimal("zkl", item);
    }
    /**
     * Object:��������ϸ's ������property 
     */
    public java.math.BigDecimal getJsje()
    {
        return getBigDecimal("jsje");
    }
    public void setJsje(java.math.BigDecimal item)
    {
        setBigDecimal("jsje", item);
    }
    /**
     * Object:��������ϸ's ˰��property 
     */
    public java.math.BigDecimal getSe()
    {
        return getBigDecimal("se");
    }
    public void setSe(java.math.BigDecimal item)
    {
        setBigDecimal("se", item);
    }
    /**
     * Object:��������ϸ's ��˰�ϼ�property 
     */
    public java.math.BigDecimal getJshj()
    {
        return getBigDecimal("jshj");
    }
    public void setJshj(java.math.BigDecimal item)
    {
        setBigDecimal("jshj", item);
    }
    /**
     * Object:��������ϸ's ��עproperty 
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
     * Object:��������ϸ's ҵ�񵥾ݱ��property 
     */
    public String getYwdjbh()
    {
        return getString("ywdjbh");
    }
    public void setYwdjbh(String item)
    {
        setString("ywdjbh", item);
    }
    /**
     * Object:��������ϸ's �м��IDproperty 
     */
    public String getTempID()
    {
        return getString("tempID");
    }
    public void setTempID(String item)
    {
        setString("tempID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F959E230");
    }
}