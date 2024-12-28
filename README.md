# Android.Notifications.Forwarder

## Description

This app listens to all notifications and forwards them to a web endpoint in JSON format using a POST method. The JSON payload includes the following fields: `appName`, `notificationTitle`, `notificationText`, `timestamp`, and `notificationId`.

## Features

- Listens to all notifications
- Forwards notifications to a web endpoint in JSON format
- Provides a settings page to configure which apps should forward notifications
- Implements a retry mechanism for unreachable web endpoints
- Saves notifications locally when the web endpoint is unreachable
- Syncs notifications in the background when the endpoint becomes available
- Requests notification access permission from the user
- Uses Moshi for JSON handling
- Uses JobScheduler or WorkManager for efficient background processing

## Building and Running the App

1. Clone the repository:
   ```
   git clone https://github.com/tspascoal/Android.Notifications.Forwarder.git
   ```
2. Open the project in Android Studio.
3. Build the project using Gradle.
4. Run the app on an Android device or emulator.

## Configuring the Settings Page

1. Open the app and navigate to the settings page.
2. Use the search bar to quickly find specific apps.
3. Check or uncheck the apps you want to forward notifications from.
4. Input the web endpoint URL in the provided field.
5. Save the settings and return to the main screen.
