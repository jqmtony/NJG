package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetValueBillCollection extends AbstractObjectCollection 
{
    public ProjectTargetValueBillCollection()
    {
        super(ProjectTargetValueBillInfo.class);
    }
    public boolean add(ProjectTargetValueBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetValueBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetValueBillInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetValueBillInfo get(int index)
    {
        return(ProjectTargetValueBillInfo)getObject(index);
    }
    public ProjectTargetValueBillInfo get(Object key)
    {
        return(ProjectTargetValueBillInfo)getObject(key);
    }
    public void set(int index, ProjectTargetValueBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetValueBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetValueBillInfo item)
    {
        return super.indexOf(item);
    }
}