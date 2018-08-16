package com.spanenterprises.spanenquirer.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.spanenterprises.spanenquirer.Model.SubjectsModel;
import com.spanenterprises.spanenquirer.R;
import com.spanenterprises.spanenquirer.Repository.GetSemesterHelper;
import com.spanenterprises.spanenquirer.Utility.UtilsClass;

import java.util.ArrayList;
import java.util.Vector;

public class DisplaySemesterDetailsActivity extends AppCompatActivity {


    private TextView degreeLbl;
    private TextView deptNameLbl;
    private TextView semNoLbl;
    private ImageView backImg;
    private LinearLayout detailListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_semester_details);

        initialize();

        setAdapter();

        setOnClickListener();
    }

    private void setOnClickListener() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void setAdapter() {

        if (getIntent() != null && UtilsClass.isValidString(getIntent().getStringExtra("Degree")) && UtilsClass.isValidString(getIntent().getStringExtra("DepartmentName"))) {

            String degree = getIntent().getStringExtra("Degree");

            degreeLbl.setText(degree);

            String deptName = getIntent().getStringExtra("DepartmentName");

            deptNameLbl.setText(deptName);

            int departmentId = getIntent().getIntExtra("DepartmentId", 0);

            String selectedSemester = getIntent().getStringExtra("SemesterNo");

            semNoLbl.setText(String.valueOf(selectedSemester));

            GetSemesterHelper getSemesterHelper = new GetSemesterHelper();

            Vector<SubjectsModel> subjectsList = getSemesterHelper.getSemesterDetails(DisplaySemesterDetailsActivity.this, departmentId, selectedSemester);

            detailListLayout.removeAllViews();

            if (subjectsList != null && subjectsList.size() > 0) {

                for (int i = 0; i < subjectsList.size(); i++) {

                    LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final View childView = layoutInflater.inflate(R.layout.subject_detail_list_item, null);

                    TextView subCodeLbl = childView.findViewById(R.id.subCodeLbl);

                    TextView subNameLbl = childView.findViewById(R.id.subNameLbl);

                    subCodeLbl.setText(subjectsList.get(i).getSubCode());

                    subNameLbl.setText(subjectsList.get(i).getSubName());

                    detailListLayout.addView(childView);
                }
            }
        }
    }

    private void initialize() {

        backImg = findViewById(R.id.backImgView);

        degreeLbl = findViewById(R.id.degreeLbl);

        deptNameLbl = findViewById(R.id.deptNameLbl);

        semNoLbl = findViewById(R.id.semNoLbl);

        detailListLayout = findViewById(R.id.detailListLayout);

    }
}
