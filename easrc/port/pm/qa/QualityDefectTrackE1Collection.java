package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QualityDefectTrackE1Collection extends AbstractObjectCollection 
{
    public QualityDefectTrackE1Collection()
    {
        super(QualityDefectTrackE1Info.class);
    }
    public boolean add(QualityDefectTrackE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(QualityDefectTrackE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QualityDefectTrackE1Info item)
    {
        return removeObject(item);
    }
    public QualityDefectTrackE1Info get(int index)
    {
        return(QualityDefectTrackE1Info)getObject(index);
    }
    public QualityDefectTrackE1Info get(Object key)
    {
        return(QualityDefectTrackE1Info)getObject(key);
    }
    public void set(int index, QualityDefectTrackE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(QualityDefectTrackE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QualityDefectTrackE1Info item)
    {
        return super.indexOf(item);
    }
}