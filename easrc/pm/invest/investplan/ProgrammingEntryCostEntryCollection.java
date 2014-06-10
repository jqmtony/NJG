package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingEntryCostEntryCollection extends AbstractObjectCollection 
{
    public ProgrammingEntryCostEntryCollection()
    {
        super(ProgrammingEntryCostEntryInfo.class);
    }
    public boolean add(ProgrammingEntryCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingEntryCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingEntryCostEntryInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingEntryCostEntryInfo get(int index)
    {
        return(ProgrammingEntryCostEntryInfo)getObject(index);
    }
    public ProgrammingEntryCostEntryInfo get(Object key)
    {
        return(ProgrammingEntryCostEntryInfo)getObject(key);
    }
    public void set(int index, ProgrammingEntryCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingEntryCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingEntryCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}