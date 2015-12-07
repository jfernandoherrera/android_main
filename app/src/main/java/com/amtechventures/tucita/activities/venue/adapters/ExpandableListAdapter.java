package com.amtechventures.tucita.activities.venue.adapters;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.venue.VenueFragment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.utils.views.ViewUtils;
import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter{


    private ArrayList<ArrayList<Service>> childItems;
    private LayoutInflater inflater;
    private ArrayList<SubCategory> parentItems;
    private final ExpandableListView expandableListView;
    private VenueFragment.OnServiceSelected listener;
    private ViewUtils viewUtils;


    public ExpandableListAdapter(List<SubCategory> parents, List<ArrayList<Service>> children, ViewUtils viewUtils, ExpandableListView expandableListView, VenueFragment.OnServiceSelected listener)
    {
        this.parentItems = (ArrayList<SubCategory>) parents;

        this.childItems = (ArrayList<ArrayList<Service>>) children;

        this.expandableListView = expandableListView;

        this.viewUtils = viewUtils;

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {

            this.expandableListView .setIndicatorBounds(expandableListView.getRight()-50,expandableListView.getRight()-10);

        } else {

            this.expandableListView .setIndicatorBoundsRelative(expandableListView.getRight()-50,expandableListView.getRight()-10);
        }

        this.listener = listener;
    }

    public void setInflater(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        Service service = childItems.get(groupPosition).get(childPosition);

        final TextView textName;

        final TextView textDuration;

        final TextView textPricesFrom;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_service, null);
        }

        String serviceName = service.getName();

        String servicePrice = "$" + String.valueOf(service.getPrice());

        textDuration = (TextView) convertView.findViewById(R.id.textDuration);

        textPricesFrom = (TextView) convertView.findViewById(R.id.textPricesFrom);

        textName = (TextView) convertView.findViewById(R.id.textName);

        textName.setText(serviceName);

        textDuration.setText(service.getDurationInfo());

        textPricesFrom.setText(servicePrice);

        convertView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

           v.setBackgroundResource(R.drawable.pressed_application_background_static);

        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

        v.setBackgroundColor(v.getResources().getColor(R.color.colorPrimaryLight));

        }
            return false;
        }
    });
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                listener.onServiceSelected(childItems.get(groupPosition).get(childPosition), view);
            }
        });

        textName.setHeight(viewUtils.childHeight);

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.parent_view, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition).getName());

        ((CheckedTextView) convertView).setChecked(isExpanded);

        ((CheckedTextView) convertView).setHeight(viewUtils.parentHeight);

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
        return (childItems.get(groupPosition)).size();
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

        viewUtils.setListViewHeightBasedOnChildren(expandableListView);
    }

    @Override
    public void onGroupExpanded(int groupPosition)
    {
        super.onGroupExpanded(groupPosition);

        viewUtils.setListViewHeightBasedOnChildren(expandableListView);
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
