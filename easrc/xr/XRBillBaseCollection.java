package com.kingdee.eas.xr;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XRBillBaseCollection extends AbstractObjectCollection 
{
    public XRBillBaseCollection()
    {
        super(XRBillBaseInfo.class);
    }
    public boolean add(XRBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XRBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XRBillBaseInfo item)
    {
        return removeObject(item);
    }
    public XRBillBaseInfo get(int index)
    {
        return(XRBillBaseInfo)getObject(index);
    }
    public XRBillBaseInfo get(Object key)
    {
        return(XRBillBaseInfo)getObject(key);
    }
    public void set(int index, XRBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XRBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XRBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}