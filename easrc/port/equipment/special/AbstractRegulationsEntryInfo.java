package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRegulationsEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRegulationsEntryInfo()
    {
        this("id");
    }
    protected AbstractRegulationsEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ϸ 's null property 
     */
    public com.kingdee.eas.port.equipment.special.RegulationsInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.RegulationsInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.RegulationsInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:������ϸ's ��������property 
     */
    public String getDocName()
    {
        return getString("docName");
    }
    public void setDocName(String item)
    {
        setString("docName", item);
    }
    /**
     * Object:������ϸ's �ϴ�����property 
     */
    public java.util.Date getUpLoadDate()
    {
        return getDate("upLoadDate");
    }
    public void setUpLoadDate(java.util.Date item)
    {
        setDate("upLoadDate", item);
    }
    /**
     * Object:������ϸ's ��עproperty 
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
        return new BOSObjectType("F3E3E053");
    }
}