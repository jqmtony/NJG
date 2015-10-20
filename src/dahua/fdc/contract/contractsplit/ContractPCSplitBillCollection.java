package com.kingdee.eas.fdc.contract.contractsplit;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPCSplitBillCollection extends AbstractObjectCollection 
{
    public ContractPCSplitBillCollection()
    {
        super(ContractPCSplitBillInfo.class);
    }
    public boolean add(ContractPCSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPCSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPCSplitBillInfo item)
    {
        return removeObject(item);
    }
    public ContractPCSplitBillInfo get(int index)
    {
        return(ContractPCSplitBillInfo)getObject(index);
    }
    public ContractPCSplitBillInfo get(Object key)
    {
        return(ContractPCSplitBillInfo)getObject(key);
    }
    public void set(int index, ContractPCSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPCSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPCSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}