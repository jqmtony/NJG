package com.kingdee.eas.fdc.contract.contractsplit;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPCSplitBillEntryCollection extends AbstractObjectCollection 
{
    public ContractPCSplitBillEntryCollection()
    {
        super(ContractPCSplitBillEntryInfo.class);
    }
    public boolean add(ContractPCSplitBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPCSplitBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPCSplitBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractPCSplitBillEntryInfo get(int index)
    {
        return(ContractPCSplitBillEntryInfo)getObject(index);
    }
    public ContractPCSplitBillEntryInfo get(Object key)
    {
        return(ContractPCSplitBillEntryInfo)getObject(key);
    }
    public void set(int index, ContractPCSplitBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPCSplitBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPCSplitBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}