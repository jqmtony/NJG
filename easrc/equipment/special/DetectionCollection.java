package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DetectionCollection extends AbstractObjectCollection 
{
    public DetectionCollection()
    {
        super(DetectionInfo.class);
    }
    public boolean add(DetectionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DetectionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DetectionInfo item)
    {
        return removeObject(item);
    }
    public DetectionInfo get(int index)
    {
        return(DetectionInfo)getObject(index);
    }
    public DetectionInfo get(Object key)
    {
        return(DetectionInfo)getObject(key);
    }
    public void set(int index, DetectionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DetectionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DetectionInfo item)
    {
        return super.indexOf(item);
    }
}