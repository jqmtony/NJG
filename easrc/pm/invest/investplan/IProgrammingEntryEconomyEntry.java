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

public interface IProgrammingEntryEconomyEntry extends ICoreBase
{
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection() throws BOSException;
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection(String oql) throws BOSException;
}