package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreProjectCollection extends AbstractObjectCollection 
{
    public PreProjectCollection()
    {
        super(PreProjectInfo.class);
    }
    public boolean add(PreProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreProjectInfo item)
    {
        return removeObject(item);
    }
    public PreProjectInfo get(int index)
    {
        return(PreProjectInfo)getObject(index);
    }
    public PreProjectInfo get(Object key)
    {
        return(PreProjectInfo)getObject(key);
    }
    public void set(int index, PreProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreProjectInfo item)
    {
        return super.indexOf(item);
    }
}