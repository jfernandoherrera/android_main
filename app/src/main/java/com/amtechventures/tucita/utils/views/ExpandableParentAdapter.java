package com.amtechventures.tucita.utils.views;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.service.Service;

import java.util.List;

public class ExpandableParentAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    String description;
    ViewUtils viewUtils;
    int childHeight;
    private final ExpandableListView listViewParent;

    public ExpandableParentAdapter( String description, ExpandableListView listViewParent, ViewUtils viewUtils){

        this.description = description;

        this.listViewParent = listViewParent;

        this.viewUtils = viewUtils;

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


        return 1;
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

            convertView = inflater.inflate(R.layout.parent_view, null);

        }
        CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(R.id.textViewGroupName);

        checkedTextView.setText(convertView.getResources().getString(R.string.description));

        checkedTextView.setHeight(viewUtils.parentHeight);

        checkedTextView.setPadding(24,20,0,0);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item, null);

            final TextView text = (TextView) convertView.findViewById(R.id.textList);

            text.setText(description);

            text.setTextColor(convertView.getResources().getColor(R.color.blackSecondary));

            text.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                boolean isFirst = true;

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    childHeight = bottom - top ;

                    if(isFirst) {

                        viewUtils.setListViewHeightBasedOnChildrenCountLines(listViewParent, childHeight);

                        isFirst = false;
                    }
                }
            });
        }

        return convertView;
    }

    @Override
    public void onGroupCollapsed(int groupPosition)
    {
        super.onGroupCollapsed(groupPosition);

        viewUtils.setListViewHeightBasedOnChildren(listViewParent);
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {
        super.onGroupExpanded(groupPosition);

        if(childHeight != 0) {

            viewUtils.setListViewHeightBasedOnChildrenCountLines(listViewParent, childHeight);

        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
