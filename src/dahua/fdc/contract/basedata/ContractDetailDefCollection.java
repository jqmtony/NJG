package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractDetailDefCollection extends AbstractObjectCollection 
{
    public ContractDetailDefCollection()
    {
        super(ContractDetailDefInfo.class);
    }
    public boolean add(ContractDetailDefInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractDetailDefCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractDetailDefInfo item)
    {
        return removeObject(item);
    }
    public ContractDetailDefInfo get(int index)
    {
        return(ContractDetailDefInfo)getObject(index);
    }
    public ContractDetailDefInfo get(Object key)
    {
        return(ContractDetailDefInfo)getObject(key);
    }
    public void set(int index, ContractDetailDefInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractDetailDefInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractDetailDefInfo item)
    {
        return super.indexOf(item);
    }
}