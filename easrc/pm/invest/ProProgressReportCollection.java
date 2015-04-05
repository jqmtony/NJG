package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProProgressReportCollection extends AbstractObjectCollection 
{
    public ProProgressReportCollection()
    {
        super(ProProgressReportInfo.class);
    }
    public boolean add(ProProgressReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProProgressReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProProgressReportInfo item)
    {
        return removeObject(item);
    }
    public ProProgressReportInfo get(int index)
    {
        return(ProProgressReportInfo)getObject(index);
    }
    public ProProgressReportInfo get(Object key)
    {
        return(ProProgressReportInfo)getObject(key);
    }
    public void set(int index, ProProgressReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProProgressReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProProgressReportInfo item)
    {
        return super.indexOf(item);
    }
}