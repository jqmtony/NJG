package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesExamineCollection extends AbstractObjectCollection 
{
    public JudgesExamineCollection()
    {
        super(JudgesExamineInfo.class);
    }
    public boolean add(JudgesExamineInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesExamineCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesExamineInfo item)
    {
        return removeObject(item);
    }
    public JudgesExamineInfo get(int index)
    {
        return(JudgesExamineInfo)getObject(index);
    }
    public JudgesExamineInfo get(Object key)
    {
        return(JudgesExamineInfo)getObject(key);
    }
    public void set(int index, JudgesExamineInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesExamineInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesExamineInfo item)
    {
        return super.indexOf(item);
    }
}