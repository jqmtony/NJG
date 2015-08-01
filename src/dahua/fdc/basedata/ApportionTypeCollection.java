package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ApportionTypeCollection extends AbstractObjectCollection 
{
    public ApportionTypeCollection()
    {
        super(ApportionTypeInfo.class);
    }
    public boolean add(ApportionTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ApportionTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ApportionTypeInfo item)
    {
        return removeObject(item);
    }
    public ApportionTypeInfo get(int index)
    {
        return(ApportionTypeInfo)getObject(index);
    }
    public ApportionTypeInfo get(Object key)
    {
        return(ApportionTypeInfo)getObject(key);
    }
    public void set(int index, ApportionTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ApportionTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ApportionTypeInfo item)
    {
        return super.indexOf(item);
    }
}