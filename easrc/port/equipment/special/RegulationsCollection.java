package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RegulationsCollection extends AbstractObjectCollection 
{
    public RegulationsCollection()
    {
        super(RegulationsInfo.class);
    }
    public boolean add(RegulationsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RegulationsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RegulationsInfo item)
    {
        return removeObject(item);
    }
    public RegulationsInfo get(int index)
    {
        return(RegulationsInfo)getObject(index);
    }
    public RegulationsInfo get(Object key)
    {
        return(RegulationsInfo)getObject(key);
    }
    public void set(int index, RegulationsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RegulationsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RegulationsInfo item)
    {
        return super.indexOf(item);
    }
}