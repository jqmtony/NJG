/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractTankTechnicalEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTankTechnicalEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator11;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator13;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator14;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator15;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator16;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator17;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator18;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator19;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator20;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator21;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator22;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator23;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator24;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator25;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator26;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator27;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator28;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator29;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator30;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator31;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator32;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator33;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator34;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator35;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator36;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator37;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator38;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator39;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator40;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator41;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator42;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator43;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator44;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator45;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator46;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel11;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel12;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel13;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel14;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator47;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator48;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator49;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator50;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator51;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator52;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator53;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel15;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel16;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel17;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel18;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttankDiameter;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttankCapacity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttankWallHeight;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator54;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator55;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator56;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator57;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator58;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator59;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator60;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel20;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel21;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel22;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel23;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel24;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel25;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel26;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel27;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel28;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel29;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel30;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel31;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel32;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel33;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel34;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel35;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel36;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel37;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel38;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator61;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator62;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator63;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator64;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator65;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator66;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator67;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel39;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel40;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel41;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel42;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel43;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel44;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel45;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel46;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel47;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel48;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel49;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator68;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel50;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel51;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator69;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator70;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator71;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator72;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator73;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel52;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel53;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel54;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel55;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel56;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel57;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel58;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel59;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel60;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel61;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel62;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel63;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel64;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel65;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel66;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator74;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator75;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator76;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel67;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshejiyali;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgongzuoyali;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshejiwendu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgongzuowendu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfushiyuliang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshejimidu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contanquangaodu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuanremianji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbibanchicun;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguanbibanone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguanbibantwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandingbanone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandingbantwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandingbanthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbianyuanbanone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbianyuanbantwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbianyuanbanthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongfubanone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongfubantwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongfubanthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjingyouguan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongxingaojy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfalanjiekoujy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contluoshuanjy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchuyouguan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongxingaocy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfalanjiekoucy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contluoshuancy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrukong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhongxingaork;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrukongshuliang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contluoshuanrk;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttouguangkong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttouguangkongsl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contluoshuantgk;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijifljkone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijils;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijiguige;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijicd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeweijizxgone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjianzaoTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshejidanwei;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshigongdanweione;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguanrong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdxtime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaxiushigongriqi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshigongdanweitwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaxiuneirong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqijkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqils;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqiguige;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqicd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaobanqizxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomojkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomojkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomols;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomoguige;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomocd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaomozxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguanjkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguanjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguanls;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguanguige;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguancd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouliangguanzxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttouguangkongone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttouguangkongthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqingsaokongone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqingsaokongtwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqingsaokongthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcehoujilu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwaiguan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjianzaofeiyong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqingsaokongfour;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqingsaokongfive;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contluoshuanqs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifajkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifajkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifals;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifagg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbwcjkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbwcjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifaCd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthuxifazxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbwcls;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaowecenggg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaowencengcd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaowencengzxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttiebijkfaone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttiepijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contteipils;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttiepigg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttiepicd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttiepizxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguanjkflone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguanjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguanls;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguangg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguancd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaoboguanzxg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiareqijkfaone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiareqijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiarqichang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator77;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttankDiameter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttankCapacity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttankWallHeight;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshejiyali;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgongzuoyali;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshejiwendu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgongzuowendu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfushiyuliang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshejimidu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtanquangaodu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuanremianji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbibanchicun;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguanbibanone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguanbibantwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguandingbanone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguandingbantwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguandingbanthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbianyuanbanone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbianyuanbantwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbianyuanbanthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongfubanone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongfubantwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongfubanthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjingyouguan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongxingaojy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfalanjiekoujy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtluoshuanjy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchuyouguan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongxingaocy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfalanjiekoucy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtluoshuancy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrukong;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhongxingaork;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtrukongshuliang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtluoshuanrk;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttouguangkong;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttouguangkongsl;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtluoshuantgk;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijifljkone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijils;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijiguige;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijicd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeweijizxgone;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkjianzaoTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshejidanwei;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshigongdanweione;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguanrong;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdxtime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdaxiushigongriqi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshigongdanweitwo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanedaxiuneirong;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtdaxiuneirong;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqijkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqils;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqiguige;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqicd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiaobanqizxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomojkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomojkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomols;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomoguige;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomocd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaomozxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguanjkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguanjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguanls;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguanguige;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguancd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouliangguanzxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttouguangkongone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttouguangkongthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqingsaokongone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqingsaokongtwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqingsaokongthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcehoujilu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtwaiguan;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtjianzaofeiyong;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqingsaokongfour;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqingsaokongfive;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtluoshuanqs;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifajkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifajkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifals;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifagg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbwcjkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbwcjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifaCd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthuxifazxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbwcls;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbaowecenggg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbaowencengcd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbaowencengzxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttiebijkfaone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttiepijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtteipils;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttiepigg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttiepicd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttiepizxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguanjkflone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguanjkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguanls;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguangg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguancd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdaoboguanzxg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiareqijkfaone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiareqijkfltwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjiarqichang;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.eas.port.equipment.record.TankTechnicalInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTankTechnicalEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractTankTechnicalEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator11 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator13 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator14 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator15 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator16 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator17 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator18 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator19 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator20 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator21 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator22 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator23 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator24 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator25 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator26 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator27 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator28 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator29 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator30 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator31 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator32 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator33 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator34 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator35 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator36 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator37 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator38 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator39 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator40 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator41 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator42 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator43 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator44 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator45 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator46 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel11 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel12 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel13 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel14 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator47 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator48 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator49 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator50 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator51 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator52 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator53 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel15 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel16 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel17 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel18 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel19 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttankDiameter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttankCapacity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttankWallHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator54 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator55 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator56 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator57 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator58 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator59 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator60 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel20 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel21 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel22 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel23 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel24 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel25 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel26 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel27 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel28 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel29 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel30 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel31 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel32 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel33 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel34 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel35 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel36 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel37 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel38 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator61 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator62 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator63 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator64 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator65 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator66 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator67 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel39 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel40 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel41 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel42 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel43 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel44 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel45 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel46 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel47 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel48 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel49 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator68 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel50 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel51 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator69 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator70 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator71 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator72 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator73 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel52 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel53 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel54 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel55 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel56 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel57 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel58 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel59 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel60 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel61 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel62 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel63 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel64 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel65 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel66 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator74 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator75 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator76 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel67 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contshejiyali = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgongzuoyali = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshejiwendu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgongzuowendu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfushiyuliang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshejimidu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contanquangaodu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuanremianji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbibanchicun = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguanbibanone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguanbibantwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguandingbanone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguandingbantwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguandingbanthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbianyuanbanone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbianyuanbantwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbianyuanbanthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongfubanone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongfubantwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongfubanthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjingyouguan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongxingaojy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfalanjiekoujy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contluoshuanjy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchuyouguan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongxingaocy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfalanjiekoucy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contluoshuancy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrukong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhongxingaork = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrukongshuliang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contluoshuanrk = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttouguangkong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttouguangkongsl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contluoshuantgk = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijifljkone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijijkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijils = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijiguige = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijicd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeweijizxgone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjianzaoTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshejidanwei = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshigongdanweione = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguanrong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdxtime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaxiushigongriqi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshigongdanweitwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaxiuneirong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqijkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqijkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqils = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqiguige = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqicd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaobanqizxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomojkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomojkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomols = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomoguige = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomocd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaomozxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguanjkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguanjkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguanls = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguanguige = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguancd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouliangguanzxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttouguangkongone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttouguangkongthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqingsaokongone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqingsaokongtwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqingsaokongthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcehoujilu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwaiguan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjianzaofeiyong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqingsaokongfour = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqingsaokongfive = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contluoshuanqs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifajkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifajkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifals = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifagg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbwcjkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbwcjkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifaCd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthuxifazxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbwcls = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaowecenggg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaowencengcd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaowencengzxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttiebijkfaone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttiepijkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contteipils = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttiepigg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttiepicd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttiepizxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguanjkflone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguanjkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguanls = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguangg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguancd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdaoboguanzxg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiareqijkfaone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiareqijkfltwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiarqichang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator77 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttankDiameter = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttankCapacity = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttankWallHeight = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshejiyali = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtgongzuoyali = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshejiwendu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtgongzuowendu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfushiyuliang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshejimidu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtanquangaodu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuanremianji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbibanchicun = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguanbibanone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguanbibantwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguandingbanone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguandingbantwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguandingbanthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbianyuanbanone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbianyuanbantwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbianyuanbanthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongfubanone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongfubantwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongfubanthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjingyouguan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongxingaojy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfalanjiekoujy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtluoshuanjy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchuyouguan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongxingaocy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfalanjiekoucy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtluoshuancy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrukong = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhongxingaork = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrukongshuliang = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtluoshuanrk = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttouguangkong = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttouguangkongsl = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtluoshuantgk = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijifljkone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijijkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijils = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijiguige = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijicd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeweijizxgone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkjianzaoTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtshejidanwei = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshigongdanweione = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pktime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtguanrong = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkdxtime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkdaxiushigongriqi = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtshigongdanweitwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.scrollPanedaxiuneirong = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtdaxiuneirong = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtjiaobanqijkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiaobanqijkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiaobanqils = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiaobanqiguige = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiaobanqicd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiaobanqizxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomojkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomojkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomols = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomoguige = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomocd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpaomozxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguanjkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguanjkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguanls = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguanguige = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguancd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouliangguanzxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttouguangkongone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttouguangkongthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqingsaokongone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqingsaokongtwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqingsaokongthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcehoujilu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtwaiguan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjianzaofeiyong = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtqingsaokongfour = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqingsaokongfive = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtluoshuanqs = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifajkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifajkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifals = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifagg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbwcjkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbwcjkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifaCd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthuxifazxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbwcls = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbaowecenggg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbaowencengcd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbaowencengzxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttiebijkfaone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttiepijkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtteipils = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttiepigg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttiepicd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttiepizxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguanjkflone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguanjkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguanls = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguangg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguancd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdaoboguanzxg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiareqijkfaone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiareqijkfltwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjiarqichang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDSeparator11.setName("kDSeparator11");
        this.kDSeparator13.setName("kDSeparator13");
        this.kDSeparator14.setName("kDSeparator14");
        this.kDSeparator15.setName("kDSeparator15");
        this.kDSeparator16.setName("kDSeparator16");
        this.kDSeparator17.setName("kDSeparator17");
        this.kDSeparator18.setName("kDSeparator18");
        this.kDSeparator19.setName("kDSeparator19");
        this.kDSeparator20.setName("kDSeparator20");
        this.kDSeparator21.setName("kDSeparator21");
        this.kDSeparator22.setName("kDSeparator22");
        this.kDSeparator23.setName("kDSeparator23");
        this.kDSeparator24.setName("kDSeparator24");
        this.kDSeparator25.setName("kDSeparator25");
        this.kDSeparator26.setName("kDSeparator26");
        this.kDSeparator27.setName("kDSeparator27");
        this.kDSeparator28.setName("kDSeparator28");
        this.kDSeparator29.setName("kDSeparator29");
        this.kDSeparator30.setName("kDSeparator30");
        this.kDSeparator31.setName("kDSeparator31");
        this.kDSeparator32.setName("kDSeparator32");
        this.kDSeparator33.setName("kDSeparator33");
        this.kDSeparator34.setName("kDSeparator34");
        this.kDSeparator35.setName("kDSeparator35");
        this.kDSeparator36.setName("kDSeparator36");
        this.kDSeparator37.setName("kDSeparator37");
        this.kDSeparator38.setName("kDSeparator38");
        this.kDSeparator39.setName("kDSeparator39");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDSeparator40.setName("kDSeparator40");
        this.kDSeparator41.setName("kDSeparator41");
        this.kDSeparator42.setName("kDSeparator42");
        this.kDSeparator43.setName("kDSeparator43");
        this.kDSeparator44.setName("kDSeparator44");
        this.kDSeparator45.setName("kDSeparator45");
        this.kDSeparator46.setName("kDSeparator46");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.kDLabel8.setName("kDLabel8");
        this.kDLabel9.setName("kDLabel9");
        this.kDLabel10.setName("kDLabel10");
        this.kDLabel11.setName("kDLabel11");
        this.kDLabel12.setName("kDLabel12");
        this.kDLabel13.setName("kDLabel13");
        this.kDLabel14.setName("kDLabel14");
        this.kDSeparator47.setName("kDSeparator47");
        this.kDSeparator48.setName("kDSeparator48");
        this.kDSeparator49.setName("kDSeparator49");
        this.kDSeparator50.setName("kDSeparator50");
        this.kDSeparator51.setName("kDSeparator51");
        this.kDSeparator52.setName("kDSeparator52");
        this.kDSeparator53.setName("kDSeparator53");
        this.kDLabel15.setName("kDLabel15");
        this.kDLabel16.setName("kDLabel16");
        this.kDLabel17.setName("kDLabel17");
        this.kDLabel18.setName("kDLabel18");
        this.kDLabel19.setName("kDLabel19");
        this.contNumber.setName("contNumber");
        this.conttankDiameter.setName("conttankDiameter");
        this.conttankCapacity.setName("conttankCapacity");
        this.conttankWallHeight.setName("conttankWallHeight");
        this.kDSeparator54.setName("kDSeparator54");
        this.kDSeparator55.setName("kDSeparator55");
        this.kDSeparator56.setName("kDSeparator56");
        this.kDSeparator57.setName("kDSeparator57");
        this.kDSeparator58.setName("kDSeparator58");
        this.kDSeparator59.setName("kDSeparator59");
        this.kDSeparator60.setName("kDSeparator60");
        this.kDLabel20.setName("kDLabel20");
        this.kDLabel21.setName("kDLabel21");
        this.kDLabel22.setName("kDLabel22");
        this.kDLabel23.setName("kDLabel23");
        this.kDLabel24.setName("kDLabel24");
        this.kDLabel25.setName("kDLabel25");
        this.kDLabel26.setName("kDLabel26");
        this.kDLabel27.setName("kDLabel27");
        this.kDLabel28.setName("kDLabel28");
        this.kDLabel29.setName("kDLabel29");
        this.kDLabel30.setName("kDLabel30");
        this.kDLabel31.setName("kDLabel31");
        this.kDLabel32.setName("kDLabel32");
        this.kDLabel33.setName("kDLabel33");
        this.kDLabel34.setName("kDLabel34");
        this.kDLabel35.setName("kDLabel35");
        this.kDLabel36.setName("kDLabel36");
        this.kDLabel37.setName("kDLabel37");
        this.kDLabel38.setName("kDLabel38");
        this.kDSeparator61.setName("kDSeparator61");
        this.kDSeparator62.setName("kDSeparator62");
        this.kDSeparator63.setName("kDSeparator63");
        this.kDSeparator64.setName("kDSeparator64");
        this.kDSeparator65.setName("kDSeparator65");
        this.kDSeparator66.setName("kDSeparator66");
        this.kDSeparator67.setName("kDSeparator67");
        this.kDLabel39.setName("kDLabel39");
        this.kDLabel40.setName("kDLabel40");
        this.kDLabel41.setName("kDLabel41");
        this.kDLabel42.setName("kDLabel42");
        this.kDLabel43.setName("kDLabel43");
        this.kDLabel44.setName("kDLabel44");
        this.kDLabel45.setName("kDLabel45");
        this.kDLabel46.setName("kDLabel46");
        this.kDLabel47.setName("kDLabel47");
        this.kDLabel48.setName("kDLabel48");
        this.kDLabel49.setName("kDLabel49");
        this.kDSeparator68.setName("kDSeparator68");
        this.kDLabel50.setName("kDLabel50");
        this.kDLabel51.setName("kDLabel51");
        this.kDSeparator69.setName("kDSeparator69");
        this.kDSeparator70.setName("kDSeparator70");
        this.kDSeparator71.setName("kDSeparator71");
        this.kDSeparator72.setName("kDSeparator72");
        this.kDSeparator73.setName("kDSeparator73");
        this.kDLabel52.setName("kDLabel52");
        this.kDLabel53.setName("kDLabel53");
        this.kDLabel54.setName("kDLabel54");
        this.kDLabel55.setName("kDLabel55");
        this.kDLabel56.setName("kDLabel56");
        this.kDLabel57.setName("kDLabel57");
        this.kDLabel58.setName("kDLabel58");
        this.kDLabel59.setName("kDLabel59");
        this.kDLabel60.setName("kDLabel60");
        this.kDLabel61.setName("kDLabel61");
        this.kDLabel62.setName("kDLabel62");
        this.kDLabel63.setName("kDLabel63");
        this.kDLabel64.setName("kDLabel64");
        this.kDLabel65.setName("kDLabel65");
        this.kDLabel66.setName("kDLabel66");
        this.kDSeparator74.setName("kDSeparator74");
        this.kDSeparator75.setName("kDSeparator75");
        this.kDSeparator76.setName("kDSeparator76");
        this.kDLabel67.setName("kDLabel67");
        this.contshejiyali.setName("contshejiyali");
        this.contgongzuoyali.setName("contgongzuoyali");
        this.contshejiwendu.setName("contshejiwendu");
        this.contgongzuowendu.setName("contgongzuowendu");
        this.contfushiyuliang.setName("contfushiyuliang");
        this.contshejimidu.setName("contshejimidu");
        this.contanquangaodu.setName("contanquangaodu");
        this.conthuanremianji.setName("conthuanremianji");
        this.contbibanchicun.setName("contbibanchicun");
        this.contguanbibanone.setName("contguanbibanone");
        this.contguanbibantwo.setName("contguanbibantwo");
        this.contguandingbanone.setName("contguandingbanone");
        this.contguandingbantwo.setName("contguandingbantwo");
        this.contguandingbanthree.setName("contguandingbanthree");
        this.contbianyuanbanone.setName("contbianyuanbanone");
        this.contbianyuanbantwo.setName("contbianyuanbantwo");
        this.contbianyuanbanthree.setName("contbianyuanbanthree");
        this.contzhongfubanone.setName("contzhongfubanone");
        this.contzhongfubantwo.setName("contzhongfubantwo");
        this.contzhongfubanthree.setName("contzhongfubanthree");
        this.contjingyouguan.setName("contjingyouguan");
        this.contzhongxingaojy.setName("contzhongxingaojy");
        this.contfalanjiekoujy.setName("contfalanjiekoujy");
        this.contluoshuanjy.setName("contluoshuanjy");
        this.contchuyouguan.setName("contchuyouguan");
        this.contzhongxingaocy.setName("contzhongxingaocy");
        this.contfalanjiekoucy.setName("contfalanjiekoucy");
        this.contluoshuancy.setName("contluoshuancy");
        this.contrukong.setName("contrukong");
        this.contzhongxingaork.setName("contzhongxingaork");
        this.contrukongshuliang.setName("contrukongshuliang");
        this.contluoshuanrk.setName("contluoshuanrk");
        this.conttouguangkong.setName("conttouguangkong");
        this.conttouguangkongsl.setName("conttouguangkongsl");
        this.contluoshuantgk.setName("contluoshuantgk");
        this.contyeweijifljkone.setName("contyeweijifljkone");
        this.contyeweijijkfltwo.setName("contyeweijijkfltwo");
        this.contyeweijils.setName("contyeweijils");
        this.contyeweijiguige.setName("contyeweijiguige");
        this.contyeweijicd.setName("contyeweijicd");
        this.contyeweijizxgone.setName("contyeweijizxgone");
        this.contjianzaoTime.setName("contjianzaoTime");
        this.contshejidanwei.setName("contshejidanwei");
        this.contshigongdanweione.setName("contshigongdanweione");
        this.conttime.setName("conttime");
        this.contguanrong.setName("contguanrong");
        this.contdxtime.setName("contdxtime");
        this.contdaxiushigongriqi.setName("contdaxiushigongriqi");
        this.contshigongdanweitwo.setName("contshigongdanweitwo");
        this.contdaxiuneirong.setName("contdaxiuneirong");
        this.contjiaobanqijkflone.setName("contjiaobanqijkflone");
        this.contjiaobanqijkfltwo.setName("contjiaobanqijkfltwo");
        this.contjiaobanqils.setName("contjiaobanqils");
        this.contjiaobanqiguige.setName("contjiaobanqiguige");
        this.contjiaobanqicd.setName("contjiaobanqicd");
        this.contjiaobanqizxg.setName("contjiaobanqizxg");
        this.contpaomojkflone.setName("contpaomojkflone");
        this.contpaomojkfltwo.setName("contpaomojkfltwo");
        this.contpaomols.setName("contpaomols");
        this.contpaomoguige.setName("contpaomoguige");
        this.contpaomocd.setName("contpaomocd");
        this.contpaomozxg.setName("contpaomozxg");
        this.contyouliangguanjkflone.setName("contyouliangguanjkflone");
        this.contyouliangguanjkfltwo.setName("contyouliangguanjkfltwo");
        this.contyouliangguanls.setName("contyouliangguanls");
        this.contyouliangguanguige.setName("contyouliangguanguige");
        this.contyouliangguancd.setName("contyouliangguancd");
        this.contyouliangguanzxg.setName("contyouliangguanzxg");
        this.conttouguangkongone.setName("conttouguangkongone");
        this.conttouguangkongthree.setName("conttouguangkongthree");
        this.contqingsaokongone.setName("contqingsaokongone");
        this.contqingsaokongtwo.setName("contqingsaokongtwo");
        this.contqingsaokongthree.setName("contqingsaokongthree");
        this.contcehoujilu.setName("contcehoujilu");
        this.contwaiguan.setName("contwaiguan");
        this.contjianzaofeiyong.setName("contjianzaofeiyong");
        this.contqingsaokongfour.setName("contqingsaokongfour");
        this.contqingsaokongfive.setName("contqingsaokongfive");
        this.contluoshuanqs.setName("contluoshuanqs");
        this.conthuxifajkflone.setName("conthuxifajkflone");
        this.conthuxifajkfltwo.setName("conthuxifajkfltwo");
        this.conthuxifals.setName("conthuxifals");
        this.conthuxifagg.setName("conthuxifagg");
        this.contbwcjkflone.setName("contbwcjkflone");
        this.contbwcjkfltwo.setName("contbwcjkfltwo");
        this.conthuxifaCd.setName("conthuxifaCd");
        this.conthuxifazxg.setName("conthuxifazxg");
        this.contbwcls.setName("contbwcls");
        this.contbaowecenggg.setName("contbaowecenggg");
        this.contbaowencengcd.setName("contbaowencengcd");
        this.contbaowencengzxg.setName("contbaowencengzxg");
        this.conttiebijkfaone.setName("conttiebijkfaone");
        this.conttiepijkfltwo.setName("conttiepijkfltwo");
        this.contteipils.setName("contteipils");
        this.conttiepigg.setName("conttiepigg");
        this.conttiepicd.setName("conttiepicd");
        this.conttiepizxg.setName("conttiepizxg");
        this.contdaoboguanjkflone.setName("contdaoboguanjkflone");
        this.contdaoboguanjkfltwo.setName("contdaoboguanjkfltwo");
        this.contdaoboguanls.setName("contdaoboguanls");
        this.contdaoboguangg.setName("contdaoboguangg");
        this.contdaoboguancd.setName("contdaoboguancd");
        this.contdaoboguanzxg.setName("contdaoboguanzxg");
        this.contjiareqijkfaone.setName("contjiareqijkfaone");
        this.contjiareqijkfltwo.setName("contjiareqijkfltwo");
        this.contjiarqichang.setName("contjiarqichang");
        this.contAuditTime.setName("contAuditTime");
        this.contBizStatus.setName("contBizStatus");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contStatus.setName("contStatus");
        this.kDSeparator77.setName("kDSeparator77");
        this.txtNumber.setName("txtNumber");
        this.txttankDiameter.setName("txttankDiameter");
        this.txttankCapacity.setName("txttankCapacity");
        this.txttankWallHeight.setName("txttankWallHeight");
        this.txtshejiyali.setName("txtshejiyali");
        this.txtgongzuoyali.setName("txtgongzuoyali");
        this.txtshejiwendu.setName("txtshejiwendu");
        this.txtgongzuowendu.setName("txtgongzuowendu");
        this.txtfushiyuliang.setName("txtfushiyuliang");
        this.txtshejimidu.setName("txtshejimidu");
        this.txtanquangaodu.setName("txtanquangaodu");
        this.txthuanremianji.setName("txthuanremianji");
        this.txtbibanchicun.setName("txtbibanchicun");
        this.txtguanbibanone.setName("txtguanbibanone");
        this.txtguanbibantwo.setName("txtguanbibantwo");
        this.txtguandingbanone.setName("txtguandingbanone");
        this.txtguandingbantwo.setName("txtguandingbantwo");
        this.txtguandingbanthree.setName("txtguandingbanthree");
        this.txtbianyuanbanone.setName("txtbianyuanbanone");
        this.txtbianyuanbantwo.setName("txtbianyuanbantwo");
        this.txtbianyuanbanthree.setName("txtbianyuanbanthree");
        this.txtzhongfubanone.setName("txtzhongfubanone");
        this.txtzhongfubantwo.setName("txtzhongfubantwo");
        this.txtzhongfubanthree.setName("txtzhongfubanthree");
        this.txtjingyouguan.setName("txtjingyouguan");
        this.txtzhongxingaojy.setName("txtzhongxingaojy");
        this.txtfalanjiekoujy.setName("txtfalanjiekoujy");
        this.txtluoshuanjy.setName("txtluoshuanjy");
        this.txtchuyouguan.setName("txtchuyouguan");
        this.txtzhongxingaocy.setName("txtzhongxingaocy");
        this.txtfalanjiekoucy.setName("txtfalanjiekoucy");
        this.txtluoshuancy.setName("txtluoshuancy");
        this.txtrukong.setName("txtrukong");
        this.txtzhongxingaork.setName("txtzhongxingaork");
        this.txtrukongshuliang.setName("txtrukongshuliang");
        this.txtluoshuanrk.setName("txtluoshuanrk");
        this.txttouguangkong.setName("txttouguangkong");
        this.txttouguangkongsl.setName("txttouguangkongsl");
        this.txtluoshuantgk.setName("txtluoshuantgk");
        this.txtyeweijifljkone.setName("txtyeweijifljkone");
        this.txtyeweijijkfltwo.setName("txtyeweijijkfltwo");
        this.txtyeweijils.setName("txtyeweijils");
        this.txtyeweijiguige.setName("txtyeweijiguige");
        this.txtyeweijicd.setName("txtyeweijicd");
        this.txtyeweijizxgone.setName("txtyeweijizxgone");
        this.pkjianzaoTime.setName("pkjianzaoTime");
        this.txtshejidanwei.setName("txtshejidanwei");
        this.txtshigongdanweione.setName("txtshigongdanweione");
        this.pktime.setName("pktime");
        this.txtguanrong.setName("txtguanrong");
        this.pkdxtime.setName("pkdxtime");
        this.pkdaxiushigongriqi.setName("pkdaxiushigongriqi");
        this.txtshigongdanweitwo.setName("txtshigongdanweitwo");
        this.scrollPanedaxiuneirong.setName("scrollPanedaxiuneirong");
        this.txtdaxiuneirong.setName("txtdaxiuneirong");
        this.txtjiaobanqijkflone.setName("txtjiaobanqijkflone");
        this.txtjiaobanqijkfltwo.setName("txtjiaobanqijkfltwo");
        this.txtjiaobanqils.setName("txtjiaobanqils");
        this.txtjiaobanqiguige.setName("txtjiaobanqiguige");
        this.txtjiaobanqicd.setName("txtjiaobanqicd");
        this.txtjiaobanqizxg.setName("txtjiaobanqizxg");
        this.txtpaomojkflone.setName("txtpaomojkflone");
        this.txtpaomojkfltwo.setName("txtpaomojkfltwo");
        this.txtpaomols.setName("txtpaomols");
        this.txtpaomoguige.setName("txtpaomoguige");
        this.txtpaomocd.setName("txtpaomocd");
        this.txtpaomozxg.setName("txtpaomozxg");
        this.txtyouliangguanjkflone.setName("txtyouliangguanjkflone");
        this.txtyouliangguanjkfltwo.setName("txtyouliangguanjkfltwo");
        this.txtyouliangguanls.setName("txtyouliangguanls");
        this.txtyouliangguanguige.setName("txtyouliangguanguige");
        this.txtyouliangguancd.setName("txtyouliangguancd");
        this.txtyouliangguanzxg.setName("txtyouliangguanzxg");
        this.txttouguangkongone.setName("txttouguangkongone");
        this.txttouguangkongthree.setName("txttouguangkongthree");
        this.txtqingsaokongone.setName("txtqingsaokongone");
        this.txtqingsaokongtwo.setName("txtqingsaokongtwo");
        this.txtqingsaokongthree.setName("txtqingsaokongthree");
        this.txtcehoujilu.setName("txtcehoujilu");
        this.txtwaiguan.setName("txtwaiguan");
        this.txtjianzaofeiyong.setName("txtjianzaofeiyong");
        this.txtqingsaokongfour.setName("txtqingsaokongfour");
        this.txtqingsaokongfive.setName("txtqingsaokongfive");
        this.txtluoshuanqs.setName("txtluoshuanqs");
        this.txthuxifajkflone.setName("txthuxifajkflone");
        this.txthuxifajkfltwo.setName("txthuxifajkfltwo");
        this.txthuxifals.setName("txthuxifals");
        this.txthuxifagg.setName("txthuxifagg");
        this.txtbwcjkflone.setName("txtbwcjkflone");
        this.txtbwcjkfltwo.setName("txtbwcjkfltwo");
        this.txthuxifaCd.setName("txthuxifaCd");
        this.txthuxifazxg.setName("txthuxifazxg");
        this.txtbwcls.setName("txtbwcls");
        this.txtbaowecenggg.setName("txtbaowecenggg");
        this.txtbaowencengcd.setName("txtbaowencengcd");
        this.txtbaowencengzxg.setName("txtbaowencengzxg");
        this.txttiebijkfaone.setName("txttiebijkfaone");
        this.txttiepijkfltwo.setName("txttiepijkfltwo");
        this.txtteipils.setName("txtteipils");
        this.txttiepigg.setName("txttiepigg");
        this.txttiepicd.setName("txttiepicd");
        this.txttiepizxg.setName("txttiepizxg");
        this.txtdaoboguanjkflone.setName("txtdaoboguanjkflone");
        this.txtdaoboguanjkfltwo.setName("txtdaoboguanjkfltwo");
        this.txtdaoboguanls.setName("txtdaoboguanls");
        this.txtdaoboguangg.setName("txtdaoboguangg");
        this.txtdaoboguancd.setName("txtdaoboguancd");
        this.txtdaoboguanzxg.setName("txtdaoboguanzxg");
        this.txtjiareqijkfaone.setName("txtjiareqijkfaone");
        this.txtjiareqijkfltwo.setName("txtjiareqijkfltwo");
        this.txtjiarqichang.setName("txtjiarqichang");
        this.pkAuditTime.setName("pkAuditTime");
        this.comboBizStatus.setName("comboBizStatus");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboStatus.setName("comboStatus");
        // CoreUI		
        this.setPreferredSize(new Dimension(660,629));
        // kDScrollPane1		
        this.kDScrollPane1.setOpaque(false);		
        this.kDScrollPane1.setPreferredSize(new Dimension(10,10));
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));		
        this.kDPanel1.setPreferredSize(new Dimension(657,931));		
        this.kDPanel1.setMinimumSize(new Dimension(657,931));
        // kDSeparator8
        // kDSeparator9
        // kDSeparator10
        // kDSeparator11
        // kDSeparator13
        // kDSeparator14
        // kDSeparator15
        // kDSeparator16
        // kDSeparator17
        // kDSeparator18
        // kDSeparator19
        // kDSeparator20
        // kDSeparator21
        // kDSeparator22
        // kDSeparator23
        // kDSeparator24
        // kDSeparator25
        // kDSeparator26
        // kDSeparator27
        // kDSeparator28
        // kDSeparator29
        // kDSeparator30
        // kDSeparator31
        // kDSeparator32
        // kDSeparator33
        // kDSeparator34
        // kDSeparator35
        // kDSeparator36
        // kDSeparator37
        // kDSeparator38
        // kDSeparator39
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDSeparator40		
        this.kDSeparator40.setOrientation(1);
        // kDSeparator41		
        this.kDSeparator41.setOrientation(1);
        // kDSeparator42		
        this.kDSeparator42.setOrientation(1);
        // kDSeparator43		
        this.kDSeparator43.setOrientation(1);
        // kDSeparator44		
        this.kDSeparator44.setOrientation(1);
        // kDSeparator45		
        this.kDSeparator45.setOrientation(1);
        // kDSeparator46		
        this.kDSeparator46.setOrientation(1);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
        // kDLabel11		
        this.kDLabel11.setText(resHelper.getString("kDLabel11.text"));
        // kDLabel12		
        this.kDLabel12.setText(resHelper.getString("kDLabel12.text"));
        // kDLabel13		
        this.kDLabel13.setText(resHelper.getString("kDLabel13.text"));
        // kDLabel14		
        this.kDLabel14.setText(resHelper.getString("kDLabel14.text"));
        // kDSeparator47		
        this.kDSeparator47.setOrientation(1);
        // kDSeparator48		
        this.kDSeparator48.setOrientation(1);
        // kDSeparator49		
        this.kDSeparator49.setOrientation(1);
        // kDSeparator50		
        this.kDSeparator50.setOrientation(1);
        // kDSeparator51		
        this.kDSeparator51.setOrientation(1);
        // kDSeparator52		
        this.kDSeparator52.setOrientation(1);
        // kDSeparator53		
        this.kDSeparator53.setOrientation(1);
        // kDLabel15		
        this.kDLabel15.setText(resHelper.getString("kDLabel15.text"));
        // kDLabel16		
        this.kDLabel16.setText(resHelper.getString("kDLabel16.text"));
        // kDLabel17		
        this.kDLabel17.setText(resHelper.getString("kDLabel17.text"));
        // kDLabel18		
        this.kDLabel18.setText(resHelper.getString("kDLabel18.text"));
        // kDLabel19		
        this.kDLabel19.setText(resHelper.getString("kDLabel19.text"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(0);		
        this.contNumber.setBoundLabelUnderline(true);
        // conttankDiameter		
        this.conttankDiameter.setBoundLabelText(resHelper.getString("conttankDiameter.boundLabelText"));		
        this.conttankDiameter.setBoundLabelLength(0);		
        this.conttankDiameter.setBoundLabelUnderline(true);		
        this.conttankDiameter.setVisible(true);
        // conttankCapacity		
        this.conttankCapacity.setBoundLabelText(resHelper.getString("conttankCapacity.boundLabelText"));		
        this.conttankCapacity.setBoundLabelLength(0);		
        this.conttankCapacity.setBoundLabelUnderline(true);		
        this.conttankCapacity.setVisible(true);
        // conttankWallHeight		
        this.conttankWallHeight.setBoundLabelText(resHelper.getString("conttankWallHeight.boundLabelText"));		
        this.conttankWallHeight.setBoundLabelLength(0);		
        this.conttankWallHeight.setBoundLabelUnderline(true);		
        this.conttankWallHeight.setVisible(true);
        // kDSeparator54		
        this.kDSeparator54.setOrientation(1);
        // kDSeparator55		
        this.kDSeparator55.setOrientation(1);
        // kDSeparator56		
        this.kDSeparator56.setOrientation(1);
        // kDSeparator57		
        this.kDSeparator57.setOrientation(1);
        // kDSeparator58		
        this.kDSeparator58.setOrientation(1);
        // kDSeparator59		
        this.kDSeparator59.setOrientation(1);
        // kDSeparator60		
        this.kDSeparator60.setOrientation(1);
        // kDLabel20		
        this.kDLabel20.setText(resHelper.getString("kDLabel20.text"));
        // kDLabel21		
        this.kDLabel21.setText(resHelper.getString("kDLabel21.text"));
        // kDLabel22		
        this.kDLabel22.setText(resHelper.getString("kDLabel22.text"));
        // kDLabel23		
        this.kDLabel23.setText(resHelper.getString("kDLabel23.text"));
        // kDLabel24		
        this.kDLabel24.setText(resHelper.getString("kDLabel24.text"));
        // kDLabel25		
        this.kDLabel25.setText(resHelper.getString("kDLabel25.text"));
        // kDLabel26		
        this.kDLabel26.setText(resHelper.getString("kDLabel26.text"));
        // kDLabel27		
        this.kDLabel27.setText(resHelper.getString("kDLabel27.text"));
        // kDLabel28		
        this.kDLabel28.setText(resHelper.getString("kDLabel28.text"));
        // kDLabel29		
        this.kDLabel29.setText(resHelper.getString("kDLabel29.text"));
        // kDLabel30		
        this.kDLabel30.setText(resHelper.getString("kDLabel30.text"));
        // kDLabel31		
        this.kDLabel31.setText(resHelper.getString("kDLabel31.text"));
        // kDLabel32		
        this.kDLabel32.setText(resHelper.getString("kDLabel32.text"));
        // kDLabel33		
        this.kDLabel33.setText(resHelper.getString("kDLabel33.text"));
        // kDLabel34		
        this.kDLabel34.setText(resHelper.getString("kDLabel34.text"));
        // kDLabel35		
        this.kDLabel35.setText(resHelper.getString("kDLabel35.text"));
        // kDLabel36		
        this.kDLabel36.setText(resHelper.getString("kDLabel36.text"));
        // kDLabel37		
        this.kDLabel37.setText(resHelper.getString("kDLabel37.text"));
        // kDLabel38		
        this.kDLabel38.setText(resHelper.getString("kDLabel38.text"));
        // kDSeparator61		
        this.kDSeparator61.setOrientation(1);
        // kDSeparator62		
        this.kDSeparator62.setOrientation(1);
        // kDSeparator63		
        this.kDSeparator63.setOrientation(1);
        // kDSeparator64		
        this.kDSeparator64.setOrientation(1);
        // kDSeparator65		
        this.kDSeparator65.setOrientation(1);
        // kDSeparator66		
        this.kDSeparator66.setOrientation(1);
        // kDSeparator67		
        this.kDSeparator67.setOrientation(1);
        // kDLabel39		
        this.kDLabel39.setText(resHelper.getString("kDLabel39.text"));
        // kDLabel40		
        this.kDLabel40.setText(resHelper.getString("kDLabel40.text"));
        // kDLabel41		
        this.kDLabel41.setText(resHelper.getString("kDLabel41.text"));
        // kDLabel42		
        this.kDLabel42.setText(resHelper.getString("kDLabel42.text"));
        // kDLabel43		
        this.kDLabel43.setText(resHelper.getString("kDLabel43.text"));
        // kDLabel44		
        this.kDLabel44.setText(resHelper.getString("kDLabel44.text"));
        // kDLabel45		
        this.kDLabel45.setText(resHelper.getString("kDLabel45.text"));
        // kDLabel46		
        this.kDLabel46.setText(resHelper.getString("kDLabel46.text"));
        // kDLabel47		
        this.kDLabel47.setText(resHelper.getString("kDLabel47.text"));
        // kDLabel48		
        this.kDLabel48.setText(resHelper.getString("kDLabel48.text"));
        // kDLabel49		
        this.kDLabel49.setText(resHelper.getString("kDLabel49.text"));
        // kDSeparator68
        // kDLabel50		
        this.kDLabel50.setText(resHelper.getString("kDLabel50.text"));
        // kDLabel51		
        this.kDLabel51.setText(resHelper.getString("kDLabel51.text"));
        // kDSeparator69		
        this.kDSeparator69.setOrientation(1);
        // kDSeparator70		
        this.kDSeparator70.setOrientation(1);
        // kDSeparator71		
        this.kDSeparator71.setOrientation(1);
        // kDSeparator72		
        this.kDSeparator72.setOrientation(1);
        // kDSeparator73		
        this.kDSeparator73.setOrientation(1);
        // kDLabel52		
        this.kDLabel52.setText(resHelper.getString("kDLabel52.text"));
        // kDLabel53		
        this.kDLabel53.setText(resHelper.getString("kDLabel53.text"));
        // kDLabel54		
        this.kDLabel54.setText(resHelper.getString("kDLabel54.text"));
        // kDLabel55		
        this.kDLabel55.setText(resHelper.getString("kDLabel55.text"));
        // kDLabel56		
        this.kDLabel56.setText(resHelper.getString("kDLabel56.text"));
        // kDLabel57		
        this.kDLabel57.setText(resHelper.getString("kDLabel57.text"));
        // kDLabel58		
        this.kDLabel58.setText(resHelper.getString("kDLabel58.text"));
        // kDLabel59		
        this.kDLabel59.setText(resHelper.getString("kDLabel59.text"));
        // kDLabel60		
        this.kDLabel60.setText(resHelper.getString("kDLabel60.text"));
        // kDLabel61		
        this.kDLabel61.setText(resHelper.getString("kDLabel61.text"));
        // kDLabel62		
        this.kDLabel62.setText(resHelper.getString("kDLabel62.text"));
        // kDLabel63		
        this.kDLabel63.setText(resHelper.getString("kDLabel63.text"));
        // kDLabel64		
        this.kDLabel64.setText(resHelper.getString("kDLabel64.text"));
        // kDLabel65		
        this.kDLabel65.setText(resHelper.getString("kDLabel65.text"));
        // kDLabel66		
        this.kDLabel66.setText(resHelper.getString("kDLabel66.text"));
        // kDSeparator74		
        this.kDSeparator74.setOrientation(1);
        // kDSeparator75		
        this.kDSeparator75.setOrientation(1);
        // kDSeparator76		
        this.kDSeparator76.setOrientation(1);
        // kDLabel67		
        this.kDLabel67.setText(resHelper.getString("kDLabel67.text"));
        // contshejiyali		
        this.contshejiyali.setBoundLabelText(resHelper.getString("contshejiyali.boundLabelText"));		
        this.contshejiyali.setBoundLabelLength(0);		
        this.contshejiyali.setBoundLabelUnderline(true);		
        this.contshejiyali.setVisible(true);
        // contgongzuoyali		
        this.contgongzuoyali.setBoundLabelText(resHelper.getString("contgongzuoyali.boundLabelText"));		
        this.contgongzuoyali.setBoundLabelLength(0);		
        this.contgongzuoyali.setBoundLabelUnderline(true);		
        this.contgongzuoyali.setVisible(true);
        // contshejiwendu		
        this.contshejiwendu.setBoundLabelText(resHelper.getString("contshejiwendu.boundLabelText"));		
        this.contshejiwendu.setBoundLabelLength(0);		
        this.contshejiwendu.setBoundLabelUnderline(true);		
        this.contshejiwendu.setVisible(true);
        // contgongzuowendu		
        this.contgongzuowendu.setBoundLabelText(resHelper.getString("contgongzuowendu.boundLabelText"));		
        this.contgongzuowendu.setBoundLabelLength(0);		
        this.contgongzuowendu.setBoundLabelUnderline(true);		
        this.contgongzuowendu.setVisible(true);
        // contfushiyuliang		
        this.contfushiyuliang.setBoundLabelText(resHelper.getString("contfushiyuliang.boundLabelText"));		
        this.contfushiyuliang.setBoundLabelLength(0);		
        this.contfushiyuliang.setBoundLabelUnderline(true);		
        this.contfushiyuliang.setVisible(true);
        // contshejimidu		
        this.contshejimidu.setBoundLabelText(resHelper.getString("contshejimidu.boundLabelText"));		
        this.contshejimidu.setBoundLabelLength(0);		
        this.contshejimidu.setBoundLabelUnderline(true);		
        this.contshejimidu.setVisible(true);
        // contanquangaodu		
        this.contanquangaodu.setBoundLabelText(resHelper.getString("contanquangaodu.boundLabelText"));		
        this.contanquangaodu.setBoundLabelLength(0);		
        this.contanquangaodu.setBoundLabelUnderline(true);		
        this.contanquangaodu.setVisible(true);
        // conthuanremianji		
        this.conthuanremianji.setBoundLabelText(resHelper.getString("conthuanremianji.boundLabelText"));		
        this.conthuanremianji.setBoundLabelLength(0);		
        this.conthuanremianji.setBoundLabelUnderline(true);		
        this.conthuanremianji.setVisible(true);
        // contbibanchicun		
        this.contbibanchicun.setBoundLabelText(resHelper.getString("contbibanchicun.boundLabelText"));		
        this.contbibanchicun.setBoundLabelLength(0);		
        this.contbibanchicun.setBoundLabelUnderline(true);		
        this.contbibanchicun.setVisible(true);
        // contguanbibanone		
        this.contguanbibanone.setBoundLabelText(resHelper.getString("contguanbibanone.boundLabelText"));		
        this.contguanbibanone.setBoundLabelLength(0);		
        this.contguanbibanone.setBoundLabelUnderline(true);		
        this.contguanbibanone.setVisible(true);
        // contguanbibantwo		
        this.contguanbibantwo.setBoundLabelText(resHelper.getString("contguanbibantwo.boundLabelText"));		
        this.contguanbibantwo.setBoundLabelLength(0);		
        this.contguanbibantwo.setBoundLabelUnderline(true);		
        this.contguanbibantwo.setVisible(true);
        // contguandingbanone		
        this.contguandingbanone.setBoundLabelText(resHelper.getString("contguandingbanone.boundLabelText"));		
        this.contguandingbanone.setBoundLabelLength(0);		
        this.contguandingbanone.setBoundLabelUnderline(true);		
        this.contguandingbanone.setVisible(true);
        // contguandingbantwo		
        this.contguandingbantwo.setBoundLabelText(resHelper.getString("contguandingbantwo.boundLabelText"));		
        this.contguandingbantwo.setBoundLabelLength(0);		
        this.contguandingbantwo.setBoundLabelUnderline(true);		
        this.contguandingbantwo.setVisible(true);
        // contguandingbanthree		
        this.contguandingbanthree.setBoundLabelText(resHelper.getString("contguandingbanthree.boundLabelText"));		
        this.contguandingbanthree.setBoundLabelLength(0);		
        this.contguandingbanthree.setBoundLabelUnderline(true);		
        this.contguandingbanthree.setVisible(true);
        // contbianyuanbanone		
        this.contbianyuanbanone.setBoundLabelText(resHelper.getString("contbianyuanbanone.boundLabelText"));		
        this.contbianyuanbanone.setBoundLabelLength(0);		
        this.contbianyuanbanone.setBoundLabelUnderline(true);		
        this.contbianyuanbanone.setVisible(true);
        // contbianyuanbantwo		
        this.contbianyuanbantwo.setBoundLabelText(resHelper.getString("contbianyuanbantwo.boundLabelText"));		
        this.contbianyuanbantwo.setBoundLabelLength(0);		
        this.contbianyuanbantwo.setBoundLabelUnderline(true);		
        this.contbianyuanbantwo.setVisible(true);
        // contbianyuanbanthree		
        this.contbianyuanbanthree.setBoundLabelText(resHelper.getString("contbianyuanbanthree.boundLabelText"));		
        this.contbianyuanbanthree.setBoundLabelLength(0);		
        this.contbianyuanbanthree.setBoundLabelUnderline(true);		
        this.contbianyuanbanthree.setVisible(true);
        // contzhongfubanone		
        this.contzhongfubanone.setBoundLabelText(resHelper.getString("contzhongfubanone.boundLabelText"));		
        this.contzhongfubanone.setBoundLabelLength(0);		
        this.contzhongfubanone.setBoundLabelUnderline(true);		
        this.contzhongfubanone.setVisible(true);
        // contzhongfubantwo		
        this.contzhongfubantwo.setBoundLabelText(resHelper.getString("contzhongfubantwo.boundLabelText"));		
        this.contzhongfubantwo.setBoundLabelLength(0);		
        this.contzhongfubantwo.setBoundLabelUnderline(true);		
        this.contzhongfubantwo.setVisible(true);
        // contzhongfubanthree		
        this.contzhongfubanthree.setBoundLabelText(resHelper.getString("contzhongfubanthree.boundLabelText"));		
        this.contzhongfubanthree.setBoundLabelLength(0);		
        this.contzhongfubanthree.setBoundLabelUnderline(true);		
        this.contzhongfubanthree.setVisible(true);
        // contjingyouguan		
        this.contjingyouguan.setBoundLabelText(resHelper.getString("contjingyouguan.boundLabelText"));		
        this.contjingyouguan.setBoundLabelLength(0);		
        this.contjingyouguan.setBoundLabelUnderline(true);		
        this.contjingyouguan.setVisible(true);
        // contzhongxingaojy		
        this.contzhongxingaojy.setBoundLabelText(resHelper.getString("contzhongxingaojy.boundLabelText"));		
        this.contzhongxingaojy.setBoundLabelLength(0);		
        this.contzhongxingaojy.setBoundLabelUnderline(true);		
        this.contzhongxingaojy.setVisible(true);
        // contfalanjiekoujy		
        this.contfalanjiekoujy.setBoundLabelText(resHelper.getString("contfalanjiekoujy.boundLabelText"));		
        this.contfalanjiekoujy.setBoundLabelLength(0);		
        this.contfalanjiekoujy.setBoundLabelUnderline(true);		
        this.contfalanjiekoujy.setVisible(true);
        // contluoshuanjy		
        this.contluoshuanjy.setBoundLabelText(resHelper.getString("contluoshuanjy.boundLabelText"));		
        this.contluoshuanjy.setBoundLabelLength(0);		
        this.contluoshuanjy.setBoundLabelUnderline(true);		
        this.contluoshuanjy.setVisible(true);
        // contchuyouguan		
        this.contchuyouguan.setBoundLabelText(resHelper.getString("contchuyouguan.boundLabelText"));		
        this.contchuyouguan.setBoundLabelLength(0);		
        this.contchuyouguan.setBoundLabelUnderline(true);		
        this.contchuyouguan.setVisible(true);
        // contzhongxingaocy		
        this.contzhongxingaocy.setBoundLabelText(resHelper.getString("contzhongxingaocy.boundLabelText"));		
        this.contzhongxingaocy.setBoundLabelLength(0);		
        this.contzhongxingaocy.setBoundLabelUnderline(true);		
        this.contzhongxingaocy.setVisible(true);
        // contfalanjiekoucy		
        this.contfalanjiekoucy.setBoundLabelText(resHelper.getString("contfalanjiekoucy.boundLabelText"));		
        this.contfalanjiekoucy.setBoundLabelLength(0);		
        this.contfalanjiekoucy.setBoundLabelUnderline(true);		
        this.contfalanjiekoucy.setVisible(true);
        // contluoshuancy		
        this.contluoshuancy.setBoundLabelText(resHelper.getString("contluoshuancy.boundLabelText"));		
        this.contluoshuancy.setBoundLabelLength(0);		
        this.contluoshuancy.setBoundLabelUnderline(true);		
        this.contluoshuancy.setVisible(true);
        // contrukong		
        this.contrukong.setBoundLabelText(resHelper.getString("contrukong.boundLabelText"));		
        this.contrukong.setBoundLabelLength(0);		
        this.contrukong.setBoundLabelUnderline(true);		
        this.contrukong.setVisible(true);
        // contzhongxingaork		
        this.contzhongxingaork.setBoundLabelText(resHelper.getString("contzhongxingaork.boundLabelText"));		
        this.contzhongxingaork.setBoundLabelLength(0);		
        this.contzhongxingaork.setBoundLabelUnderline(true);		
        this.contzhongxingaork.setVisible(true);
        // contrukongshuliang		
        this.contrukongshuliang.setBoundLabelText(resHelper.getString("contrukongshuliang.boundLabelText"));		
        this.contrukongshuliang.setBoundLabelLength(0);		
        this.contrukongshuliang.setBoundLabelUnderline(true);		
        this.contrukongshuliang.setVisible(true);
        // contluoshuanrk		
        this.contluoshuanrk.setBoundLabelText(resHelper.getString("contluoshuanrk.boundLabelText"));		
        this.contluoshuanrk.setBoundLabelLength(0);		
        this.contluoshuanrk.setBoundLabelUnderline(true);		
        this.contluoshuanrk.setVisible(true);
        // conttouguangkong		
        this.conttouguangkong.setBoundLabelText(resHelper.getString("conttouguangkong.boundLabelText"));		
        this.conttouguangkong.setBoundLabelLength(0);		
        this.conttouguangkong.setBoundLabelUnderline(true);		
        this.conttouguangkong.setVisible(true);
        // conttouguangkongsl		
        this.conttouguangkongsl.setBoundLabelText(resHelper.getString("conttouguangkongsl.boundLabelText"));		
        this.conttouguangkongsl.setBoundLabelLength(0);		
        this.conttouguangkongsl.setBoundLabelUnderline(true);		
        this.conttouguangkongsl.setVisible(true);
        // contluoshuantgk		
        this.contluoshuantgk.setBoundLabelText(resHelper.getString("contluoshuantgk.boundLabelText"));		
        this.contluoshuantgk.setBoundLabelLength(0);		
        this.contluoshuantgk.setBoundLabelUnderline(true);		
        this.contluoshuantgk.setVisible(true);
        // contyeweijifljkone		
        this.contyeweijifljkone.setBoundLabelText(resHelper.getString("contyeweijifljkone.boundLabelText"));		
        this.contyeweijifljkone.setBoundLabelLength(0);		
        this.contyeweijifljkone.setBoundLabelUnderline(true);		
        this.contyeweijifljkone.setVisible(true);
        // contyeweijijkfltwo		
        this.contyeweijijkfltwo.setBoundLabelText(resHelper.getString("contyeweijijkfltwo.boundLabelText"));		
        this.contyeweijijkfltwo.setBoundLabelLength(0);		
        this.contyeweijijkfltwo.setBoundLabelUnderline(true);		
        this.contyeweijijkfltwo.setVisible(true);
        // contyeweijils		
        this.contyeweijils.setBoundLabelText(resHelper.getString("contyeweijils.boundLabelText"));		
        this.contyeweijils.setBoundLabelLength(0);		
        this.contyeweijils.setBoundLabelUnderline(true);		
        this.contyeweijils.setVisible(true);
        // contyeweijiguige		
        this.contyeweijiguige.setBoundLabelText(resHelper.getString("contyeweijiguige.boundLabelText"));		
        this.contyeweijiguige.setBoundLabelLength(0);		
        this.contyeweijiguige.setBoundLabelUnderline(true);		
        this.contyeweijiguige.setVisible(true);
        // contyeweijicd		
        this.contyeweijicd.setBoundLabelText(resHelper.getString("contyeweijicd.boundLabelText"));		
        this.contyeweijicd.setBoundLabelLength(0);		
        this.contyeweijicd.setBoundLabelUnderline(true);		
        this.contyeweijicd.setVisible(true);
        // contyeweijizxgone		
        this.contyeweijizxgone.setBoundLabelText(resHelper.getString("contyeweijizxgone.boundLabelText"));		
        this.contyeweijizxgone.setBoundLabelLength(0);		
        this.contyeweijizxgone.setBoundLabelUnderline(true);		
        this.contyeweijizxgone.setVisible(true);
        // contjianzaoTime		
        this.contjianzaoTime.setBoundLabelText(resHelper.getString("contjianzaoTime.boundLabelText"));		
        this.contjianzaoTime.setBoundLabelLength(0);		
        this.contjianzaoTime.setBoundLabelUnderline(true);		
        this.contjianzaoTime.setVisible(true);
        // contshejidanwei		
        this.contshejidanwei.setBoundLabelText(resHelper.getString("contshejidanwei.boundLabelText"));		
        this.contshejidanwei.setBoundLabelLength(0);		
        this.contshejidanwei.setBoundLabelUnderline(true);		
        this.contshejidanwei.setVisible(true);
        // contshigongdanweione		
        this.contshigongdanweione.setBoundLabelText(resHelper.getString("contshigongdanweione.boundLabelText"));		
        this.contshigongdanweione.setBoundLabelLength(0);		
        this.contshigongdanweione.setBoundLabelUnderline(true);		
        this.contshigongdanweione.setVisible(true);
        // conttime		
        this.conttime.setBoundLabelText(resHelper.getString("conttime.boundLabelText"));		
        this.conttime.setBoundLabelLength(0);		
        this.conttime.setBoundLabelUnderline(true);		
        this.conttime.setVisible(true);
        // contguanrong		
        this.contguanrong.setBoundLabelText(resHelper.getString("contguanrong.boundLabelText"));		
        this.contguanrong.setBoundLabelLength(0);		
        this.contguanrong.setBoundLabelUnderline(true);		
        this.contguanrong.setVisible(true);
        // contdxtime		
        this.contdxtime.setBoundLabelText(resHelper.getString("contdxtime.boundLabelText"));		
        this.contdxtime.setBoundLabelLength(0);		
        this.contdxtime.setBoundLabelUnderline(true);		
        this.contdxtime.setVisible(true);
        // contdaxiushigongriqi		
        this.contdaxiushigongriqi.setBoundLabelText(resHelper.getString("contdaxiushigongriqi.boundLabelText"));		
        this.contdaxiushigongriqi.setBoundLabelLength(0);		
        this.contdaxiushigongriqi.setBoundLabelUnderline(true);		
        this.contdaxiushigongriqi.setVisible(true);
        // contshigongdanweitwo		
        this.contshigongdanweitwo.setBoundLabelText(resHelper.getString("contshigongdanweitwo.boundLabelText"));		
        this.contshigongdanweitwo.setBoundLabelLength(0);		
        this.contshigongdanweitwo.setBoundLabelUnderline(true);		
        this.contshigongdanweitwo.setVisible(true);
        // contdaxiuneirong		
        this.contdaxiuneirong.setBoundLabelText(resHelper.getString("contdaxiuneirong.boundLabelText"));		
        this.contdaxiuneirong.setBoundLabelLength(0);		
        this.contdaxiuneirong.setBoundLabelUnderline(true);		
        this.contdaxiuneirong.setVisible(true);
        // contjiaobanqijkflone		
        this.contjiaobanqijkflone.setBoundLabelText(resHelper.getString("contjiaobanqijkflone.boundLabelText"));		
        this.contjiaobanqijkflone.setBoundLabelLength(0);		
        this.contjiaobanqijkflone.setBoundLabelUnderline(true);		
        this.contjiaobanqijkflone.setVisible(true);
        // contjiaobanqijkfltwo		
        this.contjiaobanqijkfltwo.setBoundLabelText(resHelper.getString("contjiaobanqijkfltwo.boundLabelText"));		
        this.contjiaobanqijkfltwo.setBoundLabelLength(0);		
        this.contjiaobanqijkfltwo.setBoundLabelUnderline(true);		
        this.contjiaobanqijkfltwo.setVisible(true);
        // contjiaobanqils		
        this.contjiaobanqils.setBoundLabelText(resHelper.getString("contjiaobanqils.boundLabelText"));		
        this.contjiaobanqils.setBoundLabelLength(0);		
        this.contjiaobanqils.setBoundLabelUnderline(true);		
        this.contjiaobanqils.setVisible(true);
        // contjiaobanqiguige		
        this.contjiaobanqiguige.setBoundLabelText(resHelper.getString("contjiaobanqiguige.boundLabelText"));		
        this.contjiaobanqiguige.setBoundLabelLength(0);		
        this.contjiaobanqiguige.setBoundLabelUnderline(true);		
        this.contjiaobanqiguige.setVisible(true);
        // contjiaobanqicd		
        this.contjiaobanqicd.setBoundLabelText(resHelper.getString("contjiaobanqicd.boundLabelText"));		
        this.contjiaobanqicd.setBoundLabelLength(0);		
        this.contjiaobanqicd.setBoundLabelUnderline(true);		
        this.contjiaobanqicd.setVisible(true);
        // contjiaobanqizxg		
        this.contjiaobanqizxg.setBoundLabelText(resHelper.getString("contjiaobanqizxg.boundLabelText"));		
        this.contjiaobanqizxg.setBoundLabelLength(0);		
        this.contjiaobanqizxg.setBoundLabelUnderline(true);		
        this.contjiaobanqizxg.setVisible(true);
        // contpaomojkflone		
        this.contpaomojkflone.setBoundLabelText(resHelper.getString("contpaomojkflone.boundLabelText"));		
        this.contpaomojkflone.setBoundLabelLength(0);		
        this.contpaomojkflone.setBoundLabelUnderline(true);		
        this.contpaomojkflone.setVisible(true);
        // contpaomojkfltwo		
        this.contpaomojkfltwo.setBoundLabelText(resHelper.getString("contpaomojkfltwo.boundLabelText"));		
        this.contpaomojkfltwo.setBoundLabelLength(0);		
        this.contpaomojkfltwo.setBoundLabelUnderline(true);		
        this.contpaomojkfltwo.setVisible(true);
        // contpaomols		
        this.contpaomols.setBoundLabelText(resHelper.getString("contpaomols.boundLabelText"));		
        this.contpaomols.setBoundLabelLength(0);		
        this.contpaomols.setBoundLabelUnderline(true);		
        this.contpaomols.setVisible(true);
        // contpaomoguige		
        this.contpaomoguige.setBoundLabelText(resHelper.getString("contpaomoguige.boundLabelText"));		
        this.contpaomoguige.setBoundLabelLength(0);		
        this.contpaomoguige.setBoundLabelUnderline(true);		
        this.contpaomoguige.setVisible(true);
        // contpaomocd		
        this.contpaomocd.setBoundLabelText(resHelper.getString("contpaomocd.boundLabelText"));		
        this.contpaomocd.setBoundLabelLength(0);		
        this.contpaomocd.setBoundLabelUnderline(true);		
        this.contpaomocd.setVisible(true);
        // contpaomozxg		
        this.contpaomozxg.setBoundLabelText(resHelper.getString("contpaomozxg.boundLabelText"));		
        this.contpaomozxg.setBoundLabelLength(0);		
        this.contpaomozxg.setBoundLabelUnderline(true);		
        this.contpaomozxg.setVisible(true);
        // contyouliangguanjkflone		
        this.contyouliangguanjkflone.setBoundLabelText(resHelper.getString("contyouliangguanjkflone.boundLabelText"));		
        this.contyouliangguanjkflone.setBoundLabelLength(0);		
        this.contyouliangguanjkflone.setBoundLabelUnderline(true);		
        this.contyouliangguanjkflone.setVisible(true);
        // contyouliangguanjkfltwo		
        this.contyouliangguanjkfltwo.setBoundLabelText(resHelper.getString("contyouliangguanjkfltwo.boundLabelText"));		
        this.contyouliangguanjkfltwo.setBoundLabelLength(0);		
        this.contyouliangguanjkfltwo.setBoundLabelUnderline(true);		
        this.contyouliangguanjkfltwo.setVisible(true);
        // contyouliangguanls		
        this.contyouliangguanls.setBoundLabelText(resHelper.getString("contyouliangguanls.boundLabelText"));		
        this.contyouliangguanls.setBoundLabelLength(0);		
        this.contyouliangguanls.setBoundLabelUnderline(true);		
        this.contyouliangguanls.setVisible(true);
        // contyouliangguanguige		
        this.contyouliangguanguige.setBoundLabelText(resHelper.getString("contyouliangguanguige.boundLabelText"));		
        this.contyouliangguanguige.setBoundLabelLength(0);		
        this.contyouliangguanguige.setBoundLabelUnderline(true);		
        this.contyouliangguanguige.setVisible(true);
        // contyouliangguancd		
        this.contyouliangguancd.setBoundLabelText(resHelper.getString("contyouliangguancd.boundLabelText"));		
        this.contyouliangguancd.setBoundLabelLength(0);		
        this.contyouliangguancd.setBoundLabelUnderline(true);		
        this.contyouliangguancd.setVisible(true);
        // contyouliangguanzxg		
        this.contyouliangguanzxg.setBoundLabelText(resHelper.getString("contyouliangguanzxg.boundLabelText"));		
        this.contyouliangguanzxg.setBoundLabelLength(0);		
        this.contyouliangguanzxg.setBoundLabelUnderline(true);		
        this.contyouliangguanzxg.setVisible(true);
        // conttouguangkongone		
        this.conttouguangkongone.setBoundLabelText(resHelper.getString("conttouguangkongone.boundLabelText"));		
        this.conttouguangkongone.setBoundLabelLength(0);		
        this.conttouguangkongone.setBoundLabelUnderline(true);		
        this.conttouguangkongone.setVisible(true);
        // conttouguangkongthree		
        this.conttouguangkongthree.setBoundLabelText(resHelper.getString("conttouguangkongthree.boundLabelText"));		
        this.conttouguangkongthree.setBoundLabelLength(0);		
        this.conttouguangkongthree.setBoundLabelUnderline(true);		
        this.conttouguangkongthree.setVisible(true);
        // contqingsaokongone		
        this.contqingsaokongone.setBoundLabelText(resHelper.getString("contqingsaokongone.boundLabelText"));		
        this.contqingsaokongone.setBoundLabelLength(0);		
        this.contqingsaokongone.setBoundLabelUnderline(true);		
        this.contqingsaokongone.setVisible(true);
        // contqingsaokongtwo		
        this.contqingsaokongtwo.setBoundLabelText(resHelper.getString("contqingsaokongtwo.boundLabelText"));		
        this.contqingsaokongtwo.setBoundLabelLength(0);		
        this.contqingsaokongtwo.setBoundLabelUnderline(true);		
        this.contqingsaokongtwo.setVisible(true);
        // contqingsaokongthree		
        this.contqingsaokongthree.setBoundLabelText(resHelper.getString("contqingsaokongthree.boundLabelText"));		
        this.contqingsaokongthree.setBoundLabelLength(0);		
        this.contqingsaokongthree.setBoundLabelUnderline(true);		
        this.contqingsaokongthree.setVisible(true);
        // contcehoujilu		
        this.contcehoujilu.setBoundLabelText(resHelper.getString("contcehoujilu.boundLabelText"));		
        this.contcehoujilu.setBoundLabelLength(0);		
        this.contcehoujilu.setBoundLabelUnderline(true);		
        this.contcehoujilu.setVisible(true);
        // contwaiguan		
        this.contwaiguan.setBoundLabelText(resHelper.getString("contwaiguan.boundLabelText"));		
        this.contwaiguan.setBoundLabelLength(0);		
        this.contwaiguan.setBoundLabelUnderline(true);		
        this.contwaiguan.setVisible(true);
        // contjianzaofeiyong		
        this.contjianzaofeiyong.setBoundLabelText(resHelper.getString("contjianzaofeiyong.boundLabelText"));		
        this.contjianzaofeiyong.setBoundLabelLength(0);		
        this.contjianzaofeiyong.setBoundLabelUnderline(true);		
        this.contjianzaofeiyong.setVisible(true);
        // contqingsaokongfour		
        this.contqingsaokongfour.setBoundLabelText(resHelper.getString("contqingsaokongfour.boundLabelText"));		
        this.contqingsaokongfour.setBoundLabelLength(0);		
        this.contqingsaokongfour.setBoundLabelUnderline(true);		
        this.contqingsaokongfour.setVisible(true);
        // contqingsaokongfive		
        this.contqingsaokongfive.setBoundLabelText(resHelper.getString("contqingsaokongfive.boundLabelText"));		
        this.contqingsaokongfive.setBoundLabelLength(0);		
        this.contqingsaokongfive.setBoundLabelUnderline(true);		
        this.contqingsaokongfive.setVisible(true);
        // contluoshuanqs		
        this.contluoshuanqs.setBoundLabelText(resHelper.getString("contluoshuanqs.boundLabelText"));		
        this.contluoshuanqs.setBoundLabelLength(0);		
        this.contluoshuanqs.setBoundLabelUnderline(true);		
        this.contluoshuanqs.setVisible(true);
        // conthuxifajkflone		
        this.conthuxifajkflone.setBoundLabelText(resHelper.getString("conthuxifajkflone.boundLabelText"));		
        this.conthuxifajkflone.setBoundLabelLength(0);		
        this.conthuxifajkflone.setBoundLabelUnderline(true);		
        this.conthuxifajkflone.setVisible(true);
        // conthuxifajkfltwo		
        this.conthuxifajkfltwo.setBoundLabelText(resHelper.getString("conthuxifajkfltwo.boundLabelText"));		
        this.conthuxifajkfltwo.setBoundLabelLength(0);		
        this.conthuxifajkfltwo.setBoundLabelUnderline(true);		
        this.conthuxifajkfltwo.setVisible(true);
        // conthuxifals		
        this.conthuxifals.setBoundLabelText(resHelper.getString("conthuxifals.boundLabelText"));		
        this.conthuxifals.setBoundLabelLength(0);		
        this.conthuxifals.setBoundLabelUnderline(true);		
        this.conthuxifals.setVisible(true);
        // conthuxifagg		
        this.conthuxifagg.setBoundLabelText(resHelper.getString("conthuxifagg.boundLabelText"));		
        this.conthuxifagg.setBoundLabelLength(0);		
        this.conthuxifagg.setBoundLabelUnderline(true);		
        this.conthuxifagg.setVisible(true);
        // contbwcjkflone		
        this.contbwcjkflone.setBoundLabelText(resHelper.getString("contbwcjkflone.boundLabelText"));		
        this.contbwcjkflone.setBoundLabelLength(0);		
        this.contbwcjkflone.setBoundLabelUnderline(true);		
        this.contbwcjkflone.setVisible(true);
        // contbwcjkfltwo		
        this.contbwcjkfltwo.setBoundLabelText(resHelper.getString("contbwcjkfltwo.boundLabelText"));		
        this.contbwcjkfltwo.setBoundLabelLength(0);		
        this.contbwcjkfltwo.setBoundLabelUnderline(true);		
        this.contbwcjkfltwo.setVisible(true);
        // conthuxifaCd		
        this.conthuxifaCd.setBoundLabelText(resHelper.getString("conthuxifaCd.boundLabelText"));		
        this.conthuxifaCd.setBoundLabelLength(0);		
        this.conthuxifaCd.setBoundLabelUnderline(true);		
        this.conthuxifaCd.setVisible(true);
        // conthuxifazxg		
        this.conthuxifazxg.setBoundLabelText(resHelper.getString("conthuxifazxg.boundLabelText"));		
        this.conthuxifazxg.setBoundLabelLength(0);		
        this.conthuxifazxg.setBoundLabelUnderline(true);		
        this.conthuxifazxg.setVisible(true);
        // contbwcls		
        this.contbwcls.setBoundLabelText(resHelper.getString("contbwcls.boundLabelText"));		
        this.contbwcls.setBoundLabelLength(0);		
        this.contbwcls.setBoundLabelUnderline(true);		
        this.contbwcls.setVisible(true);
        // contbaowecenggg		
        this.contbaowecenggg.setBoundLabelText(resHelper.getString("contbaowecenggg.boundLabelText"));		
        this.contbaowecenggg.setBoundLabelLength(0);		
        this.contbaowecenggg.setBoundLabelUnderline(true);		
        this.contbaowecenggg.setVisible(true);
        // contbaowencengcd		
        this.contbaowencengcd.setBoundLabelText(resHelper.getString("contbaowencengcd.boundLabelText"));		
        this.contbaowencengcd.setBoundLabelLength(0);		
        this.contbaowencengcd.setBoundLabelUnderline(true);		
        this.contbaowencengcd.setVisible(true);
        // contbaowencengzxg		
        this.contbaowencengzxg.setBoundLabelText(resHelper.getString("contbaowencengzxg.boundLabelText"));		
        this.contbaowencengzxg.setBoundLabelLength(0);		
        this.contbaowencengzxg.setBoundLabelUnderline(true);		
        this.contbaowencengzxg.setVisible(true);
        // conttiebijkfaone		
        this.conttiebijkfaone.setBoundLabelText(resHelper.getString("conttiebijkfaone.boundLabelText"));		
        this.conttiebijkfaone.setBoundLabelLength(0);		
        this.conttiebijkfaone.setBoundLabelUnderline(true);		
        this.conttiebijkfaone.setVisible(true);
        // conttiepijkfltwo		
        this.conttiepijkfltwo.setBoundLabelText(resHelper.getString("conttiepijkfltwo.boundLabelText"));		
        this.conttiepijkfltwo.setBoundLabelLength(0);		
        this.conttiepijkfltwo.setBoundLabelUnderline(true);		
        this.conttiepijkfltwo.setVisible(true);
        // contteipils		
        this.contteipils.setBoundLabelText(resHelper.getString("contteipils.boundLabelText"));		
        this.contteipils.setBoundLabelLength(0);		
        this.contteipils.setBoundLabelUnderline(true);		
        this.contteipils.setVisible(true);
        // conttiepigg		
        this.conttiepigg.setBoundLabelText(resHelper.getString("conttiepigg.boundLabelText"));		
        this.conttiepigg.setBoundLabelLength(0);		
        this.conttiepigg.setBoundLabelUnderline(true);		
        this.conttiepigg.setVisible(true);
        // conttiepicd		
        this.conttiepicd.setBoundLabelText(resHelper.getString("conttiepicd.boundLabelText"));		
        this.conttiepicd.setBoundLabelLength(0);		
        this.conttiepicd.setBoundLabelUnderline(true);		
        this.conttiepicd.setVisible(true);
        // conttiepizxg		
        this.conttiepizxg.setBoundLabelText(resHelper.getString("conttiepizxg.boundLabelText"));		
        this.conttiepizxg.setBoundLabelLength(0);		
        this.conttiepizxg.setBoundLabelUnderline(true);		
        this.conttiepizxg.setVisible(true);
        // contdaoboguanjkflone		
        this.contdaoboguanjkflone.setBoundLabelText(resHelper.getString("contdaoboguanjkflone.boundLabelText"));		
        this.contdaoboguanjkflone.setBoundLabelLength(0);		
        this.contdaoboguanjkflone.setBoundLabelUnderline(true);		
        this.contdaoboguanjkflone.setVisible(true);
        // contdaoboguanjkfltwo		
        this.contdaoboguanjkfltwo.setBoundLabelText(resHelper.getString("contdaoboguanjkfltwo.boundLabelText"));		
        this.contdaoboguanjkfltwo.setBoundLabelLength(0);		
        this.contdaoboguanjkfltwo.setBoundLabelUnderline(true);		
        this.contdaoboguanjkfltwo.setVisible(true);
        // contdaoboguanls		
        this.contdaoboguanls.setBoundLabelText(resHelper.getString("contdaoboguanls.boundLabelText"));		
        this.contdaoboguanls.setBoundLabelLength(0);		
        this.contdaoboguanls.setBoundLabelUnderline(true);		
        this.contdaoboguanls.setVisible(true);
        // contdaoboguangg		
        this.contdaoboguangg.setBoundLabelText(resHelper.getString("contdaoboguangg.boundLabelText"));		
        this.contdaoboguangg.setBoundLabelLength(0);		
        this.contdaoboguangg.setBoundLabelUnderline(true);		
        this.contdaoboguangg.setVisible(true);
        // contdaoboguancd		
        this.contdaoboguancd.setBoundLabelText(resHelper.getString("contdaoboguancd.boundLabelText"));		
        this.contdaoboguancd.setBoundLabelLength(0);		
        this.contdaoboguancd.setBoundLabelUnderline(true);		
        this.contdaoboguancd.setVisible(true);
        // contdaoboguanzxg		
        this.contdaoboguanzxg.setBoundLabelText(resHelper.getString("contdaoboguanzxg.boundLabelText"));		
        this.contdaoboguanzxg.setBoundLabelLength(0);		
        this.contdaoboguanzxg.setBoundLabelUnderline(true);		
        this.contdaoboguanzxg.setVisible(true);
        // contjiareqijkfaone		
        this.contjiareqijkfaone.setBoundLabelText(resHelper.getString("contjiareqijkfaone.boundLabelText"));		
        this.contjiareqijkfaone.setBoundLabelLength(0);		
        this.contjiareqijkfaone.setBoundLabelUnderline(true);		
        this.contjiareqijkfaone.setVisible(true);
        // contjiareqijkfltwo		
        this.contjiareqijkfltwo.setBoundLabelText(resHelper.getString("contjiareqijkfltwo.boundLabelText"));		
        this.contjiareqijkfltwo.setBoundLabelLength(0);		
        this.contjiareqijkfltwo.setBoundLabelUnderline(true);		
        this.contjiareqijkfltwo.setVisible(true);
        // contjiarqichang		
        this.contjiarqichang.setBoundLabelText(resHelper.getString("contjiarqichang.boundLabelText"));		
        this.contjiarqichang.setBoundLabelLength(0);		
        this.contjiarqichang.setBoundLabelUnderline(true);		
        this.contjiarqichang.setVisible(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // kDSeparator77
        // txtNumber
        // txttankDiameter		
        this.txttankDiameter.setVisible(true);		
        this.txttankDiameter.setHorizontalAlignment(2);		
        this.txttankDiameter.setMaxLength(100);		
        this.txttankDiameter.setRequired(false);
        // txttankCapacity		
        this.txttankCapacity.setVisible(true);		
        this.txttankCapacity.setHorizontalAlignment(2);		
        this.txttankCapacity.setMaxLength(100);		
        this.txttankCapacity.setRequired(false);
        // txttankWallHeight		
        this.txttankWallHeight.setVisible(true);		
        this.txttankWallHeight.setHorizontalAlignment(2);		
        this.txttankWallHeight.setMaxLength(100);		
        this.txttankWallHeight.setRequired(false);
        // txtshejiyali		
        this.txtshejiyali.setVisible(true);		
        this.txtshejiyali.setHorizontalAlignment(2);		
        this.txtshejiyali.setMaxLength(100);		
        this.txtshejiyali.setRequired(false);
        // txtgongzuoyali		
        this.txtgongzuoyali.setVisible(true);		
        this.txtgongzuoyali.setHorizontalAlignment(2);		
        this.txtgongzuoyali.setMaxLength(100);		
        this.txtgongzuoyali.setRequired(false);
        // txtshejiwendu		
        this.txtshejiwendu.setVisible(true);		
        this.txtshejiwendu.setHorizontalAlignment(2);		
        this.txtshejiwendu.setMaxLength(100);		
        this.txtshejiwendu.setRequired(false);
        // txtgongzuowendu		
        this.txtgongzuowendu.setVisible(true);		
        this.txtgongzuowendu.setHorizontalAlignment(2);		
        this.txtgongzuowendu.setMaxLength(100);		
        this.txtgongzuowendu.setRequired(false);
        // txtfushiyuliang		
        this.txtfushiyuliang.setVisible(true);		
        this.txtfushiyuliang.setHorizontalAlignment(2);		
        this.txtfushiyuliang.setMaxLength(100);		
        this.txtfushiyuliang.setRequired(false);
        // txtshejimidu		
        this.txtshejimidu.setVisible(true);		
        this.txtshejimidu.setHorizontalAlignment(2);		
        this.txtshejimidu.setMaxLength(100);		
        this.txtshejimidu.setRequired(false);
        // txtanquangaodu		
        this.txtanquangaodu.setVisible(true);		
        this.txtanquangaodu.setHorizontalAlignment(2);		
        this.txtanquangaodu.setMaxLength(100);		
        this.txtanquangaodu.setRequired(false);
        // txthuanremianji		
        this.txthuanremianji.setVisible(true);		
        this.txthuanremianji.setHorizontalAlignment(2);		
        this.txthuanremianji.setMaxLength(100);		
        this.txthuanremianji.setRequired(false);
        // txtbibanchicun		
        this.txtbibanchicun.setVisible(true);		
        this.txtbibanchicun.setHorizontalAlignment(2);		
        this.txtbibanchicun.setMaxLength(225);		
        this.txtbibanchicun.setRequired(false);
        // txtguanbibanone		
        this.txtguanbibanone.setVisible(true);		
        this.txtguanbibanone.setHorizontalAlignment(2);		
        this.txtguanbibanone.setMaxLength(100);		
        this.txtguanbibanone.setRequired(false);
        // txtguanbibantwo		
        this.txtguanbibantwo.setVisible(true);		
        this.txtguanbibantwo.setHorizontalAlignment(2);		
        this.txtguanbibantwo.setMaxLength(100);		
        this.txtguanbibantwo.setRequired(false);
        // txtguandingbanone		
        this.txtguandingbanone.setVisible(true);		
        this.txtguandingbanone.setHorizontalAlignment(2);		
        this.txtguandingbanone.setMaxLength(100);		
        this.txtguandingbanone.setRequired(false);
        // txtguandingbantwo		
        this.txtguandingbantwo.setVisible(true);		
        this.txtguandingbantwo.setHorizontalAlignment(2);		
        this.txtguandingbantwo.setMaxLength(100);		
        this.txtguandingbantwo.setRequired(false);
        // txtguandingbanthree		
        this.txtguandingbanthree.setVisible(true);		
        this.txtguandingbanthree.setHorizontalAlignment(2);		
        this.txtguandingbanthree.setMaxLength(100);		
        this.txtguandingbanthree.setRequired(false);
        // txtbianyuanbanone		
        this.txtbianyuanbanone.setVisible(true);		
        this.txtbianyuanbanone.setHorizontalAlignment(2);		
        this.txtbianyuanbanone.setMaxLength(100);		
        this.txtbianyuanbanone.setRequired(false);
        // txtbianyuanbantwo		
        this.txtbianyuanbantwo.setVisible(true);		
        this.txtbianyuanbantwo.setHorizontalAlignment(2);		
        this.txtbianyuanbantwo.setMaxLength(100);		
        this.txtbianyuanbantwo.setRequired(false);
        // txtbianyuanbanthree		
        this.txtbianyuanbanthree.setVisible(true);		
        this.txtbianyuanbanthree.setHorizontalAlignment(2);		
        this.txtbianyuanbanthree.setMaxLength(100);		
        this.txtbianyuanbanthree.setRequired(false);
        // txtzhongfubanone		
        this.txtzhongfubanone.setVisible(true);		
        this.txtzhongfubanone.setHorizontalAlignment(2);		
        this.txtzhongfubanone.setMaxLength(100);		
        this.txtzhongfubanone.setRequired(false);
        // txtzhongfubantwo		
        this.txtzhongfubantwo.setVisible(true);		
        this.txtzhongfubantwo.setHorizontalAlignment(2);		
        this.txtzhongfubantwo.setMaxLength(100);		
        this.txtzhongfubantwo.setRequired(false);
        // txtzhongfubanthree		
        this.txtzhongfubanthree.setVisible(true);		
        this.txtzhongfubanthree.setHorizontalAlignment(2);		
        this.txtzhongfubanthree.setMaxLength(100);		
        this.txtzhongfubanthree.setRequired(false);
        // txtjingyouguan		
        this.txtjingyouguan.setVisible(true);		
        this.txtjingyouguan.setHorizontalAlignment(2);		
        this.txtjingyouguan.setMaxLength(100);		
        this.txtjingyouguan.setRequired(false);
        // txtzhongxingaojy		
        this.txtzhongxingaojy.setVisible(true);		
        this.txtzhongxingaojy.setHorizontalAlignment(2);		
        this.txtzhongxingaojy.setMaxLength(100);		
        this.txtzhongxingaojy.setRequired(false);
        // txtfalanjiekoujy		
        this.txtfalanjiekoujy.setVisible(true);		
        this.txtfalanjiekoujy.setHorizontalAlignment(2);		
        this.txtfalanjiekoujy.setMaxLength(100);		
        this.txtfalanjiekoujy.setRequired(false);
        // txtluoshuanjy		
        this.txtluoshuanjy.setVisible(true);		
        this.txtluoshuanjy.setHorizontalAlignment(2);		
        this.txtluoshuanjy.setMaxLength(100);		
        this.txtluoshuanjy.setRequired(false);
        // txtchuyouguan		
        this.txtchuyouguan.setVisible(true);		
        this.txtchuyouguan.setHorizontalAlignment(2);		
        this.txtchuyouguan.setMaxLength(100);		
        this.txtchuyouguan.setRequired(false);
        // txtzhongxingaocy		
        this.txtzhongxingaocy.setVisible(true);		
        this.txtzhongxingaocy.setHorizontalAlignment(2);		
        this.txtzhongxingaocy.setMaxLength(100);		
        this.txtzhongxingaocy.setRequired(false);
        // txtfalanjiekoucy		
        this.txtfalanjiekoucy.setVisible(true);		
        this.txtfalanjiekoucy.setHorizontalAlignment(2);		
        this.txtfalanjiekoucy.setMaxLength(100);		
        this.txtfalanjiekoucy.setRequired(false);
        // txtluoshuancy		
        this.txtluoshuancy.setVisible(true);		
        this.txtluoshuancy.setHorizontalAlignment(2);		
        this.txtluoshuancy.setMaxLength(100);		
        this.txtluoshuancy.setRequired(false);
        // txtrukong		
        this.txtrukong.setVisible(true);		
        this.txtrukong.setHorizontalAlignment(2);		
        this.txtrukong.setMaxLength(100);		
        this.txtrukong.setRequired(false);
        // txtzhongxingaork		
        this.txtzhongxingaork.setVisible(true);		
        this.txtzhongxingaork.setHorizontalAlignment(2);		
        this.txtzhongxingaork.setMaxLength(100);		
        this.txtzhongxingaork.setRequired(false);
        // txtrukongshuliang		
        this.txtrukongshuliang.setVisible(true);		
        this.txtrukongshuliang.setHorizontalAlignment(2);		
        this.txtrukongshuliang.setDataType(1);		
        this.txtrukongshuliang.setSupportedEmpty(true);		
        this.txtrukongshuliang.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtrukongshuliang.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtrukongshuliang.setPrecision(4);		
        this.txtrukongshuliang.setRequired(false);
        // txtluoshuanrk		
        this.txtluoshuanrk.setVisible(true);		
        this.txtluoshuanrk.setHorizontalAlignment(2);		
        this.txtluoshuanrk.setMaxLength(100);		
        this.txtluoshuanrk.setRequired(false);
        // txttouguangkong		
        this.txttouguangkong.setVisible(true);		
        this.txttouguangkong.setHorizontalAlignment(2);		
        this.txttouguangkong.setMaxLength(100);		
        this.txttouguangkong.setRequired(false);
        // txttouguangkongsl		
        this.txttouguangkongsl.setVisible(true);		
        this.txttouguangkongsl.setHorizontalAlignment(2);		
        this.txttouguangkongsl.setDataType(1);		
        this.txttouguangkongsl.setSupportedEmpty(true);		
        this.txttouguangkongsl.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txttouguangkongsl.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txttouguangkongsl.setPrecision(4);		
        this.txttouguangkongsl.setRequired(false);
        // txtluoshuantgk		
        this.txtluoshuantgk.setVisible(true);		
        this.txtluoshuantgk.setHorizontalAlignment(2);		
        this.txtluoshuantgk.setMaxLength(100);		
        this.txtluoshuantgk.setRequired(false);
        // txtyeweijifljkone		
        this.txtyeweijifljkone.setVisible(true);		
        this.txtyeweijifljkone.setHorizontalAlignment(2);		
        this.txtyeweijifljkone.setMaxLength(100);		
        this.txtyeweijifljkone.setRequired(false);
        // txtyeweijijkfltwo		
        this.txtyeweijijkfltwo.setVisible(true);		
        this.txtyeweijijkfltwo.setHorizontalAlignment(2);		
        this.txtyeweijijkfltwo.setMaxLength(100);		
        this.txtyeweijijkfltwo.setRequired(false);
        // txtyeweijils		
        this.txtyeweijils.setVisible(true);		
        this.txtyeweijils.setHorizontalAlignment(2);		
        this.txtyeweijils.setMaxLength(100);		
        this.txtyeweijils.setRequired(false);
        // txtyeweijiguige		
        this.txtyeweijiguige.setVisible(true);		
        this.txtyeweijiguige.setHorizontalAlignment(2);		
        this.txtyeweijiguige.setMaxLength(100);		
        this.txtyeweijiguige.setRequired(false);
        // txtyeweijicd		
        this.txtyeweijicd.setVisible(true);		
        this.txtyeweijicd.setHorizontalAlignment(2);		
        this.txtyeweijicd.setMaxLength(100);		
        this.txtyeweijicd.setRequired(false);
        // txtyeweijizxgone		
        this.txtyeweijizxgone.setVisible(true);		
        this.txtyeweijizxgone.setHorizontalAlignment(2);		
        this.txtyeweijizxgone.setMaxLength(100);		
        this.txtyeweijizxgone.setRequired(false);
        // pkjianzaoTime		
        this.pkjianzaoTime.setVisible(true);		
        this.pkjianzaoTime.setRequired(false);
        // txtshejidanwei		
        this.txtshejidanwei.setVisible(true);		
        this.txtshejidanwei.setHorizontalAlignment(2);		
        this.txtshejidanwei.setMaxLength(225);		
        this.txtshejidanwei.setRequired(false);
        // txtshigongdanweione		
        this.txtshigongdanweione.setVisible(true);		
        this.txtshigongdanweione.setHorizontalAlignment(2);		
        this.txtshigongdanweione.setMaxLength(225);		
        this.txtshigongdanweione.setRequired(false);
        // pktime		
        this.pktime.setVisible(true);		
        this.pktime.setRequired(false);
        // txtguanrong		
        this.txtguanrong.setVisible(true);		
        this.txtguanrong.setHorizontalAlignment(2);		
        this.txtguanrong.setMaxLength(100);		
        this.txtguanrong.setRequired(false);
        // pkdxtime		
        this.pkdxtime.setVisible(true);		
        this.pkdxtime.setRequired(false);
        // pkdaxiushigongriqi		
        this.pkdaxiushigongriqi.setVisible(true);		
        this.pkdaxiushigongriqi.setRequired(false);
        // txtshigongdanweitwo		
        this.txtshigongdanweitwo.setVisible(true);		
        this.txtshigongdanweitwo.setHorizontalAlignment(2);		
        this.txtshigongdanweitwo.setMaxLength(225);		
        this.txtshigongdanweitwo.setRequired(false);
        // scrollPanedaxiuneirong
        // txtdaxiuneirong		
        this.txtdaxiuneirong.setVisible(true);		
        this.txtdaxiuneirong.setRequired(false);		
        this.txtdaxiuneirong.setMaxLength(5000);
        // txtjiaobanqijkflone		
        this.txtjiaobanqijkflone.setVisible(true);		
        this.txtjiaobanqijkflone.setHorizontalAlignment(2);		
        this.txtjiaobanqijkflone.setMaxLength(100);		
        this.txtjiaobanqijkflone.setRequired(false);
        // txtjiaobanqijkfltwo		
        this.txtjiaobanqijkfltwo.setVisible(true);		
        this.txtjiaobanqijkfltwo.setHorizontalAlignment(2);		
        this.txtjiaobanqijkfltwo.setMaxLength(100);		
        this.txtjiaobanqijkfltwo.setRequired(false);
        // txtjiaobanqils		
        this.txtjiaobanqils.setVisible(true);		
        this.txtjiaobanqils.setHorizontalAlignment(2);		
        this.txtjiaobanqils.setMaxLength(100);		
        this.txtjiaobanqils.setRequired(false);
        // txtjiaobanqiguige		
        this.txtjiaobanqiguige.setVisible(true);		
        this.txtjiaobanqiguige.setHorizontalAlignment(2);		
        this.txtjiaobanqiguige.setMaxLength(100);		
        this.txtjiaobanqiguige.setRequired(false);
        // txtjiaobanqicd		
        this.txtjiaobanqicd.setVisible(true);		
        this.txtjiaobanqicd.setHorizontalAlignment(2);		
        this.txtjiaobanqicd.setMaxLength(100);		
        this.txtjiaobanqicd.setRequired(false);
        // txtjiaobanqizxg		
        this.txtjiaobanqizxg.setVisible(true);		
        this.txtjiaobanqizxg.setHorizontalAlignment(2);		
        this.txtjiaobanqizxg.setMaxLength(100);		
        this.txtjiaobanqizxg.setRequired(false);
        // txtpaomojkflone		
        this.txtpaomojkflone.setVisible(true);		
        this.txtpaomojkflone.setHorizontalAlignment(2);		
        this.txtpaomojkflone.setMaxLength(100);		
        this.txtpaomojkflone.setRequired(false);
        // txtpaomojkfltwo		
        this.txtpaomojkfltwo.setVisible(true);		
        this.txtpaomojkfltwo.setHorizontalAlignment(2);		
        this.txtpaomojkfltwo.setMaxLength(100);		
        this.txtpaomojkfltwo.setRequired(false);
        // txtpaomols		
        this.txtpaomols.setVisible(true);		
        this.txtpaomols.setHorizontalAlignment(2);		
        this.txtpaomols.setMaxLength(100);		
        this.txtpaomols.setRequired(false);
        // txtpaomoguige		
        this.txtpaomoguige.setVisible(true);		
        this.txtpaomoguige.setHorizontalAlignment(2);		
        this.txtpaomoguige.setMaxLength(100);		
        this.txtpaomoguige.setRequired(false);
        // txtpaomocd		
        this.txtpaomocd.setVisible(true);		
        this.txtpaomocd.setHorizontalAlignment(2);		
        this.txtpaomocd.setMaxLength(100);		
        this.txtpaomocd.setRequired(false);
        // txtpaomozxg		
        this.txtpaomozxg.setVisible(true);		
        this.txtpaomozxg.setHorizontalAlignment(2);		
        this.txtpaomozxg.setMaxLength(100);		
        this.txtpaomozxg.setRequired(false);
        // txtyouliangguanjkflone		
        this.txtyouliangguanjkflone.setVisible(true);		
        this.txtyouliangguanjkflone.setHorizontalAlignment(2);		
        this.txtyouliangguanjkflone.setMaxLength(100);		
        this.txtyouliangguanjkflone.setRequired(false);
        // txtyouliangguanjkfltwo		
        this.txtyouliangguanjkfltwo.setVisible(true);		
        this.txtyouliangguanjkfltwo.setHorizontalAlignment(2);		
        this.txtyouliangguanjkfltwo.setMaxLength(100);		
        this.txtyouliangguanjkfltwo.setRequired(false);
        // txtyouliangguanls		
        this.txtyouliangguanls.setVisible(true);		
        this.txtyouliangguanls.setHorizontalAlignment(2);		
        this.txtyouliangguanls.setMaxLength(100);		
        this.txtyouliangguanls.setRequired(false);
        // txtyouliangguanguige		
        this.txtyouliangguanguige.setVisible(true);		
        this.txtyouliangguanguige.setHorizontalAlignment(2);		
        this.txtyouliangguanguige.setMaxLength(100);		
        this.txtyouliangguanguige.setRequired(false);
        // txtyouliangguancd		
        this.txtyouliangguancd.setVisible(true);		
        this.txtyouliangguancd.setHorizontalAlignment(2);		
        this.txtyouliangguancd.setMaxLength(100);		
        this.txtyouliangguancd.setRequired(false);
        // txtyouliangguanzxg		
        this.txtyouliangguanzxg.setVisible(true);		
        this.txtyouliangguanzxg.setHorizontalAlignment(2);		
        this.txtyouliangguanzxg.setMaxLength(100);		
        this.txtyouliangguanzxg.setRequired(false);
        // txttouguangkongone		
        this.txttouguangkongone.setVisible(true);		
        this.txttouguangkongone.setHorizontalAlignment(2);		
        this.txttouguangkongone.setMaxLength(100);		
        this.txttouguangkongone.setRequired(false);
        // txttouguangkongthree		
        this.txttouguangkongthree.setVisible(true);		
        this.txttouguangkongthree.setHorizontalAlignment(2);		
        this.txttouguangkongthree.setMaxLength(100);		
        this.txttouguangkongthree.setRequired(false);
        // txtqingsaokongone		
        this.txtqingsaokongone.setVisible(true);		
        this.txtqingsaokongone.setHorizontalAlignment(2);		
        this.txtqingsaokongone.setMaxLength(100);		
        this.txtqingsaokongone.setRequired(false);
        // txtqingsaokongtwo		
        this.txtqingsaokongtwo.setVisible(true);		
        this.txtqingsaokongtwo.setHorizontalAlignment(2);		
        this.txtqingsaokongtwo.setMaxLength(100);		
        this.txtqingsaokongtwo.setRequired(false);
        // txtqingsaokongthree		
        this.txtqingsaokongthree.setVisible(true);		
        this.txtqingsaokongthree.setHorizontalAlignment(2);		
        this.txtqingsaokongthree.setMaxLength(100);		
        this.txtqingsaokongthree.setRequired(false);
        // txtcehoujilu		
        this.txtcehoujilu.setVisible(true);		
        this.txtcehoujilu.setHorizontalAlignment(2);		
        this.txtcehoujilu.setMaxLength(225);		
        this.txtcehoujilu.setRequired(false);
        // txtwaiguan		
        this.txtwaiguan.setVisible(true);		
        this.txtwaiguan.setHorizontalAlignment(2);		
        this.txtwaiguan.setMaxLength(225);		
        this.txtwaiguan.setRequired(false);
        // txtjianzaofeiyong		
        this.txtjianzaofeiyong.setVisible(true);		
        this.txtjianzaofeiyong.setHorizontalAlignment(2);		
        this.txtjianzaofeiyong.setDataType(1);		
        this.txtjianzaofeiyong.setSupportedEmpty(true);		
        this.txtjianzaofeiyong.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtjianzaofeiyong.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtjianzaofeiyong.setPrecision(2);		
        this.txtjianzaofeiyong.setRequired(false);
        // txtqingsaokongfour		
        this.txtqingsaokongfour.setVisible(true);		
        this.txtqingsaokongfour.setHorizontalAlignment(2);		
        this.txtqingsaokongfour.setMaxLength(100);		
        this.txtqingsaokongfour.setRequired(false);
        // txtqingsaokongfive		
        this.txtqingsaokongfive.setVisible(true);		
        this.txtqingsaokongfive.setHorizontalAlignment(2);		
        this.txtqingsaokongfive.setMaxLength(100);		
        this.txtqingsaokongfive.setRequired(false);
        // txtluoshuanqs		
        this.txtluoshuanqs.setVisible(true);		
        this.txtluoshuanqs.setHorizontalAlignment(2);		
        this.txtluoshuanqs.setMaxLength(100);		
        this.txtluoshuanqs.setRequired(false);
        // txthuxifajkflone		
        this.txthuxifajkflone.setVisible(true);		
        this.txthuxifajkflone.setHorizontalAlignment(2);		
        this.txthuxifajkflone.setMaxLength(100);		
        this.txthuxifajkflone.setRequired(false);
        // txthuxifajkfltwo		
        this.txthuxifajkfltwo.setVisible(true);		
        this.txthuxifajkfltwo.setHorizontalAlignment(2);		
        this.txthuxifajkfltwo.setMaxLength(100);		
        this.txthuxifajkfltwo.setRequired(false);
        // txthuxifals		
        this.txthuxifals.setVisible(true);		
        this.txthuxifals.setHorizontalAlignment(2);		
        this.txthuxifals.setMaxLength(100);		
        this.txthuxifals.setRequired(false);
        // txthuxifagg		
        this.txthuxifagg.setVisible(true);		
        this.txthuxifagg.setHorizontalAlignment(2);		
        this.txthuxifagg.setMaxLength(100);		
        this.txthuxifagg.setRequired(false);
        // txtbwcjkflone		
        this.txtbwcjkflone.setVisible(true);		
        this.txtbwcjkflone.setHorizontalAlignment(2);		
        this.txtbwcjkflone.setMaxLength(100);		
        this.txtbwcjkflone.setRequired(false);
        // txtbwcjkfltwo		
        this.txtbwcjkfltwo.setVisible(true);		
        this.txtbwcjkfltwo.setHorizontalAlignment(2);		
        this.txtbwcjkfltwo.setMaxLength(100);		
        this.txtbwcjkfltwo.setRequired(false);
        // txthuxifaCd		
        this.txthuxifaCd.setVisible(true);		
        this.txthuxifaCd.setHorizontalAlignment(2);		
        this.txthuxifaCd.setMaxLength(100);		
        this.txthuxifaCd.setRequired(false);
        // txthuxifazxg		
        this.txthuxifazxg.setVisible(true);		
        this.txthuxifazxg.setHorizontalAlignment(2);		
        this.txthuxifazxg.setMaxLength(100);		
        this.txthuxifazxg.setRequired(false);
        // txtbwcls		
        this.txtbwcls.setVisible(true);		
        this.txtbwcls.setHorizontalAlignment(2);		
        this.txtbwcls.setMaxLength(100);		
        this.txtbwcls.setRequired(false);
        // txtbaowecenggg		
        this.txtbaowecenggg.setVisible(true);		
        this.txtbaowecenggg.setHorizontalAlignment(2);		
        this.txtbaowecenggg.setMaxLength(100);		
        this.txtbaowecenggg.setRequired(false);
        // txtbaowencengcd		
        this.txtbaowencengcd.setVisible(true);		
        this.txtbaowencengcd.setHorizontalAlignment(2);		
        this.txtbaowencengcd.setMaxLength(100);		
        this.txtbaowencengcd.setRequired(false);
        // txtbaowencengzxg		
        this.txtbaowencengzxg.setVisible(true);		
        this.txtbaowencengzxg.setHorizontalAlignment(2);		
        this.txtbaowencengzxg.setMaxLength(100);		
        this.txtbaowencengzxg.setRequired(false);
        // txttiebijkfaone		
        this.txttiebijkfaone.setVisible(true);		
        this.txttiebijkfaone.setHorizontalAlignment(2);		
        this.txttiebijkfaone.setMaxLength(100);		
        this.txttiebijkfaone.setRequired(false);
        // txttiepijkfltwo		
        this.txttiepijkfltwo.setVisible(true);		
        this.txttiepijkfltwo.setHorizontalAlignment(2);		
        this.txttiepijkfltwo.setMaxLength(100);		
        this.txttiepijkfltwo.setRequired(false);
        // txtteipils		
        this.txtteipils.setVisible(true);		
        this.txtteipils.setHorizontalAlignment(2);		
        this.txtteipils.setMaxLength(100);		
        this.txtteipils.setRequired(false);
        // txttiepigg		
        this.txttiepigg.setVisible(true);		
        this.txttiepigg.setHorizontalAlignment(2);		
        this.txttiepigg.setMaxLength(100);		
        this.txttiepigg.setRequired(false);
        // txttiepicd		
        this.txttiepicd.setVisible(true);		
        this.txttiepicd.setHorizontalAlignment(2);		
        this.txttiepicd.setMaxLength(100);		
        this.txttiepicd.setRequired(false);
        // txttiepizxg		
        this.txttiepizxg.setVisible(true);		
        this.txttiepizxg.setHorizontalAlignment(2);		
        this.txttiepizxg.setMaxLength(100);		
        this.txttiepizxg.setRequired(false);
        // txtdaoboguanjkflone		
        this.txtdaoboguanjkflone.setVisible(true);		
        this.txtdaoboguanjkflone.setHorizontalAlignment(2);		
        this.txtdaoboguanjkflone.setMaxLength(100);		
        this.txtdaoboguanjkflone.setRequired(false);
        // txtdaoboguanjkfltwo		
        this.txtdaoboguanjkfltwo.setVisible(true);		
        this.txtdaoboguanjkfltwo.setHorizontalAlignment(2);		
        this.txtdaoboguanjkfltwo.setMaxLength(100);		
        this.txtdaoboguanjkfltwo.setRequired(false);
        // txtdaoboguanls		
        this.txtdaoboguanls.setVisible(true);		
        this.txtdaoboguanls.setHorizontalAlignment(2);		
        this.txtdaoboguanls.setMaxLength(100);		
        this.txtdaoboguanls.setRequired(false);
        // txtdaoboguangg		
        this.txtdaoboguangg.setVisible(true);		
        this.txtdaoboguangg.setHorizontalAlignment(2);		
        this.txtdaoboguangg.setMaxLength(100);		
        this.txtdaoboguangg.setRequired(false);
        // txtdaoboguancd		
        this.txtdaoboguancd.setVisible(true);		
        this.txtdaoboguancd.setHorizontalAlignment(2);		
        this.txtdaoboguancd.setMaxLength(100);		
        this.txtdaoboguancd.setRequired(false);
        // txtdaoboguanzxg		
        this.txtdaoboguanzxg.setVisible(true);		
        this.txtdaoboguanzxg.setHorizontalAlignment(2);		
        this.txtdaoboguanzxg.setMaxLength(100);		
        this.txtdaoboguanzxg.setRequired(false);
        // txtjiareqijkfaone		
        this.txtjiareqijkfaone.setVisible(true);		
        this.txtjiareqijkfaone.setHorizontalAlignment(2);		
        this.txtjiareqijkfaone.setMaxLength(100);		
        this.txtjiareqijkfaone.setRequired(false);
        // txtjiareqijkfltwo		
        this.txtjiareqijkfltwo.setVisible(true);		
        this.txtjiareqijkfltwo.setHorizontalAlignment(2);		
        this.txtjiareqijkfltwo.setMaxLength(100);		
        this.txtjiareqijkfltwo.setRequired(false);
        // txtjiarqichang		
        this.txtjiarqichang.setVisible(true);		
        this.txtjiarqichang.setHorizontalAlignment(2);		
        this.txtjiarqichang.setMaxLength(100);		
        this.txtjiarqichang.setRequired(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // pkBizDate
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txttankCapacity,txttankDiameter,txttankWallHeight,txtshejiyali,txtgongzuoyali,txtshejiwendu,txtgongzuowendu,txtfushiyuliang,txtshejimidu,txtanquangaodu,txthuanremianji,txtbibanchicun,txtguanbibanone,txtguanbibantwo,txtguandingbanone,txtguandingbantwo,txtguandingbanthree,txtbianyuanbanone,txtbianyuanbantwo,txtbianyuanbanthree,txtzhongfubanone,txtzhongfubantwo,txtzhongfubanthree,txtjingyouguan,txtzhongxingaojy,txtfalanjiekoujy,txtluoshuanjy,txtchuyouguan,txtzhongxingaocy,txtfalanjiekoucy,txtluoshuancy,txtrukong,txtzhongxingaork,txtrukongshuliang,txtluoshuanrk,txttouguangkong,txttouguangkongsl,txtluoshuantgk,txtyeweijifljkone,txtyeweijijkfltwo,txtyeweijils,txtyeweijiguige,txtyeweijicd,txtyeweijizxgone,pkjianzaoTime,txtshejidanwei,txtshigongdanweione,pktime,txtguanrong,pkdxtime,pkdaxiushigongriqi,txtshigongdanweitwo,txtdaxiuneirong,txtjiaobanqijkflone,txtjiaobanqijkfltwo,txtjiaobanqils,txtjiaobanqiguige,txtjiaobanqicd,txtjiaobanqizxg,txtpaomojkflone,txtpaomojkfltwo,txtpaomols,txtpaomoguige,txtpaomocd,txtpaomozxg,txtyouliangguanjkflone,txtyouliangguanjkfltwo,txtyouliangguanls,txtyouliangguanguige,txtyouliangguancd,txtyouliangguanzxg,txttouguangkongone,txttouguangkongthree,txtqingsaokongone,txtqingsaokongtwo,txtqingsaokongthree,txtcehoujilu,txtwaiguan,txtjianzaofeiyong,txtqingsaokongfour,txtqingsaokongfive,txtluoshuanqs,txthuxifajkflone,txthuxifajkfltwo,txthuxifals,txthuxifagg,txtbwcjkflone,txtbwcjkfltwo,txthuxifaCd,txthuxifazxg,txtbwcls,txtbaowecenggg,txtbaowencengcd,txtbaowencengzxg,txttiebijkfaone,txttiepijkfltwo,txtteipils,txttiepigg,txttiepicd,txttiepizxg,txtdaoboguanjkflone,txtdaoboguanjkfltwo,txtdaoboguanls,txtdaoboguangg,txtdaoboguancd,txtdaoboguanzxg,txtjiareqijkfaone,txtjiareqijkfltwo,txtjiarqichang}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 660, 934));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 660, 934));
        kDScrollPane1.setBounds(new Rectangle(2, 1, 657, 629));
        this.add(kDScrollPane1, new KDLayout.Constraints(2, 1, 657, 629, 0));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(null);        kDSeparator8.setBounds(new Rectangle(7, 55, 681, 10));
        kDPanel1.add(kDSeparator8, null);
        kDSeparator9.setBounds(new Rectangle(4, 32, 681, 10));
        kDPanel1.add(kDSeparator9, null);
        kDSeparator10.setBounds(new Rectangle(10, 79, 679, 10));
        kDPanel1.add(kDSeparator10, null);
        kDSeparator11.setBounds(new Rectangle(10, 101, 679, 10));
        kDPanel1.add(kDSeparator11, null);
        kDSeparator13.setBounds(new Rectangle(10, 124, 679, 10));
        kDPanel1.add(kDSeparator13, null);
        kDSeparator14.setBounds(new Rectangle(10, 147, 679, 10));
        kDPanel1.add(kDSeparator14, null);
        kDSeparator15.setBounds(new Rectangle(15, 170, 679, 10));
        kDPanel1.add(kDSeparator15, null);
        kDSeparator16.setBounds(new Rectangle(15, 193, 679, 10));
        kDPanel1.add(kDSeparator16, null);
        kDSeparator17.setBounds(new Rectangle(15, 216, 679, 10));
        kDPanel1.add(kDSeparator17, null);
        kDSeparator18.setBounds(new Rectangle(15, 239, 679, 10));
        kDPanel1.add(kDSeparator18, null);
        kDSeparator19.setBounds(new Rectangle(15, 262, 679, 10));
        kDPanel1.add(kDSeparator19, null);
        kDSeparator20.setBounds(new Rectangle(15, 285, 679, 10));
        kDPanel1.add(kDSeparator20, null);
        kDSeparator21.setBounds(new Rectangle(15, 308, 679, 10));
        kDPanel1.add(kDSeparator21, null);
        kDSeparator22.setBounds(new Rectangle(15, 331, 679, 10));
        kDPanel1.add(kDSeparator22, null);
        kDSeparator23.setBounds(new Rectangle(15, 354, 679, 10));
        kDPanel1.add(kDSeparator23, null);
        kDSeparator24.setBounds(new Rectangle(15, 377, 679, 10));
        kDPanel1.add(kDSeparator24, null);
        kDSeparator25.setBounds(new Rectangle(15, 400, 679, 10));
        kDPanel1.add(kDSeparator25, null);
        kDSeparator26.setBounds(new Rectangle(15, 423, 679, 10));
        kDPanel1.add(kDSeparator26, null);
        kDSeparator27.setBounds(new Rectangle(15, 446, 679, 10));
        kDPanel1.add(kDSeparator27, null);
        kDSeparator28.setBounds(new Rectangle(15, 469, 679, 10));
        kDPanel1.add(kDSeparator28, null);
        kDSeparator29.setBounds(new Rectangle(14, 492, 679, 10));
        kDPanel1.add(kDSeparator29, null);
        kDSeparator30.setBounds(new Rectangle(15, 515, 679, 10));
        kDPanel1.add(kDSeparator30, null);
        kDSeparator31.setBounds(new Rectangle(15, 538, 679, 10));
        kDPanel1.add(kDSeparator31, null);
        kDSeparator32.setBounds(new Rectangle(15, 561, 679, 10));
        kDPanel1.add(kDSeparator32, null);
        kDSeparator33.setBounds(new Rectangle(15, 584, 679, 10));
        kDPanel1.add(kDSeparator33, null);
        kDSeparator34.setBounds(new Rectangle(15, 607, 679, 10));
        kDPanel1.add(kDSeparator34, null);
        kDSeparator35.setBounds(new Rectangle(15, 630, 679, 10));
        kDPanel1.add(kDSeparator35, null);
        kDSeparator36.setBounds(new Rectangle(70, 653, 568, 10));
        kDPanel1.add(kDSeparator36, null);
        kDSeparator37.setBounds(new Rectangle(71, 676, 623, 10));
        kDPanel1.add(kDSeparator37, null);
        kDSeparator38.setBounds(new Rectangle(71, 699, 623, 10));
        kDPanel1.add(kDSeparator38, null);
        kDSeparator39.setBounds(new Rectangle(15, 724, 679, 10));
        kDPanel1.add(kDSeparator39, null);
        kDLabel1.setBounds(new Rectangle(221, 13, 210, 17));
        kDPanel1.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(14, 35, 53, 22));
        kDPanel1.add(kDLabel2, null);
        kDSeparator40.setBounds(new Rectangle(70, 33, 10, 69));
        kDPanel1.add(kDSeparator40, null);
        kDSeparator41.setBounds(new Rectangle(153, 33, 1, 69));
        kDPanel1.add(kDSeparator41, null);
        kDSeparator42.setBounds(new Rectangle(227, 33, 8, 69));
        kDPanel1.add(kDSeparator42, null);
        kDSeparator43.setBounds(new Rectangle(308, 33, 10, 69));
        kDPanel1.add(kDSeparator43, null);
        kDSeparator44.setBounds(new Rectangle(390, 33, 4, 69));
        kDPanel1.add(kDSeparator44, null);
        kDSeparator45.setBounds(new Rectangle(473, 33, 4, 69));
        kDPanel1.add(kDSeparator45, null);
        kDSeparator46.setBounds(new Rectangle(548, 33, 8, 69));
        kDPanel1.add(kDSeparator46, null);
        kDLabel3.setBounds(new Rectangle(165, 35, 56, 22));
        kDPanel1.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(327, 35, 54, 22));
        kDPanel1.add(kDLabel4, null);
        kDLabel5.setBounds(new Rectangle(487, 35, 55, 22));
        kDPanel1.add(kDLabel5, null);
        kDLabel6.setBounds(new Rectangle(14, 59, 52, 22));
        kDPanel1.add(kDLabel6, null);
        kDLabel7.setBounds(new Rectangle(14, 81, 49, 22));
        kDPanel1.add(kDLabel7, null);
        kDLabel8.setBounds(new Rectangle(165, 59, 55, 22));
        kDPanel1.add(kDLabel8, null);
        kDLabel9.setBounds(new Rectangle(165, 81, 57, 22));
        kDPanel1.add(kDLabel9, null);
        kDLabel10.setBounds(new Rectangle(327, 59, 52, 22));
        kDPanel1.add(kDLabel10, null);
        kDLabel11.setBounds(new Rectangle(327, 81, 50, 22));
        kDPanel1.add(kDLabel11, null);
        kDLabel12.setBounds(new Rectangle(487, 59, 58, 22));
        kDPanel1.add(kDLabel12, null);
        kDLabel13.setBounds(new Rectangle(487, 81, 50, 22));
        kDPanel1.add(kDLabel13, null);
        kDLabel14.setBounds(new Rectangle(14, 103, 55, 22));
        kDPanel1.add(kDLabel14, null);
        kDSeparator47.setBounds(new Rectangle(70, 124, 3, 70));
        kDPanel1.add(kDSeparator47, null);
        kDSeparator48.setBounds(new Rectangle(153, 171, 8, 23));
        kDPanel1.add(kDSeparator48, null);
        kDSeparator49.setBounds(new Rectangle(227, 148, 5, 45));
        kDPanel1.add(kDSeparator49, null);
        kDSeparator50.setBounds(new Rectangle(308, 148, 5, 45));
        kDPanel1.add(kDSeparator50, null);
        kDSeparator51.setBounds(new Rectangle(390, 148, 5, 45));
        kDPanel1.add(kDSeparator51, null);
        kDSeparator52.setBounds(new Rectangle(473, 148, 5, 45));
        kDPanel1.add(kDSeparator52, null);
        kDSeparator53.setBounds(new Rectangle(548, 148, 8, 45));
        kDPanel1.add(kDSeparator53, null);
        kDLabel15.setBounds(new Rectangle(14, 126, 52, 19));
        kDPanel1.add(kDLabel15, null);
        kDLabel16.setBounds(new Rectangle(14, 151, 45, 19));
        kDPanel1.add(kDLabel16, null);
        kDLabel17.setBounds(new Rectangle(14, 174, 39, 19));
        kDPanel1.add(kDLabel17, null);
        kDLabel18.setBounds(new Rectangle(327, 151, 56, 16));
        kDPanel1.add(kDLabel18, null);
        kDLabel19.setBounds(new Rectangle(327, 174, 55, 16));
        kDPanel1.add(kDLabel19, null);
        contNumber.setBounds(new Rectangle(73, 35, 78, 19));
        kDPanel1.add(contNumber, null);
        conttankDiameter.setBounds(new Rectangle(393, 35, 78, 19));
        kDPanel1.add(conttankDiameter, null);
        conttankCapacity.setBounds(new Rectangle(229, 35, 78, 19));
        kDPanel1.add(conttankCapacity, null);
        conttankWallHeight.setBounds(new Rectangle(551, 35, 78, 19));
        kDPanel1.add(conttankWallHeight, null);
        kDSeparator54.setBounds(new Rectangle(70, 217, 4, 115));
        kDPanel1.add(kDSeparator54, null);
        kDSeparator55.setBounds(new Rectangle(153, 217, 2, 115));
        kDPanel1.add(kDSeparator55, null);
        kDSeparator56.setBounds(new Rectangle(227, 217, 2, 115));
        kDPanel1.add(kDSeparator56, null);
        kDSeparator57.setBounds(new Rectangle(308, 217, 2, 115));
        kDPanel1.add(kDSeparator57, null);
        kDSeparator58.setBounds(new Rectangle(390, 217, 2, 115));
        kDPanel1.add(kDSeparator58, null);
        kDSeparator59.setBounds(new Rectangle(473, 217, 2, 115));
        kDPanel1.add(kDSeparator59, null);
        kDSeparator60.setBounds(new Rectangle(548, 217, 2, 115));
        kDPanel1.add(kDSeparator60, null);
        kDLabel20.setBounds(new Rectangle(14, 198, 45, 16));
        kDPanel1.add(kDLabel20, null);
        kDLabel21.setBounds(new Rectangle(14, 221, 45, 15));
        kDPanel1.add(kDLabel21, null);
        kDLabel22.setBounds(new Rectangle(14, 244, 46, 17));
        kDPanel1.add(kDLabel22, null);
        kDLabel23.setBounds(new Rectangle(14, 265, 40, 18));
        kDPanel1.add(kDLabel23, null);
        kDLabel24.setBounds(new Rectangle(14, 287, 41, 20));
        kDPanel1.add(kDLabel24, null);
        kDLabel25.setBounds(new Rectangle(14, 312, 41, 18));
        kDPanel1.add(kDLabel25, null);
        kDLabel26.setBounds(new Rectangle(165, 218, 46, 20));
        kDPanel1.add(kDLabel26, null);
        kDLabel27.setBounds(new Rectangle(165, 246, 41, 13));
        kDPanel1.add(kDLabel27, null);
        kDLabel28.setBounds(new Rectangle(165, 266, 42, 16));
        kDPanel1.add(kDLabel28, null);
        kDLabel29.setBounds(new Rectangle(327, 219, 48, 18));
        kDPanel1.add(kDLabel29, null);
        kDLabel30.setBounds(new Rectangle(327, 245, 51, 15));
        kDPanel1.add(kDLabel30, null);
        kDLabel31.setBounds(new Rectangle(327, 266, 57, 15));
        kDPanel1.add(kDLabel31, null);
        kDLabel32.setBounds(new Rectangle(323, 288, 65, 17));
        kDPanel1.add(kDLabel32, null);
        kDLabel33.setBounds(new Rectangle(487, 220, 33, 17));
        kDPanel1.add(kDLabel33, null);
        kDLabel34.setBounds(new Rectangle(487, 245, 40, 14));
        kDPanel1.add(kDLabel34, null);
        kDLabel35.setBounds(new Rectangle(487, 266, 35, 16));
        kDPanel1.add(kDLabel35, null);
        kDLabel36.setBounds(new Rectangle(487, 290, 33, 14));
        kDPanel1.add(kDLabel36, null);
        kDLabel37.setBounds(new Rectangle(487, 315, 37, 12));
        kDPanel1.add(kDLabel37, null);
        kDLabel38.setBounds(new Rectangle(17, 334, 50, 18));
        kDPanel1.add(kDLabel38, null);
        kDSeparator61.setBounds(new Rectangle(70, 355, 8, 92));
        kDPanel1.add(kDSeparator61, null);
        kDSeparator62.setBounds(new Rectangle(153, 377, 8, 420));
        kDPanel1.add(kDSeparator62, null);
        kDSeparator63.setBounds(new Rectangle(227, 355, 8, 253));
        kDPanel1.add(kDSeparator63, null);
        kDSeparator64.setBounds(new Rectangle(308, 355, 8, 114));
        kDPanel1.add(kDSeparator64, null);
        kDSeparator65.setBounds(new Rectangle(390, 355, 8, 114));
        kDPanel1.add(kDSeparator65, null);
        kDSeparator66.setBounds(new Rectangle(473, 355, 8, 114));
        kDPanel1.add(kDSeparator66, null);
        kDSeparator67.setBounds(new Rectangle(548, 355, 8, 231));
        kDPanel1.add(kDSeparator67, null);
        kDLabel39.setBounds(new Rectangle(17, 380, 45, 17));
        kDPanel1.add(kDLabel39, null);
        kDLabel40.setBounds(new Rectangle(14, 402, 49, 19));
        kDPanel1.add(kDLabel40, null);
        kDLabel41.setBounds(new Rectangle(12, 425, 61, 20));
        kDPanel1.add(kDLabel41, null);
        kDLabel42.setBounds(new Rectangle(14, 448, 50, 21));
        kDPanel1.add(kDLabel42, null);
        kDLabel43.setBounds(new Rectangle(14, 474, 44, 14));
        kDPanel1.add(kDLabel43, null);
        kDLabel44.setBounds(new Rectangle(14, 497, 41, 14));
        kDPanel1.add(kDLabel44, null);
        kDLabel45.setBounds(new Rectangle(14, 519, 44, 17));
        kDPanel1.add(kDLabel45, null);
        kDLabel46.setBounds(new Rectangle(14, 543, 53, 14));
        kDPanel1.add(kDLabel46, null);
        kDLabel47.setBounds(new Rectangle(14, 564, 53, 17));
        kDPanel1.add(kDLabel47, null);
        kDLabel48.setBounds(new Rectangle(14, 589, 54, 12));
        kDPanel1.add(kDLabel48, null);
        kDLabel49.setBounds(new Rectangle(14, 614, 51, 14));
        kDPanel1.add(kDLabel49, null);
        kDSeparator68.setBounds(new Rectangle(71, 749, 565, 8));
        kDPanel1.add(kDSeparator68, null);
        kDLabel50.setBounds(new Rectangle(14, 659, 52, 29));
        kDPanel1.add(kDLabel50, null);
        kDLabel51.setBounds(new Rectangle(14, 749, 118, 21));
        kDPanel1.add(kDLabel51, null);
        kDSeparator69.setBounds(new Rectangle(70, 447, 8, 348));
        kDPanel1.add(kDSeparator69, null);
        kDSeparator70.setBounds(new Rectangle(227, 725, 3, 26));
        kDPanel1.add(kDSeparator70, null);
        kDSeparator71.setBounds(new Rectangle(308, 725, 3, 26));
        kDPanel1.add(kDSeparator71, null);
        kDSeparator72.setBounds(new Rectangle(390, 725, 3, 26));
        kDPanel1.add(kDSeparator72, null);
        kDSeparator73.setBounds(new Rectangle(473, 725, 3, 26));
        kDPanel1.add(kDSeparator73, null);
        kDLabel52.setBounds(new Rectangle(26, 689, 30, 22));
        kDPanel1.add(kDLabel52, null);
        kDLabel53.setBounds(new Rectangle(74, 730, 57, 14));
        kDPanel1.add(kDLabel53, null);
        kDLabel54.setBounds(new Rectangle(74, 636, 58, 13));
        kDPanel1.add(kDLabel54, null);
        kDLabel55.setBounds(new Rectangle(74, 658, 55, 15));
        kDPanel1.add(kDLabel55, null);
        kDLabel56.setBounds(new Rectangle(74, 681, 55, 15));
        kDPanel1.add(kDLabel56, null);
        kDLabel57.setBounds(new Rectangle(74, 704, 48, 15));
        kDPanel1.add(kDLabel57, null);
        kDLabel58.setBounds(new Rectangle(74, 760, 64, 25));
        kDPanel1.add(kDLabel58, null);
        kDLabel59.setBounds(new Rectangle(235, 727, 64, 20));
        kDPanel1.add(kDLabel59, null);
        kDLabel60.setBounds(new Rectangle(401, 728, 56, 17));
        kDPanel1.add(kDLabel60, null);
        kDLabel61.setBounds(new Rectangle(127, 358, 56, 17));
        kDPanel1.add(kDLabel61, null);
        kDLabel62.setBounds(new Rectangle(230, 357, 64, 19));
        kDPanel1.add(kDLabel62, null);
        kDLabel63.setBounds(new Rectangle(313, 356, 56, 20));
        kDPanel1.add(kDLabel63, null);
        kDLabel64.setBounds(new Rectangle(393, 359, 66, 14));
        kDPanel1.add(kDLabel64, null);
        kDLabel65.setBounds(new Rectangle(476, 359, 53, 14));
        kDPanel1.add(kDLabel65, null);
        kDLabel66.setBounds(new Rectangle(160, 587, 61, 17));
        kDPanel1.add(kDLabel66, null);
        kDSeparator74.setBounds(new Rectangle(308, 493, 8, 91));
        kDPanel1.add(kDSeparator74, null);
        kDSeparator75.setBounds(new Rectangle(390, 493, 8, 115));
        kDPanel1.add(kDSeparator75, null);
        kDSeparator76.setBounds(new Rectangle(473, 493, 12, 114));
        kDPanel1.add(kDSeparator76, null);
        kDLabel67.setBounds(new Rectangle(401, 587, 56, 16));
        kDPanel1.add(kDLabel67, null);
        contshejiyali.setBounds(new Rectangle(73, 58, 78, 19));
        kDPanel1.add(contshejiyali, null);
        contgongzuoyali.setBounds(new Rectangle(229, 58, 78, 19));
        kDPanel1.add(contgongzuoyali, null);
        contshejiwendu.setBounds(new Rectangle(393, 58, 78, 19));
        kDPanel1.add(contshejiwendu, null);
        contgongzuowendu.setBounds(new Rectangle(551, 58, 78, 19));
        kDPanel1.add(contgongzuowendu, null);
        contfushiyuliang.setBounds(new Rectangle(73, 81, 78, 19));
        kDPanel1.add(contfushiyuliang, null);
        contshejimidu.setBounds(new Rectangle(229, 81, 78, 19));
        kDPanel1.add(contshejimidu, null);
        contanquangaodu.setBounds(new Rectangle(393, 81, 78, 19));
        kDPanel1.add(contanquangaodu, null);
        conthuanremianji.setBounds(new Rectangle(551, 81, 78, 19));
        kDPanel1.add(conthuanremianji, null);
        contbibanchicun.setBounds(new Rectangle(73, 126, 557, 19));
        kDPanel1.add(contbibanchicun, null);
        contguanbibanone.setBounds(new Rectangle(73, 150, 152, 19));
        kDPanel1.add(contguanbibanone, null);
        contguanbibantwo.setBounds(new Rectangle(229, 150, 78, 19));
        kDPanel1.add(contguanbibantwo, null);
        contguandingbanone.setBounds(new Rectangle(393, 150, 78, 19));
        kDPanel1.add(contguandingbanone, null);
        contguandingbantwo.setBounds(new Rectangle(477, 150, 69, 19));
        kDPanel1.add(contguandingbantwo, null);
        contguandingbanthree.setBounds(new Rectangle(551, 150, 78, 19));
        kDPanel1.add(contguandingbanthree, null);
        contbianyuanbanone.setBounds(new Rectangle(73, 173, 78, 19));
        kDPanel1.add(contbianyuanbanone, null);
        contbianyuanbantwo.setBounds(new Rectangle(156, 173, 70, 19));
        kDPanel1.add(contbianyuanbantwo, null);
        contbianyuanbanthree.setBounds(new Rectangle(229, 173, 78, 19));
        kDPanel1.add(contbianyuanbanthree, null);
        contzhongfubanone.setBounds(new Rectangle(393, 173, 78, 19));
        kDPanel1.add(contzhongfubanone, null);
        contzhongfubantwo.setBounds(new Rectangle(477, 173, 69, 19));
        kDPanel1.add(contzhongfubantwo, null);
        contzhongfubanthree.setBounds(new Rectangle(551, 173, 78, 19));
        kDPanel1.add(contzhongfubanthree, null);
        contjingyouguan.setBounds(new Rectangle(73, 219, 78, 19));
        kDPanel1.add(contjingyouguan, null);
        contzhongxingaojy.setBounds(new Rectangle(229, 219, 78, 19));
        kDPanel1.add(contzhongxingaojy, null);
        contfalanjiekoujy.setBounds(new Rectangle(393, 219, 78, 19));
        kDPanel1.add(contfalanjiekoujy, null);
        contluoshuanjy.setBounds(new Rectangle(551, 219, 78, 19));
        kDPanel1.add(contluoshuanjy, null);
        contchuyouguan.setBounds(new Rectangle(73, 242, 78, 19));
        kDPanel1.add(contchuyouguan, null);
        contzhongxingaocy.setBounds(new Rectangle(229, 242, 78, 19));
        kDPanel1.add(contzhongxingaocy, null);
        contfalanjiekoucy.setBounds(new Rectangle(393, 242, 78, 19));
        kDPanel1.add(contfalanjiekoucy, null);
        contluoshuancy.setBounds(new Rectangle(551, 242, 78, 19));
        kDPanel1.add(contluoshuancy, null);
        contrukong.setBounds(new Rectangle(73, 265, 78, 19));
        kDPanel1.add(contrukong, null);
        contzhongxingaork.setBounds(new Rectangle(229, 265, 78, 19));
        kDPanel1.add(contzhongxingaork, null);
        contrukongshuliang.setBounds(new Rectangle(393, 265, 78, 19));
        kDPanel1.add(contrukongshuliang, null);
        contluoshuanrk.setBounds(new Rectangle(551, 265, 78, 19));
        kDPanel1.add(contluoshuanrk, null);
        conttouguangkong.setBounds(new Rectangle(73, 288, 78, 19));
        kDPanel1.add(conttouguangkong, null);
        conttouguangkongsl.setBounds(new Rectangle(393, 288, 78, 19));
        kDPanel1.add(conttouguangkongsl, null);
        contluoshuantgk.setBounds(new Rectangle(551, 288, 78, 19));
        kDPanel1.add(contluoshuantgk, null);
        contyeweijifljkone.setBounds(new Rectangle(73, 380, 78, 19));
        kDPanel1.add(contyeweijifljkone, null);
        contyeweijijkfltwo.setBounds(new Rectangle(156, 380, 69, 19));
        kDPanel1.add(contyeweijijkfltwo, null);
        contyeweijils.setBounds(new Rectangle(229, 380, 78, 19));
        kDPanel1.add(contyeweijils, null);
        contyeweijiguige.setBounds(new Rectangle(310, 380, 78, 19));
        kDPanel1.add(contyeweijiguige, null);
        contyeweijicd.setBounds(new Rectangle(393, 380, 78, 19));
        kDPanel1.add(contyeweijicd, null);
        contyeweijizxgone.setBounds(new Rectangle(476, 380, 70, 19));
        kDPanel1.add(contyeweijizxgone, null);
        contjianzaoTime.setBounds(new Rectangle(73, 587, 78, 19));
        kDPanel1.add(contjianzaoTime, null);
        contshejidanwei.setBounds(new Rectangle(229, 587, 159, 19));
        kDPanel1.add(contshejidanwei, null);
        contshigongdanweione.setBounds(new Rectangle(476, 587, 152, 19));
        kDPanel1.add(contshigongdanweione, null);
        conttime.setBounds(new Rectangle(311, 633, 159, 19));
        kDPanel1.add(conttime, null);
        contguanrong.setBounds(new Rectangle(311, 656, 159, 19));
        kDPanel1.add(contguanrong, null);
        contdxtime.setBounds(new Rectangle(155, 728, 70, 19));
        kDPanel1.add(contdxtime, null);
        contdaxiushigongriqi.setBounds(new Rectangle(311, 728, 78, 19));
        kDPanel1.add(contdaxiushigongriqi, null);
        contshigongdanweitwo.setBounds(new Rectangle(476, 728, 152, 19));
        kDPanel1.add(contshigongdanweitwo, null);
        contdaxiuneirong.setBounds(new Rectangle(155, 752, 473, 40));
        kDPanel1.add(contdaxiuneirong, null);
        contjiaobanqijkflone.setBounds(new Rectangle(73, 403, 78, 19));
        kDPanel1.add(contjiaobanqijkflone, null);
        contjiaobanqijkfltwo.setBounds(new Rectangle(156, 403, 69, 19));
        kDPanel1.add(contjiaobanqijkfltwo, null);
        contjiaobanqils.setBounds(new Rectangle(229, 403, 78, 19));
        kDPanel1.add(contjiaobanqils, null);
        contjiaobanqiguige.setBounds(new Rectangle(310, 403, 78, 19));
        kDPanel1.add(contjiaobanqiguige, null);
        contjiaobanqicd.setBounds(new Rectangle(393, 403, 78, 19));
        kDPanel1.add(contjiaobanqicd, null);
        contjiaobanqizxg.setBounds(new Rectangle(476, 403, 70, 19));
        kDPanel1.add(contjiaobanqizxg, null);
        contpaomojkflone.setBounds(new Rectangle(73, 426, 78, 19));
        kDPanel1.add(contpaomojkflone, null);
        contpaomojkfltwo.setBounds(new Rectangle(156, 426, 69, 19));
        kDPanel1.add(contpaomojkfltwo, null);
        contpaomols.setBounds(new Rectangle(229, 426, 78, 19));
        kDPanel1.add(contpaomols, null);
        contpaomoguige.setBounds(new Rectangle(310, 426, 78, 19));
        kDPanel1.add(contpaomoguige, null);
        contpaomocd.setBounds(new Rectangle(393, 426, 78, 19));
        kDPanel1.add(contpaomocd, null);
        contpaomozxg.setBounds(new Rectangle(476, 426, 70, 19));
        kDPanel1.add(contpaomozxg, null);
        contyouliangguanjkflone.setBounds(new Rectangle(73, 518, 78, 19));
        kDPanel1.add(contyouliangguanjkflone, null);
        contyouliangguanjkfltwo.setBounds(new Rectangle(156, 518, 69, 19));
        kDPanel1.add(contyouliangguanjkfltwo, null);
        contyouliangguanls.setBounds(new Rectangle(229, 518, 78, 19));
        kDPanel1.add(contyouliangguanls, null);
        contyouliangguanguige.setBounds(new Rectangle(310, 518, 78, 19));
        kDPanel1.add(contyouliangguanguige, null);
        contyouliangguancd.setBounds(new Rectangle(393, 518, 78, 19));
        kDPanel1.add(contyouliangguancd, null);
        contyouliangguanzxg.setBounds(new Rectangle(476, 518, 70, 19));
        kDPanel1.add(contyouliangguanzxg, null);
        conttouguangkongone.setBounds(new Rectangle(156, 288, 70, 19));
        kDPanel1.add(conttouguangkongone, null);
        conttouguangkongthree.setBounds(new Rectangle(229, 288, 78, 19));
        kDPanel1.add(conttouguangkongthree, null);
        contqingsaokongone.setBounds(new Rectangle(73, 311, 78, 19));
        kDPanel1.add(contqingsaokongone, null);
        contqingsaokongtwo.setBounds(new Rectangle(156, 311, 70, 19));
        kDPanel1.add(contqingsaokongtwo, null);
        contqingsaokongthree.setBounds(new Rectangle(229, 311, 78, 19));
        kDPanel1.add(contqingsaokongthree, null);
        contcehoujilu.setBounds(new Rectangle(155, 679, 475, 19));
        kDPanel1.add(contcehoujilu, null);
        contwaiguan.setBounds(new Rectangle(155, 703, 475, 19));
        kDPanel1.add(contwaiguan, null);
        contjianzaofeiyong.setBounds(new Rectangle(73, 610, 78, 19));
        kDPanel1.add(contjianzaofeiyong, null);
        contqingsaokongfour.setBounds(new Rectangle(311, 311, 78, 19));
        kDPanel1.add(contqingsaokongfour, null);
        contqingsaokongfive.setBounds(new Rectangle(393, 311, 78, 19));
        kDPanel1.add(contqingsaokongfive, null);
        contluoshuanqs.setBounds(new Rectangle(551, 311, 78, 19));
        kDPanel1.add(contluoshuanqs, null);
        conthuxifajkflone.setBounds(new Rectangle(73, 449, 78, 19));
        kDPanel1.add(conthuxifajkflone, null);
        conthuxifajkfltwo.setBounds(new Rectangle(156, 449, 69, 19));
        kDPanel1.add(conthuxifajkfltwo, null);
        conthuxifals.setBounds(new Rectangle(229, 449, 78, 19));
        kDPanel1.add(conthuxifals, null);
        conthuxifagg.setBounds(new Rectangle(310, 449, 78, 19));
        kDPanel1.add(conthuxifagg, null);
        contbwcjkflone.setBounds(new Rectangle(73, 541, 78, 19));
        kDPanel1.add(contbwcjkflone, null);
        contbwcjkfltwo.setBounds(new Rectangle(156, 541, 69, 19));
        kDPanel1.add(contbwcjkfltwo, null);
        conthuxifaCd.setBounds(new Rectangle(393, 449, 78, 19));
        kDPanel1.add(conthuxifaCd, null);
        conthuxifazxg.setBounds(new Rectangle(476, 449, 70, 19));
        kDPanel1.add(conthuxifazxg, null);
        contbwcls.setBounds(new Rectangle(229, 541, 78, 19));
        kDPanel1.add(contbwcls, null);
        contbaowecenggg.setBounds(new Rectangle(310, 541, 78, 19));
        kDPanel1.add(contbaowecenggg, null);
        contbaowencengcd.setBounds(new Rectangle(393, 541, 78, 19));
        kDPanel1.add(contbaowencengcd, null);
        contbaowencengzxg.setBounds(new Rectangle(476, 541, 70, 19));
        kDPanel1.add(contbaowencengzxg, null);
        conttiebijkfaone.setBounds(new Rectangle(73, 564, 78, 19));
        kDPanel1.add(conttiebijkfaone, null);
        conttiepijkfltwo.setBounds(new Rectangle(156, 564, 69, 19));
        kDPanel1.add(conttiepijkfltwo, null);
        contteipils.setBounds(new Rectangle(229, 564, 78, 19));
        kDPanel1.add(contteipils, null);
        conttiepigg.setBounds(new Rectangle(310, 564, 78, 19));
        kDPanel1.add(conttiepigg, null);
        conttiepicd.setBounds(new Rectangle(393, 564, 78, 19));
        kDPanel1.add(conttiepicd, null);
        conttiepizxg.setBounds(new Rectangle(476, 564, 70, 19));
        kDPanel1.add(conttiepizxg, null);
        contdaoboguanjkflone.setBounds(new Rectangle(73, 495, 78, 19));
        kDPanel1.add(contdaoboguanjkflone, null);
        contdaoboguanjkfltwo.setBounds(new Rectangle(156, 495, 69, 19));
        kDPanel1.add(contdaoboguanjkfltwo, null);
        contdaoboguanls.setBounds(new Rectangle(229, 495, 78, 19));
        kDPanel1.add(contdaoboguanls, null);
        contdaoboguangg.setBounds(new Rectangle(310, 495, 78, 19));
        kDPanel1.add(contdaoboguangg, null);
        contdaoboguancd.setBounds(new Rectangle(393, 495, 78, 19));
        kDPanel1.add(contdaoboguancd, null);
        contdaoboguanzxg.setBounds(new Rectangle(476, 495, 70, 19));
        kDPanel1.add(contdaoboguanzxg, null);
        contjiareqijkfaone.setBounds(new Rectangle(73, 472, 78, 19));
        kDPanel1.add(contjiareqijkfaone, null);
        contjiareqijkfltwo.setBounds(new Rectangle(156, 472, 69, 19));
        kDPanel1.add(contjiareqijkfltwo, null);
        contjiarqichang.setBounds(new Rectangle(229, 472, 317, 19));
        kDPanel1.add(contjiarqichang, null);
        contAuditTime.setBounds(new Rectangle(361, 893, 270, 19));
        kDPanel1.add(contAuditTime, null);
        contBizStatus.setBounds(new Rectangle(972, 652, 270, 19));
        kDPanel1.add(contBizStatus, null);
        contCreator.setBounds(new Rectangle(13, 839, 270, 19));
        kDPanel1.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(361, 839, 270, 19));
        kDPanel1.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(13, 866, 270, 19));
        kDPanel1.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(361, 866, 270, 19));
        kDPanel1.add(contLastUpdateTime, null);
        contCU.setBounds(new Rectangle(13, 814, 270, 19));
        kDPanel1.add(contCU, null);
        contBizDate.setBounds(new Rectangle(970, 590, 270, 19));
        kDPanel1.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(975, 619, 270, 19));
        kDPanel1.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(13, 893, 270, 19));
        kDPanel1.add(contAuditor, null);
        contStatus.setBounds(new Rectangle(361, 813, 270, 19));
        kDPanel1.add(contStatus, null);
        kDSeparator77.setBounds(new Rectangle(15, 796, 648, 10));
        kDPanel1.add(kDSeparator77, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //conttankDiameter
        conttankDiameter.setBoundEditor(txttankDiameter);
        //conttankCapacity
        conttankCapacity.setBoundEditor(txttankCapacity);
        //conttankWallHeight
        conttankWallHeight.setBoundEditor(txttankWallHeight);
        //contshejiyali
        contshejiyali.setBoundEditor(txtshejiyali);
        //contgongzuoyali
        contgongzuoyali.setBoundEditor(txtgongzuoyali);
        //contshejiwendu
        contshejiwendu.setBoundEditor(txtshejiwendu);
        //contgongzuowendu
        contgongzuowendu.setBoundEditor(txtgongzuowendu);
        //contfushiyuliang
        contfushiyuliang.setBoundEditor(txtfushiyuliang);
        //contshejimidu
        contshejimidu.setBoundEditor(txtshejimidu);
        //contanquangaodu
        contanquangaodu.setBoundEditor(txtanquangaodu);
        //conthuanremianji
        conthuanremianji.setBoundEditor(txthuanremianji);
        //contbibanchicun
        contbibanchicun.setBoundEditor(txtbibanchicun);
        //contguanbibanone
        contguanbibanone.setBoundEditor(txtguanbibanone);
        //contguanbibantwo
        contguanbibantwo.setBoundEditor(txtguanbibantwo);
        //contguandingbanone
        contguandingbanone.setBoundEditor(txtguandingbanone);
        //contguandingbantwo
        contguandingbantwo.setBoundEditor(txtguandingbantwo);
        //contguandingbanthree
        contguandingbanthree.setBoundEditor(txtguandingbanthree);
        //contbianyuanbanone
        contbianyuanbanone.setBoundEditor(txtbianyuanbanone);
        //contbianyuanbantwo
        contbianyuanbantwo.setBoundEditor(txtbianyuanbantwo);
        //contbianyuanbanthree
        contbianyuanbanthree.setBoundEditor(txtbianyuanbanthree);
        //contzhongfubanone
        contzhongfubanone.setBoundEditor(txtzhongfubanone);
        //contzhongfubantwo
        contzhongfubantwo.setBoundEditor(txtzhongfubantwo);
        //contzhongfubanthree
        contzhongfubanthree.setBoundEditor(txtzhongfubanthree);
        //contjingyouguan
        contjingyouguan.setBoundEditor(txtjingyouguan);
        //contzhongxingaojy
        contzhongxingaojy.setBoundEditor(txtzhongxingaojy);
        //contfalanjiekoujy
        contfalanjiekoujy.setBoundEditor(txtfalanjiekoujy);
        //contluoshuanjy
        contluoshuanjy.setBoundEditor(txtluoshuanjy);
        //contchuyouguan
        contchuyouguan.setBoundEditor(txtchuyouguan);
        //contzhongxingaocy
        contzhongxingaocy.setBoundEditor(txtzhongxingaocy);
        //contfalanjiekoucy
        contfalanjiekoucy.setBoundEditor(txtfalanjiekoucy);
        //contluoshuancy
        contluoshuancy.setBoundEditor(txtluoshuancy);
        //contrukong
        contrukong.setBoundEditor(txtrukong);
        //contzhongxingaork
        contzhongxingaork.setBoundEditor(txtzhongxingaork);
        //contrukongshuliang
        contrukongshuliang.setBoundEditor(txtrukongshuliang);
        //contluoshuanrk
        contluoshuanrk.setBoundEditor(txtluoshuanrk);
        //conttouguangkong
        conttouguangkong.setBoundEditor(txttouguangkong);
        //conttouguangkongsl
        conttouguangkongsl.setBoundEditor(txttouguangkongsl);
        //contluoshuantgk
        contluoshuantgk.setBoundEditor(txtluoshuantgk);
        //contyeweijifljkone
        contyeweijifljkone.setBoundEditor(txtyeweijifljkone);
        //contyeweijijkfltwo
        contyeweijijkfltwo.setBoundEditor(txtyeweijijkfltwo);
        //contyeweijils
        contyeweijils.setBoundEditor(txtyeweijils);
        //contyeweijiguige
        contyeweijiguige.setBoundEditor(txtyeweijiguige);
        //contyeweijicd
        contyeweijicd.setBoundEditor(txtyeweijicd);
        //contyeweijizxgone
        contyeweijizxgone.setBoundEditor(txtyeweijizxgone);
        //contjianzaoTime
        contjianzaoTime.setBoundEditor(pkjianzaoTime);
        //contshejidanwei
        contshejidanwei.setBoundEditor(txtshejidanwei);
        //contshigongdanweione
        contshigongdanweione.setBoundEditor(txtshigongdanweione);
        //conttime
        conttime.setBoundEditor(pktime);
        //contguanrong
        contguanrong.setBoundEditor(txtguanrong);
        //contdxtime
        contdxtime.setBoundEditor(pkdxtime);
        //contdaxiushigongriqi
        contdaxiushigongriqi.setBoundEditor(pkdaxiushigongriqi);
        //contshigongdanweitwo
        contshigongdanweitwo.setBoundEditor(txtshigongdanweitwo);
        //contdaxiuneirong
        contdaxiuneirong.setBoundEditor(scrollPanedaxiuneirong);
        //scrollPanedaxiuneirong
        scrollPanedaxiuneirong.getViewport().add(txtdaxiuneirong, null);
        //contjiaobanqijkflone
        contjiaobanqijkflone.setBoundEditor(txtjiaobanqijkflone);
        //contjiaobanqijkfltwo
        contjiaobanqijkfltwo.setBoundEditor(txtjiaobanqijkfltwo);
        //contjiaobanqils
        contjiaobanqils.setBoundEditor(txtjiaobanqils);
        //contjiaobanqiguige
        contjiaobanqiguige.setBoundEditor(txtjiaobanqiguige);
        //contjiaobanqicd
        contjiaobanqicd.setBoundEditor(txtjiaobanqicd);
        //contjiaobanqizxg
        contjiaobanqizxg.setBoundEditor(txtjiaobanqizxg);
        //contpaomojkflone
        contpaomojkflone.setBoundEditor(txtpaomojkflone);
        //contpaomojkfltwo
        contpaomojkfltwo.setBoundEditor(txtpaomojkfltwo);
        //contpaomols
        contpaomols.setBoundEditor(txtpaomols);
        //contpaomoguige
        contpaomoguige.setBoundEditor(txtpaomoguige);
        //contpaomocd
        contpaomocd.setBoundEditor(txtpaomocd);
        //contpaomozxg
        contpaomozxg.setBoundEditor(txtpaomozxg);
        //contyouliangguanjkflone
        contyouliangguanjkflone.setBoundEditor(txtyouliangguanjkflone);
        //contyouliangguanjkfltwo
        contyouliangguanjkfltwo.setBoundEditor(txtyouliangguanjkfltwo);
        //contyouliangguanls
        contyouliangguanls.setBoundEditor(txtyouliangguanls);
        //contyouliangguanguige
        contyouliangguanguige.setBoundEditor(txtyouliangguanguige);
        //contyouliangguancd
        contyouliangguancd.setBoundEditor(txtyouliangguancd);
        //contyouliangguanzxg
        contyouliangguanzxg.setBoundEditor(txtyouliangguanzxg);
        //conttouguangkongone
        conttouguangkongone.setBoundEditor(txttouguangkongone);
        //conttouguangkongthree
        conttouguangkongthree.setBoundEditor(txttouguangkongthree);
        //contqingsaokongone
        contqingsaokongone.setBoundEditor(txtqingsaokongone);
        //contqingsaokongtwo
        contqingsaokongtwo.setBoundEditor(txtqingsaokongtwo);
        //contqingsaokongthree
        contqingsaokongthree.setBoundEditor(txtqingsaokongthree);
        //contcehoujilu
        contcehoujilu.setBoundEditor(txtcehoujilu);
        //contwaiguan
        contwaiguan.setBoundEditor(txtwaiguan);
        //contjianzaofeiyong
        contjianzaofeiyong.setBoundEditor(txtjianzaofeiyong);
        //contqingsaokongfour
        contqingsaokongfour.setBoundEditor(txtqingsaokongfour);
        //contqingsaokongfive
        contqingsaokongfive.setBoundEditor(txtqingsaokongfive);
        //contluoshuanqs
        contluoshuanqs.setBoundEditor(txtluoshuanqs);
        //conthuxifajkflone
        conthuxifajkflone.setBoundEditor(txthuxifajkflone);
        //conthuxifajkfltwo
        conthuxifajkfltwo.setBoundEditor(txthuxifajkfltwo);
        //conthuxifals
        conthuxifals.setBoundEditor(txthuxifals);
        //conthuxifagg
        conthuxifagg.setBoundEditor(txthuxifagg);
        //contbwcjkflone
        contbwcjkflone.setBoundEditor(txtbwcjkflone);
        //contbwcjkfltwo
        contbwcjkfltwo.setBoundEditor(txtbwcjkfltwo);
        //conthuxifaCd
        conthuxifaCd.setBoundEditor(txthuxifaCd);
        //conthuxifazxg
        conthuxifazxg.setBoundEditor(txthuxifazxg);
        //contbwcls
        contbwcls.setBoundEditor(txtbwcls);
        //contbaowecenggg
        contbaowecenggg.setBoundEditor(txtbaowecenggg);
        //contbaowencengcd
        contbaowencengcd.setBoundEditor(txtbaowencengcd);
        //contbaowencengzxg
        contbaowencengzxg.setBoundEditor(txtbaowencengzxg);
        //conttiebijkfaone
        conttiebijkfaone.setBoundEditor(txttiebijkfaone);
        //conttiepijkfltwo
        conttiepijkfltwo.setBoundEditor(txttiepijkfltwo);
        //contteipils
        contteipils.setBoundEditor(txtteipils);
        //conttiepigg
        conttiepigg.setBoundEditor(txttiepigg);
        //conttiepicd
        conttiepicd.setBoundEditor(txttiepicd);
        //conttiepizxg
        conttiepizxg.setBoundEditor(txttiepizxg);
        //contdaoboguanjkflone
        contdaoboguanjkflone.setBoundEditor(txtdaoboguanjkflone);
        //contdaoboguanjkfltwo
        contdaoboguanjkfltwo.setBoundEditor(txtdaoboguanjkfltwo);
        //contdaoboguanls
        contdaoboguanls.setBoundEditor(txtdaoboguanls);
        //contdaoboguangg
        contdaoboguangg.setBoundEditor(txtdaoboguangg);
        //contdaoboguancd
        contdaoboguancd.setBoundEditor(txtdaoboguancd);
        //contdaoboguanzxg
        contdaoboguanzxg.setBoundEditor(txtdaoboguanzxg);
        //contjiareqijkfaone
        contjiareqijkfaone.setBoundEditor(txtjiareqijkfaone);
        //contjiareqijkfltwo
        contjiareqijkfltwo.setBoundEditor(txtjiareqijkfltwo);
        //contjiarqichang
        contjiarqichang.setBoundEditor(txtjiarqichang);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contStatus
        contStatus.setBoundEditor(comboStatus);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("tankDiameter", String.class, this.txttankDiameter, "text");
		dataBinder.registerBinding("tankCapacity", String.class, this.txttankCapacity, "text");
		dataBinder.registerBinding("tankWallHeight", String.class, this.txttankWallHeight, "text");
		dataBinder.registerBinding("shejiyali", String.class, this.txtshejiyali, "text");
		dataBinder.registerBinding("gongzuoyali", String.class, this.txtgongzuoyali, "text");
		dataBinder.registerBinding("shejiwendu", String.class, this.txtshejiwendu, "text");
		dataBinder.registerBinding("gongzuowendu", String.class, this.txtgongzuowendu, "text");
		dataBinder.registerBinding("fushiyuliang", String.class, this.txtfushiyuliang, "text");
		dataBinder.registerBinding("shejimidu", String.class, this.txtshejimidu, "text");
		dataBinder.registerBinding("anquangaodu", String.class, this.txtanquangaodu, "text");
		dataBinder.registerBinding("huanremianji", String.class, this.txthuanremianji, "text");
		dataBinder.registerBinding("bibanchicun", String.class, this.txtbibanchicun, "text");
		dataBinder.registerBinding("guanbibanone", String.class, this.txtguanbibanone, "text");
		dataBinder.registerBinding("guanbibantwo", String.class, this.txtguanbibantwo, "text");
		dataBinder.registerBinding("guandingbanone", String.class, this.txtguandingbanone, "text");
		dataBinder.registerBinding("guandingbantwo", String.class, this.txtguandingbantwo, "text");
		dataBinder.registerBinding("guandingbanthree", String.class, this.txtguandingbanthree, "text");
		dataBinder.registerBinding("bianyuanbanone", String.class, this.txtbianyuanbanone, "text");
		dataBinder.registerBinding("bianyuanbantwo", String.class, this.txtbianyuanbantwo, "text");
		dataBinder.registerBinding("bianyuanbanthree", String.class, this.txtbianyuanbanthree, "text");
		dataBinder.registerBinding("zhongfubanone", String.class, this.txtzhongfubanone, "text");
		dataBinder.registerBinding("zhongfubantwo", String.class, this.txtzhongfubantwo, "text");
		dataBinder.registerBinding("zhongfubanthree", String.class, this.txtzhongfubanthree, "text");
		dataBinder.registerBinding("jingyouguan", String.class, this.txtjingyouguan, "text");
		dataBinder.registerBinding("zhongxingaojy", String.class, this.txtzhongxingaojy, "text");
		dataBinder.registerBinding("falanjiekoujy", String.class, this.txtfalanjiekoujy, "text");
		dataBinder.registerBinding("luoshuanjy", String.class, this.txtluoshuanjy, "text");
		dataBinder.registerBinding("chuyouguan", String.class, this.txtchuyouguan, "text");
		dataBinder.registerBinding("zhongxingaocy", String.class, this.txtzhongxingaocy, "text");
		dataBinder.registerBinding("falanjiekoucy", String.class, this.txtfalanjiekoucy, "text");
		dataBinder.registerBinding("luoshuancy", String.class, this.txtluoshuancy, "text");
		dataBinder.registerBinding("rukong", String.class, this.txtrukong, "text");
		dataBinder.registerBinding("zhongxingaork", String.class, this.txtzhongxingaork, "text");
		dataBinder.registerBinding("rukongshuliang", java.math.BigDecimal.class, this.txtrukongshuliang, "value");
		dataBinder.registerBinding("luoshuanrk", String.class, this.txtluoshuanrk, "text");
		dataBinder.registerBinding("touguangkong", String.class, this.txttouguangkong, "text");
		dataBinder.registerBinding("touguangkongsl", java.math.BigDecimal.class, this.txttouguangkongsl, "value");
		dataBinder.registerBinding("luoshuantgk", String.class, this.txtluoshuantgk, "text");
		dataBinder.registerBinding("yeweijifljkone", String.class, this.txtyeweijifljkone, "text");
		dataBinder.registerBinding("yeweijijkfltwo", String.class, this.txtyeweijijkfltwo, "text");
		dataBinder.registerBinding("yeweijils", String.class, this.txtyeweijils, "text");
		dataBinder.registerBinding("yeweijiguige", String.class, this.txtyeweijiguige, "text");
		dataBinder.registerBinding("yeweijicd", String.class, this.txtyeweijicd, "text");
		dataBinder.registerBinding("yeweijizxgone", String.class, this.txtyeweijizxgone, "text");
		dataBinder.registerBinding("jianzaoTime", java.util.Date.class, this.pkjianzaoTime, "value");
		dataBinder.registerBinding("shejidanwei", String.class, this.txtshejidanwei, "text");
		dataBinder.registerBinding("shigongdanweione", String.class, this.txtshigongdanweione, "text");
		dataBinder.registerBinding("time", java.util.Date.class, this.pktime, "value");
		dataBinder.registerBinding("guanrong", String.class, this.txtguanrong, "text");
		dataBinder.registerBinding("dxtime", java.util.Date.class, this.pkdxtime, "value");
		dataBinder.registerBinding("daxiushigongriqi", java.util.Date.class, this.pkdaxiushigongriqi, "value");
		dataBinder.registerBinding("shigongdanweitwo", String.class, this.txtshigongdanweitwo, "text");
		dataBinder.registerBinding("daxiuneirong", String.class, this.txtdaxiuneirong, "text");
		dataBinder.registerBinding("jiaobanqijkflone", String.class, this.txtjiaobanqijkflone, "text");
		dataBinder.registerBinding("jiaobanqijkfltwo", String.class, this.txtjiaobanqijkfltwo, "text");
		dataBinder.registerBinding("jiaobanqils", String.class, this.txtjiaobanqils, "text");
		dataBinder.registerBinding("jiaobanqiguige", String.class, this.txtjiaobanqiguige, "text");
		dataBinder.registerBinding("jiaobanqicd", String.class, this.txtjiaobanqicd, "text");
		dataBinder.registerBinding("jiaobanqizxg", String.class, this.txtjiaobanqizxg, "text");
		dataBinder.registerBinding("paomojkflone", String.class, this.txtpaomojkflone, "text");
		dataBinder.registerBinding("paomojkfltwo", String.class, this.txtpaomojkfltwo, "text");
		dataBinder.registerBinding("paomols", String.class, this.txtpaomols, "text");
		dataBinder.registerBinding("paomoguige", String.class, this.txtpaomoguige, "text");
		dataBinder.registerBinding("paomocd", String.class, this.txtpaomocd, "text");
		dataBinder.registerBinding("paomozxg", String.class, this.txtpaomozxg, "text");
		dataBinder.registerBinding("youliangguanjkflone", String.class, this.txtyouliangguanjkflone, "text");
		dataBinder.registerBinding("youliangguanjkfltwo", String.class, this.txtyouliangguanjkfltwo, "text");
		dataBinder.registerBinding("youliangguanls", String.class, this.txtyouliangguanls, "text");
		dataBinder.registerBinding("youliangguanguige", String.class, this.txtyouliangguanguige, "text");
		dataBinder.registerBinding("youliangguancd", String.class, this.txtyouliangguancd, "text");
		dataBinder.registerBinding("youliangguanzxg", String.class, this.txtyouliangguanzxg, "text");
		dataBinder.registerBinding("touguangkongone", String.class, this.txttouguangkongone, "text");
		dataBinder.registerBinding("touguangkongthree", String.class, this.txttouguangkongthree, "text");
		dataBinder.registerBinding("qingsaokongone", String.class, this.txtqingsaokongone, "text");
		dataBinder.registerBinding("qingsaokongtwo", String.class, this.txtqingsaokongtwo, "text");
		dataBinder.registerBinding("qingsaokongthree", String.class, this.txtqingsaokongthree, "text");
		dataBinder.registerBinding("cehoujilu", String.class, this.txtcehoujilu, "text");
		dataBinder.registerBinding("waiguan", String.class, this.txtwaiguan, "text");
		dataBinder.registerBinding("jianzaofeiyong", java.math.BigDecimal.class, this.txtjianzaofeiyong, "value");
		dataBinder.registerBinding("qingsaokongfour", String.class, this.txtqingsaokongfour, "text");
		dataBinder.registerBinding("qingsaokongfive", String.class, this.txtqingsaokongfive, "text");
		dataBinder.registerBinding("luoshuanqs", String.class, this.txtluoshuanqs, "text");
		dataBinder.registerBinding("huxifajkflone", String.class, this.txthuxifajkflone, "text");
		dataBinder.registerBinding("huxifajkfltwo", String.class, this.txthuxifajkfltwo, "text");
		dataBinder.registerBinding("huxifals", String.class, this.txthuxifals, "text");
		dataBinder.registerBinding("huxifagg", String.class, this.txthuxifagg, "text");
		dataBinder.registerBinding("bwcjkflone", String.class, this.txtbwcjkflone, "text");
		dataBinder.registerBinding("bwcjkfltwo", String.class, this.txtbwcjkfltwo, "text");
		dataBinder.registerBinding("huxifaCd", String.class, this.txthuxifaCd, "text");
		dataBinder.registerBinding("huxifazxg", String.class, this.txthuxifazxg, "text");
		dataBinder.registerBinding("bwcls", String.class, this.txtbwcls, "text");
		dataBinder.registerBinding("baowecenggg", String.class, this.txtbaowecenggg, "text");
		dataBinder.registerBinding("baowencengcd", String.class, this.txtbaowencengcd, "text");
		dataBinder.registerBinding("baowencengzxg", String.class, this.txtbaowencengzxg, "text");
		dataBinder.registerBinding("tiebijkfaone", String.class, this.txttiebijkfaone, "text");
		dataBinder.registerBinding("tiepijkfltwo", String.class, this.txttiepijkfltwo, "text");
		dataBinder.registerBinding("teipils", String.class, this.txtteipils, "text");
		dataBinder.registerBinding("tiepigg", String.class, this.txttiepigg, "text");
		dataBinder.registerBinding("tiepicd", String.class, this.txttiepicd, "text");
		dataBinder.registerBinding("tiepizxg", String.class, this.txttiepizxg, "text");
		dataBinder.registerBinding("daoboguanjkflone", String.class, this.txtdaoboguanjkflone, "text");
		dataBinder.registerBinding("daoboguanjkfltwo", String.class, this.txtdaoboguanjkfltwo, "text");
		dataBinder.registerBinding("daoboguanls", String.class, this.txtdaoboguanls, "text");
		dataBinder.registerBinding("daoboguangg", String.class, this.txtdaoboguangg, "text");
		dataBinder.registerBinding("daoboguancd", String.class, this.txtdaoboguancd, "text");
		dataBinder.registerBinding("daoboguanzxg", String.class, this.txtdaoboguanzxg, "text");
		dataBinder.registerBinding("jiareqijkfaone", String.class, this.txtjiareqijkfaone, "text");
		dataBinder.registerBinding("jiareqijkfltwo", String.class, this.txtjiareqijkfltwo, "text");
		dataBinder.registerBinding("jiarqichang", String.class, this.txtjiarqichang, "text");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.record.app.TankTechnicalEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txttankCapacity.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.record.TankTechnicalInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tankDiameter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tankCapacity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tankWallHeight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shejiyali", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gongzuoyali", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shejiwendu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gongzuowendu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fushiyuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shejimidu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("anquangaodu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huanremianji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bibanchicun", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guanbibanone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guanbibantwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandingbanone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandingbantwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandingbanthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bianyuanbanone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bianyuanbantwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bianyuanbanthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongfubanone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongfubantwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongfubanthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jingyouguan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongxingaojy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("falanjiekoujy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("luoshuanjy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chuyouguan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongxingaocy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("falanjiekoucy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("luoshuancy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rukong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhongxingaork", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rukongshuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("luoshuanrk", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("touguangkong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("touguangkongsl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("luoshuantgk", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijifljkone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijijkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijils", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijiguige", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijicd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeweijizxgone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jianzaoTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shejidanwei", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shigongdanweione", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("time", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guanrong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dxtime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daxiushigongriqi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shigongdanweitwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daxiuneirong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqijkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqijkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqils", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqiguige", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqicd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaobanqizxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomojkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomojkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomols", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomoguige", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomocd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paomozxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguanjkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguanjkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguanls", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguanguige", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguancd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youliangguanzxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("touguangkongone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("touguangkongthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qingsaokongone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qingsaokongtwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qingsaokongthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cehoujilu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("waiguan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jianzaofeiyong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qingsaokongfour", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qingsaokongfive", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("luoshuanqs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifajkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifajkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifals", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifagg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bwcjkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bwcjkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifaCd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("huxifazxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bwcls", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baowecenggg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baowencengcd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baowencengzxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tiebijkfaone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tiepijkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("teipils", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tiepigg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tiepicd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tiepizxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguanjkflone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguanjkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguanls", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguangg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguancd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daoboguanzxg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiareqijkfaone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiareqijkfltwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiarqichang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("tankDiameter"));
        sic.add(new SelectorItemInfo("tankCapacity"));
        sic.add(new SelectorItemInfo("tankWallHeight"));
        sic.add(new SelectorItemInfo("shejiyali"));
        sic.add(new SelectorItemInfo("gongzuoyali"));
        sic.add(new SelectorItemInfo("shejiwendu"));
        sic.add(new SelectorItemInfo("gongzuowendu"));
        sic.add(new SelectorItemInfo("fushiyuliang"));
        sic.add(new SelectorItemInfo("shejimidu"));
        sic.add(new SelectorItemInfo("anquangaodu"));
        sic.add(new SelectorItemInfo("huanremianji"));
        sic.add(new SelectorItemInfo("bibanchicun"));
        sic.add(new SelectorItemInfo("guanbibanone"));
        sic.add(new SelectorItemInfo("guanbibantwo"));
        sic.add(new SelectorItemInfo("guandingbanone"));
        sic.add(new SelectorItemInfo("guandingbantwo"));
        sic.add(new SelectorItemInfo("guandingbanthree"));
        sic.add(new SelectorItemInfo("bianyuanbanone"));
        sic.add(new SelectorItemInfo("bianyuanbantwo"));
        sic.add(new SelectorItemInfo("bianyuanbanthree"));
        sic.add(new SelectorItemInfo("zhongfubanone"));
        sic.add(new SelectorItemInfo("zhongfubantwo"));
        sic.add(new SelectorItemInfo("zhongfubanthree"));
        sic.add(new SelectorItemInfo("jingyouguan"));
        sic.add(new SelectorItemInfo("zhongxingaojy"));
        sic.add(new SelectorItemInfo("falanjiekoujy"));
        sic.add(new SelectorItemInfo("luoshuanjy"));
        sic.add(new SelectorItemInfo("chuyouguan"));
        sic.add(new SelectorItemInfo("zhongxingaocy"));
        sic.add(new SelectorItemInfo("falanjiekoucy"));
        sic.add(new SelectorItemInfo("luoshuancy"));
        sic.add(new SelectorItemInfo("rukong"));
        sic.add(new SelectorItemInfo("zhongxingaork"));
        sic.add(new SelectorItemInfo("rukongshuliang"));
        sic.add(new SelectorItemInfo("luoshuanrk"));
        sic.add(new SelectorItemInfo("touguangkong"));
        sic.add(new SelectorItemInfo("touguangkongsl"));
        sic.add(new SelectorItemInfo("luoshuantgk"));
        sic.add(new SelectorItemInfo("yeweijifljkone"));
        sic.add(new SelectorItemInfo("yeweijijkfltwo"));
        sic.add(new SelectorItemInfo("yeweijils"));
        sic.add(new SelectorItemInfo("yeweijiguige"));
        sic.add(new SelectorItemInfo("yeweijicd"));
        sic.add(new SelectorItemInfo("yeweijizxgone"));
        sic.add(new SelectorItemInfo("jianzaoTime"));
        sic.add(new SelectorItemInfo("shejidanwei"));
        sic.add(new SelectorItemInfo("shigongdanweione"));
        sic.add(new SelectorItemInfo("time"));
        sic.add(new SelectorItemInfo("guanrong"));
        sic.add(new SelectorItemInfo("dxtime"));
        sic.add(new SelectorItemInfo("daxiushigongriqi"));
        sic.add(new SelectorItemInfo("shigongdanweitwo"));
        sic.add(new SelectorItemInfo("daxiuneirong"));
        sic.add(new SelectorItemInfo("jiaobanqijkflone"));
        sic.add(new SelectorItemInfo("jiaobanqijkfltwo"));
        sic.add(new SelectorItemInfo("jiaobanqils"));
        sic.add(new SelectorItemInfo("jiaobanqiguige"));
        sic.add(new SelectorItemInfo("jiaobanqicd"));
        sic.add(new SelectorItemInfo("jiaobanqizxg"));
        sic.add(new SelectorItemInfo("paomojkflone"));
        sic.add(new SelectorItemInfo("paomojkfltwo"));
        sic.add(new SelectorItemInfo("paomols"));
        sic.add(new SelectorItemInfo("paomoguige"));
        sic.add(new SelectorItemInfo("paomocd"));
        sic.add(new SelectorItemInfo("paomozxg"));
        sic.add(new SelectorItemInfo("youliangguanjkflone"));
        sic.add(new SelectorItemInfo("youliangguanjkfltwo"));
        sic.add(new SelectorItemInfo("youliangguanls"));
        sic.add(new SelectorItemInfo("youliangguanguige"));
        sic.add(new SelectorItemInfo("youliangguancd"));
        sic.add(new SelectorItemInfo("youliangguanzxg"));
        sic.add(new SelectorItemInfo("touguangkongone"));
        sic.add(new SelectorItemInfo("touguangkongthree"));
        sic.add(new SelectorItemInfo("qingsaokongone"));
        sic.add(new SelectorItemInfo("qingsaokongtwo"));
        sic.add(new SelectorItemInfo("qingsaokongthree"));
        sic.add(new SelectorItemInfo("cehoujilu"));
        sic.add(new SelectorItemInfo("waiguan"));
        sic.add(new SelectorItemInfo("jianzaofeiyong"));
        sic.add(new SelectorItemInfo("qingsaokongfour"));
        sic.add(new SelectorItemInfo("qingsaokongfive"));
        sic.add(new SelectorItemInfo("luoshuanqs"));
        sic.add(new SelectorItemInfo("huxifajkflone"));
        sic.add(new SelectorItemInfo("huxifajkfltwo"));
        sic.add(new SelectorItemInfo("huxifals"));
        sic.add(new SelectorItemInfo("huxifagg"));
        sic.add(new SelectorItemInfo("bwcjkflone"));
        sic.add(new SelectorItemInfo("bwcjkfltwo"));
        sic.add(new SelectorItemInfo("huxifaCd"));
        sic.add(new SelectorItemInfo("huxifazxg"));
        sic.add(new SelectorItemInfo("bwcls"));
        sic.add(new SelectorItemInfo("baowecenggg"));
        sic.add(new SelectorItemInfo("baowencengcd"));
        sic.add(new SelectorItemInfo("baowencengzxg"));
        sic.add(new SelectorItemInfo("tiebijkfaone"));
        sic.add(new SelectorItemInfo("tiepijkfltwo"));
        sic.add(new SelectorItemInfo("teipils"));
        sic.add(new SelectorItemInfo("tiepigg"));
        sic.add(new SelectorItemInfo("tiepicd"));
        sic.add(new SelectorItemInfo("tiepizxg"));
        sic.add(new SelectorItemInfo("daoboguanjkflone"));
        sic.add(new SelectorItemInfo("daoboguanjkfltwo"));
        sic.add(new SelectorItemInfo("daoboguanls"));
        sic.add(new SelectorItemInfo("daoboguangg"));
        sic.add(new SelectorItemInfo("daoboguancd"));
        sic.add(new SelectorItemInfo("daoboguanzxg"));
        sic.add(new SelectorItemInfo("jiareqijkfaone"));
        sic.add(new SelectorItemInfo("jiareqijkfltwo"));
        sic.add(new SelectorItemInfo("jiarqichang"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("bizStatus"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("status"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "TankTechnicalEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.equipment.record.client.TankTechnicalEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.TankTechnicalFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.TankTechnicalInfo objectValue = new com.kingdee.eas.port.equipment.record.TankTechnicalInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/record/TankTechnical";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.TankTechnicalQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}