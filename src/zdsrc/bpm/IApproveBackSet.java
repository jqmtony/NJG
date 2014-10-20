package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IApproveBackSet extends IDataBase
{
    public ApproveBackSetInfo getApproveBackSetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ApproveBackSetInfo getApproveBackSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ApproveBackSetInfo getApproveBackSetInfo(String oql) throws BOSException, EASBizException;
    public ApproveBackSetCollection getApproveBackSetCollection() throws BOSException;
    public ApproveBackSetCollection getApproveBackSetCollection(EntityViewInfo view) throws BOSException;
    public ApproveBackSetCollection getApproveBackSetCollection(String oql) throws BOSException;
}