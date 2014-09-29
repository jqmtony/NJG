/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.client;

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
public abstract class AbstractJudgesEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractJudgesEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjuName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbirthday;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteducation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttelephone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmobile;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprofession;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyears;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurDep;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurPost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttechLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjudgeType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpersonName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisOuter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjuName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox sex;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbirthday;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmteducation;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttelephone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmobile;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprofession;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyears;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurDep;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcurPost;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttechLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjudgeType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpersonName;
    protected com.kingdee.eas.port.pm.base.JudgesInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractJudgesEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractJudgesEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjuName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbirthday = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteducation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttelephone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmobile = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprofession = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyears = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurDep = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurPost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttechLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjudgeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisUse = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contpersonName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisOuter = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmtjuName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.sex = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkbirthday = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmteducation = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txttelephone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmobile = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtprofession = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyears = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtcurDep = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcurPost = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttechLevel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtjudgeType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtpersonName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contjuName.setName("contjuName");
        this.contsex.setName("contsex");
        this.contbirthday.setName("contbirthday");
        this.conteducation.setName("conteducation");
        this.conttelephone.setName("conttelephone");
        this.contmobile.setName("contmobile");
        this.contprofession.setName("contprofession");
        this.contyears.setName("contyears");
        this.contcurDep.setName("contcurDep");
        this.contcurPost.setName("contcurPost");
        this.conttechLevel.setName("conttechLevel");
        this.contjudgeType.setName("contjudgeType");
        this.chkisUse.setName("chkisUse");
        this.contpersonName.setName("contpersonName");
        this.chkisOuter.setName("chkisOuter");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.prmtjuName.setName("prmtjuName");
        this.sex.setName("sex");
        this.pkbirthday.setName("pkbirthday");
        this.prmteducation.setName("prmteducation");
        this.txttelephone.setName("txttelephone");
        this.txtmobile.setName("txtmobile");
        this.txtprofession.setName("txtprofession");
        this.txtyears.setName("txtyears");
        this.prmtcurDep.setName("prmtcurDep");
        this.txtcurPost.setName("txtcurPost");
        this.txttechLevel.setName("txttechLevel");
        this.prmtjudgeType.setName("prmtjudgeType");
        this.txtpersonName.setName("txtpersonName");
        // CoreUI		
        this.setPreferredSize(new Dimension(585,235));		
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
        // contjuName		
        this.contjuName.setBoundLabelText(resHelper.getString("contjuName.boundLabelText"));		
        this.contjuName.setBoundLabelLength(100);		
        this.contjuName.setBoundLabelUnderline(true);		
        this.contjuName.setVisible(true);
        // contsex		
        this.contsex.setBoundLabelText(resHelper.getString("contsex.boundLabelText"));		
        this.contsex.setBoundLabelLength(100);		
        this.contsex.setBoundLabelUnderline(true);		
        this.contsex.setVisible(true);
        // contbirthday		
        this.contbirthday.setBoundLabelText(resHelper.getString("contbirthday.boundLabelText"));		
        this.contbirthday.setBoundLabelLength(100);		
        this.contbirthday.setBoundLabelUnderline(true);		
        this.contbirthday.setVisible(true);
        // conteducation		
        this.conteducation.setBoundLabelText(resHelper.getString("conteducation.boundLabelText"));		
        this.conteducation.setBoundLabelLength(100);		
        this.conteducation.setBoundLabelUnderline(true);		
        this.conteducation.setVisible(true);
        // conttelephone		
        this.conttelephone.setBoundLabelText(resHelper.getString("conttelephone.boundLabelText"));		
        this.conttelephone.setBoundLabelLength(100);		
        this.conttelephone.setBoundLabelUnderline(true);		
        this.conttelephone.setVisible(true);
        // contmobile		
        this.contmobile.setBoundLabelText(resHelper.getString("contmobile.boundLabelText"));		
        this.contmobile.setBoundLabelLength(100);		
        this.contmobile.setBoundLabelUnderline(true);		
        this.contmobile.setVisible(true);
        // contprofession		
        this.contprofession.setBoundLabelText(resHelper.getString("contprofession.boundLabelText"));		
        this.contprofession.setBoundLabelLength(100);		
        this.contprofession.setBoundLabelUnderline(true);		
        this.contprofession.setVisible(true);
        // contyears		
        this.contyears.setBoundLabelText(resHelper.getString("contyears.boundLabelText"));		
        this.contyears.setBoundLabelLength(100);		
        this.contyears.setBoundLabelUnderline(true);		
        this.contyears.setVisible(true);
        // contcurDep		
        this.contcurDep.setBoundLabelText(resHelper.getString("contcurDep.boundLabelText"));		
        this.contcurDep.setBoundLabelLength(100);		
        this.contcurDep.setBoundLabelUnderline(true);		
        this.contcurDep.setVisible(true);
        // contcurPost		
        this.contcurPost.setBoundLabelText(resHelper.getString("contcurPost.boundLabelText"));		
        this.contcurPost.setBoundLabelLength(100);		
        this.contcurPost.setBoundLabelUnderline(true);		
        this.contcurPost.setVisible(true);
        // conttechLevel		
        this.conttechLevel.setBoundLabelText(resHelper.getString("conttechLevel.boundLabelText"));		
        this.conttechLevel.setBoundLabelLength(100);		
        this.conttechLevel.setBoundLabelUnderline(true);		
        this.conttechLevel.setVisible(true);
        // contjudgeType		
        this.contjudgeType.setBoundLabelText(resHelper.getString("contjudgeType.boundLabelText"));		
        this.contjudgeType.setBoundLabelLength(100);		
        this.contjudgeType.setBoundLabelUnderline(true);		
        this.contjudgeType.setVisible(true);
        // chkisUse		
        this.chkisUse.setText(resHelper.getString("chkisUse.text"));		
        this.chkisUse.setVisible(true);		
        this.chkisUse.setHorizontalAlignment(2);
        // contpersonName		
        this.contpersonName.setBoundLabelText(resHelper.getString("contpersonName.boundLabelText"));		
        this.contpersonName.setBoundLabelLength(100);		
        this.contpersonName.setBoundLabelUnderline(true);		
        this.contpersonName.setVisible(true);
        // chkisOuter		
        this.chkisOuter.setText(resHelper.getString("chkisOuter.text"));		
        this.chkisOuter.setVisible(true);		
        this.chkisOuter.setHorizontalAlignment(2);
        this.chkisOuter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkisOuter_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // txtDescription
        // prmtjuName		
        this.prmtjuName.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtjuName.setVisible(true);		
        this.prmtjuName.setEditable(true);		
        this.prmtjuName.setDisplayFormat("$name$");		
        this.prmtjuName.setEditFormat("$number$");		
        this.prmtjuName.setCommitFormat("$number$");		
        this.prmtjuName.setRequired(false);
        prmtjuName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtjuName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtjuName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtjuName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // sex		
        this.sex.setVisible(true);		
        this.sex.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.person.Genders").toArray());		
        this.sex.setRequired(false);
        // pkbirthday		
        this.pkbirthday.setVisible(true);		
        this.pkbirthday.setRequired(false);
        // prmteducation		
        this.prmteducation.setQueryInfo("com.kingdee.eas.basedata.hraux.app.DiplomaQuery");		
        this.prmteducation.setVisible(true);		
        this.prmteducation.setEditable(true);		
        this.prmteducation.setDisplayFormat("$name$");		
        this.prmteducation.setEditFormat("$number$");		
        this.prmteducation.setCommitFormat("$number$");		
        this.prmteducation.setRequired(false);
        // txttelephone		
        this.txttelephone.setVisible(true);		
        this.txttelephone.setHorizontalAlignment(2);		
        this.txttelephone.setMaxLength(100);		
        this.txttelephone.setRequired(false);
        // txtmobile		
        this.txtmobile.setVisible(true);		
        this.txtmobile.setHorizontalAlignment(2);		
        this.txtmobile.setMaxLength(100);		
        this.txtmobile.setRequired(false);
        // txtprofession		
        this.txtprofession.setVisible(true);		
        this.txtprofession.setHorizontalAlignment(2);		
        this.txtprofession.setMaxLength(100);		
        this.txtprofession.setRequired(false);
        // txtyears		
        this.txtyears.setVisible(true);		
        this.txtyears.setHorizontalAlignment(2);		
        this.txtyears.setMaxLength(100);		
        this.txtyears.setRequired(false);
        // prmtcurDep		
        this.prmtcurDep.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtcurDep.setVisible(true);		
        this.prmtcurDep.setEditable(true);		
        this.prmtcurDep.setDisplayFormat("$name$");		
        this.prmtcurDep.setEditFormat("$number$");		
        this.prmtcurDep.setCommitFormat("$number$");		
        this.prmtcurDep.setRequired(false);
        // txtcurPost		
        this.txtcurPost.setVisible(true);		
        this.txtcurPost.setHorizontalAlignment(2);		
        this.txtcurPost.setMaxLength(100);		
        this.txtcurPost.setRequired(false);
        // txttechLevel		
        this.txttechLevel.setVisible(true);		
        this.txttechLevel.setHorizontalAlignment(2);		
        this.txttechLevel.setMaxLength(80);		
        this.txttechLevel.setRequired(false);
        // prmtjudgeType		
        this.prmtjudgeType.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");		
        this.prmtjudgeType.setVisible(true);		
        this.prmtjudgeType.setEditable(true);		
        this.prmtjudgeType.setDisplayFormat("$name$");		
        this.prmtjudgeType.setEditFormat("$number$");		
        this.prmtjudgeType.setCommitFormat("$number$");		
        this.prmtjudgeType.setRequired(false);
        		prmtjudgeType.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI prmtjudgeType_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtjudgeType_F7ListUI == null) {
					try {
						prmtjudgeType_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtjudgeType_F7ListUI));
					prmtjudgeType_F7ListUI.setF7Use(true,ctx);
					prmtjudgeType.setSelector(prmtjudgeType_F7ListUI);
				}
			}
		});
					
        // txtpersonName		
        this.txtpersonName.setVisible(true);		
        this.txtpersonName.setHorizontalAlignment(2);		
        this.txtpersonName.setMaxLength(80);		
        this.txtpersonName.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtjuName,sex,pkbirthday,prmteducation,txttelephone,txtmobile,txtprofession,txtyears,txttechLevel,prmtcurDep,txtcurPost,prmtjudgeType,chkisUse,txtpersonName,chkisOuter}));
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
        this.setBounds(new Rectangle(0, 0, 585, 235));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 585, 235));
        kDLabelContainer1.setBounds(new Rectangle(14, 34, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(14, 34, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(158, 240, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(158, 240, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(160, 267, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(160, 267, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(158, 290, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(158, 290, 270, 19, 0));
        contjuName.setBounds(new Rectangle(14, 58, 270, 19));
        this.add(contjuName, new KDLayout.Constraints(14, 58, 270, 19, 0));
        contsex.setBounds(new Rectangle(299, 58, 270, 19));
        this.add(contsex, new KDLayout.Constraints(299, 58, 270, 19, 0));
        contbirthday.setBounds(new Rectangle(14, 82, 270, 19));
        this.add(contbirthday, new KDLayout.Constraints(14, 82, 270, 19, 0));
        conteducation.setBounds(new Rectangle(299, 82, 270, 19));
        this.add(conteducation, new KDLayout.Constraints(299, 82, 270, 19, 0));
        conttelephone.setBounds(new Rectangle(299, 154, 270, 19));
        this.add(conttelephone, new KDLayout.Constraints(299, 154, 270, 19, 0));
        contmobile.setBounds(new Rectangle(14, 179, 270, 19));
        this.add(contmobile, new KDLayout.Constraints(14, 179, 270, 19, 0));
        contprofession.setBounds(new Rectangle(14, 106, 270, 19));
        this.add(contprofession, new KDLayout.Constraints(14, 106, 270, 19, 0));
        contyears.setBounds(new Rectangle(299, 106, 270, 19));
        this.add(contyears, new KDLayout.Constraints(299, 106, 270, 19, 0));
        contcurDep.setBounds(new Rectangle(299, 130, 270, 19));
        this.add(contcurDep, new KDLayout.Constraints(299, 130, 270, 19, 0));
        contcurPost.setBounds(new Rectangle(14, 154, 270, 19));
        this.add(contcurPost, new KDLayout.Constraints(14, 154, 270, 19, 0));
        conttechLevel.setBounds(new Rectangle(14, 130, 270, 19));
        this.add(conttechLevel, new KDLayout.Constraints(14, 130, 270, 19, 0));
        contjudgeType.setBounds(new Rectangle(299, 34, 270, 19));
        this.add(contjudgeType, new KDLayout.Constraints(299, 34, 270, 19, 0));
        chkisUse.setBounds(new Rectangle(14, 205, 270, 19));
        this.add(chkisUse, new KDLayout.Constraints(14, 205, 270, 19, 0));
        contpersonName.setBounds(new Rectangle(14, 58, 270, 19));
        this.add(contpersonName, new KDLayout.Constraints(14, 58, 270, 19, 0));
        chkisOuter.setBounds(new Rectangle(14, 10, 270, 19));
        this.add(chkisOuter, new KDLayout.Constraints(14, 10, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contjuName
        contjuName.setBoundEditor(prmtjuName);
        //contsex
        contsex.setBoundEditor(sex);
        //contbirthday
        contbirthday.setBoundEditor(pkbirthday);
        //conteducation
        conteducation.setBoundEditor(prmteducation);
        //conttelephone
        conttelephone.setBoundEditor(txttelephone);
        //contmobile
        contmobile.setBoundEditor(txtmobile);
        //contprofession
        contprofession.setBoundEditor(txtprofession);
        //contyears
        contyears.setBoundEditor(txtyears);
        //contcurDep
        contcurDep.setBoundEditor(prmtcurDep);
        //contcurPost
        contcurPost.setBoundEditor(txtcurPost);
        //conttechLevel
        conttechLevel.setBoundEditor(txttechLevel);
        //contjudgeType
        contjudgeType.setBoundEditor(prmtjudgeType);
        //contpersonName
        contpersonName.setBoundEditor(txtpersonName);

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
		dataBinder.registerBinding("isUse", boolean.class, this.chkisUse, "selected");
		dataBinder.registerBinding("isOuter", boolean.class, this.chkisOuter, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("juName", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtjuName, "data");
		dataBinder.registerBinding("sex", com.kingdee.eas.basedata.person.Genders.class, this.sex, "selectedItem");
		dataBinder.registerBinding("birthday", java.util.Date.class, this.pkbirthday, "value");
		dataBinder.registerBinding("education", com.kingdee.eas.basedata.hraux.DiplomaInfo.class, this.prmteducation, "data");
		dataBinder.registerBinding("telephone", String.class, this.txttelephone, "text");
		dataBinder.registerBinding("mobile", String.class, this.txtmobile, "text");
		dataBinder.registerBinding("profession", String.class, this.txtprofession, "text");
		dataBinder.registerBinding("years", String.class, this.txtyears, "text");
		dataBinder.registerBinding("curDep", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtcurDep, "data");
		dataBinder.registerBinding("curPost", String.class, this.txtcurPost, "text");
		dataBinder.registerBinding("techLevel", String.class, this.txttechLevel, "text");
		dataBinder.registerBinding("judgeType", com.kingdee.eas.port.pm.base.JudgesInfo.class, this.prmtjudgeType, "data");
		dataBinder.registerBinding("personName", String.class, this.txtpersonName, "text");		
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
	    return "com.kingdee.eas.port.pm.base.app.JudgesEditUIHandler";
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
        this.prmtjuName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.base.JudgesInfo)ov;
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
		getValidateHelper().registerBindProperty("isUse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isOuter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("juName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("birthday", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("education", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mobile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("profession", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("years", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curDep", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curPost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("techLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("judgeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personName", ValidateHelper.ON_SAVE);    		
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
     * output chkisOuter_stateChanged method
     */
    protected void chkisOuter_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtjuName_dataChanged method
     */
    protected void prmtjuName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmtjuName_Changed() method
     */
    public void prmtjuName_Changed() throws Exception
    {
        System.out.println("prmtjuName_Changed() Function is executed!");
            txtpersonName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtjuName.getData(),"name")));


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
        sic.add(new SelectorItemInfo("isUse"));
        sic.add(new SelectorItemInfo("isOuter"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("juName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("juName.id"));
        	sic.add(new SelectorItemInfo("juName.number"));
        	sic.add(new SelectorItemInfo("juName.name"));
		}
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("birthday"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("education.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("education.id"));
        	sic.add(new SelectorItemInfo("education.number"));
        	sic.add(new SelectorItemInfo("education.name"));
		}
        sic.add(new SelectorItemInfo("telephone"));
        sic.add(new SelectorItemInfo("mobile"));
        sic.add(new SelectorItemInfo("profession"));
        sic.add(new SelectorItemInfo("years"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curDep.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curDep.id"));
        	sic.add(new SelectorItemInfo("curDep.number"));
        	sic.add(new SelectorItemInfo("curDep.name"));
		}
        sic.add(new SelectorItemInfo("curPost"));
        sic.add(new SelectorItemInfo("techLevel"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("judgeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("judgeType.id"));
        	sic.add(new SelectorItemInfo("judgeType.number"));
        	sic.add(new SelectorItemInfo("judgeType.name"));
		}
        sic.add(new SelectorItemInfo("personName"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.base.client", "JudgesEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.pm.base.client.JudgesEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.base.JudgesFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.base.JudgesInfo objectValue = new com.kingdee.eas.port.pm.base.JudgesInfo();
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
		vo.put("sex",new Integer(1));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}