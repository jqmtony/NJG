package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIncomeAccountInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractIncomeAccountInfo()
    {
        this("id");
    }
    protected AbstractIncomeAccountInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����Ŀ 's ���ڵĹ�����Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �����Ŀ 's ����� property 
     */
    public com.kingdee.eas.fdc.basedata.IncomeAccountInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.IncomeAccountInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.IncomeAccountInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����Ŀ 's ���ڵ���֯ property 
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
     * Object:�����Ŀ's �ѷ���property 
     */
    public boolean isAssigned()
    {
        return getBoolean("assigned");
    }
    public void setAssigned(boolean item)
    {
        setBoolean("assigned", item);
    }
    /**
     * Object:�����Ŀ's ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:�����Ŀ's �Ƿ���Դproperty 
     */
    public boolean isIsSource()
    {
        return getBoolean("isSource");
    }
    public void setIsSource(boolean item)
    {
        setBoolean("isSource", item);
    }
    /**
     * Object:�����Ŀ's �����ĿԴidproperty 
     */
    public String getSrcIncomeAccountId()
    {
        return getString("srcIncomeAccountId");
    }
    public void setSrcIncomeAccountId(String item)
    {
        setString("srcIncomeAccountId", item);
    }
    /**
     * Object:�����Ŀ's ��ʶproperty 
     */
    public int getFlag()
    {
        return getInt("flag");
    }
    public void setFlag(int item)
    {
        setInt("flag", item);
    }
    /**
     * Object:�����Ŀ's �����Ŀ����property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6424292");
    }
}