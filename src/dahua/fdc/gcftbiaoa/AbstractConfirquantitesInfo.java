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
     * Object:������ȷ�ϵ�'s �Ƿ�����ƾ֤property 
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
     * Object:������ȷ�ϵ�'s �汾��property 
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
     * Object:������ȷ�ϵ�'s ����property 
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
     * Object:������ȷ�ϵ�'s �������property 
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
     * Object:������ȷ�ϵ�'s ����״̬property 
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
     * Object:������ȷ�ϵ�'s �Ƿ����°�property 
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
     * Object: ������ȷ�ϵ� 's ��ͬ���� property 
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
     * Object: ������ȷ�ϵ� 's ������Ŀ property 
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
     * Object: ������ȷ�ϵ� 's �а���λ property 
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
     * Object:������ȷ�ϵ�'s ��Ŀ���ʱ��property 
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
     * Object:������ȷ�ϵ�'s ���̲����property 
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
     * Object:������ȷ�ϵ�'s �ɱ��������property 
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
     * Object:������ȷ�ϵ�'s ��Ŀ��˾��һ���������property 
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
     * Object:������ȷ�ϵ�'s �걨������property 
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
     * Object:������ȷ�ϵ�'s �а���������property 
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
     * Object:������ȷ�ϵ�'s ���̲�������property 
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
     * Object:������ȷ�ϵ�'s �ɱ���������property 
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
     * Object:������ȷ�ϵ�'s ��Ŀ��һ������property 
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