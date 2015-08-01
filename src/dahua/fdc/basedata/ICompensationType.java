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

public interface ICompensationType extends IFDCDataBase
{
    public CompensationTypeInfo getCompensationTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompensationTypeInfo getCompensationTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompensationTypeInfo getCompensationTypeInfo(String oql) throws BOSException, EASBizException;
    public CompensationTypeCollection getCompensationTypeCollection() throws BOSException;
    public CompensationTypeCollection getCompensationTypeCollection(EntityViewInfo view) throws BOSException;
    public CompensationTypeCollection getCompensationTypeCollection(String oql) throws BOSException;
}