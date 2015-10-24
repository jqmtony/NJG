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
     * Object:�滮���ۼ��м��(��Ժ�ͬ��֡������֡�������)'s ��Լ�滮IDproperty 
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
     * Object:�滮���ۼ��м��(��Ժ�ͬ��֡������֡�������)'s �ۼ����property 
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
     * Object:�滮���ۼ��м��(��Ժ�ͬ��֡������֡�������)'s ������Դproperty 
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