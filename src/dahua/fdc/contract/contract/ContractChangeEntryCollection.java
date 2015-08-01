package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractChangeEntryCollection extends AbstractObjectCollection 
{
    public ContractChangeEntryCollection()
    {
        super(ContractChangeEntryInfo.class);
    }
    public boolean add(ContractChangeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractChangeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractChangeEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractChangeEntryInfo get(int index)
    {
        return(ContractChangeEntryInfo)getObject(index);
    }
    public ContractChangeEntryInfo get(Object key)
    {
        return(ContractChangeEntryInfo)getObject(key);
    }
    public void set(int index, ContractChangeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractChangeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractChangeEntryInfo item)
    {
        return super.indexOf(item);
    }
}