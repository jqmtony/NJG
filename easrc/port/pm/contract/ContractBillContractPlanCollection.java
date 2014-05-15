package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillContractPlanCollection extends AbstractObjectCollection 
{
    public ContractBillContractPlanCollection()
    {
        super(ContractBillContractPlanInfo.class);
    }
    public boolean add(ContractBillContractPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillContractPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillContractPlanInfo item)
    {
        return removeObject(item);
    }
    public ContractBillContractPlanInfo get(int index)
    {
        return(ContractBillContractPlanInfo)getObject(index);
    }
    public ContractBillContractPlanInfo get(Object key)
    {
        return(ContractBillContractPlanInfo)getObject(key);
    }
    public void set(int index, ContractBillContractPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillContractPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillContractPlanInfo item)
    {
        return super.indexOf(item);
    }
}