package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ApportionInfoBaseCollection extends AbstractObjectCollection 
{
    public ApportionInfoBaseCollection()
    {
        super(ApportionInfoBaseInfo.class);
    }
    public boolean add(ApportionInfoBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ApportionInfoBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ApportionInfoBaseInfo item)
    {
        return removeObject(item);
    }
    public ApportionInfoBaseInfo get(int index)
    {
        return(ApportionInfoBaseInfo)getObject(index);
    }
    public ApportionInfoBaseInfo get(Object key)
    {
        return(ApportionInfoBaseInfo)getObject(key);
    }
    public void set(int index, ApportionInfoBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ApportionInfoBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ApportionInfoBaseInfo item)
    {
        return super.indexOf(item);
    }
}