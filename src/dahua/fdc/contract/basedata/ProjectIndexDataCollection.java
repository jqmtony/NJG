package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectIndexDataCollection extends AbstractObjectCollection 
{
    public ProjectIndexDataCollection()
    {
        super(ProjectIndexDataInfo.class);
    }
    public boolean add(ProjectIndexDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectIndexDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectIndexDataInfo item)
    {
        return removeObject(item);
    }
    public ProjectIndexDataInfo get(int index)
    {
        return(ProjectIndexDataInfo)getObject(index);
    }
    public ProjectIndexDataInfo get(Object key)
    {
        return(ProjectIndexDataInfo)getObject(key);
    }
    public void set(int index, ProjectIndexDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectIndexDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectIndexDataInfo item)
    {
        return super.indexOf(item);
    }
}