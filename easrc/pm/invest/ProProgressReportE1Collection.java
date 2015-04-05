package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProProgressReportE1Collection extends AbstractObjectCollection 
{
    public ProProgressReportE1Collection()
    {
        super(ProProgressReportE1Info.class);
    }
    public boolean add(ProProgressReportE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProProgressReportE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProProgressReportE1Info item)
    {
        return removeObject(item);
    }
    public ProProgressReportE1Info get(int index)
    {
        return(ProProgressReportE1Info)getObject(index);
    }
    public ProProgressReportE1Info get(Object key)
    {
        return(ProProgressReportE1Info)getObject(key);
    }
    public void set(int index, ProProgressReportE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProProgressReportE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProProgressReportE1Info item)
    {
        return super.indexOf(item);
    }
}