package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialOrderBizBillEntryCollection extends AbstractObjectCollection 
{
    public MaterialOrderBizBillEntryCollection()
    {
        super(MaterialOrderBizBillEntryInfo.class);
    }
    public boolean add(MaterialOrderBizBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialOrderBizBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialOrderBizBillEntryInfo item)
    {
        return removeObject(item);
    }
    public MaterialOrderBizBillEntryInfo get(int index)
    {
        return(MaterialOrderBizBillEntryInfo)getObject(index);
    }
    public MaterialOrderBizBillEntryInfo get(Object key)
    {
        return(MaterialOrderBizBillEntryInfo)getObject(key);
    }
    public void set(int index, MaterialOrderBizBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialOrderBizBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialOrderBizBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}