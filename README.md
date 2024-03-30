# TMDb Movies
This is a sample app that fetches a list of popular, now playing and upcoming movies from The Movie Database (TMDb) API and 
caches the result in a local database. The app uses pagination to load more pages when user scrolls
down the list. When clicking on a specific movie a movie details screen will be shown with more info
about the movie.

### Screenshots
|                                                       |                                                                  |                                                                         
|-------------------------------------------------------|------------------------------------------------------------------|
| <img width="250" src="screenshots/movies_list.png" /> | <img width="250" src="screenshots/movie_details.png" />          |

### Build & Installation Instructions
You must use Gradle JDK 11 to run the project.

### Architecture
This sample app uses MVVM Architecture with clean architecture principles, a well known architecture for 
Android, the app's components are less dependents and easier to test.

### Testing
There are only tests for `GetMovieDetailsUseCase`, `MovieDetailsViewModel` and db testing but I will add other
tests for more classes and also add different kind of testing like UI, integration or mock server
testing.

### Resources
1. APK link: <https://drive.google.com/file/d/1Hc6EB91VaPuEdnbVhhIeZ083HczZV6B1/view?usp=sharing>.

### Libraries used
* Lifecycle & ViewModel.
* [Kotlin Coroutines][coroutines].
* [Hilt][hilt] for dependency injection.
* [Retrofit][retrofit] for REST api communication.
* [Gson][gson] for parsing JSON.
* [Jetpack compose][compose] for UI.
* [Room][room] for local storage.
* Junit & mockito for testing.

### Jetpack Compose Implementation
- - - - - - - - - - - - - - - - - - - -
The project has an implementation using *Jetpack Compose* to run it you need to use at least android
studio [Android Studio Arctic Fox or Bumblebee Canary](https://developer.android.com/studio).

* The project uses toml file to manage dependencies.
* The `minSdkVersion` is __24__.
* The additional libraries used are __Accompanist (Insets & Coil)__ and __the Navigation Component__.
* The app theme relays on the Material Theme to model Colors, Shapes, and Typography.
* `LocalElevations` & `LocalImages` are used to associate different Elevations & Images with the app themes.

[retrofit]: http://square.github.io/retrofit
[gson]: https://github.com/google/gson
[hilt]: https://developer.android.com/training/dependency-injection/hilt-android
[compose]: https://developer.android.com/jetpack/compose
[coroutines]: https://kotlinlang.org/docs/coroutines-overview.html
[room]: https://developer.android.com/training/data-storage/room
