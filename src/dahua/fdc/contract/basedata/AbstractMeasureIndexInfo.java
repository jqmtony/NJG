package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureIndexInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMeasureIndexInfo()
    {
        this("id");
    }
    protected AbstractMeasureIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 成本测算分摊指标设置 's 指标名称 property 
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
     * Object:成本测算分摊指标设置's 固定指标property 
     */
    public com.kingdee.eas.fdc.basedata.StandardIndexEnum getStandardIndex()
    {
        return com.kingdee.eas.fdc.basedata.StandardIndexEnum.getEnum(getString("standardIndex"));
    }
    public void setStandardIndex(com.kingdee.eas.fdc.basedata.StandardIndexEnum item)
    {
		if (item != null) {
        setString("standardIndex", item.getValue());
		}
    }
    /**
     * Object:成本测算分摊指标设置's 指标类型property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeEnum getType()
    {
        return com.kingdee.eas.fdc.basedata.ApportionTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.basedata.ApportionTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF787A06");
    }
}