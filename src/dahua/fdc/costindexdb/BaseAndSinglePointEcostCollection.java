package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseAndSinglePointEcostCollection extends AbstractObjectCollection 
{
    public BaseAndSinglePointEcostCollection()
    {
        super(BaseAndSinglePointEcostInfo.class);
    }
    public boolean add(BaseAndSinglePointEcostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseAndSinglePointEcostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseAndSinglePointEcostInfo item)
    {
        return removeObject(item);
    }
    public BaseAndSinglePointEcostInfo get(int index)
    {
        return(BaseAndSinglePointEcostInfo)getObject(index);
    }
    public BaseAndSinglePointEcostInfo get(Object key)
    {
        return(BaseAndSinglePointEcostInfo)getObject(key);
    }
    public void set(int index, BaseAndSinglePointEcostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseAndSinglePointEcostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseAndSinglePointEcostInfo item)
    {
        return super.indexOf(item);
    }
}