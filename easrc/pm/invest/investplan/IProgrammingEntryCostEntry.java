package com.kingdee.eas.port.pm.invest.investplan;

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

public interface IProgrammingEntryCostEntry extends ICoreBase
{
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection() throws BOSException;
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection(String oql) throws BOSException;
}