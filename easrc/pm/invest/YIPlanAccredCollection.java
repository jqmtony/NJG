package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YIPlanAccredCollection extends AbstractObjectCollection 
{
    public YIPlanAccredCollection()
    {
        super(YIPlanAccredInfo.class);
    }
    public boolean add(YIPlanAccredInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(YIPlanAccredCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YIPlanAccredInfo item)
    {
        return removeObject(item);
    }
    public YIPlanAccredInfo get(int index)
    {
        return(YIPlanAccredInfo)getObject(index);
    }
    public YIPlanAccredInfo get(Object key)
    {
        return(YIPlanAccredInfo)getObject(key);
    }
    public void set(int index, YIPlanAccredInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(YIPlanAccredInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YIPlanAccredInfo item)
    {
        return super.indexOf(item);
    }
}