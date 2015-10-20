package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeTempInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractConChangeTempInfo()
    {
        this("id");
    }
    protected AbstractConChangeTempInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s ����ԭ�ҽ��property 
     */
    public java.math.BigDecimal getOriBalanceAmount()
    {
        return getBigDecimal("oriBalanceAmount");
    }
    public void setOriBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriBalanceAmount", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s ������property 
     */
    public java.math.BigDecimal getBalanceAmount()
    {
        return getBigDecimal("balanceAmount");
    }
    public void setBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("balanceAmount", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s �Ƿ��ѽ���property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s ����ʱ��property 
     */
    public java.util.Date getSettleTime()
    {
        return getDate("settleTime");
    }
    public void setSettleTime(java.util.Date item)
    {
        setDate("settleTime", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s ���ս��㵥IDproperty 
     */
    public String getSettleID()
    {
        return getString("settleID");
    }
    public void setSettleID(String item)
    {
        setString("settleID", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s �Ƿ��Ǳ��ǩ֤ȷ��property 
     */
    public boolean isIsConChange()
    {
        return getBoolean("isConChange");
    }
    public void setIsConChange(boolean item)
    {
        setBoolean("isConChange", item);
    }
    /**
     * Object:�洢���ǩ֤ȷ����Ϣ����ʱ��'s ���ǩ֤ȷ��ID�����Ǳ��ǩ֤����IDproperty 
     */
    public String getSourceID()
    {
        return getString("sourceID");
    }
    public void setSourceID(String item)
    {
        setString("sourceID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("452A8C6B");
    }
}