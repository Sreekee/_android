package com.spanenterprises.spanenquirer.Repository;

import android.content.Context;

import com.spanenterprises.spanenquirer.Model.DepartmentModel;
import com.spanenterprises.spanenquirer.Model.SubjectsModel;
import com.spanenterprises.spanenquirer.Utility.UtilsClass;

import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.Util;

public class GetSemesterHelper {

    public Vector<String> getAllDepartment(Context mContext, String degree) {

        Vector<String> deptList = new Vector<>();

        deptList.add("Select");

        Realm.init(mContext);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<DepartmentModel> realmResults = realm.where(DepartmentModel.class)
                .equalTo("degree", degree)
                .findAll();

        String deptName = "";

        if (realmResults != null && realmResults.size() > 0) {

            for (int i = 0; i < realmResults.size(); i++) {

                deptName = realmResults.get(i).getDeptName();

                deptList.add(deptName);
            }
        }

        realm.close();

        return deptList;
    }

    public Vector<SubjectsModel> getSemesterDetails(Context mContext, int selectedDeptId, String selectedSemester) {

        Vector<SubjectsModel> subjectsList = new Vector<>();

        if (UtilsClass.isValidString(selectedSemester) &&  selectedDeptId>=0) {


            Realm.init(mContext);

            Realm realm = Realm.getDefaultInstance();

            RealmResults<SubjectsModel> realmResults = realm.where(SubjectsModel.class)
                    .equalTo("deptId", selectedDeptId)
                    .equalTo("semNo", selectedSemester)
                    .findAll();

            if (realmResults != null && realmResults.size() > 0) {

                subjectsList.addAll(realm.copyFromRealm(realmResults));
            }

            realm.close();
        }

        return subjectsList;
    }

    public int getDepartmentId(Context mContext, String selectedDept) {

        Realm.init(mContext);

        Realm realm = Realm.getDefaultInstance();

        DepartmentModel realmResults = realm.where(DepartmentModel.class).equalTo("deptName", selectedDept).findFirst();

        if (realmResults!=null) {
            int deptId = realmResults.getDeptId();

            if (deptId > 0) {

                realm.close();

                return deptId;
            }
        }

        return -1;
    }

    public boolean saveDepartments(Context mContext, Vector<DepartmentModel> departmentModelVector) {

        Realm.init(mContext);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        realm.where(DepartmentModel.class).findAll().deleteAllFromRealm();

        if (departmentModelVector != null && departmentModelVector.size() > 0) {

            for (DepartmentModel departments : departmentModelVector) {

                DepartmentModel model = realm.createObject(DepartmentModel.class);

                model.setDeptId(departments.getDeptId());

                model.setDeptName(departments.getDeptName());

                model.setDegree(departments.getDegree());
            }

            realm.commitTransaction();

            realm.close();

            return true;
        }

        return false;
    }

    public void saveSubjectsIntoRealm(Context mContext, Vector<SubjectsModel> subjectsModelVector) {

        Realm.init(mContext);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        realm.where(SubjectsModel.class).findAll().deleteAllFromRealm();

        if (subjectsModelVector != null && subjectsModelVector.size() > 0) {

            for (SubjectsModel subjectsModel : subjectsModelVector) {

                SubjectsModel model = realm.createObject(SubjectsModel.class);

                model.setDeptId(subjectsModel.getDeptId());

                model.setSemNo(subjectsModel.getSemNo());

                model.setSubCode(subjectsModel.getSubCode());

                model.setSubName(subjectsModel.getSubName());
            }
        }

        realm.commitTransaction();

        realm.close();
    }
}
