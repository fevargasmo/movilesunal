package co.edu.unal.reto8;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(tableName = "company")
public class Company {

    @Getter
    @Setter
    @DatabaseField(generatedId = true)
    private Long id;

    @Getter
    @Setter
    @DatabaseField
    private String index;

    @Getter
    @Setter
    @DatabaseField
    private String name;

    @Getter
    @Setter
    @DatabaseField
    private String url;

    @Getter
    @Setter
    @DatabaseField
    private String telephone;

    @Getter
    @Setter
    @DatabaseField
    private String email;

    @Getter
    @Setter
    @DatabaseField
    private String products;

    @Getter
    @Setter
    @DatabaseField
    private String services;

    @Getter
    @Setter
    @DatabaseField
    private String type;

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}