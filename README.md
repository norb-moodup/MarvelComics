# MarvelComics
Marvel App MVVM 

#### Achitekture and project description:
Project is written in a MVVM pattern
I decided to take this pattern because I know this pattern and wrote some apps in this pattern. Maybe now when I reed about MVI I may go with this way but I need more articules to good know this pattern.
I didn't want to over engineer this easy to write app so I didn't use too much libraries or additional files. My goal was writing a correct app with MVVM pattern and I hope that i did it.
I decided to write this app in Kotlin although most of my Dev carrer I was writting app in Java but we have 2020 and in my opinion Java is litle old when we're talking about Android apps and now most developers writting app in Kotlin. I'm happy what i heard on meeting that Neos goes this way and we'll change Java to Kotlin successively.
When I wrote app I base on our company workflow and work habits.
I used basic files in whole apps like this it is Models, ApiClient and ApiService to get data from backend, 
I added some external libraries to speed up work for example: Glide, Gson, Retrofit...
there is only a basic libraries, nothing special
I didn't decide to add cache because in my opinion it's not neccessary in this app, but if I decided to add cache, it would allow offline use
App has a pagination for both results but it's not working 100% correct, it still need small polishing 
