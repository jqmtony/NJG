package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractModelCollection extends AbstractObjectCollection 
{
    public ContractModelCollection()
    {
        super(ContractModelInfo.class);
    }
    public boolean add(ContractModelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractModelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractModelInfo item)
    {
        return removeObject(item);
    }
    public ContractModelInfo get(int index)
    {
        return(ContractModelInfo)getObject(index);
    }
    public ContractModelInfo get(Object key)
    {
        return(ContractModelInfo)getObject(key);
    }
    public void set(int index, ContractModelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractModelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractModelInfo item)
    {
        return super.indexOf(item);
    }
}