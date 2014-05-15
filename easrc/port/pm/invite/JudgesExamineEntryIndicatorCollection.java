package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgesExamineEntryIndicatorCollection extends AbstractObjectCollection 
{
    public JudgesExamineEntryIndicatorCollection()
    {
        super(JudgesExamineEntryIndicatorInfo.class);
    }
    public boolean add(JudgesExamineEntryIndicatorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgesExamineEntryIndicatorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgesExamineEntryIndicatorInfo item)
    {
        return removeObject(item);
    }
    public JudgesExamineEntryIndicatorInfo get(int index)
    {
        return(JudgesExamineEntryIndicatorInfo)getObject(index);
    }
    public JudgesExamineEntryIndicatorInfo get(Object key)
    {
        return(JudgesExamineEntryIndicatorInfo)getObject(key);
    }
    public void set(int index, JudgesExamineEntryIndicatorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgesExamineEntryIndicatorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgesExamineEntryIndicatorInfo item)
    {
        return super.indexOf(item);
    }
}