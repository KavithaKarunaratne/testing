# Ticketing System

The Ticketing System is a coursework project, which purpose is to demonstrate the use of Object-Oriented Programming (OOP) paradigm for the creation of a live ticket management system. It sustains both vendors and customers by providing the means to create tickets, purchase them, and update tickets in real-time using Server-Sent Events (SSE).

## Features

- Vendor Module:
- To add tickets to the ticket pool.

  > - Monitor ticket inventory.

- Customer Module:
- Get the list of available tickets on the go.
- Buy tickets without any hassle.

- Real-Time Updates:
- SSE for ticket pool updates with live updates.
- Responsive Frontend:
- Developed with the latest user interface technologies for a friendly user interface.

## System Architecture

This project follows a layered architecture comprising:

1. **Controller Layer**: Handles HTTP requests and the act of routing.
2. **Service Layer**: Corporate rationality for ticketing.
3. **Data Access Layer**: Communication with the database.
4. **Frontend**: Customer and vendor front end developed using react.
5. **Event Handling**: Real-time ticket update through SSE based real communication.

Here’s a visual representation of the architecture:

```
React <--> Spring Boot <--> MySQL
       |                                      |
       +----------- Real-Time Updates --------+
```

## Technologies Used

### Backend:

- **Java with Spring Boot** (REST API, SSE)
- **MySQL** (Database)
- **Lombok** (Boilerplate code reduction)

### Frontend:

- **React** (UI components)
- **Tailwind CSS** (Styling)
- **Fetch API** (HTTP client)

## Setup Instructions

### Backend Setup:

1. Clone the repository:

```bash
git clone <repository-url>
```

2. Navigate to the backend directory:

```bash
cd backend
```

3. Configure the database:

- Change the application.properties file to contain your MySQL credentials.
- Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticketing_system
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

4. Build and run the application:

```bash
mvn spring-boot:run
```

### Frontend Setup:

1. Navigate to the frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm start
```

4. Open the application through your web browser.

## How It Works

### Vendor Workflow:

1. Vendors sign on and proceed to the ‘Add Tickets’ page.
2. Fill out the ticket details and click on submit to put tickets into the ticket pool.
3. All connected users get updates in real-time.

### Customer Workflow:

1. Customers then login to view the real time ticket pool.
2. Choose the number of tickets to buy and pay for the tickets.
3. Tickets availability changes are reflected instantly for all users.

## Code Snippets

### Backend Example: Ticket Creation Controller

```java
@PostMapping("/ticketAdd")
public ResponseEntity<String> addTickets(@RequestBody TicketDTO ticketDTO) {
    ticketService.addTickets(ticketDTO);
    return ResponseEntity.ok("Tickets successfully added!");
}
```

### Frontend Example: Real-Time Ticket Display

```javascript
import {useEffect, useState} from "react";

const RealTimeTickets = () => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    const eventSource = new EventSource(
      "http://localhost:8080/api/tickets/stream"
    );
    eventSource.onmessage = (event) => {
      setTickets(JSON.parse(event.data));
    };
    return () => eventSource.close();
  }, []);

  return (
    <div>
      {tickets.map((ticket) => (
        <p key={ticket.id}>
          {ticket.name} - {ticket.quantity} available
        </p>
      ))}
    </div>
  );
};

export default RealTimeTickets;
```

## Future Enhancements

– add user authentication and user authorization.
-Ticket refund or cancellation services.
-Vendor tracking of ticket sales through the use of analytics dashboard.

- Integration to cloud services (AWS, Azure, etc.).

## Acknowledgments

Special thanks to the course instructor and the team members for the support and assistance in the course of this work.
