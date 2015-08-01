package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

public interface IProjectFacade extends IBizCtrl
{
    public HisProjectCollection getHisProjectCollection(String curProjectID) throws BOSException, EASBizException;
    public boolean updateHisProjectInfo(HisProjectInfo hisProjectInfo) throws BOSException, EASBizException;
    public boolean updateCurProjectInfo(CurProjectInfo curProjectInfo) throws BOSException, EASBizException;
    public Map canAddNew(String curProjectId) throws BOSException, EASBizException;
    public boolean checkBeforeModifyIsDevPrj(CurProjectInfo ObjectValue) throws BOSException, EASBizException;
}