package com.kingdee.eas.fdc.aimcost.costkf;

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

public interface ICQGSEntry extends ICoreBillEntryBase
{
    public CQGSEntryInfo getCQGSEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CQGSEntryInfo getCQGSEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CQGSEntryInfo getCQGSEntryInfo(String oql) throws BOSException, EASBizException;
    public CQGSEntryCollection getCQGSEntryCollection() throws BOSException;
    public CQGSEntryCollection getCQGSEntryCollection(EntityViewInfo view) throws BOSException;
    public CQGSEntryCollection getCQGSEntryCollection(String oql) throws BOSException;
}