package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractProgrammingEntryCollection extends AbstractObjectCollection 
{
    public ContractProgrammingEntryCollection()
    {
        super(ContractProgrammingEntryInfo.class);
    }
    public boolean add(ContractProgrammingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractProgrammingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractProgrammingEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractProgrammingEntryInfo get(int index)
    {
        return(ContractProgrammingEntryInfo)getObject(index);
    }
    public ContractProgrammingEntryInfo get(Object key)
    {
        return(ContractProgrammingEntryInfo)getObject(key);
    }
    public void set(int index, ContractProgrammingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractProgrammingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractProgrammingEntryInfo item)
    {
        return super.indexOf(item);
    }
}