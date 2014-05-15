package com.kingdee.eas.port.equipment.base;

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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISpecialCheckItem extends ITreeBase
{
    public SpecialCheckItemInfo getSpecialCheckItemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SpecialCheckItemInfo getSpecialCheckItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SpecialCheckItemInfo getSpecialCheckItemInfo(String oql) throws BOSException, EASBizException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection() throws BOSException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection(EntityViewInfo view) throws BOSException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection(String oql) throws BOSException;
}