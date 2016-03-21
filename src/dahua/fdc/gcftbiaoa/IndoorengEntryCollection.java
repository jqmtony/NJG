package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndoorengEntryCollection extends AbstractObjectCollection 
{
    public IndoorengEntryCollection()
    {
        super(IndoorengEntryInfo.class);
    }
    public boolean add(IndoorengEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndoorengEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndoorengEntryInfo item)
    {
        return removeObject(item);
    }
    public IndoorengEntryInfo get(int index)
    {
        return(IndoorengEntryInfo)getObject(index);
    }
    public IndoorengEntryInfo get(Object key)
    {
        return(IndoorengEntryInfo)getObject(key);
    }
    public void set(int index, IndoorengEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndoorengEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndoorengEntryInfo item)
    {
        return super.indexOf(item);
    }
}