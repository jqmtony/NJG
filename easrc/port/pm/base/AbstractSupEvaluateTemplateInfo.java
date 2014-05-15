package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupEvaluateTemplateInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSupEvaluateTemplateInfo()
    {
        this("id");
    }
    protected AbstractSupEvaluateTemplateInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.base.SupEvaluateTemplateEntryCollection());
    }
    /**
     * Object: ��Ӧ������ģ�� 's ��� property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: ��Ӧ������ģ�� 's ��¼ property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluateTemplateEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluateTemplateEntryCollection)get("Entry");
    }
    /**
     * Object:��Ӧ������ģ��'s ��������property 
     */
    public String getAssessType()
    {
        return getString("assessType");
    }
    public void setAssessType(String item)
    {
        setString("assessType", item);
    }
    /**
     * Object:��Ӧ������ģ��'s ��עproperty 
     */
    public String getBIMUDF0008()
    {
        return getString("BIMUDF0008");
    }
    public void setBIMUDF0008(String item)
    {
        setString("BIMUDF0008", item);
    }
    /**
     * Object:��Ӧ������ģ��'s ����property 
     */
    public boolean isUse()
    {
        return getBoolean("use");
    }
    public void setUse(boolean item)
    {
        setBoolean("use", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC5578C0");
    }
}