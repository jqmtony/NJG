package com.kingdee.eas.port.pm.project;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectFeeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProjectFeeInfo()
    {
        this("id");
    }
    protected AbstractProjectFeeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀ����Ԥ�� 's ���ڵ� property 
     */
    public com.kingdee.eas.port.pm.project.ProjectFeeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.project.ProjectFeeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.project.ProjectFeeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ŀ����Ԥ��'s ������property 
     */
    public java.math.BigDecimal getEstimatesAmount()
    {
        return getBigDecimal("estimatesAmount");
    }
    public void setEstimatesAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimatesAmount", item);
    }
    /**
     * Object:��Ŀ����Ԥ��'s Ԥ����property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("50324398");
    }
}