package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NoticeInfoCollection extends AbstractObjectCollection 
{
    public NoticeInfoCollection()
    {
        super(NoticeInfoInfo.class);
    }
    public boolean add(NoticeInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NoticeInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NoticeInfoInfo item)
    {
        return removeObject(item);
    }
    public NoticeInfoInfo get(int index)
    {
        return(NoticeInfoInfo)getObject(index);
    }
    public NoticeInfoInfo get(Object key)
    {
        return(NoticeInfoInfo)getObject(key);
    }
    public void set(int index, NoticeInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NoticeInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NoticeInfoInfo item)
    {
        return super.indexOf(item);
    }
}