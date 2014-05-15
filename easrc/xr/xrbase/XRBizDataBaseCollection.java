package com.kingdee.eas.xr.xrbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XRBizDataBaseCollection extends AbstractObjectCollection 
{
    public XRBizDataBaseCollection()
    {
        super(XRBizDataBaseInfo.class);
    }
    public boolean add(XRBizDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XRBizDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XRBizDataBaseInfo item)
    {
        return removeObject(item);
    }
    public XRBizDataBaseInfo get(int index)
    {
        return(XRBizDataBaseInfo)getObject(index);
    }
    public XRBizDataBaseInfo get(Object key)
    {
        return(XRBizDataBaseInfo)getObject(key);
    }
    public void set(int index, XRBizDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XRBizDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XRBizDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}