package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseAndSinglePointEntryCollection extends AbstractObjectCollection 
{
    public BaseAndSinglePointEntryCollection()
    {
        super(BaseAndSinglePointEntryInfo.class);
    }
    public boolean add(BaseAndSinglePointEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseAndSinglePointEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseAndSinglePointEntryInfo item)
    {
        return removeObject(item);
    }
    public BaseAndSinglePointEntryInfo get(int index)
    {
        return(BaseAndSinglePointEntryInfo)getObject(index);
    }
    public BaseAndSinglePointEntryInfo get(Object key)
    {
        return(BaseAndSinglePointEntryInfo)getObject(key);
    }
    public void set(int index, BaseAndSinglePointEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseAndSinglePointEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseAndSinglePointEntryInfo item)
    {
        return super.indexOf(item);
    }
}