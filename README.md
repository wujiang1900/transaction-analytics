# A reactive webflux java project that loads transaction data and posts to an analytics endpoint
This is a reactive example java project that loads transaction data from csv files, retrieves customer profile, and posts data to an analytics endpoint

## Requirements
You are responsible for designing a customer profiling platform. Data is sourced from an upstream system (within your organization) for different e-commerece vendors (e.g. amazon, ebay, local online vendors etc.) as CSV files. You have the flexibility to choose the interface between the upstream system and your platform (you may include that in the README file mentioned below). The data consists of transactions made by customers for the e-commerce website (identified by vendorId).

CSV Report format of customer transaction data:
CustomerEmail, Products, TransactionID, TransactionAmount, Currency, Timestamp, Region, VendorId
rich123@gmail.com, SKU123|SKU121, transac123, 100.56, USD, 2023-05-05T12:00:00Z, US_CA, amzn_US

About the CSV file:
CSV file may contain upto a maximum of 10K rows
A customer (identified by customer email) may appear multiple times in a CSV
A customer may appear across multiple e-commerce vendors

Implement a service that would do the following:
- Store the purchase details in a data store of your choice
- Fetch customer details from customer service API (within the same organization) against the customer email
  Customer Service Request:
  GET  -> /customer?email=rich123@gmail.com
  Header -> "X-API": "ABC123"

    Successful customer Service Response format:
    {
        "code": "SUCCESS",
        "customer": {
            "email": rich123@gmail.com, #customer_email
            "name": "Ricahrd S", #customer_name
            "id": "cust123", #internal_customer_id
            "category": "MARKETING_PROMO_1"
        }
    }

    It may return a 404 NOT FOUND if the email doesn't map to any customer.

- Aggregate all the purchases made by the customer so far (based on data stored in our platform)
- Create a request object and pass it on to a downstream system that accepts POST APIs at a rate of 10 req/sec
  Request format:
  Header -> "X-API": "ABC123"
  POST API -> /analytics/customer
  {
        "custId": "cust123",
        "custCategory": "MARKETING_PROMO_1",
        "transaction_summary": {
            "total_count": 2, #total_transactions_made_by_customer_across_all_vendors
            "total_amount": 350.56 #total_amount_spent_by_customer_across_all_vendors
        },
        "transactions": [
            {
                "currency": "USD",
                "amount": 100.56,
                "transactionID": "",
                "timestamp": "2023-05-05T12:00:00Z",
                "products": ["SKU123", "SKU121"],
                "vendorId": "amzn_US"
            },
            {
                "currency": "USD",
                "amount": 250.00,
                "transactionID": "",
                "timestamp": "2023-05-07T12:00:00Z",
                "products": ["SKU455", "SKU777"],
                "vendorId": "ebay_CA"
            }
        ]
  }

In your project, along with the source code, create a README.md file and explain the design of your service.
Identify the technologies you would use in your design. You may stub out all external components like database (if applicable), message bus(if applicable), cache (if applicable) etc.

## Tech stack
- **Backend**
    - Spring Boot, Spring Webflux, Spring Data
    - Maven
    - Libraries (Lombok, ModelMapper, Junit 5)

## Implementation details
1. Spring boot framework is chosen as the DI engine to manage the class dependencies. 
2. Interface is utilized to abstract the real implementation, so that we can easily swap the implementations. e.g. use mock services to stub external api calls for integration testing.
3. TransactionWorkFlowService is the service that coordinates all the steps needed in this project.
4. TransactionLoadService is responsible for loading the transaction data from the upstream system (vendor csv files) or the company platform (how this will work is ambiguous and needs clarification, so I just defined an interface method).
5. CustomerDetailService is responsible for retrieving customer details from an api endpoint. My implementation is to use reactive Webflux webclient. One can implement it with RestTemplate, or even messaging bus. Too many possible ways.
6. CustomerAnalyticsService is responsible for creating request payload and posting to analytics endpoint. Again, my implementation is reactive Webflux webclient. Many other possible ways could be implemented. 
7. H2 in-memory db is used in this project. We can easily swap it with a real DB in production env.
8. TransactionDetailDto is the domain object in service layer that needs to be sent in request payload. TransactionDetail is the entity object that maps to db table to persist the transactions in DB. ModelMapper library is used to convert between these two objects.