package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingEntryCollection extends AbstractObjectCollection 
{
    public ProgrammingEntryCollection()
    {
        super(ProgrammingEntryInfo.class);
    }
    public boolean add(ProgrammingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingEntryInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingEntryInfo get(int index)
    {
        return(ProgrammingEntryInfo)getObject(index);
    }
    public ProgrammingEntryInfo get(Object key)
    {
        return(ProgrammingEntryInfo)getObject(key);
    }
    public void set(int index, ProgrammingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingEntryInfo item)
    {
        return super.indexOf(item);
    }
}