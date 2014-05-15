package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmTypeTreeCollection extends AbstractObjectCollection 
{
    public EqmTypeTreeCollection()
    {
        super(EqmTypeTreeInfo.class);
    }
    public boolean add(EqmTypeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmTypeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmTypeTreeInfo item)
    {
        return removeObject(item);
    }
    public EqmTypeTreeInfo get(int index)
    {
        return(EqmTypeTreeInfo)getObject(index);
    }
    public EqmTypeTreeInfo get(Object key)
    {
        return(EqmTypeTreeInfo)getObject(key);
    }
    public void set(int index, EqmTypeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmTypeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmTypeTreeInfo item)
    {
        return super.indexOf(item);
    }
}