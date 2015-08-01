package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CurProjCostEntriesCollection extends AbstractObjectCollection 
{
    public CurProjCostEntriesCollection()
    {
        super(CurProjCostEntriesInfo.class);
    }
    public boolean add(CurProjCostEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CurProjCostEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CurProjCostEntriesInfo item)
    {
        return removeObject(item);
    }
    public CurProjCostEntriesInfo get(int index)
    {
        return(CurProjCostEntriesInfo)getObject(index);
    }
    public CurProjCostEntriesInfo get(Object key)
    {
        return(CurProjCostEntriesInfo)getObject(key);
    }
    public void set(int index, CurProjCostEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CurProjCostEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CurProjCostEntriesInfo item)
    {
        return super.indexOf(item);
    }
}