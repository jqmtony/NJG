package com.kingdee.eas.fdc.gcftbiaoa;

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

public interface IHousing extends IDataBase
{
    public HousingInfo getHousingInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HousingInfo getHousingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HousingInfo getHousingInfo(String oql) throws BOSException, EASBizException;
    public HousingCollection getHousingCollection() throws BOSException;
    public HousingCollection getHousingCollection(EntityViewInfo view) throws BOSException;
    public HousingCollection getHousingCollection(String oql) throws BOSException;
}