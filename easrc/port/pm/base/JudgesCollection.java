package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesCollection extends AbstractObjectCollection 
{
    public JudgesCollection()
    {
        super(JudgesInfo.class);
    }
    public boolean add(JudgesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesInfo item)
    {
        return removeObject(item);
    }
    public JudgesInfo get(int index)
    {
        return(JudgesInfo)getObject(index);
    }
    public JudgesInfo get(Object key)
    {
        return(JudgesInfo)getObject(key);
    }
    public void set(int index, JudgesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesInfo item)
    {
        return super.indexOf(item);
    }
}