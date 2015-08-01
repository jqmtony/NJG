package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWOTCodingTypeInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractContractWOTCodingTypeInfo()
    {
        this("id");
    }
    protected AbstractContractWOTCodingTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ı���ͬ��������'s ����property 
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
     * Object: ���ı���ͬ�������� 's ��ͬ���� property 
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
     * Object:���ı���ͬ��������'s ����״̬property 
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
        return new BOSObjectType("9173D3DC");
    }
}