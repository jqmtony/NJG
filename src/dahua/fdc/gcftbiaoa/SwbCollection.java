package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SwbCollection extends AbstractObjectCollection 
{
    public SwbCollection()
    {
        super(SwbInfo.class);
    }
    public boolean add(SwbInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SwbCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SwbInfo item)
    {
        return removeObject(item);
    }
    public SwbInfo get(int index)
    {
        return(SwbInfo)getObject(index);
    }
    public SwbInfo get(Object key)
    {
        return(SwbInfo)getObject(key);
    }
    public void set(int index, SwbInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SwbInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SwbInfo item)
    {
        return super.indexOf(item);
    }
}