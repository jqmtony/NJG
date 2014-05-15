package com.kingdee.eas.port.pm.base;

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

public interface IReviewer extends IDataBase
{
    public ReviewerInfo getReviewerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ReviewerInfo getReviewerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ReviewerInfo getReviewerInfo(String oql) throws BOSException, EASBizException;
    public ReviewerCollection getReviewerCollection() throws BOSException;
    public ReviewerCollection getReviewerCollection(EntityViewInfo view) throws BOSException;
    public ReviewerCollection getReviewerCollection(String oql) throws BOSException;
}