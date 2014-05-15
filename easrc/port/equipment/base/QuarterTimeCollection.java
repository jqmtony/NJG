package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuarterTimeCollection extends AbstractObjectCollection 
{
    public QuarterTimeCollection()
    {
        super(QuarterTimeInfo.class);
    }
    public boolean add(QuarterTimeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuarterTimeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuarterTimeInfo item)
    {
        return removeObject(item);
    }
    public QuarterTimeInfo get(int index)
    {
        return(QuarterTimeInfo)getObject(index);
    }
    public QuarterTimeInfo get(Object key)
    {
        return(QuarterTimeInfo)getObject(key);
    }
    public void set(int index, QuarterTimeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuarterTimeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuarterTimeInfo item)
    {
        return super.indexOf(item);
    }
}