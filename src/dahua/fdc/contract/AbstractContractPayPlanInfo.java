package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractPayPlanInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection());
    }
    /**
     * Object: 合同付款计划 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: 合同付款计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection)get("entry");
    }
    /**
     * Object:合同付款计划's 版本号property 
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
     * Object:合同付款计划's 是否最新property 
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
     * Object: 合同付款计划 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: 合同付款计划 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object:合同付款计划's 用款部门property 
     */
    public com.kingdee.eas.fdc.contract.DepartmentEnum getDepartment()
    {
        return com.kingdee.eas.fdc.contract.DepartmentEnum.getEnum(getString("department"));
    }
    public void setDepartment(com.kingdee.eas.fdc.contract.DepartmentEnum item)
    {
		if (item != null) {
        setString("department", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("96800AE4");
    }
}