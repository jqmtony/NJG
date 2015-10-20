package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeSplitTempCollection extends AbstractObjectCollection 
{
    public ConChangeSplitTempCollection()
    {
        super(ConChangeSplitTempInfo.class);
    }
    public boolean add(ConChangeSplitTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeSplitTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeSplitTempInfo item)
    {
        return removeObject(item);
    }
    public ConChangeSplitTempInfo get(int index)
    {
        return(ConChangeSplitTempInfo)getObject(index);
    }
    public ConChangeSplitTempInfo get(Object key)
    {
        return(ConChangeSplitTempInfo)getObject(key);
    }
    public void set(int index, ConChangeSplitTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeSplitTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeSplitTempInfo item)
    {
        return super.indexOf(item);
    }
}