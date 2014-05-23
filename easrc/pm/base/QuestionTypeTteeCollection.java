package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionTypeTteeCollection extends AbstractObjectCollection 
{
    public QuestionTypeTteeCollection()
    {
        super(QuestionTypeTteeInfo.class);
    }
    public boolean add(QuestionTypeTteeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionTypeTteeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionTypeTteeInfo item)
    {
        return removeObject(item);
    }
    public QuestionTypeTteeInfo get(int index)
    {
        return(QuestionTypeTteeInfo)getObject(index);
    }
    public QuestionTypeTteeInfo get(Object key)
    {
        return(QuestionTypeTteeInfo)getObject(key);
    }
    public void set(int index, QuestionTypeTteeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionTypeTteeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionTypeTteeInfo item)
    {
        return super.indexOf(item);
    }
}