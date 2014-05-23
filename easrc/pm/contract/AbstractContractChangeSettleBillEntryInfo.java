package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeSettleBillEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractChangeSettleBillEntryInfo()
    {
        this("id");
    }
    protected AbstractContractChangeSettleBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��¼'s �嵥����property 
     */
    public String getNumer()
    {
        return getString("numer");
    }
    public void setNumer(String item)
    {
        setString("numer", item);
    }
    /**
     * Object:��¼'s �䶯��Ŀ����property 
     */
    public String getChangeContent()
    {
        return getString("changeContent");
    }
    public void setChangeContent(String item)
    {
        setString("changeContent", item);
    }
    /**
     * Object:��¼'s ����property 
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
     * Object:��¼'s ������property 
     */
    public java.math.BigDecimal getProNumber()
    {
        return getBigDecimal("proNumber");
    }
    public void setProNumber(java.math.BigDecimal item)
    {
        setBigDecimal("proNumber", item);
    }
    /**
     * Object:��¼'s ��λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object: ��¼ 's �� property 
     */
    public com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo getParent()
    {
        return (com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s �ϼƽ��property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:��¼'s ��ע��Ϣproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1AB220BE");
    }
}