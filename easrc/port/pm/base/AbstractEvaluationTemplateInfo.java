package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationTemplateInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractEvaluationTemplateInfo()
    {
        this("id");
    }
    protected AbstractEvaluationTemplateInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection());
    }
    /**
     * Object: ����ģ�� 's ��� property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:����ģ��'s ����ģ������property 
     */
    public String getTemplateName()
    {
        return getString("templateName");
    }
    public void setTemplateName(String item)
    {
        setString("templateName", item);
    }
    /**
     * Object: ����ģ�� 's ģ������ property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo getTemplateType()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo)get("templateType");
    }
    public void setTemplateType(com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo item)
    {
        put("templateType", item);
    }
    /**
     * Object:����ģ��'s �Ƿ�Ȩ�ؼ��������ֵproperty 
     */
    public boolean isIsWeight()
    {
        return getBoolean("isWeight");
    }
    public void setIsWeight(boolean item)
    {
        setBoolean("isWeight", item);
    }
    /**
     * Object:����ģ��'s ��עproperty 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object: ����ģ�� 's ָ���¼ property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection)get("Entry");
    }
    /**
     * Object:����ģ��'s ����property 
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
        return new BOSObjectType("6352DDD7");
    }
}