package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherEntryPCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherEntryPCollection()
    {
        super(MarketSupplierReviewGatherEntryPInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherEntryPInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherEntryPCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherEntryPInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherEntryPInfo get(int index)
    {
        return(MarketSupplierReviewGatherEntryPInfo)getObject(index);
    }
    public MarketSupplierReviewGatherEntryPInfo get(Object key)
    {
        return(MarketSupplierReviewGatherEntryPInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherEntryPInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherEntryPInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherEntryPInfo item)
    {
        return super.indexOf(item);
    }
}