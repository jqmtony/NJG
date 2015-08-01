package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillReviseEntryCollection extends AbstractObjectCollection 
{
    public ContractBillReviseEntryCollection()
    {
        super(ContractBillReviseEntryInfo.class);
    }
    public boolean add(ContractBillReviseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillReviseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillReviseEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillReviseEntryInfo get(int index)
    {
        return(ContractBillReviseEntryInfo)getObject(index);
    }
    public ContractBillReviseEntryInfo get(Object key)
    {
        return(ContractBillReviseEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillReviseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillReviseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillReviseEntryInfo item)
    {
        return super.indexOf(item);
    }
}