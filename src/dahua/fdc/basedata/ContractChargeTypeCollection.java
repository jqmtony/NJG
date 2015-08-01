package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractChargeTypeCollection extends AbstractObjectCollection 
{
    public ContractChargeTypeCollection()
    {
        super(ContractChargeTypeInfo.class);
    }
    public boolean add(ContractChargeTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractChargeTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractChargeTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractChargeTypeInfo get(int index)
    {
        return(ContractChargeTypeInfo)getObject(index);
    }
    public ContractChargeTypeInfo get(Object key)
    {
        return(ContractChargeTypeInfo)getObject(key);
    }
    public void set(int index, ContractChargeTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractChargeTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractChargeTypeInfo item)
    {
        return super.indexOf(item);
    }
}