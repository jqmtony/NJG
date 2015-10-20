package com.kingdee.eas.fdc.aimcost.costkf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCQGSInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractCQGSInfo()
    {
        this("id");
    }
    protected AbstractCQGSInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection());
    }
    /**
     * Object: 产权归属申报表 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryCollection)get("entrys");
    }
    /**
     * Object:产权归属申报表's 是否生成凭证property 
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
     * Object:产权归属申报表's 公司名称property 
     */
    public String getCompany()
    {
        return getString("company");
    }
    public void setCompany(String item)
    {
        setString("company", item);
    }
    /**
     * Object:产权归属申报表's 计划开工时间property 
     */
    public java.util.Date getStartdate()
    {
        return getDate("startdate");
    }
    public void setStartdate(java.util.Date item)
    {
        setDate("startdate", item);
    }
    /**
     * Object:产权归属申报表's 计划竣工时间property 
     */
    public java.util.Date getEnddate()
    {
        return getDate("enddate");
    }
    public void setEnddate(java.util.Date item)
    {
        setDate("enddate", item);
    }
    /**
     * Object:产权归属申报表's 红线用地面积property 
     */
    public java.math.BigDecimal getRedarea()
    {
        return getBigDecimal("redarea");
    }
    public void setRedarea(java.math.BigDecimal item)
    {
        setBigDecimal("redarea", item);
    }
    /**
     * Object:产权归属申报表's 容积率property 
     */
    public String getVolumeRatio()
    {
        return getString("VolumeRatio");
    }
    public void setVolumeRatio(String item)
    {
        setString("VolumeRatio", item);
    }
    /**
     * Object:产权归属申报表's 总建筑面积property 
     */
    public String getTotalArea()
    {
        return getString("TotalArea");
    }
    public void setTotalArea(String item)
    {
        setString("TotalArea", item);
    }
    /**
     * Object:产权归属申报表's 状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("State"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("State", item.getValue());
		}
    }
    /**
     * Object: 产权归属申报表 's 项目名称 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProjectName()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("ProjectName");
    }
    public void setProjectName(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("ProjectName", item);
    }
    /**
     * Object:产权归属申报表's 版本号property 
     */
    public int getVersion()
    {
        return getInt("Version");
    }
    public void setVersion(int item)
    {
        setInt("Version", item);
    }
    /**
     * Object:产权归属申报表's 是否最新property 
     */
    public boolean isLasted()
    {
        return getBoolean("lasted");
    }
    public void setLasted(boolean item)
    {
        setBoolean("lasted", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D1BD4D83");
    }
}