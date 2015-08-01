package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectCollection extends AbstractObjectCollection 
{
    public ProjectCollection()
    {
        super(ProjectInfo.class);
    }
    public boolean add(ProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectInfo item)
    {
        return removeObject(item);
    }
    public ProjectInfo get(int index)
    {
        return(ProjectInfo)getObject(index);
    }
    public ProjectInfo get(Object key)
    {
        return(ProjectInfo)getObject(key);
    }
    public void set(int index, ProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectInfo item)
    {
        return super.indexOf(item);
    }
}