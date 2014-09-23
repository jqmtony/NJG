package com.kingdee.eas.bpm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BPMLogCollection extends AbstractObjectCollection 
{
    public BPMLogCollection()
    {
        super(BPMLogInfo.class);
    }
    public boolean add(BPMLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BPMLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BPMLogInfo item)
    {
        return removeObject(item);
    }
    public BPMLogInfo get(int index)
    {
        return(BPMLogInfo)getObject(index);
    }
    public BPMLogInfo get(Object key)
    {
        return(BPMLogInfo)getObject(key);
    }
    public void set(int index, BPMLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BPMLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BPMLogInfo item)
    {
        return super.indexOf(item);
    }
}