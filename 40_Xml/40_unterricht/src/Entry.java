
import java.time.LocalDate;

// POJO bzw. Model
public class Entry
{
    private String id;
    private String title;
    private String description;
    private LocalDate date;
    private int balance;
    private String currency;

    public Entry(String id, String title, String description, LocalDate date, int balance, String currency)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.balance = balance;
        this.currency = currency;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    @Override
    public String toString()
    {
        return "Entry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }




}
