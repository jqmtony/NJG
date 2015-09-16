package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CurProjectSplitProjectCollection extends AbstractObjectCollection 
{
    public CurProjectSplitProjectCollection()
    {
        super(CurProjectSplitProjectInfo.class);
    }
    public boolean add(CurProjectSplitProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CurProjectSplitProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CurProjectSplitProjectInfo item)
    {
        return removeObject(item);
    }
    public CurProjectSplitProjectInfo get(int index)
    {
        return(CurProjectSplitProjectInfo)getObject(index);
    }
    public CurProjectSplitProjectInfo get(Object key)
    {
        return(CurProjectSplitProjectInfo)getObject(key);
    }
    public void set(int index, CurProjectSplitProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CurProjectSplitProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CurProjectSplitProjectInfo item)
    {
        return super.indexOf(item);
    }
}