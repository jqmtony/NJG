package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationEntryValidInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationEntryValidInfo()
    {
        this("id");
    }
    protected AbstractEvaluationEntryValidInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����������¼ 's null property 
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
     * Object:����������¼'s ������ָ��property 
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
     * Object:����������¼'s ������property 
     */
    public String getValid()
    {
        return getString("valid");
    }
    public void setValid(String item)
    {
        setString("valid", item);
    }
    /**
     * Object:����������¼'s ��ίproperty 
     */
    public String getJudges()
    {
        return getString("judges");
    }
    public void setJudges(String item)
    {
        setString("judges", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A85941CF");
    }
}