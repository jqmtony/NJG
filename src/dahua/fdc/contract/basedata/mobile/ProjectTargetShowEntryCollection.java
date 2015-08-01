package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetShowEntryCollection extends AbstractObjectCollection 
{
    public ProjectTargetShowEntryCollection()
    {
        super(ProjectTargetShowEntryInfo.class);
    }
    public boolean add(ProjectTargetShowEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetShowEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetShowEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetShowEntryInfo get(int index)
    {
        return(ProjectTargetShowEntryInfo)getObject(index);
    }
    public ProjectTargetShowEntryInfo get(Object key)
    {
        return(ProjectTargetShowEntryInfo)getObject(key);
    }
    public void set(int index, ProjectTargetShowEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetShowEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetShowEntryInfo item)
    {
        return super.indexOf(item);
    }
}