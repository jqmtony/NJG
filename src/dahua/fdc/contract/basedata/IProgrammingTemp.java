package com.kingdee.eas.fdc.contract.basedata;

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

public interface IProgrammingTemp extends IDataBase
{
    public ProgrammingTempInfo getProgrammingTempInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingTempInfo getProgrammingTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingTempInfo getProgrammingTempInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingTempCollection getProgrammingTempCollection() throws BOSException;
    public ProgrammingTempCollection getProgrammingTempCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingTempCollection getProgrammingTempCollection(String oql) throws BOSException;
}