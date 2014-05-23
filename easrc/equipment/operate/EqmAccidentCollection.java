package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmAccidentCollection extends AbstractObjectCollection 
{
    public EqmAccidentCollection()
    {
        super(EqmAccidentInfo.class);
    }
    public boolean add(EqmAccidentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmAccidentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmAccidentInfo item)
    {
        return removeObject(item);
    }
    public EqmAccidentInfo get(int index)
    {
        return(EqmAccidentInfo)getObject(index);
    }
    public EqmAccidentInfo get(Object key)
    {
        return(EqmAccidentInfo)getObject(key);
    }
    public void set(int index, EqmAccidentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmAccidentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmAccidentInfo item)
    {
        return super.indexOf(item);
    }
}