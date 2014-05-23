package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockEntryCollection()
    {
        super(MarketSupplierStockEntryInfo.class);
    }
    public boolean add(MarketSupplierStockEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockEntryInfo get(int index)
    {
        return(MarketSupplierStockEntryInfo)getObject(index);
    }
    public MarketSupplierStockEntryInfo get(Object key)
    {
        return(MarketSupplierStockEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockEntryInfo item)
    {
        return super.indexOf(item);
    }
}