package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeVisaAppSendUnitCollection extends AbstractObjectCollection 
{
    public ChangeVisaAppSendUnitCollection()
    {
        super(ChangeVisaAppSendUnitInfo.class);
    }
    public boolean add(ChangeVisaAppSendUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeVisaAppSendUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeVisaAppSendUnitInfo item)
    {
        return removeObject(item);
    }
    public ChangeVisaAppSendUnitInfo get(int index)
    {
        return(ChangeVisaAppSendUnitInfo)getObject(index);
    }
    public ChangeVisaAppSendUnitInfo get(Object key)
    {
        return(ChangeVisaAppSendUnitInfo)getObject(key);
    }
    public void set(int index, ChangeVisaAppSendUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeVisaAppSendUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeVisaAppSendUnitInfo item)
    {
        return super.indexOf(item);
    }
}