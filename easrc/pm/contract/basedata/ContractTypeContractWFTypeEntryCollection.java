package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractTypeContractWFTypeEntryCollection extends AbstractObjectCollection 
{
    public ContractTypeContractWFTypeEntryCollection()
    {
        super(ContractTypeContractWFTypeEntryInfo.class);
    }
    public boolean add(ContractTypeContractWFTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractTypeContractWFTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractTypeContractWFTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractTypeContractWFTypeEntryInfo get(int index)
    {
        return(ContractTypeContractWFTypeEntryInfo)getObject(index);
    }
    public ContractTypeContractWFTypeEntryInfo get(Object key)
    {
        return(ContractTypeContractWFTypeEntryInfo)getObject(key);
    }
    public void set(int index, ContractTypeContractWFTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractTypeContractWFTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractTypeContractWFTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}