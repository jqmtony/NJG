package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GcftbEntryDetailCollection extends AbstractObjectCollection 
{
    public GcftbEntryDetailCollection()
    {
        super(GcftbEntryDetailInfo.class);
    }
    public boolean add(GcftbEntryDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GcftbEntryDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GcftbEntryDetailInfo item)
    {
        return removeObject(item);
    }
    public GcftbEntryDetailInfo get(int index)
    {
        return(GcftbEntryDetailInfo)getObject(index);
    }
    public GcftbEntryDetailInfo get(Object key)
    {
        return(GcftbEntryDetailInfo)getObject(key);
    }
    public void set(int index, GcftbEntryDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GcftbEntryDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GcftbEntryDetailInfo item)
    {
        return super.indexOf(item);
    }
}