package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementSettlementEntryCollection extends AbstractObjectCollection 
{
    public ContractsettlementSettlementEntryCollection()
    {
        super(ContractsettlementSettlementEntryInfo.class);
    }
    public boolean add(ContractsettlementSettlementEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementSettlementEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementSettlementEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementSettlementEntryInfo get(int index)
    {
        return(ContractsettlementSettlementEntryInfo)getObject(index);
    }
    public ContractsettlementSettlementEntryInfo get(Object key)
    {
        return(ContractsettlementSettlementEntryInfo)getObject(key);
    }
    public void set(int index, ContractsettlementSettlementEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementSettlementEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementSettlementEntryInfo item)
    {
        return super.indexOf(item);
    }
}