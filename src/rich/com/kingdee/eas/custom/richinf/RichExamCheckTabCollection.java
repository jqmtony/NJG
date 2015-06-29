package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichExamCheckTabCollection extends AbstractObjectCollection 
{
    public RichExamCheckTabCollection()
    {
        super(RichExamCheckTabInfo.class);
    }
    public boolean add(RichExamCheckTabInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichExamCheckTabCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichExamCheckTabInfo item)
    {
        return removeObject(item);
    }
    public RichExamCheckTabInfo get(int index)
    {
        return(RichExamCheckTabInfo)getObject(index);
    }
    public RichExamCheckTabInfo get(Object key)
    {
        return(RichExamCheckTabInfo)getObject(key);
    }
    public void set(int index, RichExamCheckTabInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichExamCheckTabInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichExamCheckTabInfo item)
    {
        return super.indexOf(item);
    }
}