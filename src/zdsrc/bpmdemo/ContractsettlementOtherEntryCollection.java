package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementOtherEntryCollection extends AbstractObjectCollection 
{
    public ContractsettlementOtherEntryCollection()
    {
        super(ContractsettlementOtherEntryInfo.class);
    }
    public boolean add(ContractsettlementOtherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementOtherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementOtherEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementOtherEntryInfo get(int index)
    {
        return(ContractsettlementOtherEntryInfo)getObject(index);
    }
    public ContractsettlementOtherEntryInfo get(Object key)
    {
        return(ContractsettlementOtherEntryInfo)getObject(key);
    }
    public void set(int index, ContractsettlementOtherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementOtherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementOtherEntryInfo item)
    {
        return super.indexOf(item);
    }
}