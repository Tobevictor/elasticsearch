package com.learn.model.SA;

import java.io.Serializable;

/**
 * @author dshuyou
 * @date 2019/11/4 17:35
 */
public class VIEW_MAP_JSYD_BJINFO implements Serializable {
    //BJNO
    private String BJNO;
    //BJNAME :报建名称
    private String BJNAME;
    //WFNAME :流程名称
    private String WFNAME;
    //INPUTDATE :申报日期
    private String INPUTDATE;
    //BJUNIT ：:申报单位
    private String BJUNIT;
    //CTN_NAME : 区域
    private String CTN_NAME;

    public String getBJNO() {
        return BJNO;
    }

    public void setBJNO(String BJNO) {
        this.BJNO = BJNO;
    }

    public String getBJNAME() {
        return BJNAME;
    }

    public void setBJNAME(String BJNAME) {
        this.BJNAME = BJNAME;
    }

    public String getWFNAME() {
        return WFNAME;
    }

    public void setWFNAME(String WFNAME) {
        this.WFNAME = WFNAME;
    }

    public String getINPUTDATE() {
        return INPUTDATE;
    }

    public void setINPUTDATE(String INPUTDATE) {
        this.INPUTDATE = INPUTDATE;
    }

    public String getBJUNIT() {
        return BJUNIT;
    }

    public void setBJUNIT(String BJUNIT) {
        this.BJUNIT = BJUNIT;
    }

    public String getCTN_NAME() {
        return CTN_NAME;
    }

    public void setCTN_NAME(String CTN_NAME) {
        this.CTN_NAME = CTN_NAME;
    }

    @Override
    public String toString() {
        return "VIEW_MAP_JSYD_BJINFO{" +
                "BJNO='" + BJNO + '\'' +
                ", BJNAME='" + BJNAME + '\'' +
                ", WFNAME='" + WFNAME + '\'' +
                ", INPUTDATE='" + INPUTDATE + '\'' +
                ", BJUNIT='" + BJUNIT + '\'' +
                ", CTN_NAME='" + CTN_NAME + '\'' +
                '}';
    }
}
