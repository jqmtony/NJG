package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectIndexDataEntryCollection extends AbstractObjectCollection 
{
    public ProjectIndexDataEntryCollection()
    {
        super(ProjectIndexDataEntryInfo.class);
    }
    public boolean add(ProjectIndexDataEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectIndexDataEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectIndexDataEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectIndexDataEntryInfo get(int index)
    {
        return(ProjectIndexDataEntryInfo)getObject(index);
    }
    public ProjectIndexDataEntryInfo get(Object key)
    {
        return(ProjectIndexDataEntryInfo)getObject(key);
    }
    public void set(int index, ProjectIndexDataEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectIndexDataEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectIndexDataEntryInfo item)
    {
        return super.indexOf(item);
    }
}