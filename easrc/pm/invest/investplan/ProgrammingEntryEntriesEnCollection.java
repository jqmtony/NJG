package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingEntryEntriesEnCollection extends AbstractObjectCollection 
{
    public ProgrammingEntryEntriesEnCollection()
    {
        super(ProgrammingEntryEntriesEnInfo.class);
    }
    public boolean add(ProgrammingEntryEntriesEnInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingEntryEntriesEnCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingEntryEntriesEnInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingEntryEntriesEnInfo get(int index)
    {
        return(ProgrammingEntryEntriesEnInfo)getObject(index);
    }
    public ProgrammingEntryEntriesEnInfo get(Object key)
    {
        return(ProgrammingEntryEntriesEnInfo)getObject(key);
    }
    public void set(int index, ProgrammingEntryEntriesEnInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingEntryEntriesEnInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingEntryEntriesEnInfo item)
    {
        return super.indexOf(item);
    }
}