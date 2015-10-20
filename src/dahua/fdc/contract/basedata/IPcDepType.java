package com.kingdee.eas.fdc.contract.basedata;

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

public interface IPcDepType extends IDataBase
{
    public PcDepTypeInfo getPcDepTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PcDepTypeInfo getPcDepTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PcDepTypeInfo getPcDepTypeInfo(String oql) throws BOSException, EASBizException;
    public PcDepTypeCollection getPcDepTypeCollection() throws BOSException;
    public PcDepTypeCollection getPcDepTypeCollection(EntityViewInfo view) throws BOSException;
    public PcDepTypeCollection getPcDepTypeCollection(String oql) throws BOSException;
}