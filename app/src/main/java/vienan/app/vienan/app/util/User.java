package vienan.app.vienan.app.util;

/**
 * Created by lenovo on 2015/4/22.
 */
public class User {
    public static String NAME="username";
    public static String ADDRESS="address";
    public static String UNIT="unit";
    public static String MOBILE="mobile";
    public static String QQ="qq";
    private String username;
    private String mobile;
    private String unit;
    private String qq;
    private String address;
    private int DB_id=-1;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", unit='" + unit + '\'' +
                ", qq='" + qq + '\'' +
                ", address='" + address + '\'' +
                ", DB_id=" + DB_id +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDB_id() {
        return DB_id;
    }

    public void setDB_id(int DB_id) {
        this.DB_id = DB_id;
    }
}
