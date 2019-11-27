# EntryManagement
## App for Innovacer SummerGeeks 2020

## Intrdouction
Entry Management is a software biuld to manage the entries made by visitors to meet the hosts at Innovaccer Office. 

## Resources Used
- Android Studio (Java and XML)
- Firebase Realtime Database
- Java Mail API
- SMS services

## Database Structure

```
|__Hosts
	|__1
	   |__About
	   |__Department
	   |__Email
	   |__Name
	   |__Phone
	   |__Post
	|__2
	   |__About
	   |__Department
	   |__Email
	   |__Name
	   |__Phone
	   |__Post
|_Visitors
	 |__Generated ID
	 		|__checkin
			|__checkin-date
			|__checkout
			|__email
			|__host_object
			|__name
			|__ongoing
			|__phone
```

## Application Flow
- When you open the application a splash screen appears

![SplashScreen](https://user-images.githubusercontent.com/32924261/69726175-fa6bb600-1145-11ea-82ea-91e708341e70.jpg)

- After a moment the splash finishes and a list of  current entries made appears. If no current entries are made a list empty message is displayed.

 ![EmptyEntryList](https://user-images.githubusercontent.com/32924261/69726096-c4c6cd00-1145-11ea-901b-f7bab88184db.jpg)

Clicking on "make a new entry" button will display a list of hosts available at the department and whom the visitor can meet. 

![HostsList](https://user-images.githubusercontent.com/32924261/69726440-9ac1da80-1146-11ea-9580-6b9763c0a09e.jpg)

The visitor can select one of the hosts he/she wants to meets. When a host is selected a page is displayed showing the details of the host.

![HostDetails](https://user-images.githubusercontent.com/32924261/69726577-dbb9ef00-1146-11ea-9fd1-bc933fd08a23.jpg)

After confirming the details of the host which the visitor wants to meet he/she presses the "Continue" button and a form is displayed.

![Form](https://user-images.githubusercontent.com/32924261/69726609-e83e4780-1146-11ea-93c6-fa55c481362b.jpg)

After filling in his/her details the visitor presses the "Check-in" button. Doing this the user check-in entry is completed.
The entry list page again opens after this with the details of the checked-in user.

![FilledEntryList](https://user-images.githubusercontent.com/32924261/69726607-e83e4780-1146-11ea-9e0b-32a47e34a7e9.jpg)

The host receives an email as well as the message describing the details of the visitor. Image of the mail is attached.

![HostMail](https://user-images.githubusercontent.com/32924261/69726611-e83e4780-1146-11ea-8b7d-4808cdc406f6.jpg)

After the visit is done the visitor selects his/her details from the list. When the item on the entry lis is clicked a page appears showing the visit details as well as an option to checkout.

![CheckinDetails](https://user-images.githubusercontent.com/32924261/69726605-e7a5b100-1146-11ea-943c-5e1a400c8115.jpg)

When the "checkout" button is pressed the visitor is asked to confirm the checkout.

![ConfirmCheckout](https://user-images.githubusercontent.com/32924261/69726606-e7a5b100-1146-11ea-8e94-5dbe58cdcea6.jpg)

If the visitor selects "yes" he/she is successfully checked-out. Also the details of that visitor is removed from that list. The update d list is shown.

![EmptyEntryList](https://user-images.githubusercontent.com/32924261/69726096-c4c6cd00-1145-11ea-901b-f7bab88184db.jpg)

The visitor then gets an email describing the details of the visit.

![VisitorEmail](https://user-images.githubusercontent.com/32924261/69727831-45d39380-1149-11ea-89cf-ffe99f9f17bb.jpeg)

Since the data of the visitor is to be preserved, therefore that data is stored in Firebase Realtime Database.

New host can be added to the database and the list will be updated on the app in realtime.
