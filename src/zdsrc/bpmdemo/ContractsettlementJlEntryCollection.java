package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementJlEntryCollection extends AbstractObjectCollection 
{
    public ContractsettlementJlEntryCollection()
    {
        super(ContractsettlementJlEntryInfo.class);
    }
    public boolean add(ContractsettlementJlEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementJlEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementJlEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementJlEntryInfo get(int index)
    {
        return(ContractsettlementJlEntryInfo)getObject(index);
    }
    public ContractsettlementJlEntryInfo get(Object key)
    {
        return(ContractsettlementJlEntryInfo)getObject(key);
    }
    public void set(int index, ContractsettlementJlEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementJlEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementJlEntryInfo item)
    {
        return super.indexOf(item);
    }
}