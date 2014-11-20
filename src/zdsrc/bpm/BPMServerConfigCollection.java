package com.kingdee.eas.bpm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BPMServerConfigCollection extends AbstractObjectCollection 
{
    public BPMServerConfigCollection()
    {
        super(BPMServerConfigInfo.class);
    }
    public boolean add(BPMServerConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BPMServerConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BPMServerConfigInfo item)
    {
        return removeObject(item);
    }
    public BPMServerConfigInfo get(int index)
    {
        return(BPMServerConfigInfo)getObject(index);
    }
    public BPMServerConfigInfo get(Object key)
    {
        return(BPMServerConfigInfo)getObject(key);
    }
    public void set(int index, BPMServerConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BPMServerConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BPMServerConfigInfo item)
    {
        return super.indexOf(item);
    }
}