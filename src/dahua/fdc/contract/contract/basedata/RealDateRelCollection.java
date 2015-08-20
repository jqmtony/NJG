package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RealDateRelCollection extends AbstractObjectCollection 
{
    public RealDateRelCollection()
    {
        super(RealDateRelInfo.class);
    }
    public boolean add(RealDateRelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RealDateRelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RealDateRelInfo item)
    {
        return removeObject(item);
    }
    public RealDateRelInfo get(int index)
    {
        return(RealDateRelInfo)getObject(index);
    }
    public RealDateRelInfo get(Object key)
    {
        return(RealDateRelInfo)getObject(key);
    }
    public void set(int index, RealDateRelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RealDateRelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RealDateRelInfo item)
    {
        return super.indexOf(item);
    }
}