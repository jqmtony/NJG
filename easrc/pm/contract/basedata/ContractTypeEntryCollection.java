package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractTypeEntryCollection extends AbstractObjectCollection 
{
    public ContractTypeEntryCollection()
    {
        super(ContractTypeEntryInfo.class);
    }
    public boolean add(ContractTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractTypeEntryInfo get(int index)
    {
        return(ContractTypeEntryInfo)getObject(index);
    }
    public ContractTypeEntryInfo get(Object key)
    {
        return(ContractTypeEntryInfo)getObject(key);
    }
    public void set(int index, ContractTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}