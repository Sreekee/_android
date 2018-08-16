package com.spanenterprises.spanenquirer.Model;

import java.util.Vector;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class DepartmentModel extends RealmObject {

    private String deptName;
    private int deptId;
    private String degree;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}