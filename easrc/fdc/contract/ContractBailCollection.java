package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBailCollection extends AbstractObjectCollection 
{
    public ContractBailCollection()
    {
        super(ContractBailInfo.class);
    }
    public boolean add(ContractBailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBailInfo item)
    {
        return removeObject(item);
    }
    public ContractBailInfo get(int index)
    {
        return(ContractBailInfo)getObject(index);
    }
    public ContractBailInfo get(Object key)
    {
        return(ContractBailInfo)getObject(key);
    }
    public void set(int index, ContractBailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBailInfo item)
    {
        return super.indexOf(item);
    }
}