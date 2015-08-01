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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.HashSet;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface ICostAccountWithAccount extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public CostAccountWithAccountInfo getCostAccountWithAccountInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CostAccountWithAccountInfo getCostAccountWithAccountInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CostAccountWithAccountInfo getCostAccountWithAccountInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(CostAccountWithAccountInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, CostAccountWithAccountInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, CostAccountWithAccountInfo model) throws BOSException, EASBizException;
    public void updatePartial(CostAccountWithAccountInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, CostAccountWithAccountInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public CostAccountWithAccountCollection getCostAccountWithAccountCollection() throws BOSException;
    public CostAccountWithAccountCollection getCostAccountWithAccountCollection(EntityViewInfo view) throws BOSException;
    public CostAccountWithAccountCollection getCostAccountWithAccountCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void submitAll(CostAccountWithAccountCollection objCol) throws BOSException, EASBizException;
    public void importGroupData() throws BOSException, EASBizException;
    public void importGroupData(HashSet nodeIdSet) throws BOSException, EASBizException;
    public void submitAll(CostAccountWithAccountCollection objCol, String nodeID) throws BOSException, EASBizException;
}