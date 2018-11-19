package db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Create By hHui on 2018/11/16
 */
@Entity(
        nameInDb = "TABLE_DEVICES"
)
public class Device {
    @Id(autoincrement = false)
    private String device_id;  //即设备mac地址

    @Property(nameInDb = "DEVICE_NAME")
    private String device_name;//设备名称

    @Property(nameInDb = "LAST_CONNECT_TIME")
    private Date lastConnectTime; //上次连接时间

    @Generated(hash = 1686680236)
    public Device(String device_id, String device_name, Date lastConnectTime) {
        this.device_id = device_id;
        this.device_name = device_name;
        this.lastConnectTime = lastConnectTime;
    }

    @Generated(hash = 1469582394)
    public Device() {
    }

    public String getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return this.device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Date getLastConnectTime() {
        return this.lastConnectTime;
    }

    public void setLastConnectTime(Date lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

}
