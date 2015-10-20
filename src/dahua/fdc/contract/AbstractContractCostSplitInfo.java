package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractContractCostSplitInfo()
    {
        this("id");
    }
    protected AbstractContractCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection());
    }
    /**
     * Object: ��ͬ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object:��ͬ���'s �Ƿ���ȷ��property 
     */
    public boolean isIsConfirm()
    {
        return getBoolean("isConfirm");
    }
    public void setIsConfirm(boolean item)
    {
        setBoolean("isConfirm", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1C2F5180");
    }
}