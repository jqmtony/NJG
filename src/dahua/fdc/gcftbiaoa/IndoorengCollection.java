package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndoorengCollection extends AbstractObjectCollection 
{
    public IndoorengCollection()
    {
        super(IndoorengInfo.class);
    }
    public boolean add(IndoorengInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndoorengCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndoorengInfo item)
    {
        return removeObject(item);
    }
    public IndoorengInfo get(int index)
    {
        return(IndoorengInfo)getObject(index);
    }
    public IndoorengInfo get(Object key)
    {
        return(IndoorengInfo)getObject(key);
    }
    public void set(int index, IndoorengInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndoorengInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndoorengInfo item)
    {
        return super.indexOf(item);
    }
}