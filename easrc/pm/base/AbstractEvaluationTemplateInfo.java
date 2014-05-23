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
     * Object: 评标模板 's 组别 property 
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
     * Object:评标模板's 评标模板名称property 
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
     * Object: 评标模板 's 模板类型 property 
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
     * Object:评标模板's 是否按权重计算评标分值property 
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
     * Object:评标模板's 备注property 
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
     * Object: 评标模板 's 指标分录 property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection)get("Entry");
    }
    /**
     * Object:评标模板's 启用property 
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