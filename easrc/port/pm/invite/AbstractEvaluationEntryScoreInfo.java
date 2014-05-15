package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationEntryScoreInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationEntryScoreInfo()
    {
        this("id");
    }
    protected AbstractEvaluationEntryScoreInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评分分录 's null property 
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
     * Object:评分分录's 评委property 
     */
    public String getJudges()
    {
        return getString("judges");
    }
    public void setJudges(String item)
    {
        setString("judges", item);
    }
    /**
     * Object:评分分录's 指标property 
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
     * Object:评分分录's 满分property 
     */
    public String getFullScore()
    {
        return getString("fullScore");
    }
    public void setFullScore(String item)
    {
        setString("fullScore", item);
    }
    /**
     * Object:评分分录's 分数property 
     */
    public String getScore()
    {
        return getString("score");
    }
    public void setScore(String item)
    {
        setString("score", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A82FF065");
    }
}