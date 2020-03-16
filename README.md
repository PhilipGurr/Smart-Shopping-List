# Smart Shopping List
[![CircleCI](https://circleci.com/gh/PhilipGurr/Smart-Shopping-List/tree/master.svg?style=svg)](https://circleci.com/gh/PhilipGurr/Smart-Shopping-List/tree/master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1f058b0f266547e591176ab41120b4f2)](https://www.codacy.com/manual/PhilipGurr/Smart-Shopping-List?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=PhilipGurr/Smart-Shopping-List&amp;utm_campaign=Badge_Grade)

This app allows the user to create shopping lists and keep track of the products he needs to buy or wants to save for later access.
Products can be added by text or scanning the barcode of the product with the device camera. It provides an overview of how many products
the user collected already and which ones are yet to tick off. By signing in with a Google account, shopping lists can be accessed on other 
devices too.

## Screenshots

My Lists            |  Completed Lists            |  Detail            |  Barcode Scanning
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/PhilipGurr/Smart-Shopping-List/blob/master/Screenshots/MyLists.jpg?raw=true" width="200" height="396">  |  <img src="https://github.com/PhilipGurr/Smart-Shopping-List/blob/master/Screenshots/CompletedLists.jpg?raw=true" width="200" height="396">  |  <img src="https://github.com/PhilipGurr/Smart-Shopping-List/blob/master/Screenshots/Detail.jpg?raw=true" width="200" height="396">  |  <img src="https://github.com/PhilipGurr/Smart-Shopping-List/blob/master/Screenshots/BarcodeScan.jpg?raw=true" width="200" height="396">


## Project Setup
The project was developed using 100% Kotlin and a simplified Clean Architecture approach including ViewModels and Repository pattern. Additionally, the project is 
separated into **app**, **data** and **domain** modules to ensure reusability.

### Tech stack

*   [AndroidX](https://developer.android.com/jetpack/androidx)
*   [Fragment (Single Activity)](https://developer.android.com/guide/components/fragments)
*   [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
*   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
*   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
*   [Data Binding](https://developer.android.com/topic/libraries/data-binding/)
*   [Navigation Components](https://developer.android.com/topic/libraries/architecture/navigation/)
*   [Dagger 2](https://google.github.io/dagger/)
*   [Authentication](https://firebase.google.com/docs/auth)
*   [Retrofit](http://square.github.io/retrofit/)
*   [Cloud Firestore](https://firebase.google.com/docs/firestore)
*   [ML Kit](https://developers.google.com/ml-kit)
*   [CameraX](https://developer.android.com/training/camerax)
*   [Animations & Transitions](https://developer.android.com/training/animation/)
*   [Coil](https://github.com/coil-kt/coil)
*   [JUnit 4](https://junit.org/junit4/)
*   [Mockito](http://site.mockito.org/)
*   [QuickPermissions](https://github.com/QuickPermissions/QuickPermissions-Kotlin)

## License
[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)
