package i.gishreloaded.gishcode.managers;

import i.gishreloaded.gishcode.utils.LoginUtils;

public class LoginManager {

    private String email;

    private String name;

    private String password;

    private boolean cracked;

    private boolean favourites;

    public LoginManager(String email, String password, boolean favourites) {

        this.email = email;
        this.favourites = favourites;

        if (password == null || password.isEmpty()) {
            name = email;
            this.password = null;
            cracked = true;
        } else {
            name = LoginUtils.getName(email, password);
            this.password = password;
            this.cracked = false;
        }
    }

    public LoginManager(String email, String password) {

        this(email, password, false);
    }

    public LoginManager(String email, String name, String password, boolean favourites) {

        this.email = email;
        this.favourites = favourites;

        if (password == null || password.isEmpty()) {
            name = email;
            this.password = null;
            cracked = true;
        } else {
            this.name = name;
            this.password = password;
            this.cracked = false;
        }
    }

    public LoginManager(String name, boolean favourites) {

        this.email = name;
        this.name = name;
        this.password = null;
        this.cracked = true;
        this.favourites = favourites;
    }

    public LoginManager(String name) {

        this(name, false);
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;

        if (password == null || password.isEmpty()) {
            name = email;
            password = null;
            cracked = true;
        } else {
            name = LoginUtils.getName(email, password);
            cracked = false;
        }
    }

    public String getName() {

        if (name != null) {
            return name;
        } else if (email != null) {
            return email;
        } else {
            return "";
        }
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPassword() {

        if (password == null || password.isEmpty()) {
            cracked = true;
            return "";
        } else {
            return password;
        }
    }

    public void setPassword(String password) {

        this.password = password;

        if (password == null || password.isEmpty()) {
            name = email;
            password = null;
            cracked = true;
        } else {
            name = LoginUtils.getName(email, password);
            this.password = password;
            cracked = false;
        }
    }

    public boolean isCracked() {

        return cracked;
    }

    public boolean isFavourites() {

        return favourites;
    }

    public void setFavourites(boolean favourites) {

        this.favourites = favourites;
    }

    public void setCracked() {

        name = email;
        password = null;
        cracked = true;
    }
}
