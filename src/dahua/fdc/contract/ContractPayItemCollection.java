package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayItemCollection extends AbstractObjectCollection 
{
    public ContractPayItemCollection()
    {
        super(ContractPayItemInfo.class);
    }
    public boolean add(ContractPayItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayItemInfo item)
    {
        return removeObject(item);
    }
    public ContractPayItemInfo get(int index)
    {
        return(ContractPayItemInfo)getObject(index);
    }
    public ContractPayItemInfo get(Object key)
    {
        return(ContractPayItemInfo)getObject(key);
    }
    public void set(int index, ContractPayItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayItemInfo item)
    {
        return super.indexOf(item);
    }
}