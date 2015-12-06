package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynamicCostInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractProjectDynamicCostInfo()
    {
        this("id");
    }
    protected AbstractProjectDynamicCostInfo(String pkField)
    {
        super(pkField);
        put("EntryPosition", new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionCollection());
        put("entrys", new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryCollection());
        put("EntrysAccount", new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection());
    }
    /**
     * Object: 工程动态成本跟踪表 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryCollection)get("entrys");
    }
    /**
     * Object:工程动态成本跟踪表's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 工程动态成本跟踪表 's 项目名称 property 
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
     * Object:工程动态成本跟踪表's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:工程动态成本跟踪表's 年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:工程动态成本跟踪表's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:工程动态成本跟踪表's 状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:工程动态成本跟踪表's 审核时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object: 工程动态成本跟踪表 's 科目维度分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection getEntrysAccount()
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection)get("EntrysAccount");
    }
    /**
     * Object: 工程动态成本跟踪表 's 一级科目预警职位 property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getFirstLevelPos()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("firstLevelPos");
    }
    public void setFirstLevelPos(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("firstLevelPos", item);
    }
    /**
     * Object: 工程动态成本跟踪表 's 二级科目预警职位 property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getSecondLevelPos()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("secondLevelPos");
    }
    public void setSecondLevelPos(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("secondLevelPos", item);
    }
    /**
     * Object: 工程动态成本跟踪表 's 三级科目预警职位 property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getThirdLevelPos()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("thirdLevelPos");
    }
    public void setThirdLevelPos(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("thirdLevelPos", item);
    }
    /**
     * Object: 工程动态成本跟踪表 's 职位表 property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionCollection getEntryPosition()
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionCollection)get("EntryPosition");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75CD9A79");
    }
}