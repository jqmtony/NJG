package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractMoveHistoryInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractContractMoveHistoryInfo()
    {
        this("id");
    }
    protected AbstractContractMoveHistoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同交底记录 's 责任部门 property 
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
     * Object: 合同交底记录 's 责任人 property 
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
     * Object:合同交底记录's 移交日期property 
     */
    public java.util.Date getMoveDate()
    {
        return getDate("moveDate");
    }
    public void setMoveDate(java.util.Date item)
    {
        setDate("moveDate", item);
    }
    /**
     * Object:合同交底记录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:合同交底记录's 合同IDproperty 
     */
    public String getContractBillID()
    {
        return getString("contractBillID");
    }
    public void setContractBillID(String item)
    {
        setString("contractBillID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F3BCC0F6");
    }
}