package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAppraiseTemplateEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierAppraiseTemplateEntryCollection()
    {
        super(MarketSupplierAppraiseTemplateEntryInfo.class);
    }
    public boolean add(MarketSupplierAppraiseTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAppraiseTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAppraiseTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierAppraiseTemplateEntryInfo get(int index)
    {
        return(MarketSupplierAppraiseTemplateEntryInfo)getObject(index);
    }
    public MarketSupplierAppraiseTemplateEntryInfo get(Object key)
    {
        return(MarketSupplierAppraiseTemplateEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierAppraiseTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAppraiseTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAppraiseTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}