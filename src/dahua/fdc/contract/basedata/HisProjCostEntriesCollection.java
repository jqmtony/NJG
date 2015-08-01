package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HisProjCostEntriesCollection extends AbstractObjectCollection 
{
    public HisProjCostEntriesCollection()
    {
        super(HisProjCostEntriesInfo.class);
    }
    public boolean add(HisProjCostEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HisProjCostEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HisProjCostEntriesInfo item)
    {
        return removeObject(item);
    }
    public HisProjCostEntriesInfo get(int index)
    {
        return(HisProjCostEntriesInfo)getObject(index);
    }
    public HisProjCostEntriesInfo get(Object key)
    {
        return(HisProjCostEntriesInfo)getObject(key);
    }
    public void set(int index, HisProjCostEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HisProjCostEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HisProjCostEntriesInfo item)
    {
        return super.indexOf(item);
    }
}