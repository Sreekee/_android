package com.spanenterprises.spanenquirer.Controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spanenterprises.spanenquirer.R;
import com.spanenterprises.spanenquirer.Repository.GetSemesterHelper;
import com.spanenterprises.spanenquirer.Utility.UtilsClass;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Spinner departmentSpinner;
    private Spinner semesterSpinner;
    private RadioButton beRadioBtn;
    private RadioButton btechRadioBtn;
    private RadioGroup degreeRadioGrp;
    private Button searchButton;
    private TextView resetLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initialize();

        setAdapter();

        setOnClickListener();
    }

    private void setAdapter() {

        setDepartmentSpinnerData();

        //setting semester spinner data
        ArrayList<String> semesterList = new ArrayList<String>();

        semesterList.add("Select");

        semesterList.add("I");

        semesterList.add("II");

        semesterList.add("III");

        semesterList.add("IV");

        semesterList.add("V");

        semesterList.add("VI");

        semesterList.add("VII");

        semesterList.add("VIII");

        ArrayAdapter<String> semesterDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesterList);

        semesterDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        semesterSpinner.setAdapter(semesterDataAdapter);
    }

    private void setDepartmentSpinnerData() {

        String degree = "";

        if (beRadioBtn.isChecked())

            degree = beRadioBtn.getText().toString();

        else if (btechRadioBtn.isChecked())

            degree = btechRadioBtn.getText().toString();

        Vector<String> departments = new GetSemesterHelper().getAllDepartment(MainActivity.this, degree);

        if (departments != null && departments.size() > 0) {

            ArrayList<String> departmentModelList = new ArrayList<>(departments);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, departmentModelList);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            departmentSpinner.setAdapter(dataAdapter);
        }
    }

    private void setOnClickListener() {

        degreeRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                setDepartmentSpinnerData();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String degree = "";

                int flag = 0;

                if (beRadioBtn.isChecked())

                    degree = beRadioBtn.getText().toString();

                else if (btechRadioBtn.isChecked())

                    degree = btechRadioBtn.getText().toString();

                if (departmentSpinner.getSelectedItemPosition() == 0) {

                    flag = 1;

                    Toast.makeText(MainActivity.this, "Select department!", Toast.LENGTH_SHORT).show();
                }

                if (semesterSpinner.getSelectedItemPosition() == 0) {

                    flag = 1;

                    Toast.makeText(MainActivity.this, "Select semester!", Toast.LENGTH_SHORT).show();
                }

                if (flag == 0) {

                    String selectedDept = departmentSpinner.getSelectedItem().toString();

                    int departmentId = new GetSemesterHelper().getDepartmentId(MainActivity.this, selectedDept);

                    String selectedSemester = semesterSpinner.getSelectedItem().toString();

                    if (UtilsClass.isValidString(selectedDept) && UtilsClass.isValidString(selectedSemester) && UtilsClass.isValidString(degree) && departmentId>0) {

                        Intent intent = new Intent(MainActivity.this, DisplaySemesterDetailsActivity.class);

                        intent.putExtra("Degree", degree);

                        intent.putExtra("DepartmentName", selectedDept);

                        intent.putExtra("DepartmentId", departmentId);

                        intent.putExtra("SemesterNo", selectedSemester);

                        startActivity(intent);
                    }
                }
            }
        });

        resetLbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                beRadioBtn.setChecked(true);

                departmentSpinner.setSelection(0);

                semesterSpinner.setSelection(0);
            }
        });
    }

    private void initialize() {

        degreeRadioGrp = findViewById(R.id.degreeRadioGrp);

        departmentSpinner = findViewById(R.id.departmentSpinner);

        semesterSpinner = findViewById(R.id.semesterSpinner);

        searchButton = findViewById(R.id.searchBtn);

        beRadioBtn = findViewById(R.id.beRadioBtn);

        btechRadioBtn = findViewById(R.id.btechRadioBtn);

        resetLbl = findViewById(R.id.reset_lbl);
    }
}
