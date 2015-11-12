package com.amtechventures.tucita.activities.venue.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter{


    private Activity activity;
    private ArrayList<ArrayList> childtems;
    private LayoutInflater inflater;
    private ArrayList<SubCategory> parentItems;
    private ArrayList<String> child;

    // constructor
    public ExpandableListAdapter(List<SubCategory> parents, List<ArrayList> childern)
    {
        this.parentItems = (ArrayList<SubCategory>) parents;
        this.childtems = (ArrayList<ArrayList>) childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity)
    {
        this.inflater = inflater;
        this.activity = activity;
    }

    // method getChildView is called automatically for each child view.
    //  Implement this method as per your requirement
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item, null);
        }

        // get the textView reference and set the value
        textView = (TextView) convertView.findViewById(R.id.textlist);

        textView.setText(child.get(childPosition));

        // set the ClickListener to handle the click event on child item
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(activity, child.get(childPosition),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    // method getGroupView is called automatically for each parent item
    // Implement this method as per your requirement
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.parent_view, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition).getName());

        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public int getGroupCount()
    {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition)
    {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }
}
