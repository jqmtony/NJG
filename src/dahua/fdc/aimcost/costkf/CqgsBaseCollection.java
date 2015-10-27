package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CqgsBaseCollection extends AbstractObjectCollection 
{
    public CqgsBaseCollection()
    {
        super(CqgsBaseInfo.class);
    }
    public boolean add(CqgsBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CqgsBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CqgsBaseInfo item)
    {
        return removeObject(item);
    }
    public CqgsBaseInfo get(int index)
    {
        return(CqgsBaseInfo)getObject(index);
    }
    public CqgsBaseInfo get(Object key)
    {
        return(CqgsBaseInfo)getObject(key);
    }
    public void set(int index, CqgsBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CqgsBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CqgsBaseInfo item)
    {
        return super.indexOf(item);
    }
}