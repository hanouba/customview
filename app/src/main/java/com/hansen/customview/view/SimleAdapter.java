package com.hansen.customview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hansen.customview.R;

import java.util.List;

/**
 * @author HanN on 2020/8/26 14:02
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/8/26 14:02
 * @updateremark:
 * @version: 2.1.67
 */
public class SimleAdapter extends BaseAdapter {
    private List<String> datas;
    private Context context;
    public SimleAdapter(Context context, List<String> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video_tree, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_cata_total);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(datas.get(position));

        return convertView;
    }

    public final class ViewHolder {
        TextView textView;
    }
}
