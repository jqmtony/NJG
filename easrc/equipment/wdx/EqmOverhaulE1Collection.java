package com.kingdee.eas.port.equipment.wdx;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmOverhaulE1Collection extends AbstractObjectCollection 
{
    public EqmOverhaulE1Collection()
    {
        super(EqmOverhaulE1Info.class);
    }
    public boolean add(EqmOverhaulE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmOverhaulE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmOverhaulE1Info item)
    {
        return removeObject(item);
    }
    public EqmOverhaulE1Info get(int index)
    {
        return(EqmOverhaulE1Info)getObject(index);
    }
    public EqmOverhaulE1Info get(Object key)
    {
        return(EqmOverhaulE1Info)getObject(key);
    }
    public void set(int index, EqmOverhaulE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmOverhaulE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmOverhaulE1Info item)
    {
        return super.indexOf(item);
    }
}