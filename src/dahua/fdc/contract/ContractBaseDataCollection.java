package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBaseDataCollection extends AbstractObjectCollection 
{
    public ContractBaseDataCollection()
    {
        super(ContractBaseDataInfo.class);
    }
    public boolean add(ContractBaseDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBaseDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBaseDataInfo item)
    {
        return removeObject(item);
    }
    public ContractBaseDataInfo get(int index)
    {
        return(ContractBaseDataInfo)getObject(index);
    }
    public ContractBaseDataInfo get(Object key)
    {
        return(ContractBaseDataInfo)getObject(key);
    }
    public void set(int index, ContractBaseDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBaseDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBaseDataInfo item)
    {
        return super.indexOf(item);
    }
}