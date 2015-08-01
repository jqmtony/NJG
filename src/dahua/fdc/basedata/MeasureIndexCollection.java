package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureIndexCollection extends AbstractObjectCollection 
{
    public MeasureIndexCollection()
    {
        super(MeasureIndexInfo.class);
    }
    public boolean add(MeasureIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureIndexInfo item)
    {
        return removeObject(item);
    }
    public MeasureIndexInfo get(int index)
    {
        return(MeasureIndexInfo)getObject(index);
    }
    public MeasureIndexInfo get(Object key)
    {
        return(MeasureIndexInfo)getObject(key);
    }
    public void set(int index, MeasureIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureIndexInfo item)
    {
        return super.indexOf(item);
    }
}