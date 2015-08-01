package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialConfirmBillEntryCollection extends AbstractObjectCollection 
{
    public MaterialConfirmBillEntryCollection()
    {
        super(MaterialConfirmBillEntryInfo.class);
    }
    public boolean add(MaterialConfirmBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialConfirmBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialConfirmBillEntryInfo item)
    {
        return removeObject(item);
    }
    public MaterialConfirmBillEntryInfo get(int index)
    {
        return(MaterialConfirmBillEntryInfo)getObject(index);
    }
    public MaterialConfirmBillEntryInfo get(Object key)
    {
        return(MaterialConfirmBillEntryInfo)getObject(key);
    }
    public void set(int index, MaterialConfirmBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialConfirmBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialConfirmBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}