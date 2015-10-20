package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBailInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractBailInfo()
    {
        this("id");
    }
    protected AbstractContractBailInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractBailEntryCollection());
    }
    /**
     * Object:��Լ��֤�𼰷�������'s ��Լ��֤���ԭ�ң�property 
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
     * Object:��Լ��֤�𼰷�������'s ��Լ��֤�����property 
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
     * Object: ��Լ��֤�𼰷������� 's ��Լ��֤�𼰷������ַ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBailEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractBailEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0D6DB38F");
    }
}