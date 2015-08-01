package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LandInfomationCollection extends AbstractObjectCollection 
{
    public LandInfomationCollection()
    {
        super(LandInfomationInfo.class);
    }
    public boolean add(LandInfomationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LandInfomationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LandInfomationInfo item)
    {
        return removeObject(item);
    }
    public LandInfomationInfo get(int index)
    {
        return(LandInfomationInfo)getObject(index);
    }
    public LandInfomationInfo get(Object key)
    {
        return(LandInfomationInfo)getObject(key);
    }
    public void set(int index, LandInfomationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LandInfomationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LandInfomationInfo item)
    {
        return super.indexOf(item);
    }
}