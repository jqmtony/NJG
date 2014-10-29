package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockE4Collection extends AbstractObjectCollection 
{
    public MarketSupplierStockE4Collection()
    {
        super(MarketSupplierStockE4Info.class);
    }
    public boolean add(MarketSupplierStockE4Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockE4Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockE4Info item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockE4Info get(int index)
    {
        return(MarketSupplierStockE4Info)getObject(index);
    }
    public MarketSupplierStockE4Info get(Object key)
    {
        return(MarketSupplierStockE4Info)getObject(key);
    }
    public void set(int index, MarketSupplierStockE4Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockE4Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockE4Info item)
    {
        return super.indexOf(item);
    }
}