package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTempInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractProgrammingTempInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTempInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:规划余额扣减中间表(针对合同拆分、变更拆分、结算拆分)'s 合约规划IDproperty 
     */
    public String getProgrammingId()
    {
        return getString("programmingId");
    }
    public void setProgrammingId(String item)
    {
        setString("programmingId", item);
    }
    /**
     * Object:规划余额扣减中间表(针对合同拆分、变更拆分、结算拆分)'s 扣减金额property 
     */
    public java.math.BigDecimal getSubAmount()
    {
        return getBigDecimal("subAmount");
    }
    public void setSubAmount(java.math.BigDecimal item)
    {
        setBigDecimal("subAmount", item);
    }
    /**
     * Object:规划余额扣减中间表(针对合同拆分、变更拆分、结算拆分)'s 单据来源property 
     */
    public String getBill()
    {
        return getString("bill");
    }
    public void setBill(String item)
    {
        setString("bill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EE2D5BDB");
    }
}