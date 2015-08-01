package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ITargetType extends ITreeBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public TargetTypeInfo getTargetTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TargetTypeInfo getTargetTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TargetTypeInfo getTargetTypeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(TargetTypeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, TargetTypeInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, TargetTypeInfo model) throws BOSException, EASBizException;
    public void updatePartial(TargetTypeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, TargetTypeInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public TargetTypeCollection getTargetTypeCollection() throws BOSException;
    public TargetTypeCollection getTargetTypeCollection(EntityViewInfo view) throws BOSException;
    public TargetTypeCollection getTargetTypeCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK ttPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ttPK) throws BOSException, EASBizException;
}