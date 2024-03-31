package com.example.flipicklms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flipicklms.MainActivity;
import com.example.flipicklms.Resources.Data;
import com.example.flipicklms.Resources.HomeClass;
import com.example.flipicklms.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemRowHolder> {

private ArrayList<HomeClass> dataList;
private Context mContext;

public RecyclerViewAdapter(Context context, ArrayList<HomeClass> dataList) {
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
public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

    // For LOGIN in main page
//    itemRowHolder.tvViewAll.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            ((MainActivity)mContext).openRegisterDialog();
//
//        }
//    });

        if (!dataList.get(i).getHeader().isEmpty())
        {
            final String sectionName = dataList.get(i).getHeader();
            itemRowHolder.itemTitle.setText(sectionName);
        }
        if (dataList.get(i).getContentArrayList().size()>1)
        {
            itemRowHolder.tvViewAll.setVisibility(View.VISIBLE);
        }
        else {
            itemRowHolder.tvViewAll.setVisibility(View.INVISIBLE);

        }
        ArrayList<Data> singleSectionItems = dataList.get(i).getContentArrayList();
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        }

@Override
public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
        }

public static class ItemRowHolder extends RecyclerView.ViewHolder {

    protected TextView itemTitle,tvViewAll;

    protected RecyclerView recycler_view_list;

    protected Button btnMore;

    public ItemRowHolder(View view) {

        super(view);

        this.itemTitle = (TextView) view.findViewById(R.id.tvHeader);
        this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recyclerChild);
            this.tvViewAll= (TextView) view.findViewById(R.id.tvViewAll);


    }

}

}