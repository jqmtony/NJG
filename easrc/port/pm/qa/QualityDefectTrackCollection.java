package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QualityDefectTrackCollection extends AbstractObjectCollection 
{
    public QualityDefectTrackCollection()
    {
        super(QualityDefectTrackInfo.class);
    }
    public boolean add(QualityDefectTrackInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QualityDefectTrackCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QualityDefectTrackInfo item)
    {
        return removeObject(item);
    }
    public QualityDefectTrackInfo get(int index)
    {
        return(QualityDefectTrackInfo)getObject(index);
    }
    public QualityDefectTrackInfo get(Object key)
    {
        return(QualityDefectTrackInfo)getObject(key);
    }
    public void set(int index, QualityDefectTrackInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QualityDefectTrackInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QualityDefectTrackInfo item)
    {
        return super.indexOf(item);
    }
}