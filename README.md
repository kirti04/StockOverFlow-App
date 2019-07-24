# StockOverFlowApp
Features checklist:
20 StackOverflow users is listed when App is launched
List shows Profile image, name and reputation
Tap on list item will open a new Activity with the Detail View.
Detail view shows profile image , reputation, userid and name, creation date and location with badges . 
  -User can be Blocked and Followed in the Detail screen using the Buttons. 
  -Screen data and View is maintained even when screen configuration is changed.
Application handles the network state changed, so when App goes in offline mode, error screen is displayed with Empty list view and when the network is restored, screen refreshes automatically and displays the data. 
List item is greyed out if the user is blocked and Pop menu will open to Unblock the user. 
List item is marked as Followed if the User is followed . 
Block/Follow feature is impleented offline.  

Technical Details: 
Retrofit and RxJava with Okhttp client and Cache is used to process the Api calls and can be used to store the data in offline mode. 
Glide used for image processing
Butterknife used for UI dependency
Dagger2 is used for maintaining the DI
MVVM model is used as an architectural model to retain ad observe the view changes to handle configuration chnages and better redability. 
Live data concept used for making activity observe the changes in the data as it is Life cycle aware. 

Packages in Application:
di- holds the dependency injection components and modules
data- holds data and repositories
ui -  stores the activities and associated viewModels
Util- has Viewmodelfactory





