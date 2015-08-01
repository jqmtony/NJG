package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractMoveHistoryCollection extends AbstractObjectCollection 
{
    public ContractMoveHistoryCollection()
    {
        super(ContractMoveHistoryInfo.class);
    }
    public boolean add(ContractMoveHistoryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractMoveHistoryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractMoveHistoryInfo item)
    {
        return removeObject(item);
    }
    public ContractMoveHistoryInfo get(int index)
    {
        return(ContractMoveHistoryInfo)getObject(index);
    }
    public ContractMoveHistoryInfo get(Object key)
    {
        return(ContractMoveHistoryInfo)getObject(key);
    }
    public void set(int index, ContractMoveHistoryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractMoveHistoryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractMoveHistoryInfo item)
    {
        return super.indexOf(item);
    }
}