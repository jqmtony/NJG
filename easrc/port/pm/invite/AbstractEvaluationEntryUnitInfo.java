package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationEntryUnitInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationEntryUnitInfo()
    {
        this("id");
    }
    protected AbstractEvaluationEntryUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 投标单位分录 's null property 
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
     * Object:投标单位分录's 投标单位property 
     */
    public String getEnterprise()
    {
        return getString("enterprise");
    }
    public void setEnterprise(String item)
    {
        setString("enterprise", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9A132151");
    }
}