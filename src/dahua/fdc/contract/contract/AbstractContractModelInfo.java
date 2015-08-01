package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractModelInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractModelInfo()
    {
        this("id");
    }
    protected AbstractContractModelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同范本 's 合同范本 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:合同范本's 摘要property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:合同范本's 版本号property 
     */
    public float getVerNumber()
    {
        return getFloat("verNumber");
    }
    public void setVerNumber(float item)
    {
        setFloat("verNumber", item);
    }
    /**
     * Object:合同范本's 是否最终版本property 
     */
    public boolean isIsLastVer()
    {
        return getBoolean("isLastVer");
    }
    public void setIsLastVer(boolean item)
    {
        setBoolean("isLastVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A0EA0ADC");
    }
}