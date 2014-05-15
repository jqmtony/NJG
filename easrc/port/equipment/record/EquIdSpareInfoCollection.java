package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquIdSpareInfoCollection extends AbstractObjectCollection 
{
    public EquIdSpareInfoCollection()
    {
        super(EquIdSpareInfoInfo.class);
    }
    public boolean add(EquIdSpareInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquIdSpareInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquIdSpareInfoInfo item)
    {
        return removeObject(item);
    }
    public EquIdSpareInfoInfo get(int index)
    {
        return(EquIdSpareInfoInfo)getObject(index);
    }
    public EquIdSpareInfoInfo get(Object key)
    {
        return(EquIdSpareInfoInfo)getObject(key);
    }
    public void set(int index, EquIdSpareInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquIdSpareInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquIdSpareInfoInfo item)
    {
        return super.indexOf(item);
    }
}