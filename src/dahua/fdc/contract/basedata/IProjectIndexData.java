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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public interface IProjectIndexData extends IFDCBill
{
    public ProjectIndexDataInfo getProjectIndexDataInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectIndexDataInfo getProjectIndexDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectIndexDataInfo getProjectIndexDataInfo(String oql) throws BOSException, EASBizException;
    public ProjectIndexDataCollection getProjectIndexDataCollection() throws BOSException;
    public ProjectIndexDataCollection getProjectIndexDataCollection(EntityViewInfo view) throws BOSException;
    public ProjectIndexDataCollection getProjectIndexDataCollection(String oql) throws BOSException;
    public IRowSet sum(List projIdList, String productTypeId) throws BOSException, EASBizException;
    public Map submitAreaIndex(CoreBaseCollection colls) throws BOSException, EASBizException;
    public Map idxRefresh(Map param) throws BOSException, EASBizException;
}