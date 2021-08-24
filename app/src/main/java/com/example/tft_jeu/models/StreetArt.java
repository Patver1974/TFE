package com.example.tft_jeu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class
StreetArt implements Comparator<StreetArt>, Parcelable {


    private  long id;
    private Object nameOfTheWork;
    private float distance;
    private Photo photo;
    private String adres;
    private String naamVanDeKunstenaar;
    private String nameOfTheArtist;
    private Object explanation;
    private String adresse;
    private Object precision;
    private Object verduidelijking;
    private String location;
    private String categorie;
    private String lieu;
    private String nomDeLArtiste;
    private Object annee;
    private Geocoordinates geocoordinates;
    private String plaats;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public StreetArt(long id, Object nameOfTheWork, String nameOfTheArtist, String adresse, Geocoordinates geocoordinates,String categorie, Photo photo) {
        this.id = id;
        this.nameOfTheWork = nameOfTheWork;
        this.nameOfTheArtist = nameOfTheArtist;
        this.adresse = adresse;
        this.geocoordinates = geocoordinates;
        this.categorie = categorie;
        this.photo = photo;



    }

    public StreetArt(long id, Object nameOfTheWork, String adresse, String categorie, Geocoordinates geocoordinates) {
        this.id = id;
        this.nameOfTheWork = nameOfTheWork;
        this.adresse = adresse;
        this.categorie = categorie;
        this.geocoordinates = geocoordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StreetArt(Object nameOfTheWork, String nameOfTheArtist, String adresse, Geocoordinates geocoordinates, String categorie, Photo photo) {
        this.id = 0;
        this.nameOfTheWork = nameOfTheWork;
        this.nameOfTheArtist = nameOfTheArtist;
        this.adresse = adresse;
        this.geocoordinates = geocoordinates;
        this.categorie = categorie;
        this.photo= photo;

    }
    public StreetArt( Object nameOfTheWork, String nameOfTheArtist, String adresse, Geocoordinates geocoordinates,String categorie, Float distance,Photo photo ) {
        this.id = 0;
        this.nameOfTheWork = nameOfTheWork;
        this.nameOfTheArtist = nameOfTheArtist;
        this.adresse = adresse;
        this.geocoordinates = geocoordinates;
        this.categorie = categorie;
        this.distance = distance;
        this.photo= photo;

    }

    protected StreetArt(Parcel in) {
        this.nameOfTheArtist = in.readString();
        this.nameOfTheWork = in.readString();
        this.adresse = in.readString();
        this.distance = in.readFloat();
        this.geocoordinates.setLat(in.readDouble());
        this.geocoordinates.setLon(in.readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.createByteArray() POUR LES IMAGES
        dest.writeString(nameOfTheArtist);
        dest.writeString(nameOfTheWork.toString());
        dest.writeString(adresse);
        dest.writeFloat(distance);
        dest.writeDouble(geocoordinates.getLat());
        dest.writeDouble(geocoordinates.getLon());
    }

    public static final Creator<StreetArt> CREATOR = new Creator<StreetArt>() {
        @Override
        public StreetArt createFromParcel(Parcel in) {
            return new StreetArt(in);
        }

        @Override
        public StreetArt[] newArray(int size) {
            return new StreetArt[size];
        }
    };

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Object getNameOfTheWork() {
        return nameOfTheWork;
    }

    public void setNameOfTheWork(Object nameOfTheWork) {
        this.nameOfTheWork = nameOfTheWork;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNaamVanDeKunstenaar() {
        return naamVanDeKunstenaar;
    }

    public void setNaamVanDeKunstenaar(String naamVanDeKunstenaar) {
        this.naamVanDeKunstenaar = naamVanDeKunstenaar;
    }

    public String getNameOfTheArtist() {
        return nameOfTheArtist;
    }

    public void setNameOfTheArtist(String nameOfTheArtist) {
        this.nameOfTheArtist = nameOfTheArtist;
    }

    public Object getExplanation() {
        return explanation;
    }

    public void setExplanation(Object explanation) {
        this.explanation = explanation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Object getPrecision() {
        return precision;
    }

    public void setPrecision(Object precision) {
        this.precision = precision;
    }

    public Object getVerduidelijking() {
        return verduidelijking;
    }

    public void setVerduidelijking(Object verduidelijking) {
        this.verduidelijking = verduidelijking;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNomDeLArtiste() {
        return nomDeLArtiste;
    }

    public void setNomDeLArtiste(String nomDeLArtiste) {
        this.nomDeLArtiste = nomDeLArtiste;
    }

    public Object getAnnee() {
        return annee;
    }

    public void setAnnee(Object annee) {
        this.annee = annee;
    }

    public Geocoordinates getGeocoordinates() {
        return geocoordinates;
    }

    public void setGeocoordinates(Geocoordinates geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StreetArt.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("nameOfTheWork");
        sb.append('=');
        sb.append(((this.nameOfTheWork == null)?"<null>":this.nameOfTheWork));
        sb.append(',');
        sb.append("photo");
        sb.append('=');
        sb.append(((this.photo == null)?"<null>":this.photo));
        sb.append(',');
        sb.append("adres");
        sb.append('=');
        sb.append(((this.adres == null)?"<null>":this.adres));
        sb.append(',');
        sb.append("naamVanDeKunstenaar");
        sb.append('=');
        sb.append(((this.naamVanDeKunstenaar == null)?"<null>":this.naamVanDeKunstenaar));
        sb.append(',');
        sb.append("nameOfTheArtist");
        sb.append('=');
        sb.append(((this.nameOfTheArtist == null)?"<null>":this.nameOfTheArtist));
        sb.append(',');
        sb.append("explanation");
        sb.append('=');
        sb.append(((this.explanation == null)?"<null>":this.explanation));
        sb.append(',');
        sb.append("adresse");
        sb.append('=');
        sb.append(((this.adresse == null)?"<null>":this.adresse));
        sb.append(',');
        sb.append("precision");
        sb.append('=');
        sb.append(((this.precision == null)?"<null>":this.precision));
        sb.append(',');
        sb.append("verduidelijking");
        sb.append('=');
        sb.append(((this.verduidelijking == null)?"<null>":this.verduidelijking));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("lieu");
        sb.append('=');
        sb.append(((this.lieu == null)?"<null>":this.lieu));
        sb.append(',');
        sb.append("nomDeLArtiste");
        sb.append('=');
        sb.append(((this.nomDeLArtiste == null)?"<null>":this.nomDeLArtiste));
        sb.append(',');
        sb.append("annee");
        sb.append('=');
        sb.append(((this.annee == null)?"<null>":this.annee));
        sb.append(',');
        sb.append("geocoordinates");
        sb.append('=');
        sb.append(((this.geocoordinates == null)?"<null>":this.geocoordinates));
        sb.append(',');
        sb.append("plaats");
        sb.append('=');
        sb.append(((this.plaats == null)?"<null>":this.plaats));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.verduidelijking == null)? 0 :this.verduidelijking.hashCode()));
        result = ((result* 31)+((this.nameOfTheArtist == null)? 0 :this.nameOfTheArtist.hashCode()));
        result = ((result* 31)+((this.plaats == null)? 0 :this.plaats.hashCode()));
        result = ((result* 31)+((this.precision == null)? 0 :this.precision.hashCode()));
        result = ((result* 31)+((this.nameOfTheWork == null)? 0 :this.nameOfTheWork.hashCode()));
        result = ((result* 31)+((this.photo == null)? 0 :this.photo.hashCode()));
        result = ((result* 31)+((this.annee == null)? 0 :this.annee.hashCode()));
        result = ((result* 31)+((this.explanation == null)? 0 :this.explanation.hashCode()));
        result = ((result* 31)+((this.geocoordinates == null)? 0 :this.geocoordinates.hashCode()));
        result = ((result* 31)+((this.naamVanDeKunstenaar == null)? 0 :this.naamVanDeKunstenaar.hashCode()));
        result = ((result* 31)+((this.adresse == null)? 0 :this.adresse.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.adres == null)? 0 :this.adres.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.lieu == null)? 0 :this.lieu.hashCode()));
        result = ((result* 31)+((this.nomDeLArtiste == null)? 0 :this.nomDeLArtiste.hashCode()));
        return result;
    }

    @Override
    public int compare(StreetArt o1, StreetArt o2) {
        return o1.distance < o2.distance ? -1 : 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StreetArt) == false) {
            return false;
        }
        StreetArt rhs = ((StreetArt) other);
        return (((((((((((((((((this.verduidelijking == rhs.verduidelijking)||((this.verduidelijking!= null)&&this.verduidelijking.equals(rhs.verduidelijking)))&&((this.nameOfTheArtist == rhs.nameOfTheArtist)||((this.nameOfTheArtist!= null)&&this.nameOfTheArtist.equals(rhs.nameOfTheArtist))))&&((this.plaats == rhs.plaats)||((this.plaats!= null)&&this.plaats.equals(rhs.plaats))))&&((this.precision == rhs.precision)||((this.precision!= null)&&this.precision.equals(rhs.precision))))&&((this.nameOfTheWork == rhs.nameOfTheWork)||((this.nameOfTheWork!= null)&&this.nameOfTheWork.equals(rhs.nameOfTheWork))))&&((this.photo == rhs.photo)||((this.photo!= null)&&this.photo.equals(rhs.photo))))&&((this.annee == rhs.annee)||((this.annee!= null)&&this.annee.equals(rhs.annee))))&&((this.explanation == rhs.explanation)||((this.explanation!= null)&&this.explanation.equals(rhs.explanation))))&&((this.geocoordinates == rhs.geocoordinates)||((this.geocoordinates!= null)&&this.geocoordinates.equals(rhs.geocoordinates))))&&((this.naamVanDeKunstenaar == rhs.naamVanDeKunstenaar)||((this.naamVanDeKunstenaar!= null)&&this.naamVanDeKunstenaar.equals(rhs.naamVanDeKunstenaar))))&&((this.adresse == rhs.adresse)||((this.adresse!= null)&&this.adresse.equals(rhs.adresse))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.adres == rhs.adres)||((this.adres!= null)&&this.adres.equals(rhs.adres))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.lieu == rhs.lieu)||((this.lieu!= null)&&this.lieu.equals(rhs.lieu))))&&((this.nomDeLArtiste == rhs.nomDeLArtiste)||((this.nomDeLArtiste!= null)&&this.nomDeLArtiste.equals(rhs.nomDeLArtiste))));
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


}
