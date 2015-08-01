package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeSplitTempInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractConChangeSplitTempInfo()
    {
        this("id");
    }
    protected AbstractConChangeSplitTempInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempCollection());
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s �����ֵ���ͷ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s �����ֵ���ͷԭ�ҽ��property 
     */
    public java.math.BigDecimal getOriAmount()
    {
        return getBigDecimal("oriAmount");
    }
    public void setOriAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oriAmount", item);
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s ���ǩ֤ȷ��IDproperty 
     */
    public String getConChangeID()
    {
        return getString("conChangeID");
    }
    public void setConChangeID(String item)
    {
        setString("conChangeID", item);
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s �����ֵ���IDproperty 
     */
    public String getSplitID()
    {
        return getString("splitID");
    }
    public void setSplitID(String item)
    {
        setString("splitID", item);
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s �������property 
     */
    public java.util.Date getSplitDate()
    {
        return getDate("splitDate");
    }
    public void setSplitDate(java.util.Date item)
    {
        setDate("splitDate", item);
    }
    /**
     * Object:�洢��������Ϣ����ʱ��'s ���ս��㵥IDproperty 
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
     * Object: �洢��������Ϣ����ʱ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AA3E33D7");
    }
}