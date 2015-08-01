package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractExecInfosCollection extends AbstractObjectCollection 
{
    public ContractExecInfosCollection()
    {
        super(ContractExecInfosInfo.class);
    }
    public boolean add(ContractExecInfosInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractExecInfosCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractExecInfosInfo item)
    {
        return removeObject(item);
    }
    public ContractExecInfosInfo get(int index)
    {
        return(ContractExecInfosInfo)getObject(index);
    }
    public ContractExecInfosInfo get(Object key)
    {
        return(ContractExecInfosInfo)getObject(key);
    }
    public void set(int index, ContractExecInfosInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractExecInfosInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractExecInfosInfo item)
    {
        return super.indexOf(item);
    }
}