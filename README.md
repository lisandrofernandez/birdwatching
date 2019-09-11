# Birdwatching

A birdwatching RESTful API.


## Technical details

* Written in Java.
* Uses Spring Framework.
* Application runs on port 8080.
* H2 database in memory mode.
    - Created once the application starts and dropped when the application shuts
      down.
    - Connection:
        + URL: `jdbc:h2:tcp://localhost:9090/mem:birdwatching`.
        + Username: `su`.
        + Password: (empty).


## Requirements

* JDK 1.8 or superior.
* Maven (optional).


## Compile and run

Using maven wrapper:

```
$ ./mvnw clean spring-boot:run
```

Alternatively using installed maven version:

```
$ mvn clean spring-boot:run
```


## Designing and implementation

### Bird

```java
@Entity
@Table(name = "bird")
public class Bird extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Enumerated
    private BirdSize size;

    @Column(name = "photo_url")
    private String photoURL;

    @ElementCollection(targetClass = Color.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "bird_color", joinColumns = @JoinColumn(name = "bird_id"))
    @Column(name = "color")
    @Enumerated
    private List<Color> colors = new ArrayList<>();

    // Constructors and methods removed for brevity
}
```

* `BirdSize` is an `enum`.
* `Color` is an `enum`. Plumage colors are represented as a list of it. The
  number restriction (four) should by implemented by code validation.

### NaturalReserve

```java
@Entity
@Table(name = "natural_reserve")
public class NaturalReserve extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    // Just for cascade delete, no getter and setter
    @OneToMany(mappedBy = "reserve", cascade = CascadeType.REMOVE, orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Chance> chances = new ArrayList<>();

    // Constructors and methods removed for brevity
}
```

* `Region` is an entity itself with `id` and `name`, so in order to create or
  update a `NaturalReserve` entity a `Region.id` must be provided.

### Chance

```java
@Entity
@Table(name = "chance")
public class Chance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bird_id")
    private Bird bird;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "natural_reserve_id")
    private NaturalReserve reserve;

    @Enumerated
    private Month month;

    @Column(name = "probability")
    private Double probability;

    // Constructors and methods removed for brevity
}
```

* `Chance` is an entity which represents the probability of seeing a bird in a
  natural reserve depending on the month.

### Region

```java
@Entity
@Table(name = "region")
public class Region extends BaseEntity {

    @Column(name = "name")
    private String name;

    // Constructors and methods removed for brevity
}
```

## Services

Examples can be found in `doc/examples.sh`.

### Birds

| Method | URL           | Description        |
| -------|---------------|--------------------|
| `GET`  | /api/v1/birds | Retrieve all birds |

### Natural reserves

| Method   | URL                   | Description                   |
| ---------|-----------------------|-------------------------------|
| `GET`    | /api/v1/reserves      | Retrieve all natural reserves |
| `GET`    | /api/v1/reserves/{id} | Retrieve a natural reserves   |
| `POST`   | /api/v1/reserves      | Create a natural reserve      |
| `PUT`    | /api/v1/reserves/{id} | Update a natural reserve      |
| `DELETE` | /api/v1/reserves/{id} | Delete a natural reserve      |

### Chances

| Method | URL                         | Description                   |
| -------|-----------------------------|-------------------------------|
| `GET`  | /api/v1/chances?date={date} | Retrieve all chances by month |

### Regions

| Method | URL             | Description          |
| -------|-----------------|----------------------|
| `GET`  | /api/v1/regions | Retrieve all regions |


## Database

After the database is created, two scripts (a DDL and a DML) are executed. These
files are:

* `src/main/resources/schema.sql`
* `src/main/resources/data.sql`


## Copyright

Copyright &copy; 2019 [Lisandro Fernandez](https://github.com/lisandrofernandez).
