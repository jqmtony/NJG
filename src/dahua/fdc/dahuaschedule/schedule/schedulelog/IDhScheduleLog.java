package com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog;

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

public interface IDhScheduleLog extends IDataBase
{
    public DhScheduleLogInfo getDhScheduleLogInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DhScheduleLogInfo getDhScheduleLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DhScheduleLogInfo getDhScheduleLogInfo(String oql) throws BOSException, EASBizException;
    public DhScheduleLogCollection getDhScheduleLogCollection() throws BOSException;
    public DhScheduleLogCollection getDhScheduleLogCollection(EntityViewInfo view) throws BOSException;
    public DhScheduleLogCollection getDhScheduleLogCollection(String oql) throws BOSException;
}