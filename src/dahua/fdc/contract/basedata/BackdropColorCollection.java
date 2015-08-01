package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BackdropColorCollection extends AbstractObjectCollection 
{
    public BackdropColorCollection()
    {
        super(BackdropColorInfo.class);
    }
    public boolean add(BackdropColorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BackdropColorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BackdropColorInfo item)
    {
        return removeObject(item);
    }
    public BackdropColorInfo get(int index)
    {
        return(BackdropColorInfo)getObject(index);
    }
    public BackdropColorInfo get(Object key)
    {
        return(BackdropColorInfo)getObject(key);
    }
    public void set(int index, BackdropColorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BackdropColorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BackdropColorInfo item)
    {
        return super.indexOf(item);
    }
}