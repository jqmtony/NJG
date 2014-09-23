package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractDemoEntryCollection extends AbstractObjectCollection 
{
    public ContractDemoEntryCollection()
    {
        super(ContractDemoEntryInfo.class);
    }
    public boolean add(ContractDemoEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractDemoEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractDemoEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractDemoEntryInfo get(int index)
    {
        return(ContractDemoEntryInfo)getObject(index);
    }
    public ContractDemoEntryInfo get(Object key)
    {
        return(ContractDemoEntryInfo)getObject(key);
    }
    public void set(int index, ContractDemoEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractDemoEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractDemoEntryInfo item)
    {
        return super.indexOf(item);
    }
}