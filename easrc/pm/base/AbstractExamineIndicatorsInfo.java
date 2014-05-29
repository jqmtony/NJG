package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExamineIndicatorsInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractExamineIndicatorsInfo()
    {
        this("id");
    }
    protected AbstractExamineIndicatorsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ָ�� 's ��� property 
     */
    public com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:����ָ��'s �������property 
     */
    public String getExamCategory()
    {
        return getString("examCategory");
    }
    public void setExamCategory(String item)
    {
        setString("examCategory", item);
    }
    /**
     * Object:����ָ��'s ����ָ��property 
     */
    public String getExamineIndicator()
    {
        return getString("examineIndicator");
    }
    public void setExamineIndicator(String item)
    {
        setString("examineIndicator", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85126C64");
    }
}