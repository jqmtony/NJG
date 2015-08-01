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

public interface IFdcDataTable extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FdcDataTableInfo getFdcDataTableInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FdcDataTableInfo getFdcDataTableInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FdcDataTableInfo getFdcDataTableInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FdcDataTableInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FdcDataTableInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FdcDataTableInfo model) throws BOSException, EASBizException;
    public void updatePartial(FdcDataTableInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FdcDataTableInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FdcDataTableCollection getFdcDataTableCollection() throws BOSException;
    public FdcDataTableCollection getFdcDataTableCollection(EntityViewInfo view) throws BOSException;
    public FdcDataTableCollection getFdcDataTableCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}