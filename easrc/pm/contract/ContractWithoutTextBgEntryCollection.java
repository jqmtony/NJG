package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWithoutTextBgEntryCollection extends AbstractObjectCollection 
{
    public ContractWithoutTextBgEntryCollection()
    {
        super(ContractWithoutTextBgEntryInfo.class);
    }
    public boolean add(ContractWithoutTextBgEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWithoutTextBgEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWithoutTextBgEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractWithoutTextBgEntryInfo get(int index)
    {
        return(ContractWithoutTextBgEntryInfo)getObject(index);
    }
    public ContractWithoutTextBgEntryInfo get(Object key)
    {
        return(ContractWithoutTextBgEntryInfo)getObject(key);
    }
    public void set(int index, ContractWithoutTextBgEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWithoutTextBgEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWithoutTextBgEntryInfo item)
    {
        return super.indexOf(item);
    }
}