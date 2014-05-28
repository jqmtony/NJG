package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DetectionE2Collection extends AbstractObjectCollection 
{
    public DetectionE2Collection()
    {
        super(DetectionE2Info.class);
    }
    public boolean add(DetectionE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(DetectionE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DetectionE2Info item)
    {
        return removeObject(item);
    }
    public DetectionE2Info get(int index)
    {
        return(DetectionE2Info)getObject(index);
    }
    public DetectionE2Info get(Object key)
    {
        return(DetectionE2Info)getObject(key);
    }
    public void set(int index, DetectionE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(DetectionE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DetectionE2Info item)
    {
        return super.indexOf(item);
    }
}