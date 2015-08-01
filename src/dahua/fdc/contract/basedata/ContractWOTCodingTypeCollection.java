package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWOTCodingTypeCollection extends AbstractObjectCollection 
{
    public ContractWOTCodingTypeCollection()
    {
        super(ContractWOTCodingTypeInfo.class);
    }
    public boolean add(ContractWOTCodingTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWOTCodingTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWOTCodingTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractWOTCodingTypeInfo get(int index)
    {
        return(ContractWOTCodingTypeInfo)getObject(index);
    }
    public ContractWOTCodingTypeInfo get(Object key)
    {
        return(ContractWOTCodingTypeInfo)getObject(key);
    }
    public void set(int index, ContractWOTCodingTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWOTCodingTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWOTCodingTypeInfo item)
    {
        return super.indexOf(item);
    }
}