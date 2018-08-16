package com.spanenterprises.spanenquirer.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spanenterprises.spanenquirer.Model.DepartmentModel;
import com.spanenterprises.spanenquirer.Model.SubjectsModel;
import com.spanenterprises.spanenquirer.R;
import com.spanenterprises.spanenquirer.Repository.GetSemesterHelper;

import java.util.Vector;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        readDepartmentFromFirebase();
    }

    private void readDepartmentFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("dept_table");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Vector<DepartmentModel> list = new Vector<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    DepartmentModel model = new DepartmentModel();

                    for (DataSnapshot lastChild : postSnapshot.getChildren()) {

                        if (lastChild.getKey().equals("dept_id")) {

                            model.setDeptId(Integer.parseInt(lastChild.getValue(String.class)));
                        }

                        if (lastChild.getKey().equals("dept_name")) {

                            model.setDeptName(lastChild.getValue(String.class));
                        }

                        if (lastChild.getKey().equals("degree")) {

                            model.setDegree(lastChild.getValue(String.class));
                        }
                    }

                    list.add(model);
                }

                uploadDepartmentsData(list);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Error : ", error.getDetails());
            }
        });
    }

    private void readSubjectsFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("dept_subjects");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null && dataSnapshot.hasChildren()) {

                    Vector<SubjectsModel> list = new Vector<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        SubjectsModel model = new SubjectsModel();

                        for (DataSnapshot lastChild : postSnapshot.getChildren()) {

                            if (lastChild.getKey().equals("dept_id")) {

                                model.setDeptId(Integer.parseInt(lastChild.getValue(String.class)));
                            }

                            if (lastChild.getKey().equals("sem_no")) {

                                model.setSemNo(lastChild.getValue(String.class));
                            }

                            if (lastChild.getKey().equals("sub_code")) {

                                model.setSubCode(lastChild.getValue(String.class));
                            }

                            if (lastChild.getKey().equals("sub_name")) {

                                model.setSubName(lastChild.getValue(String.class));
                            }
                        }

                        list.add(model);
                    }
                    uploadSubjects(list);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Error : ", error.getDetails());
            }
        });
    }

    private void uploadSubjects(Vector<SubjectsModel> list) {

        GetSemesterHelper getSemesterHelper = new GetSemesterHelper();

        getSemesterHelper.saveSubjectsIntoRealm(this, list);

        Intent intent = new Intent(SplashScreen.this, MainActivity.class);

        startActivity(intent);

        finish();
    }

    private void uploadDepartmentsData(Vector<DepartmentModel> list) {

        Vector<DepartmentModel> departmentModelVector = new Vector<>();

        GetSemesterHelper getSemesterHelper = new GetSemesterHelper();

        for (int index = 0; index < list.size(); index++) {

            DepartmentModel departmentModel = new DepartmentModel();

            departmentModel.setDeptId(list.get(index).getDeptId());

            departmentModel.setDeptName(list.get(index).getDeptName());

            departmentModel.setDegree(list.get(index).getDegree());

            departmentModelVector.add(departmentModel);
        }

        getSemesterHelper.saveDepartments(this, departmentModelVector);

        readSubjectsFromFirebase();
    }
}
