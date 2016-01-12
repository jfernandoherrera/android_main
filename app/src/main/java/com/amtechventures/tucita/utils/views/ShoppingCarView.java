package com.amtechventures.tucita.utils.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amtechventures.tucita.R;
import com.amtechventures.tucita.activities.book.adapters.ServicesToBookAdapter;
import com.amtechventures.tucita.model.domain.service.Service;

import java.util.ArrayList;

public class ShoppingCarView extends FrameLayout implements ServicesToBookAdapter.OnItemClosed {

    private ImageView car;
    private Button bookNow;
    private CircleTextView circleTextView;
    private final double positionRatio = 0.095;
    private ArrayList<Service> servicesToBook;
    private RecyclerView recyclerView;
    private ServicesToBookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnItemClosed listenerItemRemove;
    private OnMoreServices listener;
    private OnBookNow listenerBookNow;
    private RelativeLayout shoppingCar;
    private RelativeLayout shoppingCarList;

    public interface OnBookNow {

        void onBookNow();

    }

    public int[] getDuration() {

        int hours = 0;

        int minutes = 0;

        for (Service service : servicesToBook) {

            hours += service.getDurationHour();

            minutes += service.getDurationMinutes();

        }

        if (minutes > 59) {
        }

        return new int[]{hours, minutes};

    }

    public interface OnMoreServices {

        void onMoreServices();

    }

    public interface OnItemClosed {

        void onItemClosed(Service service);

    }

    public ShoppingCarView(Context context, AttributeSet attrs) {

        super(context, attrs);

        listenerItemRemove = (OnItemClosed) context;

        listenerBookNow = (OnBookNow) context;

        listener = (OnMoreServices) context;

        init(context);

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.shopping_car, this);

        car = (ImageView) findViewById(R.id.car);

        bookNow = (Button) findViewById(R.id.bookNow);

        shoppingCar = (RelativeLayout) findViewById(R.id.containerShoppingView);

        shoppingCarList = (RelativeLayout) findViewById(R.id.listContainer);

        servicesToBook = new ArrayList<>();

        adapter = new ServicesToBookAdapter(servicesToBook, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.moreServices);

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    button.setBackgroundResource(R.drawable.pressed_application_background_static);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    button.setBackgroundColor(Color.TRANSPARENT);

                }

                return false;

            }

        });

        button.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {

                                          if (listIsVisible()) {

                                              hideList();

                                          }

                                          listener.onMoreServices();

                                      }

                                  }

        );

        circleTextView = (CircleTextView) findViewById(R.id.count);

        car.setImageResource(R.mipmap.ic_launcher);

        circleTextView.setText(" " + String.valueOf(servicesToBook.size()));

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        int translation = (int) (size.x * positionRatio) / 2;

        circleTextView.setTranslationX(translation);

        circleTextView.bringToFront();

        car.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (listIsVisible()) {

                    hideList();

                } else {

                    showList();

                }

            }

        });

        bookNow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                listenerBookNow.onBookNow();

            }

        });

    }

    public boolean listIsVisible() {

        boolean isVisible = shoppingCarList.getVisibility() == VISIBLE;

        return isVisible;

    }

    public void showView() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            shoppingCar.animate().translationY(getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    shoppingCar.setVisibility(VISIBLE);

                    shoppingCar.animate().translationY(0);

                }

            });

        } else {

            shoppingCar.setVisibility(VISIBLE);

        }

    }

    public void showList() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            shoppingCarList.animate().translationY(shoppingCarList.getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    shoppingCarList.animate().translationY(0);

                    shoppingCarList.setVisibility(View.VISIBLE);

                }

            });

        } else {

            shoppingCarList.setVisibility(View.VISIBLE);

        }

        setBackgroundColor(getResources().getColor(R.color.dialogColor));

    }

    public void hideView() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            shoppingCar.animate().translationY(getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    shoppingCar.setVisibility(View.INVISIBLE);

                }

            });

        } else {

            shoppingCar.setVisibility(View.INVISIBLE);

        }

    }

    public void hideList() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            shoppingCarList.animate().translationY(getHeight()).withEndAction(new Runnable() {

                @Override
                public void run() {

                    shoppingCarList.setVisibility(View.GONE);

                }

            });

        } else {

            shoppingCarList.setVisibility(View.GONE);

        }

        setBackgroundColor(Color.TRANSPARENT);

    }

    public ArrayList<Service> getServicesToBook() {

        return servicesToBook;

    }

    public int getDurationInMinutes() {

        int duration = 0;

        for (Service service : servicesToBook) {

            duration = duration + (service.getDurationHour() * 60) + service.getDurationMinutes();

        }

        return duration;

    }

    public void setCount() {

        circleTextView.setText(" " + String.valueOf(servicesToBook.size()));

        if (servicesToBook.size() == 1 && shoppingCar.getVisibility() != VISIBLE) {

            showView();

        } else if (servicesToBook.isEmpty()) {

            hideList();

            hideView();

        }

    }

    public boolean isEmpty() {

        boolean isEmpty = servicesToBook.isEmpty();

        return isEmpty;

    }

    public boolean alreadyExistsService(Service service) {

        boolean exists = false;

        if (servicesToBook != null) {

            for (Service service1 : servicesToBook) {

                if (service1.getName().equals(service.getName())) {

                    exists = true;

                }

            }

        }

        return exists;

    }

    public void addService(Service service) {

        servicesToBook.add(service);

        if (adapter != null) {

            adapter.notifyDataSetChanged();

        }

        setCount();

    }

    public void removeService(Service service) {

        servicesToBook.remove(service);

        if (adapter != null) {

            adapter.notifyDataSetChanged();

        }

        setCount();

    }

    @Override
    public void onItemClosed(Service service) {

        removeService(service);

        adapter.notifyDataSetChanged();

        listenerItemRemove.onItemClosed(service);

    }

}
