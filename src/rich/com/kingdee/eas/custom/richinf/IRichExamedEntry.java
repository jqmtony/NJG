package com.kingdee.eas.custom.richinf;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IRichExamedEntry extends ICoreBillEntryBase
{
    public RichExamedEntryInfo getRichExamedEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RichExamedEntryInfo getRichExamedEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RichExamedEntryInfo getRichExamedEntryInfo(String oql) throws BOSException, EASBizException;
    public RichExamedEntryCollection getRichExamedEntryCollection() throws BOSException;
    public RichExamedEntryCollection getRichExamedEntryCollection(EntityViewInfo view) throws BOSException;
    public RichExamedEntryCollection getRichExamedEntryCollection(String oql) throws BOSException;
}