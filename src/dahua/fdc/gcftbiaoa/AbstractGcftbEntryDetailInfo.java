package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGcftbEntryDetailInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractGcftbEntryDetailInfo()
    {
        this("id");
    }
    protected AbstractGcftbEntryDetailInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分摊项目 's null property 
     */
    public com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo getParent1()
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: 分摊项目 's 收益项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getBenefitProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("benefitProject");
    }
    public void setBenefitProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("benefitProject", item);
    }
    /**
     * Object:分摊项目's 分摊基数property 
     */
    public java.math.BigDecimal getAllocationBase()
    {
        return getBigDecimal("allocationBase");
    }
    public void setAllocationBase(java.math.BigDecimal item)
    {
        setBigDecimal("allocationBase", item);
    }
    /**
     * Object:分摊项目's 分摊金额property 
     */
    public java.math.BigDecimal getShareAmount()
    {
        return getBigDecimal("shareAmount");
    }
    public void setShareAmount(java.math.BigDecimal item)
    {
        setBigDecimal("shareAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D0FBA5D2");
    }
}