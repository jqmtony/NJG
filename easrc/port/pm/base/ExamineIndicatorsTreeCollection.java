package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExamineIndicatorsTreeCollection extends AbstractObjectCollection 
{
    public ExamineIndicatorsTreeCollection()
    {
        super(ExamineIndicatorsTreeInfo.class);
    }
    public boolean add(ExamineIndicatorsTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExamineIndicatorsTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExamineIndicatorsTreeInfo item)
    {
        return removeObject(item);
    }
    public ExamineIndicatorsTreeInfo get(int index)
    {
        return(ExamineIndicatorsTreeInfo)getObject(index);
    }
    public ExamineIndicatorsTreeInfo get(Object key)
    {
        return(ExamineIndicatorsTreeInfo)getObject(key);
    }
    public void set(int index, ExamineIndicatorsTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExamineIndicatorsTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExamineIndicatorsTreeInfo item)
    {
        return super.indexOf(item);
    }
}