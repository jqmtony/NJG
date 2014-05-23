package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReviewerCollection extends AbstractObjectCollection 
{
    public ReviewerCollection()
    {
        super(ReviewerInfo.class);
    }
    public boolean add(ReviewerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReviewerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReviewerInfo item)
    {
        return removeObject(item);
    }
    public ReviewerInfo get(int index)
    {
        return(ReviewerInfo)getObject(index);
    }
    public ReviewerInfo get(Object key)
    {
        return(ReviewerInfo)getObject(key);
    }
    public void set(int index, ReviewerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReviewerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReviewerInfo item)
    {
        return super.indexOf(item);
    }
}