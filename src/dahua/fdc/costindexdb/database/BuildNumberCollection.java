package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildNumberCollection extends AbstractObjectCollection 
{
    public BuildNumberCollection()
    {
        super(BuildNumberInfo.class);
    }
    public boolean add(BuildNumberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildNumberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildNumberInfo item)
    {
        return removeObject(item);
    }
    public BuildNumberInfo get(int index)
    {
        return(BuildNumberInfo)getObject(index);
    }
    public BuildNumberInfo get(Object key)
    {
        return(BuildNumberInfo)getObject(key);
    }
    public void set(int index, BuildNumberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildNumberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildNumberInfo item)
    {
        return super.indexOf(item);
    }
}