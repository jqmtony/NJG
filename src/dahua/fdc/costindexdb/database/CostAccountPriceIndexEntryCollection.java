package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAccountPriceIndexEntryCollection extends AbstractObjectCollection 
{
    public CostAccountPriceIndexEntryCollection()
    {
        super(CostAccountPriceIndexEntryInfo.class);
    }
    public boolean add(CostAccountPriceIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAccountPriceIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAccountPriceIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public CostAccountPriceIndexEntryInfo get(int index)
    {
        return(CostAccountPriceIndexEntryInfo)getObject(index);
    }
    public CostAccountPriceIndexEntryInfo get(Object key)
    {
        return(CostAccountPriceIndexEntryInfo)getObject(key);
    }
    public void set(int index, CostAccountPriceIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAccountPriceIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAccountPriceIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}