package com.sunland.hzhc.bean.i_inspect_person;

import com.sunlandgroup.def.bean.result.ResultBase;

public class InspectPersonJsonRet extends ResultBase {
    /**
     * ryxxReq	人员信息
     * fhm	返回码	代码项，000：正常，101：异常
     * fhmbc	返回码备注	返回码备注
     * zj	主键	人员主键
     * jnjw	境内境外	代码项，01：境内，02：境外
     * xm	姓名	姓名
     * xb	性别	代码项，1：男，2：女
     * csrq	出生日期	出生日期，格式为
     * “YYYY-MM-DD”
     * mz	民族	代码项，详见第4章B04
     * hjdqh	户籍地区	代码项，JNJW为01（境内）时，详见第4章B05;JNJW为02（境外）时，详见第4章B06
     * zjlx	证件类型	代码项，详见第4章B07
     * zjhm	证件号码	证件号码
     * zp	人员照片	人员照片
     * sjhm	手机号码	手机号码
     * sfsj	是否司机	代码项,0表示否，1表示是
     * hcjg	核查结果	核查结果
     * rylb	人员类别	人员类别
     * bjxx	背景信息	背景信息
     */
    private String status;
    private RyxxRes ryxx;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RyxxRes getRyxx() {
        return ryxx;
    }

    public void setRyxx(RyxxRes ryxx) {
        this.ryxx = ryxx;
    }
}
