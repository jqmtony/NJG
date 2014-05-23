package com.kingdee.eas.port.pm.project;

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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProjectFee extends ITreeBase
{
    public ProjectFeeInfo getProjectFeeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectFeeInfo getProjectFeeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectFeeInfo getProjectFeeInfo(String oql) throws BOSException, EASBizException;
    public ProjectFeeCollection getProjectFeeCollection() throws BOSException;
    public ProjectFeeCollection getProjectFeeCollection(EntityViewInfo view) throws BOSException;
    public ProjectFeeCollection getProjectFeeCollection(String oql) throws BOSException;
}