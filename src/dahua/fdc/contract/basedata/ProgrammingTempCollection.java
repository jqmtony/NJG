package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingTempCollection extends AbstractObjectCollection 
{
    public ProgrammingTempCollection()
    {
        super(ProgrammingTempInfo.class);
    }
    public boolean add(ProgrammingTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingTempInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingTempInfo get(int index)
    {
        return(ProgrammingTempInfo)getObject(index);
    }
    public ProgrammingTempInfo get(Object key)
    {
        return(ProgrammingTempInfo)getObject(key);
    }
    public void set(int index, ProgrammingTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingTempInfo item)
    {
        return super.indexOf(item);
    }
}