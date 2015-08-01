package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractDetailDefInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractContractDetailDefInfo()
    {
        this("id");
    }
    protected AbstractContractDetailDefInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ͬ��ϸ��Ϣ���� 's ��ͬ���� property 
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
     * Object:��ͬ��ϸ��Ϣ����'s ��������property 
     */
    public com.kingdee.eas.fdc.basedata.DataTypeEnum getDataTypeEnum()
    {
        return com.kingdee.eas.fdc.basedata.DataTypeEnum.getEnum(getInt("dataTypeEnum"));
    }
    public void setDataTypeEnum(com.kingdee.eas.fdc.basedata.DataTypeEnum item)
    {
		if (item != null) {
        setInt("dataTypeEnum", item.getValue());
		}
    }
    /**
     * Object:��ͬ��ϸ��Ϣ����'s �Ƿ��¼��property 
     */
    public boolean isIsMustInput()
    {
        return getBoolean("isMustInput");
    }
    public void setIsMustInput(boolean item)
    {
        setBoolean("isMustInput", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ED295450");
    }
}