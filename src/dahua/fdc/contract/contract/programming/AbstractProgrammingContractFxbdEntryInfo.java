package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractFxbdEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProgrammingContractFxbdEntryInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractFxbdEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 副项表单 's null property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getParent1()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: 副项表单 's 部门类型 property 
     */
    public com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo getDepType()
    {
        return (com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo)get("depType");
    }
    public void setDepType(com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo item)
    {
        put("depType", item);
    }
    /**
     * Object:副项表单's 事项名称property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:副项表单's 计划完成时间property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:副项表单's 实际完成时间property 
     */
    public java.util.Date getRealDate()
    {
        return getDate("realDate");
    }
    public void setRealDate(java.util.Date item)
    {
        setDate("realDate", item);
    }
    /**
     * Object:副项表单's recordSeqproperty 
     */
    public String getRecordSeq()
    {
        return getString("recordSeq");
    }
    public void setRecordSeq(String item)
    {
        setString("recordSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6B7634A3");
    }
}