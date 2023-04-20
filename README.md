# Getting Started

You can clone or download this project to your local machine using Github
api [https://github.com/jakal23/Rijks-Museum-task]

## Who is it for?

* Android developers looking for a way to structure their app in a testable and maintainable way.
* Advanced developers looking for quick reference.

# Requirement

- *Android Studio IDE*
- *Android Device (SDK 24-33)*

# Tools used

Google

- ```com.google.android.material```
- ```com.google.code.gson```
- ```com.google.dagger:hilt```

Android x

- ```androidx.navigation:navigation-fragment```
- ```androidx.paging:paging-runtime```

Third party

- ```com.squareup.retrofit2```
- ```com.github.bumptech.glide```

Tests junit 4

- ```com.google.truth```
- ```org.mockito.kotlin```

# Service

There is single service to manage url calls. In this interface we have specified ```collections```
requests.
To make the Collection search request we need to send some parameters. In this case we have to
add ```q``` query parameters with ```@Query``` annotation.

```kotlin 
interface CollectionService {

    @GET("api/{culture}/collection")
    suspend fun searchCollection(
        @Path("culture") languageCode: String,
        @Query("q") query: String,
        @Query("s") sortBy: String,
        @Query("p") page: Int,
        @Query("ps") limit: Int
    ): CollectionResponse

    @GET("api/{culture}/collection/{number}")
    suspend fun loadCollectionDetails(
        @Path("culture") languageCode: String,
        @Path("number") objectNumber: String,
    ): CollectionDetailResponse
}
```

# Screenshot

<table>
  <tr>
    <td>Collection list</td>
    <td>Collections sort</td>
    <td>Collection detail</td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/9334960/233311089-6989427b-abee-4428-9779-9510e0c2a134.png" width=216 height=456></td>
    <td><img src="https://user-images.githubusercontent.com/9334960/233311910-4868f06b-45ea-44a8-ae4b-95b7cb752a24.png" width=216 height=456></td>
    <td><img src="https://user-images.githubusercontent.com/9334960/233311131-8dc4cdcb-2f1c-4b8e-8019-3d94fc356ea4.png" width=216 height=456></td>
  </tr>
 </table>

# Additional resources

Check out these Wiki pages to learn more about Rijksmuseum:

- [Notable Community Contributions](https://en.wikipedia.org/wiki/Rijksmuseum)

- [Api](https://data.rijksmuseum.nl/object-metadata/api)

# About

Rijksmuseum is an android application for online investigating museum arts.
We provides a lot of information about art makers and pictures.

The Rijksmuseum is the national museum of the Netherlands.
We tell the story of 800 years of Dutch history, from 1200 to now.
In addition, we organize several exhibitions per year from our own collection and with (inter)
national loans.

