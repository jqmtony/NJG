package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmScrapCollection extends AbstractObjectCollection 
{
    public EqmScrapCollection()
    {
        super(EqmScrapInfo.class);
    }
    public boolean add(EqmScrapInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmScrapCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmScrapInfo item)
    {
        return removeObject(item);
    }
    public EqmScrapInfo get(int index)
    {
        return(EqmScrapInfo)getObject(index);
    }
    public EqmScrapInfo get(Object key)
    {
        return(EqmScrapInfo)getObject(key);
    }
    public void set(int index, EqmScrapInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmScrapInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmScrapInfo item)
    {
        return super.indexOf(item);
    }
}