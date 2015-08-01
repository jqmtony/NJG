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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IFdcEntityObject extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FdcEntityObjectInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FdcEntityObjectInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FdcEntityObjectInfo model) throws BOSException, EASBizException;
    public void updatePartial(FdcEntityObjectInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FdcEntityObjectInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection() throws BOSException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection(EntityViewInfo view) throws BOSException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public FdcEntityObjectCollection synchronizeMD(String fullNamePerfix, boolean isPackage) throws BOSException, EASBizException;
    public FdcEntityObjectCollection synchronizeEasMD() throws BOSException, EASBizException;
    public FdcEntityObjectCollection synchronizeBaseMD() throws BOSException, EASBizException;
    public FdcEntityObjectCollection synchronizeFdcMD() throws BOSException, EASBizException;
}