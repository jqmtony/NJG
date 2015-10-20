package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBailEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractBailEntryInfo()
    {
        this("id");
    }
    protected AbstractContractBailEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Լ��֤�𼰷������ַ�¼'s ����property 
     */
    public java.util.Date getBailDate()
    {
        return getDate("bailDate");
    }
    public void setBailDate(java.util.Date item)
    {
        setDate("bailDate", item);
    }
    /**
     * Object:��Լ��֤�𼰷������ַ�¼'s ��������property 
     */
    public String getBailConditon()
    {
        return getString("bailConditon");
    }
    public void setBailConditon(String item)
    {
        setString("bailConditon", item);
    }
    /**
     * Object:��Լ��֤�𼰷������ַ�¼'s ��������property 
     */
    public java.math.BigDecimal getProp()
    {
        return getBigDecimal("prop");
    }
    public void setProp(java.math.BigDecimal item)
    {
        setBigDecimal("prop", item);
    }
    /**
     * Object:��Լ��֤�𼰷������ַ�¼'s �������ԭ��property 
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
     * Object:��Լ��֤�𼰷������ַ�¼'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: ��Լ��֤�𼰷������ַ�¼ 's ��Լ��֤�𼰷�������ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBailInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBailInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBailInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BA2F4623");
    }
}