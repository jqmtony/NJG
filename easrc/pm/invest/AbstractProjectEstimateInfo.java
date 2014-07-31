package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectEstimateInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectEstimateInfo()
    {
        this("id");
    }
    protected AbstractProjectEstimateInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.ProjectEstimateE1Collection());
    }
    /**
     * Object: ��Ŀ���� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectEstimateE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectEstimateE1Collection)get("E1");
    }
    /**
     * Object: ��Ŀ���� 's ģ������ property 
     */
    public com.kingdee.eas.port.pm.invest.CostTempInfo getTempName()
    {
        return (com.kingdee.eas.port.pm.invest.CostTempInfo)get("tempName");
    }
    public void setTempName(com.kingdee.eas.port.pm.invest.CostTempInfo item)
    {
        put("tempName", item);
    }
    /**
     * Object: ��Ŀ���� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:��Ŀ����'s ������property 
     */
    public java.math.BigDecimal getEstimateAmount()
    {
        return getBigDecimal("estimateAmount");
    }
    public void setEstimateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimateAmount", item);
    }
    /**
     * Object: ��Ŀ���� 's ��Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: ��Ŀ���� 's ���� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeparment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deparment");
    }
    public void setDeparment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deparment", item);
    }
    /**
     * Object: ��Ŀ���� 's Ͷ����� property 
     */
    public com.kingdee.eas.port.pm.base.InvestYearInfo getYear()
    {
        return (com.kingdee.eas.port.pm.base.InvestYearInfo)get("year");
    }
    public void setYear(com.kingdee.eas.port.pm.base.InvestYearInfo item)
    {
        put("year", item);
    }
    /**
     * Object: ��Ŀ���� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.port.pm.base.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.port.pm.base.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.port.pm.base.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7C6F69E4");
    }
}