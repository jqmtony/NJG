package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeSplitEntryTempInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractConChangeSplitEntryTempInfo()
    {
        this("id");
    }
    protected AbstractConChangeSplitEntryTempInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:存储变更拆分分录信息的临时表's 金额property 
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
     * Object: 存储变更拆分分录信息的临时表 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:存储变更拆分分录信息的临时表's 变更拆分单分录IDproperty 
     */
    public String getEntryID()
    {
        return getString("entryID");
    }
    public void setEntryID(String item)
    {
        setString("entryID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5F9D143");
    }
}