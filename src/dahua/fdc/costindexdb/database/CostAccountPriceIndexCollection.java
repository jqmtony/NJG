package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAccountPriceIndexCollection extends AbstractObjectCollection 
{
    public CostAccountPriceIndexCollection()
    {
        super(CostAccountPriceIndexInfo.class);
    }
    public boolean add(CostAccountPriceIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAccountPriceIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAccountPriceIndexInfo item)
    {
        return removeObject(item);
    }
    public CostAccountPriceIndexInfo get(int index)
    {
        return(CostAccountPriceIndexInfo)getObject(index);
    }
    public CostAccountPriceIndexInfo get(Object key)
    {
        return(CostAccountPriceIndexInfo)getObject(key);
    }
    public void set(int index, CostAccountPriceIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAccountPriceIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAccountPriceIndexInfo item)
    {
        return super.indexOf(item);
    }
}