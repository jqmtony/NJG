package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialOrderBizBillAssEntryCollection extends AbstractObjectCollection 
{
    public MaterialOrderBizBillAssEntryCollection()
    {
        super(MaterialOrderBizBillAssEntryInfo.class);
    }
    public boolean add(MaterialOrderBizBillAssEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialOrderBizBillAssEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialOrderBizBillAssEntryInfo item)
    {
        return removeObject(item);
    }
    public MaterialOrderBizBillAssEntryInfo get(int index)
    {
        return(MaterialOrderBizBillAssEntryInfo)getObject(index);
    }
    public MaterialOrderBizBillAssEntryInfo get(Object key)
    {
        return(MaterialOrderBizBillAssEntryInfo)getObject(key);
    }
    public void set(int index, MaterialOrderBizBillAssEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialOrderBizBillAssEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialOrderBizBillAssEntryInfo item)
    {
        return super.indexOf(item);
    }
}