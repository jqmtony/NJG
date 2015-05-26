/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

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
public abstract class AbstractRichExamTempTabEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRichExamTempTabEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contywdjbh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbizdate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contldh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqydw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjdw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpdw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contskdw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjpzh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkppzh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contskpzh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbiznumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfph;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzje;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxsy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttjlb;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbizState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjtcmc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjtcbm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjxmbm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjxmmc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxslb;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsklb;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjxbs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contklj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzkl;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjsje;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjshj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjjg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpjg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjrq;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkflag;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzjjg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtywdjbh;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbizdate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtldh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqydw;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjdw;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtkpdw;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtskdw;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjpzh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtkppzh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtskpzh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbiznumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfph;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzje;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxsy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttjlb;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbizState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjtcmc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjr;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjtcbm;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjxmbm;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjxmmc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxslb;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsklb;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjxbs;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtklj;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzkl;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjsje;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtse;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjshj;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdjjg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtkpjg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdjrq;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtkh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzjjg;
    protected com.kingdee.eas.custom.richinf.RichExamTempTabInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRichExamTempTabEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRichExamTempTabEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contywdjbh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbizdate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contldh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqydw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjdw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpdw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contskdw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjpzh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkppzh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contskpzh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbiznumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfph = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzje = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxsy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttjlb = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbizState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbeizhu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjtcmc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjtcbm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjxmbm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjxmmc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxslb = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsklb = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjxbs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contklj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzkl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjsje = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjshj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjjg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpjg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjrq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkflag = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contkh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzjjg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtywdjbh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbizdate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtldh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqydw = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjdw = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtkpdw = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtskdw = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjpzh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtkppzh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtskpzh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbiznumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfph = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzje = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtxsy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttjlb = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbizState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbeizhu = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjtcmc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjtcbm = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjxmbm = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjxmmc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtxslb = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsklb = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjxbs = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtklj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzkl = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjsje = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtse = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtjshj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdjjg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtkpjg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkdjrq = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtkh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzjjg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contywdjbh.setName("contywdjbh");
        this.contbizdate.setName("contbizdate");
        this.contldh.setName("contldh");
        this.contqydw.setName("contqydw");
        this.contdjdw.setName("contdjdw");
        this.contkpdw.setName("contkpdw");
        this.contskdw.setName("contskdw");
        this.contdjpzh.setName("contdjpzh");
        this.contkppzh.setName("contkppzh");
        this.contskpzh.setName("contskpzh");
        this.contbiznumber.setName("contbiznumber");
        this.contfph.setName("contfph");
        this.contzje.setName("contzje");
        this.contxsy.setName("contxsy");
        this.conttjlb.setName("conttjlb");
        this.contbizState.setName("contbizState");
        this.contbeizhu.setName("contbeizhu");
        this.contdjtcmc.setName("contdjtcmc");
        this.contdjr.setName("contdjr");
        this.contdjtcbm.setName("contdjtcbm");
        this.contdjxmbm.setName("contdjxmbm");
        this.contdjxmmc.setName("contdjxmmc");
        this.contxslb.setName("contxslb");
        this.contsklb.setName("contsklb");
        this.contjxbs.setName("contjxbs");
        this.contklj.setName("contklj");
        this.contzkl.setName("contzkl");
        this.contjsje.setName("contjsje");
        this.contse.setName("contse");
        this.contjshj.setName("contjshj");
        this.contdjjg.setName("contdjjg");
        this.contkpjg.setName("contkpjg");
        this.contdjrq.setName("contdjrq");
        this.chkflag.setName("chkflag");
        this.contkh.setName("contkh");
        this.contzjjg.setName("contzjjg");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.txtywdjbh.setName("txtywdjbh");
        this.pkbizdate.setName("pkbizdate");
        this.txtldh.setName("txtldh");
        this.txtqydw.setName("txtqydw");
        this.txtdjdw.setName("txtdjdw");
        this.txtkpdw.setName("txtkpdw");
        this.txtskdw.setName("txtskdw");
        this.txtdjpzh.setName("txtdjpzh");
        this.txtkppzh.setName("txtkppzh");
        this.txtskpzh.setName("txtskpzh");
        this.txtbiznumber.setName("txtbiznumber");
        this.txtfph.setName("txtfph");
        this.txtzje.setName("txtzje");
        this.txtxsy.setName("txtxsy");
        this.txttjlb.setName("txttjlb");
        this.txtbizState.setName("txtbizState");
        this.txtbeizhu.setName("txtbeizhu");
        this.txtdjtcmc.setName("txtdjtcmc");
        this.txtdjr.setName("txtdjr");
        this.txtdjtcbm.setName("txtdjtcbm");
        this.txtdjxmbm.setName("txtdjxmbm");
        this.txtdjxmmc.setName("txtdjxmmc");
        this.txtxslb.setName("txtxslb");
        this.txtsklb.setName("txtsklb");
        this.txtjxbs.setName("txtjxbs");
        this.txtklj.setName("txtklj");
        this.txtzkl.setName("txtzkl");
        this.txtjsje.setName("txtjsje");
        this.txtse.setName("txtse");
        this.txtjshj.setName("txtjshj");
        this.txtdjjg.setName("txtdjjg");
        this.txtkpjg.setName("txtkpjg");
        this.pkdjrq.setName("pkdjrq");
        this.txtkh.setName("txtkh");
        this.txtzjjg.setName("txtzjjg");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // contywdjbh		
        this.contywdjbh.setBoundLabelText(resHelper.getString("contywdjbh.boundLabelText"));		
        this.contywdjbh.setBoundLabelLength(100);		
        this.contywdjbh.setBoundLabelUnderline(true);		
        this.contywdjbh.setVisible(true);
        // contbizdate		
        this.contbizdate.setBoundLabelText(resHelper.getString("contbizdate.boundLabelText"));		
        this.contbizdate.setBoundLabelLength(100);		
        this.contbizdate.setBoundLabelUnderline(true);		
        this.contbizdate.setVisible(true);
        // contldh		
        this.contldh.setBoundLabelText(resHelper.getString("contldh.boundLabelText"));		
        this.contldh.setBoundLabelLength(100);		
        this.contldh.setBoundLabelUnderline(true);		
        this.contldh.setVisible(true);
        // contqydw		
        this.contqydw.setBoundLabelText(resHelper.getString("contqydw.boundLabelText"));		
        this.contqydw.setBoundLabelLength(100);		
        this.contqydw.setBoundLabelUnderline(true);		
        this.contqydw.setVisible(true);
        // contdjdw		
        this.contdjdw.setBoundLabelText(resHelper.getString("contdjdw.boundLabelText"));		
        this.contdjdw.setBoundLabelLength(100);		
        this.contdjdw.setBoundLabelUnderline(true);		
        this.contdjdw.setVisible(true);
        // contkpdw		
        this.contkpdw.setBoundLabelText(resHelper.getString("contkpdw.boundLabelText"));		
        this.contkpdw.setBoundLabelLength(100);		
        this.contkpdw.setBoundLabelUnderline(true);		
        this.contkpdw.setVisible(true);
        // contskdw		
        this.contskdw.setBoundLabelText(resHelper.getString("contskdw.boundLabelText"));		
        this.contskdw.setBoundLabelLength(100);		
        this.contskdw.setBoundLabelUnderline(true);		
        this.contskdw.setVisible(true);
        // contdjpzh		
        this.contdjpzh.setBoundLabelText(resHelper.getString("contdjpzh.boundLabelText"));		
        this.contdjpzh.setBoundLabelLength(100);		
        this.contdjpzh.setBoundLabelUnderline(true);		
        this.contdjpzh.setVisible(true);
        // contkppzh		
        this.contkppzh.setBoundLabelText(resHelper.getString("contkppzh.boundLabelText"));		
        this.contkppzh.setBoundLabelLength(100);		
        this.contkppzh.setBoundLabelUnderline(true);		
        this.contkppzh.setVisible(true);
        // contskpzh		
        this.contskpzh.setBoundLabelText(resHelper.getString("contskpzh.boundLabelText"));		
        this.contskpzh.setBoundLabelLength(100);		
        this.contskpzh.setBoundLabelUnderline(true);		
        this.contskpzh.setVisible(true);
        // contbiznumber		
        this.contbiznumber.setBoundLabelText(resHelper.getString("contbiznumber.boundLabelText"));		
        this.contbiznumber.setBoundLabelLength(100);		
        this.contbiznumber.setBoundLabelUnderline(true);		
        this.contbiznumber.setVisible(true);
        // contfph		
        this.contfph.setBoundLabelText(resHelper.getString("contfph.boundLabelText"));		
        this.contfph.setBoundLabelLength(100);		
        this.contfph.setBoundLabelUnderline(true);		
        this.contfph.setVisible(true);
        // contzje		
        this.contzje.setBoundLabelText(resHelper.getString("contzje.boundLabelText"));		
        this.contzje.setBoundLabelLength(100);		
        this.contzje.setBoundLabelUnderline(true);		
        this.contzje.setVisible(true);
        // contxsy		
        this.contxsy.setBoundLabelText(resHelper.getString("contxsy.boundLabelText"));		
        this.contxsy.setBoundLabelLength(100);		
        this.contxsy.setBoundLabelUnderline(true);		
        this.contxsy.setVisible(true);
        // conttjlb		
        this.conttjlb.setBoundLabelText(resHelper.getString("conttjlb.boundLabelText"));		
        this.conttjlb.setBoundLabelLength(100);		
        this.conttjlb.setBoundLabelUnderline(true);		
        this.conttjlb.setVisible(true);
        // contbizState		
        this.contbizState.setBoundLabelText(resHelper.getString("contbizState.boundLabelText"));		
        this.contbizState.setBoundLabelLength(100);		
        this.contbizState.setBoundLabelUnderline(true);		
        this.contbizState.setVisible(true);
        // contbeizhu		
        this.contbeizhu.setBoundLabelText(resHelper.getString("contbeizhu.boundLabelText"));		
        this.contbeizhu.setBoundLabelLength(100);		
        this.contbeizhu.setBoundLabelUnderline(true);		
        this.contbeizhu.setVisible(true);
        // contdjtcmc		
        this.contdjtcmc.setBoundLabelText(resHelper.getString("contdjtcmc.boundLabelText"));		
        this.contdjtcmc.setBoundLabelLength(100);		
        this.contdjtcmc.setBoundLabelUnderline(true);		
        this.contdjtcmc.setVisible(true);
        // contdjr		
        this.contdjr.setBoundLabelText(resHelper.getString("contdjr.boundLabelText"));		
        this.contdjr.setBoundLabelLength(100);		
        this.contdjr.setBoundLabelUnderline(true);		
        this.contdjr.setVisible(true);
        // contdjtcbm		
        this.contdjtcbm.setBoundLabelText(resHelper.getString("contdjtcbm.boundLabelText"));		
        this.contdjtcbm.setBoundLabelLength(100);		
        this.contdjtcbm.setBoundLabelUnderline(true);		
        this.contdjtcbm.setVisible(true);
        // contdjxmbm		
        this.contdjxmbm.setBoundLabelText(resHelper.getString("contdjxmbm.boundLabelText"));		
        this.contdjxmbm.setBoundLabelLength(100);		
        this.contdjxmbm.setBoundLabelUnderline(true);		
        this.contdjxmbm.setVisible(true);
        // contdjxmmc		
        this.contdjxmmc.setBoundLabelText(resHelper.getString("contdjxmmc.boundLabelText"));		
        this.contdjxmmc.setBoundLabelLength(100);		
        this.contdjxmmc.setBoundLabelUnderline(true);		
        this.contdjxmmc.setVisible(true);
        // contxslb		
        this.contxslb.setBoundLabelText(resHelper.getString("contxslb.boundLabelText"));		
        this.contxslb.setBoundLabelLength(100);		
        this.contxslb.setBoundLabelUnderline(true);		
        this.contxslb.setVisible(true);
        // contsklb		
        this.contsklb.setBoundLabelText(resHelper.getString("contsklb.boundLabelText"));		
        this.contsklb.setBoundLabelLength(100);		
        this.contsklb.setBoundLabelUnderline(true);		
        this.contsklb.setVisible(true);
        // contjxbs		
        this.contjxbs.setBoundLabelText(resHelper.getString("contjxbs.boundLabelText"));		
        this.contjxbs.setBoundLabelLength(100);		
        this.contjxbs.setBoundLabelUnderline(true);		
        this.contjxbs.setVisible(true);
        // contklj		
        this.contklj.setBoundLabelText(resHelper.getString("contklj.boundLabelText"));		
        this.contklj.setBoundLabelLength(100);		
        this.contklj.setBoundLabelUnderline(true);		
        this.contklj.setVisible(true);
        // contzkl		
        this.contzkl.setBoundLabelText(resHelper.getString("contzkl.boundLabelText"));		
        this.contzkl.setBoundLabelLength(100);		
        this.contzkl.setBoundLabelUnderline(true);		
        this.contzkl.setVisible(true);
        // contjsje		
        this.contjsje.setBoundLabelText(resHelper.getString("contjsje.boundLabelText"));		
        this.contjsje.setBoundLabelLength(100);		
        this.contjsje.setBoundLabelUnderline(true);		
        this.contjsje.setVisible(true);
        // contse		
        this.contse.setBoundLabelText(resHelper.getString("contse.boundLabelText"));		
        this.contse.setBoundLabelLength(100);		
        this.contse.setBoundLabelUnderline(true);		
        this.contse.setVisible(true);
        // contjshj		
        this.contjshj.setBoundLabelText(resHelper.getString("contjshj.boundLabelText"));		
        this.contjshj.setBoundLabelLength(100);		
        this.contjshj.setBoundLabelUnderline(true);		
        this.contjshj.setVisible(true);
        // contdjjg		
        this.contdjjg.setBoundLabelText(resHelper.getString("contdjjg.boundLabelText"));		
        this.contdjjg.setBoundLabelLength(100);		
        this.contdjjg.setBoundLabelUnderline(true);		
        this.contdjjg.setVisible(true);
        // contkpjg		
        this.contkpjg.setBoundLabelText(resHelper.getString("contkpjg.boundLabelText"));		
        this.contkpjg.setBoundLabelLength(100);		
        this.contkpjg.setBoundLabelUnderline(true);		
        this.contkpjg.setVisible(true);
        // contdjrq		
        this.contdjrq.setBoundLabelText(resHelper.getString("contdjrq.boundLabelText"));		
        this.contdjrq.setBoundLabelLength(100);		
        this.contdjrq.setBoundLabelUnderline(true);		
        this.contdjrq.setVisible(true);
        // chkflag		
        this.chkflag.setText(resHelper.getString("chkflag.text"));		
        this.chkflag.setVisible(true);		
        this.chkflag.setHorizontalAlignment(2);
        // contkh		
        this.contkh.setBoundLabelText(resHelper.getString("contkh.boundLabelText"));		
        this.contkh.setBoundLabelLength(100);		
        this.contkh.setBoundLabelUnderline(true);		
        this.contkh.setVisible(true);
        // contzjjg		
        this.contzjjg.setBoundLabelText(resHelper.getString("contzjjg.boundLabelText"));		
        this.contzjjg.setBoundLabelLength(100);		
        this.contzjjg.setBoundLabelUnderline(true);		
        this.contzjjg.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // txtDescription
        // txtywdjbh		
        this.txtywdjbh.setVisible(true);		
        this.txtywdjbh.setHorizontalAlignment(2);		
        this.txtywdjbh.setMaxLength(100);		
        this.txtywdjbh.setRequired(false);
        // pkbizdate		
        this.pkbizdate.setVisible(true);		
        this.pkbizdate.setRequired(false);
        // txtldh		
        this.txtldh.setVisible(true);		
        this.txtldh.setHorizontalAlignment(2);		
        this.txtldh.setMaxLength(100);		
        this.txtldh.setRequired(false);
        // txtqydw		
        this.txtqydw.setVisible(true);		
        this.txtqydw.setHorizontalAlignment(2);		
        this.txtqydw.setMaxLength(100);		
        this.txtqydw.setRequired(false);
        // txtdjdw		
        this.txtdjdw.setVisible(true);		
        this.txtdjdw.setHorizontalAlignment(2);		
        this.txtdjdw.setMaxLength(100);		
        this.txtdjdw.setRequired(false);
        // txtkpdw		
        this.txtkpdw.setVisible(true);		
        this.txtkpdw.setHorizontalAlignment(2);		
        this.txtkpdw.setMaxLength(100);		
        this.txtkpdw.setRequired(false);
        // txtskdw		
        this.txtskdw.setVisible(true);		
        this.txtskdw.setHorizontalAlignment(2);		
        this.txtskdw.setMaxLength(100);		
        this.txtskdw.setRequired(false);
        // txtdjpzh		
        this.txtdjpzh.setVisible(true);		
        this.txtdjpzh.setHorizontalAlignment(2);		
        this.txtdjpzh.setMaxLength(100);		
        this.txtdjpzh.setRequired(false);
        // txtkppzh		
        this.txtkppzh.setVisible(true);		
        this.txtkppzh.setHorizontalAlignment(2);		
        this.txtkppzh.setMaxLength(100);		
        this.txtkppzh.setRequired(false);
        // txtskpzh		
        this.txtskpzh.setVisible(true);		
        this.txtskpzh.setHorizontalAlignment(2);		
        this.txtskpzh.setMaxLength(100);		
        this.txtskpzh.setRequired(false);
        // txtbiznumber		
        this.txtbiznumber.setVisible(true);		
        this.txtbiznumber.setHorizontalAlignment(2);		
        this.txtbiznumber.setMaxLength(100);		
        this.txtbiznumber.setRequired(false);
        // txtfph		
        this.txtfph.setVisible(true);		
        this.txtfph.setHorizontalAlignment(2);		
        this.txtfph.setMaxLength(100);		
        this.txtfph.setRequired(false);
        // txtzje		
        this.txtzje.setVisible(true);		
        this.txtzje.setHorizontalAlignment(2);		
        this.txtzje.setMaxLength(100);		
        this.txtzje.setRequired(false);
        // txtxsy		
        this.txtxsy.setVisible(true);		
        this.txtxsy.setHorizontalAlignment(2);		
        this.txtxsy.setMaxLength(100);		
        this.txtxsy.setRequired(false);
        // txttjlb		
        this.txttjlb.setVisible(true);		
        this.txttjlb.setHorizontalAlignment(2);		
        this.txttjlb.setMaxLength(100);		
        this.txttjlb.setRequired(false);
        // txtbizState		
        this.txtbizState.setVisible(true);		
        this.txtbizState.setHorizontalAlignment(2);		
        this.txtbizState.setMaxLength(100);		
        this.txtbizState.setRequired(false);
        // txtbeizhu		
        this.txtbeizhu.setVisible(true);		
        this.txtbeizhu.setHorizontalAlignment(2);		
        this.txtbeizhu.setMaxLength(100);		
        this.txtbeizhu.setRequired(false);
        // txtdjtcmc		
        this.txtdjtcmc.setVisible(true);		
        this.txtdjtcmc.setHorizontalAlignment(2);		
        this.txtdjtcmc.setMaxLength(100);		
        this.txtdjtcmc.setRequired(false);
        // txtdjr		
        this.txtdjr.setVisible(true);		
        this.txtdjr.setHorizontalAlignment(2);		
        this.txtdjr.setMaxLength(100);		
        this.txtdjr.setRequired(false);
        // txtdjtcbm		
        this.txtdjtcbm.setVisible(true);		
        this.txtdjtcbm.setHorizontalAlignment(2);		
        this.txtdjtcbm.setMaxLength(100);		
        this.txtdjtcbm.setRequired(false);
        // txtdjxmbm		
        this.txtdjxmbm.setVisible(true);		
        this.txtdjxmbm.setHorizontalAlignment(2);		
        this.txtdjxmbm.setMaxLength(100);		
        this.txtdjxmbm.setRequired(false);
        // txtdjxmmc		
        this.txtdjxmmc.setVisible(true);		
        this.txtdjxmmc.setHorizontalAlignment(2);		
        this.txtdjxmmc.setMaxLength(100);		
        this.txtdjxmmc.setRequired(false);
        // txtxslb		
        this.txtxslb.setVisible(true);		
        this.txtxslb.setHorizontalAlignment(2);		
        this.txtxslb.setMaxLength(100);		
        this.txtxslb.setRequired(false);
        // txtsklb		
        this.txtsklb.setVisible(true);		
        this.txtsklb.setHorizontalAlignment(2);		
        this.txtsklb.setMaxLength(100);		
        this.txtsklb.setRequired(false);
        // txtjxbs		
        this.txtjxbs.setVisible(true);		
        this.txtjxbs.setHorizontalAlignment(2);		
        this.txtjxbs.setMaxLength(100);		
        this.txtjxbs.setRequired(false);
        // txtklj		
        this.txtklj.setVisible(true);		
        this.txtklj.setHorizontalAlignment(2);		
        this.txtklj.setMaxLength(100);		
        this.txtklj.setRequired(false);
        // txtzkl		
        this.txtzkl.setVisible(true);		
        this.txtzkl.setHorizontalAlignment(2);		
        this.txtzkl.setMaxLength(100);		
        this.txtzkl.setRequired(false);
        // txtjsje		
        this.txtjsje.setVisible(true);		
        this.txtjsje.setHorizontalAlignment(2);		
        this.txtjsje.setMaxLength(100);		
        this.txtjsje.setRequired(false);
        // txtse		
        this.txtse.setVisible(true);		
        this.txtse.setHorizontalAlignment(2);		
        this.txtse.setMaxLength(100);		
        this.txtse.setRequired(false);
        // txtjshj		
        this.txtjshj.setVisible(true);		
        this.txtjshj.setHorizontalAlignment(2);		
        this.txtjshj.setMaxLength(100);		
        this.txtjshj.setRequired(false);
        // txtdjjg		
        this.txtdjjg.setVisible(true);		
        this.txtdjjg.setHorizontalAlignment(2);		
        this.txtdjjg.setMaxLength(100);		
        this.txtdjjg.setRequired(false);
        // txtkpjg		
        this.txtkpjg.setVisible(true);		
        this.txtkpjg.setHorizontalAlignment(2);		
        this.txtkpjg.setMaxLength(100);		
        this.txtkpjg.setRequired(false);
        // pkdjrq		
        this.pkdjrq.setVisible(true);		
        this.pkdjrq.setRequired(false);
        // txtkh		
        this.txtkh.setVisible(true);		
        this.txtkh.setHorizontalAlignment(2);		
        this.txtkh.setMaxLength(100);		
        this.txtkh.setRequired(false);
        // txtzjjg		
        this.txtzjjg.setVisible(true);		
        this.txtzjjg.setHorizontalAlignment(2);		
        this.txtzjjg.setMaxLength(100);		
        this.txtzjjg.setRequired(false);
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
        this.setBounds(new Rectangle(0, 0, 1157, 629));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(12, 12, 259, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(290, 606, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(6, 606, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(583, 606, 270, 19));
        this.add(kDLabelContainer4, null);
        contywdjbh.setBounds(new Rectangle(12, 37, 259, 19));
        this.add(contywdjbh, null);
        contbizdate.setBounds(new Rectangle(292, 38, 259, 19));
        this.add(contbizdate, null);
        contldh.setBounds(new Rectangle(14, 195, 259, 19));
        this.add(contldh, null);
        contqydw.setBounds(new Rectangle(293, 222, 259, 19));
        this.add(contqydw, null);
        contdjdw.setBounds(new Rectangle(14, 222, 259, 19));
        this.add(contdjdw, null);
        contkpdw.setBounds(new Rectangle(578, 222, 259, 19));
        this.add(contkpdw, null);
        contskdw.setBounds(new Rectangle(857, 222, 259, 19));
        this.add(contskdw, null);
        contdjpzh.setBounds(new Rectangle(854, 532, 259, 19));
        this.add(contdjpzh, null);
        contkppzh.setBounds(new Rectangle(289, 534, 259, 19));
        this.add(contkppzh, null);
        contskpzh.setBounds(new Rectangle(10, 533, 259, 19));
        this.add(contskpzh, null);
        contbiznumber.setBounds(new Rectangle(579, 534, 259, 19));
        this.add(contbiznumber, null);
        contfph.setBounds(new Rectangle(577, 249, 259, 19));
        this.add(contfph, null);
        contzje.setBounds(new Rectangle(13, 277, 259, 19));
        this.add(contzje, null);
        contxsy.setBounds(new Rectangle(14, 250, 259, 19));
        this.add(contxsy, null);
        conttjlb.setBounds(new Rectangle(292, 249, 259, 19));
        this.add(conttjlb, null);
        contbizState.setBounds(new Rectangle(861, 38, 259, 19));
        this.add(contbizState, null);
        contbeizhu.setBounds(new Rectangle(294, 276, 828, 19));
        this.add(contbeizhu, null);
        contdjtcmc.setBounds(new Rectangle(572, 331, 259, 19));
        this.add(contdjtcmc, null);
        contdjr.setBounds(new Rectangle(14, 332, 259, 19));
        this.add(contdjr, null);
        contdjtcbm.setBounds(new Rectangle(289, 332, 259, 19));
        this.add(contdjtcbm, null);
        contdjxmbm.setBounds(new Rectangle(855, 327, 259, 19));
        this.add(contdjxmbm, null);
        contdjxmmc.setBounds(new Rectangle(14, 361, 259, 19));
        this.add(contdjxmmc, null);
        contxslb.setBounds(new Rectangle(288, 360, 259, 19));
        this.add(contxslb, null);
        contsklb.setBounds(new Rectangle(572, 358, 259, 19));
        this.add(contsklb, null);
        contjxbs.setBounds(new Rectangle(855, 357, 259, 19));
        this.add(contjxbs, null);
        contklj.setBounds(new Rectangle(14, 387, 259, 19));
        this.add(contklj, null);
        contzkl.setBounds(new Rectangle(288, 387, 259, 19));
        this.add(contzkl, null);
        contjsje.setBounds(new Rectangle(571, 387, 259, 19));
        this.add(contjsje, null);
        contse.setBounds(new Rectangle(854, 384, 259, 19));
        this.add(contse, null);
        contjshj.setBounds(new Rectangle(14, 414, 259, 19));
        this.add(contjshj, null);
        contdjjg.setBounds(new Rectangle(13, 90, 259, 19));
        this.add(contdjjg, null);
        contkpjg.setBounds(new Rectangle(296, 90, 259, 19));
        this.add(contkpjg, null);
        contdjrq.setBounds(new Rectangle(291, 13, 259, 19));
        this.add(contdjrq, null);
        chkflag.setBounds(new Rectangle(861, 15, 259, 19));
        this.add(chkflag, null);
        contkh.setBounds(new Rectangle(14, 141, 259, 19));
        this.add(contkh, null);
        contzjjg.setBounds(new Rectangle(578, 91, 257, 19));
        this.add(contzjjg, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contywdjbh
        contywdjbh.setBoundEditor(txtywdjbh);
        //contbizdate
        contbizdate.setBoundEditor(pkbizdate);
        //contldh
        contldh.setBoundEditor(txtldh);
        //contqydw
        contqydw.setBoundEditor(txtqydw);
        //contdjdw
        contdjdw.setBoundEditor(txtdjdw);
        //contkpdw
        contkpdw.setBoundEditor(txtkpdw);
        //contskdw
        contskdw.setBoundEditor(txtskdw);
        //contdjpzh
        contdjpzh.setBoundEditor(txtdjpzh);
        //contkppzh
        contkppzh.setBoundEditor(txtkppzh);
        //contskpzh
        contskpzh.setBoundEditor(txtskpzh);
        //contbiznumber
        contbiznumber.setBoundEditor(txtbiznumber);
        //contfph
        contfph.setBoundEditor(txtfph);
        //contzje
        contzje.setBoundEditor(txtzje);
        //contxsy
        contxsy.setBoundEditor(txtxsy);
        //conttjlb
        conttjlb.setBoundEditor(txttjlb);
        //contbizState
        contbizState.setBoundEditor(txtbizState);
        //contbeizhu
        contbeizhu.setBoundEditor(txtbeizhu);
        //contdjtcmc
        contdjtcmc.setBoundEditor(txtdjtcmc);
        //contdjr
        contdjr.setBoundEditor(txtdjr);
        //contdjtcbm
        contdjtcbm.setBoundEditor(txtdjtcbm);
        //contdjxmbm
        contdjxmbm.setBoundEditor(txtdjxmbm);
        //contdjxmmc
        contdjxmmc.setBoundEditor(txtdjxmmc);
        //contxslb
        contxslb.setBoundEditor(txtxslb);
        //contsklb
        contsklb.setBoundEditor(txtsklb);
        //contjxbs
        contjxbs.setBoundEditor(txtjxbs);
        //contklj
        contklj.setBoundEditor(txtklj);
        //contzkl
        contzkl.setBoundEditor(txtzkl);
        //contjsje
        contjsje.setBoundEditor(txtjsje);
        //contse
        contse.setBoundEditor(txtse);
        //contjshj
        contjshj.setBoundEditor(txtjshj);
        //contdjjg
        contdjjg.setBoundEditor(txtdjjg);
        //contkpjg
        contkpjg.setBoundEditor(txtkpjg);
        //contdjrq
        contdjrq.setBoundEditor(pkdjrq);
        //contkh
        contkh.setBoundEditor(txtkh);
        //contzjjg
        contzjjg.setBoundEditor(txtzjjg);

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
        this.menuBar.add(menuTool);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("flag", boolean.class, this.chkflag, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("ywdjbh", String.class, this.txtywdjbh, "text");
		dataBinder.registerBinding("bizdate", java.util.Date.class, this.pkbizdate, "value");
		dataBinder.registerBinding("ldh", String.class, this.txtldh, "text");
		dataBinder.registerBinding("qydw", String.class, this.txtqydw, "text");
		dataBinder.registerBinding("djdw", String.class, this.txtdjdw, "text");
		dataBinder.registerBinding("kpdw", String.class, this.txtkpdw, "text");
		dataBinder.registerBinding("skdw", String.class, this.txtskdw, "text");
		dataBinder.registerBinding("djpzh", String.class, this.txtdjpzh, "text");
		dataBinder.registerBinding("kppzh", String.class, this.txtkppzh, "text");
		dataBinder.registerBinding("skpzh", String.class, this.txtskpzh, "text");
		dataBinder.registerBinding("biznumber", String.class, this.txtbiznumber, "text");
		dataBinder.registerBinding("fph", String.class, this.txtfph, "text");
		dataBinder.registerBinding("zje", String.class, this.txtzje, "text");
		dataBinder.registerBinding("xsy", String.class, this.txtxsy, "text");
		dataBinder.registerBinding("tjlb", String.class, this.txttjlb, "text");
		dataBinder.registerBinding("bizState", String.class, this.txtbizState, "text");
		dataBinder.registerBinding("beizhu", String.class, this.txtbeizhu, "text");
		dataBinder.registerBinding("djtcmc", String.class, this.txtdjtcmc, "text");
		dataBinder.registerBinding("djr", String.class, this.txtdjr, "text");
		dataBinder.registerBinding("djtcbm", String.class, this.txtdjtcbm, "text");
		dataBinder.registerBinding("djxmbm", String.class, this.txtdjxmbm, "text");
		dataBinder.registerBinding("djxmmc", String.class, this.txtdjxmmc, "text");
		dataBinder.registerBinding("xslb", String.class, this.txtxslb, "text");
		dataBinder.registerBinding("sklb", String.class, this.txtsklb, "text");
		dataBinder.registerBinding("jxbs", String.class, this.txtjxbs, "text");
		dataBinder.registerBinding("klj", String.class, this.txtklj, "text");
		dataBinder.registerBinding("zkl", String.class, this.txtzkl, "text");
		dataBinder.registerBinding("jsje", String.class, this.txtjsje, "text");
		dataBinder.registerBinding("se", String.class, this.txtse, "text");
		dataBinder.registerBinding("jshj", String.class, this.txtjshj, "text");
		dataBinder.registerBinding("djjg", String.class, this.txtdjjg, "text");
		dataBinder.registerBinding("kpjg", String.class, this.txtkpjg, "text");
		dataBinder.registerBinding("djrq", java.util.Date.class, this.pkdjrq, "value");
		dataBinder.registerBinding("kh", String.class, this.txtkh, "text");
		dataBinder.registerBinding("zjjg", String.class, this.txtzjjg, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.richinf.app.RichExamTempTabEditUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.richinf.RichExamTempTabInfo)ov;
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
		getValidateHelper().registerBindProperty("flag", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ywdjbh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizdate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ldh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qydw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djdw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpdw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("skdw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djpzh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kppzh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("skpzh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biznumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fph", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zje", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xsy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tjlb", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djtcmc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djtcbm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djxmbm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djxmmc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xslb", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sklb", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jxbs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("klj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zkl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jsje", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("se", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jshj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djjg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpjg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djrq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zjjg", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
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
        sic.add(new SelectorItemInfo("flag"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("ywdjbh"));
        sic.add(new SelectorItemInfo("bizdate"));
        sic.add(new SelectorItemInfo("ldh"));
        sic.add(new SelectorItemInfo("qydw"));
        sic.add(new SelectorItemInfo("djdw"));
        sic.add(new SelectorItemInfo("kpdw"));
        sic.add(new SelectorItemInfo("skdw"));
        sic.add(new SelectorItemInfo("djpzh"));
        sic.add(new SelectorItemInfo("kppzh"));
        sic.add(new SelectorItemInfo("skpzh"));
        sic.add(new SelectorItemInfo("biznumber"));
        sic.add(new SelectorItemInfo("fph"));
        sic.add(new SelectorItemInfo("zje"));
        sic.add(new SelectorItemInfo("xsy"));
        sic.add(new SelectorItemInfo("tjlb"));
        sic.add(new SelectorItemInfo("bizState"));
        sic.add(new SelectorItemInfo("beizhu"));
        sic.add(new SelectorItemInfo("djtcmc"));
        sic.add(new SelectorItemInfo("djr"));
        sic.add(new SelectorItemInfo("djtcbm"));
        sic.add(new SelectorItemInfo("djxmbm"));
        sic.add(new SelectorItemInfo("djxmmc"));
        sic.add(new SelectorItemInfo("xslb"));
        sic.add(new SelectorItemInfo("sklb"));
        sic.add(new SelectorItemInfo("jxbs"));
        sic.add(new SelectorItemInfo("klj"));
        sic.add(new SelectorItemInfo("zkl"));
        sic.add(new SelectorItemInfo("jsje"));
        sic.add(new SelectorItemInfo("se"));
        sic.add(new SelectorItemInfo("jshj"));
        sic.add(new SelectorItemInfo("djjg"));
        sic.add(new SelectorItemInfo("kpjg"));
        sic.add(new SelectorItemInfo("djrq"));
        sic.add(new SelectorItemInfo("kh"));
        sic.add(new SelectorItemInfo("zjjg"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.richinf.client", "RichExamTempTabEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.richinf.client.RichExamTempTabEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamTempTabFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamTempTabInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamTempTabInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
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