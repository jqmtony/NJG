package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupEvaluationIndicatorInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSupEvaluationIndicatorInfo()
    {
        this("id");
    }
    protected AbstractSupEvaluationIndicatorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ������ָ�� 's ��� property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:��Ӧ������ָ��'s ����property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    /**
     * Object: ��Ӧ������ָ�� 's ����ά�� property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo getPswd()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo)get("pswd");
    }
    public void setPswd(com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo item)
    {
        put("pswd", item);
    }
    /**
     * Object:��Ӧ������ָ��'s ���ֱ�׼property 
     */
    public String getStandard()
    {
        return getString("standard");
    }
    public void setStandard(String item)
    {
        setString("standard", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("840C8E86");
    }
}