package com.kingdee.eas.fdc.contract.contractsplit;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPCSplitBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractPCSplitBillInfo()
    {
        this("id");
    }
    protected AbstractContractPCSplitBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection());
    }
    /**
     * Object:���ں�ͬ���'s ���״̬property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object: ���ں�ͬ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ���ں�ͬ��� 's ��ͬ property 
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
     * Object: ���ں�ͬ��� 's ��ͬ���ָ�� property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getContractChangeBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("contractChangeBill");
    }
    public void setContractChangeBill(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("contractChangeBill", item);
    }
    /**
     * Object: ���ں�ͬ��� 's ��ͬ���ȷ�� property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getContractChangeSettleBill()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("contractChangeSettleBill");
    }
    public void setContractChangeSettleBill(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("contractChangeSettleBill", item);
    }
    /**
     * Object: ���ں�ͬ��� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettlementBillInfo getContractSettleBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractSettlementBillInfo)get("contractSettleBill");
    }
    public void setContractSettleBill(com.kingdee.eas.fdc.contract.ContractSettlementBillInfo item)
    {
        put("contractSettleBill", item);
    }
    /**
     * Object: ���ں�ͬ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8FB144FB");
    }
}