package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITargetWarning extends IFDCDataBase
{
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public TargetWarningInfo getTargetWarningInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TargetWarningCollection getTargetWarningCollection(EntityViewInfo view) throws BOSException;
    public TargetWarningInfo getTargetWarningInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TargetWarningInfo getTargetWarningInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(TargetWarningInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, TargetWarningInfo model) throws BOSException, EASBizException;
    public TargetWarningCollection getTargetWarningCollection(String oql) throws BOSException;
    public boolean enable(IObjectPK pk) throws BOSException, EASBizException;
    public boolean disable(IObjectPK pk) throws BOSException, EASBizException;
}