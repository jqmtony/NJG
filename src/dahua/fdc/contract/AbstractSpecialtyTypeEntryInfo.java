package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialtyTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSpecialtyTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractSpecialtyTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: רҵ���� 's ���ǩ֤�����¼ property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
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
        return new BOSObjectType("0E915F75");
    }
}