package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class REAutoRememberCollection extends AbstractObjectCollection 
{
    public REAutoRememberCollection()
    {
        super(REAutoRememberInfo.class);
    }
    public boolean add(REAutoRememberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(REAutoRememberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(REAutoRememberInfo item)
    {
        return removeObject(item);
    }
    public REAutoRememberInfo get(int index)
    {
        return(REAutoRememberInfo)getObject(index);
    }
    public REAutoRememberInfo get(Object key)
    {
        return(REAutoRememberInfo)getObject(key);
    }
    public void set(int index, REAutoRememberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(REAutoRememberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(REAutoRememberInfo item)
    {
        return super.indexOf(item);
    }
}