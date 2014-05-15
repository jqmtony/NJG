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

public interface IJudges extends IDataBase
{
    public JudgesInfo getJudgesInfo(IObjectPK pk) throws BOSException, EASBizException;
    public JudgesInfo getJudgesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public JudgesInfo getJudgesInfo(String oql) throws BOSException, EASBizException;
    public JudgesCollection getJudgesCollection() throws BOSException;
    public JudgesCollection getJudgesCollection(EntityViewInfo view) throws BOSException;
    public JudgesCollection getJudgesCollection(String oql) throws BOSException;
}