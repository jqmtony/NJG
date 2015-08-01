package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrjPayEntryBaseInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPrjPayEntryBaseInfo()
    {
        this("id");
    }
    protected AbstractPrjPayEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工程情况表基类's 预付款截至上期累计实付property 
     */
    public java.math.BigDecimal getLstAdvanceAllPaid()
    {
        return getBigDecimal("lstAdvanceAllPaid");
    }
    public void setLstAdvanceAllPaid(java.math.BigDecimal item)
    {
        setBigDecimal("lstAdvanceAllPaid", item);
    }
    /**
     * Object:工程情况表基类's 预付款截至上期累计申请property 
     */
    public java.math.BigDecimal getLstAdvanceAllReq()
    {
        return getBigDecimal("lstAdvanceAllReq");
    }
    public void setLstAdvanceAllReq(java.math.BigDecimal item)
    {
        setBigDecimal("lstAdvanceAllReq", item);
    }
    /**
     * Object:工程情况表基类's 预付款原币property 
     */
    public java.math.BigDecimal getAdvance()
    {
        return getBigDecimal("advance");
    }
    public void setAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("advance", item);
    }
    /**
     * Object:工程情况表基类's 预付款本币property 
     */
    public java.math.BigDecimal getLocAdvance()
    {
        return getBigDecimal("locAdvance");
    }
    public void setLocAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("locAdvance", item);
    }
    /**
     * Object:工程情况表基类's 预付款截至本期累计申请property 
     */
    public java.math.BigDecimal getAdvanceAllReq()
    {
        return getBigDecimal("advanceAllReq");
    }
    public void setAdvanceAllReq(java.math.BigDecimal item)
    {
        setBigDecimal("advanceAllReq", item);
    }
    /**
     * Object:工程情况表基类's 预付款截至本期累计实付property 
     */
    public java.math.BigDecimal getAdvanceAllPaid()
    {
        return getBigDecimal("advanceAllPaid");
    }
    public void setAdvanceAllPaid(java.math.BigDecimal item)
    {
        setBigDecimal("advanceAllPaid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BEF951C8");
    }
}