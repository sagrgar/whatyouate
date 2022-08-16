package com.example.android.whatyouate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {



    private Context context;

    private ArrayList meal_id, mealtype, date, time, meal, water, comment;



    CustomAdapter (Context context, ArrayList meal_id, ArrayList mealtype, ArrayList date, ArrayList time, ArrayList meal, ArrayList water, ArrayList comment) {
        this.context = context;
        this.meal_id = meal_id;
        this.mealtype = mealtype;
        this.meal = meal;
        this.date =date;
        this.time = time;
        this.water = water;
        this.comment = comment;



    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.meal_id.setText(String.valueOf(meal_id.get(position)));
        holder.mealtype.setText(String.valueOf(mealtype.get(position)));
        holder.meal.setText(String.valueOf(meal.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        holder.water.setText(String.valueOf(water.get(position)));
        holder.comment.setText(String.valueOf(comment.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateFood.class);
                intent.putExtra("id", String.valueOf(meal_id.get(position)));
                intent.putExtra("mealtype", String.valueOf(mealtype.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("time", String.valueOf(time.get(position)));
                intent.putExtra("meal", String.valueOf(meal.get(position)));
                intent.putExtra("water", String.valueOf(water.get(position)));
                intent.putExtra("comment", String.valueOf(comment.get(position)));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete item");
                builder.setMessage("Are you sure you want to delete the item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddFoodHelper MyDB = new AddFoodHelper(context);
                        MyDB.deleteItem(String.valueOf(meal_id.get(position)));
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return meal_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView meal_id, mealtype, meal, date, time, water, comment;
        ConstraintLayout mainLayout;
        private ImageView delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            meal_id = itemView.findViewById(R.id.mealID);
            mealtype = itemView.findViewById(R.id.mealTypeR);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            meal = itemView.findViewById(R.id.meal);
            water = itemView.findViewById(R.id.water);
            comment = itemView.findViewById(R.id.comment);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }


}
