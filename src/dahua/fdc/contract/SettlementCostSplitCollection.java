package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettlementCostSplitCollection extends AbstractObjectCollection 
{
    public SettlementCostSplitCollection()
    {
        super(SettlementCostSplitInfo.class);
    }
    public boolean add(SettlementCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettlementCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettlementCostSplitInfo item)
    {
        return removeObject(item);
    }
    public SettlementCostSplitInfo get(int index)
    {
        return(SettlementCostSplitInfo)getObject(index);
    }
    public SettlementCostSplitInfo get(Object key)
    {
        return(SettlementCostSplitInfo)getObject(key);
    }
    public void set(int index, SettlementCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettlementCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettlementCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}