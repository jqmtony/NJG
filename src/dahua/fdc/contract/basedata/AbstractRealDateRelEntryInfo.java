package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRealDateRelEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRealDateRelEntryInfo()
    {
        this("id");
    }
    protected AbstractRealDateRelEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's null property 
     */
    public com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��ͬǩ��ҵ��������������property 
     */
    public int getCostDays()
    {
        return getInt("costDays");
    }
    public void setCostDays(int item)
    {
        setInt("costDays", item);
    }
    /**
     * Object:��¼'s ����������������property 
     */
    public int getStartDays()
    {
        return getInt("startDays");
    }
    public void setStartDays(int item)
    {
        setInt("startDays", item);
    }
    /**
     * Object:��¼'s ��Լ�滮����property 
     */
    public String getPcname()
    {
        return getString("pcname");
    }
    public void setPcname(String item)
    {
        setString("pcname", item);
    }
    /**
     * Object:��¼'s longNumberproperty 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9446817");
    }
}