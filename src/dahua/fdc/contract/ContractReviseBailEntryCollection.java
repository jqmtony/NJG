package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractReviseBailEntryCollection extends AbstractObjectCollection 
{
    public ContractReviseBailEntryCollection()
    {
        super(ContractReviseBailEntryInfo.class);
    }
    public boolean add(ContractReviseBailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractReviseBailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractReviseBailEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractReviseBailEntryInfo get(int index)
    {
        return(ContractReviseBailEntryInfo)getObject(index);
    }
    public ContractReviseBailEntryInfo get(Object key)
    {
        return(ContractReviseBailEntryInfo)getObject(key);
    }
    public void set(int index, ContractReviseBailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractReviseBailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractReviseBailEntryInfo item)
    {
        return super.indexOf(item);
    }
}