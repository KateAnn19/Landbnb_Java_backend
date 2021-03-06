package local.airbnb.buildairbnb.services;

import local.airbnb.buildairbnb.models.Listing;
import local.airbnb.buildairbnb.models.User;

import java.util.List;

public interface ListingService
{
    void delete(long id);

    Listing save(long userid, Listing list);


    Listing update(User user, Listing list, long id);

    public void deleteAll();

    List<Listing> findAll();

    Listing findListingById(long id);

    Listing saveByAuth(User user, Listing list, String str);

    Listing savePrice(Listing list, String str);

    List<Listing> findByUser_UsernameIgnoringCase(String name);
}
