# my-nol-app
Sample NOL Application using webflux implementation

## Steps to run the application
  
  - mvn clean install  
  
  - java -jar target\my-nol-app-0.0.1-SNAPSHOT.jar
  
 ## Launch URL
  
  - To Create NOL record with initial balance
    
    http://localhost:8899/nol/create
    
    Req:
    {
      "paxName": "Abhinav Rana",
      "mobileNumber": "5555555",
      "paxAddress": "XYZ",
      "nolBalance": 100.00,
      "creationDate": "2019-01-28T13:30:00.000+0000"
    }
  
  - To Do check-in
    
    http://localhost:8899/nol/checkin

    Req:
    {
      "nolID": "c809b77b-4352-463e-9512-505adc5853bc",
      "transportType": "Boat",
      "checkInZone": "7",
      "checkInTime": "2019-01-28T14:35:00.000+0000"
    }
  
  - To Do check-out
    
    http://localhost:8899/nol/checkout
    
    Req:
    {
      "nolID": "c809b77b-4352-463e-9512-505adc5853bc",
      "checkOutZone": "3",
      "checkOutTime": "2019-01-28T15:05:00.000+0000"
    }
    
## Transport Type
  - Bus, Metro, Boat, Tram

## Zones
  - 1, 2, 3, 4, 5, 6, 7
