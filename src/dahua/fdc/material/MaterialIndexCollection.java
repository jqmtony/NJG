package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialIndexCollection extends AbstractObjectCollection 
{
    public MaterialIndexCollection()
    {
        super(MaterialIndexInfo.class);
    }
    public boolean add(MaterialIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialIndexInfo item)
    {
        return removeObject(item);
    }
    public MaterialIndexInfo get(int index)
    {
        return(MaterialIndexInfo)getObject(index);
    }
    public MaterialIndexInfo get(Object key)
    {
        return(MaterialIndexInfo)getObject(key);
    }
    public void set(int index, MaterialIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialIndexInfo item)
    {
        return super.indexOf(item);
    }
}