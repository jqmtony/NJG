package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IFdcColumn extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FdcColumnInfo getFdcColumnInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FdcColumnInfo getFdcColumnInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FdcColumnInfo getFdcColumnInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FdcColumnInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FdcColumnInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FdcColumnInfo model) throws BOSException, EASBizException;
    public void updatePartial(FdcColumnInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FdcColumnInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FdcColumnCollection getFdcColumnCollection() throws BOSException;
    public FdcColumnCollection getFdcColumnCollection(EntityViewInfo view) throws BOSException;
    public FdcColumnCollection getFdcColumnCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}