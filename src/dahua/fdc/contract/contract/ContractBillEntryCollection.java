package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillEntryCollection extends AbstractObjectCollection 
{
    public ContractBillEntryCollection()
    {
        super(ContractBillEntryInfo.class);
    }
    public boolean add(ContractBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillEntryInfo get(int index)
    {
        return(ContractBillEntryInfo)getObject(index);
    }
    public ContractBillEntryInfo get(Object key)
    {
        return(ContractBillEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}