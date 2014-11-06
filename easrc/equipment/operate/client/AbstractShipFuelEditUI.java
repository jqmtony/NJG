/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

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
public abstract class AbstractShipFuelEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractShipFuelEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshipName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPower;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchuanzhang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlunjizhang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportMonth;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtshipName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPower;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator14;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel12;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator15;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel13;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel20;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel24;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator16;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel25;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator17;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel27;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel30;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel32;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel34;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel37;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel38;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel39;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel40;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator18;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel41;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator19;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel45;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel47;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel16;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlastMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqione;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqitwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhione;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhitwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmiduone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmidutwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contintoTotal;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqithree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqifour;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhithree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhifour;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmiduthree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmidufour;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalConsum;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel14;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmonthBalance;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator21;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel17;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel18;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel19;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel21;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator22;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel22;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator23;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel23;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator24;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel26;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel28;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel29;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel31;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel33;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel35;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator25;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator26;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator27;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contportShipment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsmallTransport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotal;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoutputValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgzde;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgzdel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxyzde;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxyzdel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthjde;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthjdel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchanzhione;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchanzhitwo;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator28;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator29;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhdel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshiyongliang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjieyou;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchaohao;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlastMonth;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqione;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqitwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhione;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhitwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmiduone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmidutwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtintoTotal;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqithree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqifour;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhithree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhifour;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmiduthree;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmidufour;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttotalConsum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmonthBalance;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtportShipment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsmallTransport;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTotal;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtoutputValue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgzde;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgzdel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxyzde;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxyzdel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthjde;
    protected com.kingdee.bos.ctrl.swing.KDTextField txthjdel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchanzhione;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchanzhitwo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhdel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshiyongliang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjieyou;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchaohao;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator11;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel36;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator30;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel42;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator31;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel43;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator32;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel44;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator33;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel46;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel48;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel49;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator34;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator35;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel50;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel51;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel52;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator36;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator37;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator38;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator39;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator40;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrunhuayoujiecun;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrunhuayougangzuo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrunhuayouxiao;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrunhuayouben;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchilunyoushang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchilunyougang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchilunyouxiao;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchilunyouben;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeyayoushang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeyayougang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeyayouxiao;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyeyayouben;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contedingrunranbi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshijirunranbi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdingeliangxx;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshiyongliangone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjieone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchaoone;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator60;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel66;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlingrurhy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlingrucly;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlingruyyy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrunhuayoujiecun;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrunhuayougangzuo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrunhuayouxiao;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrunhuayouben;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchilunyoushang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchilunyougang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchilunyouxiao;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchilunyouben;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeyayoushang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeyayougang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeyayouxiao;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyeyayouben;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtedingrunranbi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshijirunranbi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdingeliangxx;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtshiyongliangone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjieone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchaoone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlingrurhy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlingrucly;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlingruyyy;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel11;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contselfLeve;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtselfLeve;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator52;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator53;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator54;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel59;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel60;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel61;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator55;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator56;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator57;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator58;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator59;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel62;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel63;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel64;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel65;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuobenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyoujibenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfujibenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdianbiaobenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdianbiaoheji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdianbiaoshangyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfujiheji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfujishangyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyoujiheji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyoujishangyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuoheji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuoshangyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuobenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyoujibenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfujibenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdianbiaobenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdianbiaoheji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdianbiaoshangyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfujiheji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfujishangyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyoujiheji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyoujishangyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuoheji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuoshangyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnote;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleijiyongdian;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleijiranyou;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleijiyunshi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleijichanzhi;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanenote;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtnote;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleijiyongdian;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleijiranyou;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleijiyunshi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleijichanzhi;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel53;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator41;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel54;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator42;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel55;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator43;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel56;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel57;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel58;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator44;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator45;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator46;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator47;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator48;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator49;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator50;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator51;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjishudinge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchanzhidinge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjishubenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchanzhibenyue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjishuleiji;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchanzhileiji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjishudinge;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchanzhidinge;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjishubenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchanzhibenyue;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjishuleiji;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchanzhileiji;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtchuanzhang;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlunjizhang;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportMonth;
    protected com.kingdee.eas.port.equipment.operate.ShipFuelInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractShipFuelEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractShipFuelEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshipName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPower = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contchuanzhang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlunjizhang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtshipName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPower = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator14 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel12 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator15 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel13 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel20 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel24 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator16 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel25 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator17 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel27 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel30 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel32 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel34 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel37 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel38 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel39 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel40 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator18 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel41 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator19 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel45 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel47 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel16 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator20 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contlastMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqione = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqitwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhione = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhitwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmiduone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmidutwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contintoTotal = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqithree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqifour = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhithree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhifour = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmiduthree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmidufour = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalConsum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel14 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel15 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contmonthBalance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator21 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel17 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel18 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel19 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel21 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator22 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel22 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator23 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel23 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator24 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel26 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel28 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel29 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel31 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel33 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel35 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator25 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator26 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator27 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contportShipment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsmallTransport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotal = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoutputValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgzde = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgzdel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxyzde = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxyzdel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthjde = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthjdel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchanzhione = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchanzhitwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator28 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator29 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contzhdel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshiyongliang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjieyou = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchaohao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtlastMonth = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqione = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqitwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhione = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhitwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmiduone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmidutwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtintoTotal = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqithree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqifour = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhithree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhifour = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmiduthree = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmidufour = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttotalConsum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmonthBalance = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtportShipment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsmallTransport = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotal = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtoutputValue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtgzde = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtgzdel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtxyzde = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtxyzdel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthjde = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthjdel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchanzhione = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchanzhitwo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhdel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshiyongliang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjieyou = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchaohao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator11 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel36 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator30 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel42 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator31 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel43 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator32 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel44 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator33 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel46 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel48 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel49 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator34 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator35 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel50 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel51 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel52 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator36 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator37 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator38 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator39 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator40 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contrunhuayoujiecun = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrunhuayougangzuo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrunhuayouxiao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrunhuayouben = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchilunyoushang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchilunyougang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchilunyouxiao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchilunyouben = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeyayoushang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeyayougang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeyayouxiao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyeyayouben = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contedingrunranbi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshijirunranbi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdingeliangxx = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshiyongliangone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjieone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchaoone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator60 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel66 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contlingrurhy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlingrucly = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlingruyyy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtrunhuayoujiecun = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrunhuayougangzuo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrunhuayouxiao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrunhuayouben = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchilunyoushang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchilunyougang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchilunyouxiao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchilunyouben = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeyayoushang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeyayougang = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeyayouxiao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyeyayouben = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtedingrunranbi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshijirunranbi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdingeliangxx = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtshiyongliangone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjieone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchaoone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlingrurhy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlingrucly = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlingruyyy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel11 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator13 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contselfLeve = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtselfLeve = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDSeparator52 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator53 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator54 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel59 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel60 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel61 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator55 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator56 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator57 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator58 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator59 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel62 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel63 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel64 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel65 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contzuobenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyoujibenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfujibenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdianbiaobenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdianbiaoheji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdianbiaoshangyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfujiheji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfujishangyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyoujiheji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyoujishangyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzuoheji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzuoshangyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtzuobenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyoujibenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfujibenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdianbiaobenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdianbiaoheji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdianbiaoshangyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfujiheji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfujishangyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyoujiheji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyoujishangyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzuoheji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzuoshangyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contnote = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleijiyongdian = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleijiranyou = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleijiyunshi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contleijichanzhi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanenote = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtnote = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtleijiyongdian = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtleijiranyou = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtleijiyunshi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtleijichanzhi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel53 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator41 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel54 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator42 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel55 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator43 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabel56 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel57 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel58 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator44 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator45 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator46 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator47 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator48 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator49 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator50 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator51 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contjishudinge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchanzhidinge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjishubenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchanzhibenyue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjishuleiji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchanzhileiji = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtjishudinge = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchanzhidinge = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjishubenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchanzhibenyue = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjishuleiji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchanzhileiji = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtchuanzhang = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtlunjizhang = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtreportMonth = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contStatus.setName("contStatus");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.contshipName.setName("contshipName");
        this.contPower.setName("contPower");
        this.kDPanel1.setName("kDPanel1");
        this.contchuanzhang.setName("contchuanzhang");
        this.contlunjizhang.setName("contlunjizhang");
        this.contreportMonth.setName("contreportMonth");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboStatus.setName("comboStatus");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtshipName.setName("prmtshipName");
        this.txtPower.setName("txtPower");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel7.setName("kDPanel7");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDSeparator14.setName("kDSeparator14");
        this.kDLabel12.setName("kDLabel12");
        this.kDSeparator15.setName("kDSeparator15");
        this.kDLabel13.setName("kDLabel13");
        this.kDLabel20.setName("kDLabel20");
        this.kDLabel24.setName("kDLabel24");
        this.kDSeparator16.setName("kDSeparator16");
        this.kDLabel25.setName("kDLabel25");
        this.kDSeparator17.setName("kDSeparator17");
        this.kDLabel27.setName("kDLabel27");
        this.kDLabel30.setName("kDLabel30");
        this.kDLabel32.setName("kDLabel32");
        this.kDLabel34.setName("kDLabel34");
        this.kDLabel37.setName("kDLabel37");
        this.kDLabel38.setName("kDLabel38");
        this.kDLabel39.setName("kDLabel39");
        this.kDLabel40.setName("kDLabel40");
        this.kDSeparator18.setName("kDSeparator18");
        this.kDLabel41.setName("kDLabel41");
        this.kDSeparator19.setName("kDSeparator19");
        this.kDLabel45.setName("kDLabel45");
        this.kDLabel47.setName("kDLabel47");
        this.kDLabel16.setName("kDLabel16");
        this.kDSeparator20.setName("kDSeparator20");
        this.contlastMonth.setName("contlastMonth");
        this.contqione.setName("contqione");
        this.contqitwo.setName("contqitwo");
        this.contzhione.setName("contzhione");
        this.contzhitwo.setName("contzhitwo");
        this.contmiduone.setName("contmiduone");
        this.contmidutwo.setName("contmidutwo");
        this.contintoTotal.setName("contintoTotal");
        this.contqithree.setName("contqithree");
        this.contqifour.setName("contqifour");
        this.contzhithree.setName("contzhithree");
        this.contzhifour.setName("contzhifour");
        this.contmiduthree.setName("contmiduthree");
        this.contmidufour.setName("contmidufour");
        this.conttotalConsum.setName("conttotalConsum");
        this.kDLabel14.setName("kDLabel14");
        this.kDLabel15.setName("kDLabel15");
        this.contmonthBalance.setName("contmonthBalance");
        this.kDSeparator21.setName("kDSeparator21");
        this.kDLabel17.setName("kDLabel17");
        this.kDLabel18.setName("kDLabel18");
        this.kDLabel19.setName("kDLabel19");
        this.kDLabel21.setName("kDLabel21");
        this.kDSeparator22.setName("kDSeparator22");
        this.kDLabel22.setName("kDLabel22");
        this.kDSeparator23.setName("kDSeparator23");
        this.kDLabel23.setName("kDLabel23");
        this.kDSeparator24.setName("kDSeparator24");
        this.kDLabel26.setName("kDLabel26");
        this.kDLabel28.setName("kDLabel28");
        this.kDLabel29.setName("kDLabel29");
        this.kDLabel31.setName("kDLabel31");
        this.kDLabel33.setName("kDLabel33");
        this.kDLabel35.setName("kDLabel35");
        this.kDSeparator25.setName("kDSeparator25");
        this.kDSeparator26.setName("kDSeparator26");
        this.kDSeparator27.setName("kDSeparator27");
        this.contportShipment.setName("contportShipment");
        this.contsmallTransport.setName("contsmallTransport");
        this.contTotal.setName("contTotal");
        this.contoutputValue.setName("contoutputValue");
        this.contgzde.setName("contgzde");
        this.contgzdel.setName("contgzdel");
        this.contxyzde.setName("contxyzde");
        this.contxyzdel.setName("contxyzdel");
        this.conthjde.setName("conthjde");
        this.conthjdel.setName("conthjdel");
        this.contchanzhione.setName("contchanzhione");
        this.contchanzhitwo.setName("contchanzhitwo");
        this.kDSeparator28.setName("kDSeparator28");
        this.kDSeparator29.setName("kDSeparator29");
        this.contzhdel.setName("contzhdel");
        this.contshiyongliang.setName("contshiyongliang");
        this.contjieyou.setName("contjieyou");
        this.contchaohao.setName("contchaohao");
        this.txtlastMonth.setName("txtlastMonth");
        this.txtqione.setName("txtqione");
        this.txtqitwo.setName("txtqitwo");
        this.txtzhione.setName("txtzhione");
        this.txtzhitwo.setName("txtzhitwo");
        this.txtmiduone.setName("txtmiduone");
        this.txtmidutwo.setName("txtmidutwo");
        this.txtintoTotal.setName("txtintoTotal");
        this.txtqithree.setName("txtqithree");
        this.txtqifour.setName("txtqifour");
        this.txtzhithree.setName("txtzhithree");
        this.txtzhifour.setName("txtzhifour");
        this.txtmiduthree.setName("txtmiduthree");
        this.txtmidufour.setName("txtmidufour");
        this.txttotalConsum.setName("txttotalConsum");
        this.txtmonthBalance.setName("txtmonthBalance");
        this.txtportShipment.setName("txtportShipment");
        this.txtsmallTransport.setName("txtsmallTransport");
        this.txtTotal.setName("txtTotal");
        this.txtoutputValue.setName("txtoutputValue");
        this.txtgzde.setName("txtgzde");
        this.txtgzdel.setName("txtgzdel");
        this.txtxyzde.setName("txtxyzde");
        this.txtxyzdel.setName("txtxyzdel");
        this.txthjde.setName("txthjde");
        this.txthjdel.setName("txthjdel");
        this.txtchanzhione.setName("txtchanzhione");
        this.txtchanzhitwo.setName("txtchanzhitwo");
        this.txtzhdel.setName("txtzhdel");
        this.txtshiyongliang.setName("txtshiyongliang");
        this.txtjieyou.setName("txtjieyou");
        this.txtchaohao.setName("txtchaohao");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.kDLabel8.setName("kDLabel8");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDSeparator11.setName("kDSeparator11");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel36.setName("kDLabel36");
        this.kDSeparator30.setName("kDSeparator30");
        this.kDLabel42.setName("kDLabel42");
        this.kDSeparator31.setName("kDSeparator31");
        this.kDLabel43.setName("kDLabel43");
        this.kDSeparator32.setName("kDSeparator32");
        this.kDLabel44.setName("kDLabel44");
        this.kDSeparator33.setName("kDSeparator33");
        this.kDLabel46.setName("kDLabel46");
        this.kDLabel48.setName("kDLabel48");
        this.kDLabel49.setName("kDLabel49");
        this.kDSeparator34.setName("kDSeparator34");
        this.kDSeparator35.setName("kDSeparator35");
        this.kDLabel50.setName("kDLabel50");
        this.kDLabel51.setName("kDLabel51");
        this.kDLabel52.setName("kDLabel52");
        this.kDSeparator36.setName("kDSeparator36");
        this.kDSeparator37.setName("kDSeparator37");
        this.kDSeparator38.setName("kDSeparator38");
        this.kDSeparator39.setName("kDSeparator39");
        this.kDSeparator40.setName("kDSeparator40");
        this.contrunhuayoujiecun.setName("contrunhuayoujiecun");
        this.contrunhuayougangzuo.setName("contrunhuayougangzuo");
        this.contrunhuayouxiao.setName("contrunhuayouxiao");
        this.contrunhuayouben.setName("contrunhuayouben");
        this.contchilunyoushang.setName("contchilunyoushang");
        this.contchilunyougang.setName("contchilunyougang");
        this.contchilunyouxiao.setName("contchilunyouxiao");
        this.contchilunyouben.setName("contchilunyouben");
        this.contyeyayoushang.setName("contyeyayoushang");
        this.contyeyayougang.setName("contyeyayougang");
        this.contyeyayouxiao.setName("contyeyayouxiao");
        this.contyeyayouben.setName("contyeyayouben");
        this.contedingrunranbi.setName("contedingrunranbi");
        this.contshijirunranbi.setName("contshijirunranbi");
        this.contdingeliangxx.setName("contdingeliangxx");
        this.contshiyongliangone.setName("contshiyongliangone");
        this.contjieone.setName("contjieone");
        this.contchaoone.setName("contchaoone");
        this.kDSeparator60.setName("kDSeparator60");
        this.kDLabel66.setName("kDLabel66");
        this.contlingrurhy.setName("contlingrurhy");
        this.contlingrucly.setName("contlingrucly");
        this.contlingruyyy.setName("contlingruyyy");
        this.txtrunhuayoujiecun.setName("txtrunhuayoujiecun");
        this.txtrunhuayougangzuo.setName("txtrunhuayougangzuo");
        this.txtrunhuayouxiao.setName("txtrunhuayouxiao");
        this.txtrunhuayouben.setName("txtrunhuayouben");
        this.txtchilunyoushang.setName("txtchilunyoushang");
        this.txtchilunyougang.setName("txtchilunyougang");
        this.txtchilunyouxiao.setName("txtchilunyouxiao");
        this.txtchilunyouben.setName("txtchilunyouben");
        this.txtyeyayoushang.setName("txtyeyayoushang");
        this.txtyeyayougang.setName("txtyeyayougang");
        this.txtyeyayouxiao.setName("txtyeyayouxiao");
        this.txtyeyayouben.setName("txtyeyayouben");
        this.txtedingrunranbi.setName("txtedingrunranbi");
        this.txtshijirunranbi.setName("txtshijirunranbi");
        this.txtdingeliangxx.setName("txtdingeliangxx");
        this.txtshiyongliangone.setName("txtshiyongliangone");
        this.txtjieone.setName("txtjieone");
        this.txtchaoone.setName("txtchaoone");
        this.txtlingrurhy.setName("txtlingrurhy");
        this.txtlingrucly.setName("txtlingrucly");
        this.txtlingruyyy.setName("txtlingruyyy");
        this.kDLabel9.setName("kDLabel9");
        this.kDLabel10.setName("kDLabel10");
        this.kDLabel11.setName("kDLabel11");
        this.kDSeparator13.setName("kDSeparator13");
        this.contselfLeve.setName("contselfLeve");
        this.txtselfLeve.setName("txtselfLeve");
        this.kDSeparator52.setName("kDSeparator52");
        this.kDSeparator53.setName("kDSeparator53");
        this.kDSeparator54.setName("kDSeparator54");
        this.kDLabel59.setName("kDLabel59");
        this.kDLabel60.setName("kDLabel60");
        this.kDLabel61.setName("kDLabel61");
        this.kDSeparator55.setName("kDSeparator55");
        this.kDSeparator56.setName("kDSeparator56");
        this.kDSeparator57.setName("kDSeparator57");
        this.kDSeparator58.setName("kDSeparator58");
        this.kDSeparator59.setName("kDSeparator59");
        this.kDLabel62.setName("kDLabel62");
        this.kDLabel63.setName("kDLabel63");
        this.kDLabel64.setName("kDLabel64");
        this.kDLabel65.setName("kDLabel65");
        this.contzuobenyue.setName("contzuobenyue");
        this.contyoujibenyue.setName("contyoujibenyue");
        this.contfujibenyue.setName("contfujibenyue");
        this.contdianbiaobenyue.setName("contdianbiaobenyue");
        this.contdianbiaoheji.setName("contdianbiaoheji");
        this.contdianbiaoshangyue.setName("contdianbiaoshangyue");
        this.contfujiheji.setName("contfujiheji");
        this.contfujishangyue.setName("contfujishangyue");
        this.contyoujiheji.setName("contyoujiheji");
        this.contyoujishangyue.setName("contyoujishangyue");
        this.contzuoheji.setName("contzuoheji");
        this.contzuoshangyue.setName("contzuoshangyue");
        this.txtzuobenyue.setName("txtzuobenyue");
        this.txtyoujibenyue.setName("txtyoujibenyue");
        this.txtfujibenyue.setName("txtfujibenyue");
        this.txtdianbiaobenyue.setName("txtdianbiaobenyue");
        this.txtdianbiaoheji.setName("txtdianbiaoheji");
        this.txtdianbiaoshangyue.setName("txtdianbiaoshangyue");
        this.txtfujiheji.setName("txtfujiheji");
        this.txtfujishangyue.setName("txtfujishangyue");
        this.txtyoujiheji.setName("txtyoujiheji");
        this.txtyoujishangyue.setName("txtyoujishangyue");
        this.txtzuoheji.setName("txtzuoheji");
        this.txtzuoshangyue.setName("txtzuoshangyue");
        this.contnote.setName("contnote");
        this.contleijiyongdian.setName("contleijiyongdian");
        this.contleijiranyou.setName("contleijiranyou");
        this.contleijiyunshi.setName("contleijiyunshi");
        this.contleijichanzhi.setName("contleijichanzhi");
        this.scrollPanenote.setName("scrollPanenote");
        this.txtnote.setName("txtnote");
        this.txtleijiyongdian.setName("txtleijiyongdian");
        this.txtleijiranyou.setName("txtleijiranyou");
        this.txtleijiyunshi.setName("txtleijiyunshi");
        this.txtleijichanzhi.setName("txtleijichanzhi");
        this.kDLabel53.setName("kDLabel53");
        this.kDSeparator41.setName("kDSeparator41");
        this.kDLabel54.setName("kDLabel54");
        this.kDSeparator42.setName("kDSeparator42");
        this.kDLabel55.setName("kDLabel55");
        this.kDSeparator43.setName("kDSeparator43");
        this.kDLabel56.setName("kDLabel56");
        this.kDLabel57.setName("kDLabel57");
        this.kDLabel58.setName("kDLabel58");
        this.kDSeparator44.setName("kDSeparator44");
        this.kDSeparator45.setName("kDSeparator45");
        this.kDSeparator46.setName("kDSeparator46");
        this.kDSeparator47.setName("kDSeparator47");
        this.kDSeparator48.setName("kDSeparator48");
        this.kDSeparator49.setName("kDSeparator49");
        this.kDSeparator50.setName("kDSeparator50");
        this.kDSeparator51.setName("kDSeparator51");
        this.contjishudinge.setName("contjishudinge");
        this.contchanzhidinge.setName("contchanzhidinge");
        this.contjishubenyue.setName("contjishubenyue");
        this.contchanzhibenyue.setName("contchanzhibenyue");
        this.contjishuleiji.setName("contjishuleiji");
        this.contchanzhileiji.setName("contchanzhileiji");
        this.txtjishudinge.setName("txtjishudinge");
        this.txtchanzhidinge.setName("txtchanzhidinge");
        this.txtjishubenyue.setName("txtjishubenyue");
        this.txtchanzhibenyue.setName("txtchanzhibenyue");
        this.txtjishuleiji.setName("txtjishuleiji");
        this.txtchanzhileiji.setName("txtchanzhileiji");
        this.prmtchuanzhang.setName("prmtchuanzhang");
        this.prmtlunjizhang.setName("prmtlunjizhang");
        this.prmtreportMonth.setName("prmtreportMonth");
        // CoreUI		
        this.setPreferredSize(new Dimension(963,610));
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
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
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
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contshipName		
        this.contshipName.setBoundLabelText(resHelper.getString("contshipName.boundLabelText"));		
        this.contshipName.setBoundLabelLength(100);		
        this.contshipName.setBoundLabelUnderline(true);		
        this.contshipName.setVisible(true);
        // contPower		
        this.contPower.setBoundLabelText(resHelper.getString("contPower.boundLabelText"));		
        this.contPower.setBoundLabelLength(100);		
        this.contPower.setBoundLabelUnderline(true);		
        this.contPower.setVisible(true);
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // contchuanzhang		
        this.contchuanzhang.setBoundLabelText(resHelper.getString("contchuanzhang.boundLabelText"));		
        this.contchuanzhang.setBoundLabelLength(100);		
        this.contchuanzhang.setBoundLabelUnderline(true);		
        this.contchuanzhang.setVisible(true);
        // contlunjizhang		
        this.contlunjizhang.setBoundLabelText(resHelper.getString("contlunjizhang.boundLabelText"));		
        this.contlunjizhang.setBoundLabelLength(100);		
        this.contlunjizhang.setBoundLabelUnderline(true);		
        this.contlunjizhang.setVisible(true);
        // contreportMonth		
        this.contreportMonth.setBoundLabelText(resHelper.getString("contreportMonth.boundLabelText"));		
        this.contreportMonth.setBoundLabelLength(100);		
        this.contreportMonth.setBoundLabelUnderline(true);		
        this.contreportMonth.setVisible(true);
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
        // txtNumber
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
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtshipName		
        this.prmtshipName.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtshipName.setVisible(true);		
        this.prmtshipName.setEditable(true);		
        this.prmtshipName.setDisplayFormat("$name$");		
        this.prmtshipName.setEditFormat("$number$");		
        this.prmtshipName.setCommitFormat("$number$");		
        this.prmtshipName.setRequired(false);
        this.prmtshipName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtshipName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPower		
        this.txtPower.setVisible(true);		
        this.txtPower.setHorizontalAlignment(2);		
        this.txtPower.setMaxLength(100);		
        this.txtPower.setRequired(false);
        // kDPanel2		
        this.kDPanel2.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDPanel3		
        this.kDPanel3.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDPanel4		
        this.kDPanel4.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDPanel5		
        this.kDPanel5.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDPanel6		
        this.kDPanel6.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDPanel7		
        this.kDPanel7.setBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDSeparator8
        // kDSeparator10
        // kDSeparator14		
        this.kDSeparator14.setOrientation(1);
        // kDLabel12		
        this.kDLabel12.setText(resHelper.getString("kDLabel12.text"));
        // kDSeparator15
        // kDLabel13		
        this.kDLabel13.setText(resHelper.getString("kDLabel13.text"));
        // kDLabel20		
        this.kDLabel20.setText(resHelper.getString("kDLabel20.text"));
        // kDLabel24		
        this.kDLabel24.setText(resHelper.getString("kDLabel24.text"));
        // kDSeparator16
        // kDLabel25		
        this.kDLabel25.setText(resHelper.getString("kDLabel25.text"));
        // kDSeparator17
        // kDLabel27		
        this.kDLabel27.setText(resHelper.getString("kDLabel27.text"));
        // kDLabel30		
        this.kDLabel30.setText(resHelper.getString("kDLabel30.text"));
        // kDLabel32		
        this.kDLabel32.setText(resHelper.getString("kDLabel32.text"));
        // kDLabel34		
        this.kDLabel34.setText(resHelper.getString("kDLabel34.text"));
        // kDLabel37		
        this.kDLabel37.setText(resHelper.getString("kDLabel37.text"));
        // kDLabel38		
        this.kDLabel38.setText(resHelper.getString("kDLabel38.text"));
        // kDLabel39		
        this.kDLabel39.setText(resHelper.getString("kDLabel39.text"));
        // kDLabel40		
        this.kDLabel40.setText(resHelper.getString("kDLabel40.text"));
        // kDSeparator18
        // kDLabel41		
        this.kDLabel41.setText(resHelper.getString("kDLabel41.text"));
        // kDSeparator19
        // kDLabel45		
        this.kDLabel45.setText(resHelper.getString("kDLabel45.text"));
        // kDLabel47		
        this.kDLabel47.setText(resHelper.getString("kDLabel47.text"));
        // kDLabel16		
        this.kDLabel16.setText(resHelper.getString("kDLabel16.text"));
        // kDSeparator20
        // contlastMonth		
        this.contlastMonth.setBoundLabelText(resHelper.getString("contlastMonth.boundLabelText"));		
        this.contlastMonth.setBoundLabelLength(0);		
        this.contlastMonth.setBoundLabelUnderline(true);		
        this.contlastMonth.setVisible(true);
        // contqione		
        this.contqione.setBoundLabelText(resHelper.getString("contqione.boundLabelText"));		
        this.contqione.setBoundLabelLength(13);		
        this.contqione.setBoundLabelUnderline(true);		
        this.contqione.setVisible(true);
        // contqitwo		
        this.contqitwo.setBoundLabelText(resHelper.getString("contqitwo.boundLabelText"));		
        this.contqitwo.setBoundLabelLength(13);		
        this.contqitwo.setBoundLabelUnderline(true);		
        this.contqitwo.setVisible(true);
        // contzhione		
        this.contzhione.setBoundLabelText(resHelper.getString("contzhione.boundLabelText"));		
        this.contzhione.setBoundLabelLength(13);		
        this.contzhione.setBoundLabelUnderline(true);		
        this.contzhione.setVisible(true);
        // contzhitwo		
        this.contzhitwo.setBoundLabelText(resHelper.getString("contzhitwo.boundLabelText"));		
        this.contzhitwo.setBoundLabelLength(13);		
        this.contzhitwo.setBoundLabelUnderline(true);		
        this.contzhitwo.setVisible(true);
        // contmiduone		
        this.contmiduone.setBoundLabelText(resHelper.getString("contmiduone.boundLabelText"));		
        this.contmiduone.setBoundLabelLength(25);		
        this.contmiduone.setBoundLabelUnderline(true);		
        this.contmiduone.setVisible(true);
        // contmidutwo		
        this.contmidutwo.setBoundLabelText(resHelper.getString("contmidutwo.boundLabelText"));		
        this.contmidutwo.setBoundLabelLength(25);		
        this.contmidutwo.setBoundLabelUnderline(true);		
        this.contmidutwo.setVisible(true);
        // contintoTotal		
        this.contintoTotal.setBoundLabelText(resHelper.getString("contintoTotal.boundLabelText"));		
        this.contintoTotal.setBoundLabelLength(0);		
        this.contintoTotal.setBoundLabelUnderline(true);		
        this.contintoTotal.setVisible(true);
        // contqithree		
        this.contqithree.setBoundLabelText(resHelper.getString("contqithree.boundLabelText"));		
        this.contqithree.setBoundLabelLength(13);		
        this.contqithree.setBoundLabelUnderline(true);		
        this.contqithree.setVisible(true);
        // contqifour		
        this.contqifour.setBoundLabelText(resHelper.getString("contqifour.boundLabelText"));		
        this.contqifour.setBoundLabelLength(13);		
        this.contqifour.setBoundLabelUnderline(true);		
        this.contqifour.setVisible(true);
        // contzhithree		
        this.contzhithree.setBoundLabelText(resHelper.getString("contzhithree.boundLabelText"));		
        this.contzhithree.setBoundLabelLength(13);		
        this.contzhithree.setBoundLabelUnderline(true);		
        this.contzhithree.setVisible(true);
        // contzhifour		
        this.contzhifour.setBoundLabelText(resHelper.getString("contzhifour.boundLabelText"));		
        this.contzhifour.setBoundLabelLength(13);		
        this.contzhifour.setBoundLabelUnderline(true);		
        this.contzhifour.setVisible(true);
        // contmiduthree		
        this.contmiduthree.setBoundLabelText(resHelper.getString("contmiduthree.boundLabelText"));		
        this.contmiduthree.setBoundLabelLength(25);		
        this.contmiduthree.setBoundLabelUnderline(true);		
        this.contmiduthree.setVisible(true);
        // contmidufour		
        this.contmidufour.setBoundLabelText(resHelper.getString("contmidufour.boundLabelText"));		
        this.contmidufour.setBoundLabelLength(25);		
        this.contmidufour.setBoundLabelUnderline(true);		
        this.contmidufour.setVisible(true);
        // conttotalConsum		
        this.conttotalConsum.setBoundLabelText(resHelper.getString("conttotalConsum.boundLabelText"));		
        this.conttotalConsum.setBoundLabelLength(0);		
        this.conttotalConsum.setBoundLabelUnderline(true);		
        this.conttotalConsum.setVisible(true);
        // kDLabel14		
        this.kDLabel14.setText(resHelper.getString("kDLabel14.text"));
        // kDLabel15		
        this.kDLabel15.setText(resHelper.getString("kDLabel15.text"));
        // contmonthBalance		
        this.contmonthBalance.setBoundLabelText(resHelper.getString("contmonthBalance.boundLabelText"));		
        this.contmonthBalance.setBoundLabelLength(0);		
        this.contmonthBalance.setBoundLabelUnderline(true);		
        this.contmonthBalance.setVisible(true);
        // kDSeparator21
        // kDLabel17		
        this.kDLabel17.setText(resHelper.getString("kDLabel17.text"));
        // kDLabel18		
        this.kDLabel18.setText(resHelper.getString("kDLabel18.text"));
        // kDLabel19		
        this.kDLabel19.setText(resHelper.getString("kDLabel19.text"));
        // kDLabel21		
        this.kDLabel21.setText(resHelper.getString("kDLabel21.text"));
        // kDSeparator22
        // kDLabel22		
        this.kDLabel22.setText(resHelper.getString("kDLabel22.text"));
        // kDSeparator23
        // kDLabel23		
        this.kDLabel23.setText(resHelper.getString("kDLabel23.text"));
        // kDSeparator24
        // kDLabel26		
        this.kDLabel26.setText(resHelper.getString("kDLabel26.text"));
        // kDLabel28		
        this.kDLabel28.setText(resHelper.getString("kDLabel28.text"));
        // kDLabel29		
        this.kDLabel29.setText(resHelper.getString("kDLabel29.text"));
        // kDLabel31		
        this.kDLabel31.setText(resHelper.getString("kDLabel31.text"));
        // kDLabel33		
        this.kDLabel33.setText(resHelper.getString("kDLabel33.text"));
        // kDLabel35		
        this.kDLabel35.setText(resHelper.getString("kDLabel35.text"));
        // kDSeparator25
        // kDSeparator26
        // kDSeparator27
        // contportShipment		
        this.contportShipment.setBoundLabelText(resHelper.getString("contportShipment.boundLabelText"));		
        this.contportShipment.setBoundLabelLength(0);		
        this.contportShipment.setBoundLabelUnderline(true);		
        this.contportShipment.setVisible(true);
        // contsmallTransport		
        this.contsmallTransport.setBoundLabelText(resHelper.getString("contsmallTransport.boundLabelText"));		
        this.contsmallTransport.setBoundLabelLength(0);		
        this.contsmallTransport.setBoundLabelUnderline(true);		
        this.contsmallTransport.setVisible(true);
        // contTotal		
        this.contTotal.setBoundLabelText(resHelper.getString("contTotal.boundLabelText"));		
        this.contTotal.setBoundLabelLength(0);		
        this.contTotal.setBoundLabelUnderline(true);		
        this.contTotal.setVisible(true);
        // contoutputValue		
        this.contoutputValue.setBoundLabelText(resHelper.getString("contoutputValue.boundLabelText"));		
        this.contoutputValue.setBoundLabelLength(0);		
        this.contoutputValue.setBoundLabelUnderline(true);		
        this.contoutputValue.setVisible(true);
        // contgzde		
        this.contgzde.setBoundLabelText(resHelper.getString("contgzde.boundLabelText"));		
        this.contgzde.setBoundLabelLength(0);		
        this.contgzde.setBoundLabelUnderline(true);		
        this.contgzde.setVisible(true);
        // contgzdel		
        this.contgzdel.setBoundLabelText(resHelper.getString("contgzdel.boundLabelText"));		
        this.contgzdel.setBoundLabelLength(0);		
        this.contgzdel.setBoundLabelUnderline(true);		
        this.contgzdel.setVisible(true);
        // contxyzde		
        this.contxyzde.setBoundLabelText(resHelper.getString("contxyzde.boundLabelText"));		
        this.contxyzde.setBoundLabelLength(0);		
        this.contxyzde.setBoundLabelUnderline(true);		
        this.contxyzde.setVisible(true);
        // contxyzdel		
        this.contxyzdel.setBoundLabelText(resHelper.getString("contxyzdel.boundLabelText"));		
        this.contxyzdel.setBoundLabelLength(0);		
        this.contxyzdel.setBoundLabelUnderline(true);		
        this.contxyzdel.setVisible(true);
        // conthjde		
        this.conthjde.setBoundLabelText(resHelper.getString("conthjde.boundLabelText"));		
        this.conthjde.setBoundLabelLength(0);		
        this.conthjde.setBoundLabelUnderline(true);		
        this.conthjde.setVisible(true);
        // conthjdel		
        this.conthjdel.setBoundLabelText(resHelper.getString("conthjdel.boundLabelText"));		
        this.conthjdel.setBoundLabelLength(0);		
        this.conthjdel.setBoundLabelUnderline(true);		
        this.conthjdel.setVisible(true);
        // contchanzhione		
        this.contchanzhione.setBoundLabelText(resHelper.getString("contchanzhione.boundLabelText"));		
        this.contchanzhione.setBoundLabelLength(0);		
        this.contchanzhione.setBoundLabelUnderline(true);		
        this.contchanzhione.setVisible(true);
        // contchanzhitwo		
        this.contchanzhitwo.setBoundLabelText(resHelper.getString("contchanzhitwo.boundLabelText"));		
        this.contchanzhitwo.setBoundLabelLength(0);		
        this.contchanzhitwo.setBoundLabelUnderline(true);		
        this.contchanzhitwo.setVisible(true);
        // kDSeparator28		
        this.kDSeparator28.setOrientation(1);
        // kDSeparator29		
        this.kDSeparator29.setOrientation(1);
        // contzhdel		
        this.contzhdel.setBoundLabelText(resHelper.getString("contzhdel.boundLabelText"));		
        this.contzhdel.setBoundLabelLength(0);		
        this.contzhdel.setBoundLabelUnderline(true);		
        this.contzhdel.setVisible(true);
        // contshiyongliang		
        this.contshiyongliang.setBoundLabelText(resHelper.getString("contshiyongliang.boundLabelText"));		
        this.contshiyongliang.setBoundLabelLength(0);		
        this.contshiyongliang.setBoundLabelUnderline(true);		
        this.contshiyongliang.setVisible(true);
        // contjieyou		
        this.contjieyou.setBoundLabelText(resHelper.getString("contjieyou.boundLabelText"));		
        this.contjieyou.setBoundLabelLength(0);		
        this.contjieyou.setBoundLabelUnderline(true);		
        this.contjieyou.setVisible(true);
        // contchaohao		
        this.contchaohao.setBoundLabelText(resHelper.getString("contchaohao.boundLabelText"));		
        this.contchaohao.setBoundLabelLength(0);		
        this.contchaohao.setBoundLabelUnderline(true);		
        this.contchaohao.setVisible(true);
        // txtlastMonth		
        this.txtlastMonth.setVisible(true);		
        this.txtlastMonth.setHorizontalAlignment(2);		
        this.txtlastMonth.setMaxLength(100);		
        this.txtlastMonth.setRequired(false);
        // txtqione		
        this.txtqione.setVisible(true);		
        this.txtqione.setHorizontalAlignment(2);		
        this.txtqione.setMaxLength(100);		
        this.txtqione.setRequired(false);
        // txtqitwo		
        this.txtqitwo.setVisible(true);		
        this.txtqitwo.setHorizontalAlignment(2);		
        this.txtqitwo.setMaxLength(100);		
        this.txtqitwo.setRequired(false);
        // txtzhione		
        this.txtzhione.setVisible(true);		
        this.txtzhione.setHorizontalAlignment(2);		
        this.txtzhione.setMaxLength(100);		
        this.txtzhione.setRequired(false);
        // txtzhitwo		
        this.txtzhitwo.setVisible(true);		
        this.txtzhitwo.setHorizontalAlignment(2);		
        this.txtzhitwo.setMaxLength(100);		
        this.txtzhitwo.setRequired(false);
        // txtmiduone		
        this.txtmiduone.setVisible(true);		
        this.txtmiduone.setHorizontalAlignment(2);		
        this.txtmiduone.setMaxLength(100);		
        this.txtmiduone.setRequired(false);
        // txtmidutwo		
        this.txtmidutwo.setVisible(true);		
        this.txtmidutwo.setHorizontalAlignment(2);		
        this.txtmidutwo.setMaxLength(100);		
        this.txtmidutwo.setRequired(false);
        // txtintoTotal		
        this.txtintoTotal.setVisible(true);		
        this.txtintoTotal.setHorizontalAlignment(2);		
        this.txtintoTotal.setMaxLength(100);		
        this.txtintoTotal.setRequired(false);
        // txtqithree		
        this.txtqithree.setVisible(true);		
        this.txtqithree.setHorizontalAlignment(2);		
        this.txtqithree.setMaxLength(100);		
        this.txtqithree.setRequired(false);
        // txtqifour		
        this.txtqifour.setVisible(true);		
        this.txtqifour.setHorizontalAlignment(2);		
        this.txtqifour.setMaxLength(100);		
        this.txtqifour.setRequired(false);
        // txtzhithree		
        this.txtzhithree.setVisible(true);		
        this.txtzhithree.setHorizontalAlignment(2);		
        this.txtzhithree.setMaxLength(100);		
        this.txtzhithree.setRequired(false);
        // txtzhifour		
        this.txtzhifour.setVisible(true);		
        this.txtzhifour.setHorizontalAlignment(2);		
        this.txtzhifour.setMaxLength(100);		
        this.txtzhifour.setRequired(false);
        // txtmiduthree		
        this.txtmiduthree.setVisible(true);		
        this.txtmiduthree.setHorizontalAlignment(2);		
        this.txtmiduthree.setMaxLength(100);		
        this.txtmiduthree.setRequired(false);
        // txtmidufour		
        this.txtmidufour.setVisible(true);		
        this.txtmidufour.setHorizontalAlignment(2);		
        this.txtmidufour.setMaxLength(100);		
        this.txtmidufour.setRequired(false);
        // txttotalConsum		
        this.txttotalConsum.setVisible(true);		
        this.txttotalConsum.setHorizontalAlignment(2);		
        this.txttotalConsum.setMaxLength(100);		
        this.txttotalConsum.setRequired(false);
        // txtmonthBalance		
        this.txtmonthBalance.setVisible(true);		
        this.txtmonthBalance.setHorizontalAlignment(2);		
        this.txtmonthBalance.setMaxLength(100);		
        this.txtmonthBalance.setRequired(false);
        // txtportShipment		
        this.txtportShipment.setVisible(true);		
        this.txtportShipment.setHorizontalAlignment(2);		
        this.txtportShipment.setMaxLength(100);		
        this.txtportShipment.setRequired(false);
        // txtsmallTransport		
        this.txtsmallTransport.setVisible(true);		
        this.txtsmallTransport.setHorizontalAlignment(2);		
        this.txtsmallTransport.setMaxLength(100);		
        this.txtsmallTransport.setRequired(false);
        // txtTotal		
        this.txtTotal.setVisible(true);		
        this.txtTotal.setHorizontalAlignment(2);		
        this.txtTotal.setMaxLength(100);		
        this.txtTotal.setRequired(false);
        // txtoutputValue		
        this.txtoutputValue.setVisible(true);		
        this.txtoutputValue.setHorizontalAlignment(2);		
        this.txtoutputValue.setMaxLength(100);		
        this.txtoutputValue.setRequired(false);
        // txtgzde		
        this.txtgzde.setVisible(true);		
        this.txtgzde.setHorizontalAlignment(2);		
        this.txtgzde.setMaxLength(100);		
        this.txtgzde.setRequired(false);
        // txtgzdel		
        this.txtgzdel.setVisible(true);		
        this.txtgzdel.setHorizontalAlignment(2);		
        this.txtgzdel.setMaxLength(100);		
        this.txtgzdel.setRequired(false);
        // txtxyzde		
        this.txtxyzde.setVisible(true);		
        this.txtxyzde.setHorizontalAlignment(2);		
        this.txtxyzde.setMaxLength(100);		
        this.txtxyzde.setRequired(false);
        // txtxyzdel		
        this.txtxyzdel.setVisible(true);		
        this.txtxyzdel.setHorizontalAlignment(2);		
        this.txtxyzdel.setMaxLength(100);		
        this.txtxyzdel.setRequired(false);
        // txthjde		
        this.txthjde.setVisible(true);		
        this.txthjde.setHorizontalAlignment(2);		
        this.txthjde.setMaxLength(100);		
        this.txthjde.setRequired(false);
        // txthjdel		
        this.txthjdel.setVisible(true);		
        this.txthjdel.setHorizontalAlignment(2);		
        this.txthjdel.setMaxLength(100);		
        this.txthjdel.setRequired(false);
        // txtchanzhione		
        this.txtchanzhione.setVisible(true);		
        this.txtchanzhione.setHorizontalAlignment(2);		
        this.txtchanzhione.setMaxLength(100);		
        this.txtchanzhione.setRequired(false);
        // txtchanzhitwo		
        this.txtchanzhitwo.setVisible(true);		
        this.txtchanzhitwo.setHorizontalAlignment(2);		
        this.txtchanzhitwo.setMaxLength(100);		
        this.txtchanzhitwo.setRequired(false);
        // txtzhdel		
        this.txtzhdel.setVisible(true);		
        this.txtzhdel.setHorizontalAlignment(2);		
        this.txtzhdel.setMaxLength(100);		
        this.txtzhdel.setRequired(false);
        // txtshiyongliang		
        this.txtshiyongliang.setVisible(true);		
        this.txtshiyongliang.setHorizontalAlignment(2);		
        this.txtshiyongliang.setMaxLength(100);		
        this.txtshiyongliang.setRequired(false);
        // txtjieyou		
        this.txtjieyou.setVisible(true);		
        this.txtjieyou.setHorizontalAlignment(2);		
        this.txtjieyou.setMaxLength(100);		
        this.txtjieyou.setRequired(false);
        // txtchaohao		
        this.txtchaohao.setVisible(true);		
        this.txtchaohao.setHorizontalAlignment(2);		
        this.txtchaohao.setMaxLength(100);		
        this.txtchaohao.setRequired(false);
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDSeparator9
        // kDSeparator11
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel36		
        this.kDLabel36.setText(resHelper.getString("kDLabel36.text"));
        // kDSeparator30
        // kDLabel42		
        this.kDLabel42.setText(resHelper.getString("kDLabel42.text"));
        // kDSeparator31
        // kDLabel43		
        this.kDLabel43.setText(resHelper.getString("kDLabel43.text"));
        // kDSeparator32
        // kDLabel44		
        this.kDLabel44.setText(resHelper.getString("kDLabel44.text"));
        // kDSeparator33
        // kDLabel46		
        this.kDLabel46.setText(resHelper.getString("kDLabel46.text"));
        // kDLabel48		
        this.kDLabel48.setText(resHelper.getString("kDLabel48.text"));
        // kDLabel49		
        this.kDLabel49.setText(resHelper.getString("kDLabel49.text"));
        // kDSeparator34
        // kDSeparator35
        // kDLabel50		
        this.kDLabel50.setText(resHelper.getString("kDLabel50.text"));
        // kDLabel51		
        this.kDLabel51.setText(resHelper.getString("kDLabel51.text"));
        // kDLabel52		
        this.kDLabel52.setText(resHelper.getString("kDLabel52.text"));
        // kDSeparator36
        // kDSeparator37		
        this.kDSeparator37.setOrientation(1);
        // kDSeparator38		
        this.kDSeparator38.setOrientation(1);
        // kDSeparator39		
        this.kDSeparator39.setOrientation(1);
        // kDSeparator40		
        this.kDSeparator40.setOrientation(1);
        // contrunhuayoujiecun		
        this.contrunhuayoujiecun.setBoundLabelText(resHelper.getString("contrunhuayoujiecun.boundLabelText"));		
        this.contrunhuayoujiecun.setBoundLabelLength(0);		
        this.contrunhuayoujiecun.setBoundLabelUnderline(true);		
        this.contrunhuayoujiecun.setVisible(true);
        // contrunhuayougangzuo		
        this.contrunhuayougangzuo.setBoundLabelText(resHelper.getString("contrunhuayougangzuo.boundLabelText"));		
        this.contrunhuayougangzuo.setBoundLabelLength(0);		
        this.contrunhuayougangzuo.setBoundLabelUnderline(true);		
        this.contrunhuayougangzuo.setVisible(true);
        // contrunhuayouxiao		
        this.contrunhuayouxiao.setBoundLabelText(resHelper.getString("contrunhuayouxiao.boundLabelText"));		
        this.contrunhuayouxiao.setBoundLabelLength(0);		
        this.contrunhuayouxiao.setBoundLabelUnderline(true);		
        this.contrunhuayouxiao.setVisible(true);
        // contrunhuayouben		
        this.contrunhuayouben.setBoundLabelText(resHelper.getString("contrunhuayouben.boundLabelText"));		
        this.contrunhuayouben.setBoundLabelLength(0);		
        this.contrunhuayouben.setBoundLabelUnderline(true);		
        this.contrunhuayouben.setVisible(true);
        // contchilunyoushang		
        this.contchilunyoushang.setBoundLabelText(resHelper.getString("contchilunyoushang.boundLabelText"));		
        this.contchilunyoushang.setBoundLabelLength(0);		
        this.contchilunyoushang.setBoundLabelUnderline(true);		
        this.contchilunyoushang.setVisible(true);
        // contchilunyougang		
        this.contchilunyougang.setBoundLabelText(resHelper.getString("contchilunyougang.boundLabelText"));		
        this.contchilunyougang.setBoundLabelLength(0);		
        this.contchilunyougang.setBoundLabelUnderline(true);		
        this.contchilunyougang.setVisible(true);
        // contchilunyouxiao		
        this.contchilunyouxiao.setBoundLabelText(resHelper.getString("contchilunyouxiao.boundLabelText"));		
        this.contchilunyouxiao.setBoundLabelLength(0);		
        this.contchilunyouxiao.setBoundLabelUnderline(true);		
        this.contchilunyouxiao.setVisible(true);
        // contchilunyouben		
        this.contchilunyouben.setBoundLabelText(resHelper.getString("contchilunyouben.boundLabelText"));		
        this.contchilunyouben.setBoundLabelLength(0);		
        this.contchilunyouben.setBoundLabelUnderline(true);		
        this.contchilunyouben.setVisible(true);
        // contyeyayoushang		
        this.contyeyayoushang.setBoundLabelText(resHelper.getString("contyeyayoushang.boundLabelText"));		
        this.contyeyayoushang.setBoundLabelLength(0);		
        this.contyeyayoushang.setBoundLabelUnderline(true);		
        this.contyeyayoushang.setVisible(true);
        // contyeyayougang		
        this.contyeyayougang.setBoundLabelText(resHelper.getString("contyeyayougang.boundLabelText"));		
        this.contyeyayougang.setBoundLabelLength(0);		
        this.contyeyayougang.setBoundLabelUnderline(true);		
        this.contyeyayougang.setVisible(true);
        // contyeyayouxiao		
        this.contyeyayouxiao.setBoundLabelText(resHelper.getString("contyeyayouxiao.boundLabelText"));		
        this.contyeyayouxiao.setBoundLabelLength(0);		
        this.contyeyayouxiao.setBoundLabelUnderline(true);		
        this.contyeyayouxiao.setVisible(true);
        // contyeyayouben		
        this.contyeyayouben.setBoundLabelText(resHelper.getString("contyeyayouben.boundLabelText"));		
        this.contyeyayouben.setBoundLabelLength(0);		
        this.contyeyayouben.setBoundLabelUnderline(true);		
        this.contyeyayouben.setVisible(true);
        // contedingrunranbi		
        this.contedingrunranbi.setBoundLabelText(resHelper.getString("contedingrunranbi.boundLabelText"));		
        this.contedingrunranbi.setBoundLabelLength(0);		
        this.contedingrunranbi.setBoundLabelUnderline(true);		
        this.contedingrunranbi.setVisible(true);
        // contshijirunranbi		
        this.contshijirunranbi.setBoundLabelText(resHelper.getString("contshijirunranbi.boundLabelText"));		
        this.contshijirunranbi.setBoundLabelLength(0);		
        this.contshijirunranbi.setBoundLabelUnderline(true);		
        this.contshijirunranbi.setVisible(true);
        // contdingeliangxx		
        this.contdingeliangxx.setBoundLabelText(resHelper.getString("contdingeliangxx.boundLabelText"));		
        this.contdingeliangxx.setBoundLabelLength(0);		
        this.contdingeliangxx.setBoundLabelUnderline(true);		
        this.contdingeliangxx.setVisible(true);
        // contshiyongliangone		
        this.contshiyongliangone.setBoundLabelText(resHelper.getString("contshiyongliangone.boundLabelText"));		
        this.contshiyongliangone.setBoundLabelLength(0);		
        this.contshiyongliangone.setBoundLabelUnderline(true);		
        this.contshiyongliangone.setVisible(true);
        // contjieone		
        this.contjieone.setBoundLabelText(resHelper.getString("contjieone.boundLabelText"));		
        this.contjieone.setBoundLabelLength(0);		
        this.contjieone.setBoundLabelUnderline(true);		
        this.contjieone.setVisible(true);
        // contchaoone		
        this.contchaoone.setBoundLabelText(resHelper.getString("contchaoone.boundLabelText"));		
        this.contchaoone.setBoundLabelLength(0);		
        this.contchaoone.setBoundLabelUnderline(true);		
        this.contchaoone.setVisible(true);
        // kDSeparator60
        // kDLabel66		
        this.kDLabel66.setText(resHelper.getString("kDLabel66.text"));
        // contlingrurhy		
        this.contlingrurhy.setBoundLabelText(resHelper.getString("contlingrurhy.boundLabelText"));		
        this.contlingrurhy.setBoundLabelLength(0);		
        this.contlingrurhy.setBoundLabelUnderline(true);		
        this.contlingrurhy.setVisible(true);
        // contlingrucly		
        this.contlingrucly.setBoundLabelText(resHelper.getString("contlingrucly.boundLabelText"));		
        this.contlingrucly.setBoundLabelLength(0);		
        this.contlingrucly.setBoundLabelUnderline(true);		
        this.contlingrucly.setVisible(true);
        // contlingruyyy		
        this.contlingruyyy.setBoundLabelText(resHelper.getString("contlingruyyy.boundLabelText"));		
        this.contlingruyyy.setBoundLabelLength(0);		
        this.contlingruyyy.setBoundLabelUnderline(true);		
        this.contlingruyyy.setVisible(true);
        // txtrunhuayoujiecun		
        this.txtrunhuayoujiecun.setVisible(true);		
        this.txtrunhuayoujiecun.setHorizontalAlignment(2);		
        this.txtrunhuayoujiecun.setMaxLength(100);		
        this.txtrunhuayoujiecun.setRequired(false);
        // txtrunhuayougangzuo		
        this.txtrunhuayougangzuo.setVisible(true);		
        this.txtrunhuayougangzuo.setHorizontalAlignment(2);		
        this.txtrunhuayougangzuo.setMaxLength(100);		
        this.txtrunhuayougangzuo.setRequired(false);
        // txtrunhuayouxiao		
        this.txtrunhuayouxiao.setVisible(true);		
        this.txtrunhuayouxiao.setHorizontalAlignment(2);		
        this.txtrunhuayouxiao.setMaxLength(100);		
        this.txtrunhuayouxiao.setRequired(false);
        // txtrunhuayouben		
        this.txtrunhuayouben.setVisible(true);		
        this.txtrunhuayouben.setHorizontalAlignment(2);		
        this.txtrunhuayouben.setMaxLength(100);		
        this.txtrunhuayouben.setRequired(false);
        // txtchilunyoushang		
        this.txtchilunyoushang.setVisible(true);		
        this.txtchilunyoushang.setHorizontalAlignment(2);		
        this.txtchilunyoushang.setMaxLength(100);		
        this.txtchilunyoushang.setRequired(false);
        // txtchilunyougang		
        this.txtchilunyougang.setVisible(true);		
        this.txtchilunyougang.setHorizontalAlignment(2);		
        this.txtchilunyougang.setMaxLength(100);		
        this.txtchilunyougang.setRequired(false);
        // txtchilunyouxiao		
        this.txtchilunyouxiao.setVisible(true);		
        this.txtchilunyouxiao.setHorizontalAlignment(2);		
        this.txtchilunyouxiao.setMaxLength(100);		
        this.txtchilunyouxiao.setRequired(false);
        // txtchilunyouben		
        this.txtchilunyouben.setVisible(true);		
        this.txtchilunyouben.setHorizontalAlignment(2);		
        this.txtchilunyouben.setMaxLength(100);		
        this.txtchilunyouben.setRequired(false);
        // txtyeyayoushang		
        this.txtyeyayoushang.setVisible(true);		
        this.txtyeyayoushang.setHorizontalAlignment(2);		
        this.txtyeyayoushang.setMaxLength(100);		
        this.txtyeyayoushang.setRequired(false);
        // txtyeyayougang		
        this.txtyeyayougang.setVisible(true);		
        this.txtyeyayougang.setHorizontalAlignment(2);		
        this.txtyeyayougang.setMaxLength(100);		
        this.txtyeyayougang.setRequired(false);
        // txtyeyayouxiao		
        this.txtyeyayouxiao.setVisible(true);		
        this.txtyeyayouxiao.setHorizontalAlignment(2);		
        this.txtyeyayouxiao.setMaxLength(100);		
        this.txtyeyayouxiao.setRequired(false);
        // txtyeyayouben		
        this.txtyeyayouben.setVisible(true);		
        this.txtyeyayouben.setHorizontalAlignment(2);		
        this.txtyeyayouben.setMaxLength(100);		
        this.txtyeyayouben.setRequired(false);
        // txtedingrunranbi		
        this.txtedingrunranbi.setVisible(true);		
        this.txtedingrunranbi.setHorizontalAlignment(2);		
        this.txtedingrunranbi.setMaxLength(100);		
        this.txtedingrunranbi.setRequired(false);
        // txtshijirunranbi		
        this.txtshijirunranbi.setVisible(true);		
        this.txtshijirunranbi.setHorizontalAlignment(2);		
        this.txtshijirunranbi.setMaxLength(100);		
        this.txtshijirunranbi.setRequired(false);
        // txtdingeliangxx		
        this.txtdingeliangxx.setVisible(true);		
        this.txtdingeliangxx.setHorizontalAlignment(2);		
        this.txtdingeliangxx.setMaxLength(100);		
        this.txtdingeliangxx.setRequired(false);
        // txtshiyongliangone		
        this.txtshiyongliangone.setVisible(true);		
        this.txtshiyongliangone.setHorizontalAlignment(2);		
        this.txtshiyongliangone.setMaxLength(100);		
        this.txtshiyongliangone.setRequired(false);
        // txtjieone		
        this.txtjieone.setVisible(true);		
        this.txtjieone.setHorizontalAlignment(2);		
        this.txtjieone.setMaxLength(100);		
        this.txtjieone.setRequired(false);
        // txtchaoone		
        this.txtchaoone.setVisible(true);		
        this.txtchaoone.setHorizontalAlignment(2);		
        this.txtchaoone.setMaxLength(100);		
        this.txtchaoone.setRequired(false);
        // txtlingrurhy		
        this.txtlingrurhy.setVisible(true);		
        this.txtlingrurhy.setHorizontalAlignment(2);		
        this.txtlingrurhy.setMaxLength(100);		
        this.txtlingrurhy.setRequired(false);
        // txtlingrucly		
        this.txtlingrucly.setVisible(true);		
        this.txtlingrucly.setHorizontalAlignment(2);		
        this.txtlingrucly.setMaxLength(100);		
        this.txtlingrucly.setRequired(false);
        // txtlingruyyy		
        this.txtlingruyyy.setVisible(true);		
        this.txtlingruyyy.setHorizontalAlignment(2);		
        this.txtlingruyyy.setMaxLength(100);		
        this.txtlingruyyy.setRequired(false);
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
        // kDLabel11		
        this.kDLabel11.setText(resHelper.getString("kDLabel11.text"));
        // kDSeparator13
        // contselfLeve		
        this.contselfLeve.setBoundLabelText(resHelper.getString("contselfLeve.boundLabelText"));		
        this.contselfLeve.setBoundLabelLength(132);		
        this.contselfLeve.setBoundLabelUnderline(true);		
        this.contselfLeve.setVisible(true);
        // txtselfLeve		
        this.txtselfLeve.setVisible(true);		
        this.txtselfLeve.setHorizontalAlignment(2);		
        this.txtselfLeve.setMaxLength(100);		
        this.txtselfLeve.setRequired(false);
        // kDSeparator52
        // kDSeparator53
        // kDSeparator54
        // kDLabel59		
        this.kDLabel59.setText(resHelper.getString("kDLabel59.text"));
        // kDLabel60		
        this.kDLabel60.setText(resHelper.getString("kDLabel60.text"));
        // kDLabel61		
        this.kDLabel61.setText(resHelper.getString("kDLabel61.text"));
        // kDSeparator55
        // kDSeparator56		
        this.kDSeparator56.setOrientation(1);
        // kDSeparator57		
        this.kDSeparator57.setOrientation(1);
        // kDSeparator58		
        this.kDSeparator58.setOrientation(1);
        // kDSeparator59		
        this.kDSeparator59.setOrientation(1);
        // kDLabel62		
        this.kDLabel62.setText(resHelper.getString("kDLabel62.text"));
        // kDLabel63		
        this.kDLabel63.setText(resHelper.getString("kDLabel63.text"));
        // kDLabel64		
        this.kDLabel64.setText(resHelper.getString("kDLabel64.text"));
        // kDLabel65		
        this.kDLabel65.setText(resHelper.getString("kDLabel65.text"));
        // contzuobenyue		
        this.contzuobenyue.setBoundLabelText(resHelper.getString("contzuobenyue.boundLabelText"));		
        this.contzuobenyue.setBoundLabelLength(0);		
        this.contzuobenyue.setBoundLabelUnderline(true);		
        this.contzuobenyue.setVisible(true);
        // contyoujibenyue		
        this.contyoujibenyue.setBoundLabelText(resHelper.getString("contyoujibenyue.boundLabelText"));		
        this.contyoujibenyue.setBoundLabelLength(0);		
        this.contyoujibenyue.setBoundLabelUnderline(true);		
        this.contyoujibenyue.setVisible(true);
        // contfujibenyue		
        this.contfujibenyue.setBoundLabelText(resHelper.getString("contfujibenyue.boundLabelText"));		
        this.contfujibenyue.setBoundLabelLength(0);		
        this.contfujibenyue.setBoundLabelUnderline(true);		
        this.contfujibenyue.setVisible(true);
        // contdianbiaobenyue		
        this.contdianbiaobenyue.setBoundLabelText(resHelper.getString("contdianbiaobenyue.boundLabelText"));		
        this.contdianbiaobenyue.setBoundLabelLength(0);		
        this.contdianbiaobenyue.setBoundLabelUnderline(true);		
        this.contdianbiaobenyue.setVisible(true);
        // contdianbiaoheji		
        this.contdianbiaoheji.setBoundLabelText(resHelper.getString("contdianbiaoheji.boundLabelText"));		
        this.contdianbiaoheji.setBoundLabelLength(0);		
        this.contdianbiaoheji.setBoundLabelUnderline(true);		
        this.contdianbiaoheji.setVisible(true);
        // contdianbiaoshangyue		
        this.contdianbiaoshangyue.setBoundLabelText(resHelper.getString("contdianbiaoshangyue.boundLabelText"));		
        this.contdianbiaoshangyue.setBoundLabelLength(0);		
        this.contdianbiaoshangyue.setBoundLabelUnderline(true);		
        this.contdianbiaoshangyue.setVisible(true);
        // contfujiheji		
        this.contfujiheji.setBoundLabelText(resHelper.getString("contfujiheji.boundLabelText"));		
        this.contfujiheji.setBoundLabelLength(0);		
        this.contfujiheji.setBoundLabelUnderline(true);		
        this.contfujiheji.setVisible(true);
        // contfujishangyue		
        this.contfujishangyue.setBoundLabelText(resHelper.getString("contfujishangyue.boundLabelText"));		
        this.contfujishangyue.setBoundLabelLength(0);		
        this.contfujishangyue.setBoundLabelUnderline(true);		
        this.contfujishangyue.setVisible(true);
        // contyoujiheji		
        this.contyoujiheji.setBoundLabelText(resHelper.getString("contyoujiheji.boundLabelText"));		
        this.contyoujiheji.setBoundLabelLength(0);		
        this.contyoujiheji.setBoundLabelUnderline(true);		
        this.contyoujiheji.setVisible(true);
        // contyoujishangyue		
        this.contyoujishangyue.setBoundLabelText(resHelper.getString("contyoujishangyue.boundLabelText"));		
        this.contyoujishangyue.setBoundLabelLength(0);		
        this.contyoujishangyue.setBoundLabelUnderline(true);		
        this.contyoujishangyue.setVisible(true);
        // contzuoheji		
        this.contzuoheji.setBoundLabelText(resHelper.getString("contzuoheji.boundLabelText"));		
        this.contzuoheji.setBoundLabelLength(0);		
        this.contzuoheji.setBoundLabelUnderline(true);		
        this.contzuoheji.setVisible(true);
        // contzuoshangyue		
        this.contzuoshangyue.setBoundLabelText(resHelper.getString("contzuoshangyue.boundLabelText"));		
        this.contzuoshangyue.setBoundLabelLength(0);		
        this.contzuoshangyue.setBoundLabelUnderline(true);		
        this.contzuoshangyue.setVisible(true);
        // txtzuobenyue		
        this.txtzuobenyue.setVisible(true);		
        this.txtzuobenyue.setHorizontalAlignment(2);		
        this.txtzuobenyue.setMaxLength(100);		
        this.txtzuobenyue.setRequired(false);
        // txtyoujibenyue		
        this.txtyoujibenyue.setVisible(true);		
        this.txtyoujibenyue.setHorizontalAlignment(2);		
        this.txtyoujibenyue.setMaxLength(100);		
        this.txtyoujibenyue.setRequired(false);
        // txtfujibenyue		
        this.txtfujibenyue.setVisible(true);		
        this.txtfujibenyue.setHorizontalAlignment(2);		
        this.txtfujibenyue.setMaxLength(100);		
        this.txtfujibenyue.setRequired(false);
        // txtdianbiaobenyue		
        this.txtdianbiaobenyue.setVisible(true);		
        this.txtdianbiaobenyue.setHorizontalAlignment(2);		
        this.txtdianbiaobenyue.setMaxLength(100);		
        this.txtdianbiaobenyue.setRequired(false);
        // txtdianbiaoheji		
        this.txtdianbiaoheji.setVisible(true);		
        this.txtdianbiaoheji.setHorizontalAlignment(2);		
        this.txtdianbiaoheji.setMaxLength(100);		
        this.txtdianbiaoheji.setRequired(false);
        // txtdianbiaoshangyue		
        this.txtdianbiaoshangyue.setVisible(true);		
        this.txtdianbiaoshangyue.setHorizontalAlignment(2);		
        this.txtdianbiaoshangyue.setMaxLength(100);		
        this.txtdianbiaoshangyue.setRequired(false);
        // txtfujiheji		
        this.txtfujiheji.setVisible(true);		
        this.txtfujiheji.setHorizontalAlignment(2);		
        this.txtfujiheji.setMaxLength(100);		
        this.txtfujiheji.setRequired(false);
        // txtfujishangyue		
        this.txtfujishangyue.setVisible(true);		
        this.txtfujishangyue.setHorizontalAlignment(2);		
        this.txtfujishangyue.setMaxLength(100);		
        this.txtfujishangyue.setRequired(false);
        // txtyoujiheji		
        this.txtyoujiheji.setVisible(true);		
        this.txtyoujiheji.setHorizontalAlignment(2);		
        this.txtyoujiheji.setMaxLength(100);		
        this.txtyoujiheji.setRequired(false);
        // txtyoujishangyue		
        this.txtyoujishangyue.setVisible(true);		
        this.txtyoujishangyue.setHorizontalAlignment(2);		
        this.txtyoujishangyue.setMaxLength(100);		
        this.txtyoujishangyue.setRequired(false);
        // txtzuoheji		
        this.txtzuoheji.setVisible(true);		
        this.txtzuoheji.setHorizontalAlignment(2);		
        this.txtzuoheji.setMaxLength(100);		
        this.txtzuoheji.setRequired(false);
        // txtzuoshangyue		
        this.txtzuoshangyue.setVisible(true);		
        this.txtzuoshangyue.setHorizontalAlignment(2);		
        this.txtzuoshangyue.setMaxLength(100);		
        this.txtzuoshangyue.setRequired(false);
        // contnote		
        this.contnote.setBoundLabelText(resHelper.getString("contnote.boundLabelText"));		
        this.contnote.setBoundLabelLength(25);		
        this.contnote.setBoundLabelUnderline(true);		
        this.contnote.setVisible(true);		
        this.contnote.setBoundLabelAlignment(8);
        // contleijiyongdian		
        this.contleijiyongdian.setBoundLabelText(resHelper.getString("contleijiyongdian.boundLabelText"));		
        this.contleijiyongdian.setBoundLabelLength(96);		
        this.contleijiyongdian.setBoundLabelUnderline(true);		
        this.contleijiyongdian.setVisible(true);
        // contleijiranyou		
        this.contleijiranyou.setBoundLabelText(resHelper.getString("contleijiranyou.boundLabelText"));		
        this.contleijiranyou.setBoundLabelLength(102);		
        this.contleijiranyou.setBoundLabelUnderline(true);		
        this.contleijiranyou.setVisible(true);
        // contleijiyunshi		
        this.contleijiyunshi.setBoundLabelText(resHelper.getString("contleijiyunshi.boundLabelText"));		
        this.contleijiyunshi.setBoundLabelLength(96);		
        this.contleijiyunshi.setBoundLabelUnderline(true);		
        this.contleijiyunshi.setVisible(true);
        // contleijichanzhi		
        this.contleijichanzhi.setBoundLabelText(resHelper.getString("contleijichanzhi.boundLabelText"));		
        this.contleijichanzhi.setBoundLabelLength(96);		
        this.contleijichanzhi.setBoundLabelUnderline(true);		
        this.contleijichanzhi.setVisible(true);
        // scrollPanenote
        // txtnote		
        this.txtnote.setVisible(true);		
        this.txtnote.setRequired(false);		
        this.txtnote.setMaxLength(1000);
        // txtleijiyongdian		
        this.txtleijiyongdian.setVisible(true);		
        this.txtleijiyongdian.setHorizontalAlignment(2);		
        this.txtleijiyongdian.setMaxLength(100);		
        this.txtleijiyongdian.setRequired(false);
        // txtleijiranyou		
        this.txtleijiranyou.setVisible(true);		
        this.txtleijiranyou.setHorizontalAlignment(2);		
        this.txtleijiranyou.setMaxLength(100);		
        this.txtleijiranyou.setRequired(false);
        // txtleijiyunshi		
        this.txtleijiyunshi.setVisible(true);		
        this.txtleijiyunshi.setHorizontalAlignment(2);		
        this.txtleijiyunshi.setMaxLength(100);		
        this.txtleijiyunshi.setRequired(false);
        // txtleijichanzhi		
        this.txtleijichanzhi.setVisible(true);		
        this.txtleijichanzhi.setHorizontalAlignment(2);		
        this.txtleijichanzhi.setMaxLength(100);		
        this.txtleijichanzhi.setRequired(false);
        // kDLabel53		
        this.kDLabel53.setText(resHelper.getString("kDLabel53.text"));
        // kDSeparator41
        // kDLabel54		
        this.kDLabel54.setText(resHelper.getString("kDLabel54.text"));
        // kDSeparator42
        // kDLabel55		
        this.kDLabel55.setText(resHelper.getString("kDLabel55.text"));
        // kDSeparator43
        // kDLabel56		
        this.kDLabel56.setText(resHelper.getString("kDLabel56.text"));
        // kDLabel57		
        this.kDLabel57.setText(resHelper.getString("kDLabel57.text"));
        // kDLabel58		
        this.kDLabel58.setText(resHelper.getString("kDLabel58.text"));
        // kDSeparator44
        // kDSeparator45
        // kDSeparator46
        // kDSeparator47
        // kDSeparator48
        // kDSeparator49		
        this.kDSeparator49.setOrientation(1);
        // kDSeparator50
        // kDSeparator51		
        this.kDSeparator51.setOrientation(1);
        // contjishudinge		
        this.contjishudinge.setBoundLabelText(resHelper.getString("contjishudinge.boundLabelText"));		
        this.contjishudinge.setBoundLabelLength(0);		
        this.contjishudinge.setBoundLabelUnderline(true);		
        this.contjishudinge.setVisible(true);
        // contchanzhidinge		
        this.contchanzhidinge.setBoundLabelText(resHelper.getString("contchanzhidinge.boundLabelText"));		
        this.contchanzhidinge.setBoundLabelLength(0);		
        this.contchanzhidinge.setBoundLabelUnderline(true);		
        this.contchanzhidinge.setVisible(true);
        // contjishubenyue		
        this.contjishubenyue.setBoundLabelText(resHelper.getString("contjishubenyue.boundLabelText"));		
        this.contjishubenyue.setBoundLabelLength(0);		
        this.contjishubenyue.setBoundLabelUnderline(true);		
        this.contjishubenyue.setVisible(true);
        // contchanzhibenyue		
        this.contchanzhibenyue.setBoundLabelText(resHelper.getString("contchanzhibenyue.boundLabelText"));		
        this.contchanzhibenyue.setBoundLabelLength(0);		
        this.contchanzhibenyue.setBoundLabelUnderline(true);		
        this.contchanzhibenyue.setVisible(true);
        // contjishuleiji		
        this.contjishuleiji.setBoundLabelText(resHelper.getString("contjishuleiji.boundLabelText"));		
        this.contjishuleiji.setBoundLabelLength(0);		
        this.contjishuleiji.setBoundLabelUnderline(true);		
        this.contjishuleiji.setVisible(true);
        // contchanzhileiji		
        this.contchanzhileiji.setBoundLabelText(resHelper.getString("contchanzhileiji.boundLabelText"));		
        this.contchanzhileiji.setBoundLabelLength(0);		
        this.contchanzhileiji.setBoundLabelUnderline(true);		
        this.contchanzhileiji.setVisible(true);
        // txtjishudinge		
        this.txtjishudinge.setVisible(true);		
        this.txtjishudinge.setHorizontalAlignment(2);		
        this.txtjishudinge.setMaxLength(100);		
        this.txtjishudinge.setRequired(false);
        // txtchanzhidinge		
        this.txtchanzhidinge.setVisible(true);		
        this.txtchanzhidinge.setHorizontalAlignment(2);		
        this.txtchanzhidinge.setMaxLength(100);		
        this.txtchanzhidinge.setRequired(false);
        // txtjishubenyue		
        this.txtjishubenyue.setVisible(true);		
        this.txtjishubenyue.setHorizontalAlignment(2);		
        this.txtjishubenyue.setMaxLength(100);		
        this.txtjishubenyue.setRequired(false);
        // txtchanzhibenyue		
        this.txtchanzhibenyue.setVisible(true);		
        this.txtchanzhibenyue.setHorizontalAlignment(2);		
        this.txtchanzhibenyue.setMaxLength(100);		
        this.txtchanzhibenyue.setRequired(false);
        // txtjishuleiji		
        this.txtjishuleiji.setVisible(true);		
        this.txtjishuleiji.setHorizontalAlignment(2);		
        this.txtjishuleiji.setMaxLength(100);		
        this.txtjishuleiji.setRequired(false);
        // txtchanzhileiji		
        this.txtchanzhileiji.setVisible(true);		
        this.txtchanzhileiji.setHorizontalAlignment(2);		
        this.txtchanzhileiji.setMaxLength(100);		
        this.txtchanzhileiji.setRequired(false);
        // prmtchuanzhang		
        this.prmtchuanzhang.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtchuanzhang.setVisible(true);		
        this.prmtchuanzhang.setEditable(true);		
        this.prmtchuanzhang.setDisplayFormat("$name$");		
        this.prmtchuanzhang.setEditFormat("$number$");		
        this.prmtchuanzhang.setCommitFormat("$number$");		
        this.prmtchuanzhang.setRequired(false);
        // prmtlunjizhang		
        this.prmtlunjizhang.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtlunjizhang.setVisible(true);		
        this.prmtlunjizhang.setEditable(true);		
        this.prmtlunjizhang.setDisplayFormat("$name$");		
        this.prmtlunjizhang.setEditFormat("$number$");		
        this.prmtlunjizhang.setCommitFormat("$number$");		
        this.prmtlunjizhang.setRequired(false);
        // prmtreportMonth		
        this.prmtreportMonth.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtreportMonth.setVisible(true);		
        this.prmtreportMonth.setEditable(true);		
        this.prmtreportMonth.setDisplayFormat("$number$");		
        this.prmtreportMonth.setEditFormat("$number$");		
        this.prmtreportMonth.setCommitFormat("$number$");		
        this.prmtreportMonth.setRequired(false);
        this.prmtreportMonth.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreportMonth_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtshipName,txtPower,txtlastMonth,txtqione,txtqitwo,txtzhione,txtzhitwo,txtmiduone,txtmidutwo,txtintoTotal,txtqithree,txtqifour,txtzhithree,txtzhifour,txtmiduthree,txtmidufour,txttotalConsum,txtmonthBalance,txtportShipment,txtsmallTransport,txtTotal,txtoutputValue,txtgzde,txtgzdel,txtxyzde,txtxyzdel,txthjde,txthjdel,txtchanzhione,txtchanzhitwo,txtzhdel,txtshiyongliang,txtjieyou,txtchaohao,txtselfLeve,txtnote,txtleijiyongdian,txtleijiranyou,txtleijiyunshi,txtleijichanzhi,txtrunhuayoujiecun,txtrunhuayougangzuo,txtrunhuayouxiao,txtrunhuayouben,txtchilunyoushang,txtchilunyougang,txtchilunyouxiao,txtchilunyouben,txtyeyayoushang,txtyeyayougang,txtyeyayouxiao,txtyeyayouben,txtedingrunranbi,txtshijirunranbi,txtdingeliangxx,txtshiyongliangone,txtjieone,txtchaoone,txtjishudinge,txtchanzhidinge,txtjishubenyue,txtchanzhibenyue,txtjishuleiji,txtchanzhileiji,txtzuobenyue,txtyoujibenyue,txtfujibenyue,txtdianbiaobenyue,txtzuoshangyue,txtzuoheji,txtyoujishangyue,txtyoujiheji,txtfujishangyue,txtfujiheji,txtdianbiaoshangyue,txtdianbiaoheji,prmtchuanzhang,prmtlunjizhang,txtlingrurhy,txtlingrucly,txtlingruyyy,prmtreportMonth}));
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
        this.setBounds(new Rectangle(10, 10, 963, 610));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 963, 610));
        contCreator.setBounds(new Rectangle(7, 585, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(7, 585, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(7, 564, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(7, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(347, 564, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(347, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(347, 585, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(347, 585, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(687, 7, 270, 19));
        this.add(contCU, new KDLayout.Constraints(687, 7, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(7, 7, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(7, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(985, 88, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(985, 88, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(990, 18, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(990, 18, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(687, 543, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(687, 543, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(687, 29, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(687, 29, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(996, 559, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(996, 559, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(687, 564, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(687, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contshipName.setBounds(new Rectangle(7, 29, 270, 19));
        this.add(contshipName, new KDLayout.Constraints(7, 29, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPower.setBounds(new Rectangle(347, 29, 270, 19));
        this.add(contPower, new KDLayout.Constraints(347, 29, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(7, 51, 950, 489));
        this.add(kDPanel1, new KDLayout.Constraints(7, 51, 950, 489, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contchuanzhang.setBounds(new Rectangle(7, 543, 270, 19));
        this.add(contchuanzhang, new KDLayout.Constraints(7, 543, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlunjizhang.setBounds(new Rectangle(347, 543, 270, 19));
        this.add(contlunjizhang, new KDLayout.Constraints(347, 543, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreportMonth.setBounds(new Rectangle(347, 7, 270, 19));
        this.add(contreportMonth, new KDLayout.Constraints(347, 7, 270, 19, 0));
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
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contshipName
        contshipName.setBoundEditor(prmtshipName);
        //contPower
        contPower.setBoundEditor(txtPower);
        //kDPanel1
        kDPanel1.setLayout(null);        kDPanel2.setBounds(new Rectangle(13, 12, 341, 462));
        kDPanel1.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(356, 12, 336, 249));
        kDPanel1.add(kDPanel3, null);
        kDPanel4.setBounds(new Rectangle(694, 13, 242, 126));
        kDPanel1.add(kDPanel4, null);
        kDPanel5.setBounds(new Rectangle(355, 369, 338, 105));
        kDPanel1.add(kDPanel5, null);
        kDPanel6.setBounds(new Rectangle(695, 140, 241, 334));
        kDPanel1.add(kDPanel6, null);
        kDPanel7.setBounds(new Rectangle(356, 262, 336, 105));
        kDPanel1.add(kDPanel7, null);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(13, 12, 341, 462));        kDLabel1.setBounds(new Rectangle(119, 9, 111, 20));
        kDPanel2.add(kDLabel1, new KDLayout.Constraints(119, 9, 111, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(31, 35, 52, 16));
        kDPanel2.add(kDLabel2, new KDLayout.Constraints(31, 35, 52, 16, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel3.setBounds(new Rectangle(162, 35, 55, 20));
        kDPanel2.add(kDLabel3, new KDLayout.Constraints(162, 35, 55, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(7, 52, 324, 8));
        kDPanel2.add(kDSeparator8, new KDLayout.Constraints(7, 52, 324, 8, 0));
        kDSeparator10.setBounds(new Rectangle(7, 27, 323, 10));
        kDPanel2.add(kDSeparator10, new KDLayout.Constraints(7, 27, 323, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator14.setBounds(new Rectangle(89, 27, 8, 434));
        kDPanel2.add(kDSeparator14, new KDLayout.Constraints(89, 27, 8, 434, 0));
        kDLabel12.setBounds(new Rectangle(28, 55, 56, 20));
        kDPanel2.add(kDLabel12, new KDLayout.Constraints(28, 55, 56, 20, 0));
        kDSeparator15.setBounds(new Rectangle(10, 78, 321, 9));
        kDPanel2.add(kDSeparator15, new KDLayout.Constraints(10, 78, 321, 9, 0));
        kDLabel13.setBounds(new Rectangle(30, 94, 53, 20));
        kDPanel2.add(kDLabel13, new KDLayout.Constraints(30, 94, 53, 20, 0));
        kDLabel20.setBounds(new Rectangle(146, 108, 15, 20));
        kDPanel2.add(kDLabel20, new KDLayout.Constraints(146, 108, 15, 20, 0));
        kDLabel24.setBounds(new Rectangle(298, 108, 26, 20));
        kDPanel2.add(kDLabel24, new KDLayout.Constraints(298, 108, 26, 20, 0));
        kDSeparator16.setBounds(new Rectangle(6, 130, 325, 10));
        kDPanel2.add(kDSeparator16, new KDLayout.Constraints(6, 130, 325, 10, 0));
        kDLabel25.setBounds(new Rectangle(29, 134, 53, 20));
        kDPanel2.add(kDLabel25, new KDLayout.Constraints(29, 134, 53, 20, 0));
        kDSeparator17.setBounds(new Rectangle(10, 156, 320, 8));
        kDPanel2.add(kDSeparator17, new KDLayout.Constraints(10, 156, 320, 8, 0));
        kDLabel27.setBounds(new Rectangle(147, 83, 13, 20));
        kDPanel2.add(kDLabel27, new KDLayout.Constraints(147, 83, 13, 20, 0));
        kDLabel30.setBounds(new Rectangle(297, 83, 25, 20));
        kDPanel2.add(kDLabel30, new KDLayout.Constraints(297, 83, 25, 20, 0));
        kDLabel32.setBounds(new Rectangle(28, 160, 53, 20));
        kDPanel2.add(kDLabel32, new KDLayout.Constraints(28, 160, 53, 20, 0));
        kDLabel34.setBounds(new Rectangle(145, 160, 15, 20));
        kDPanel2.add(kDLabel34, new KDLayout.Constraints(145, 160, 15, 20, 0));
        kDLabel37.setBounds(new Rectangle(297, 163, 26, 20));
        kDPanel2.add(kDLabel37, new KDLayout.Constraints(297, 163, 26, 20, 0));
        kDLabel38.setBounds(new Rectangle(219, 83, 9, 19));
        kDPanel2.add(kDLabel38, new KDLayout.Constraints(219, 83, 9, 19, 0));
        kDLabel39.setBounds(new Rectangle(219, 108, 9, 19));
        kDPanel2.add(kDLabel39, new KDLayout.Constraints(219, 108, 9, 19, 0));
        kDLabel40.setBounds(new Rectangle(218, 160, 8, 19));
        kDPanel2.add(kDLabel40, new KDLayout.Constraints(218, 160, 8, 19, 0));
        kDSeparator18.setBounds(new Rectangle(10, 182, 321, 8));
        kDPanel2.add(kDSeparator18, new KDLayout.Constraints(10, 182, 321, 8, 0));
        kDLabel41.setBounds(new Rectangle(21, 185, 69, 20));
        kDPanel2.add(kDLabel41, new KDLayout.Constraints(21, 185, 69, 20, 0));
        kDSeparator19.setBounds(new Rectangle(9, 206, 322, 8));
        kDPanel2.add(kDSeparator19, new KDLayout.Constraints(9, 206, 322, 8, 0));
        kDLabel45.setBounds(new Rectangle(218, 186, 8, 19));
        kDPanel2.add(kDLabel45, new KDLayout.Constraints(218, 186, 8, 19, 0));
        kDLabel47.setBounds(new Rectangle(297, 185, 26, 20));
        kDPanel2.add(kDLabel47, new KDLayout.Constraints(297, 185, 26, 20, 0));
        kDLabel16.setBounds(new Rectangle(27, 209, 53, 20));
        kDPanel2.add(kDLabel16, new KDLayout.Constraints(27, 209, 53, 20, 0));
        kDSeparator20.setBounds(new Rectangle(10, 231, 321, 10));
        kDPanel2.add(kDSeparator20, new KDLayout.Constraints(10, 231, 321, 10, 0));
        contlastMonth.setBounds(new Rectangle(93, 56, 219, 19));
        kDPanel2.add(contlastMonth, new KDLayout.Constraints(93, 56, 219, 19, 0));
        contqione.setBounds(new Rectangle(93, 83, 51, 19));
        kDPanel2.add(contqione, new KDLayout.Constraints(93, 83, 51, 19, 0));
        contqitwo.setBounds(new Rectangle(93, 107, 51, 19));
        kDPanel2.add(contqitwo, new KDLayout.Constraints(93, 107, 51, 19, 0));
        contzhione.setBounds(new Rectangle(161, 83, 56, 19));
        kDPanel2.add(contzhione, new KDLayout.Constraints(161, 83, 56, 19, 0));
        contzhitwo.setBounds(new Rectangle(161, 107, 56, 19));
        kDPanel2.add(contzhitwo, new KDLayout.Constraints(161, 107, 56, 19, 0));
        contmiduone.setBounds(new Rectangle(230, 83, 67, 19));
        kDPanel2.add(contmiduone, new KDLayout.Constraints(230, 83, 67, 19, 0));
        contmidutwo.setBounds(new Rectangle(230, 107, 67, 19));
        kDPanel2.add(contmidutwo, new KDLayout.Constraints(230, 107, 67, 19, 0));
        contintoTotal.setBounds(new Rectangle(93, 134, 221, 19));
        kDPanel2.add(contintoTotal, new KDLayout.Constraints(93, 134, 221, 19, 0));
        contqithree.setBounds(new Rectangle(92, 160, 51, 19));
        kDPanel2.add(contqithree, new KDLayout.Constraints(92, 160, 51, 19, 0));
        contqifour.setBounds(new Rectangle(92, 185, 51, 19));
        kDPanel2.add(contqifour, new KDLayout.Constraints(92, 185, 51, 19, 0));
        contzhithree.setBounds(new Rectangle(160, 160, 56, 19));
        kDPanel2.add(contzhithree, new KDLayout.Constraints(160, 160, 56, 19, 0));
        contzhifour.setBounds(new Rectangle(160, 185, 56, 19));
        kDPanel2.add(contzhifour, new KDLayout.Constraints(160, 185, 56, 19, 0));
        contmiduthree.setBounds(new Rectangle(229, 160, 67, 19));
        kDPanel2.add(contmiduthree, new KDLayout.Constraints(229, 160, 67, 19, 0));
        contmidufour.setBounds(new Rectangle(229, 185, 67, 19));
        kDPanel2.add(contmidufour, new KDLayout.Constraints(229, 185, 67, 19, 0));
        conttotalConsum.setBounds(new Rectangle(93, 210, 224, 19));
        kDPanel2.add(conttotalConsum, new KDLayout.Constraints(93, 210, 224, 19, 0));
        kDLabel14.setBounds(new Rectangle(144, 188, 16, 14));
        kDPanel2.add(kDLabel14, new KDLayout.Constraints(144, 188, 16, 14, 0));
        kDLabel15.setBounds(new Rectangle(27, 234, 54, 18));
        kDPanel2.add(kDLabel15, new KDLayout.Constraints(27, 234, 54, 18, 0));
        contmonthBalance.setBounds(new Rectangle(93, 233, 223, 19));
        kDPanel2.add(contmonthBalance, new KDLayout.Constraints(93, 233, 223, 19, 0));
        kDSeparator21.setBounds(new Rectangle(12, 254, 320, 8));
        kDPanel2.add(kDSeparator21, new KDLayout.Constraints(12, 254, 320, 8, 0));
        kDLabel17.setBounds(new Rectangle(21, 260, 60, 15));
        kDPanel2.add(kDLabel17, new KDLayout.Constraints(21, 260, 60, 15, 0));
        kDLabel18.setBounds(new Rectangle(102, 260, 60, 16));
        kDPanel2.add(kDLabel18, new KDLayout.Constraints(102, 260, 60, 16, 0));
        kDLabel19.setBounds(new Rectangle(170, 260, 74, 15));
        kDPanel2.add(kDLabel19, new KDLayout.Constraints(170, 260, 74, 15, 0));
        kDLabel21.setBounds(new Rectangle(256, 259, 71, 16));
        kDPanel2.add(kDLabel21, new KDLayout.Constraints(256, 259, 71, 16, 0));
        kDSeparator22.setBounds(new Rectangle(11, 278, 320, 10));
        kDPanel2.add(kDSeparator22, new KDLayout.Constraints(11, 278, 320, 10, 0));
        kDLabel22.setBounds(new Rectangle(24, 283, 58, 19));
        kDPanel2.add(kDLabel22, new KDLayout.Constraints(24, 283, 58, 19, 0));
        kDSeparator23.setBounds(new Rectangle(11, 305, 319, 10));
        kDPanel2.add(kDSeparator23, new KDLayout.Constraints(11, 305, 319, 10, 0));
        kDLabel23.setBounds(new Rectangle(19, 311, 61, 18));
        kDPanel2.add(kDLabel23, new KDLayout.Constraints(19, 311, 61, 18, 0));
        kDSeparator24.setBounds(new Rectangle(13, 332, 317, 10));
        kDPanel2.add(kDSeparator24, new KDLayout.Constraints(13, 332, 317, 10, 0));
        kDLabel26.setBounds(new Rectangle(22, 338, 58, 21));
        kDPanel2.add(kDLabel26, new KDLayout.Constraints(22, 338, 58, 21, 0));
        kDLabel28.setBounds(new Rectangle(18, 366, 64, 24));
        kDPanel2.add(kDLabel28, new KDLayout.Constraints(18, 366, 64, 24, 0));
        kDLabel29.setBounds(new Rectangle(11, 394, 79, 21));
        kDPanel2.add(kDLabel29, new KDLayout.Constraints(11, 394, 79, 21, 0));
        kDLabel31.setBounds(new Rectangle(100, 396, 57, 16));
        kDPanel2.add(kDLabel31, new KDLayout.Constraints(100, 396, 57, 16, 0));
        kDLabel33.setBounds(new Rectangle(183, 396, 51, 18));
        kDPanel2.add(kDLabel33, new KDLayout.Constraints(183, 396, 51, 18, 0));
        kDLabel35.setBounds(new Rectangle(264, 394, 52, 21));
        kDPanel2.add(kDLabel35, new KDLayout.Constraints(264, 394, 52, 21, 0));
        kDSeparator25.setBounds(new Rectangle(9, 361, 321, 10));
        kDPanel2.add(kDSeparator25, new KDLayout.Constraints(9, 361, 321, 10, 0));
        kDSeparator26.setBounds(new Rectangle(9, 391, 321, 10));
        kDPanel2.add(kDSeparator26, new KDLayout.Constraints(9, 391, 321, 10, 0));
        kDSeparator27.setBounds(new Rectangle(9, 417, 321, 10));
        kDPanel2.add(kDSeparator27, new KDLayout.Constraints(9, 417, 321, 10, 0));
        contportShipment.setBounds(new Rectangle(96, 283, 62, 19));
        kDPanel2.add(contportShipment, new KDLayout.Constraints(96, 283, 62, 19, 0));
        contsmallTransport.setBounds(new Rectangle(96, 310, 61, 19));
        kDPanel2.add(contsmallTransport, new KDLayout.Constraints(96, 310, 61, 19, 0));
        contTotal.setBounds(new Rectangle(96, 339, 60, 19));
        kDPanel2.add(contTotal, new KDLayout.Constraints(96, 339, 60, 19, 0));
        contoutputValue.setBounds(new Rectangle(96, 368, 60, 19));
        kDPanel2.add(contoutputValue, new KDLayout.Constraints(96, 368, 60, 19, 0));
        contgzde.setBounds(new Rectangle(169, 282, 75, 19));
        kDPanel2.add(contgzde, new KDLayout.Constraints(169, 282, 75, 19, 0));
        contgzdel.setBounds(new Rectangle(252, 283, 70, 19));
        kDPanel2.add(contgzdel, new KDLayout.Constraints(252, 283, 70, 19, 0));
        contxyzde.setBounds(new Rectangle(169, 310, 75, 19));
        kDPanel2.add(contxyzde, new KDLayout.Constraints(169, 310, 75, 19, 0));
        contxyzdel.setBounds(new Rectangle(251, 310, 71, 19));
        kDPanel2.add(contxyzdel, new KDLayout.Constraints(251, 310, 71, 19, 0));
        conthjde.setBounds(new Rectangle(169, 339, 77, 19));
        kDPanel2.add(conthjde, new KDLayout.Constraints(169, 339, 77, 19, 0));
        conthjdel.setBounds(new Rectangle(251, 339, 72, 19));
        kDPanel2.add(conthjdel, new KDLayout.Constraints(251, 339, 72, 19, 0));
        contchanzhione.setBounds(new Rectangle(169, 368, 76, 19));
        kDPanel2.add(contchanzhione, new KDLayout.Constraints(169, 368, 76, 19, 0));
        contchanzhitwo.setBounds(new Rectangle(251, 368, 71, 19));
        kDPanel2.add(contchanzhitwo, new KDLayout.Constraints(251, 368, 71, 19, 0));
        kDSeparator28.setBounds(new Rectangle(164, 256, 8, 192));
        kDPanel2.add(kDSeparator28, new KDLayout.Constraints(164, 256, 8, 192, 0));
        kDSeparator29.setBounds(new Rectangle(248, 259, 8, 191));
        kDPanel2.add(kDSeparator29, new KDLayout.Constraints(248, 259, 8, 191, 0));
        contzhdel.setBounds(new Rectangle(16, 421, 69, 19));
        kDPanel2.add(contzhdel, new KDLayout.Constraints(16, 421, 69, 19, 0));
        contshiyongliang.setBounds(new Rectangle(96, 421, 63, 19));
        kDPanel2.add(contshiyongliang, new KDLayout.Constraints(96, 421, 63, 19, 0));
        contjieyou.setBounds(new Rectangle(169, 421, 77, 19));
        kDPanel2.add(contjieyou, new KDLayout.Constraints(169, 421, 77, 19, 0));
        contchaohao.setBounds(new Rectangle(252, 421, 70, 19));
        kDPanel2.add(contchaohao, new KDLayout.Constraints(252, 421, 70, 19, 0));
        //contlastMonth
        contlastMonth.setBoundEditor(txtlastMonth);
        //contqione
        contqione.setBoundEditor(txtqione);
        //contqitwo
        contqitwo.setBoundEditor(txtqitwo);
        //contzhione
        contzhione.setBoundEditor(txtzhione);
        //contzhitwo
        contzhitwo.setBoundEditor(txtzhitwo);
        //contmiduone
        contmiduone.setBoundEditor(txtmiduone);
        //contmidutwo
        contmidutwo.setBoundEditor(txtmidutwo);
        //contintoTotal
        contintoTotal.setBoundEditor(txtintoTotal);
        //contqithree
        contqithree.setBoundEditor(txtqithree);
        //contqifour
        contqifour.setBoundEditor(txtqifour);
        //contzhithree
        contzhithree.setBoundEditor(txtzhithree);
        //contzhifour
        contzhifour.setBoundEditor(txtzhifour);
        //contmiduthree
        contmiduthree.setBoundEditor(txtmiduthree);
        //contmidufour
        contmidufour.setBoundEditor(txtmidufour);
        //conttotalConsum
        conttotalConsum.setBoundEditor(txttotalConsum);
        //contmonthBalance
        contmonthBalance.setBoundEditor(txtmonthBalance);
        //contportShipment
        contportShipment.setBoundEditor(txtportShipment);
        //contsmallTransport
        contsmallTransport.setBoundEditor(txtsmallTransport);
        //contTotal
        contTotal.setBoundEditor(txtTotal);
        //contoutputValue
        contoutputValue.setBoundEditor(txtoutputValue);
        //contgzde
        contgzde.setBoundEditor(txtgzde);
        //contgzdel
        contgzdel.setBoundEditor(txtgzdel);
        //contxyzde
        contxyzde.setBoundEditor(txtxyzde);
        //contxyzdel
        contxyzdel.setBoundEditor(txtxyzdel);
        //conthjde
        conthjde.setBoundEditor(txthjde);
        //conthjdel
        conthjdel.setBoundEditor(txthjdel);
        //contchanzhione
        contchanzhione.setBoundEditor(txtchanzhione);
        //contchanzhitwo
        contchanzhitwo.setBoundEditor(txtchanzhitwo);
        //contzhdel
        contzhdel.setBoundEditor(txtzhdel);
        //contshiyongliang
        contshiyongliang.setBoundEditor(txtshiyongliang);
        //contjieyou
        contjieyou.setBoundEditor(txtjieyou);
        //contchaohao
        contchaohao.setBoundEditor(txtchaohao);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(356, 12, 336, 249));        kDLabel5.setBounds(new Rectangle(34, 26, 52, 16));
        kDPanel3.add(kDLabel5, new KDLayout.Constraints(34, 26, 52, 16, 0));
        kDLabel6.setBounds(new Rectangle(124, 26, 52, 16));
        kDPanel3.add(kDLabel6, new KDLayout.Constraints(124, 26, 52, 16, 0));
        kDLabel7.setBounds(new Rectangle(206, 26, 42, 16));
        kDPanel3.add(kDLabel7, new KDLayout.Constraints(206, 26, 42, 16, 0));
        kDLabel8.setBounds(new Rectangle(276, 26, 47, 16));
        kDPanel3.add(kDLabel8, new KDLayout.Constraints(276, 26, 47, 16, 0));
        kDSeparator9.setBounds(new Rectangle(5, 42, 320, 10));
        kDPanel3.add(kDSeparator9, new KDLayout.Constraints(5, 42, 320, 10, 0));
        kDSeparator11.setBounds(new Rectangle(9, 24, 316, 10));
        kDPanel3.add(kDSeparator11, new KDLayout.Constraints(9, 24, 316, 10, 0));
        kDLabel4.setBounds(new Rectangle(113, 9, 142, 18));
        kDPanel3.add(kDLabel4, new KDLayout.Constraints(113, 9, 142, 18, 0));
        kDLabel36.setBounds(new Rectangle(32, 44, 51, 20));
        kDPanel3.add(kDLabel36, new KDLayout.Constraints(32, 44, 51, 20, 0));
        kDSeparator30.setBounds(new Rectangle(10, 64, 318, 10));
        kDPanel3.add(kDSeparator30, new KDLayout.Constraints(10, 64, 318, 10, 0));
        kDLabel42.setBounds(new Rectangle(33, 88, 55, 22));
        kDPanel3.add(kDLabel42, new KDLayout.Constraints(33, 88, 55, 22, 0));
        kDSeparator31.setBounds(new Rectangle(8, 109, 320, 10));
        kDPanel3.add(kDSeparator31, new KDLayout.Constraints(8, 109, 320, 10, 0));
        kDLabel43.setBounds(new Rectangle(26, 113, 66, 15));
        kDPanel3.add(kDLabel43, new KDLayout.Constraints(26, 113, 66, 15, 0));
        kDSeparator32.setBounds(new Rectangle(10, 131, 316, 10));
        kDPanel3.add(kDSeparator32, new KDLayout.Constraints(10, 131, 316, 10, 0));
        kDLabel44.setBounds(new Rectangle(34, 135, 56, 16));
        kDPanel3.add(kDLabel44, new KDLayout.Constraints(34, 135, 56, 16, 0));
        kDSeparator33.setBounds(new Rectangle(9, 153, 318, 10));
        kDPanel3.add(kDSeparator33, new KDLayout.Constraints(9, 153, 318, 10, 0));
        kDLabel46.setBounds(new Rectangle(14, 154, 83, 17));
        kDPanel3.add(kDLabel46, new KDLayout.Constraints(14, 154, 83, 17, 0));
        kDLabel48.setBounds(new Rectangle(107, 153, 80, 18));
        kDPanel3.add(kDLabel48, new KDLayout.Constraints(107, 153, 80, 18, 0));
        kDLabel49.setBounds(new Rectangle(234, 156, 65, 12));
        kDPanel3.add(kDLabel49, new KDLayout.Constraints(234, 156, 65, 12, 0));
        kDSeparator34.setBounds(new Rectangle(12, 170, 316, 10));
        kDPanel3.add(kDSeparator34, new KDLayout.Constraints(12, 170, 316, 10, 0));
        kDSeparator35.setBounds(new Rectangle(13, 192, 314, 10));
        kDPanel3.add(kDSeparator35, new KDLayout.Constraints(13, 192, 314, 10, 0));
        kDLabel50.setBounds(new Rectangle(67, 193, 66, 18));
        kDPanel3.add(kDLabel50, new KDLayout.Constraints(67, 193, 66, 18, 0));
        kDLabel51.setBounds(new Rectangle(202, 193, 49, 18));
        kDPanel3.add(kDLabel51, new KDLayout.Constraints(202, 193, 49, 18, 0));
        kDLabel52.setBounds(new Rectangle(275, 194, 45, 16));
        kDPanel3.add(kDLabel52, new KDLayout.Constraints(275, 194, 45, 16, 0));
        kDSeparator36.setBounds(new Rectangle(11, 210, 317, 10));
        kDPanel3.add(kDSeparator36, new KDLayout.Constraints(11, 210, 317, 10, 0));
        kDSeparator37.setBounds(new Rectangle(98, 25, 8, 164));
        kDPanel3.add(kDSeparator37, new KDLayout.Constraints(98, 25, 8, 164, 0));
        kDSeparator38.setBounds(new Rectangle(192, 25, 8, 212));
        kDPanel3.add(kDSeparator38, new KDLayout.Constraints(192, 25, 8, 212, 0));
        kDSeparator39.setBounds(new Rectangle(257, 193, 8, 45));
        kDPanel3.add(kDSeparator39, new KDLayout.Constraints(257, 193, 8, 45, 0));
        kDSeparator40.setBounds(new Rectangle(260, 24, 8, 128));
        kDPanel3.add(kDSeparator40, new KDLayout.Constraints(260, 24, 8, 128, 0));
        contrunhuayoujiecun.setBounds(new Rectangle(102, 44, 87, 19));
        kDPanel3.add(contrunhuayoujiecun, new KDLayout.Constraints(102, 44, 87, 19, 0));
        contrunhuayougangzuo.setBounds(new Rectangle(102, 89, 87, 19));
        kDPanel3.add(contrunhuayougangzuo, new KDLayout.Constraints(102, 89, 87, 19, 0));
        contrunhuayouxiao.setBounds(new Rectangle(102, 111, 87, 19));
        kDPanel3.add(contrunhuayouxiao, new KDLayout.Constraints(102, 111, 87, 19, 0));
        contrunhuayouben.setBounds(new Rectangle(102, 133, 87, 19));
        kDPanel3.add(contrunhuayouben, new KDLayout.Constraints(102, 133, 87, 19, 0));
        contchilunyoushang.setBounds(new Rectangle(197, 44, 60, 19));
        kDPanel3.add(contchilunyoushang, new KDLayout.Constraints(197, 44, 60, 19, 0));
        contchilunyougang.setBounds(new Rectangle(197, 89, 60, 19));
        kDPanel3.add(contchilunyougang, new KDLayout.Constraints(197, 89, 60, 19, 0));
        contchilunyouxiao.setBounds(new Rectangle(197, 111, 61, 19));
        kDPanel3.add(contchilunyouxiao, new KDLayout.Constraints(197, 111, 61, 19, 0));
        contchilunyouben.setBounds(new Rectangle(197, 133, 60, 19));
        kDPanel3.add(contchilunyouben, new KDLayout.Constraints(197, 133, 60, 19, 0));
        contyeyayoushang.setBounds(new Rectangle(263, 44, 57, 19));
        kDPanel3.add(contyeyayoushang, new KDLayout.Constraints(263, 44, 57, 19, 0));
        contyeyayougang.setBounds(new Rectangle(263, 89, 57, 19));
        kDPanel3.add(contyeyayougang, new KDLayout.Constraints(263, 89, 57, 19, 0));
        contyeyayouxiao.setBounds(new Rectangle(263, 111, 57, 19));
        kDPanel3.add(contyeyayouxiao, new KDLayout.Constraints(263, 111, 57, 19, 0));
        contyeyayouben.setBounds(new Rectangle(263, 133, 56, 19));
        kDPanel3.add(contyeyayouben, new KDLayout.Constraints(263, 133, 56, 19, 0));
        contedingrunranbi.setBounds(new Rectangle(16, 172, 73, 19));
        kDPanel3.add(contedingrunranbi, new KDLayout.Constraints(16, 172, 73, 19, 0));
        contshijirunranbi.setBounds(new Rectangle(102, 172, 87, 19));
        kDPanel3.add(contshijirunranbi, new KDLayout.Constraints(102, 172, 87, 19, 0));
        contdingeliangxx.setBounds(new Rectangle(197, 172, 125, 19));
        kDPanel3.add(contdingeliangxx, new KDLayout.Constraints(197, 172, 125, 19, 0));
        contshiyongliangone.setBounds(new Rectangle(16, 213, 173, 19));
        kDPanel3.add(contshiyongliangone, new KDLayout.Constraints(16, 213, 173, 19, 0));
        contjieone.setBounds(new Rectangle(197, 213, 58, 19));
        kDPanel3.add(contjieone, new KDLayout.Constraints(197, 213, 58, 19, 0));
        contchaoone.setBounds(new Rectangle(263, 213, 58, 19));
        kDPanel3.add(contchaoone, new KDLayout.Constraints(263, 213, 58, 19, 0));
        kDSeparator60.setBounds(new Rectangle(9, 87, 319, 10));
        kDPanel3.add(kDSeparator60, new KDLayout.Constraints(9, 87, 319, 10, 0));
        kDLabel66.setBounds(new Rectangle(33, 68, 56, 15));
        kDPanel3.add(kDLabel66, new KDLayout.Constraints(33, 68, 56, 15, 0));
        contlingrurhy.setBounds(new Rectangle(103, 67, 86, 19));
        kDPanel3.add(contlingrurhy, new KDLayout.Constraints(103, 67, 86, 19, 0));
        contlingrucly.setBounds(new Rectangle(197, 67, 60, 19));
        kDPanel3.add(contlingrucly, new KDLayout.Constraints(197, 67, 60, 19, 0));
        contlingruyyy.setBounds(new Rectangle(263, 67, 57, 19));
        kDPanel3.add(contlingruyyy, new KDLayout.Constraints(263, 67, 57, 19, 0));
        //contrunhuayoujiecun
        contrunhuayoujiecun.setBoundEditor(txtrunhuayoujiecun);
        //contrunhuayougangzuo
        contrunhuayougangzuo.setBoundEditor(txtrunhuayougangzuo);
        //contrunhuayouxiao
        contrunhuayouxiao.setBoundEditor(txtrunhuayouxiao);
        //contrunhuayouben
        contrunhuayouben.setBoundEditor(txtrunhuayouben);
        //contchilunyoushang
        contchilunyoushang.setBoundEditor(txtchilunyoushang);
        //contchilunyougang
        contchilunyougang.setBoundEditor(txtchilunyougang);
        //contchilunyouxiao
        contchilunyouxiao.setBoundEditor(txtchilunyouxiao);
        //contchilunyouben
        contchilunyouben.setBoundEditor(txtchilunyouben);
        //contyeyayoushang
        contyeyayoushang.setBoundEditor(txtyeyayoushang);
        //contyeyayougang
        contyeyayougang.setBoundEditor(txtyeyayougang);
        //contyeyayouxiao
        contyeyayouxiao.setBoundEditor(txtyeyayouxiao);
        //contyeyayouben
        contyeyayouben.setBoundEditor(txtyeyayouben);
        //contedingrunranbi
        contedingrunranbi.setBoundEditor(txtedingrunranbi);
        //contshijirunranbi
        contshijirunranbi.setBoundEditor(txtshijirunranbi);
        //contdingeliangxx
        contdingeliangxx.setBoundEditor(txtdingeliangxx);
        //contshiyongliangone
        contshiyongliangone.setBoundEditor(txtshiyongliangone);
        //contjieone
        contjieone.setBoundEditor(txtjieone);
        //contchaoone
        contchaoone.setBoundEditor(txtchaoone);
        //contlingrurhy
        contlingrurhy.setBoundEditor(txtlingrurhy);
        //contlingrucly
        contlingrucly.setBoundEditor(txtlingrucly);
        //contlingruyyy
        contlingruyyy.setBoundEditor(txtlingruyyy);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(694, 13, 242, 126));        kDLabel9.setBounds(new Rectangle(104, 10, 63, 22));
        kDPanel4.add(kDLabel9, new KDLayout.Constraints(104, 10, 63, 22, 0));
        kDLabel10.setBounds(new Rectangle(12, 61, 214, 25));
        kDPanel4.add(kDLabel10, new KDLayout.Constraints(12, 61, 214, 25, 0));
        kDLabel11.setBounds(new Rectangle(18, 88, 210, 22));
        kDPanel4.add(kDLabel11, new KDLayout.Constraints(18, 88, 210, 22, 0));
        kDSeparator13.setBounds(new Rectangle(13, 31, 248, 10));
        kDPanel4.add(kDSeparator13, new KDLayout.Constraints(13, 31, 248, 10, 0));
        contselfLeve.setBounds(new Rectangle(11, 35, 215, 19));
        kDPanel4.add(contselfLeve, new KDLayout.Constraints(11, 35, 215, 19, 0));
        //contselfLeve
        contselfLeve.setBoundEditor(txtselfLeve);
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(355, 369, 338, 105));        kDSeparator52.setBounds(new Rectangle(7, 26, 322, 10));
        kDPanel5.add(kDSeparator52, new KDLayout.Constraints(7, 26, 322, 10, 0));
        kDSeparator53.setBounds(new Rectangle(10, 48, 318, 10));
        kDPanel5.add(kDSeparator53, new KDLayout.Constraints(10, 48, 318, 10, 0));
        kDSeparator54.setBounds(new Rectangle(10, 70, 319, 10));
        kDPanel5.add(kDSeparator54, new KDLayout.Constraints(10, 70, 319, 10, 0));
        kDLabel59.setBounds(new Rectangle(12, 31, 30, 14));
        kDPanel5.add(kDLabel59, new KDLayout.Constraints(12, 31, 30, 14, 0));
        kDLabel60.setBounds(new Rectangle(11, 53, 31, 14));
        kDPanel5.add(kDLabel60, new KDLayout.Constraints(11, 53, 31, 14, 0));
        kDLabel61.setBounds(new Rectangle(12, 74, 31, 16));
        kDPanel5.add(kDLabel61, new KDLayout.Constraints(12, 74, 31, 16, 0));
        kDSeparator55.setBounds(new Rectangle(38, 8, 11, 36));
        kDPanel5.add(kDSeparator55, new KDLayout.Constraints(38, 8, 11, 36, 0));
        kDSeparator56.setBounds(new Rectangle(36, 5, 9, 104));
        kDPanel5.add(kDSeparator56, new KDLayout.Constraints(36, 5, 9, 104, 0));
        kDSeparator57.setBounds(new Rectangle(111, 1, 12, 101));
        kDPanel5.add(kDSeparator57, new KDLayout.Constraints(111, 1, 12, 101, 0));
        kDSeparator58.setBounds(new Rectangle(263, 5, 9, 98));
        kDPanel5.add(kDSeparator58, new KDLayout.Constraints(263, 5, 9, 98, 0));
        kDSeparator59.setBounds(new Rectangle(194, 6, 9, 96));
        kDPanel5.add(kDSeparator59, new KDLayout.Constraints(194, 6, 9, 96, 0));
        kDLabel62.setBounds(new Rectangle(36, 10, 80, 15));
        kDPanel5.add(kDLabel62, new KDLayout.Constraints(36, 10, 80, 15, 0));
        kDLabel63.setBounds(new Rectangle(112, 9, 83, 17));
        kDPanel5.add(kDLabel63, new KDLayout.Constraints(112, 9, 83, 17, 0));
        kDLabel64.setBounds(new Rectangle(195, 9, 67, 16));
        kDPanel5.add(kDLabel64, new KDLayout.Constraints(195, 9, 67, 16, 0));
        kDLabel65.setBounds(new Rectangle(265, 9, 67, 17));
        kDPanel5.add(kDLabel65, new KDLayout.Constraints(265, 9, 67, 17, 0));
        contzuobenyue.setBounds(new Rectangle(42, 28, 67, 19));
        kDPanel5.add(contzuobenyue, new KDLayout.Constraints(42, 28, 67, 19, 0));
        contyoujibenyue.setBounds(new Rectangle(115, 28, 77, 19));
        kDPanel5.add(contyoujibenyue, new KDLayout.Constraints(115, 28, 77, 19, 0));
        contfujibenyue.setBounds(new Rectangle(197, 28, 62, 19));
        kDPanel5.add(contfujibenyue, new KDLayout.Constraints(197, 28, 62, 19, 0));
        contdianbiaobenyue.setBounds(new Rectangle(268, 28, 51, 19));
        kDPanel5.add(contdianbiaobenyue, new KDLayout.Constraints(268, 28, 51, 19, 0));
        contdianbiaoheji.setBounds(new Rectangle(268, 72, 51, 19));
        kDPanel5.add(contdianbiaoheji, new KDLayout.Constraints(268, 72, 51, 19, 0));
        contdianbiaoshangyue.setBounds(new Rectangle(268, 50, 50, 19));
        kDPanel5.add(contdianbiaoshangyue, new KDLayout.Constraints(268, 50, 50, 19, 0));
        contfujiheji.setBounds(new Rectangle(197, 72, 63, 19));
        kDPanel5.add(contfujiheji, new KDLayout.Constraints(197, 72, 63, 19, 0));
        contfujishangyue.setBounds(new Rectangle(197, 50, 63, 19));
        kDPanel5.add(contfujishangyue, new KDLayout.Constraints(197, 50, 63, 19, 0));
        contyoujiheji.setBounds(new Rectangle(115, 72, 76, 19));
        kDPanel5.add(contyoujiheji, new KDLayout.Constraints(115, 72, 76, 19, 0));
        contyoujishangyue.setBounds(new Rectangle(115, 50, 76, 19));
        kDPanel5.add(contyoujishangyue, new KDLayout.Constraints(115, 50, 76, 19, 0));
        contzuoheji.setBounds(new Rectangle(42, 72, 66, 19));
        kDPanel5.add(contzuoheji, new KDLayout.Constraints(42, 72, 66, 19, 0));
        contzuoshangyue.setBounds(new Rectangle(42, 50, 66, 19));
        kDPanel5.add(contzuoshangyue, new KDLayout.Constraints(42, 50, 66, 19, 0));
        //contzuobenyue
        contzuobenyue.setBoundEditor(txtzuobenyue);
        //contyoujibenyue
        contyoujibenyue.setBoundEditor(txtyoujibenyue);
        //contfujibenyue
        contfujibenyue.setBoundEditor(txtfujibenyue);
        //contdianbiaobenyue
        contdianbiaobenyue.setBoundEditor(txtdianbiaobenyue);
        //contdianbiaoheji
        contdianbiaoheji.setBoundEditor(txtdianbiaoheji);
        //contdianbiaoshangyue
        contdianbiaoshangyue.setBoundEditor(txtdianbiaoshangyue);
        //contfujiheji
        contfujiheji.setBoundEditor(txtfujiheji);
        //contfujishangyue
        contfujishangyue.setBoundEditor(txtfujishangyue);
        //contyoujiheji
        contyoujiheji.setBoundEditor(txtyoujiheji);
        //contyoujishangyue
        contyoujishangyue.setBoundEditor(txtyoujishangyue);
        //contzuoheji
        contzuoheji.setBoundEditor(txtzuoheji);
        //contzuoshangyue
        contzuoshangyue.setBoundEditor(txtzuoshangyue);
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(695, 140, 241, 334));        contnote.setBounds(new Rectangle(12, 10, 211, 203));
        kDPanel6.add(contnote, new KDLayout.Constraints(12, 10, 211, 203, 0));
        contleijiyongdian.setBounds(new Rectangle(12, 218, 210, 19));
        kDPanel6.add(contleijiyongdian, new KDLayout.Constraints(12, 218, 210, 19, 0));
        contleijiranyou.setBounds(new Rectangle(12, 242, 210, 19));
        kDPanel6.add(contleijiranyou, new KDLayout.Constraints(12, 242, 210, 19, 0));
        contleijiyunshi.setBounds(new Rectangle(12, 267, 210, 19));
        kDPanel6.add(contleijiyunshi, new KDLayout.Constraints(12, 267, 210, 19, 0));
        contleijichanzhi.setBounds(new Rectangle(12, 291, 210, 19));
        kDPanel6.add(contleijichanzhi, new KDLayout.Constraints(12, 291, 210, 19, 0));
        //contnote
        contnote.setBoundEditor(scrollPanenote);
        //scrollPanenote
        scrollPanenote.getViewport().add(txtnote, null);
        //contleijiyongdian
        contleijiyongdian.setBoundEditor(txtleijiyongdian);
        //contleijiranyou
        contleijiranyou.setBoundEditor(txtleijiranyou);
        //contleijiyunshi
        contleijiyunshi.setBoundEditor(txtleijiyunshi);
        //contleijichanzhi
        contleijichanzhi.setBoundEditor(txtleijichanzhi);
        //kDPanel7
        kDPanel7.setLayout(new KDLayout());
        kDPanel7.putClientProperty("OriginalBounds", new Rectangle(356, 262, 336, 105));        kDLabel53.setBounds(new Rectangle(29, 9, 56, 18));
        kDPanel7.add(kDLabel53, new KDLayout.Constraints(29, 9, 56, 18, 0));
        kDSeparator41.setBounds(new Rectangle(7, 26, 319, 10));
        kDPanel7.add(kDSeparator41, new KDLayout.Constraints(7, 26, 319, 10, 0));
        kDLabel54.setBounds(new Rectangle(30, 31, 54, 13));
        kDPanel7.add(kDLabel54, new KDLayout.Constraints(30, 31, 54, 13, 0));
        kDSeparator42.setBounds(new Rectangle(7, 48, 319, 10));
        kDPanel7.add(kDSeparator42, new KDLayout.Constraints(7, 48, 319, 10, 0));
        kDLabel55.setBounds(new Rectangle(30, 51, 48, 18));
        kDPanel7.add(kDLabel55, new KDLayout.Constraints(30, 51, 48, 18, 0));
        kDSeparator43.setBounds(new Rectangle(10, 70, 316, 10));
        kDPanel7.add(kDSeparator43, new KDLayout.Constraints(10, 70, 316, 10, 0));
        kDLabel56.setBounds(new Rectangle(30, 72, 49, 20));
        kDPanel7.add(kDLabel56, new KDLayout.Constraints(30, 72, 49, 20, 0));
        kDLabel57.setBounds(new Rectangle(104, 10, 85, 16));
        kDPanel7.add(kDLabel57, new KDLayout.Constraints(104, 10, 85, 16, 0));
        kDLabel58.setBounds(new Rectangle(215, 10, 107, 15));
        kDPanel7.add(kDLabel58, new KDLayout.Constraints(215, 10, 107, 15, 0));
        kDSeparator44.setBounds(new Rectangle(93, 9, 4, 91));
        kDPanel7.add(kDSeparator44, new KDLayout.Constraints(93, 9, 4, 91, 0));
        kDSeparator45.setBounds(new Rectangle(192, 8, 3, 91));
        kDPanel7.add(kDSeparator45, new KDLayout.Constraints(192, 8, 3, 91, 0));
        kDSeparator46.setBounds(new Rectangle(92, 7, 6, 90));
        kDPanel7.add(kDSeparator46, new KDLayout.Constraints(92, 7, 6, 90, 0));
        kDSeparator47.setBounds(new Rectangle(193, 8, 6, 92));
        kDPanel7.add(kDSeparator47, new KDLayout.Constraints(193, 8, 6, 92, 0));
        kDSeparator48.setBounds(new Rectangle(85, 8, 5, 92));
        kDPanel7.add(kDSeparator48, new KDLayout.Constraints(85, 8, 5, 92, 0));
        kDSeparator49.setBounds(new Rectangle(98, 4, 8, 102));
        kDPanel7.add(kDSeparator49, new KDLayout.Constraints(98, 4, 8, 102, 0));
        kDSeparator50.setBounds(new Rectangle(199, 11, 2, 38));
        kDPanel7.add(kDSeparator50, new KDLayout.Constraints(199, 11, 2, 38, 0));
        kDSeparator51.setBounds(new Rectangle(193, 5, 9, 98));
        kDPanel7.add(kDSeparator51, new KDLayout.Constraints(193, 5, 9, 98, 0));
        contjishudinge.setBounds(new Rectangle(103, 28, 84, 19));
        kDPanel7.add(contjishudinge, new KDLayout.Constraints(103, 28, 84, 19, 0));
        contchanzhidinge.setBounds(new Rectangle(199, 28, 118, 19));
        kDPanel7.add(contchanzhidinge, new KDLayout.Constraints(199, 28, 118, 19, 0));
        contjishubenyue.setBounds(new Rectangle(103, 50, 84, 19));
        kDPanel7.add(contjishubenyue, new KDLayout.Constraints(103, 50, 84, 19, 0));
        contchanzhibenyue.setBounds(new Rectangle(199, 50, 118, 19));
        kDPanel7.add(contchanzhibenyue, new KDLayout.Constraints(199, 50, 118, 19, 0));
        contjishuleiji.setBounds(new Rectangle(103, 72, 83, 19));
        kDPanel7.add(contjishuleiji, new KDLayout.Constraints(103, 72, 83, 19, 0));
        contchanzhileiji.setBounds(new Rectangle(199, 72, 118, 19));
        kDPanel7.add(contchanzhileiji, new KDLayout.Constraints(199, 72, 118, 19, 0));
        //contjishudinge
        contjishudinge.setBoundEditor(txtjishudinge);
        //contchanzhidinge
        contchanzhidinge.setBoundEditor(txtchanzhidinge);
        //contjishubenyue
        contjishubenyue.setBoundEditor(txtjishubenyue);
        //contchanzhibenyue
        contchanzhibenyue.setBoundEditor(txtchanzhibenyue);
        //contjishuleiji
        contjishuleiji.setBoundEditor(txtjishuleiji);
        //contchanzhileiji
        contchanzhileiji.setBoundEditor(txtchanzhileiji);
        //contchuanzhang
        contchuanzhang.setBoundEditor(prmtchuanzhang);
        //contlunjizhang
        contlunjizhang.setBoundEditor(prmtlunjizhang);
        //contreportMonth
        contreportMonth.setBoundEditor(prmtreportMonth);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("shipName", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtshipName, "data");
		dataBinder.registerBinding("Power", String.class, this.txtPower, "text");
		dataBinder.registerBinding("lastMonth", String.class, this.txtlastMonth, "text");
		dataBinder.registerBinding("qione", String.class, this.txtqione, "text");
		dataBinder.registerBinding("qitwo", String.class, this.txtqitwo, "text");
		dataBinder.registerBinding("zhione", String.class, this.txtzhione, "text");
		dataBinder.registerBinding("zhitwo", String.class, this.txtzhitwo, "text");
		dataBinder.registerBinding("miduone", String.class, this.txtmiduone, "text");
		dataBinder.registerBinding("midutwo", String.class, this.txtmidutwo, "text");
		dataBinder.registerBinding("intoTotal", String.class, this.txtintoTotal, "text");
		dataBinder.registerBinding("qithree", String.class, this.txtqithree, "text");
		dataBinder.registerBinding("qifour", String.class, this.txtqifour, "text");
		dataBinder.registerBinding("zhithree", String.class, this.txtzhithree, "text");
		dataBinder.registerBinding("zhifour", String.class, this.txtzhifour, "text");
		dataBinder.registerBinding("miduthree", String.class, this.txtmiduthree, "text");
		dataBinder.registerBinding("midufour", String.class, this.txtmidufour, "text");
		dataBinder.registerBinding("totalConsum", String.class, this.txttotalConsum, "text");
		dataBinder.registerBinding("monthBalance", String.class, this.txtmonthBalance, "text");
		dataBinder.registerBinding("portShipment", String.class, this.txtportShipment, "text");
		dataBinder.registerBinding("smallTransport", String.class, this.txtsmallTransport, "text");
		dataBinder.registerBinding("Total", String.class, this.txtTotal, "text");
		dataBinder.registerBinding("outputValue", String.class, this.txtoutputValue, "text");
		dataBinder.registerBinding("gzde", String.class, this.txtgzde, "text");
		dataBinder.registerBinding("gzdel", String.class, this.txtgzdel, "text");
		dataBinder.registerBinding("xyzde", String.class, this.txtxyzde, "text");
		dataBinder.registerBinding("xyzdel", String.class, this.txtxyzdel, "text");
		dataBinder.registerBinding("hjde", String.class, this.txthjde, "text");
		dataBinder.registerBinding("hjdel", String.class, this.txthjdel, "text");
		dataBinder.registerBinding("chanzhione", String.class, this.txtchanzhione, "text");
		dataBinder.registerBinding("chanzhitwo", String.class, this.txtchanzhitwo, "text");
		dataBinder.registerBinding("zhdel", String.class, this.txtzhdel, "text");
		dataBinder.registerBinding("shiyongliang", String.class, this.txtshiyongliang, "text");
		dataBinder.registerBinding("jieyou", String.class, this.txtjieyou, "text");
		dataBinder.registerBinding("chaohao", String.class, this.txtchaohao, "text");
		dataBinder.registerBinding("runhuayoujiecun", String.class, this.txtrunhuayoujiecun, "text");
		dataBinder.registerBinding("runhuayougangzuo", String.class, this.txtrunhuayougangzuo, "text");
		dataBinder.registerBinding("runhuayouxiao", String.class, this.txtrunhuayouxiao, "text");
		dataBinder.registerBinding("runhuayouben", String.class, this.txtrunhuayouben, "text");
		dataBinder.registerBinding("chilunyoushang", String.class, this.txtchilunyoushang, "text");
		dataBinder.registerBinding("chilunyougang", String.class, this.txtchilunyougang, "text");
		dataBinder.registerBinding("chilunyouxiao", String.class, this.txtchilunyouxiao, "text");
		dataBinder.registerBinding("chilunyouben", String.class, this.txtchilunyouben, "text");
		dataBinder.registerBinding("yeyayoushang", String.class, this.txtyeyayoushang, "text");
		dataBinder.registerBinding("yeyayougang", String.class, this.txtyeyayougang, "text");
		dataBinder.registerBinding("yeyayouxiao", String.class, this.txtyeyayouxiao, "text");
		dataBinder.registerBinding("yeyayouben", String.class, this.txtyeyayouben, "text");
		dataBinder.registerBinding("edingrunranbi", String.class, this.txtedingrunranbi, "text");
		dataBinder.registerBinding("shijirunranbi", String.class, this.txtshijirunranbi, "text");
		dataBinder.registerBinding("dingeliangxx", String.class, this.txtdingeliangxx, "text");
		dataBinder.registerBinding("shiyongliangone", String.class, this.txtshiyongliangone, "text");
		dataBinder.registerBinding("jieone", String.class, this.txtjieone, "text");
		dataBinder.registerBinding("chaoone", String.class, this.txtchaoone, "text");
		dataBinder.registerBinding("lingrurhy", String.class, this.txtlingrurhy, "text");
		dataBinder.registerBinding("lingrucly", String.class, this.txtlingrucly, "text");
		dataBinder.registerBinding("lingruyyy", String.class, this.txtlingruyyy, "text");
		dataBinder.registerBinding("selfLeve", String.class, this.txtselfLeve, "text");
		dataBinder.registerBinding("zuobenyue", String.class, this.txtzuobenyue, "text");
		dataBinder.registerBinding("youjibenyue", String.class, this.txtyoujibenyue, "text");
		dataBinder.registerBinding("fujibenyue", String.class, this.txtfujibenyue, "text");
		dataBinder.registerBinding("dianbiaobenyue", String.class, this.txtdianbiaobenyue, "text");
		dataBinder.registerBinding("dianbiaoheji", String.class, this.txtdianbiaoheji, "text");
		dataBinder.registerBinding("dianbiaoshangyue", String.class, this.txtdianbiaoshangyue, "text");
		dataBinder.registerBinding("fujiheji", String.class, this.txtfujiheji, "text");
		dataBinder.registerBinding("fujishangyue", String.class, this.txtfujishangyue, "text");
		dataBinder.registerBinding("youjiheji", String.class, this.txtyoujiheji, "text");
		dataBinder.registerBinding("youjishangyue", String.class, this.txtyoujishangyue, "text");
		dataBinder.registerBinding("zuoheji", String.class, this.txtzuoheji, "text");
		dataBinder.registerBinding("zuoshangyue", String.class, this.txtzuoshangyue, "text");
		dataBinder.registerBinding("note", String.class, this.txtnote, "text");
		dataBinder.registerBinding("leijiyongdian", String.class, this.txtleijiyongdian, "text");
		dataBinder.registerBinding("leijiranyou", String.class, this.txtleijiranyou, "text");
		dataBinder.registerBinding("leijiyunshi", String.class, this.txtleijiyunshi, "text");
		dataBinder.registerBinding("leijichanzhi", String.class, this.txtleijichanzhi, "text");
		dataBinder.registerBinding("jishudinge", String.class, this.txtjishudinge, "text");
		dataBinder.registerBinding("chanzhidinge", String.class, this.txtchanzhidinge, "text");
		dataBinder.registerBinding("jishubenyue", String.class, this.txtjishubenyue, "text");
		dataBinder.registerBinding("chanzhibenyue", String.class, this.txtchanzhibenyue, "text");
		dataBinder.registerBinding("jishuleiji", String.class, this.txtjishuleiji, "text");
		dataBinder.registerBinding("chanzhileiji", String.class, this.txtchanzhileiji, "text");
		dataBinder.registerBinding("chuanzhang", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtchuanzhang, "data");
		dataBinder.registerBinding("lunjizhang", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtlunjizhang, "data");
		dataBinder.registerBinding("reportMonth", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtreportMonth, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.operate.app.ShipFuelEditUIHandler";
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
        this.prmtshipName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.operate.ShipFuelInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shipName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Power", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qione", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qitwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhione", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhitwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("miduone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("midutwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intoTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qithree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qifour", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhithree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhifour", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("miduthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("midufour", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalConsum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("monthBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portShipment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("smallTransport", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Total", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("outputValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gzde", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gzdel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xyzde", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xyzdel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hjde", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hjdel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chanzhione", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chanzhitwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhdel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shiyongliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jieyou", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chaohao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("runhuayoujiecun", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("runhuayougangzuo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("runhuayouxiao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("runhuayouben", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chilunyoushang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chilunyougang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chilunyouxiao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chilunyouben", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeyayoushang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeyayougang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeyayouxiao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yeyayouben", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("edingrunranbi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shijirunranbi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dingeliangxx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shiyongliangone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jieone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chaoone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lingrurhy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lingrucly", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lingruyyy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfLeve", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuobenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youjibenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fujibenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dianbiaobenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dianbiaoheji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dianbiaoshangyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fujiheji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fujishangyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youjiheji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youjishangyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuoheji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuoshangyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("note", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leijiyongdian", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leijiranyou", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leijiyunshi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leijichanzhi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jishudinge", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chanzhidinge", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jishubenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chanzhibenyue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jishuleiji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chanzhileiji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chuanzhang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lunjizhang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportMonth", ValidateHelper.ON_SAVE);    		
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
     * output prmtshipName_dataChanged method
     */
    protected void prmtshipName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtreportMonth_dataChanged method
     */
    protected void prmtreportMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("number"));
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
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("shipName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("shipName.id"));
        	sic.add(new SelectorItemInfo("shipName.number"));
        	sic.add(new SelectorItemInfo("shipName.name"));
		}
        sic.add(new SelectorItemInfo("Power"));
        sic.add(new SelectorItemInfo("lastMonth"));
        sic.add(new SelectorItemInfo("qione"));
        sic.add(new SelectorItemInfo("qitwo"));
        sic.add(new SelectorItemInfo("zhione"));
        sic.add(new SelectorItemInfo("zhitwo"));
        sic.add(new SelectorItemInfo("miduone"));
        sic.add(new SelectorItemInfo("midutwo"));
        sic.add(new SelectorItemInfo("intoTotal"));
        sic.add(new SelectorItemInfo("qithree"));
        sic.add(new SelectorItemInfo("qifour"));
        sic.add(new SelectorItemInfo("zhithree"));
        sic.add(new SelectorItemInfo("zhifour"));
        sic.add(new SelectorItemInfo("miduthree"));
        sic.add(new SelectorItemInfo("midufour"));
        sic.add(new SelectorItemInfo("totalConsum"));
        sic.add(new SelectorItemInfo("monthBalance"));
        sic.add(new SelectorItemInfo("portShipment"));
        sic.add(new SelectorItemInfo("smallTransport"));
        sic.add(new SelectorItemInfo("Total"));
        sic.add(new SelectorItemInfo("outputValue"));
        sic.add(new SelectorItemInfo("gzde"));
        sic.add(new SelectorItemInfo("gzdel"));
        sic.add(new SelectorItemInfo("xyzde"));
        sic.add(new SelectorItemInfo("xyzdel"));
        sic.add(new SelectorItemInfo("hjde"));
        sic.add(new SelectorItemInfo("hjdel"));
        sic.add(new SelectorItemInfo("chanzhione"));
        sic.add(new SelectorItemInfo("chanzhitwo"));
        sic.add(new SelectorItemInfo("zhdel"));
        sic.add(new SelectorItemInfo("shiyongliang"));
        sic.add(new SelectorItemInfo("jieyou"));
        sic.add(new SelectorItemInfo("chaohao"));
        sic.add(new SelectorItemInfo("runhuayoujiecun"));
        sic.add(new SelectorItemInfo("runhuayougangzuo"));
        sic.add(new SelectorItemInfo("runhuayouxiao"));
        sic.add(new SelectorItemInfo("runhuayouben"));
        sic.add(new SelectorItemInfo("chilunyoushang"));
        sic.add(new SelectorItemInfo("chilunyougang"));
        sic.add(new SelectorItemInfo("chilunyouxiao"));
        sic.add(new SelectorItemInfo("chilunyouben"));
        sic.add(new SelectorItemInfo("yeyayoushang"));
        sic.add(new SelectorItemInfo("yeyayougang"));
        sic.add(new SelectorItemInfo("yeyayouxiao"));
        sic.add(new SelectorItemInfo("yeyayouben"));
        sic.add(new SelectorItemInfo("edingrunranbi"));
        sic.add(new SelectorItemInfo("shijirunranbi"));
        sic.add(new SelectorItemInfo("dingeliangxx"));
        sic.add(new SelectorItemInfo("shiyongliangone"));
        sic.add(new SelectorItemInfo("jieone"));
        sic.add(new SelectorItemInfo("chaoone"));
        sic.add(new SelectorItemInfo("lingrurhy"));
        sic.add(new SelectorItemInfo("lingrucly"));
        sic.add(new SelectorItemInfo("lingruyyy"));
        sic.add(new SelectorItemInfo("selfLeve"));
        sic.add(new SelectorItemInfo("zuobenyue"));
        sic.add(new SelectorItemInfo("youjibenyue"));
        sic.add(new SelectorItemInfo("fujibenyue"));
        sic.add(new SelectorItemInfo("dianbiaobenyue"));
        sic.add(new SelectorItemInfo("dianbiaoheji"));
        sic.add(new SelectorItemInfo("dianbiaoshangyue"));
        sic.add(new SelectorItemInfo("fujiheji"));
        sic.add(new SelectorItemInfo("fujishangyue"));
        sic.add(new SelectorItemInfo("youjiheji"));
        sic.add(new SelectorItemInfo("youjishangyue"));
        sic.add(new SelectorItemInfo("zuoheji"));
        sic.add(new SelectorItemInfo("zuoshangyue"));
        sic.add(new SelectorItemInfo("note"));
        sic.add(new SelectorItemInfo("leijiyongdian"));
        sic.add(new SelectorItemInfo("leijiranyou"));
        sic.add(new SelectorItemInfo("leijiyunshi"));
        sic.add(new SelectorItemInfo("leijichanzhi"));
        sic.add(new SelectorItemInfo("jishudinge"));
        sic.add(new SelectorItemInfo("chanzhidinge"));
        sic.add(new SelectorItemInfo("jishubenyue"));
        sic.add(new SelectorItemInfo("chanzhibenyue"));
        sic.add(new SelectorItemInfo("jishuleiji"));
        sic.add(new SelectorItemInfo("chanzhileiji"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("chuanzhang.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("chuanzhang.id"));
        	sic.add(new SelectorItemInfo("chuanzhang.number"));
        	sic.add(new SelectorItemInfo("chuanzhang.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lunjizhang.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lunjizhang.id"));
        	sic.add(new SelectorItemInfo("lunjizhang.number"));
        	sic.add(new SelectorItemInfo("lunjizhang.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("reportMonth.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportMonth.id"));
        	sic.add(new SelectorItemInfo("reportMonth.number"));
		}
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.operate.client", "ShipFuelEditUI");
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
        return com.kingdee.eas.port.equipment.operate.client.ShipFuelEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.operate.ShipFuelFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.operate.ShipFuelInfo objectValue = new com.kingdee.eas.port.equipment.operate.ShipFuelInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/operate/ShipFuel";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.operate.app.ShipFuelQuery");
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