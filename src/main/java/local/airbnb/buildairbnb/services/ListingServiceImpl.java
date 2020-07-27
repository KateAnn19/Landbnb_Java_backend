package local.airbnb.buildairbnb.services;

import local.airbnb.buildairbnb.exceptions.ResourceNotFoundException;
import local.airbnb.buildairbnb.models.Listing;
import local.airbnb.buildairbnb.models.OptimalPrice;
import local.airbnb.buildairbnb.models.User;
import local.airbnb.buildairbnb.repository.ListingRepository;
import local.airbnb.buildairbnb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service(value = "listService")
public class ListingServiceImpl implements ListingService
{
    @Autowired
    ListingRepository listingRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Transactional
    @Override
    public void delete(User user, long id)
    {
        List<Listing> myList = new ArrayList<>();
        for (Listing l : user.getList())
        {
            if(l.getListingid() == id)
            {
                myList.add(l);
            }
        }

        if(myList.size() < 1)
        {
            throw new ResourceNotFoundException("This user does not have this listing " + id);
        }

        if (listingRepo.findById(id)
            .isPresent())
        {
            listingRepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Listing with id " + id + " Not Found!");
        }
    }


    @Override
    public Listing findListingById(long id)
    {
        return listingRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Listing with id " + id + " Not Found!"));
    }

    @Override
    public List<Listing> findAll()
    {
        List<Listing> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        listingRepo.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }


    @Override
    public Listing saveByAuth(User user, Listing list, String str)
    {
        Listing newListing = new Listing();
        User dbuser = userRepository.findById(user.getUserid())
            .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found"));
        newListing.setUser(dbuser);


        if (list.getListingid() != 0)
        {
            listingRepo.findById(list.getListingid())
                .orElseThrow(() -> new ResourceNotFoundException("Listing id " + list.getListingid() + " not found!"));
        }

        // String propertytype, String roomtype, int accomodates, int bathrooms, boolean cleanfee, String city,
        //        int latitude, int longitude, int reviewscoresrating, long zipcode, int bedrooms, int beds, boolean dryer,
        //        boolean parking, long descriptionlen, User user

        newListing.setPropertytype(list.getPropertytype());
        newListing.setRoomtype(list.getRoomtype());
        newListing.setAccomodates(list.getAccomodates());
        newListing.setBathrooms(list.getBathrooms());
        newListing.setCleanfee(list.isCleanfee());
        newListing.setCity(list.getCity());
        newListing.setLatitude(list.getLatitude());
        newListing.setLongitude(list.getLongitude());
        newListing.setReviewscoresrating(list.getReviewscoresrating());
        newListing.setZipcode(list.getZipcode());
        newListing.setBedrooms(list.getBedrooms());
        newListing.setBeds(list.getBeds());
        newListing.setDryer(list.isDryer());
        newListing.setParking(list.isParking());
        newListing.setdescriptionlen(list.getdescriptionlen());
        newListing.setPrice(str);
        newListing.setUser(user);

        return listingRepo.save(newListing);
    }

    @Transactional
    @Override
    public Listing save(long userid, Listing list){

        User currentUser = userService.findUserById(userid);
        Listing newListing = new Listing();

        if (list.getListingid() != 0)
        {
            listingRepo.findById(list.getListingid())
                .orElseThrow(() -> new ResourceNotFoundException("Listing id " + list.getListingid() + " not found!"));
        }

// String propertytype, String roomtype, int accomodates, int bathrooms, boolean cleanfee, String city,
//        int latitude, int longitude, int reviewscoresrating, long zipcode, int bedrooms, int beds, boolean dryer,
//        boolean parking, long descriptionlen, User user

        newListing.setPropertytype(list.getPropertytype());
        newListing.setRoomtype(list.getRoomtype());
        newListing.setAccomodates(list.getAccomodates());
        newListing.setBathrooms(list.getBathrooms());
        newListing.setCleanfee(list.isCleanfee());
        newListing.setCity(list.getCity());
        newListing.setLatitude(list.getLatitude());
        newListing.setLongitude(list.getLongitude());
        newListing.setReviewscoresrating(list.getReviewscoresrating());
        newListing.setZipcode(list.getZipcode());
        newListing.setBedrooms(list.getBedrooms());
        newListing.setBeds(list.getBeds());
        newListing.setDryer(list.isDryer());
        newListing.setParking(list.isParking());
        newListing.setdescriptionlen(list.getdescriptionlen());
        newListing.setUser(currentUser);

        return listingRepo.save(newListing);
    }

    @Transactional
    @Override
    public Listing savePrice(Listing list, String str)
    {
        Listing currentListing = list;
        currentListing.setPropertytype(list.getPropertytype());
        currentListing.setRoomtype(list.getRoomtype());
        currentListing.setAccomodates(list.getAccomodates());
        currentListing.setBathrooms(list.getBathrooms());
        currentListing.setCleanfee(list.isCleanfee());
        currentListing.setCity(list.getCity());
        currentListing.setLatitude(list.getLatitude());
        currentListing.setLongitude(list.getLongitude());
        currentListing.setReviewscoresrating(list.getReviewscoresrating());
        currentListing.setZipcode(list.getZipcode());
        currentListing.setBedrooms(list.getBedrooms());
        currentListing.setBeds(list.getBeds());
        currentListing.setDryer(list.isDryer());
        currentListing.setParking(list.isParking());
        currentListing.setdescriptionlen(list.getdescriptionlen());
        currentListing.setPrice(str);
        currentListing.setUser(list.getUser());

        return listingRepo.save(currentListing);
    }




    @Transactional
    @Override
    public Listing update(User user, Listing list, long id){

        User currentUser = user;

            Listing currentListing = listingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing id " + id + " not found!"));

            if (list.getPropertytype() != null)
            {
                currentListing.setPropertytype(list.getPropertytype());
            }

            if (list.getRoomtype() != null)
            {
                currentListing.setRoomtype(list.getRoomtype());
            }

            if (list.hasvalueforaccomodates)
            {
            currentListing.setAccomodates(list.getAccomodates());
            }

            if (list.hasvalueforbathrooms) //number
            {
                currentListing.setBathrooms(list.getBathrooms());
            }

            if(list.isCleanfee() != currentListing.isCleanfee())
            {
                currentListing.setCleanfee(!currentListing.isCleanfee());
            }

        if(list.getCity() != null)
        {
            currentListing.setCity(list.getCity());
        }


        if (list.hasvalueforlatitude) //number
        {
            currentListing.setLatitude(list.getLatitude());
        }
        if (list.hasvalueforlongitude) //number
        {
            currentListing.setLongitude(list.getLongitude());
        }
        if(list.hasvalueforreviewscore)
        {
            currentListing.setReviewscoresrating(list.getReviewscoresrating());
        }
        if(list.hasvalueforzipcode)
        {
            currentListing.setZipcode(list.getZipcode());
        }

        if(list.hasvalueforbedrooms)
        {
            currentListing.setBedrooms(list.getBedrooms());
        }

        if(list.hasvalueforbeds)
        {
            currentListing.setBeds(list.getBeds()); //number
        }

        if(list.isDryer() != currentListing.isDryer())
        {
            currentListing.setDryer(!currentListing.isDryer());
        }

        if(list.isParking() != currentListing.isParking())
        {
            currentListing.setParking(!currentListing.isDryer());
        }

        if(list.hasvaluefordescriptionlen)
        {
            currentListing.setdescriptionlen(list.getdescriptionlen()); //number
        }

        //ds endpoint

        RestTemplate restTemplate = new RestTemplate();

        System.out.println("THIS IS THE LIST " + currentListing);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);


        System.out.println("Reached this spot");

        String reqUrl = "https://testapifortesting.herokuapp.com/predict";

        System.out.println("made the request");
        ParameterizedTypeReference<OptimalPrice> responseType = new ParameterizedTypeReference<>() {
        };

        HttpEntity<Listing> requestEntity = new HttpEntity<>(currentListing);

        ResponseEntity<OptimalPrice> responseEntity = restTemplate.exchange(reqUrl,
            HttpMethod.POST, requestEntity, responseType);


        OptimalPrice price = responseEntity.getBody();

        //optimalpriceService.save(list.getListingid(), price);

        String stringPrice = new String(responseEntity.getBody().getPrices());
        //Str = Str.replaceAll("[A-Z]+[:]+[a-z]+",);
        stringPrice = stringPrice.replaceAll("[a-zA-Z]", "");
        stringPrice = stringPrice.replaceAll("[}{:\"]", "");
        stringPrice = stringPrice.replaceAll("[\\s+]", "");
        System.out.println(stringPrice);
        //System.out.println(Str.substring(10,17));

        System.out.println("This is response entity " + responseEntity.getBody().getPrices());

        System.out.println(price);


        //ds endpoint

        //override price
        currentListing.setPrice(stringPrice);

        currentListing.setUser(currentUser);

        return listingRepo.save(currentListing);
    }


    @Override
    public void deleteAll()
    {
        listingRepo.deleteAll();
    }
}
