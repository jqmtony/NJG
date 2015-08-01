package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetValueEntryCollection extends AbstractObjectCollection 
{
    public ProjectTargetValueEntryCollection()
    {
        super(ProjectTargetValueEntryInfo.class);
    }
    public boolean add(ProjectTargetValueEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetValueEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetValueEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetValueEntryInfo get(int index)
    {
        return(ProjectTargetValueEntryInfo)getObject(index);
    }
    public ProjectTargetValueEntryInfo get(Object key)
    {
        return(ProjectTargetValueEntryInfo)getObject(key);
    }
    public void set(int index, ProjectTargetValueEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetValueEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetValueEntryInfo item)
    {
        return super.indexOf(item);
    }
}