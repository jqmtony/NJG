package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JobTypeCollection extends AbstractObjectCollection 
{
    public JobTypeCollection()
    {
        super(JobTypeInfo.class);
    }
    public boolean add(JobTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JobTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JobTypeInfo item)
    {
        return removeObject(item);
    }
    public JobTypeInfo get(int index)
    {
        return(JobTypeInfo)getObject(index);
    }
    public JobTypeInfo get(Object key)
    {
        return(JobTypeInfo)getObject(key);
    }
    public void set(int index, JobTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JobTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JobTypeInfo item)
    {
        return super.indexOf(item);
    }
}