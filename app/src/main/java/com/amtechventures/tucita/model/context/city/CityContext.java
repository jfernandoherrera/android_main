package com.amtechventures.tucita.model.context.city;

public class CityContext {

    private CityRemote cityRemote;


    public static CityContext context(CityContext cityContext) {

        if (cityContext == null) {

            cityContext = new CityContext();

        }

        return cityContext;
    }

    private CityContext() {

        cityRemote = new CityRemote();

    }

    public void loadLikeCities(String like, CityCompletion.ErrorCompletion cityCompletion) {

        cityRemote.loadLikeCities(like, cityCompletion);

    }

}
