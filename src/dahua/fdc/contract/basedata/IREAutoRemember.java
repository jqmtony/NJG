package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface IREAutoRemember extends ICoreBase
{
    public REAutoRememberInfo getREAutoRememberInfo(IObjectPK pk) throws BOSException, EASBizException;
    public REAutoRememberInfo getREAutoRememberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public REAutoRememberInfo getREAutoRememberInfo(String oql) throws BOSException, EASBizException;
    public REAutoRememberCollection getREAutoRememberCollection() throws BOSException;
    public REAutoRememberCollection getREAutoRememberCollection(EntityViewInfo view) throws BOSException;
    public REAutoRememberCollection getREAutoRememberCollection(String oql) throws BOSException;
    public void save(String userID, String orgUnitID, String function, String value) throws BOSException;
    public String getValue(String userID, String orgUnitID, String function) throws BOSException;
}