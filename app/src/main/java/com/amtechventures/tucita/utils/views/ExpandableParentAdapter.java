package com.amtechventures.tucita.utils.views;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;

import java.util.List;

public class ExpandableParentAdapter extends BaseExpandableListAdapter {

    int view;
    List<Service> services;
    private LayoutInflater inflater;
    String description;
    ViewUtils viewUtils;
    final int parent = R.layout.parent_view;

    public ExpandableParentAdapter(int view, List<Service> services, String description){

        this.description = description;

        this.view = view;

        this.services = services;

        viewUtils = new ViewUtils(null);
    }

    public void setInflater(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }

    @Override
    public int getGroupCount() {

        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        int count = services == null ? 1 : services.size();

        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(this.parent, null);

        }

        if(description != null){

            ((CheckedTextView) convertView).setText(convertView.getResources().getString(R.string.description));

            ((CheckedTextView) convertView).setChecked(isExpanded);

            ((CheckedTextView) convertView).setHeight(viewUtils.parentHeight);

        }else{


        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(view, null);
        }

        if(services != null){

        }else{

            final TextView text = (TextView) convertView.findViewById(R.id.textList);

            text.setText(description);

            text.setTextColor(convertView.getResources().getColor(R.color.blackSecondary));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
