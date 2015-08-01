package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanTypeCollection extends AbstractObjectCollection 
{
    public ContractPayPlanTypeCollection()
    {
        super(ContractPayPlanTypeInfo.class);
    }
    public boolean add(ContractPayPlanTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanTypeInfo get(int index)
    {
        return(ContractPayPlanTypeInfo)getObject(index);
    }
    public ContractPayPlanTypeInfo get(Object key)
    {
        return(ContractPayPlanTypeInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanTypeInfo item)
    {
        return super.indexOf(item);
    }
}