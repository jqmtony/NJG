package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConfirquantitesInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractConfirquantitesInfo()
    {
        this("id");
    }
    protected AbstractConfirquantitesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工程量确认单's 是否生成凭证property 
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
     * Object:工程量确认单's 版本号property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object:工程量确认单's 事由property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object:工程量确认单's 审核日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:工程量确认单's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    /**
     * Object:工程量确认单's 是否最新版property 
     */
    public boolean isIsLast()
    {
        return getBoolean("isLast");
    }
    public void setIsLast(boolean item)
    {
        setBoolean("isLast", item);
    }
    /**
     * Object: 工程量确认单 's 合同名称 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractNumber()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractNumber");
    }
    public void setContractNumber(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractNumber", item);
    }
    /**
     * Object: 工程量确认单 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProjrct()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("projrct");
    }
    public void setProjrct(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("projrct", item);
    }
    /**
     * Object: 工程量确认单 's 承包单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getContractorUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("contractorUnit");
    }
    public void setContractorUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("contractorUnit", item);
    }
    /**
     * Object:工程量确认单's 项目完成时间property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object:工程量确认单's 工程部审核property 
     */
    public String getEngineeringAudit()
    {
        return getString("engineeringAudit");
    }
    public void setEngineeringAudit(String item)
    {
        setString("engineeringAudit", item);
    }
    /**
     * Object:工程量确认单's 成本管理部审核property 
     */
    public String getCostAudit()
    {
        return getString("costAudit");
    }
    public void setCostAudit(String item)
    {
        setString("costAudit", item);
    }
    /**
     * Object:工程量确认单's 项目公司第一负责人审核property 
     */
    public String getProjectFirstAudit()
    {
        return getString("projectFirstAudit");
    }
    public void setProjectFirstAudit(String item)
    {
        setString("projectFirstAudit", item);
    }
    /**
     * Object:工程量确认单's 申报工作量property 
     */
    public String getWorking()
    {
        return getString("working");
    }
    public void setWorking(String item)
    {
        setString("working", item);
    }
    /**
     * Object:工程量确认单's 承包方负责人property 
     */
    public String getContractorPerosn()
    {
        return getString("contractorPerosn");
    }
    public void setContractorPerosn(String item)
    {
        setString("contractorPerosn", item);
    }
    /**
     * Object:工程量确认单's 工程部负责人property 
     */
    public String getEngineeringPerosn()
    {
        return getString("engineeringPerosn");
    }
    public void setEngineeringPerosn(String item)
    {
        setString("engineeringPerosn", item);
    }
    /**
     * Object:工程量确认单's 成本部负责人property 
     */
    public String getCostPerosn()
    {
        return getString("costPerosn");
    }
    public void setCostPerosn(String item)
    {
        setString("costPerosn", item);
    }
    /**
     * Object:工程量确认单's 项目第一负责人property 
     */
    public String getFirstPerosn()
    {
        return getString("firstPerosn");
    }
    public void setFirstPerosn(String item)
    {
        setString("firstPerosn", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("77AF44C8");
    }
}