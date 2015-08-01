package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialConfirmBillCollection extends AbstractObjectCollection 
{
    public MaterialConfirmBillCollection()
    {
        super(MaterialConfirmBillInfo.class);
    }
    public boolean add(MaterialConfirmBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialConfirmBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialConfirmBillInfo item)
    {
        return removeObject(item);
    }
    public MaterialConfirmBillInfo get(int index)
    {
        return(MaterialConfirmBillInfo)getObject(index);
    }
    public MaterialConfirmBillInfo get(Object key)
    {
        return(MaterialConfirmBillInfo)getObject(key);
    }
    public void set(int index, MaterialConfirmBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialConfirmBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialConfirmBillInfo item)
    {
        return super.indexOf(item);
    }
}