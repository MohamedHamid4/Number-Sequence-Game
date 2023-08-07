package com.example.numbersequencegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbersequencegame.databinding.CustomGameItemBinding;
import com.example.numbersequencegame.databinding.CustomGameItemBinding;

import java.util.ArrayList;

public class recycle_addapter extends RecyclerView.Adapter<recycle_addapter.RecycleHolder> {
        private ArrayList<GameVeribel> gameVeribel ;
        private Context context ;

public recycle_addapter(ArrayList<GameVeribel> gameHistories, Context context) {
        this.gameVeribel = gameHistories;
        this.context = context;
        }

    public ArrayList<GameVeribel> getGameHistories() {
        return gameVeribel;
    }

    public void setGameHistories(ArrayList<GameVeribel> gameVeribel) {
        this.gameVeribel = gameVeribel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
@Override
public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    CustomGameItemBinding binding = CustomGameItemBinding.inflate(LayoutInflater.from(context));
        return new RecycleHolder(binding.getRoot());
        }

@Override
public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
    GameVeribel m = gameVeribel.get(position);
        holder.binding.tvMark.setText((m.getScore()+""));
        holder.binding.tvName.setText(m.getName());
        holder.binding.tvTime.setText(String.valueOf(m.getDate()));
        }

@Override
public int getItemCount() {
        return gameVeribel.size();
        }

class RecycleHolder extends RecyclerView.ViewHolder{
    CustomGameItemBinding binding ;
    public RecycleHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomGameItemBinding.bind(itemView);
    }
}
}

