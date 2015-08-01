package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanDataBaseInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPayPlanDataBaseInfo()
    {
        this("id");
    }
    protected AbstractPayPlanDataBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�������ݻ���'s ֧��ʱ��property 
     */
    public int getPayMonth()
    {
        return getInt("payMonth");
    }
    public void setPayMonth(int item)
    {
        setInt("payMonth", item);
    }
    /**
     * Object:�������ݻ���'s ֧�����property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F6D5FBD");
    }
}