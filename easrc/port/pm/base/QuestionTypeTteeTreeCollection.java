package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionTypeTteeTreeCollection extends AbstractObjectCollection 
{
    public QuestionTypeTteeTreeCollection()
    {
        super(QuestionTypeTteeTreeInfo.class);
    }
    public boolean add(QuestionTypeTteeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionTypeTteeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionTypeTteeTreeInfo item)
    {
        return removeObject(item);
    }
    public QuestionTypeTteeTreeInfo get(int index)
    {
        return(QuestionTypeTteeTreeInfo)getObject(index);
    }
    public QuestionTypeTteeTreeInfo get(Object key)
    {
        return(QuestionTypeTteeTreeInfo)getObject(key);
    }
    public void set(int index, QuestionTypeTteeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionTypeTteeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionTypeTteeTreeInfo item)
    {
        return super.indexOf(item);
    }
}