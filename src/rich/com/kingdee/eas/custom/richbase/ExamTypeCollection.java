package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExamTypeCollection extends AbstractObjectCollection 
{
    public ExamTypeCollection()
    {
        super(ExamTypeInfo.class);
    }
    public boolean add(ExamTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExamTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExamTypeInfo item)
    {
        return removeObject(item);
    }
    public ExamTypeInfo get(int index)
    {
        return(ExamTypeInfo)getObject(index);
    }
    public ExamTypeInfo get(Object key)
    {
        return(ExamTypeInfo)getObject(key);
    }
    public void set(int index, ExamTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExamTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExamTypeInfo item)
    {
        return super.indexOf(item);
    }
}