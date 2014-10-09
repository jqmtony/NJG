package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractsettlementOtherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractsettlementOtherEntryInfo()
    {
        this("id");
    }
    protected AbstractContractsettlementOtherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 其它资料分录 's null property 
     */
    public com.kingdee.eas.bpmdemo.ContractsettlementInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ContractsettlementInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ContractsettlementInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:其它资料分录's 选择property 
     */
    public String getSelect()
    {
        return getString("select");
    }
    public void setSelect(String item)
    {
        setString("select", item);
    }
    /**
     * Object:其它资料分录's 扣款单编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:其它资料分录's 类型property 
     */
    public String getType()
    {
        return getString("type");
    }
    public void setType(String item)
    {
        setString("type", item);
    }
    /**
     * Object:其它资料分录's 扣款事项property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:其它资料分录's 扣款金额property 
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
     * Object:其它资料分录's 扣款日期property 
     */
    public java.util.Date getDeductTime()
    {
        return getDate("deductTime");
    }
    public void setDeductTime(java.util.Date item)
    {
        setDate("deductTime", item);
    }
    /**
     * Object:其它资料分录's 制单人property 
     */
    public String getCreator()
    {
        return getString("creator");
    }
    public void setCreator(String item)
    {
        setString("creator", item);
    }
    /**
     * Object:其它资料分录's 制单日期property 
     */
    public java.util.Date getCreateDate()
    {
        return getDate("createDate");
    }
    public void setCreateDate(java.util.Date item)
    {
        setDate("createDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CF22B49B");
    }
}