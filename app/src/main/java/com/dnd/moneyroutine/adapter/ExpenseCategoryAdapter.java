package com.dnd.moneyroutine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dnd.moneyroutine.R;
import com.dnd.moneyroutine.dto.GoalCategoryDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenseCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GoalCategoryDetail> categoryList;
    private boolean current;

    public ExpenseCategoryAdapter(ArrayList<GoalCategoryDetail> categoryList, boolean current) {
        this.categoryList = categoryList;
        this.current = current;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            GoalCategoryDetail category = categoryList.get(position);
            ((CategoryViewHolder) holder).setItem(category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvEmoji;

        TextView tvCategory;
        TextView tvExpense;
        TextView tvBudget;
        TextView tvRemain;

        ProgressBar pbExpense;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategory = itemView.findViewById(R.id.iv_basic_category);
            tvEmoji = itemView.findViewById(R.id.tv_custom_category);

            tvCategory = itemView.findViewById(R.id.tv_category);
            tvExpense = itemView.findViewById(R.id.tv_cat_expense);
            tvBudget = itemView.findViewById(R.id.tv_category_budget);
            tvRemain = itemView.findViewById(R.id.tv_cat_remain);

            pbExpense = itemView.findViewById(R.id.pb_expense);
        }

        // 실제 view에 객체 내용을 적용시키는 메소드
        public void setItem(GoalCategoryDetail category) {
            if (category.isCustom()) {
                ivCategory.setVisibility(View.INVISIBLE);
                tvEmoji.setVisibility(View.VISIBLE);

                tvEmoji.setText(category.getEmoji());
            } else {
                ivCategory.setVisibility(View.VISIBLE);
                tvEmoji.setVisibility(View.INVISIBLE);
            }

            tvCategory.setText(category.getName());

            DecimalFormat myFormatter = new DecimalFormat("###,###");

            String expense = myFormatter.format(category.getTotalExpense());
            String budget = myFormatter.format(category.getBudget());
            String remain = myFormatter.format(category.getBudget() - category.getTotalExpense());

            if (current) {
                tvExpense.setText(expense + "원");
                tvRemain.setText(remain + "원");
            } else {
                tvExpense.setText(expense + "원 씀");
                tvRemain.setText(remain + "원 아낌");
            }

            tvBudget.setText("예산 " + budget + "원");

            pbExpense.setMax(category.getBudget());
            pbExpense.setProgress(category.getTotalExpense());
        }
    }
}