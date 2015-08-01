package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartAMaterialCollection extends AbstractObjectCollection 
{
    public PartAMaterialCollection()
    {
        super(PartAMaterialInfo.class);
    }
    public boolean add(PartAMaterialInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartAMaterialCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartAMaterialInfo item)
    {
        return removeObject(item);
    }
    public PartAMaterialInfo get(int index)
    {
        return(PartAMaterialInfo)getObject(index);
    }
    public PartAMaterialInfo get(Object key)
    {
        return(PartAMaterialInfo)getObject(key);
    }
    public void set(int index, PartAMaterialInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartAMaterialInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartAMaterialInfo item)
    {
        return super.indexOf(item);
    }
}