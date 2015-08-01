package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractCodingTypeInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractContractCodingTypeInfo()
    {
        this("id");
    }
    protected AbstractContractCodingTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:合同编码类型's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object: 合同编码类型 's 合同类型 property 
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
     * Object:合同编码类型's 合同性质property 
     */
    public com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum getSecondType()
    {
        return com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum.getEnum(getString("secondType"));
    }
    public void setSecondType(com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum item)
    {
		if (item != null) {
        setString("secondType", item.getValue());
		}
    }
    /**
     * Object:合同编码类型's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum getThirdType()
    {
        return com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum.getEnum(getString("thirdType"));
    }
    public void setThirdType(com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum item)
    {
		if (item != null) {
        setString("thirdType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BFCC7EE8");
    }
}