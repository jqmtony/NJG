package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationEntryTotalInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationEntryTotalInfo()
    {
        this("id");
    }
    protected AbstractEvaluationEntryTotalInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 总分保存分录 's null property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.EvaluationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:总分保存分录's 总分指标property 
     */
    public String getIndicators()
    {
        return getString("indicators");
    }
    public void setIndicators(String item)
    {
        setString("indicators", item);
    }
    /**
     * Object:总分保存分录's 结果property 
     */
    public String getResult()
    {
        return getString("result");
    }
    public void setResult(String item)
    {
        setString("result", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A8438D17");
    }
}