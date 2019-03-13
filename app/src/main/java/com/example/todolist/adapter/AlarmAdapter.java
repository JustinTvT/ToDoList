package com.example.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.bean.AlarmItem;
import com.example.todolist.utils.ToastUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    public static final int TYPE_NORMAL=121;
    public static final int TYPE_EMPTY=122;
    private Context context;
    private List<AlarmItem> dataList;
    class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView note;
        ImageView toggle;
        public AlarmViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if(viewType==TYPE_EMPTY) return;
            this.time=itemView.findViewById(R.id.alarm_time);
            this.note=itemView.findViewById(R.id.alarm_note);
            this.toggle=itemView.findViewById(R.id.alarm_toggle);
        }
    }
    public AlarmAdapter(Context context, List<AlarmItem> dataList){
        this.context=context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int size=dataList.size();
        View itemView;
        if(size==0){
            itemView= LayoutInflater.from(context).inflate(R.layout.alarm_empty_item,parent,false);
        }else{
            itemView=LayoutInflater.from(context).inflate(R.layout.alarm_item,parent,false);
        }
        return new AlarmViewHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmViewHolder holder, int position) {
        int itemViewType=getItemViewType(position);
        if(itemViewType==TYPE_EMPTY)
            return;
        holder.time.setText(dataList.get(position).getTime());
        holder.note.setText(dataList.get(position).getNote());
        if(dataList.get(position).isOpen()){
            holder.toggle.setImageResource(R.drawable.toggle_open);
        }else{
            holder.toggle.setImageResource(R.drawable.toggle_close);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("跳转到闹钟编辑界面");
            }
        });
        holder.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                boolean isOpen=dataList.get(pos).isOpen();
                dataList.get(pos).setOpen(!isOpen);
                if(isOpen){
                    holder.toggle.setImageResource(R.drawable.toggle_close);
                }else{
                    holder.toggle.setImageResource(R.drawable.toggle_open);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size()==0?1:dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.size()==0)
            return TYPE_EMPTY;
        return TYPE_NORMAL;
    }
}
