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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ICostTarget extends IFDCDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public CostTargetInfo getCostTargetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CostTargetInfo getCostTargetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CostTargetInfo getCostTargetInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(CostTargetInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, CostTargetInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, CostTargetInfo model) throws BOSException, EASBizException;
    public void updatePartial(CostTargetInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, CostTargetInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public CostTargetCollection getCostTargetCollection() throws BOSException;
    public CostTargetCollection getCostTargetCollection(EntityViewInfo view) throws BOSException;
    public CostTargetCollection getCostTargetCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException;
}