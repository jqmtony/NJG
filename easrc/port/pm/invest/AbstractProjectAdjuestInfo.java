package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectAdjuestInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectAdjuestInfo()
    {
        this("id");
    }
    protected AbstractProjectAdjuestInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目投资调整单's 调整单名称property 
     */
    public String getAdjuestName()
    {
        return getString("adjuestName");
    }
    public void setAdjuestName(String item)
    {
        setString("adjuestName", item);
    }
    /**
     * Object:项目投资调整单's 调整类型property 
     */
    public com.kingdee.eas.port.pm.base.coms.AdjustType getAdjustType()
    {
        return com.kingdee.eas.port.pm.base.coms.AdjustType.getEnum(getString("adjustType"));
    }
    public void setAdjustType(com.kingdee.eas.port.pm.base.coms.AdjustType item)
    {
		if (item != null) {
        setString("adjustType", item.getValue());
		}
    }
    /**
     * Object:项目投资调整单's 调整前金额property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:项目投资调整单's 调整后金额property 
     */
    public java.math.BigDecimal getAdjustAmount()
    {
        return getBigDecimal("adjustAmount");
    }
    public void setAdjustAmount(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAmount", item);
    }
    /**
     * Object:项目投资调整单's 调整原因property 
     */
    public String getAdjustReson()
    {
        return getString("adjustReson");
    }
    public void setAdjustReson(String item)
    {
        setString("adjustReson", item);
    }
    /**
     * Object:项目投资调整单's 调整内容property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F94B9DC");
    }
}