package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SelfDirectorCollection extends AbstractObjectCollection 
{
    public SelfDirectorCollection()
    {
        super(SelfDirectorInfo.class);
    }
    public boolean add(SelfDirectorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SelfDirectorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SelfDirectorInfo item)
    {
        return removeObject(item);
    }
    public SelfDirectorInfo get(int index)
    {
        return(SelfDirectorInfo)getObject(index);
    }
    public SelfDirectorInfo get(Object key)
    {
        return(SelfDirectorInfo)getObject(key);
    }
    public void set(int index, SelfDirectorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SelfDirectorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SelfDirectorInfo item)
    {
        return super.indexOf(item);
    }
}