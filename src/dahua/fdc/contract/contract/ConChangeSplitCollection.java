package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeSplitCollection extends AbstractObjectCollection 
{
    public ConChangeSplitCollection()
    {
        super(ConChangeSplitInfo.class);
    }
    public boolean add(ConChangeSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeSplitInfo item)
    {
        return removeObject(item);
    }
    public ConChangeSplitInfo get(int index)
    {
        return(ConChangeSplitInfo)getObject(index);
    }
    public ConChangeSplitInfo get(Object key)
    {
        return(ConChangeSplitInfo)getObject(key);
    }
    public void set(int index, ConChangeSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeSplitInfo item)
    {
        return super.indexOf(item);
    }
}