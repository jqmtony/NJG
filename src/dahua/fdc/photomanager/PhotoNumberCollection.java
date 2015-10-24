package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PhotoNumberCollection extends AbstractObjectCollection 
{
    public PhotoNumberCollection()
    {
        super(PhotoNumberInfo.class);
    }
    public boolean add(PhotoNumberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PhotoNumberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PhotoNumberInfo item)
    {
        return removeObject(item);
    }
    public PhotoNumberInfo get(int index)
    {
        return(PhotoNumberInfo)getObject(index);
    }
    public PhotoNumberInfo get(Object key)
    {
        return(PhotoNumberInfo)getObject(key);
    }
    public void set(int index, PhotoNumberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PhotoNumberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PhotoNumberInfo item)
    {
        return super.indexOf(item);
    }
}