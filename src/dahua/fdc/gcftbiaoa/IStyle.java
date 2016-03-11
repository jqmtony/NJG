package com.kingdee.eas.fdc.gcftbiaoa;

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

public interface IStyle extends IDataBase
{
    public StyleInfo getStyleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public StyleInfo getStyleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public StyleInfo getStyleInfo(String oql) throws BOSException, EASBizException;
    public StyleCollection getStyleCollection() throws BOSException;
    public StyleCollection getStyleCollection(EntityViewInfo view) throws BOSException;
    public StyleCollection getStyleCollection(String oql) throws BOSException;
}