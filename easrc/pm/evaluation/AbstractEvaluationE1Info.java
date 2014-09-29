package com.kingdee.eas.port.pm.evaluation;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationE1Info()
    {
        this("id");
    }
    protected AbstractEvaluationE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目成功度评价表 's null property 
     */
    public com.kingdee.eas.port.pm.evaluation.EvaluationInfo getParent()
    {
        return (com.kingdee.eas.port.pm.evaluation.EvaluationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.evaluation.EvaluationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目成功度评价表's 评定项目指标property 
     */
    public String getApIndex()
    {
        return getString("apIndex");
    }
    public void setApIndex(String item)
    {
        setString("apIndex", item);
    }
    /**
     * Object:项目成功度评价表's 评定等级property 
     */
    public com.kingdee.eas.port.pm.evaluation.Rate getRate()
    {
        return com.kingdee.eas.port.pm.evaluation.Rate.getEnum(getString("rate"));
    }
    public void setRate(com.kingdee.eas.port.pm.evaluation.Rate item)
    {
		if (item != null) {
        setString("rate", item.getValue());
		}
    }
    /**
     * Object:项目成功度评价表's 项目相关重要性property 
     */
    public com.kingdee.eas.port.pm.evaluation.ProjectImportance getProjectImportance()
    {
        return com.kingdee.eas.port.pm.evaluation.ProjectImportance.getEnum(getString("projectImportance"));
    }
    public void setProjectImportance(com.kingdee.eas.port.pm.evaluation.ProjectImportance item)
    {
		if (item != null) {
        setString("projectImportance", item.getValue());
		}
    }
    /**
     * Object:项目成功度评价表's 计划完成情况property 
     */
    public String getPlanDesc()
    {
        return getString("planDesc");
    }
    public void setPlanDesc(String item)
    {
        setString("planDesc", item);
    }
    /**
     * Object:项目成功度评价表's 实际完成情况property 
     */
    public String getFactDesc()
    {
        return getString("factDesc");
    }
    public void setFactDesc(String item)
    {
        setString("factDesc", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FAC1353E");
    }
}