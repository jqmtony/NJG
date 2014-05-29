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
     * Object: 考核指标 's 组别 property 
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
     * Object:考核指标's 考核类别property 
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
     * Object:考核指标's 考核指标property 
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