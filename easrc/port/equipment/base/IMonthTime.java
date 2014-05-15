package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.xr.xrbase.IXRDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IMonthTime extends IXRDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public MonthTimeInfo getMonthTimeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MonthTimeInfo getMonthTimeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MonthTimeInfo getMonthTimeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(MonthTimeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, MonthTimeInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, MonthTimeInfo model) throws BOSException, EASBizException;
    public void updatePartial(MonthTimeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, MonthTimeInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public MonthTimeCollection getMonthTimeCollection() throws BOSException;
    public MonthTimeCollection getMonthTimeCollection(EntityViewInfo view) throws BOSException;
    public MonthTimeCollection getMonthTimeCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}