package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPartAMaterialInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPartAMaterialInfo()
    {
        this("id");
    }
    protected AbstractPartAMaterialInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.material.PartAMaterialEntryCollection());
    }
    /**
     * Object: ������ϸ�� 's ��ͬ property 
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
     * Object: ������ϸ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.material.PartAMaterialEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.PartAMaterialEntryCollection)get("entrys");
    }
    /**
     * Object:������ϸ��'s �汾property 
     */
    public float getVersion()
    {
        return getFloat("version");
    }
    public void setVersion(float item)
    {
        setFloat("version", item);
    }
    /**
     * Object:������ϸ��'s ���°汾property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4D6EE46F");
    }
}