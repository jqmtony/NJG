package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBailEntryCollection extends AbstractObjectCollection 
{
    public ContractBailEntryCollection()
    {
        super(ContractBailEntryInfo.class);
    }
    public boolean add(ContractBailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBailEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBailEntryInfo get(int index)
    {
        return(ContractBailEntryInfo)getObject(index);
    }
    public ContractBailEntryInfo get(Object key)
    {
        return(ContractBailEntryInfo)getObject(key);
    }
    public void set(int index, ContractBailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBailEntryInfo item)
    {
        return super.indexOf(item);
    }
}