package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InnerManagerCollection extends AbstractObjectCollection 
{
    public InnerManagerCollection()
    {
        super(InnerManagerInfo.class);
    }
    public boolean add(InnerManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InnerManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InnerManagerInfo item)
    {
        return removeObject(item);
    }
    public InnerManagerInfo get(int index)
    {
        return(InnerManagerInfo)getObject(index);
    }
    public InnerManagerInfo get(Object key)
    {
        return(InnerManagerInfo)getObject(key);
    }
    public void set(int index, InnerManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InnerManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InnerManagerInfo item)
    {
        return super.indexOf(item);
    }
}