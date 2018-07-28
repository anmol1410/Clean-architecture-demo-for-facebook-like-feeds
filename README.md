# Clean-architecture-demo-for-facebook-like-feeds
It has 2 features. Loads the data from any repository, which can be remote(server etc) or local(database etc). We can select any feed to view its details. Uses clean architecture for this, with 2 uses cases in this project which are to Like any Feed and to Get all the feeds. 

App containing two screens:-
1) Feed with multiple type of views like facebook. The content can be Text, Image or Both.
2) Details screen showing the selected feed.

It has the below features:-
1) The user can like or unlike(if liked) a post either from feed or from details page.
2) The user can view details by clicking on feed item
3) Feed is Image-only if no text available
4) Feed is Text-only if no image available
5) Club the items with same date​ under one date title.


The Clean Architecture
Robert C. Martin aka Uncle Bob father of the clean architecture, which enforces the Solid principles.
It has 3 different layers:

Presentation Layer:
Focuses on presenting the data on UI.
Contains Android components like Activities/Services/Fragments etc. and Custom Views.
Contains Presenters
Presenters can be any of the MVC/MVP/MVVM patterns. MVP is used most favorably for presentation purposes though.
Here based on the Data changes, the View is updated.


So the presentation layer only focuses on how the data is presented on the UI.
Domain Layer:
Focuses on the business logic.
Contains the Data Models
Contains the screen/view interfaces
Use cases (different functionalities which exist in our application).
This layer is pure Java/Kotlin based and is Android free.



Data layer:
Focuses on data(external/internal).
Contains any external API's access.
Local Storage Components.
Memory/disk caches.
