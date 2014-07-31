package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingCompareEntryCollection extends AbstractObjectCollection 
{
    public ProgrammingCompareEntryCollection()
    {
        super(ProgrammingCompareEntryInfo.class);
    }
    public boolean add(ProgrammingCompareEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingCompareEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingCompareEntryInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingCompareEntryInfo get(int index)
    {
        return(ProgrammingCompareEntryInfo)getObject(index);
    }
    public ProgrammingCompareEntryInfo get(Object key)
    {
        return(ProgrammingCompareEntryInfo)getObject(key);
    }
    public void set(int index, ProgrammingCompareEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingCompareEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingCompareEntryInfo item)
    {
        return super.indexOf(item);
    }
}