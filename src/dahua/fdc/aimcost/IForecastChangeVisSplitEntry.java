package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
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

public interface IForecastChangeVisSplitEntry extends IFDCSplitBillEntry
{
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection() throws BOSException;
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection(String oql) throws BOSException;
}