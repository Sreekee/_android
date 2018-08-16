package com.spanenterprises.spanenquirer.Model;

import io.realm.RealmObject;

public class SubjectsModel extends RealmObject {

    private String subCode;
    private String subName;
    private int deptId;
    private String semNo;

    public String getSemNo() {
        return semNo;
    }

    public void setSemNo(String semNo) {
        this.semNo = semNo;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }


}
