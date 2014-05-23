package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractChangeSettleBillEntryCollection extends AbstractObjectCollection 
{
    public ContractChangeSettleBillEntryCollection()
    {
        super(ContractChangeSettleBillEntryInfo.class);
    }
    public boolean add(ContractChangeSettleBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractChangeSettleBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractChangeSettleBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractChangeSettleBillEntryInfo get(int index)
    {
        return(ContractChangeSettleBillEntryInfo)getObject(index);
    }
    public ContractChangeSettleBillEntryInfo get(Object key)
    {
        return(ContractChangeSettleBillEntryInfo)getObject(key);
    }
    public void set(int index, ContractChangeSettleBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractChangeSettleBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractChangeSettleBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}