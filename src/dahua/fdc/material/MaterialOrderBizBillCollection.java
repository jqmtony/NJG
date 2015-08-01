package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialOrderBizBillCollection extends AbstractObjectCollection 
{
    public MaterialOrderBizBillCollection()
    {
        super(MaterialOrderBizBillInfo.class);
    }
    public boolean add(MaterialOrderBizBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialOrderBizBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialOrderBizBillInfo item)
    {
        return removeObject(item);
    }
    public MaterialOrderBizBillInfo get(int index)
    {
        return(MaterialOrderBizBillInfo)getObject(index);
    }
    public MaterialOrderBizBillInfo get(Object key)
    {
        return(MaterialOrderBizBillInfo)getObject(key);
    }
    public void set(int index, MaterialOrderBizBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialOrderBizBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialOrderBizBillInfo item)
    {
        return super.indexOf(item);
    }
}