package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAttachListTreeCollection extends AbstractObjectCollection 
{
    public MarketSupplierAttachListTreeCollection()
    {
        super(MarketSupplierAttachListTreeInfo.class);
    }
    public boolean add(MarketSupplierAttachListTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAttachListTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAttachListTreeInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierAttachListTreeInfo get(int index)
    {
        return(MarketSupplierAttachListTreeInfo)getObject(index);
    }
    public MarketSupplierAttachListTreeInfo get(Object key)
    {
        return(MarketSupplierAttachListTreeInfo)getObject(key);
    }
    public void set(int index, MarketSupplierAttachListTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAttachListTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAttachListTreeInfo item)
    {
        return super.indexOf(item);
    }
}