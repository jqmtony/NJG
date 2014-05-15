package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierStockEntryPersonCollection extends AbstractObjectCollection 
{
    public MarketSupplierStockEntryPersonCollection()
    {
        super(MarketSupplierStockEntryPersonInfo.class);
    }
    public boolean add(MarketSupplierStockEntryPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierStockEntryPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierStockEntryPersonInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierStockEntryPersonInfo get(int index)
    {
        return(MarketSupplierStockEntryPersonInfo)getObject(index);
    }
    public MarketSupplierStockEntryPersonInfo get(Object key)
    {
        return(MarketSupplierStockEntryPersonInfo)getObject(key);
    }
    public void set(int index, MarketSupplierStockEntryPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierStockEntryPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierStockEntryPersonInfo item)
    {
        return super.indexOf(item);
    }
}