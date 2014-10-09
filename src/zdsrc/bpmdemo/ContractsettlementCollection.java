package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractsettlementCollection extends AbstractObjectCollection 
{
    public ContractsettlementCollection()
    {
        super(ContractsettlementInfo.class);
    }
    public boolean add(ContractsettlementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractsettlementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractsettlementInfo item)
    {
        return removeObject(item);
    }
    public ContractsettlementInfo get(int index)
    {
        return(ContractsettlementInfo)getObject(index);
    }
    public ContractsettlementInfo get(Object key)
    {
        return(ContractsettlementInfo)getObject(key);
    }
    public void set(int index, ContractsettlementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractsettlementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractsettlementInfo item)
    {
        return super.indexOf(item);
    }
}