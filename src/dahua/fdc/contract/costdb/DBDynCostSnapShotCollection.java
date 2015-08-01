package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DBDynCostSnapShotCollection extends AbstractObjectCollection 
{
    public DBDynCostSnapShotCollection()
    {
        super(DBDynCostSnapShotInfo.class);
    }
    public boolean add(DBDynCostSnapShotInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DBDynCostSnapShotCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DBDynCostSnapShotInfo item)
    {
        return removeObject(item);
    }
    public DBDynCostSnapShotInfo get(int index)
    {
        return(DBDynCostSnapShotInfo)getObject(index);
    }
    public DBDynCostSnapShotInfo get(Object key)
    {
        return(DBDynCostSnapShotInfo)getObject(key);
    }
    public void set(int index, DBDynCostSnapShotInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DBDynCostSnapShotInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DBDynCostSnapShotInfo item)
    {
        return super.indexOf(item);
    }
}