package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractProgrammingCollection extends AbstractObjectCollection 
{
    public ContractProgrammingCollection()
    {
        super(ContractProgrammingInfo.class);
    }
    public boolean add(ContractProgrammingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractProgrammingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractProgrammingInfo item)
    {
        return removeObject(item);
    }
    public ContractProgrammingInfo get(int index)
    {
        return(ContractProgrammingInfo)getObject(index);
    }
    public ContractProgrammingInfo get(Object key)
    {
        return(ContractProgrammingInfo)getObject(key);
    }
    public void set(int index, ContractProgrammingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractProgrammingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractProgrammingInfo item)
    {
        return super.indexOf(item);
    }
}