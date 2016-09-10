package com.jim.pocketaccounter.database;

import android.support.annotation.Keep;

import com.jim.pocketaccounter.database.convertors.CalendarConvertor;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Calendar;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "CREDIT_DETAILS", active = true)
public class CreditDetials {
    @Property
    private String credit_name;
    @Property
    private int icon_ID;
    @Convert(converter = CalendarConvertor.class, columnType = Long.class)
    private Calendar take_time;
    @Property
    private double procent;
    @Property
    private double procent_interval;
    @Property
    private long period_time;
    @Property
    private long period_time_tip;
    @Id(autoincrement = true)
    @Property
    private long myCredit_id;
    @Property
    private double value_of_credit;
    @Property
    private double value_of_credit_with_procent;
    @ToOne
    private Currency valyute_currency;
    @ToMany(joinProperties = {
            @JoinProperty(name = "myCredit_id", referencedName = "id")
    })
    private List<ReckingCredit> reckings;
    @Property
    private boolean key_for_include;
    @Property
    private boolean key_for_archive;
    @Property
    private String info = "";
    @Generated(hash = 330245755)
    private transient boolean valyute_currency__refreshed;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 11952630)
    private transient CreditDetialsDao myDao;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    @Keep
    public CreditDetials(int icon_ID, String credit_name, Calendar take_time, double value_of_credit,
                         double procent, long period_time, long period_time_tip, boolean key_for_include,
                         double procent_interval, Currency valyute_currency, double value_of_credit_with_procent, long myCredit_id) {
        this.icon_ID = icon_ID;
        this.credit_name = credit_name;
        this.take_time = take_time;
        this.value_of_credit = value_of_credit;
        this.procent = procent;
        this.procent_interval = procent_interval;
        this.period_time = period_time;
        this.period_time_tip = period_time_tip;
        this.key_for_include = key_for_include;
        this.valyute_currency = valyute_currency;
        this.value_of_credit_with_procent = value_of_credit_with_procent;
        this.myCredit_id = myCredit_id;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1034520580)
    public synchronized void resetReckings() {
        reckings = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 759362264)
    public List<ReckingCredit> getReckings() {
        if (reckings == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ReckingCreditDao targetDao = daoSession.getReckingCreditDao();
            List<ReckingCredit> reckingsNew = targetDao._queryCreditDetials_Reckings(myCredit_id);
            synchronized (this) {
                if (reckings == null) {
                    reckings = reckingsNew;
                }
            }
        }
        return reckings;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 215703177)
    public void setValyute_currency(Currency valyute_currency) {
        synchronized (this) {
            this.valyute_currency = valyute_currency;
            valyute_currency__refreshed = true;
        }
    }

    /**
     * To-one relationship, returned entity is not refreshed and may carry only the PK property.
     */
    @Generated(hash = 6866301)
    public Currency peakValyute_currency() {
        return valyute_currency;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 512408870)
    public Currency getValyute_currency() {
        if (valyute_currency != null || !valyute_currency__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CurrencyDao targetDao = daoSession.getCurrencyDao();
            targetDao.refresh(valyute_currency);
            valyute_currency__refreshed = true;
        }
        return valyute_currency;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 745291544)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCreditDetialsDao() : null;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isKey_for_archive() {
        return this.key_for_archive;
    }

    public void setKey_for_archive(boolean key_for_archive) {
        this.key_for_archive = key_for_archive;
    }

    public boolean isKey_for_include() {
        return this.key_for_include;
    }

    public void setKey_for_include(boolean key_for_include) {
        this.key_for_include = key_for_include;
    }

    public double getValue_of_credit_with_procent() {
        return this.value_of_credit_with_procent;
    }

    public void setValue_of_credit_with_procent(double value_of_credit_with_procent) {
        this.value_of_credit_with_procent = value_of_credit_with_procent;
    }

    public double getValue_of_credit() {
        return this.value_of_credit;
    }

    public void setValue_of_credit(double value_of_credit) {
        this.value_of_credit = value_of_credit;
    }

    public long getMyCredit_id() {
        return this.myCredit_id;
    }

    public void setMyCredit_id(long myCredit_id) {
        this.myCredit_id = myCredit_id;
    }

    public long getPeriod_time_tip() {
        return this.period_time_tip;
    }

    public void setPeriod_time_tip(long period_time_tip) {
        this.period_time_tip = period_time_tip;
    }

    public long getPeriod_time() {
        return this.period_time;
    }

    public void setPeriod_time(long period_time) {
        this.period_time = period_time;
    }

    public double getProcent_interval() {
        return this.procent_interval;
    }

    public void setProcent_interval(double procent_interval) {
        this.procent_interval = procent_interval;
    }

    public double getProcent() {
        return this.procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    public Calendar getTake_time() {
        return this.take_time;
    }

    public void setTake_time(Calendar take_time) {
        this.take_time = take_time;
    }

    public int getIcon_ID() {
        return this.icon_ID;
    }

    public void setIcon_ID(int icon_ID) {
        this.icon_ID = icon_ID;
    }

    public String getCredit_name() {
        return this.credit_name;
    }

    public void setCredit_name(String credit_name) {
        this.credit_name = credit_name;
    }

    public boolean getKey_for_archive() {
        return this.key_for_archive;
    }

    public boolean getKey_for_include() {
        return this.key_for_include;
    }

    @Generated(hash = 4952736)
    public CreditDetials(String credit_name, int icon_ID, Calendar take_time, double procent, double procent_interval,
                         long period_time, long period_time_tip, long myCredit_id, double value_of_credit, double value_of_credit_with_procent,
                         boolean key_for_include, boolean key_for_archive, String info) {
        this.credit_name = credit_name;
        this.icon_ID = icon_ID;
        this.take_time = take_time;
        this.procent = procent;
        this.procent_interval = procent_interval;
        this.period_time = period_time;
        this.period_time_tip = period_time_tip;
        this.myCredit_id = myCredit_id;
        this.value_of_credit = value_of_credit;
        this.value_of_credit_with_procent = value_of_credit_with_procent;
        this.key_for_include = key_for_include;
        this.key_for_archive = key_for_archive;
        this.info = info;
    }

    @Generated(hash = 189858148)
    public CreditDetials() {
    }
}