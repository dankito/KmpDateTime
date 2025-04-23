# Kotlin Multiplatform DateTime
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.dankito.datetime/kmp-datetime/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.dankito.datetime/kmp-datetime)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Why another DateTime library?

[kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) is great and in most cases you should use that one, but it has the following flaws:
- Incompatibilities between version 0.5 and 0.6 which leads to compile errors when both 
are on the class path (e.g. due to a third party library that references the other version).
- It doesn't compile with GraalVM.

So in cases where you want to avoid above circumstances and need only pure data classes to hold date time values and send it over the wire with almost no logic like date time calculations, this library's for you.

It features:

- The date time classes are pure data classes with almost no logic or platform specific code in it. The less logic, the less can go wrong.
- Familiar API. The API is almost the same as in Java Time and kotlinx-datetime.
- The few logic to create and parse an ISO string is extracted from data classes to `DateTimeFormatter` and `DateTimeParser`.
- The only platform specific code are the `now()` methods (`Instant.now()`, `LocalDate.today()`, ...) and converting an `Instant` to `LocalDateTime` at UTC and back.
- Serializers that automatically convert them to and from ISO 8601 representation with kotlinx-serialization and Jackson.
- Additional serializers to convert them to and from there components (like `{"year":2020,"month":12,"day":9}`) and `Instant` to and from Epoch milliseconds.


## Setup

### Gradle (Kotlin DSL)

```kotlin
implementation("net.dankito.datetime:kmp-datetime:1.1.0")
```

### Maven

```xml
<dependency>
   <groupId>net.dankito.datetime</groupId>
   <artifactId>kmp-datetime-jvm</artifactId>
   <version>1.1.0</version>
</dependency>
```



## Usage

### LocalDate

#### Creation

```kotlin
// month as number from 1 - 12
val date = LocalDate(2015, 10, 21)
// month as enum
val date2 = LocalDate(2015, Month.October, 21)
// with default values
val date3 = LocalDate(2015) // default values for month and day are January and 1; returns LocalDate(2015, Month.January, 1)

// current date
val today = LocalDate.today()

// creating from ISO 8601 date string
val fromIsoString = LocalDate.parse("2015-10-21")
// lenient parsing, returns null in case of invalid ISO string (LocalDate.parse() throws an exception in case of invalid ISO string)
val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21GMT") // returns null, 'GMT' is not a valid part of an ISO date string
```

#### Conversion

```kotlin
val date = LocalDate(2015, Month.October, 21)

// components
val year: Int = date.year // returns 2015
val month: Month = date.month // returns Month.October
val monthNumber: Int = date.monthNumber // returns 10
val day: Int = date.day // returns 21
// the day of week of this date; returns null in case of an invalid date
val dayOfWeek: DayOfWeek? = date.dayOfWeek // returns DayOfWeek.Wednesday

// to LocalTimeTime (from there you can convert it further to Instant, see LocalDateTime examples)
val atMidnight: LocalDateTime = date.atStartOfDay()
val byHourMinutesSeconds: LocalDateTime = date.atTime(12, 15, 30)
val byLocalTime: LocalDateTime = date.atTime(LocalTime.Noon)

// to ISO 8601 string
val isoString = date.isoString // returns "2015-10-21"
val isoStringDotSeparated = date.isoStringDotSeparated // variant that uses '.' instead of '-' as separator; returns "2015.10.21"
```


### LocalTime

#### Creation

```kotlin
val time = LocalTime(hour = 12, minute = 15, second = 30, nanosecondOfSecond = 123)
// with default values
val time2 = LocalTime(0) // default values for minute, second and nanosecond of second are 0; returns LocalTime(0, 0, 0, 0)

// current time
val now = LocalTime.now()

// time constants
val midnight = LocalTime.Midnight // synonym for LocalTime.StartOfDay
val noon = LocalTime.Noon
val endOfDay = LocalTime.EndOfDay // equals LocalTime(23, 59, 59, 999_999_999)

// creating from ISO 8601 time string
val fromIsoString = LocalTime.parse("12:15:30.654")
// lenient parsing, returns null in case of invalid ISO string (LocalTime.parse() throws an exception in case of invalid ISO string)
val fromIsoStringLenient = LocalTime.parseOrNull("12:15:30.654+02:00") // returns null, '+02:00' is not a valid part of an ISO time string
```

#### Conversion

```kotlin
val time = LocalTime(12, 15, 30, nanosecondOfSecond = 654_000_000)

// components
val hour: Int = time.hour // returns 12
val minute: Int = time.minute // returns 15
val second: Int = time.second // returns 30
val nanosecondOfSecond: Int = time.nanosecondOfSecond // returns 654_000_000

// to LocalTimeTime (from there you can convert it further to Instant, see LocalDateTime examples)
val byYearMonthDay: LocalDateTime = time.atDate(2015, Month.October, 21)
val byLocalDate: LocalDateTime = time.atDate(LocalDate(2015, Month.October, 21))

// to ISO 8601 string
val isoString = time.isoString // returns "12:15:30.654"
```


### LocalDateTime

#### Creation

```kotlin
// month as number from 1 - 12
val dateTime = LocalDateTime(year = 2015, monthNumber = 10, day = 21, hour = 12, minute = 15, second = 30, nanosecondOfSecond = 654)
// month as enum
val dateTime2 = LocalDateTime(2015, Month.October, 21, 12, 15)
// with default values
val dateTime3 = LocalDateTime(2015) // default values for month and day are January and 1, for all other values 0; returns LocalDateTime(2015, Month.January, 1, 0, 0, 0, 0)
// by date and time
val dateTime4 = LocalDateTime(LocalDate(2015, Month.October, 21), LocalTime(12, 15, 30, 654))

// current datetime
val now = LocalDateTime.now()

// creating from ISO 8601 datetime string
val fromIsoString = LocalDateTime.parse("2015-10-21T12:15:30.654")
// lenient parsing, returns null in case of invalid ISO string (LocalDateTime.parse() throws an exception in case of invalid ISO string)
val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21T12:15:30.654GMT") // returns null, 'GMT' is not a valid part of an ISO datetime string
```

#### Conversion

```kotlin
val dateTime = LocalDateTime(2015, Month.October, 21, 12, 15, 30)

// get date or time
val date = dateTime.date
val time = dateTime.time

// components
val year: Int = dateTime.year // returns 2015
val month: Month = dateTime.month // returns Month.October
val monthNumber: Int = dateTime.monthNumber // returns 10
val day: Int = dateTime.day // returns 21
// the day of week of this date; returns null in case of an invalid date
val dayOfWeek: DayOfWeek? = dateTime.dayOfWeek // returns DayOfWeek.Wednesday
val hour: Int = dateTime.hour // returns 12
val minute: Int = dateTime.minute // returns 15
val second: Int = dateTime.second // returns 30
val nanosecondOfSecond: Int = dateTime.nanosecondOfSecond // returns 0

// to Instant
val instantAtUtc: Instant = dateTime.toInstantAtUtc()
val instantAtSystemTimeZone: Instant = dateTime.toInstantAtSystemTimeZone()

// to ISO 8601 string
val isoString = dateTime.isoString // returns "2015-10-21T12:15:30"
```


### Instant

#### Creation

```kotlin
// by seconds since epoch (1970-01-01 midnight)
val instant = Instant(epochSeconds = 1_745_000_000, nanosecondsOfSecond = 654)
// by milliseconds since epoch
val instant2 = Instant.ofEpochMilli(1_745_000_000_654) // returns Instant(1_745_000_000, 654_000_000)
// with default values
val instant3 = Instant(1_745_000_000) // default values for nanosecondsOfSecond is 0; returns Instant(1_745_000_000, 0)
// by datetime
val instant4 = LocalDateTime(2015, Month.October, 21, 12, 15).toInstantAtUtc()

// current instant
val now = Instant.now()

// Instant constants
val epoch = Instant.Epoch

// creating from ISO 8601 instant string
val fromIsoString = Instant.parse("2015-10-21T12:15:30.654Z")
// lenient parsing, returns null in case of invalid ISO string (Instant.parse() throws an exception in case of invalid ISO string)
val fromIsoStringLenient = LocalTime.parseOrNull("2015-10-21T12:15:30.654") // returns null, does not end with 'Z'
```

#### Conversion

```kotlin
val instant = Instant(1_445_418_487)

// components
val epochSeconds: Long = instant.epochSeconds // returns 1_445_418_487
val nanosecondsOfSecond: Int = instant.nanosecondsOfSecond // returns 654_000_000
val epochMillis: Long = instant.toEpochMilliseconds() // returns 1_445_418_487_654

// to LocalDateTime (from there you can convert it further to LocalDate and LocalTime, see LocalDateTime examples)
val dateTimeAtUtc: LocalDateTime = instant.toLocalDateTimeAtUtc()
val dateTimeAtSystemTimeZone: LocalDateTime = instant.toLocalDateTimeAtSystemTimeZone()

// to ISO 8601 string
val isoString = instant.isoString // returns "2015-10-21T12:15:30Z"
val isoStringAtSystemTimeZone = instant.isoStringAtSystemTimeZone // result depends on your system's time zone, e.g. for CET "2015-10-21T11:15:30Z"
```


### OffsetDateTime

`OffsetDateTime` is in experimental stage, its API may change in the future.


## Serialization

Supports both `kotlinx-serialization` and `Jackson` out of the box. 

For `Jackson` simply call `ObjectMapper().findAndRegisterModules()` - it finds and registers our module automatically -
or register it manually with `ObjectMapper().registerModule(KmpDateTimeModule())`.

By default values are serialized to and from ISO 8601 strings.
But you can configure this via `SerializationConfig`, e.g.
```kotlin
SerializationConfig.LocalDateDefaultFormat = DateTimeSerializationFormat.Components

SerializationConfig.InstantDefaultFormat = InstantSerializationFormat.EpochMilliseconds

// you can also configure your custom serializer
SerializationConfig.LocalTimeDefaultFormat = DateTimeSerializationFormat.Custom // to enable that `SerializationConfig.LocalTimeCustomSerializer` is used
SerializationConfig.LocalTimeCustomSerializer = MyLocalTimeSerializer
```


## License

```
Copyright 2025 dankito

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```