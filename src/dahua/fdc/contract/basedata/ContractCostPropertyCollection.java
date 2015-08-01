package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractCostPropertyCollection extends AbstractObjectCollection 
{
    public ContractCostPropertyCollection()
    {
        super(ContractCostPropertyInfo.class);
    }
    public boolean add(ContractCostPropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractCostPropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractCostPropertyInfo item)
    {
        return removeObject(item);
    }
    public ContractCostPropertyInfo get(int index)
    {
        return(ContractCostPropertyInfo)getObject(index);
    }
    public ContractCostPropertyInfo get(Object key)
    {
        return(ContractCostPropertyInfo)getObject(key);
    }
    public void set(int index, ContractCostPropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractCostPropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractCostPropertyInfo item)
    {
        return super.indexOf(item);
    }
}