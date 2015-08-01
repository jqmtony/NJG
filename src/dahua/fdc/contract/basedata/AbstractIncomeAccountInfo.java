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
     * Object: 收入科目 's 所在的工程项目 property 
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
     * Object: 收入科目 's 父结点 property 
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
     * Object: 收入科目 's 所在的组织 property 
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
     * Object:收入科目's 已分配property 
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
     * Object:收入科目's 启用property 
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
     * Object:收入科目's 是分配源property 
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
     * Object:收入科目's 收入科目源idproperty 
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
     * Object:收入科目's 标识property 
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
     * Object:收入科目's 收入科目编码property 
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