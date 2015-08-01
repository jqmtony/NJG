package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IObjectBase;

public interface IEASLogTime extends IObjectBase
{
    public boolean startSucess(IObjectPK pk) throws BOSException, EASBizException;
    public boolean logTime(String ip, String port, String dbName, String userName, String password, String tableName, String field) throws BOSException, EASBizException;
}