package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureStageCollection extends AbstractObjectCollection 
{
    public MeasureStageCollection()
    {
        super(MeasureStageInfo.class);
    }
    public boolean add(MeasureStageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureStageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureStageInfo item)
    {
        return removeObject(item);
    }
    public MeasureStageInfo get(int index)
    {
        return(MeasureStageInfo)getObject(index);
    }
    public MeasureStageInfo get(Object key)
    {
        return(MeasureStageInfo)getObject(key);
    }
    public void set(int index, MeasureStageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureStageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureStageInfo item)
    {
        return super.indexOf(item);
    }
}