package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProgrammingInfo()
    {
        this("id");
    }
    protected AbstractProgrammingInfo(String pkField)
    {
        super(pkField);
        put("compareEntry", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingCompareEntryCollection());
        put("entries", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection());
    }
    /**
     * Object:投资规划's 版本property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object:投资规划's 是否最新版本property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: 投资规划 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 投资规划 's 分录 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection getEntries()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection)get("entries");
    }
    /**
     * Object:投资规划's 版本组property 
     */
    public String getVersionGroup()
    {
        return getString("versionGroup");
    }
    public void setVersionGroup(String item)
    {
        setString("versionGroup", item);
    }
    /**
     * Object:投资规划's 总建筑面积property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:投资规划's 可售面积property 
     */
    public java.math.BigDecimal getSoldArea()
    {
        return getBigDecimal("soldArea");
    }
    public void setSoldArea(java.math.BigDecimal item)
    {
        setBigDecimal("soldArea", item);
    }
    /**
     * Object: 投资规划 's 调整原因 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingCompareEntryCollection getCompareEntry()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingCompareEntryCollection)get("compareEntry");
    }
    /**
     * Object:投资规划's 项目名称property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:投资规划's 项目编码property 
     */
    public String getProjectNumber()
    {
        return getString("projectNumber");
    }
    public void setProjectNumber(String item)
    {
        setString("projectNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D602CAC");
    }
}