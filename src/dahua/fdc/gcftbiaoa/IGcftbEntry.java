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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IGcftbEntry extends ICoreBillEntryBase
{
    public GcftbEntryInfo getGcftbEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public GcftbEntryInfo getGcftbEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public GcftbEntryInfo getGcftbEntryInfo(String oql) throws BOSException, EASBizException;
    public GcftbEntryCollection getGcftbEntryCollection() throws BOSException;
    public GcftbEntryCollection getGcftbEntryCollection(EntityViewInfo view) throws BOSException;
    public GcftbEntryCollection getGcftbEntryCollection(String oql) throws BOSException;
}