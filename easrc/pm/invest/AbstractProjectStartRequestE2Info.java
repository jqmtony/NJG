package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStartRequestE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectStartRequestE2Info()
    {
        this("id");
    }
    protected AbstractProjectStartRequestE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 后评估指标 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:后评估指标's 后评标指标property 
     */
    public String getEvaluationIndex()
    {
        return getString("evaluationIndex");
    }
    public void setEvaluationIndex(String item)
    {
        setString("evaluationIndex", item);
    }
    /**
     * Object:后评估指标's 目标值property 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    /**
     * Object:后评估指标's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DB0D84D6");
    }
}