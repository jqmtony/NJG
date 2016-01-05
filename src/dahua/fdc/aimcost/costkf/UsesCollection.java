package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UsesCollection extends AbstractObjectCollection 
{
    public UsesCollection()
    {
        super(UsesInfo.class);
    }
    public boolean add(UsesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UsesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UsesInfo item)
    {
        return removeObject(item);
    }
    public UsesInfo get(int index)
    {
        return(UsesInfo)getObject(index);
    }
    public UsesInfo get(Object key)
    {
        return(UsesInfo)getObject(key);
    }
    public void set(int index, UsesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UsesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UsesInfo item)
    {
        return super.indexOf(item);
    }
}