package com.kingdee.eas.xr.xrbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XRDataBaseCollection extends AbstractObjectCollection 
{
    public XRDataBaseCollection()
    {
        super(XRDataBaseInfo.class);
    }
    public boolean add(XRDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XRDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XRDataBaseInfo item)
    {
        return removeObject(item);
    }
    public XRDataBaseInfo get(int index)
    {
        return(XRDataBaseInfo)getObject(index);
    }
    public XRDataBaseInfo get(Object key)
    {
        return(XRDataBaseInfo)getObject(key);
    }
    public void set(int index, XRDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XRDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XRDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}