# PinView

Provides a widget for enter PASSWORD/OTP code etc on Android 5+ (API 22).

<p><img src="https://user-images.githubusercontent.com/59422918/174340464-f854c194-87d6-46ff-b8c1-80049302c1f6.png" height="35%" /></p>

## Dependency

### JitPack

``` Groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.pdrozz:pinview:1.0.2'
}
```

## Usage

### Step 1:

Add PinView in your layout.

#### XML

``` xml
<com.pdrozz.view.PinView
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:pinCount="5"
  app:isPassword="true"
  app:pinPadding="6dp"
  android:textSize="30sp"
  android:textColor="#000000"
  android:textStyle="bold"
  android:fontFamily="@font/sf_pro_medium"
  app:layout_constraintBottom_toBottomOf="parent"
  app:layout_constraintLeft_toLeftOf="parent"
  app:layout_constraintRight_toRightOf="parent"
  app:layout_constraintTop_toTopOf="parent" />
```

#### Kotlin

``` Kotlin
pinView.text = "12345"
pinView.clear()
pinView.setIsEnabled(false)
pinView.requestFocusToPin()
pinView.requestFocusToPin(pinIndex = 1)
pinView.setPinBackground(R.drawable.background_pin_item)
pinView.onTextChangedListener = { text ->
    Log.d("PinViewSample", "onCreate: $text")
}
```

### Step 2:

Specifies `pinViewStyle` in your theme,

``` xml
<style name="AppTheme" parent="Theme.AppCompat.Light">
    ...
    <item name="pinViewStyle">@style/pinViewStyle</item>
</style>

<style name="pinViewStyle" parent="Widget.AppTheme.PinView">
    <item name="pinCount">6</item>
    <item name="pinPadding">4dp</item>
    <item name="isPassword">false</item>
</style>
```

or use the `PinWidget.PinView` style.

``` xml
<com.chaos.view.PinView
    android:id="@+id/pinView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    style="@style/pinViewStyle" />
```

### Step 3 (Optional):

You can use any drawable state using selector to pin item background

``` xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    ...
    <item android:state_focused="true">
        <shape android:shape="rectangle">
            <corners android:radius="6dp" />
            <stroke android:width="1dp" android:color="@color/focused_color" />
        </shape>
    </item>
</selector>
```
