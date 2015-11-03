package com.eastcom.testaidl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rockgarden on 15/11/3.
 */
class Adapter_Cell extends RecyclerView.Adapter {

    class ViewHolder extends RecyclerView.ViewHolder {
        private View root;
        private TextView textViewTitle,textViewContent;

        public ViewHolder(View root) {
            super(root);
            textViewTitle= (TextView) root.findViewById(R.id.textViewTitle);
            textViewContent= (TextView) root.findViewById(R.id.textViewContent);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewContent() {
            return textViewContent;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //return new ViewHolder(new TextView(viewGroup.getContext()));
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, null));//null为根对象
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.getTextView().setText(data[position]);
        RecyclerCellData rcd=data[position];
        viewHolder.getTextViewTitle().setText(rcd.title);
        viewHolder.getTextViewContent().setText(rcd.content);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

//    private String[] data = new String[]{"77777", "889898", "908433"};

    private RecyclerCellData[] data=new RecyclerCellData[]{
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000"),
            new RecyclerCellData("big car","100000000")};
}
