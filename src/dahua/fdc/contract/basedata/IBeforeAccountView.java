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
import com.kingdee.eas.framework.IObjectBase;

public interface IBeforeAccountView extends IObjectBase
{
    public BeforeAccountViewInfo getBeforeAccountViewInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BeforeAccountViewInfo getBeforeAccountViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BeforeAccountViewInfo getBeforeAccountViewInfo(String oql) throws BOSException, EASBizException;
    public BeforeAccountViewCollection getBeforeAccountViewCollection() throws BOSException;
    public BeforeAccountViewCollection getBeforeAccountViewCollection(EntityViewInfo view) throws BOSException;
    public BeforeAccountViewCollection getBeforeAccountViewCollection(String oql) throws BOSException;
    public void impTemplate(String companyId) throws BOSException, EASBizException;
}