package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProfessionPointCollection extends AbstractObjectCollection 
{
    public ProfessionPointCollection()
    {
        super(ProfessionPointInfo.class);
    }
    public boolean add(ProfessionPointInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProfessionPointCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProfessionPointInfo item)
    {
        return removeObject(item);
    }
    public ProfessionPointInfo get(int index)
    {
        return(ProfessionPointInfo)getObject(index);
    }
    public ProfessionPointInfo get(Object key)
    {
        return(ProfessionPointInfo)getObject(key);
    }
    public void set(int index, ProfessionPointInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProfessionPointInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProfessionPointInfo item)
    {
        return super.indexOf(item);
    }
}