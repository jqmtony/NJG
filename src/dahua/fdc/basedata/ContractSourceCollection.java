package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractSourceCollection extends AbstractObjectCollection 
{
    public ContractSourceCollection()
    {
        super(ContractSourceInfo.class);
    }
    public boolean add(ContractSourceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractSourceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractSourceInfo item)
    {
        return removeObject(item);
    }
    public ContractSourceInfo get(int index)
    {
        return(ContractSourceInfo)getObject(index);
    }
    public ContractSourceInfo get(Object key)
    {
        return(ContractSourceInfo)getObject(key);
    }
    public void set(int index, ContractSourceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractSourceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractSourceInfo item)
    {
        return super.indexOf(item);
    }
}