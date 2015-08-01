package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProjectInfo()
    {
        this("id");
    }
    protected AbstractProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ŀ's �������property 
     */
    public int getSortNo()
    {
        return getInt("sortNo");
    }
    public void setSortNo(int item)
    {
        setInt("sortNo", item);
    }
    /**
     * Object:������Ŀ's ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object: ������Ŀ 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    /**
     * Object: ������Ŀ 's �׷� property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC2BDFE7");
    }
}