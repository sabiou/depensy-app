package xyz.godi.budgetmanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "transaction_table")
public class Transaction implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "amount")
    private float amount;
    @ColumnInfo(name = "date")
    private Date transactionDate;
    @ColumnInfo(name = "category")
    private Category category;

    public Transaction(String name, float amount, Date transactionDate, Category category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    protected Transaction(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readFloat();
        transactionDate = new Date(in.readLong());
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(amount);
        parcel.writeLong(transactionDate.getTime());
    }
}
