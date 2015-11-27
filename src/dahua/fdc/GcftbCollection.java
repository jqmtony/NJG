package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GcftbCollection extends AbstractObjectCollection 
{
    public GcftbCollection()
    {
        super(GcftbInfo.class);
    }
    public boolean add(GcftbInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GcftbCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GcftbInfo item)
    {
        return removeObject(item);
    }
    public GcftbInfo get(int index)
    {
        return(GcftbInfo)getObject(index);
    }
    public GcftbInfo get(Object key)
    {
        return(GcftbInfo)getObject(key);
    }
    public void set(int index, GcftbInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GcftbInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GcftbInfo item)
    {
        return super.indexOf(item);
    }
}