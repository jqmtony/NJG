package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EASLogTimeCollection extends AbstractObjectCollection 
{
    public EASLogTimeCollection()
    {
        super(EASLogTimeInfo.class);
    }
    public boolean add(EASLogTimeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EASLogTimeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EASLogTimeInfo item)
    {
        return removeObject(item);
    }
    public EASLogTimeInfo get(int index)
    {
        return(EASLogTimeInfo)getObject(index);
    }
    public EASLogTimeInfo get(Object key)
    {
        return(EASLogTimeInfo)getObject(key);
    }
    public void set(int index, EASLogTimeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EASLogTimeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EASLogTimeInfo item)
    {
        return super.indexOf(item);
    }
}