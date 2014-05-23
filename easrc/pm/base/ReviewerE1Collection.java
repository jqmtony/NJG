package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReviewerE1Collection extends AbstractObjectCollection 
{
    public ReviewerE1Collection()
    {
        super(ReviewerE1Info.class);
    }
    public boolean add(ReviewerE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReviewerE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReviewerE1Info item)
    {
        return removeObject(item);
    }
    public ReviewerE1Info get(int index)
    {
        return(ReviewerE1Info)getObject(index);
    }
    public ReviewerE1Info get(Object key)
    {
        return(ReviewerE1Info)getObject(key);
    }
    public void set(int index, ReviewerE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ReviewerE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReviewerE1Info item)
    {
        return super.indexOf(item);
    }
}