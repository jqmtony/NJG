package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialEnterPlanBillCollection extends AbstractObjectCollection 
{
    public MaterialEnterPlanBillCollection()
    {
        super(MaterialEnterPlanBillInfo.class);
    }
    public boolean add(MaterialEnterPlanBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialEnterPlanBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialEnterPlanBillInfo item)
    {
        return removeObject(item);
    }
    public MaterialEnterPlanBillInfo get(int index)
    {
        return(MaterialEnterPlanBillInfo)getObject(index);
    }
    public MaterialEnterPlanBillInfo get(Object key)
    {
        return(MaterialEnterPlanBillInfo)getObject(key);
    }
    public void set(int index, MaterialEnterPlanBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialEnterPlanBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialEnterPlanBillInfo item)
    {
        return super.indexOf(item);
    }
}