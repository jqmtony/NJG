package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichExamTempTabCollection extends AbstractObjectCollection 
{
    public RichExamTempTabCollection()
    {
        super(RichExamTempTabInfo.class);
    }
    public boolean add(RichExamTempTabInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichExamTempTabCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichExamTempTabInfo item)
    {
        return removeObject(item);
    }
    public RichExamTempTabInfo get(int index)
    {
        return(RichExamTempTabInfo)getObject(index);
    }
    public RichExamTempTabInfo get(Object key)
    {
        return(RichExamTempTabInfo)getObject(key);
    }
    public void set(int index, RichExamTempTabInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichExamTempTabInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichExamTempTabInfo item)
    {
        return super.indexOf(item);
    }
}