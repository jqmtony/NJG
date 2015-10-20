package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingContractFxbdEntryCollection extends AbstractObjectCollection 
{
    public ProgrammingContractFxbdEntryCollection()
    {
        super(ProgrammingContractFxbdEntryInfo.class);
    }
    public boolean add(ProgrammingContractFxbdEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingContractFxbdEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingContractFxbdEntryInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingContractFxbdEntryInfo get(int index)
    {
        return(ProgrammingContractFxbdEntryInfo)getObject(index);
    }
    public ProgrammingContractFxbdEntryInfo get(Object key)
    {
        return(ProgrammingContractFxbdEntryInfo)getObject(key);
    }
    public void set(int index, ProgrammingContractFxbdEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingContractFxbdEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingContractFxbdEntryInfo item)
    {
        return super.indexOf(item);
    }
}