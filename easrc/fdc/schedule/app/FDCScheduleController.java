package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseController;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCScheduleController extends ScheduleBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getFDCScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getFDCScheduleInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getFDCScheduleInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, FDCScheduleInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, FDCScheduleInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, FDCScheduleInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleCollection getFDCScheduleCollection(Context ctx) throws BOSException, RemoteException;
    public FDCScheduleCollection getFDCScheduleCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCScheduleCollection getFDCScheduleCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public Map cancel(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public Map cancelCancel(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public Map audit(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public Map unAudit(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public Map close(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getNewData(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public void setExecuting(Context ctx, int days) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void setStart(Context ctx, int days) throws BOSException, EASBizException, RemoteException;
    public void setSendMsg(Context ctx, int days) throws BOSException, EASBizException, RemoteException;
    public void saveNewTask(Context ctx, FDCScheduleInfo schedule) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void unClose(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public void checkScheduleState(Context ctx, Set ids) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getScheduleInfo(Context ctx, boolean isMainSchedule, boolean isAdjust, ProjectInfo project, ProjectSpecialInfo projectSpecial, Map param) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getSchedule4Compare(Context ctx, String baseVerID, String compareVerID) throws BOSException, RemoteException;
    public FDCScheduleInfo getNewestVerSchedule(Context ctx, CurProjectInfo curProject, ProjectSpecialInfo projectSpecial) throws BOSException, EASBizException, RemoteException;
    public Map batchChangeTaskProperties(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public void submitMainSchedule(Context ctx, FDCScheduleInfo mainSchedule) throws BOSException, EASBizException, RemoteException;
    public void submitSpecialSchedule(Context ctx, FDCScheduleInfo specialSchedule) throws BOSException, EASBizException, RemoteException;
}