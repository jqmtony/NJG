package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearInvestPlanE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearInvestPlanE2Info()
    {
        this("id");
    }
    protected AbstractYearInvestPlanE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 后评估指标 's null property 
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
     * Object:后评估指标's 评定项目指标property 
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
     * Object:后评估指标's 计划完成情况property 
     */
    public String getPlanComplete()
    {
        return getString("planComplete");
    }
    public void setPlanComplete(String item)
    {
        setString("planComplete", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E406885");
    }
}