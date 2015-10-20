package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractConChangeNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractConChangeNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection());
    }
    /**
     * Object: ����ǳɱ���� 's ���ǩ֤ property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getContractChange()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("contractChange");
    }
    public void setContractChange(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("contractChange", item);
    }
    /**
     * Object: ����ǳɱ���� 's �����ַ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7C97E15");
    }
}