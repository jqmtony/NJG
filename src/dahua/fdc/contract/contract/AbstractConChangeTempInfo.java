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
     * Object:存储变更签证确认信息的临时表's 结算原币金额property 
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
     * Object:存储变更签证确认信息的临时表's 结算金额property 
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
     * Object:存储变更签证确认信息的临时表's 是否已结算property 
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
     * Object:存储变更签证确认信息的临时表's 结算时间property 
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
     * Object:存储变更签证确认信息的临时表's 最终结算单IDproperty 
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
     * Object:存储变更签证确认信息的临时表's 是否是变更签证确认property 
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
     * Object:存储变更签证确认信息的临时表's 变更签证确认ID或者是变更签证申请IDproperty 
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