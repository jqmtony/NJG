package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockEntryAttCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockEntryAttCollection()
    {
        super(MarketSupplierStockEntryAttInfo.class);
    }
    public boolean add(MarketSupplierStockEntryAttInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockEntryAttCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockEntryAttInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockEntryAttInfo get(int index)
    {
        return(MarketSupplierStockEntryAttInfo)getObject(index);
    }
    public MarketSupplierStockEntryAttInfo get(Object key)
    {
        return(MarketSupplierStockEntryAttInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockEntryAttInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockEntryAttInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockEntryAttInfo item)
    {
        return super.indexOf(item);
    }
}