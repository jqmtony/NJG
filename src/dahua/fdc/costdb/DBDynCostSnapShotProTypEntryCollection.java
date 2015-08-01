package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DBDynCostSnapShotProTypEntryCollection extends AbstractObjectCollection 
{
    public DBDynCostSnapShotProTypEntryCollection()
    {
        super(DBDynCostSnapShotProTypEntryInfo.class);
    }
    public boolean add(DBDynCostSnapShotProTypEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DBDynCostSnapShotProTypEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DBDynCostSnapShotProTypEntryInfo item)
    {
        return removeObject(item);
    }
    public DBDynCostSnapShotProTypEntryInfo get(int index)
    {
        return(DBDynCostSnapShotProTypEntryInfo)getObject(index);
    }
    public DBDynCostSnapShotProTypEntryInfo get(Object key)
    {
        return(DBDynCostSnapShotProTypEntryInfo)getObject(key);
    }
    public void set(int index, DBDynCostSnapShotProTypEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DBDynCostSnapShotProTypEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DBDynCostSnapShotProTypEntryInfo item)
    {
        return super.indexOf(item);
    }
}