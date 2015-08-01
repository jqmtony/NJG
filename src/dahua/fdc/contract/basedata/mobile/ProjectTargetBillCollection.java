package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTargetBillCollection extends AbstractObjectCollection 
{
    public ProjectTargetBillCollection()
    {
        super(ProjectTargetBillInfo.class);
    }
    public boolean add(ProjectTargetBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTargetBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTargetBillInfo item)
    {
        return removeObject(item);
    }
    public ProjectTargetBillInfo get(int index)
    {
        return(ProjectTargetBillInfo)getObject(index);
    }
    public ProjectTargetBillInfo get(Object key)
    {
        return(ProjectTargetBillInfo)getObject(key);
    }
    public void set(int index, ProjectTargetBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTargetBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTargetBillInfo item)
    {
        return super.indexOf(item);
    }
}