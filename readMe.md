# Json Searcher

## Descriptions:
A Json searcher support fast search on defined entities

**OS:** *Debian (Docker)

[Get Started](#get-started)

[Systems Diagram](#systems-diagram)

[Design Philosophy](#design-philosophy)

[Appendix](#appendix)


## Get Started

#### How to: run the app in an env with jdk8 and sbt setup

##### Run Test:
 
```
sbt clean test
```

##### Start the console app: 
 
```
sbt clean run
```

#### How to: run the app without jdk and sbt
Please be aware, the docker image will be built from scratch, which means the building time for the first run would be super long.... 

after the first run, two external volume would be created to cache all the loaded dependencies to make the next run much faster;

##### Run Test:
 
```
./auto/test
```

##### Start the console app: 
 
```
./auto/run
```

## Systems Diagram

```
 
 ________________________________                  ______________________________________________________
|    Console                    |                  |            Json-Searcher-Core                       |
|                               |                  |        __________________________________           |               
|  atStart                      |        |---------|-----> |- materialise denormolised-data   |          |
|  atInstruction                |        |         |       |- preload indices                 |          |
|  atSearchableFields           |        |         |       |__________________________________|          |                         
|  atStartSearch   -------------|--------|         |                                   |                 |
|  atEnterSearchTerm     --->|  |               |--|-->A In memory SearchStore <-------|                 |
|  atsEnterSearchValue   |<---| |               |  |                |                                    |
|  atOperateSearch   <---|      |               |  |                |                                    |
|               |               |               |  |                |                                    |
|               |_____________SearchTerm________|  |                |                                    |
|                               |                  |                |                                    |   
|     stdout    <---------------|----SearchResults-|----------------|                                    |
|_______________________________|                  |_____________________________________________________|
```

## Design Philosophy

#### Core Principles:
- Searhable Data should be denormolised for fast search
- Data should be indexed by the available search key
- Searchable Data should be materialised into memory before SearchOperation
- Console should have finite states and each state should know its previous restatable state
- Exceptions should not break the user experiences and should have instructive notifications

#### Disadvantages and optimizations:

##### Memory Leak when large volume of data exceeding single machine capacity:

Although diskIO by streaming in-memory-denormolised data into multiple partitions in disk by hash key for a given search key could guarantee the memory would be explored given a big enough disk volume; 

This appraoch would slow down the search dramactically even with HDFS support;

The ideal way to optimise this large volume data scenario would be applying distributed computing in a cluster with mutiple machines; each instance could be working on a partition of searchkey and later on assemble the result to serve back to the searcher; It is a well-known map-reduce application senario;



## Appendix

#### Domain Analysis:
1. Organisation: 

    All Fields are required

2. Tickets: 
    
    Optional Fields: `[type, description, assignee_id, organization_id, due_at]`

3. User:
    
    Optional Fields: `[alias, verified, locale, timezone, email, phone, organization_id]`

#### Denormolised-View for search-results-entity:
- on user granularity:
    ```
    UserSearchResult {
        user: User,
        org: Option[Organisation],
        assigned_tickets: List[Ticket],
        submitted_tickets: List[Ticket]   
    }
    ```
- on ticket granularity:
    ```
    TicketSearchResult {
        ticket: Ticket,
        org: Option[Organisation],
        assignee: Option[user],
        submitter: Option[user] 
    }
    ```
- on organisation granularity:
    ```
    OrganisationSearchResult {
        org: Organisation,
        users: Array[User],
        tickets: Array[Ticket]
    }
    ```