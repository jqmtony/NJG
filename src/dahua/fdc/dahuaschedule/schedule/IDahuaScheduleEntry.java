package com.kingdee.eas.fdc.dahuaschedule.schedule;

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

public interface IDahuaScheduleEntry extends ICoreBillEntryBase
{
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(String oql) throws BOSException, EASBizException;
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection() throws BOSException;
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection(EntityViewInfo view) throws BOSException;
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection(String oql) throws BOSException;
}