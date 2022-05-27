# Wasanii

This a simple events app that focuses on arts and culture. Due to my interest in the arts, I decided to create an app that would help people to find events and venues around the city(Nairobi).

It solves a problem for me in that I've always had a challenge trying to find events in the art space e.g. Art Exhibitions, Jazz Events, etc. I hope it solves the same problem I've had with others out there in search of similar events. Wasanii is swahili for "Artists" and I think it's a good name for the app.

## Table Of Content

- [General Preview](#general-preview)
- [Architecture](#architecture)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Resources](#resources)
- [To Do List](#to-do-list)
- [Contributors](#contributors)
- [Contributing](#contributing)
- [License](#license)

## General Preview

More designs coming soon...

<img src=assets/img1.png width="250"  />
<img src=assets/img2.png width="250" />
<img src=assets/img3.png width="250" />
<img src=assets/img4.png width="250" />
<img src=assets/img5.png width="250" />

## Architecture

The app uses the MVVM architecture: A comprehensive architecture design is still WIP.

## Installation

1. Clone the project

```bash
  git clone https://github.com/malcolmmaima/Wasanii.git
```

2. Go to android studio compile and install dependencies

3. Run the app on your emulator or device

## Dependencies

- [Hilt](https://github.com/google/hilt) - Dependency Injection library.
- [Jetpack](https://developer.android.com/jetpack)
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    - [AndroidX](https://developer.android.com/jetpack/androidx) - Major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
    - [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)-Component that allows easier implementation of navigation from simple button clicks to more complex patterns.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe http client
  and supports coroutines out of the box.
- [GSON](https://github.com/square/gson) - JSON Parser,used to parse
  requests on the data layer for Entities and understands Kotlin non-nullable
  and default parameters.
- [OkHttp-Logging-Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library support for kotlin coroutines.
- [Picasso](https://square.github.io/picasso/) - Image-loading android library .
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Storage of key-value pairs
- [Mockito](https://github.com/mockito/mockito) -

## Resources

- [Some artile that helped](link)

## To Do List

- [x] Design Screens
- [x] Implement the screens designs
- [x] Consume Api and populate events lists.
- [ ] Unit Testing
- [ ] Deploy app on Playstore

## Contributors

Auto-populated from:[contrib.rocks](https://contrib.rocks)

<a href="https://github.com/malcolmmaima/Wasanii/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=malcolmmaima/Wasanii" />
</a>

## Contributing

Contributions are always welcome!

See [contributing guidelines](https://github.com/github/docs/blob/main/CONTRIBUTING.md) for ways to get started.

Please adhere to this project's `code of conduct`.

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request and set ```dev``` as the base branch

## License

[MIT](https://choosealicense.com/licenses/mit/)
