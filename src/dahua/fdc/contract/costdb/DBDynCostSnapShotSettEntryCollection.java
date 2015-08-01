package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DBDynCostSnapShotSettEntryCollection extends AbstractObjectCollection 
{
    public DBDynCostSnapShotSettEntryCollection()
    {
        super(DBDynCostSnapShotSettEntryInfo.class);
    }
    public boolean add(DBDynCostSnapShotSettEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DBDynCostSnapShotSettEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DBDynCostSnapShotSettEntryInfo item)
    {
        return removeObject(item);
    }
    public DBDynCostSnapShotSettEntryInfo get(int index)
    {
        return(DBDynCostSnapShotSettEntryInfo)getObject(index);
    }
    public DBDynCostSnapShotSettEntryInfo get(Object key)
    {
        return(DBDynCostSnapShotSettEntryInfo)getObject(key);
    }
    public void set(int index, DBDynCostSnapShotSettEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DBDynCostSnapShotSettEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DBDynCostSnapShotSettEntryInfo item)
    {
        return super.indexOf(item);
    }
}