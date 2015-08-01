package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractApportionInfoBaseInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractApportionInfoBaseInfo()
    {
        this("id");
    }
    protected AbstractApportionInfoBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��̯��Ϣ���� 's ָ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:��̯��Ϣ����'s ֵproperty 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83E3A4E5");
    }
}