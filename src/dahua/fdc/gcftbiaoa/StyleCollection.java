package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StyleCollection extends AbstractObjectCollection 
{
    public StyleCollection()
    {
        super(StyleInfo.class);
    }
    public boolean add(StyleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StyleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StyleInfo item)
    {
        return removeObject(item);
    }
    public StyleInfo get(int index)
    {
        return(StyleInfo)getObject(index);
    }
    public StyleInfo get(Object key)
    {
        return(StyleInfo)getObject(key);
    }
    public void set(int index, StyleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StyleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StyleInfo item)
    {
        return super.indexOf(item);
    }
}