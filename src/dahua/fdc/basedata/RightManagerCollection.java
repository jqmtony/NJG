package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RightManagerCollection extends AbstractObjectCollection 
{
    public RightManagerCollection()
    {
        super(RightManagerInfo.class);
    }
    public boolean add(RightManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RightManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RightManagerInfo item)
    {
        return removeObject(item);
    }
    public RightManagerInfo get(int index)
    {
        return(RightManagerInfo)getObject(index);
    }
    public RightManagerInfo get(Object key)
    {
        return(RightManagerInfo)getObject(key);
    }
    public void set(int index, RightManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RightManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RightManagerInfo item)
    {
        return super.indexOf(item);
    }
}