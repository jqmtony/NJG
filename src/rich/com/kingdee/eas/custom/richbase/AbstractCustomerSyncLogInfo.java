package com.kingdee.eas.custom.richbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerSyncLogInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCustomerSyncLogInfo()
    {
        this("id");
    }
    protected AbstractCustomerSyncLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ͻ�ͬ����־'s ͬ������property 
     */
    public java.util.Date getSyncDate()
    {
        return getDate("syncDate");
    }
    public void setSyncDate(java.util.Date item)
    {
        setDate("syncDate", item);
    }
    /**
     * Object:�ͻ�ͬ����־'s �Ƿ�ɹ�property 
     */
    public boolean isIsSuccess()
    {
        return getBoolean("isSuccess");
    }
    public void setIsSuccess(boolean item)
    {
        setBoolean("isSuccess", item);
    }
    /**
     * Object:�ͻ�ͬ����־'s ������Ϣproperty 
     */
    public String getTipsInfo()
    {
        return getString("tipsInfo");
    }
    public void setTipsInfo(String item)
    {
        setString("tipsInfo", item);
    }
    /**
     * Object:�ͻ�ͬ����־'s �ͻ�����property 
     */
    public String getCustomerNum()
    {
        return getString("customerNum");
    }
    public void setCustomerNum(String item)
    {
        setString("customerNum", item);
    }
    /**
     * Object:�ͻ�ͬ����־'s ����property 
     */
    public String getCustomerName()
    {
        return getString("customerName");
    }
    public void setCustomerName(String item)
    {
        setString("customerName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("90108935");
    }
}