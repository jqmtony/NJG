package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementEntryCollection extends AbstractObjectCollection 
{
    public ContractsettlementEntryCollection()
    {
        super(ContractsettlementEntryInfo.class);
    }
    public boolean add(ContractsettlementEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementEntryInfo get(int index)
    {
        return(ContractsettlementEntryInfo)getObject(index);
    }
    public ContractsettlementEntryInfo get(Object key)
    {
        return(ContractsettlementEntryInfo)getObject(key);
    }
    public void set(int index, ContractsettlementEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementEntryInfo item)
    {
        return super.indexOf(item);
    }
}