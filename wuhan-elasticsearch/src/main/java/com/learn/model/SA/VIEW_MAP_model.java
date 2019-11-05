package com.learn.model.SA;

import java.io.Serializable;

/**
 * @author dshuyou
 * @date 2019/11/4 18:42
 */
public class VIEW_MAP_model implements Serializable {
    //BJNO
    private String bjno;
    //BSNUM
    private String bsnum;
    //项目名称
    private String 项目名称;
    //申请人
    private String 申请人;
    //矿山名称
    private String 矿山名称;

    public String getBjno() {
        return bjno;
    }

    public void setBjno(String bino) {
        this.bjno = bino;
    }

    public String getBsnum() {
        return bsnum;
    }

    public void setBsnum(String bsnum) {
        this.bsnum = bsnum;
    }

    public String get项目名称() {
        return 项目名称;
    }

    public void set项目名称(String 项目名称) {
        this.项目名称 = 项目名称;
    }

    public String get申请人() {
        return 申请人;
    }

    public void set申请人(String 申请人) {
        this.申请人 = 申请人;
    }

    public String get矿山名称() {
        return 矿山名称;
    }

    public void set矿山名称(String 矿山名称) {
        this.矿山名称 = 矿山名称;
    }

    @Override
    public String toString() {
        return "VIEW_MAP_model{" +
                "bino='" + bjno + '\'' +
                ", bsnum='" + bsnum + '\'' +
                ", 项目名称='" + 项目名称 + '\'' +
                ", 申请人='" + 申请人 + '\'' +
                ", 矿山名称='" + 矿山名称 + '\'' +
                '}';
    }
}
