package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExamineIndicatorsCollection extends AbstractObjectCollection 
{
    public ExamineIndicatorsCollection()
    {
        super(ExamineIndicatorsInfo.class);
    }
    public boolean add(ExamineIndicatorsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExamineIndicatorsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExamineIndicatorsInfo item)
    {
        return removeObject(item);
    }
    public ExamineIndicatorsInfo get(int index)
    {
        return(ExamineIndicatorsInfo)getObject(index);
    }
    public ExamineIndicatorsInfo get(Object key)
    {
        return(ExamineIndicatorsInfo)getObject(key);
    }
    public void set(int index, ExamineIndicatorsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExamineIndicatorsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExamineIndicatorsInfo item)
    {
        return super.indexOf(item);
    }
}