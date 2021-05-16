# BitcoinMarketPlace

Sample app written in Kotlin with the latest Android Architecture Components and  based Clean Architecture.


## Table of Contents
- [Introduction](#introduction)
- [Architecture](#architecture)
- [Libraries](#libraries)
- [Testing](#testing)


## Introduction

An Android application to showcase different bitcoin charts.
The application consumes data from the [BLOCKCHAIN API](https://www.blockchain.com/api/charts_api)

What you will find in this App:
App renders charts for three metrics viz. [Market Price](https://api.blockchain.info/charts/market-price?timespan=30days), [Transactions](https://api.blockchain.info/charts/trade-volume?timespan=30days&rollingAverage=8hours), [Trade Volume](https://api.blockchain.info/charts/trade-volume?timespan=30days&rollingAverage=8hours)
App Handles Success, Loading and Failure Cases, Also provide functionality to Retry on failure
other details:
- Minimum Api Level : 24
- compileSdkVersion : 30
- targetSdkVersion : 30
- Build System : [Gradle](https://gradle.org/)


## Architecture

The application follows clean architecture because of the benefits it brings to software which includes scalability, maintainability and testability.
It enforces separation of concerns and dependency inversion, where higher and lower level layers all depend on abstractions.
In the project, the layers are separated into different gradle modules namely:

- Domain
- Data
- App

These modules are Kotlin modules except the App module. The reason being that the low level layers need to be independent of the Android framework.
One of the key points of clean architecture is that low level layers should be platform agnostic. As a result, the domain and data layers can be plugged into a kotlin multiplatform project for example, and it will run just fine because we don't depend on the android framework.
The data layer contains implementation that use Retrofit to fetch data and conforms to contracts defined in domain.

The project has an app module that essentially serves as the presentation layer. Right now, it currently has the charts feature that holds the UI code and presents data to the users.

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to setup.
Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming, along with a fine suite of operators that make it a robust solution.



#### Domain
The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app. All other layers have their own representation of these domain models, and Mapper classes (or adapters) are used to transform the domain models to each layer's domain model representation.
Usecases which represent a single unit of business logic are also defined in the domain layer, and are consumed by the presentation layer.
Writing mappers and models can take a lot of effort and result in boilerplate, but they make the codebase much more maintainable and robust by separating concerns.

#### Data
The Data layer implements the contract for providing data defined in the domain layer,
Remote relies on Retrofit library to fetch data from the [BLOCKCHAIN REST API](https://www.blockchain.com/api/charts_api),  while the cache layer uses [RETROFIT CACHE](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-cache/).
The remote layer contains an `OkHttp Interceptor` that modifies api requests and add the cache-headers to the request.


#### Presentation
App uses the MVVM pattern for the presentation layer. The Model essentially exposes
the various states the view can be in. The ViewModel handles the UI logic and provides
data via Android architectural component LiveData to the view. The ViewModel talks to
the domain layer with the individual use case. The reason for using the `Jetpack Viewmodel` is that it survives configuration changes,
and thus ensures that the view state is persisted across screen rotation.


## Libraries

Libraries used in the application are:

- [Jetpack](https://developer.android.com/jetpack)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
  and act as a channel between use cases and UI.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Provides an observable data holder class.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android and Java
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming in order
to obtain data from the network.
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - Awesome library for rendering charts
- [JUnit](https://junit.org/junit4/) - This was used for unit testing the various layers.
- [Espresso](https://developer.android.com/training/testing/espresso) - This was used for unit testing the various layers.
- [Truth](https://truth.dev/) - Assertions Library, provides readability as far as assertions are concerned.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection plays a central role in the architectural pattern used.
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses.



## Testing

Testing is done with Junit4 testing framework, and with Google Truth for making assertions. The test uses fake objects for all tests instead of mocks, making it easier to verify interactions between objects and their dependencies, and simulate the behavior of the real objects.
Each layer has its own tests. The remote layer makes use of Mockwebserver to test the api requests and verify that mock Json responses provided in the test resource folder are returned.
The presentation layer is extensively unit-tested to ensure that the viewmodel renders the correct view states.

 UI tests just view state is rendered as expected. However, the extensive unit test coverage ensures that the app works as expected.

### PS: Espresso test uses deprecated ActivityRule, exhausted enough to try upgrading at this time