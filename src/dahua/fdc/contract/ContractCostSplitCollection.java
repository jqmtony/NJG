package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractCostSplitCollection extends AbstractObjectCollection 
{
    public ContractCostSplitCollection()
    {
        super(ContractCostSplitInfo.class);
    }
    public boolean add(ContractCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractCostSplitInfo item)
    {
        return removeObject(item);
    }
    public ContractCostSplitInfo get(int index)
    {
        return(ContractCostSplitInfo)getObject(index);
    }
    public ContractCostSplitInfo get(Object key)
    {
        return(ContractCostSplitInfo)getObject(key);
    }
    public void set(int index, ContractCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}