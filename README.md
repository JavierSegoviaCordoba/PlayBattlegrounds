# PlayBattlegrounds
[![CircleCI](https://circleci.com/gh/voghDev/PlayBattlegrounds/tree/master.svg?style=svg)](https://circleci.com/gh/voghDev/PlayBattlegrounds/tree/master)

Sample project to do some requests to the PUBG Open API

<img height="100" src="./img/kotlin.png" width="100"> <img height="100" src="https://avatars2.githubusercontent.com/u/29458023?v=4&amp;s=200" width="100">

This is a small App that tries to work on the following aspects:

- Dependency Injection in Kotlin using [Kodein 5.x][1]
- Basic usage of Monads such as Either, using [arrow-kt][2]
- a testable Kotlin architecture where all requirements can be covered with Unit Tests
- Kotlin Code style & formatting check using [ktlint][3]
- Android UI Design using [ConstraintLayout][5]
- Continous Integration using [CircleCI][8] - thanks to [JcMinarro][6] for [this awesome PR][9]

![Sample][appSample]

Installing
----------

Clone this repository and create a file named `pubg-api.properties`. Paste your PUBG ApiKey inside it like this:

    apiKey=abc12345

where `abc12345` is your PUBG ApiKey. You can see an example in [pubg-api-sample.properties][7]

Icons
-----

All icons, such as the [Chicken dinner](https://www.flaticon.com/free-icon/roast-chicken_889702#term=chicken&page=1&position=36) were downloaded from [flaticon.com][4]

[1]: https://github.com/Kodein-Framework/Kodein-DI/
[2]: https://github.com/arrow-kt/arrow
[3]: https://github.com/shyiko/ktlint
[4]: http://www.flaticon.com
[5]: https://developer.android.com/reference/android/support/constraint/ConstraintLayout
[6]: https://github.com/JcMinarro
[7]: https://github.com/voghDev/PlayBattlegrounds/blob/master/pubg-api-sample.properties
[8]: https://circleci.com/
[9]: https://github.com/voghDev/PlayBattlegrounds/pull/9

[appSample]: ./img/sample.gif
