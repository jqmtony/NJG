package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementAssEntryCollection extends AbstractObjectCollection 
{
    public ContractsettlementAssEntryCollection()
    {
        super(ContractsettlementAssEntryInfo.class);
    }
    public boolean add(ContractsettlementAssEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementAssEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementAssEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementAssEntryInfo get(int index)
    {
        return(ContractsettlementAssEntryInfo)getObject(index);
    }
    public ContractsettlementAssEntryInfo get(Object key)
    {
        return(ContractsettlementAssEntryInfo)getObject(key);
    }
    public void set(int index, ContractsettlementAssEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementAssEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementAssEntryInfo item)
    {
        return super.indexOf(item);
    }
}