package com.kingdee.eas.xr;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XRBizBillCollection extends AbstractObjectCollection 
{
    public XRBizBillCollection()
    {
        super(XRBizBillInfo.class);
    }
    public boolean add(XRBizBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XRBizBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XRBizBillInfo item)
    {
        return removeObject(item);
    }
    public XRBizBillInfo get(int index)
    {
        return(XRBizBillInfo)getObject(index);
    }
    public XRBizBillInfo get(Object key)
    {
        return(XRBizBillInfo)getObject(key);
    }
    public void set(int index, XRBizBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XRBizBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XRBizBillInfo item)
    {
        return super.indexOf(item);
    }
}