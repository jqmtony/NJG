package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractReviseBailCollection extends AbstractObjectCollection 
{
    public ContractReviseBailCollection()
    {
        super(ContractReviseBailInfo.class);
    }
    public boolean add(ContractReviseBailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractReviseBailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractReviseBailInfo item)
    {
        return removeObject(item);
    }
    public ContractReviseBailInfo get(int index)
    {
        return(ContractReviseBailInfo)getObject(index);
    }
    public ContractReviseBailInfo get(Object key)
    {
        return(ContractReviseBailInfo)getObject(key);
    }
    public void set(int index, ContractReviseBailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractReviseBailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractReviseBailInfo item)
    {
        return super.indexOf(item);
    }
}