package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface ICostAccountFixDataFacade extends IBizCtrl
{
    public void fixOrgSourseID(BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException;
    public void fixPrjSourseID(BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException;
}