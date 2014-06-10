package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingEntryEconomyEntryCollection extends AbstractObjectCollection 
{
    public ProgrammingEntryEconomyEntryCollection()
    {
        super(ProgrammingEntryEconomyEntryInfo.class);
    }
    public boolean add(ProgrammingEntryEconomyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingEntryEconomyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingEntryEconomyEntryInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingEntryEconomyEntryInfo get(int index)
    {
        return(ProgrammingEntryEconomyEntryInfo)getObject(index);
    }
    public ProgrammingEntryEconomyEntryInfo get(Object key)
    {
        return(ProgrammingEntryEconomyEntryInfo)getObject(key);
    }
    public void set(int index, ProgrammingEntryEconomyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingEntryEconomyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingEntryEconomyEntryInfo item)
    {
        return super.indexOf(item);
    }
}