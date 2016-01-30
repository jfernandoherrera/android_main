package com.amtechventures.tucita.model.context.service;

import android.util.Log;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.domain.service.Service;
import com.amtechventures.tucita.model.domain.service.ServiceAttributes;
import com.amtechventures.tucita.model.domain.subcategory.SubCategory;
import com.amtechventures.tucita.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServiceRemote {

    private ParseQuery<Service> query;

    private void setQuery() {

        query = Service.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public int getPricesFrom(SubCategory subCategory, ParseQuery<Service> servicesLocalQuery) {

        query = servicesLocalQuery;

        query.include(ServiceAttributes.subCategory);

        query.whereEqualTo(ServiceAttributes.subCategory, subCategory);

        List<Service> services = null;

        try {

            services = query.find();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        int priceFrom = 0;

        if (services != null) {

            priceFrom = services.get(0).getPrice();

            for (Service service : services) {

                int currentServicePrice = service.getPrice();

                if (currentServicePrice < priceFrom) {

                    priceFrom = currentServicePrice;

                }

            }

        }

        return priceFrom;

    }

    public void loadServices(ParseQuery<Service> servicesRemoteQuery, final ServiceCompletion.ErrorCompletion completion) {

        query = servicesRemoteQuery;

        query.include(ServiceAttributes.subCategory);

        query.orderByAscending(ServiceAttributes.name);

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, com.parse.ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadSubCategorizedServices(SubCategory subCategory, final ServiceCompletion.ErrorCompletion completion) {

        setQuery();

        query.whereEqualTo(ServiceAttributes.subCategory, subCategory);

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadCategorizedServices(List<SubCategory> subCategories, final ServiceCompletion.ErrorCompletion completion) {

        setQuery();

        query.whereContainedIn(ServiceAttributes.subCategory, subCategories);

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadAppointmentServices(ParseQuery<Service> appointment, final ServiceCompletion.ErrorCompletion completion) {

        query = appointment;

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, ParseException e) {

                if(e!=null){

                  e.printStackTrace();

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

}
