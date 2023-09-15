package com.cp.jo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cp.jo.R;
import com.cp.jo.databinding.AdapterProtectionBinding;
import com.cp.jo.listener.OnClickItemProtection;
import com.cp.jo.model.ModelProtection;
import com.cp.jo.tool.Animation;
import com.cp.jo.tool.Tools;

import java.util.List;

public class AdapterProtection extends RecyclerView.Adapter<AdapterProtection.ViewHolder> {

    @SuppressLint("StaticFieldLeak")
    public Context context;
    private List<ModelProtection> dataProtection;
    private final OnClickItemProtection onClickItemProtection;

    public AdapterProtection(Context context, List<ModelProtection> dataProtection, OnClickItemProtection onClickItemProtection) {
        this.context = context;
        this.dataProtection = dataProtection;
        this.onClickItemProtection = onClickItemProtection;
    }

    public void UpdateData(List<ModelProtection> newList) {
        DiffUtilProtection diffUtilProtection = new DiffUtilProtection(dataProtection, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilProtection);
        dataProtection.clear();
        dataProtection.addAll(newList);
        diffResult.dispatchUpdatesTo(this);

    }

    public List<ModelProtection> getDataProtection() {
        return dataProtection;
    }

    public void setDataProtection(List<ModelProtection> dataProtection) {
        this.dataProtection = dataProtection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_protection, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.ProtectionTvName.setText(dataProtection.get(position).getName());
        if (dataProtection.get(position).isPremium()) {
            holder.binding.ProtectionImgPremium.setImageResource(R.drawable.ic_premium);
        } else
            holder.binding.ProtectionImgPremium.setImageResource(R.drawable.ic_free);

        switch (dataProtection.get(position).getModeView()) {
            case 0:
                holder.binding.ProtectionImgMode.setImageResource(R.drawable.ic_function);
                break;
            case 1:
                holder.binding.ProtectionImgMode.setImageResource(R.drawable.ic_shell);
                break;
            case 2:
                holder.binding.ProtectionImgMode.setImageResource(R.drawable.ic_risk);
                break;
            case 3:
                holder.binding.ProtectionImgMode.setImageResource(R.drawable.ic_safe);
                break;
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            if (dataProtection.get(position).isPremium()) {
                if (Tools.checkPremium()) {
                    Animation.AnimateBounce(holder.binding.getRoot(), context);
                    onClickItemProtection.onClickItemProtection(dataProtection, position);
                }
            } else {
                Animation.AnimateBounce(holder.binding.getRoot(), context);
                onClickItemProtection.onClickItemProtection(dataProtection, position);
            }
        });

        holder.binding.ProtectionTvDateCreate.setText(Tools.millisToDate(dataProtection.get(position).getDateCreate()));
    }

    @Override
    public int getItemCount() {
        return dataProtection.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final AdapterProtectionBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterProtectionBinding.bind(itemView);
        }
    }
}


