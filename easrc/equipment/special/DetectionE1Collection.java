package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DetectionE1Collection extends AbstractObjectCollection 
{
    public DetectionE1Collection()
    {
        super(DetectionE1Info.class);
    }
    public boolean add(DetectionE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(DetectionE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DetectionE1Info item)
    {
        return removeObject(item);
    }
    public DetectionE1Info get(int index)
    {
        return(DetectionE1Info)getObject(index);
    }
    public DetectionE1Info get(Object key)
    {
        return(DetectionE1Info)getObject(key);
    }
    public void set(int index, DetectionE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(DetectionE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DetectionE1Info item)
    {
        return super.indexOf(item);
    }
}