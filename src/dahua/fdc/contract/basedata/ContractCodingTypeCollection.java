package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractCodingTypeCollection extends AbstractObjectCollection 
{
    public ContractCodingTypeCollection()
    {
        super(ContractCodingTypeInfo.class);
    }
    public boolean add(ContractCodingTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractCodingTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractCodingTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractCodingTypeInfo get(int index)
    {
        return(ContractCodingTypeInfo)getObject(index);
    }
    public ContractCodingTypeInfo get(Object key)
    {
        return(ContractCodingTypeInfo)getObject(key);
    }
    public void set(int index, ContractCodingTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractCodingTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractCodingTypeInfo item)
    {
        return super.indexOf(item);
    }
}