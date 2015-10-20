package com.kingdee.eas.fdc.contract.programming;

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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IPcType extends ICoreBillBase
{
    public PcTypeCollection getPcTypeCollection() throws BOSException;
    public PcTypeCollection getPcTypeCollection(EntityViewInfo view) throws BOSException;
    public PcTypeCollection getPcTypeCollection(String oql) throws BOSException;
    public PcTypeInfo getPcTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PcTypeInfo getPcTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PcTypeInfo getPcTypeInfo(String oql) throws BOSException, EASBizException;
}