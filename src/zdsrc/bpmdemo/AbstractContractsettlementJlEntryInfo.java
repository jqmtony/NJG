package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractsettlementJlEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractsettlementJlEntryInfo()
    {
        this("id");
    }
    protected AbstractContractsettlementJlEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 结算奖励分录 's null property 
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
     * Object:结算奖励分录's 选择property 
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
     * Object:结算奖励分录's 奖励单编码property 
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
     * Object:结算奖励分录's 奖励单名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:结算奖励分录's 奖励金额property 
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
     * Object:结算奖励分录's 事项property 
     */
    public String getResaon()
    {
        return getString("resaon");
    }
    public void setResaon(String item)
    {
        setString("resaon", item);
    }
    /**
     * Object:结算奖励分录's 发放方式property 
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
     * Object:结算奖励分录's 制单人property 
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
     * Object:结算奖励分录's 制单日期property 
     */
    public java.util.Date getCreateTime()
    {
        return getDate("createTime");
    }
    public void setCreateTime(java.util.Date item)
    {
        setDate("createTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("254FF997");
    }
}