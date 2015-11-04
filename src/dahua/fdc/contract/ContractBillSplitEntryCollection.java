package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillSplitEntryCollection extends AbstractObjectCollection 
{
    public ContractBillSplitEntryCollection()
    {
        super(ContractBillSplitEntryInfo.class);
    }
    public boolean add(ContractBillSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillSplitEntryInfo get(int index)
    {
        return(ContractBillSplitEntryInfo)getObject(index);
    }
    public ContractBillSplitEntryInfo get(Object key)
    {
        return(ContractBillSplitEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}