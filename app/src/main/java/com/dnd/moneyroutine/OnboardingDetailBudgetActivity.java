package com.dnd.moneyroutine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dnd.moneyroutine.adapter.BudgetRecyclerViewAdapter;
import com.dnd.moneyroutine.item.BudgetItem;
import com.dnd.moneyroutine.item.CategoryItem;
import com.dnd.moneyroutine.service.RequestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingDetailBudgetActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tv_entire_budget_sub;
    private TextView tvGone;
    private String entireBudget;
    private RecyclerView rcBudget;
    private Button btnNext;
    private ImageView ivBack;

    private ArrayList<CategoryItem> cList;
    private ArrayList<BudgetItem> bgList;
    private ArrayList<CategoryItem> newItem;

    private BudgetRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_detail_budget);

        Intent intent = getIntent();
        entireBudget = intent.getStringExtra("Budget");
        cList = (ArrayList<CategoryItem>) getIntent().getSerializableExtra("BudgetItem");
        newItem = (ArrayList<CategoryItem>) intent.getSerializableExtra("New Item");




//        tvTotal=findViewById(R.id.tv_entire_budget);
//        tvAlert=findViewById(R.id.tv_budget_alert);

        tvTitle = (TextView) findViewById(R.id.tv_entire_budget);
        tvTitle.setText(entireBudget + "만원 안으로\n세부 예산 항목을 정해주세요");

        //예산 비교 위해 전체 예산 저장해두는 view (더 나은 방법 찾기)
        tvGone = findViewById(R.id.tv_gone);
        tvGone.setText(entireBudget);

        getRecyclerView();


        ivBack = findViewById(R.id.iv_back_detail);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext = findViewById(R.id.btn_start);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for(int i=0;i<newItem.size();i++){
                    customCategoryServer(newItem.get(i).getCategoryEx(), newItem.get(i).getCategoryName(),1);
                }
//                customCategoryServer("test", "test1",1);

            }
        });

    }

    //recyclerview adapter 연결
    private void getRecyclerView(){

        ArrayList<Integer> enteredBudget = new ArrayList<>();

        rcBudget =  findViewById(R.id.rc_budget);
//        tvTotal=findViewById(R.id.tv_budget_total);
        rcBudget.setLayoutManager(new LinearLayoutManager(this));

        bgList=new ArrayList<>();

        for(int i=0; i<cList.size();i++){
            bgList.add(new BudgetItem(i, cList.get(i).getCategoryIcon(), cList.get(i).getCategoryName()));
        }


        adapter=new BudgetRecyclerViewAdapter(bgList);

        rcBudget.setAdapter(adapter);

    }

//
//    }

    private void customCategoryServer(String detail, String name, int userId){
        Call<CustomCategoryModel> call = RequestService.getInstance().create(new CustomCategoryModel(detail, name, userId));
        call.enqueue(new Callback<CustomCategoryModel>() {
            @Override
            public void onResponse(Call<CustomCategoryModel> call, Response<CustomCategoryModel> response) {
                if(response.isSuccessful()){
                    CustomCategoryModel post = response.body();
                    Log.d("res","success" );
                }
            }

            @Override
            public void onFailure(Call<CustomCategoryModel> call, Throwable t) {
                Log.d("res","fail" );
            }
        });
    }


}