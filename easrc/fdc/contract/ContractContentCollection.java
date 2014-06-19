package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractContentCollection extends AbstractObjectCollection 
{
    public ContractContentCollection()
    {
        super(ContractContentInfo.class);
    }
    public boolean add(ContractContentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractContentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractContentInfo item)
    {
        return removeObject(item);
    }
    public ContractContentInfo get(int index)
    {
        return(ContractContentInfo)getObject(index);
    }
    public ContractContentInfo get(Object key)
    {
        return(ContractContentInfo)getObject(key);
    }
    public void set(int index, ContractContentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractContentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractContentInfo item)
    {
        return super.indexOf(item);
    }
}