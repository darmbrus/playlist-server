# playlist-server
## Description
Project to create and manipulate playlists for a Spotify user. Can expand (turn
track list in to unique album list) and randomize a playlist for a user. Server
caches first user to log in and will create weekly expanded discover weekly playlist
every Monday at 2am.

## Build instructions
* Clone [playlist-app](https://github.com/darmbrus/playlist-app) and playlist-server
to your local machine.
* In playlist-app run:
  * ```npm install ```
  * ```npm run build```
  * ```cp -r [playlist-app]\build\* [playlist-server]\src\main\resources\static\```
  Where: {folder location}
* In playlist-server run:
  * ```.\gradlew build``` (win cmd)
* Fill in properties files found in ```properties\dev```
  * ```clientId``` - from spotify.
  * ```clientSecret``` - from spotify.
  * ```callbackUrl``` - ```[host]/callback``` Must be registered with spotify.
  * ```discoverWeeklyId``` - Playlist id of your discover weekly. Only needed for
   weekly service execution. Set value to ```none``` if unknown.
* Copy the following files together into a separate location and launch jar.
Ex: ```java -jar playlist-server-0.0.1.jar```
  * ```[playlist-server]\properties\dev\secure.properties```
  * ```[playlist-server]\properties\dev\application.properties```
  * ```[playlist-server]\build\libs\playlist-server-{version}.jar```
* Navigate to root in web browser. Ex: ```localhost:8080/```
