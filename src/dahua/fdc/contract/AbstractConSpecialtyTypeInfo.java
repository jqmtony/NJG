package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConSpecialtyTypeInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractConSpecialtyTypeInfo()
    {
        this("id");
    }
    protected AbstractConSpecialtyTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: רҵ���� 's ���ǩ֤ȷ�Ϸ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: רҵ���� 's רҵ���� property 
     */
    public com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo getSpecialtyType()
    {
        return (com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo)get("specialtyType");
    }
    public void setSpecialtyType(com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo item)
    {
        put("specialtyType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8563B731");
    }
}