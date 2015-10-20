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
     * Object:存储变更拆分信息的临时表's 变更拆分单据头金额property 
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
     * Object:存储变更拆分信息的临时表's 变更拆分单据头原币金额property 
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
     * Object:存储变更拆分信息的临时表's 变更签证确认IDproperty 
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
     * Object:存储变更拆分信息的临时表's 变更拆分单据IDproperty 
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
     * Object:存储变更拆分信息的临时表's 拆分日期property 
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
     * Object:存储变更拆分信息的临时表's 最终结算单IDproperty 
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
     * Object: 存储变更拆分信息的临时表 's 分录 property 
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