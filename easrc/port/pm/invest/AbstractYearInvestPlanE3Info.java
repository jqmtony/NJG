package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearInvestPlanE3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearInvestPlanE3Info()
    {
        this("id");
    }
    protected AbstractYearInvestPlanE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审意见 's null property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.YearInvestPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:评审意见's 评审时间property 
     */
    public java.util.Date getReviewTime()
    {
        return getDate("reviewTime");
    }
    public void setReviewTime(java.util.Date item)
    {
        setDate("reviewTime", item);
    }
    /**
     * Object:评审意见's 评审阶段property 
     */
    public com.kingdee.eas.port.pm.invest.AccredTypeEnum getReviewStage()
    {
        return com.kingdee.eas.port.pm.invest.AccredTypeEnum.getEnum(getString("reviewStage"));
    }
    public void setReviewStage(com.kingdee.eas.port.pm.invest.AccredTypeEnum item)
    {
		if (item != null) {
        setString("reviewStage", item.getValue());
		}
    }
    /**
     * Object:评审意见's 评审结论property 
     */
    public String getAccredConclusion()
    {
        return getString("accredConclusion");
    }
    public void setAccredConclusion(String item)
    {
        setString("accredConclusion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E406886");
    }
}