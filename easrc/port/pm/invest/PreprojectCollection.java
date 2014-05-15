package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreprojectCollection extends AbstractObjectCollection 
{
    public PreprojectCollection()
    {
        super(PreprojectInfo.class);
    }
    public boolean add(PreprojectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreprojectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreprojectInfo item)
    {
        return removeObject(item);
    }
    public PreprojectInfo get(int index)
    {
        return(PreprojectInfo)getObject(index);
    }
    public PreprojectInfo get(Object key)
    {
        return(PreprojectInfo)getObject(key);
    }
    public void set(int index, PreprojectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreprojectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreprojectInfo item)
    {
        return super.indexOf(item);
    }
}