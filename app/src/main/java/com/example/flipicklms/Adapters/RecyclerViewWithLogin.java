package com.example.flipicklms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.flipicklms.R;
import com.example.flipicklms.Resources.Data;
import com.example.flipicklms.Resources.HomeModelWithLogin;
import com.example.flipicklms.ViewAllActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWithLogin extends RecyclerView.Adapter<RecyclerViewWithLogin.ItemRowHolder> {

    private List<HomeModelWithLogin> dataList;
    private Context mContext;

    public RecyclerViewWithLogin(Context context, List<HomeModelWithLogin> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_home_child, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, @SuppressLint("RecyclerView") int i) {

        if (!dataList.get(i).getHeader().isEmpty())
        {
            final String sectionName = dataList.get(i).getHeader();
            itemRowHolder.itemTitle.setText(sectionName);
        }

        Log.d("pp","size"+dataList.get(i).getContentArrayList().size());

        if (dataList.get(i).getContentArrayList().size()>1)
        {
            itemRowHolder.viewAll.setVisibility(View.VISIBLE);
        }
        else {
            itemRowHolder.viewAll.setVisibility(View.INVISIBLE);

        }
        itemRowHolder.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewAllActivity.class);
                intent.putExtra("type",dataList.get(i).getHeader());
                Log.d("zzz","Adapter Header"+dataList.get(i).getHeader());
                mContext.startActivity(intent);
            }
        });

        ArrayList<Data> singleSectionItems = dataList.get(i).getContentArrayList();
        SelectionListDataAdapterAfterLogin itemListDataAdapter = new SelectionListDataAdapterAfterLogin(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected TextView viewAll;
        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.tvHeader);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recyclerChild);
            this.viewAll= (TextView) view.findViewById(R.id.tvViewAll);


        }

    }

}
