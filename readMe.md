### Domain Analysis:
1. Organisation: 

    All Fields are required

2. Tickets: 
    
    Optional Fields: `[type, description, assignee_id, organization_id, due_at]`

3. User:
    
    Optional Fields: `[alias, verified, locale, timezone, email, phone, organization_id]`

### Denormolised-View for search-results-entity:
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