package com.kingdee.eas.port.equipment.wdx;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmOverhaulCollection extends AbstractObjectCollection 
{
    public EqmOverhaulCollection()
    {
        super(EqmOverhaulInfo.class);
    }
    public boolean add(EqmOverhaulInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmOverhaulCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmOverhaulInfo item)
    {
        return removeObject(item);
    }
    public EqmOverhaulInfo get(int index)
    {
        return(EqmOverhaulInfo)getObject(index);
    }
    public EqmOverhaulInfo get(Object key)
    {
        return(EqmOverhaulInfo)getObject(key);
    }
    public void set(int index, EqmOverhaulInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmOverhaulInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmOverhaulInfo item)
    {
        return super.indexOf(item);
    }
}