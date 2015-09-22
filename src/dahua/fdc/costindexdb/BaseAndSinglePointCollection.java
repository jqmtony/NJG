package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseAndSinglePointCollection extends AbstractObjectCollection 
{
    public BaseAndSinglePointCollection()
    {
        super(BaseAndSinglePointInfo.class);
    }
    public boolean add(BaseAndSinglePointInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseAndSinglePointCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseAndSinglePointInfo item)
    {
        return removeObject(item);
    }
    public BaseAndSinglePointInfo get(int index)
    {
        return(BaseAndSinglePointInfo)getObject(index);
    }
    public BaseAndSinglePointInfo get(Object key)
    {
        return(BaseAndSinglePointInfo)getObject(key);
    }
    public void set(int index, BaseAndSinglePointInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseAndSinglePointInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseAndSinglePointInfo item)
    {
        return super.indexOf(item);
    }
}