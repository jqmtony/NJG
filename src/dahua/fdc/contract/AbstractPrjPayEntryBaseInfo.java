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
     * Object:������������'s Ԥ������������ۼ�ʵ��property 
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
     * Object:������������'s Ԥ������������ۼ�����property 
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
     * Object:������������'s Ԥ����ԭ��property 
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
     * Object:������������'s Ԥ�����property 
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
     * Object:������������'s Ԥ������������ۼ�����property 
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
     * Object:������������'s Ԥ������������ۼ�ʵ��property 
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