package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DirectorCollection extends AbstractObjectCollection 
{
    public DirectorCollection()
    {
        super(DirectorInfo.class);
    }
    public boolean add(DirectorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DirectorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DirectorInfo item)
    {
        return removeObject(item);
    }
    public DirectorInfo get(int index)
    {
        return(DirectorInfo)getObject(index);
    }
    public DirectorInfo get(Object key)
    {
        return(DirectorInfo)getObject(key);
    }
    public void set(int index, DirectorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DirectorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DirectorInfo item)
    {
        return super.indexOf(item);
    }
}