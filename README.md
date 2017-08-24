# movies
Instant app with Dagger2, ViewModels, and the Lifecycle

## Description
Trying out the new `instantapp` concept with `architecture components` and dependency injection via `dagger2`.

## Architecture
Using `mvvm` to separate layers and keep things testable.

## Kotlin
Mixing Kotlin in here and there. It's still a bit of headache to do unit testing and use `kotlin`, so w/e.

## Feedback
I have a full-time job in the Bay area, so in between `pr`s at my company and beer I'll try to work on this here and there. 
I'd appreciate feedback.

## Things to keep in mind
There are two oustanding bugs with `instantapp`s that very much matter.
1) `databinding` doesn't work in feature modules.
2) `lifecycle` fragments and activities don't yet work in feature modules. 

Until those are fixed...well...Google has a lot on their plate.
