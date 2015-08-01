package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectIndexDataInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectIndexDataInfo()
    {
        this("id");
    }
    protected AbstractProjectIndexDataInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection());
    }
    /**
     * Object:项目指标数据's 工程项目或者组织IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjOrOrgID()
    {
        return getBOSUuid("projOrOrgID");
    }
    public void setProjOrOrgID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projOrOrgID", item);
    }
    /**
     * Object:项目指标数据's 项目阶段property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStageEnum getProjectStage()
    {
        return com.kingdee.eas.fdc.basedata.ProjectStageEnum.getEnum(getString("projectStage"));
    }
    public void setProjectStage(com.kingdee.eas.fdc.basedata.ProjectStageEnum item)
    {
		if (item != null) {
        setString("projectStage", item.getValue());
		}
    }
    /**
     * Object: 项目指标数据 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:项目指标数据's 修订时间property 
     */
    public java.sql.Timestamp getVerTime()
    {
        return getTimestamp("verTime");
    }
    public void setVerTime(java.sql.Timestamp item)
    {
        setTimestamp("verTime", item);
    }
    /**
     * Object:项目指标数据's 版本号property 
     */
    public String getVerNo()
    {
        return getString("verNo");
    }
    public void setVerNo(String item)
    {
        setString("verNo", item);
    }
    /**
     * Object:项目指标数据's 版本名称property 
     */
    public String getVerName()
    {
        return getString("verName");
    }
    public void setVerName(String item)
    {
        setString("verName", item);
    }
    /**
     * Object:项目指标数据's 版本说明property 
     */
    public String getVerRemark()
    {
        return getString("verRemark");
    }
    public void setVerRemark(String item)
    {
        setString("verRemark", item);
    }
    /**
     * Object:项目指标数据's 是否最新版本property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: 项目指标数据 's 分录 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection)get("entries");
    }
    /**
     * Object:项目指标数据's 子版本最新数据property 
     */
    public boolean isIsLatestSubVer()
    {
        return getBoolean("isLatestSubVer");
    }
    public void setIsLatestSubVer(boolean item)
    {
        setBoolean("isLatestSubVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("734D0775");
    }
}