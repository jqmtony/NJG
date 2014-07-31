package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateEntirePteEnonomyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateEntirePteEnonomyInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateEntirePteEnonomyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:经济条款's 付款比例property 
     */
    public java.math.BigDecimal getScale()
    {
        return getBigDecimal("scale");
    }
    public void setScale(java.math.BigDecimal item)
    {
        setBigDecimal("scale", item);
    }
    /**
     * Object:经济条款's 付款条件property 
     */
    public String getCondition()
    {
        return getString("condition");
    }
    public void setCondition(String item)
    {
        setString("condition", item);
    }
    /**
     * Object:经济条款's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 经济条款 's 合约框架模板分录 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 经济条款 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ABFF92C9");
    }
}