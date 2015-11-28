package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProfessionPointEntryCollection extends AbstractObjectCollection 
{
    public ProfessionPointEntryCollection()
    {
        super(ProfessionPointEntryInfo.class);
    }
    public boolean add(ProfessionPointEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProfessionPointEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProfessionPointEntryInfo item)
    {
        return removeObject(item);
    }
    public ProfessionPointEntryInfo get(int index)
    {
        return(ProfessionPointEntryInfo)getObject(index);
    }
    public ProfessionPointEntryInfo get(Object key)
    {
        return(ProfessionPointEntryInfo)getObject(key);
    }
    public void set(int index, ProfessionPointEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProfessionPointEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProfessionPointEntryInfo item)
    {
        return super.indexOf(item);
    }
}